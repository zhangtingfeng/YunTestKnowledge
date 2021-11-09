package cn.tedu.sp25.controller;

import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.JsonUtil;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp25.base.BaseController;

import org.apache.commons.lang3.StringUtils;

import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static cn.tedu.sp0ag4studio.common.util.WebUtils.getParamAsDto;

@RestController
@RequestMapping("/SysMenu")
public class SysMenuController extends BaseController {


    /**
     * 分页
     * 先调用
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/findNavTree")
    public JsonResult<?> findNavTree(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        try {
            String strgetData = JsonUtil.serialize(JsonResultbase64.getData());
            String strid = JsonUtil.getString(strgetData, "userid");
            Long userid = Long.parseLong(strid);


            List<Dto> resultList = findByUser(userid);

            resultList.sort((o1, o2) -> o1.getAsInteger("order_num").compareTo(o2.getAsInteger("order_num")));


            return JsonResult.ok("查询findNavTree").data(resultList);
        } catch (Exception e) {
            String strErr = debugLogUtil.send("findNavTree", "程序findNavTree报错", e);
            log.info(strErr);
            e.printStackTrace();
            return JsonResult.err(strErr);
        }
    }

    /**
     * 根据角色id获取菜单列表
     *
     * @param userId
     * @return
     */
    public List<Dto> findByUser(Long userId) {

        BaseDto baseDto = new BaseDto("userid", userId);
        baseDto.put("roleid", 1);
        List<Dto> roleList = bizService.queryForList("sysRoleUser.queryList", baseDto);

        List<Dto> menus = new ArrayList<>();
        if (roleList.isEmpty()) {
            menus = bizService.queryForList("sysMenu.queryMenuListByUserid", new BaseDto("id", userId));
        } else {
            menus = bizService.queryForList("sysMenu.queryList", new BaseDto());
        }


        List<Dto> sysMenus = new ArrayList<>();

        for (Dto menu : menus) {
            //遍历集合
            if (menu.getAsInteger("pid") == null || menu.getAsInteger("pid") == 0) {
                menu.put("level", 0);
                if (!exists(sysMenus, menu) && menu.getAsString("is_delete").equals("N")) {
                    sysMenus.add(menu);
                }
            }
        }

        findChildren(sysMenus, menus, 1);

        return sysMenus;
    }

