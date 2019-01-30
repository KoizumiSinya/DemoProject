package jp.sinya.db.demo1.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.sinya.db.demo1.db.annotation.DbField;
import jp.sinya.db.demo1.db.annotation.DbTable;

/**
 * @author Koizumi Sinya
 * @date 2018/04/02. 23:59
 * @edithor
 * @date
 */
public abstract class BaseDao<T> implements IBaseDao<T> {

    /**
     * 持有数据库操作类的引用
     */
    private SQLiteDatabase database;

    /**
     * 是否已经初始化，用于保证只执行一次
     */
    private boolean isInit = false;

    /**
     * 表对应的实体类
     */
    private Class<T> entityClass;

    /**
     * 维护表名与成员变量的映射关系
     * key -> 表名
     * value -> 字段名
     */
    private HashMap<String, Field> cacheMap;

    private String tableName;

    protected synchronized boolean init(Class<T> entity, SQLiteDatabase database) {
        if (!isInit) {
            this.entityClass = entity;
            this.database = database;

            if (this.entityClass.getAnnotation(DbTable.class) == null) {
                tableName = this.entityClass.getClass().getSimpleName();
            } else {
                tableName = this.entityClass.getAnnotation(DbTable.class).value();
            }

            if (!this.database.isOpen()) {
                return false;
            }

            if (!TextUtils.isEmpty(createTable())) {
                database.execSQL(createTable());
            }
            cacheMap = new HashMap<>();
            initCacheMap();

            isInit = true;
        }
        return isInit;
    }

    private void initCacheMap() {
        String sql = "select * from " + this.tableName + " limit 1, 0";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, null);
            String[] columnNames = cursor.getColumnNames();
            Field[] columnFields = entityClass.getFields();

            for (Field field : columnFields) {
                field.setAccessible(true);
            }

            //遍历数据库表中的列名，判断是否与实体bean中的某个字段名相同
            for (String columnName : columnNames) {
                Field columnField = null;

                for (Field field : columnFields) {
                    String fieldName = null;

                    if (field.getAnnotation(DbField.class) != null) {
                        fieldName = field.getAnnotation(DbField.class).value();
                    } else {
                        fieldName = field.getName();
                    }

                    //改数据库表的列字段，与实体bean的参数名相同
                    if (columnName.equals(fieldName)) {
                        columnField = field;
                        break;
                    }
                }

                if (columnField != null) {
                    cacheMap.put(columnName, columnField);
                }
            }
        } catch (Exception e) {


        } finally {
            cursor.close();
        }
    }

    private Map<String, String> getValue(T entity) {
        HashMap<String, String> result = new HashMap<>();
        Iterator<Field> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()) {
            Field columnToFiled = iterator.next();
            String cacheKey = null;
            String cacheValue = null;
            if (columnToFiled.getAnnotation(DbField.class) != null) {
                cacheKey = columnToFiled.getAnnotation(DbField.class).value();
            } else {
                cacheKey = columnToFiled.getName();
            }

            try {
                if (null == columnToFiled.get(entity)) {
                    continue;
                }

                //根据这个字段field获取对应的bean中这个参数的value
                cacheValue = columnToFiled.get(entity).toString();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
            result.put(cacheKey, cacheValue);
        }

        return result;
    }

    private ContentValues getContentValue(Map<String, String> paramMap) {
        ContentValues contentValues = new ContentValues();
        Iterator<String> iterator = paramMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = paramMap.get(key);

            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    private List<T> getResult(Cursor cursor, T where) {
        List<T> list = new ArrayList<>();
        T item;
        while (cursor.moveToNext()) {
            try {
                item = (T) where.getClass().newInstance();

                Iterator<Map.Entry<String, Field>> iterator = cacheMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Field> entry = iterator.next();
                    String columnName = entry.getKey();
                    Field field = entry.getValue();

                    Integer columnIndex = cursor.getColumnIndex(columnName);
                    Class type = field.getType();

                    if (columnIndex != -1) {
                        if (type == String.class) {
                            field.set(item, cursor.getString(columnIndex));
                        } else if (type == Double.class) {
                            field.set(item, cursor.getDouble(columnIndex));
                        } else if (type == Long.class) {
                            field.set(item, cursor.getLong(columnIndex));
                        } else if (type == Integer.class) {
                            field.set(item, cursor.getInt(columnIndex));
                        } else if (type == byte[].class) {
                            field.set(item, cursor.getBlob(columnIndex));
                        } else {
                            continue;
                        }
                    }
                }
                list.add(item);
            } catch (Exception e) {
                e.printStackTrace();

            } finally {

            }
        }
        return list;
    }

    @Override
    public Long insert(T entity) {
        Map<String, String> map = getValue(entity);
        ContentValues values = getContentValue(map);
        Long result = database.insert(tableName, null, values);
        return result;
    }

    @Override
    public int update(T entity, T where) {
        int result;
        Map values = getValue(entity);
        Map whereClause = getValue(where);
        Condition condition = new Condition(whereClause);
        ContentValues contentValues = getContentValue(values);
        result = database.update(tableName, contentValues, condition.getWhereClause(), condition.getWhereArgs());
        return result;
    }

    @Override
    public List<T> query(T where) {
        return query(where, null, null, null);
    }


    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        Map map = getValue(where);
        String limitStr = null;
        if (startIndex != null && limit != null) {
            limitStr = startIndex + ", " + limit;
        }
        Condition condition = new Condition(map);
        Cursor cursor = database.query(tableName, null, condition.getWhereClause(), condition.getWhereArgs(), null, null, orderBy, limitStr);
        List<T> resultList = getResult(cursor, where);
        return resultList;
    }

    @Override
    public int delete(T where) {
        Map map = getValue(where);
        Condition condition = new Condition(map);
        return database.delete(tableName, condition.getWhereClause(), condition.getWhereArgs());
    }

    protected abstract String createTable();

    static class Condition {
        private String whereClause;
        private String[] whereArgs;

        public String getWhereClause() {
            return whereClause;
        }

        public void setWhereClause(String whereClause) {
            this.whereClause = whereClause;
        }

        public String[] getWhereArgs() {
            return whereArgs;
        }

        public void setWhereArgs(String[] whereArgs) {
            this.whereArgs = whereArgs;
        }

        public Condition(Map<String, String> whereClause) {
            List list = new ArrayList();

            StringBuilder builder = new StringBuilder();
            builder.append(" 1=1 ");

            Iterator<String> iterator = whereClause.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = whereClause.get(key);

                if (value != null) {
                    builder.append(" and " + key + " =?");
                    list.add(value);
                }
            }
            this.whereClause = builder.toString();
            this.whereArgs = (String[]) list.toArray(new String[list.size()]);
        }
    }
}
