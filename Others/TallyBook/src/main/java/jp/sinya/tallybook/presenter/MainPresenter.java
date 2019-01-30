package jp.sinya.tallybook.presenter;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jp.sinya.tallybook.db.TallyDBHelper;
import jp.sinya.tallybook.model.Tally;
import jp.sinya.tallybook.view.MainView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/02. 23:21
 * @edithor
 * @date
 */
public class MainPresenter {

    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void queryTally() {
        TallyDBHelper helper = new TallyDBHelper((Context) view);
        Cursor cursor = helper.getAllTally();
        List<Tally> list = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Tally tally = new Tally();
                tally.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                tally.setDate(cursor.getString(cursor.getColumnIndex("date")));
                tally.setCost(cursor.getFloat(cursor.getColumnIndex("cost")));
                list.add(tally);
            }
            cursor.close();
        }
        view.update(list);
    }

    public void saveTally(Tally tally) {
        TallyDBHelper helper = new TallyDBHelper((Context) view);
        helper.insert(tally);
        queryTally();
    }



}
