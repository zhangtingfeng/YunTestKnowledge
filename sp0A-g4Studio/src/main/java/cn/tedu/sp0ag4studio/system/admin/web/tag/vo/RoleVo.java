package cn.tedu.sp0ag4studio.system.admin.web.tag.vo;

import cn.tedu.sp0ag4studio.core.metatype.BaseVo;

/**
 * 角色值对象
 *
 * @author OSWorks-XC
 * @since 2010-04-22
 */
public class RoleVo extends BaseVo {
    private String roleid;
    private String rolename;
    private String deptid;
    private String iconcls;
    private String roletype;
    private String checked;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls;
    }

    public String getRoletype() {
        return roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

}
