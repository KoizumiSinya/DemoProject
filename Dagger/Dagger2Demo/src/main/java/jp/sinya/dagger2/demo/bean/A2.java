package jp.sinya.dagger2.demo.bean;

import android.util.Log;

/**
 * @author Sinya
 * @date 2018/07/13. 13:14
 * @edithor
 * @date
 */
public class A2 {
    private B b;

    public A2() {
    }

    public A2(B b) {
        this.b = b;
    }

    public void print() {
        if (b != null) {
            Log.i("Sinya", "A2 对象打印输出  " + b.getName());
        }
    }
}
