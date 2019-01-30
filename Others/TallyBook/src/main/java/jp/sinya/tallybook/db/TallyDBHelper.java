package jp.sinya.tallybook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jp.sinya.tallybook.model.Tally;

/**
 * @author Koizumi Sinya
 * @date 2018/01/03. 10:26
 * @edithor
 * @date
 */
public class TallyDBHelper extends SQLiteOpenHelper {

    private static final String databaseName = "tallybook_db";
    private static final String tabName = "t_tally";

    public TallyDBHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    public TallyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + tabName + "(" //
                + "id integer primary key," //
                + "title varchar," //
                + "date varchar," //
                + "cost smallmoney)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Tally tally) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", tally.getTitle());
        values.put("date", tally.getDate());
        values.put("cost", tally.getCost());
        database.insert(tabName, null, values);
    }

    public Cursor getAllTally() {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(tabName, null, null, null, null, null, "date ASC");
        return cursor;
    }

    public void deleteAllTally() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(tabName, null, null);
    }

}
