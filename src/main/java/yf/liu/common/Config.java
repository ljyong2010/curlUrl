package yf.liu.common;

import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.template.Engine;
import yf.liu.model._MappingKit;


public class Config extends JFinalConfig{

    @Override
    public void configConstant(Constants me) {
        PropKit.use("jdbc.properties");
        me.setDevMode(PropKit.getBoolean("devMode", false));
    }

    @Override
    public void configRoute(Routes me) {

    }

    @Override
    public void configEngine(Engine me) {

    }

    public static C3p0Plugin createC3p0Plugin() {
        return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim(), PropKit.get("jdbcDriver").trim());
    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = createC3p0Plugin();
        me.add(c3p0Plugin);

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        activeRecordPlugin.setDialect(new SqlServerDialect());
        me.add(activeRecordPlugin);
        activeRecordPlugin.setShowSql(true);
        _MappingKit.mapping(activeRecordPlugin);
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {

    }
}
