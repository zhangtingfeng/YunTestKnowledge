package cn.tedu.sp0ag4studio.system.admin.service.impl;

import cn.tedu.sp0ag4studio.common.service.impl.BaseServiceImpl;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import cn.tedu.sp0ag4studio.core.util.G4Utils;
import cn.tedu.sp0ag4studio.system.admin.service.RoleService;
import cn.tedu.sp0ag4studio.system.common.util.idgenerator.IDHelper;

/**
 * 角色管理与授权业务实现类
 *
 * @author OSWorks-XC
 * @since 2010-04-13
 */
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

    /**
     * 保存角色
     *
     * @param pDto
     * @return
     */
    public Dto saveRoleItem(Dto pDto) {
        pDto.put("roleid", IDHelper.getRoleID());
        g4Dao.insert("Role.saveRoleItem", pDto);
        return null;
    }

    /**
     * 删除角色
     *
     * @param pDto
     * @return
     */
    public Dto deleteRoleItems(Dto pDto) {
        Dto dto = new BaseDto();
        String[] arrChecked = pDto.getAsString("strChecked").split(",");
        for (int i = 0; i < arrChecked.length; i++) {
            dto.put("roleid", arrChecked[i]);
            g4Dao.delete("Role.deleteEaroleAuthorizeInRoleManage", dto);
            g4Dao.delete("Role.deleteEauserauthorizeInRoleManage", dto);
            g4Dao.delete("Role.deleteEarolemenupartInRoleManage", dto);
            g4Dao.delete("Role.deleteEaroleInRoleManage", dto);
        }
        return null;
    }

    /**
     * 修改角色
     *
     * @param pDto
     * @return
     */
    public Dto updateRoleItem(Dto pDto) {
        g4Dao.update("Role.updateRoleItem", pDto);
        if (!pDto.getAsString("deptid").equals(pDto.getAsString("deptid_old"))) {
            g4Dao.delete("Role.deleteEaroleAuthorizeInRoleManage", pDto);
        }
        return null;
    }

    /**
     * 保存角色授权信息
     *
     * @param pDto
     * @return
     */
    public Dto saveGrant(Dto pDto) {
        g4Dao.delete("Role.deleteERoleGrants", pDto);
        String[] menuids = pDto.getAsString("menuid").split(",");
        for (int i = 0; i < menuids.length; i++) {
            String menuid = menuids[i];
            if (G4Utils.isEmpty(menuid))
                continue;
            pDto.put("menuid", menuid);
            pDto.put("authorizeid", IDHelper.getAuthorizeid4Role());
            g4Dao.insert("Role.saveRoleGrantItem", pDto);
        }
        return null;
    }

    /**
     * 保存角色用户关联信息
     *
     * @param pDto
     * @return
     */
    public Dto saveSelectUser(Dto pDto) {
        g4Dao.delete("Role.deleteEaUserAuthorizeByRoleId", pDto);
        String[] userids = pDto.getAsString("userid").split(",");
        for (int i = 0; i < userids.length; i++) {
            String userid = userids[i];
            if (G4Utils.isEmpty(userid))
                continue;
            pDto.put("userid", userid);
            pDto.put("authorizeid", IDHelper.getAuthorizeid4User());
            g4Dao.insert("Role.saveSelectUser", pDto);
        }
        return null;
    }
}
