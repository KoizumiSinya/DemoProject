package jp.sinya.selectwheelview.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.sinya.selectwheelview.R;


/**
 * 日期时间滚轮
 *
 * @author KoizumiSinya
 * @date 2016/2/26.
 */
public class SelectWheelDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;
    private WheelView wvHour;
    private WheelView wvMin;
    private WheelView wvString, wvString2, wvString3;

    private ArrayList<String> yearList = new ArrayList<>();
    private ArrayList<String> monthList = new ArrayList<>();
    private ArrayList<String> dayList = new ArrayList<>();
    private ArrayList<String> hourList = new ArrayList<>();
    private ArrayList<String> minList = new ArrayList<>();
    private ArrayList<String> StringList;
    private ArrayList<String> StringList2;
    private ArrayList<String> StringList3;

    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDaydapter;
    private CalendarTextAdapter mHourdapter;
    private CalendarTextAdapter mMindapter;
    private CalendarTextAdapter mStringdapter, mStringdapter2, mStringdapter3;

    private String title;
    private int selectedYear, selectedMonth, selectedDate, selectedHour, selectedMin;
    private String selectedStr, selectedStr2, selectedStr3;

    private int maxTextSize = 24;
    private int minTextSize = 14;

    private TextView tvTitle, tvCancel, tvOK;

    private onDateWheelCommitListener listener;
    private onStringWheelCommitListener stringWheelCommitListener;

    public SelectWheelDialog(Context context) {
        super(context, R.style.WheelDialog);
        mContext = context;
    }

    public SelectWheelDialog(Context context, ArrayList<String> StringList) {
        super(context, R.style.WheelDialog);
        mContext = context;
        this.StringList = StringList;
        selectedYear = -1;
        selectedMonth = -1;
        selectedDate = -1;
        selectedHour = -1;
        selectedMin = -1;
    }

    public SelectWheelDialog(Context context, ArrayList<String> StringList, String key) {
        super(context, R.style.WheelDialog);
        mContext = context;
        this.StringList = StringList;
        selectedYear = -1;
        selectedMonth = -1;
        selectedDate = -1;
        selectedHour = -1;
        selectedMin = -1;
        selectedStr = key;
    }

    public SelectWheelDialog(Context context, int year, ArrayList<String> StringList, String key) {
        super(context, R.style.WheelDialog);
        mContext = context;
        selectedYear = year;
        selectedMonth = -1;
        selectedDate = -1;
        selectedHour = -1;
        selectedMin = -1;
        this.StringList = StringList;
        selectedStr = key;
    }

    public SelectWheelDialog(Context context, ArrayList<String> StringList, String key, ArrayList<String> StringList2, String key2) {
        super(context, R.style.WheelDialog);
        mContext = context;
        this.StringList = StringList;
        this.StringList2 = StringList2;
        selectedYear = -1;
        selectedMonth = -1;
        selectedDate = -1;
        selectedHour = -1;
        selectedMin = -1;
        selectedStr = key;
        selectedStr2 = key2;
    }

    public SelectWheelDialog(Context context, ArrayList<String> StringList, String key, ArrayList<String> StringList2, String key2, ArrayList<String> StringList3, String key3) {
        super(context, R.style.WheelDialog);
        mContext = context;
        this.StringList = StringList;
        this.StringList2 = StringList2;
        this.StringList3 = StringList3;
        selectedYear = -1;
        selectedMonth = -1;
        selectedDate = -1;
        selectedHour = -1;
        selectedMin = -1;
        selectedStr = key;
        selectedStr2 = key2;
        selectedStr3 = key3;
    }


    public SelectWheelDialog(Context context, int year) {
        super(context, R.style.WheelDialog);
        mContext = context;
        selectedYear = year;
        selectedMonth = -1;
        selectedDate = -1;
        selectedHour = -1;
        selectedMin = -1;
    }

    public SelectWheelDialog(Context context, int year, int month) {
        super(context, R.style.WheelDialog);
        mContext = context;
        selectedYear = year;
        selectedMonth = month;
        selectedDate = -1;
        selectedHour = -1;
        selectedMin = -1;
    }

    public SelectWheelDialog(Context context, int year, int month, int date) {
        super(context, R.style.WheelDialog);
        mContext = context;
        selectedYear = year;
        selectedMonth = month;
        selectedDate = date;
        selectedHour = -1;
        selectedMin = -1;
    }

    public SelectWheelDialog(Context context, int year, int month, int date, int hour, int min) {
        super(context, R.style.WheelDialog);
        mContext = context;
        selectedYear = year;
        selectedMonth = month;
        selectedDate = date;
        selectedHour = hour;
        selectedMin = min;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_select_wheel);

        initDates();
        LinearLayout ll_dialog = (LinearLayout) findViewById(R.id.ll_dialog);
        ll_dialog.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.btn_cancel);
        tvCancel.setOnClickListener(this);
        tvOK = (TextView) findViewById(R.id.btn_ok);
        tvOK.setOnClickListener(this);
        if (selectedYear != -1) {
            initYearWheel();
        }
        if (selectedMonth != -1) {
            initMonthWheel();
        }
        if (selectedDate != -1) {
            initDayWheel();
        }
        if (selectedHour != -1) {
            initHourWheel();
        }
        if (selectedMin != -1) {
            initMinWheel();
        }

        if (StringList != null && StringList.size() > 0) {
            initStringWheel();
        }

        if (StringList2 != null && StringList2.size() > 0) {
            initStringWheel2();
        }

        if (StringList3 != null && StringList3.size() > 0) {
            initStringWheel3();
        }

        setCanceledOnTouchOutside(true);
        //设置对话框在屏幕的底部
        getWindow().setGravity(Gravity.BOTTOM);
        //设置对话框从下往上弹出
        getWindow().setWindowAnimations(R.style.dialg_wheel_style);
    }


    private void initYearWheel() {
        wvYear = (WheelView) findViewById(R.id.wv_year);
        wvYear.setVisibility(View.VISIBLE);
        mYearAdapter = new CalendarTextAdapter(mContext, yearList, getListIndex(yearList, selectedYear + ""), maxTextSize, minTextSize);
        wvYear.setVisibleItems(5);
        wvYear.setViewAdapter(mYearAdapter);
        wvYear.setCurrentItem(getListIndex(yearList, selectedYear + ""));

        wvYear.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectedYear = Integer.valueOf(mYearAdapter.getItemText(wheel.getCurrentItem()).toString());
                setTextviewSize(mYearAdapter.getItemText(wheel.getCurrentItem()).toString(), mYearAdapter);
            }
        });

        wvYear.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectedYear = Integer.valueOf(mYearAdapter.getItemText(wheel.getCurrentItem()).toString());
                setTextviewSize(mYearAdapter.getItemText(wheel.getCurrentItem()).toString(), mYearAdapter);
                Log.i("Sinya", "所选年 " + selectedYear);

            }
        });
    }


    private void initMonthWheel() {
        wvMonth = (WheelView) findViewById(R.id.wv_month);
        wvMonth.setVisibility(View.VISIBLE);
        mMonthAdapter = new CalendarTextAdapter(mContext, monthList, selectedMonth - 1, maxTextSize, minTextSize);
        wvMonth.setVisibleItems(5);
        wvMonth.setViewAdapter(mMonthAdapter);
        wvMonth.setCurrentItem(selectedMonth - 1);

        wvMonth.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectedMonth = Integer.valueOf(mMonthAdapter.getItemText(wheel.getCurrentItem()).toString());
                setTextviewSize(mMonthAdapter.getItemText(wheel.getCurrentItem()).toString(), mMonthAdapter);

                if (selectedDate != -1) {
                    initDayList(selectedYear, selectedMonth);
                    if (selectedDate == 29) {
                        if (hasDays(selectedYear, selectedMonth) == 28) {
                            selectedDate = 28;
                        }
                    } else if (selectedDate == 30) {
                        if (hasDays(selectedYear, selectedMonth) == 28) {
                            selectedDate = 28;
                        } else if (hasDays(selectedYear, selectedMonth) == 29) {
                            selectedDate = 29;
                        }
                    } else if (selectedDate == 31) {
                        if (hasDays(selectedYear, selectedMonth) == 28) {
                            selectedDate = 28;
                        } else if (hasDays(selectedYear, selectedMonth) == 29) {
                            selectedDate = 29;
                        } else if (hasDays(selectedYear, selectedMonth) == 30) {
                            selectedDate = 30;
                        }
                    }

                    mDaydapter = new CalendarTextAdapter(mContext, dayList, selectedDate - 1, maxTextSize, minTextSize);
                    wvDay.setVisibleItems(5);
                    wvDay.setViewAdapter(mDaydapter);
                    wvDay.setCurrentItem(selectedDate - 1);
                }
            }
        });

        wvMonth.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectedMonth = Integer.valueOf(mMonthAdapter.getItemText(wheel.getCurrentItem()).toString());
                setTextviewSize(mMonthAdapter.getItemText(wheel.getCurrentItem()).toString(), mMonthAdapter);

            }
        });

    }

    private void initDayWheel() {
        wvDay = (WheelView) findViewById(R.id.wv_day);
        wvDay.setVisibility(View.VISIBLE);
        mDaydapter = new CalendarTextAdapter(mContext, dayList, selectedDate - 1, maxTextSize, minTextSize);
        wvDay.setVisibleItems(5);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(selectedDate - 1);

        wvDay.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectedDate = Integer.valueOf(mDaydapter.getItemText(wheel.getCurrentItem()).toString());
                setTextviewSize(mDaydapter.getItemText(wheel.getCurrentItem()).toString(), mDaydapter);
            }
        });

        wvDay.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectedDate = Integer.valueOf(mDaydapter.getItemText(wheel.getCurrentItem()).toString());
                setTextviewSize(mDaydapter.getItemText(wheel.getCurrentItem()).toString(), mDaydapter);

            }
        });
    }

    private void initHourWheel() {
        wvHour = (WheelView) findViewById(R.id.wv_hour);
        wvHour.setVisibility(View.VISIBLE);
        mHourdapter = new CalendarTextAdapter(mContext, hourList, selectedHour, maxTextSize, minTextSize);
        wvHour.setVisibleItems(5);
        wvHour.setViewAdapter(mHourdapter);
        wvHour.setCurrentItem(selectedHour);
        wvHour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectedHour = Integer.valueOf(mHourdapter.getItemText(wheel.getCurrentItem()).toString());
                setTextviewSize(mHourdapter.getItemText(wheel.getCurrentItem()).toString(), mHourdapter);
            }
        });

        wvHour.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectedHour = Integer.valueOf(mHourdapter.getItemText(wheel.getCurrentItem()).toString());
                setTextviewSize(mHourdapter.getItemText(wheel.getCurrentItem()).toString(), mHourdapter);
            }
        });
    }

    private void initMinWheel() {
        wvMin = (WheelView) findViewById(R.id.wv_min);
        wvMin.setVisibility(View.VISIBLE);
        mMindapter = new CalendarTextAdapter(mContext, minList, selectedMin, maxTextSize, minTextSize);
        wvMin.setVisibleItems(5);
        wvMin.setViewAdapter(mMindapter);
        wvMin.setCurrentItem(selectedMin);

        wvMin.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectedMin = Integer.valueOf(mMindapter.getItemText(wheel.getCurrentItem()).toString());
                setTextviewSize(mMindapter.getItemText(wheel.getCurrentItem()).toString(), mMindapter);
            }
        });

        wvMin.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectedMin = Integer.valueOf(mMindapter.getItemText(wheel.getCurrentItem()).toString());
                setTextviewSize(mMindapter.getItemText(wheel.getCurrentItem()).toString(), mMindapter);
            }
        });
    }

    private void initStringWheel() {
        wvString = (WheelView) findViewById(R.id.wv_strings);
        wvString.setVisibility(View.VISIBLE);

        mStringdapter = new CalendarTextAdapter(mContext, StringList, TextUtils.isEmpty(selectedStr) ? 0 : getListIndex(StringList, selectedStr), maxTextSize, minTextSize);
        wvString.setVisibleItems(5);
        wvString.setViewAdapter(mStringdapter);
        wvString.setCurrentItem(TextUtils.isEmpty(selectedStr) ? 0 : getListIndex(StringList, selectedStr));

        wvString.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectedStr = mStringdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextviewSize(mStringdapter.getItemText(wheel.getCurrentItem()).toString(), mStringdapter);
            }
        });

        wvString.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectedStr = mStringdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextviewSize(mStringdapter.getItemText(wheel.getCurrentItem()).toString(), mStringdapter);
            }
        });
    }


    private void initStringWheel2() {
        wvString2 = (WheelView) findViewById(R.id.wv_strings2);
        wvString2.setVisibility(View.VISIBLE);

        mStringdapter2 = new CalendarTextAdapter(mContext, StringList2, TextUtils.isEmpty(selectedStr2) ? 0 : getListIndex(StringList2, selectedStr2), maxTextSize, minTextSize);
        wvString2.setVisibleItems(5);
        wvString2.setViewAdapter(mStringdapter2);
        wvString2.setCurrentItem(TextUtils.isEmpty(selectedStr2) ? 0 : getListIndex(StringList2, selectedStr2));

        wvString2.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectedStr2 = mStringdapter2.getItemText(wheel.getCurrentItem()).toString();
                setTextviewSize(mStringdapter2.getItemText(wheel.getCurrentItem()).toString(), mStringdapter2);
            }
        });

        wvString2.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectedStr2 = mStringdapter2.getItemText(wheel.getCurrentItem()).toString();
                setTextviewSize(mStringdapter2.getItemText(wheel.getCurrentItem()).toString(), mStringdapter2);
            }
        });
    }

    private void initStringWheel3() {
        wvString3 = (WheelView) findViewById(R.id.wv_strings3);
        wvString3.setVisibility(View.VISIBLE);

        mStringdapter3 = new CalendarTextAdapter(mContext, StringList3, TextUtils.isEmpty(selectedStr3) ? 0 : getListIndex(StringList3, selectedStr3), maxTextSize, minTextSize);
        wvString3.setVisibleItems(5);
        wvString3.setViewAdapter(mStringdapter3);
        wvString3.setCurrentItem(TextUtils.isEmpty(selectedStr3) ? 0 : getListIndex(StringList3, selectedStr3));

        wvString3.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectedStr3 = mStringdapter3.getItemText(wheel.getCurrentItem()).toString();
                setTextviewSize(mStringdapter3.getItemText(wheel.getCurrentItem()).toString(), mStringdapter3);
            }
        });

        wvString3.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectedStr3 = mStringdapter3.getItemText(wheel.getCurrentItem()).toString();
                setTextviewSize(mStringdapter3.getItemText(wheel.getCurrentItem()).toString(), mStringdapter3);
            }
        });
    }

    private void initDates() {
        initYearList();
        initMonthList();
        initDayList(selectedYear, selectedMonth);
        initHourList();
        initMinList();
    }

    private void initYearList() {
        for (int i = 2050; i > 1950; i--) {
            yearList.add(i + "");
        }
    }

    private void initMonthList() {
        for (int i = 1; i <= 12; i++) {
            if (String.valueOf(i).length() == 1) {
                monthList.add("0" + i);
            } else {
                monthList.add(i + "");
            }
        }
    }

    private void initDayList(int year, int month) {
        dayList.clear();
        for (int i = 1; i <= hasDays(year, month); i++) {
            if (String.valueOf(i).length() == 1) {
                dayList.add("0" + i);
            } else {
                dayList.add(i + "");
            }
        }
    }

    private void initHourList() {
        for (int i = 0; i <= 23; i++) {
            if (String.valueOf(i).length() == 1) {
                hourList.add("0" + i);
            } else {
                hourList.add(i + "");
            }
        }
    }

    private void initMinList() {
        for (int i = 0; i <= 59; i++) {
            if (String.valueOf(i).length() == 1) {
                minList.add("0" + i);
            } else {
                minList.add(i + "");
            }
        }
    }

    private int hasDays(int year, int month) {
        int days = 0;
        boolean isLeayYear;

        //计算闰年
        if (year % 4 == 0 && year % 100 != 0) {
            isLeayYear = true;
        } else {
            isLeayYear = false;
        }

        for (int i = 1; i <= 12; i++) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days = 31;
                    break;

                case 2:
                    if (isLeayYear) {
                        days = 29;
                    } else {
                        days = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days = 30;
                    break;
            }
        }
        return days;
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }

    public void setTitle(String str) {
        tvTitle.setText(str);
    }

    private int getListIndex(List<String> list, String key) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (key.equals(list.get(i))) {
                index = i;
            }
        }
        return index;
    }

    // [+] Click

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ok:
                if (listener != null) {
                    listener.getDate(selectedYear, selectedMonth, selectedDate, selectedHour, selectedMin);
                }
                if (stringWheelCommitListener != null) {
                    stringWheelCommitListener.getSelectedStr(selectedStr, selectedStr2, selectedStr3);
                }
                dismiss();
                break;
            case R.id.ll_dialog:
                dismiss();
                break;
            default:
                break;
        }

    }

    // [-] Click
    // [+] Class

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.view_select_wheel_text_item, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    public interface onDateWheelCommitListener {
        public void getDate(int year, int month, int day, int hour, int min);
    }

    public void setDateWheelCommitListener(onDateWheelCommitListener listener) {
        this.listener = listener;
    }

    public interface onStringWheelCommitListener {
        public void getSelectedStr(String str1, String str2, String str3);
    }

    public void setStringWheelCommitListener(onStringWheelCommitListener listener) {
        this.stringWheelCommitListener = listener;
    }
    // [-] Class
}
