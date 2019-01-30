package jp.sinya.dagger2.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import jp.sinya.dagger2.demo.bean.A;
import jp.sinya.dagger2.demo.bean.A2;
import jp.sinya.dagger2.demo.component.DaggerMainComponent;

public class MainActivity extends Activity {

    @Inject
    protected A a;
    @Inject
    protected A2 a2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第一种方式注入
        DaggerMainComponent.create().inject(this);


        //第二种方式注入
        //DaggerMainComponent.builder().build().inject(this);


    }

    public void onClick1(View v) {
        //可以直接使用这个对象了
        a.print();
    }

    public void onClick2(View v) {
        //可以直接使用这个对象了
        a2.print();
    }


}
