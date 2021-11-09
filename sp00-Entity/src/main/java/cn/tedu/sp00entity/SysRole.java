package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * 角色信息表
 *
 * @author oliver zhang
 * @date 2021-03-31
 */
public class SysRole extends BaseEntity implements Serializable {

    /**
     * 角色编号
     */
    private long id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
     */
    private String type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 锁定标志(1:锁定;0:激活)
     */
    private String status;
    /**
     * 创建人ID
     */
    private long creator;
    /**
     * 修改人ID
     */
    private long updator;
    /**
     * Y表示删除  N表示未删除
     */
    private String isDelete;


    /**
     * 角色编号
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /**
     * 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    /**
     * 角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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


    /**
     * 创建人ID
     */
    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }


    /**
     * 修改人ID
     */
    public long getUpdator() {
        return updator;
    }

    public void setUpdator(long updator) {
        this.updator = updator;
    }


    /**
     * Y表示删除  N表示未删除
     */
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

}
