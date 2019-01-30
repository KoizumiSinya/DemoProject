package bean;

import java.util.Observable;

/**
 * 被观察的对象
 *
 * @author KoizumiSinya
 * @date 2016/3/13.
 */
public class DataChange extends Observable {
    private static DataChange instance = null;

    public static DataChange getInstance() {
        if (instance == null) {
            instance = new DataChange();
        }
        return instance;
    }

    public void notifyDataChange(Data data) {
        //被观察的对象 如何通知观察者我发生了改变？关键的地方就是这两个方法
        setChanged();//调用Observable的这个setChanged方法
        notifyObservers(data);//传入需要更新的是那些数据
    }
}
