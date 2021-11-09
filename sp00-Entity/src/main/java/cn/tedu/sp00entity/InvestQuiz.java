package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * 调查问卷表
 *
 * @author oliver zhang
 * @date 2021-03-31
 */
public class InvestQuiz extends BaseEntity implements Serializable {

    /**
     * key id
     */
    private String id;
    private String title;
    private String investMemo;
    /**
     * 测试或者训练类型
     */
    private String typeTestOrLearing;
    private String picture;
    /**
     * 显示报告需要的费用
     */
    private double reportNeedMoney;
    /**
     * 开始测试需要的费用
     */
    private double testNeedMoney;
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
     * key id
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getInvestMemo() {
        return investMemo;
    }

    public void setInvestMemo(String investMemo) {
        this.investMemo = investMemo;
    }


    /**
     * 测试或者训练类型
     */
    public String getTypeTestOrLearing() {
        return typeTestOrLearing;
    }

    public void setTypeTestOrLearing(String typeTestOrLearing) {
        this.typeTestOrLearing = typeTestOrLearing;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    /**
     * 显示报告需要的费用
     */
    public double getReportNeedMoney() {
        return reportNeedMoney;
    }

    public void setReportNeedMoney(double reportNeedMoney) {
        this.reportNeedMoney = reportNeedMoney;
    }


    /**
     * 开始测试需要的费用
     */
    public double getTestNeedMoney() {
        return testNeedMoney;
    }

    public void setTestNeedMoney(double testNeedMoney) {
        this.testNeedMoney = testNeedMoney;
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
