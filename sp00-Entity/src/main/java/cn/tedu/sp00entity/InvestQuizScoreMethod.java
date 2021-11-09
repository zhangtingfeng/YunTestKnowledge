package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * @author oliver zhang
 * @date 2021-03-31
 */
public class InvestQuizScoreMethod extends BaseEntity implements Serializable {

    private String id;
    /**
     * 那个药方的统计办法
     */
    private long quizId;
    private String title;
    private String investMemo;
    /**
     * 计分的起始值间隔
     */
    private double lowScore;
    /**
     * 计分的中止值区间间隔
     */
    private double hightScore;
    /**
     * 排序
     */
    private long sort;
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
     * 那个药方的统计办法
     */
    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
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
     * 计分的起始值间隔
     */
    public double getLowScore() {
        return lowScore;
    }

    public void setLowScore(double lowScore) {
        this.lowScore = lowScore;
    }


    /**
     * 计分的中止值区间间隔
     */
    public double getHightScore() {
        return hightScore;
    }

    public void setHightScore(double hightScore) {
        this.hightScore = hightScore;
    }


    /**
     * 排序
     */
    public long getSort() {
        return sort;
    }

    public void setSort(long sort) {
        this.sort = sort;
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
