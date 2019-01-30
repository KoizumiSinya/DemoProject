package jp.sinya.datespanselectrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

import jp.sinya.datespanselectrecyclerview.widget.DatePickerController;
import jp.sinya.datespanselectrecyclerview.widget.DayPickerView;
import jp.sinya.datespanselectrecyclerview.widget.SimpleMonthAdapter.CalendarDay;
import jp.sinya.datespanselectrecyclerview.widget.SimpleMonthAdapter.SelectedDays;

public class MainActivity extends AppCompatActivity implements DatePickerController {

    private DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayPickerView = (DayPickerView) findViewById(R.id.view_span);
        dayPickerView.setController(this);

        //设置当前的view滑动到哪个位置
        int counts = dayPickerView.getAdapter().getItemCount();
        int currPosition = Calendar.getInstance().get(Calendar.MONTH) + 1;
        if (currPosition < counts) {
            dayPickerView.scrollToPosition(currPosition - 1);
        } else if (currPosition == counts) {
            dayPickerView.scrollToPosition(counts - 1);
        }

        //规定一开始显示的时间段
        dayPickerView.setSelectSpan(2016, 3, 10, 2016, 4, 1);

    }

    @Override
    public int getMaxYear() {
        return Calendar.getInstance().get(Calendar.YEAR) + 2;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {

    }

    @Override
    public void onDateRangeSelected(SelectedDays<CalendarDay> selectedDays) {

        int[] date = getSpanDay(selectedDays);
        Log.i("Sinya", "修正后的时间：start:" + date[0] + "-" + date[1] + "-" + date[2]//
                + "; end:" + date[3] + "-" + date[4] + "-" + date[5]);
    }

    private int[] getSpanDay(SelectedDays<CalendarDay> selectedDays) {
        int[] days = new int[6];
        CalendarDay firstDate = selectedDays.getFirst();
        CalendarDay endDate = selectedDays.getLast();

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(firstDate.year, firstDate.month, firstDate.day);
        calendar2.set(endDate.year, endDate.month, endDate.day);
        int result = calendar1.compareTo(calendar2);

        if (result > 0) {
            days[0] = endDate.year;
            days[1] = endDate.month + 1;
            days[2] = endDate.day;
            days[3] = firstDate.year;
            days[4] = firstDate.month + 1;
            days[5] = firstDate.day;
        } else {
            days[0] = firstDate.year;
            days[1] = firstDate.month + 1;
            days[2] = firstDate.day;
            days[3] = endDate.year;
            days[4] = endDate.month + 1;
            days[5] = endDate.day;
        }

        return days;
    }
}
