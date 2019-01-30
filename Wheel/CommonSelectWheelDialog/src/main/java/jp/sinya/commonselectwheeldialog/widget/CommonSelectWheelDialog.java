package jp.sinya.commonselectwheeldialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.sinya.commonselectwheeldialog.R;
import jp.sinya.commonselectwheeldialog.widget.adapter.WheelTextAdapter;
import jp.sinya.commonselectwheeldialog.widget.bean.SelectBean;

/**
 * @author KoizumiSinya
 * @date 2016/08/04. 11:11
 * @editor
 * @date
 * @describe
 */
public class CommonSelectWheelDialog extends Dialog implements View.OnClickListener {

    /**
     * 年
     */
    public static final int TYPE_YEAR = 101;
    /**
     * 年 月
     */
    public static final int TYPE_YEAR_MONTH = 102;
    /**
     * 年 月 日
     */
    public static final int TYPE_YEAR_MONTH_DATE = 103;
    /**
     * 年 月 日 时
     */
    public static final int TYPE_YEAR_MONTH_DATE_HOUR = 104;
    /**
     * 年 月 日 时 分
     */
    public static final int TYPE_YEAR_MONTH_DATE_HOUR_MIN = 105;
    /**
     * 年 季度
     */
    public static final int TYPE_YEAR_QUARTER = 106;

    public static final int TYPE_OHTERS = 100;

    private Context context;
    private TextView tvTitle;
    private String title;

    private WheelView mWheelView1;
    private WheelView mWheelView2;
    private WheelView mWheelView3;
    private WheelView mWheelView4;
    private WheelView mWheelView5;

    private WheelView tempWheelView;

    private WheelTextAdapter mYearAdapter;
    private WheelTextAdapter mMonthAdapter;
    private WheelTextAdapter mDaydapter;


    private int wheelCount;
    private int type;

    private int visibleItemCount = 5;

    private SelectBean[] selectBeanArray;
    private String[] resultArray;

    private List<String> dateStrList;

    private OnClickOKListener okListener;

    private CommonSelectWheelDialog(Context context) {
        super(context, R.style.CommonSelectWheelStyle);
        this.context = context;
    }

    private CommonSelectWheelDialog(Context context, int type) {
        this(context);
        this.type = type;
    }

    /**
     * @param context
     * @param type
     * @param select
     */
    public CommonSelectWheelDialog(Context context, int type, SelectBean... select) {
        this(context, type);
        this.selectBeanArray = select;
    }

