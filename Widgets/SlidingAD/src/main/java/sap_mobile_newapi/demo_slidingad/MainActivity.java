package sap_mobile_newapi.demo_slidingad;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import sap_mobile_newapi.demo_slidingad.widget.SlidingViewSwitcherAuto;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_view);

//        SlidingViewSwitcherAuto adView = (SlidingViewSwitcherAuto)findViewById(R.id.slidingLayout);
//        adView.startAutoPlay();
    }

}
