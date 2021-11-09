package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * @author oliver zhang
 * @date 2021-03-31
 */
public class InvestQuizUser extends BaseEntity implements Serializable {

    private String id;
    /**
     * 用户吃那个药
     */
    private String quizId;
    /**
     * 那个用户吃药
     */
    private long userId;
    /**
     * 用户的测验成绩
     */
    private double quziUserScore;
    /**
     * 用户吃药备注
     */
    private String userMemo;
    /**
     * 用户是否支付、显示报告需要的费用
     */
    private double reportpayMoney;
    /**
     * 用户是否支付、开始测试需要的费用
     */
    private double testpayMoney;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    /**
     * 用户吃那个药
     */
    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }


    /**
     * 那个用户吃药
     */
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    /**
     * 用户的测验成绩
     */
    public double getQuziUserScore() {
        return quziUserScore;
    }

    public void setQuziUserScore(double quziUserScore) {
        this.quziUserScore = quziUserScore;
    }


    /**
     * 用户吃药备注
     */
    public String getUserMemo() {
        return userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = userMemo;
    }


    /**
     * 用户是否支付、显示报告需要的费用
     */
    public double getReportpayMoney() {
        return reportpayMoney;
    }

    public void setReportpayMoney(double reportpayMoney) {
        this.reportpayMoney = reportpayMoney;
    }


    /**
     * 用户是否支付、开始测试需要的费用
     */
    public double getTestpayMoney() {
        return testpayMoney;
    }

    public void setTestpayMoney(double testpayMoney) {
        this.testpayMoney = testpayMoney;
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
