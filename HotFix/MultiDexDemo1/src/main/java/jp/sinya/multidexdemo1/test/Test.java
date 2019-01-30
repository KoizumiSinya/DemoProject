package jp.sinya.multidexdemo1.test;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Koizumi Sinya
 * @date 2018/01/06. 14:26
 * @edithor
 * @date
 */
public class Test {
    public void run(Context context) {
        int a = 10;
        int b = 0;
        Toast.makeText(context, "a/b=" + a / b, Toast.LENGTH_SHORT).show();
    }
}
