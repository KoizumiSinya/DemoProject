package jp.sinya.observerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Observable;

import bean.Data;
import bean.DataChange;
import bean.DataWatcher;

public class MainActivity extends AppCompatActivity {

    private DataWatcher mDataWatcher = new DataWatcher() {
        @Override
        public void update(Observable observable, Object data) {
            super.update(observable, data);

            //收到被观察的对象发来的更新通知
            Data bean = (Data) data;
            Log.i("Sinya", "update: value = " + bean.getDataValue());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = new Data();
        data.setDataValue(0);
        DataChange.getInstance().notifyDataChange(data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //观察者 向 被观察者中 添加订阅事件
        DataChange.getInstance().addObserver(mDataWatcher);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DataChange.getInstance().deleteObserver(mDataWatcher);
    }

    public void update(View view) {
        Data data = new Data();
        data.setDataValue(50);
        DataChange.getInstance().notifyDataChange(data);
    }
}
