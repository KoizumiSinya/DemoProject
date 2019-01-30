package jp.sinya.db.demo1.db.dao;

import java.util.List;

/**
 * @author Koizumi Sinya
 * @date 2018/04/02. 23:52
 * @edithor
 * @date
 */
public interface IBaseDao<T> {
    Long insert(T entity);

    int update(T entity, T where);

    int delete(T where);

    List<T> query(T where);

    List<T> query(String sql);

    List<T> query(T where, String orderBy, Integer startIndex, Integer limit);
}
