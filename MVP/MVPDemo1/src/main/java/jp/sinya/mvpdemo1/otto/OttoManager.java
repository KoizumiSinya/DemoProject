package jp.sinya.mvpdemo1.otto;

import com.squareup.otto.Bus;

/**
 * @author Koizumi Sinya
 * @date 2017/02/20. 16:01
 * @edithor
 * @date
 */
public class OttoManager {
    private volatile static Bus ottoBus = null;

    private OttoManager() {

    }

    public static Bus getInstance() {
        if (ottoBus == null) {
            synchronized (OttoManager.class) {
                ottoBus = new Bus();
            }
        }
        return ottoBus;
    }
}
