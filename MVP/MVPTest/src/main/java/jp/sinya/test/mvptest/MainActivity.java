package jp.sinya.test.mvptest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jp.sinya.test.mvptest.base.GetPhoneInfoPresenter;
import jp.sinya.test.mvptest.frame.GetPhoneInfoFragment;

public class MainActivity extends AppCompatActivity {


    private GetPhoneInfoFragment getPhoneInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPhoneInfoFragment = new GetPhoneInfoFragment();
        new GetPhoneInfoPresenter(getPhoneInfoFragment);

        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.activity_main_frame_layout, getPhoneInfoFragment);
        transaction.commit();
    }

}
