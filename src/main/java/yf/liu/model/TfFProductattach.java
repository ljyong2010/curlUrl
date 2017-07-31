package yf.liu.model;

import com.jfinal.plugin.activerecord.Db;
import yf.liu.model.base.BaseTfFProductattach;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class TfFProductattach extends BaseTfFProductattach<TfFProductattach> {
	public static final TfFProductattach dao = new TfFProductattach().dao();

	public TfFProductattach  gettf(int i){
		TfFProductattach tfFProductattach = TfFProductattach.dao.findFirst("select * from tf_f_productattach where pkid=?",i);
		return tfFProductattach;
	}
	public int getCount(){
		int count =  Db.queryInt("select count(1) from tf_f_productattach where flag = ? and delflag  = ?",0,0);
		return count;
	}

	public List<TfFProductattach> getTfFProductattachList(){
		List<TfFProductattach> list = TfFProductattach.dao.find("select top 100 pkid,file_path,flag,delflag from tf_f_productattach where flag = ? and delflag  = ? order by pkid",0,0);
		return list;
	}
	public int updateTfFProductattach(int pkid){
		int update = Db.update("update tf_f_productattach set flag = 1,delflag = 1 where pkid = ?",pkid);
		return update;
	}
	public void batUpdate(List pkid){
		Db.batch("update tf_f_productattach set flag = 1,delflag = 1 where pkid = ?",new Object[][] {pkid.toArray()},100);
	}

}