    /**
     * @param context
     * @param type
     * @param select
     */
    public CommonSelectWheelDialog(Context context, String title, int type, SelectBean... select) {
        this(context, type, select);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_common_select_wheel_layout);
        init();
        initWheel();
    }

    // [+] Init

    private void init() {

        tvTitle = (TextView) findViewById(R.id.view_common_select_wheel_layout_tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setText("请选择");
        }

        findViewById(R.id.view_common_select_wheel_layout_ll_rootview).setOnClickListener(this);
        findViewById(R.id.view_common_select_wheel_layout_btn_cancel).setOnClickListener(this);
        findViewById(R.id.view_common_select_wheel_layout_btn_ok).setOnClickListener(this);
    }

    private void initWheel() {

        initDefaultSelectBeans();

        switch (wheelCount) {
            case 5:
                mWheelView5 = (WheelView) findViewById(R.id.view_common_select_wheel_dialog_item_wheel_5);
                mWheelView5.setVisibility(View.VISIBLE);
            case 4:
                mWheelView4 = (WheelView) findViewById(R.id.view_common_select_wheel_dialog_item_wheel_4);
                mWheelView4.setVisibility(View.VISIBLE);
            case 3:
                mWheelView3 = (WheelView) findViewById(R.id.view_common_select_wheel_dialog_item_wheel_3);
                mWheelView3.setVisibility(View.VISIBLE);
            case 2:
                mWheelView2 = (WheelView) findViewById(R.id.view_common_select_wheel_dialog_item_wheel_2);
                mWheelView2.setVisibility(View.VISIBLE);
            case 1:
                mWheelView1 = (WheelView) findViewById(R.id.view_common_select_wheel_dialog_item_wheel_1);
                mWheelView1.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        createWheel();
    }

    /**
     * 针对不传任何选择数列对象的 只有Type的时候，也能初始化默认的选择方式
     * 1、默认Key选中的为当前系统日期时间
     * 2、使用默认的选择范围
     */
    private void initDefaultSelectBeans() {

        if (selectBeanArray == null || selectBeanArray.length == 0) {

            String curYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            String curMonth = setAutoZero(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
            String curDate = setAutoZero(String.valueOf(Calendar.getInstance().get(Calendar.DATE)));
            String curHour = setAutoZero(String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
            String curMin = setAutoZero(String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)));

            switch (type) {

                case TYPE_YEAR_MONTH_DATE_HOUR_MIN:
                    selectBeanArray = new SelectBean[5];
                    selectBeanArray[0] = new SelectBean(SelectBean.YEAR, curYear);
                    selectBeanArray[1] = new SelectBean(SelectBean.MONTH, curMonth);
                    selectBeanArray[2] = new SelectBean(SelectBean.DATE, curDate);
                    selectBeanArray[3] = new SelectBean(SelectBean.HOUR, curHour);
                    selectBeanArray[4] = new SelectBean(SelectBean.MIN, curMin);
                    break;

                case TYPE_YEAR_MONTH_DATE_HOUR:
                    selectBeanArray = new SelectBean[4];
                    selectBeanArray[0] = new SelectBean(SelectBean.YEAR, curYear);
                    selectBeanArray[1] = new SelectBean(SelectBean.MONTH, curMonth);
                    selectBeanArray[2] = new SelectBean(SelectBean.DATE, curDate);
                    selectBeanArray[3] = new SelectBean(SelectBean.HOUR, curHour);
                    break;

                case TYPE_YEAR_MONTH_DATE:
                    selectBeanArray = new SelectBean[3];
                    selectBeanArray[0] = new SelectBean(SelectBean.YEAR, curYear);
                    selectBeanArray[1] = new SelectBean(SelectBean.MONTH, curMonth);
                    selectBeanArray[2] = new SelectBean(SelectBean.DATE, curDate);
                    break;

                case TYPE_YEAR_MONTH:
                    selectBeanArray = new SelectBean[2];
                    selectBeanArray[0] = new SelectBean(SelectBean.YEAR, curYear);
                    selectBeanArray[1] = new SelectBean(SelectBean.MONTH, curMonth);
                    break;

                case TYPE_YEAR:
                    selectBeanArray = new SelectBean[1];
                    selectBeanArray[0] = new SelectBean(SelectBean.YEAR, curYear);
                    break;

                case TYPE_YEAR_QUARTER:
                    selectBeanArray = new SelectBean[2];
                    break;

                case TYPE_OHTERS:
                    break;

                default:
                    break;
            }
        }

        wheelCount = selectBeanArray == null ? 0 : selectBeanArray.length;
        resultArray = new String[selectBeanArray.length];
    }

    private void createWheel() {

        if (wheelCount == 0) {
            return;
        }

        switch (type) {

            case TYPE_YEAR:
                initYearWheel(mWheelView1);
                break;

            case TYPE_YEAR_MONTH:
                initYearWheel(mWheelView1);
                initMonthWheel(mWheelView2);
                break;

            case TYPE_YEAR_MONTH_DATE:
                initYearWheel(mWheelView1);
                initMonthWheel(mWheelView2);
                tempWheelView = mWheelView3;
                initDateWheel(mWheelView3);
                break;

            case TYPE_YEAR_MONTH_DATE_HOUR:
                initYearWheel(mWheelView1);
                initMonthWheel(mWheelView2);
                tempWheelView = mWheelView3;
                initDateWheel(mWheelView3);
                initHourWheel(mWheelView4);
                break;

            case TYPE_YEAR_MONTH_DATE_HOUR_MIN:
                initYearWheel(mWheelView1);
                initMonthWheel(mWheelView2);
                tempWheelView = mWheelView3;
                initDateWheel(mWheelView3);
                initHourWheel(mWheelView4);
                initMinWheel(mWheelView5);
                break;

            case TYPE_YEAR_QUARTER:
                initYearWheel(mWheelView1);
                initQuarterWheel(mWheelView2);
                break;
            case TYPE_OHTERS:
                break;
            default:
                break;
        }
    }

    /**
     * 设置选择年份的滚轮
     */
    private void initYearWheel(WheelView wheelView) {
        if (selectBeanArray != null && selectBeanArray.length > 0) {

            SelectBean selectBean = null;
            int indexYear = -1;

            for (int i = 0; i < selectBeanArray.length; i++) {
                if (selectBeanArray[i].type == SelectBean.YEAR) {
                    selectBean = selectBeanArray[i];
                    indexYear = i;
                }
            }

            if (selectBean != null && indexYear != -1) {
                //设置默认值
                resultArray[indexYear] = selectBean.key;

                final int finalIndexYear = indexYear;
                final List<String> yearStrList = getSelectYearStringList(selectBean);

                mYearAdapter = new WheelTextAdapter(context, yearStrList, getListIndex(yearStrList, selectBean.key));

                wheelView.setVisibleItems(visibleItemCount);
                wheelView.setViewAdapter(mYearAdapter);

                wheelView.addChangingListener(new OnWheelChangedListener() {
                    @Override
                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
                        resultArray[finalIndexYear] = yearStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndexYear], mYearAdapter);
                    }
                });

                wheelView.addScrollingListener(new OnWheelScrollListener() {
                    @Override
                    public void onScrollingStarted(WheelView wheel) {

                    }

                    @Override
                    public void onScrollingFinished(WheelView wheel) {
                        resultArray[finalIndexYear] = yearStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndexYear], mYearAdapter);
                    }
                });

                wheelView.setCurrentItem(getListIndex(yearStrList, selectBean.key));
            }
        }
    }

    /**
     * 初始化选择 年月 的滚轮
     */
    private void initMonthWheel(WheelView wheelView) {

        if (selectBeanArray != null && selectBeanArray.length > 0) {

            SelectBean selectBean = null;
            SelectBean selectDateBean = null;
            int indexMonth = -1;
            int indexYear = -1;
            int indexDate = -1;

            for (int i = 0; i < selectBeanArray.length; i++) {
                if (selectBeanArray[i].type == SelectBean.MONTH) {
                    selectBean = selectBeanArray[i];
                    indexMonth = i;
                }

                if (selectBeanArray[i].type == SelectBean.YEAR) {
                    indexYear = i;
                }

                if (selectBeanArray[i].type == SelectBean.DATE) {
                    selectDateBean = selectBeanArray[i];
                    indexDate = i;
                }
            }

            if (selectBean != null && indexMonth != -1) {

                selectBean.key = setAutoZero(selectBean.key);
                //设置默认值
                resultArray[indexMonth] = selectBean.key;//月

                final int finalIndexMonth = indexMonth;
                final int finalIndexYear = indexYear;
                final int finalIndexDate = indexDate;
                final SelectBean finalSelectDateBean = selectDateBean;

                final List<String> monthStrList = getSelectMonthStringList(selectBean);

                mMonthAdapter = new WheelTextAdapter(context, monthStrList, getListIndex(monthStrList, selectBean.key));

                wheelView.setVisibleItems(visibleItemCount);
                wheelView.setViewAdapter(mMonthAdapter);

                wheelView.addChangingListener(new OnWheelChangedListener() {
                    @Override
                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
                        resultArray[finalIndexMonth] = monthStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndexMonth], mMonthAdapter);

                        changeSelectDate(finalSelectDateBean,//
                                finalIndexYear != -1 ? Integer.valueOf(resultArray[finalIndexYear]) : 0,//
                                finalIndexMonth != -1 ? Integer.valueOf(resultArray[finalIndexMonth]) : 0,//
                                finalIndexDate);
                    }
                });

                wheelView.addScrollingListener(new OnWheelScrollListener() {
                    @Override
                    public void onScrollingStarted(WheelView wheel) {

                    }

                    @Override
                    public void onScrollingFinished(WheelView wheel) {
                        resultArray[finalIndexMonth] = monthStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndexMonth], mMonthAdapter);
                    }
                });

                wheelView.setCurrentItem(getListIndex(monthStrList, selectBean.key));
            }
        }
    }

    /**
     * 初始化选择 日 的滚轮
     */
    private void initDateWheel(WheelView wheelView) {
        if (selectBeanArray != null && selectBeanArray.length > 0) {

            SelectBean selectBean = null;
            int indexDate = -1;
            int indexYear = -1;
            int indexMonth = -1;

            for (int i = 0; i < selectBeanArray.length; i++) {
                if (selectBeanArray[i].type == SelectBean.DATE) {
                    selectBean = selectBeanArray[i];
                    indexDate = i;
                }

                if (selectBeanArray[i].type == SelectBean.YEAR) {
                    indexYear = i;
                }

                if (selectBeanArray[i].type == SelectBean.MONTH) {
                    indexMonth = i;
                }
            }

            if (selectBean != null && indexDate != -1) {

                selectBean.key = setAutoZero(selectBean.key);
                //设置默认值
                resultArray[indexDate] = selectBean.key;//日

                final int finalIndex = indexDate;
                dateStrList = getSelectDateStringList(selectBean, //
                        indexYear != -1 ? Integer.valueOf(resultArray[indexYear]) : 0, //
                        indexMonth != -1 ? Integer.valueOf(resultArray[indexMonth]) : 1);

                mDaydapter = new WheelTextAdapter(context, dateStrList, getListIndex(dateStrList, selectBean.key));

                wheelView.setVisibleItems(visibleItemCount);
                wheelView.setViewAdapter(mDaydapter);

                wheelView.addChangingListener(new OnWheelChangedListener() {
                    @Override
                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
                        resultArray[finalIndex] = dateStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndex], mDaydapter);
                    }
                });

                wheelView.addScrollingListener(new OnWheelScrollListener() {
                    @Override
                    public void onScrollingStarted(WheelView wheel) {

                    }

                    @Override
                    public void onScrollingFinished(WheelView wheel) {
                        resultArray[finalIndex] = dateStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndex], mDaydapter);
                    }
                });

                wheelView.setCurrentItem(getListIndex(dateStrList, selectBean.key));
            }
        }
    }

    private void changeSelectDate(SelectBean bean, int year, int month, final int dateIndex) {
        if (mDaydapter != null) {

            if (resultArray[dateIndex].equals(bean.end != -1 ? String.valueOf(bean.end) : "29")) {
                if (hasDays(year, month) == 28) {
                    resultArray[dateIndex] = bean.end != -1 ? String.valueOf(bean.end) : "28";
                }
            } else if (resultArray[dateIndex].equals(bean.end != -1 ? String.valueOf(bean.end) : "30")) {
                if (hasDays(year, month) == 28) {
                    resultArray[dateIndex] = bean.end != -1 ? String.valueOf(bean.end) : "28";
                } else if (hasDays(year, month) == 29) {
                    resultArray[dateIndex] = bean.end != -1 ? String.valueOf(bean.end) : "29";
                }
            } else if (resultArray[dateIndex].equals(bean.end != -1 ? String.valueOf(bean.end) : "31")) {
                if (hasDays(year, month) == 28) {
                    resultArray[dateIndex] = bean.end != -1 ? String.valueOf(bean.end) : "28";
                } else if (hasDays(year, month) == 29) {
                    resultArray[dateIndex] = bean.end != -1 ? String.valueOf(bean.end) : "29";
                } else if (hasDays(year, month) == 30) {
                    resultArray[dateIndex] = bean.end != -1 ? String.valueOf(bean.end) : "30";
                }
            }

            dateStrList = getSelectDateStringList(bean, year, month);
            mDaydapter = new WheelTextAdapter(context, dateStrList, getListIndex(dateStrList, resultArray[dateIndex]));

            tempWheelView.setVisibleItems(visibleItemCount);
            tempWheelView.setViewAdapter(mDaydapter);
            tempWheelView.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    resultArray[dateIndex] = dateStrList.get(wheel.getCurrentItem());
                    setTextViewSize(resultArray[dateIndex], mDaydapter);
                }
            });

            tempWheelView.addScrollingListener(new OnWheelScrollListener() {
                @Override
                public void onScrollingStarted(WheelView wheel) {

                }

                @Override
                public void onScrollingFinished(WheelView wheel) {
                    resultArray[dateIndex] = dateStrList.get(wheel.getCurrentItem());
                    setTextViewSize(resultArray[dateIndex], mDaydapter);
                }
            });
            tempWheelView.setCurrentItem(getListIndex(dateStrList, resultArray[dateIndex]));
        }
    }


    private void initHourWheel(WheelView wheelView) {
        if (selectBeanArray != null && selectBeanArray.length > 0) {

            SelectBean selectBean = null;
            int indexHour = -1;

            for (int i = 0; i < selectBeanArray.length; i++) {
                if (selectBeanArray[i].type == SelectBean.HOUR) {
                    selectBean = selectBeanArray[i];
                    indexHour = i;
                }
            }

            if (selectBean != null && indexHour != -1) {
                //设置默认值
                selectBean.key = setAutoZero(selectBean.key);
                resultArray[indexHour] = selectBean.key;

                final int finalIndexHour = indexHour;
                final List<String> hourStrList = getSelectHourStringList(selectBean);

                final WheelTextAdapter mHourdapter = new WheelTextAdapter(context, hourStrList, getListIndex(hourStrList, selectBean.key));

                wheelView.setVisibleItems(visibleItemCount);
                wheelView.setViewAdapter(mHourdapter);

                wheelView.addChangingListener(new OnWheelChangedListener() {
                    @Override
                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
                        resultArray[finalIndexHour] = hourStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndexHour], mHourdapter);
                    }
                });

                wheelView.addScrollingListener(new OnWheelScrollListener() {
                    @Override
                    public void onScrollingStarted(WheelView wheel) {

                    }

                    @Override
                    public void onScrollingFinished(WheelView wheel) {
                        resultArray[finalIndexHour] = hourStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndexHour], mHourdapter);
                    }
                });

                wheelView.setCurrentItem(getListIndex(hourStrList, selectBean.key));
            }
        }
    }

    private void initMinWheel(WheelView wheelView) {
        if (selectBeanArray != null && selectBeanArray.length > 0) {

            SelectBean selectBean = null;
            int indexMin = -1;

            for (int i = 0; i < selectBeanArray.length; i++) {
                if (selectBeanArray[i].type == SelectBean.MIN) {
                    selectBean = selectBeanArray[i];
                    indexMin = i;
                }
            }

            if (selectBean != null && indexMin != -1) {
                //设置默认值
                selectBean.key = setAutoZero(selectBean.key);
                resultArray[indexMin] = selectBean.key;

                final int finalIndexMin = indexMin;
                final List<String> minStrList = getSelectMinStringList(selectBean);

                final WheelTextAdapter mMindapter = new WheelTextAdapter(context, minStrList, getListIndex(minStrList, selectBean.key));

                wheelView.setVisibleItems(visibleItemCount);
                wheelView.setViewAdapter(mMindapter);

                wheelView.addChangingListener(new OnWheelChangedListener() {
                    @Override
                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
                        resultArray[finalIndexMin] = minStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndexMin], mMindapter);
                    }
                });

                wheelView.addScrollingListener(new OnWheelScrollListener() {
                    @Override
                    public void onScrollingStarted(WheelView wheel) {

                    }

                    @Override
                    public void onScrollingFinished(WheelView wheel) {
                        resultArray[finalIndexMin] = minStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndexMin], mMindapter);
                    }
                });

                wheelView.setCurrentItem(getListIndex(minStrList, selectBean.key));
            }
        }
    }

    private void initQuarterWheel(WheelView wheelView) {
        if (selectBeanArray != null && selectBeanArray.length > 0) {

            SelectBean selectBean = null;
            int indexQuarter = -1;

            for (int i = 0; i < selectBeanArray.length; i++) {
                if (selectBeanArray[i].type == SelectBean.QUARTER) {
                    selectBean = selectBeanArray[i];
                    indexQuarter = i;
                }
            }

            if (selectBean != null && indexQuarter != -1) {
                //设置默认值
                selectBean.key = setAutoZero(selectBean.key);
                resultArray[indexQuarter] = selectBean.key;

                final int finalIndexQuarter = indexQuarter;
                final List<String> quarterStrList = getSelectQuarterStringList(selectBean);

                final WheelTextAdapter mQuarterAdapter = new WheelTextAdapter(context, quarterStrList, getListIndex(quarterStrList, selectBean.key));

                wheelView.setVisibleItems(visibleItemCount);
                wheelView.setViewAdapter(mQuarterAdapter);

                wheelView.addChangingListener(new OnWheelChangedListener() {
                    @Override
                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
                        resultArray[finalIndexQuarter] = quarterStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndexQuarter], mQuarterAdapter);
                    }
                });

                wheelView.addScrollingListener(new OnWheelScrollListener() {
                    @Override
                    public void onScrollingStarted(WheelView wheel) {

                    }

                    @Override
                    public void onScrollingFinished(WheelView wheel) {
                        resultArray[finalIndexQuarter] = quarterStrList.get(wheel.getCurrentItem());
                        setTextViewSize(resultArray[finalIndexQuarter], mQuarterAdapter);
                    }
                });

                wheelView.setCurrentItem(getListIndex(quarterStrList, selectBean.key));
            }
        }
    }


    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    private void setTextViewSize(String curriteItemText, WheelTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(24);
            } else {
                textvew.setTextSize(14);
            }
        }
    }


    // [-] Init
    // [+] Click

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_common_select_wheel_layout_ll_rootview:
            case R.id.view_common_select_wheel_layout_btn_cancel:
                dismiss();
                break;

            case R.id.view_common_select_wheel_layout_btn_ok:
                if (okListener != null) {
                    okListener.click(resultArray);
                }
                dismiss();
                break;

            default:
                break;
        }
    }

    // [-] Click
    // [+] 计算相关的方法

    /**
     * 设置年份的可选择范围，生成对应的String列表
     *
     * @param bean
     * @return
     */
    private List<String> getSelectYearStringList(SelectBean bean) {
        List<String> list = new ArrayList<>();
        if (bean != null) {
            if (bean.start != -1 && bean.end != -1) {
                for (int i = bean.end; i > bean.start - 1; i--) {
                    list.add(i + "");
                }
            } else {
                for (int i = 2050; i > 1969; i--) {
                    list.add(i + "");
                }
            }
        } else {
            for (int i = 2050; i > 1969; i--) {
                list.add(i + "");
            }
        }
        return list;
    }

    /**
     * 设置月份的可选择范围，生成对应的String列表
     *
     * @param bean
     * @return
     */
    private List<String> getSelectMonthStringList(SelectBean bean) {
        List<String> list = new ArrayList<>();

        if (bean != null) {
            if (bean.start != -1 && bean.end != -1) {
                for (int i = bean.end; i > bean.start - 1; i--) {
                    list.add(setAutoZero(String.valueOf(i)));
                }
            } else {
                for (int i = 12; i > 0; i--) {
                    list.add(setAutoZero(String.valueOf(i)));
                }
            }
        } else {
            for (int i = 12; i > 0; i--) {
                list.add(setAutoZero(String.valueOf(i)));
            }
        }

        return list;
    }

    private List<String> getSelectDateStringList(SelectBean bean, int year, int month) {
        List<String> list = new ArrayList<>();

        if (bean != null) {
            if (bean.start != -1 && bean.end != -1) {
                for (int i = bean.end; i > bean.start - 1; i--) {
                    list.add(setAutoZero(String.valueOf(i)) + getWeekStr(year, month, i));
                }
            } else {
                for (int i = hasDays(year, month); i > 0; i--) {
                    list.add(setAutoZero(String.valueOf(i)));
                }
            }
        } else {
            for (int i = hasDays(year, month); i > 0; i--) {
                list.add(setAutoZero(String.valueOf(i)));
            }
        }
        return list;
    }

    private List<String> getSelectHourStringList(SelectBean bean) {

        List<String> list = new ArrayList<>();

        if (bean != null) {
            if (bean.start != -1 && bean.end != -1) {
                for (int i = bean.end; i > bean.start - 1; i--) {
                    list.add(setAutoZero(String.valueOf(i)));
                }
            } else {
                for (int i = 24; i > 0; i--) {
                    list.add(setAutoZero(String.valueOf(i)));
                }
            }
        } else {
            for (int i = 24; i > 0; i--) {
                list.add(setAutoZero(String.valueOf(i)));
            }
        }

        return list;
    }

    private List<String> getSelectMinStringList(SelectBean bean) {

        List<String> list = new ArrayList<>();

        if (bean != null) {
            if (bean.start != -1 && bean.end != -1) {
                for (int i = bean.end; i > bean.start - 1; i--) {
                    list.add(setAutoZero(String.valueOf(i)));
                }
            } else {
                for (int i = 59; i >= 0; i--) {
                    list.add(setAutoZero(String.valueOf(i)));
                }
            }
        } else {
            for (int i = 59; i >= 0; i--) {
                list.add(setAutoZero(String.valueOf(i)));
            }
        }

        return list;
    }

    private List<String> getSelectQuarterStringList(SelectBean bean) {
        List<String> list = new ArrayList<>();

        if (bean != null) {
            if (bean.start != -1 && bean.end != -1) {
                for (int i = bean.end; i > bean.start - 1; i--) {
                    list.add("第" + setAutoZero(String.valueOf(i)) + "季度");
                }
            } else {
                for (int i = 4; i > 0; i--) {
                    list.add("第" + setAutoZero(String.valueOf(i)) + "季度");
                }
            }
        } else {
            for (int i = 4; i > 0; i--) {
                list.add("第" + setAutoZero(String.valueOf(i)) + "季度");
            }
        }

        return list;
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
     * 获取已选中的Item关键字的位置index
     *
     * @param list
     * @param key
     * @return
     */
    private int getListIndex(List<String> list, String key) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (key.equals(list.get(i))) {
                index = i;
            }
        }
        return index;
    }

    private String setAutoZero(String str) {
        String result = str.length() == 1 ? "0" + str : str;
        return result;
    }

    public static String getWeekStr(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date date = calendar.getTime();
        return new SimpleDateFormat("(EEEE)").format(date);
    }
    // [-] 计算相关的方法
    // [+] Class


    public interface OnClickOKListener {
        public void click(String[] resultArr);

    }

    public void setOnClickOKListener(OnClickOKListener okListener) {
        this.okListener = okListener;
    }

    // [-] Class
}
