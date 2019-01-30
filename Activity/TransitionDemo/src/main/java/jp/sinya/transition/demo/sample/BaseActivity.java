package jp.sinya.transition.demo.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import jp.sinya.transition.demo.library.TransitionsHelper;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initViews();
    }

    public abstract int getLayoutId();


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        TransitionsHelper.unbind(this);
        super.onDestroy();
        Log.d("tag", "onDestroy");
    }

    protected abstract void initViews();
}