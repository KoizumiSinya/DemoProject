package jp.sinya.db.demo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

import jp.sinya.db.demo1.db.dao.BaseDaoFactory;
import jp.sinya.db.demo1.db.dao.IBaseDao;
import jp.sinya.db.demo1.db.dao.UserDao;
import jp.sinya.db.demo1.db.table.User;

public class MainActivity extends AppCompatActivity {

    private IBaseDao<User> userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = BaseDaoFactory.getInstance().getDataHelper(UserDao.class, User.class);
    }

    public void saveUser(View view) {
        for (int i = 1; i <= 20; i++) {
            User user = new User(i, "sinya", ("123456" + i));
            userDao.insert(user);
        }
    }

    public void queryUser(View view) {
        User where = new User();
        where.setUserId(1);
        List<User> list = userDao.query(where);
        Log.i("Sinya", "查询到 " + (list != null ? list.size() : 0) + " 条数据");
        Log.i("Sinya", (list != null ? list.toString() : null));
    }

    public void queryUsers(View view) {
        User where = new User();
        where.setUsername("sinya");
        List<User> list = userDao.query(where);
        Log.i("Sinya", "查询到 " + (list != null ? list.size() : 0) + " 条数据");
        Log.i("Sinya", (list != null ? list.toString() : null));
    }

    public void updateUser(View view) {
        User where = new User(1, "sinya");
        User user = new User();
        user.setUsername("Tomcat");
        userDao.update(user, where);
    }

    public void deleteUser(View view) {
    }
}
