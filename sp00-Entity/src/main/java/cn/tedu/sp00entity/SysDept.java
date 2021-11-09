package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * 部门信息表
 *
 * @author oliver zhang
 * @date 2021-03-31
 */
public class SysDept extends BaseEntity implements Serializable {

    /**
     * 部门编号
     */
    private long id;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 上级部门编号
     */
    private long pid;
    /**
     * 排序号
     */
    private long sort;
    /**
     * 编号
     */
    private String number;
    /**
     * 联系人
     */
    private String userName;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 联系座机
     */
    private String tel;
    /**
     * E-mail
     */
    private String email;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 类型 关联配置表
     */
    private long type;
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
     * 部门编号
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /**
     * 部门名称
     */
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    /**
     * 上级部门编号
     */
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }


    /**
     * 排序号
     */
    public long getSort() {
        return sort;
    }

    public void setSort(long sort) {
        this.sort = sort;
    }


    /**
     * 编号
     */
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    /**
     * 联系人
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    /**
     * 联系电话
     */
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    /**
     * 联系座机
     */
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    /**
     * E-mail
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * 联系地址
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * 类型 关联配置表
     */
    public long getType() {
        return type;
    }

    public void setType(long type) {
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
