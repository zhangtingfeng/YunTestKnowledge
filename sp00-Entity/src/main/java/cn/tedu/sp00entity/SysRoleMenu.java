package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * 角色授权表
 *
 * @author oliver zhang
 * @date 2021-03-31
 */
public class SysRoleMenu extends BaseEntity implements Serializable {

    /**
     * 授权ID
     */
    private long id;
    /**
     * 角色编号
     */
    private long roleid;
    /**
     * 菜单编号
     */
    private long menuid;
    /**
     * 锁定标志(1:锁定;0:激活)
     */
    private String status;


    /**
     * 授权ID
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /**
     * 角色编号
     */
    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }


    /**
     * 菜单编号
     */
    public long getMenuid() {
        return menuid;
    }

    public void setMenuid(long menuid) {
        this.menuid = menuid;
    }


    /**
     * 锁定标志(1:锁定;0:激活)
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
