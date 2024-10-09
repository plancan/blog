package com.domain;

/**
 * @author 今昔
 * @description 分页展示类
 * @date 2022/11/9 21:14
 */
public class MyPage<T> {
    private T records;
    private int currentPage;
    private int pages;
    private int size;
    private int count;
    private int total;

    public MyPage() {
    }

    public T getRecords() {
        return records;
    }

    public void setRecords(T data) {
        this.records = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MyPage{" +
                "data=" + records +
                ", currentPage=" + currentPage +
                ", pages=" + pages +
                ", size=" + size +
                ", count=" + count +
                ", total=" + total +
                '}';
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
