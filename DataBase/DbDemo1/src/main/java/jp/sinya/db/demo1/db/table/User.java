package jp.sinya.db.demo1.db.table;

import jp.sinya.db.demo1.db.DbConstants;
import jp.sinya.db.demo1.db.annotation.DbField;
import jp.sinya.db.demo1.db.annotation.DbTable;

/**
 * @author Koizumi Sinya
 * @date 2018/04/02. 23:57
 * @edithor
 * @date
 */
@DbTable(DbConstants.TABLE_NAME_USER)
public class User {

    @DbField("username")
    public String username;

    @DbField("password")
    public String password;

    @DbField("user_id")
    public Integer userId;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int userId, String username) {
        this.username = username;
        this.userId = userId;
    }

    public User(int userId, String username, String password) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", userId=" + userId + '}';
    }
}
