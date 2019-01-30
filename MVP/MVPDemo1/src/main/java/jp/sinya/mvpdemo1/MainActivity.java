package jp.sinya.mvpdemo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jp.sinya.mvpdemo1.baiduhome.BaiduFragment;
import jp.sinya.mvpdemo1.baiduhome.BaiduPresenter;

/**
 * @author Koizumi Sinya
 * @date 2017/02/20. 12:12
 * @edithor
 * @date
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaiduFragment framgent = new BaiduFragment();
        new BaiduPresenter(framgent);

        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.activity_main_fl, framgent);
        transaction.commit();
    }

}
