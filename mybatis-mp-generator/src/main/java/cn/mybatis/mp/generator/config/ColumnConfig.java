package cn.mybatis.mp.generator.config;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 列配置
 */
@Getter
public class ColumnConfig {

    /**
     * 排除列
     */
    private List<String> excludeColumns = new ArrayList<>();

    /**
     * 禁止修改的列
     */
    private List<String> disableUpdateColumns = new ArrayList<>();

    /**
     * 禁止查询列
     */
    private List<String> disableSelectColumns = new ArrayList<>();

    private ColumnConfig excludeColumns(String... columns) {
        this.excludeColumns.addAll(Arrays.asList(columns));
        return this;
    }

    public ColumnConfig disableUpdateColumns(String... columns) {
        this.disableUpdateColumns.addAll(Arrays.asList(columns));
        return this;
    }

    public ColumnConfig disableSelectColumns(String... columns) {
        this.disableSelectColumns.addAll(Arrays.asList(columns));
        return this;
    }
}
