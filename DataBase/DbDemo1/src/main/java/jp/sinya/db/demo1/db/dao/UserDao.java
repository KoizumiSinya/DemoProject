package jp.sinya.db.demo1.db.dao;

import java.util.List;

import jp.sinya.db.demo1.db.DbConstants;
import jp.sinya.db.demo1.db.table.User;

/**
 * @author Sinya
 * @date 2018/06/10. 17:29
 * @edithor
 * @date
 */
public class UserDao extends BaseDao<User> {
    @Override
    protected String createTable() {
        return "create table if not exists " + DbConstants.TABLE_NAME_USER + "(" //
                + "user_id int," //
                + "username varchar(20)," //
                + "password varchar(19)" //
                + ")";
    }

    @Override
    public List<User> query(String sql) {
        return null;
    }
}
