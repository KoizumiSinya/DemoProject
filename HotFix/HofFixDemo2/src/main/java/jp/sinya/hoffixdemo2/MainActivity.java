package jp.sinya.hoffixdemo2;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void run(View view) {
        new Test().run(this);
    }

    public void fix(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        if (file.exists()) {
            FixDexManager manager = new FixDexManager(this);
            try {
                manager.fixDex(file.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
