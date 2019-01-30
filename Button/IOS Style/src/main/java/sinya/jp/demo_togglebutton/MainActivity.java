package sinya.jp.demo_togglebutton;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import widget.ToggleButton;
import widget.ToggleButton.onChangedListener;


public class MainActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        ToggleButton togglebtn = (ToggleButton) findViewById(R.id.togglebtn);
        togglebtn.setOnchangedListener(listener);
    }


    onChangedListener listener = new onChangedListener() {
        @Override
        public void onChanged(boolean checkState) {
        }
    };
}
