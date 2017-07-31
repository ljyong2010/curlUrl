package yf.liu.common.tool;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlserverMetaBuilder extends MetaBuilder {
    private boolean flag = true;
    public String tablePrefix;
    public void setTablePrefix(String tablePrefix){
        this.tablePrefix = tablePrefix;
    }

    public SqlserverMetaBuilder(DataSource dataSource) {
        super(dataSource);
    }

    public SqlserverMetaBuilder(DataSource dataSource,boolean flag) {
        super(dataSource);
        this.flag = flag;
    }
    protected void buildTableNames(List<TableMeta> ret) throws SQLException {
        ResultSet rs = getTablesResultSet();
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");

            if (excludedTables.contains(tableName)) {
                System.out.println("Skip excluded table :" + tableName);
                continue;
            }
            if (isSkipTable(tableName)){
                System.out.println("Skip table:"+tableName);
                continue;
            }
            if (!tableName.contains(tablePrefix)){
                System.out.println("Skip table :"+tableName);
                continue;
            }

            TableMeta tableMeta = new TableMeta();
            tableMeta.name = tableName;
            tableMeta.remarks = rs.getString("REMARKS");

            boolean tempFlag = false; // 移除表名前缀仅用于生成 modelName、baseModelName。tableMeta.name 表名自身不受影响
            if (removedTableNamePrefixes != null) {
                for (String prefix : removedTableNamePrefixes) {
                    if (tableName.startsWith(prefix)) {
                        tableName = tableName.replaceFirst(prefix, "");
                        tempFlag = true;
                        break;
                    }
                }
            }
            if(flag || tempFlag){
                tableMeta.modelName = StrKit.firstCharToUpperCase(StrKit.toCamelCase(tableName));
                tableMeta.baseModelName = "Base" + tableMeta.modelName;
                ret.add(tableMeta);
            }
        }
        rs.close();
    }

    protected ResultSet getTablesResultSet() throws SQLException {
        dialect = new SqlServerDialect();
        return dbMeta.getTables(conn.getCatalog(), "dbo", null, new String[]{"TABLE", "VIEW"});
    }
}
