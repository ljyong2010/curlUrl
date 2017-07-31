package yf.liu.service;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import yf.liu.model.TfFProductattach;

import java.util.List;

public class ProductattachService {
    static {
        C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:sqlserver://192.168.200.196:1433;databaseName=b2b_v360","sa","hyjk2012@","com.microsoft.sqlserver.jdbc.SQLServerDriver");
        c3p0Plugin.start();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.setDialect(new SqlServerDialect());
        arp.addMapping("tf_f_productattach", "PKID", TfFProductattach.class);
        arp.start();
    }
    /**
     *
     */
    public void getTfFProductattach(){
        TfFProductattach tfFProductattach = TfFProductattach.dao.gettf(3);
        System.out.println(tfFProductattach.getFilePath());
    }
    /**
     * 取得没有下载图片总数
     * @return
     */
    public int getProductattachCount(){
        int count = TfFProductattach.dao.getCount();
        return count;
    }
    /**
     * 每次处理的列表数目
     * @return
     */
    public List<TfFProductattach> getTfList(){
        return TfFProductattach.dao.getTfFProductattachList();
    }

    /**
     * 更新标志
     * @param pkid
     * @return
     */
    public int updateTf(int pkid){
        return TfFProductattach.dao.updateTfFProductattach(pkid);
    }
    public void batchUpdate(List<Integer> pkid){
        TfFProductattach.dao.batUpdate(pkid);
    }
}
