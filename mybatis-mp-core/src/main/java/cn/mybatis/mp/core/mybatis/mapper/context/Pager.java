package cn.mybatis.mp.core.mybatis.mapper.context;

import java.util.List;

public class Pager<T> {

    private transient boolean optimize = false;

    private transient boolean executeCount = true;

    private List<T> results;

    private Integer total;

    private int number = 1;

    private int size = 20;

    public int getOffset() {
        return (number - 1) * size;
    }

    public boolean isExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(boolean executeCount) {
        this.executeCount = executeCount;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isOptimize() {
        return optimize;
    }

    public void setOptimize(boolean optimize) {
        this.optimize = optimize;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "optimize=" + optimize +
                ", executeCount=" + executeCount +
                ", results=" + results +
                ", total=" + total +
                ", number=" + number +
                ", size=" + size +
                '}';
    }
}
