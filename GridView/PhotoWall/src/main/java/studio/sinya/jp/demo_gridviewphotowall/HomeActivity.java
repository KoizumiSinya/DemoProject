package studio.sinya.jp.demo_gridviewphotowall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2015/10/25.
 */
public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    public void click(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.btn1:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn2:
                intent = new Intent(this, BestCacheActivity.class);
                startActivity(intent);
            break;

            default:
                break;
        }
    }
}
