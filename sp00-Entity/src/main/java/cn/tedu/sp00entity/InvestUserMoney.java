package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * @author oliver zhang
 * @date 2021-03-31
 */
public class InvestUserMoney extends BaseEntity implements Serializable {

    /**
     * 此数据需要递增
     */
    private long id;
    private double money;
    /**
     * 那个用户的钱ID
     */
    private String userId;
    /**
     * add or mint
     */
    private String addOrMinus;
    /**
     * 最终显示的多少钱
     */
    private double lastAllMoney;
    /**
     * 钱改变的原因
     */
    private String cause;
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
     * 此数据需要递增
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }


    /**
     * 那个用户的钱ID
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    /**
     * add or mint
     */
    public String getAddOrMinus() {
        return addOrMinus;
    }

    public void setAddOrMinus(String addOrMinus) {
        this.addOrMinus = addOrMinus;
    }


    /**
     * 最终显示的多少钱
     */
    public double getLastAllMoney() {
        return lastAllMoney;
    }

    public void setLastAllMoney(double lastAllMoney) {
        this.lastAllMoney = lastAllMoney;
    }


    /**
     * 钱改变的原因
     */
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
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
