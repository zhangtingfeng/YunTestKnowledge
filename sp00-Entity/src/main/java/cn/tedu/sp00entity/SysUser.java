package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * 后台用户管理
 *
 * @author oliver zhang
 * @date 2021-03-31
 */
public class SysUser extends BaseEntity implements Serializable {

    /**
     * 主键ID
     */
    private long id;
    /**
     * 店铺id
     */
    private long shopId;
    /**
     * 微信openid
     */
    private String wopenid;
    /**
     * 支付宝openid
     */
    private String zopenid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 性别
     */
    private String sex;
    /**
     * 上级领导ID
     */
    private long pid;
    /**
     * 工号
     */
    private String number;
    /**
     * 手机号
     */
    private String moile;
    /**
     * 部门id
     */
    private long deptid;
    /**
     * 职位名称
     */
    private String lname;
    /**
     * 角色ID
     */
    private long roleid;
    /**
     * 登陆账号
     */
    private String account;
    /**
     * 登陆密码
     */
    private String password;
    /**
     * 状态(1在职 0离职)
     */
    private long status;
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
     * 主键ID
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /**
     * 店铺id
     */
    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }


    /**
     * 微信openid
     */
    public String getWopenid() {
        return wopenid;
    }

    public void setWopenid(String wopenid) {
        this.wopenid = wopenid;
    }


    /**
     * 支付宝openid
     */
    public String getZopenid() {
        return zopenid;
    }

    public void setZopenid(String zopenid) {
        this.zopenid = zopenid;
    }


    /**
     * 用户名
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * 性别
     */
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    /**
     * 上级领导ID
     */
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }


    /**
     * 工号
     */
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    /**
     * 手机号
     */
    public String getMoile() {
        return moile;
    }

    public void setMoile(String moile) {
        this.moile = moile;
    }


    /**
     * 部门id
     */
    public long getDeptid() {
        return deptid;
    }

    public void setDeptid(long deptid) {
        this.deptid = deptid;
    }


    /**
     * 职位名称
     */
    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }


    /**
     * 角色ID
     */
    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }


    /**
     * 登陆账号
     */
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    /**
     * 登陆密码
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * 状态(1在职 0离职)
     */
    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
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
