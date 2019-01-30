package bean;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者
 *
 * @author KoizumiSinya
 * @date 2016/3/13.
 */
public abstract class DataWatcher implements Observer {
    @Override
    public void update(Observable observable, Object data) {

    }
}
