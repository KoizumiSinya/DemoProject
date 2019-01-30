package jp.sinya.tallybook;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.sinya.tallybook.db.TallyDBHelper;
import jp.sinya.tallybook.model.Tally;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("jp.sinya.tallybook", appContext.getPackageName());
    }

    @Test
    public void testDB() {
        TallyDBHelper helper = new TallyDBHelper(InstrumentationRegistry.getTargetContext());
        for (int i = 0; i < 100; i++) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String time = formatter.format(new Date());
            Tally tally = new Tally("Title" + i, time, i * 1.5f);
            helper.insert(tally);
        }
    }

    @Test
    public void queryTally() {
        TallyDBHelper helper = new TallyDBHelper(InstrumentationRegistry.getTargetContext());
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
        Log.i("Sinya", list.toString());

    }
}
