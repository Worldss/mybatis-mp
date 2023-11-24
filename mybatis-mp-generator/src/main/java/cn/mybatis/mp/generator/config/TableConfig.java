package cn.mybatis.mp.generator.config;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TableConfig {

    /**
     * 需要生成的表名
     */
    private final List<String> tablePrefixs = new ArrayList<>();

    /**
     * 需要生成的表名
     */
    private final List<String> includeTables = new ArrayList<>();

    /**
     * 需要排除的表名
     */
    private final List<String> excludeTables = new ArrayList<>();


    /**
     * 表的前缀
     *
     * @param prefixs
     * @return
     */
    public TableConfig tablePrefixs(String... prefixs) {
        this.tablePrefixs.addAll(Arrays.asList(prefixs).stream()
                .map(item -> {
                    if (item.toLowerCase().equals(item)) {
                        return item.toLowerCase();
                    }
                    return item;
                })
                .sorted(Comparator.comparing(i -> i.length()))
                .sorted(Comparator.reverseOrder()
                ).collect(Collectors.toList()));
        return this;
    }

    /**
     * 设置需要生成的表
     *
     * @param tables
     * @return
     */
    public TableConfig includeTable(String... tables) {
        this.includeTables.addAll(Arrays.asList(tables));
        return this;
    }

    /**
     * 设置需要不生成的表
     *
     * @param tables
     * @return
     */
    public TableConfig excludeTable(String... tables) {
        this.excludeTables.addAll(Arrays.asList(tables));
        return this;
    }
}
