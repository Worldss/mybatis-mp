package cn.mybatis.mp.generator.database.meta;

import cn.mybatis.mp.core.util.NamingUtil;
import lombok.Data;

import java.util.List;

@Data
public class TableInfo {

    private String schema;

    private String catalog;

    private String name;

    private String remarks;

    private String tableType;

    private ColumnInfo idColumnInfo;

    private List<ColumnInfo> columnInfoList;

    public boolean isView() {
        return "VIEW".equals(tableType);
    }



}
