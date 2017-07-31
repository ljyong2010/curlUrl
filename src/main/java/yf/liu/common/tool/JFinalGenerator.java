package yf.liu.common.tool;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import yf.liu.common.Config;

import javax.sql.DataSource;

public class JFinalGenerator {
    public static DataSource getDataSource(){
        PropKit.use("jdbc.properties");
        C3p0Plugin c3p0Plugin= Config.createC3p0Plugin();
        c3p0Plugin.start();
        return c3p0Plugin.getDataSource();
    }
    public static void main(String[] args){
        // base model 所使用的包名
        String baseModelPackageName = "yf.liu.model.base";
        // base model 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/yf/liu/model/base";

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "yf.liu.model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        // 创建生成器
        Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        // 添加不需要生成的表名
//		gernerator.addExcludedTable("x_user");
        // 设置是否在 Model 中生成 dao 对象
        SqlserverMetaBuilder sqlbuilder = new SqlserverMetaBuilder(getDataSource(),true);
        sqlbuilder.setTablePrefix("tf_f_productattach");
        gernerator.setMetaBuilder(sqlbuilder);
        gernerator.setDialect(new SqlServerDialect());
        gernerator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        gernerator.setGenerateDataDictionary(true);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
//        gernerator.setRemovedTableNamePrefixes("D_");
        // 生成
        MappingKitBulid mappingKitBulid = new MappingKitBulid(modelPackageName,modelOutputDir);
        gernerator.setMappingKitGenerator(mappingKitBulid);
        gernerator.generate();
    }
}
