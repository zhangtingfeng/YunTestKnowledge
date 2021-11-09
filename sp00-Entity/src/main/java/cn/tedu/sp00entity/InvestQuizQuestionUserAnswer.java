package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * @author oliver zhang
 * @date 2021-03-31
 */
public class InvestQuizQuestionUserAnswer extends BaseEntity implements Serializable {

    private String id;
    private String quziQuestionUserId;
    /**
     * 用户答案
     */
    private String answer;
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


    public String getQuziQuestionUserId() {
        return quziQuestionUserId;
    }

    public void setQuziQuestionUserId(String quziQuestionUserId) {
        this.quziQuestionUserId = quziQuestionUserId;
    }


    /**
     * 用户答案
     */
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


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
