package jp.sinya.wheel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import jp.sinya.wheel.widget.DateWheelDialog;

public class MainActivity extends AppCompatActivity {

    private TextView mBirth;
    private TextView mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBirth = (TextView) findViewById(R.id.tv_birth);
        mAddress = (TextView) findViewById(R.id.tv_address);

        mBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(MainActivity.this);
                mChangeBirthDialog.setDate(2015, 03, 29);
                mChangeBirthDialog.show();
                mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day) {
                        // TODO Auto-generated method stub
                        Toast.makeText(MainActivity.this, year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                /*DateWheelDialog dateWheelDialog = new DateWheelDialog(MainActivity.this, 2016);
                dateWheelDialog.show();
                dateWheelDialog.setDateWheelCommitListener(new DateWheelDialog.onDateWheelCommitListener() {
                    @Override
                    public void getDate(int year, int month, int day, int hour, int min) {
                        Log.i("Sinya", year + "-" + month + "-" + day + "-" + hour + "-" + min);
                    }
                });*/

                ArrayList<String> strs = new ArrayList<>();
                strs.add("第一季度");
                strs.add("第二季度");
                strs.add("第三季度");
                strs.add("第四季度");
                DateWheelDialog dateWheelDialog = new DateWheelDialog(MainActivity.this, strs, "第四季度");
                dateWheelDialog.show();
                dateWheelDialog.setTitle("选择季度");

                dateWheelDialog.setStringWheelCommitListener(new DateWheelDialog.onStringWheelCommitListener() {

                    @Override
                    public void getSelectedStr(String str) {
                        Log.i("Sinya", str);

                    }
                });
            }
        });
    }
}
