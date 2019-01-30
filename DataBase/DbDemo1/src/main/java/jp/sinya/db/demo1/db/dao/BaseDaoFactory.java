package jp.sinya.db.demo1.db.dao;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import jp.sinya.db.demo1.MyApplication;

import static jp.sinya.db.demo1.db.DbConstants.DB_NAME;

/**
 * @author Sinya
 * @date 2018/06/10. 18:20
 * @edithor
 * @date
 */
public class BaseDaoFactory {
    private String databasePath;
    private SQLiteDatabase database;

    private static BaseDaoFactory instance = new BaseDaoFactory();

    public BaseDaoFactory() {
        ///storage/emulated/0/Sinya/jp.sinya.db.demo1/sinya_test_demo1.db
        String localPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Sinya/" + MyApplication.context.getPackageName() + "/";
        File file = new File(localPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        databasePath = localPath + DB_NAME;
        Log.i("Sinya", "path: " + localPath + databasePath);
        openDatabase();
    }

    private void openDatabase() {
        this.database = SQLiteDatabase.openOrCreateDatabase(databasePath, null);
    }

    public static BaseDaoFactory getInstance() {
        return instance;
    }

    //M 数据库实体bean;
    public synchronized <T extends BaseDao<M>, M> T getDataHelper(Class<T> daoClass, Class<M> entityClass) {
        BaseDao baseDao = null;
        try {
            baseDao = daoClass.newInstance();
            baseDao.init(entityClass, database);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return (T) baseDao;
    }

}