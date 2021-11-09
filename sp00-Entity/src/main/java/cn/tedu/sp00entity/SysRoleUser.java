package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * 角色用户关联表
 *
 * @author oliver zhang
 * @date 2021-03-31
 */
public class SysRoleUser extends BaseEntity implements Serializable {

    /**
     * ID
     */
    private long id;
    /**
     * 角色编号
     */
    private long roleid;
    /**
     * 用户编号
     */
    private long userid;
    /**
     * 锁定标志(1:锁定;0:激活)
     */
    private String status;


    /**
     * ID
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
     * 用户编号
     */
    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
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
