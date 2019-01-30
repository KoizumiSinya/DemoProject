package jp.sinya.commonselectwheeldialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import jp.sinya.commonselectwheeldialog.widget.CommonSelectWheelDialog;
import jp.sinya.commonselectwheeldialog.widget.bean.SelectBean;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_select_wheel);
    }

    /**
     * 选择年
     *
     * @param view
     */
    public void show1(View view) {

        SelectBean bean = new SelectBean(SelectBean.YEAR, 2000, 2020, String.valueOf(2010));

//        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年份", CommonSelectWheelDialog.TYPE_YEAR);
        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年", CommonSelectWheelDialog.TYPE_YEAR, bean);

        dialog.setOnClickOKListener(new CommonSelectWheelDialog.OnClickOKListener() {
            @Override
            public void click(String[] resultArr) {
                Toast.makeText(MainActivity.this, "选择了" + resultArr[0], Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    /**
     * 选择年月
     *
     * @param v
     */
    public void show2(View v) {
        SelectBean beanYear = new SelectBean(SelectBean.YEAR, 1970, 2020, String.valueOf(2016));
        SelectBean beanMonth = new SelectBean(SelectBean.MONTH, 1, 10, String.valueOf(8));

//        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年月", CommonSelectWheelDialog.TYPE_YEAR_MONTH);
        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年月", CommonSelectWheelDialog.TYPE_YEAR_MONTH, beanYear, beanMonth);

        dialog.setOnClickOKListener(new CommonSelectWheelDialog.OnClickOKListener() {
            @Override
            public void click(String[] resultArr) {
                Toast.makeText(MainActivity.this, "选择了" + resultArr[0] + "年" + resultArr[1] + "月", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    /**
     * 选择年月日
     *
     * @param v
     */
    public void show3(View v) {
        SelectBean beanYear = new SelectBean(SelectBean.YEAR, 1970, 2020, String.valueOf(2016));
        SelectBean beanMonth = new SelectBean(SelectBean.MONTH, 1, 10, String.valueOf(8));
        SelectBean beanDate = new SelectBean(SelectBean.DATE, 5, 13, String.valueOf(7) + CommonSelectWheelDialog.getWeekStr(2016, 8, 7));

//        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年月日", CommonSelectWheelDialog.TYPE_YEAR_MONTH_DATE);
        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年月日", CommonSelectWheelDialog.TYPE_YEAR_MONTH_DATE, beanYear, beanMonth, beanDate);
        dialog.setOnClickOKListener(new CommonSelectWheelDialog.OnClickOKListener() {
            @Override
            public void click(String[] resultArr) {
                Toast.makeText(MainActivity.this, "选择了" + resultArr[0] + "年" + resultArr[1] + "月" + resultArr[2] + "日", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    /**
     * 年月日时
     *
     * @param v
     */
    public void show4(View v) {
        SelectBean beanYear = new SelectBean(SelectBean.YEAR, 1970, 2020, String.valueOf(2016));
        SelectBean beanMonth = new SelectBean(SelectBean.MONTH, 1, 10, String.valueOf(8));
        SelectBean beanDate = new SelectBean(SelectBean.DATE, 5, 13, String.valueOf(7) + CommonSelectWheelDialog.getWeekStr(2016, 8, 7));
        SelectBean beanHour = new SelectBean(SelectBean.HOUR, 9, 18, String.valueOf(14));

//        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年月日时", CommonSelectWheelDialog.TYPE_YEAR_MONTH_DATE_HOUR);
        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年月日时", CommonSelectWheelDialog.TYPE_YEAR_MONTH_DATE_HOUR, beanYear, beanMonth, beanDate, beanHour);
        dialog.setOnClickOKListener(new CommonSelectWheelDialog.OnClickOKListener() {
            @Override
            public void click(String[] resultArr) {
                Toast.makeText(MainActivity.this, "选择了" + resultArr[0] + "年" + resultArr[1] + "月" + resultArr[2] + "日" + resultArr[3] + "时", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    /**
     * 年月日时分
     *
     * @param v
     */
    public void show5(View v) {
        SelectBean beanYear = new SelectBean(SelectBean.YEAR, 1970, 2020, String.valueOf(2016));
        SelectBean beanMonth = new SelectBean(SelectBean.MONTH, 1, 10, String.valueOf(8));
        SelectBean beanDate = new SelectBean(SelectBean.DATE, 5, 13, String.valueOf(7) + CommonSelectWheelDialog.getWeekStr(2016, 8, 7));
        SelectBean beanHour = new SelectBean(SelectBean.HOUR, 9, 18, String.valueOf(14));
        SelectBean beanMin = new SelectBean(SelectBean.MIN, 0, 40, String.valueOf(35));

//        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年月日时分", CommonSelectWheelDialog.TYPE_YEAR_MONTH_DATE_HOUR_MIN);
        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年月日时分", CommonSelectWheelDialog.TYPE_YEAR_MONTH_DATE_HOUR_MIN, beanYear, beanMonth, beanDate, beanHour, beanMin);
        dialog.setOnClickOKListener(new CommonSelectWheelDialog.OnClickOKListener() {
            @Override
            public void click(String[] resultArr) {
                Toast.makeText(MainActivity.this, "选择了" + resultArr[0] + "年" + resultArr[1] + "月" + resultArr[2] + "日" + resultArr[3] + "时" + resultArr[4] + "分", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    /**
     * 年季度
     *
     * @param v
     */
    public void show6(View v) {
        SelectBean beanYear = new SelectBean(SelectBean.YEAR, 2000, 2020, String.valueOf(2016));
        SelectBean beanQuarter = new SelectBean(SelectBean.QUARTER, 1, 3, "第02季度");

//        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年季度", CommonSelectWheelDialog.TYPE_YEAR_QUARTER);
        CommonSelectWheelDialog dialog = new CommonSelectWheelDialog(this, "请选择年季度", CommonSelectWheelDialog.TYPE_YEAR_QUARTER, beanYear, beanQuarter);
        dialog.setOnClickOKListener(new CommonSelectWheelDialog.OnClickOKListener() {
            @Override
            public void click(String[] resultArr) {
                Toast.makeText(MainActivity.this, "选择了" + resultArr[0] + "年" + resultArr[1], Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