    private void findChildren(List<Dto> SysMenus, List<Dto> menus, int menuType) {
        for (Dto SysMenu : SysMenus) {
            List<Dto> children = new ArrayList<>();
            for (Dto menu : menus) {
                if (menuType == 1 && menu.getAsInteger("type") == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue;
                }
                if (SysMenu.getAsInteger("id") != null && SysMenu.getAsInteger("id").equals(menu.getAsInteger("pid"))) {
                    menu.put("parentName", SysMenu.getAsString("name"));
                    menu.put("level", SysMenu.getAsInteger("level") + 1);
                    if (!exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            SysMenu.put("children", children);
            children.sort((o1, o2) -> o1.getAsInteger("order_num").compareTo(o2.getAsInteger("order_num")));
            findChildren(children, menus, menuType);
        }
    }

    /**
     * 判断当前权限是否添加过
     *
     * @param sysMenus
     * @param sysMenu
     * @return
     */
    private boolean exists(List<Dto> sysMenus, Dto sysMenu) {
        boolean exist = false;
        for (Dto menu : sysMenus) {
            if (menu.getAsString("id").equals(sysMenu.getAsString("id"))) {
                exist = true;
            }
        }
        return exist;
    }


    /**
     * 获取按钮信息
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/findPermissions")
    public JsonResult<?> findPermissions(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {

        try {

            String strgetData = JsonUtil.serialize(JsonResultbase64.getData());
            String userid = JsonUtil.getString(strgetData, "userid");

            BaseDto baseDto = new BaseDto("userid", userid);
            baseDto.put("roleid", 1);
            List<Dto> roleList = bizService.queryForList("sysRoleUser.queryList", baseDto);

            if (roleList.isEmpty()) {
                roleList = bizService.queryForList("sysMenu.getListByRole", new BaseDto("userid", userid));
            } else {
                roleList = bizService.queryForList("sysMenu.queryList", new BaseDto());
            }

            if (roleList.isEmpty()) {
                return JsonResult.err().code(9999).msg("请联系管理员配置权限");
                ///   result.setCode("9999");
                //  result.setMsg("请联系管理员配置权限");
                //  return result;
            }


            Set<String> perms = new HashSet<>();

            for (Dto sysMenu : roleList) {
                if (StringUtils.isNotEmpty(sysMenu.getAsString("perms"))) {
                    String[] permsList = sysMenu.getAsString("perms").split(",");
                    perms.addAll(Arrays.asList(permsList));
                }
            }
            return JsonResult.ok().data(perms);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.err(e.getLocalizedMessage());
            // result = reduceErr(e.getLocalizedMessage());
            // debugLog.send("QY", "程序报错" + request.getRequestURI(), e.getLocalizedMessage());

        }
        //return result;
    }


    @RequestMapping(value = "/editUser")
    public JsonResult<?> editUser(@RequestBody JsonResult JsonResultbase64) {
        //  Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = getParamAsDto(JsonResultbase64.datetoMap());

        JsonResult<?> result = JsonResult.ok();
        // String strgetData = JsonUtil.serialize(JsonResultbase64.getData());
//错误删除了表
        try {
            // String itokend =JsonResultbase64.getToken();

            Dto member = redisServiceImpl.getObject(JsonResultbase64.getToken(), BaseDto.class);


            String id = dto.getAsString("id");
            dto.put("tableName", "sysUser");
            if (StringUtils.isNotEmpty(id)) {
                //更新
                // 修改
                dto.put("updator", member == null ? "" : member.get("id"));
                bizService.updateInfo("sysUser.updateInfo", JsonResultbase64.getData());
            } else {
                //插入
                dto.put("creator", member == null ? "" : member.get("id"));
                dto.put("updator", member == null ? "" : member.get("id"));

                bizService.saveInfo("sysUser.saveInfo", dto);
            }


            if (StringUtils.isNotEmpty(id)) {
                Dto delete = new BaseDto();
                delete.put("tableName", "sysRoleUser");
                delete.put("method", "deleteInfo");
                delete.put("userid", dto.getAsString("id"));
                bizService.delete(delete);

                String userRoles = dto.getAsString("userRoles");
                if (StringUtils.isNotEmpty(userRoles)) {
                    for (String roleId : userRoles.split(",")) {
                        if (StringUtils.isNotEmpty(roleId)) {

                            Dto insert = new BaseDto();
                            insert.put("roleid", roleId);
                            insert.put("userid", dto.getAsString("id"));
                            insert.put("tableName", "sysRoleUser");
                            bizService.saveInfo("sysRoleUser.saveInfo", insert);
                        }
                    }
                }
            }
            //JsonResult.ok();
            result = JsonResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());
            // result = reduceErr(e.getLocalizedMessage());
        }
        return result;
    }


    @RequestMapping(value = "/findDeptTree")
    public JsonResult<?> findDeptTree(@RequestBody JsonResult JsonResultbase64) {
        JsonResult<?> result = JsonResult.ok();

        String strtoken = JsonResultbase64.getToken();
        Dto dto = getParamAsDto(JsonResultbase64.datetoMap());

        try {
            List<Dto> sysDepts = new ArrayList<>();

            List<Dto> depts = bizService.queryForList("sysDept.queryList", dto);
            for (Dto dept : depts) {
                if (dept.getAsInteger("pid") == null || dept.getAsInteger("pid") == 0) {
                    dept.put("level", 0);
                    sysDepts.add(dept);
                }
            }
            findChildren(sysDepts, depts);

            depts.sort((o1, o2) -> o1.getAsInteger("sort").compareTo(o2.getAsInteger("sort")));
            result = JsonResult.ok().data(sysDepts);

            // result.setData(sysDepts);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());
        }
        return result;
    }


    @RequestMapping(value = "/findProductTypeTree")
    public JsonResult<?> findProductTypeTree(@RequestBody JsonResult JsonResultbase64) {
        String strtoken = JsonResultbase64.getToken();
        Dto dto = getParamAsDto(JsonResultbase64.datetoMap());

        JsonResult<?> result = JsonResult.ok();

        try {
            List<Dto> sysDepts = new ArrayList<>();

            List<Dto> depts = bizService.queryForList("productType.queryList", dto);
            for (Dto dept : depts) {
                if (dept.getAsInteger("pid") == null || dept.getAsInteger("pid") == 0) {
                    dept.put("level", 0);
                    sysDepts.add(dept);
                }
            }
            findProductChildren(sysDepts, depts);

            depts.sort((o1, o2) -> o1.getAsInteger("id").compareTo(o2.getAsInteger("id")));

            result = result.ok().data(sysDepts);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());
        }
        return result;
    }


    @RequestMapping(value = "/selectDeptTree")
    public JsonResult<?> selectDeptTree(@RequestBody JsonResult JsonResultbase64) {
        String strtoken = JsonResultbase64.getToken();
        Dto dto = getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();

        try {
            if (!StringUtils.isNotEmpty(dto.getAsString("dept_name"))) {
                dto.put("dept_name", "");
            }
            List<Dto> depts = bizService.queryForList("sysDept.queryDeptList", dto);
            Set<String> parentList = new HashSet<>();
            depts.forEach(element -> {
                String parent = element.getAsString("parentList");
                if (StringUtils.isNotEmpty(parent)) {
                    parentList.addAll(Arrays.asList(parent.split(",")));
                }
            });
            result = JsonResult.ok().data(parentList);

        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());
        }
        return result;
    }


    @RequestMapping(value = "/findMenuTree")
    public JsonResult<?> findMenuTree(@RequestBody JsonResult JsonResultbase64) {
        String strtoken = JsonResultbase64.getToken();
        Dto dto = getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            Dto member = redisServiceImpl.getObject(strtoken, BaseDto.class);

//            根据用户id查询菜单id
            String userid = member.getAsString("id");
            dto.put("id", userid);


            BaseDto baseDto = new BaseDto("userid", userid);
            baseDto.put("roleid", 1);
            List<Dto> roleList = bizService.queryForList("sysRoleUser.queryList", baseDto);

            List<Dto> menus = new ArrayList<>();
            if (roleList.isEmpty()) {
                menus = bizService.queryForList("sysMenu.queryMenuListByUserid", dto);
            } else {
                menus = bizService.queryForList("sysMenu.queryList", new BaseDto());
            }

            List<Dto> resultList = new ArrayList<>();
            for (Dto menu : menus) {
                if (menu.getAsInteger("pid") == null || menu.getAsInteger("pid") == 0) {
                    menu.put("level", 0);
                    if (!exists(resultList, menu) && menu.getAsString("is_delete").equals("N")) {
                        resultList.add(menu);
                    }
                }
            }
            findChildren(resultList, menus);
            resultList.sort((o1, o2) -> o1.getAsInteger("order_num").compareTo(o2.getAsInteger("order_num")));
            result = JsonResult.ok().data(resultList);

        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());
        }
        return result;
    }


    private void findProductChildren(List<Dto> sysDepts, List<Dto> depts) {
        for (Dto sysDept : sysDepts) {
            List<Dto> children = new ArrayList<>();
            for (Dto dept : depts) {
                if (sysDept.getAsInteger("id") != null && sysDept.getAsInteger("id").equals(dept.getAsInteger("pid"))) {
                    dept.put("parentName", sysDept.getAsString("name"));
                    dept.put("level", sysDept.getAsInteger("level") + 1);
                    children.add(dept);
                }
            }
            sysDept.put("children", children);
            children.sort((o1, o2) -> o1.getAsInteger("id").compareTo(o2.getAsInteger("id")));
            findProductChildren(children, depts);
        }
    }


    private void findChildren(List<Dto> sysDepts, List<Dto> depts) {
        for (Dto sysDept : sysDepts) {
            List<Dto> children = new ArrayList<>();
            for (Dto dept : depts) {
                if (sysDept.getAsInteger("id") != null && sysDept.getAsInteger("id").equals(dept.getAsInteger("pid"))) {
                    dept.put("parentName", sysDept.getAsString("dept_name"));
                    dept.put("level", sysDept.getAsInteger("level") + 1);
                    children.add(dept);
                }
            }
            sysDept.put("children", children);
            children.sort((o1, o2) -> o1.getAsInteger("order_num").compareTo(o2.getAsInteger("order_num")));
            findChildren(children, depts);
        }
    }

}

