package cn.eggsoft.sp10.tuoke;

import lombok.Data;

import java.util.List;

/**
 * @author shawn
 */
@Data
public class PageHelper<T> {
    private long currentPage;
    private long total;
    private long pageSize;
    private List<T> list;

    public PageHelper(long pageNum, long total, long pageSize, List<T> list) {
        this.currentPage = pageNum;
        this.total = total;
        this.pageSize = pageSize;
        this.list = list;
    }

    public PageHelper(long pageNum, long pageSize, List<T> list) {
        this.currentPage = pageNum;
        this.pageSize = pageSize;
        this.list = list;
    }
}