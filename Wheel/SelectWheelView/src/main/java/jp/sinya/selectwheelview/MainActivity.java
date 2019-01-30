package jp.sinya.selectwheelview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import jp.sinya.selectwheelview.widget.SelectWheelDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void show(View view) {
        switch (view.getId()) {

            case R.id.tv_show:
                SelectWheelDialog dateWheelDialog = new SelectWheelDialog(MainActivity.this, 2016, 2, 28);
                dateWheelDialog.show();
                dateWheelDialog.setDateWheelCommitListener(new SelectWheelDialog.onDateWheelCommitListener() {
                    @Override
                    public void getDate(int year, int month, int day, int hour, int min) {
                        Log.i("Sinya", year + "-" + month + "-" + day + "-" + hour + "-" + min);
                    }
                });
                break;

            case R.id.tv_show2:
                ArrayList<String> strs = new ArrayList<>();
                strs.add("第一季度");
                strs.add("第二季度");
                strs.add("第三季度");
                strs.add("第四季度");
                SelectWheelDialog selectWheelDialog = new SelectWheelDialog(MainActivity.this, strs, "第四季度");
                selectWheelDialog.show();
                selectWheelDialog.setTitle("选择季度");

                selectWheelDialog.setStringWheelCommitListener(new SelectWheelDialog.onStringWheelCommitListener() {

                    @Override
                    public void getSelectedStr(String str1, String str2, String str3) {

                    }
                });
                break;

            case R.id.tv_show3:
                ArrayList<String> strs2 = new ArrayList<>();
                strs2.add("第一季度");
                strs2.add("第二季度");
                strs2.add("第三季度");
                strs2.add("第四季度");
                SelectWheelDialog selectWheelDialog3 = new SelectWheelDialog(MainActivity.this, 2016, strs2, "第二季度");
                selectWheelDialog3.show();
                selectWheelDialog3.setTitle("选择年和季度");
                selectWheelDialog3.setDateWheelCommitListener(new SelectWheelDialog.onDateWheelCommitListener() {
                    @Override
                    public void getDate(int year, int month, int day, int hour, int min) {
                        Log.i("Sinya", "year " + year);
                    }
                });
                selectWheelDialog3.setStringWheelCommitListener(new SelectWheelDialog.onStringWheelCommitListener() {

                    @Override
                    public void getSelectedStr(String str1, String str2, String str3) {
                        Log.i("Sinya", str1);
                    }
                });

                break;
            default:
                break;
        }

    }
}
