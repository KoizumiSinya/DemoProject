package jp.sinya.multidexdemo1;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import jp.sinya.multidexdemo1.test.Test;
import jp.sinya.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void run(View v) {
        new Test().run(this);
    }

    public void fix(View view) {
        fixBug();
    }

    private void fixBug() {

        // 对应目录 /data/data/packageName/mydex/classes2.dex
        File fileDir = getDir(FixDexUtils.DEX_DIR, Context.MODE_PRIVATE);
        String name = "classes2.dex";
        String filePath = fileDir.getAbsolutePath() + File.separator + name;
        LogUtils.Sinya("dexSavePath: " + filePath);

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            //下载已修复的dex，保存在 SD卡路径根目录下的 /01Sinya/classes2.dex
            String downDexFilePath = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "01Sinya/" + name;
            LogUtils.Sinya("downDexFilePath: " + downDexFilePath);

            inputStream = new FileInputStream(downDexFilePath);
            fileOutputStream = new FileOutputStream(filePath);

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, len);
            }
            File newFile = new File(filePath);
            if (newFile.exists()) {
                Toast.makeText(this, "dex 迁移成功", Toast.LENGTH_SHORT).show();
            }

            //热修复
            FixDexUtils.loadFixedDex(this);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.SinyaE(e.toString());
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e2) {
                LogUtils.SinyaE(e2.toString());
            }
        }


    }

}
