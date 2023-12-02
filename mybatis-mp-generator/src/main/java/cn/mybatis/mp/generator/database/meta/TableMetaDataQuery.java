package cn.mybatis.mp.generator.database.meta;

import cn.mybatis.mp.generator.config.GeneratorConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class TableMetaDataQuery {

    protected final DatabaseMetaData metaData;

    protected final GeneratorConfig generatorConfig;

    protected final String connDatabaseName;

    public TableMetaDataQuery(GeneratorConfig generatorConfig, Connection connection) throws SQLException {
        this.metaData = connection.getMetaData();
        this.generatorConfig = generatorConfig;
        this.connDatabaseName = connection.getCatalog();
    }

    public List<TableInfo> getTableInfoList(boolean includeTable, boolean includeView) {
        Set<String> types = new HashSet<>();
        if (includeTable) {
            types.add("TABLE");
        }
        if (includeView) {
            types.add("VIEW");
        }

        List<TableInfo> tables = new ArrayList<>();

        String databaseName = generatorConfig.getDataBaseConfig().getDatabaseName();
        if (databaseName == null) {
            databaseName = this.connDatabaseName;
        }

        try (ResultSet resultSet = metaData.getTables(databaseName, generatorConfig.getDataBaseConfig().getSchema(), null, types.toArray(new String[2]))) {
            TableInfo tableInfo;
            while (resultSet.next()) {
                String TABLE_NAME = resultSet.getString("TABLE_NAME");
                String tableName = TABLE_NAME;
                if (tableName.toUpperCase().equals(tableName)) {
                    tableName = tableName.toLowerCase();
                }
                tableInfo = new TableInfo();
                tableInfo.setName(tableName);
                tableInfo.setRemarks(resultSet.getString("REMARKS"));
                tableInfo.setTableType(resultSet.getString("TABLE_TYPE"));
                tableInfo.setSchema(resultSet.getString("TABLE_SCHEM"));
                tableInfo.setCatalog(resultSet.getString("TABLE_CAT"));

                tableInfo.setColumnInfoList(getColumnInfo(tableInfo, TABLE_NAME));
                List<ColumnInfo> idColumnInfoList = tableInfo.getColumnInfoList().stream().filter(item -> item.isPrimaryKey()).collect(Collectors.toList());
                if (!idColumnInfoList.isEmpty()) {
                    tableInfo.setIdColumnInfo(idColumnInfoList.get(0));
                }
                tables.add(tableInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取数据库表信息出现错误", e);
        }
        return tables;
    }

    private List<ColumnInfo> getColumnInfo(TableInfo tableInfo, String tableName) {
        Set<String> primaryKeys = new HashSet<>();
        if (!tableInfo.isView()) {
            try (ResultSet primaryKeysResultSet = metaData.getPrimaryKeys(tableInfo.getCatalog(), tableInfo.getSchema(), tableName)) {
                while (primaryKeysResultSet.next()) {
                    String columnName = primaryKeysResultSet.getString("COLUMN_NAME");
                    if (columnName.toUpperCase().equals(columnName)) {
                        columnName = columnName.toLowerCase();
                    }
                    primaryKeys.add(columnName);
                }
            } catch (SQLException e) {
                throw new RuntimeException("读取表主键信息:" + tableInfo.getName() + "错误:", e);
            }
        }

        if (primaryKeys.size() > 1) {
            log.warn("当前表:{}，存在多主键情况！", tableInfo.getName());
        }

        List<ColumnInfo> columnsInfoList = new ArrayList<>();
        try (ResultSet resultSet = metaData.getColumns(tableInfo.getCatalog(), tableInfo.getSchema(), tableName, "%")) {
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                if (columnName.toUpperCase().equals(columnName)) {
                    columnName = columnName.toLowerCase();
                }

                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setTableInfo(tableInfo);
                columnInfo.setName(columnName);
                columnInfo.setPrimaryKey(primaryKeys.contains(columnInfo.getName()));

                columnInfo.setTypeName(resultSet.getString("TYPE_NAME"));
                columnInfo.setJdbcType(JdbcType.forCode(resultSet.getInt("DATA_TYPE")));
                columnInfo.setLength(resultSet.getInt("COLUMN_SIZE"));
                columnInfo.setScale(resultSet.getInt("DECIMAL_DIGITS"));
                columnInfo.setRemarks(resultSet.getString("REMARKS"));
                columnInfo.setDefaultValue(resultSet.getString("COLUMN_DEF"));
                columnInfo.setNullable(resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable);

                columnInfo.setVersion(columnName.equals(generatorConfig.getColumnConfig().getVersionColumn()));
                columnInfo.setTenantId(columnName.equals(generatorConfig.getColumnConfig().getTenantIdColumn()));
                columnInfo.setLogicDelete(columnName.equals(generatorConfig.getColumnConfig().getLogicDeleteColumn()));
                try {
                    columnInfo.setAutoIncrement("YES".equals(resultSet.getString("IS_AUTOINCREMENT")));
                } catch (SQLException e) {
                    log.error("获取自增信息异常：", e);
                }
                columnsInfoList.add(columnInfo);
            }
            return columnsInfoList;
        } catch (SQLException e) {
            throw new RuntimeException("读取表字段信息:" + tableInfo.getName() + "错误:", e);
        }
    }
}
