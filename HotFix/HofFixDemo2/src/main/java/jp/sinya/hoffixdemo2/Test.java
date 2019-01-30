package jp.sinya.hoffixdemo2;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Koizumi Sinya
 * @date 2018/01/28. 21:32
 * @edithor
 * @date
 */
public class Test {
    public void run(Context context) {
        int a = 10;
        int b = 0;
        Toast.makeText(context, a + "/" + b + "=" + a / b, Toast.LENGTH_SHORT).show();

    }
}
