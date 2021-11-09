package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * 菜单管理
 *
 * @author oliver zhang
 * @date 2021-03-31
 */
public class SysMenu extends BaseEntity implements Serializable {

    /**
     * 编号
     */
    private long id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 父菜单ID，一级菜单为0
     */
    private long pid;
    /**
     * 菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)
     */
    private String url;
    /**
     * 授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)
     */
    private String perms;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private long type;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 排序
     */
    private long orderNum;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 更新人
     */
    private String lastUpdateBy;
    /**
     * 更新时间
     */
    private java.sql.Timestamp lastUpdateTime;
    /**
     * 是否删除  Y：已删除  N：正常
     */
    private String isDelete;
    private long level;


    /**
     * 编号
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /**
     * 菜单名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * 父菜单ID，一级菜单为0
     */
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }


    /**
     * 菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * 授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)
     */
    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }


    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }


    /**
     * 菜单图标
     */
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    /**
     * 排序
     */
    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }


    /**
     * 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }


    /**
     * 更新人
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }


    /**
     * 更新时间
     */
    public java.sql.Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(java.sql.Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    /**
     * 是否删除  Y：已删除  N：正常
     */
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }


    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

}
