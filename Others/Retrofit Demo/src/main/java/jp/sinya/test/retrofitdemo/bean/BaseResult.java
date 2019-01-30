package jp.sinya.test.retrofitdemo.bean;

/**
 * @author Koizumi Sinya
 * @date 2017/05/20. 12:00
 * @edithor
 * @date
 */
public class BaseResult {
    public int status;
    public String msg;

    public long eventTag;//eventbus 接收标志
    public boolean success;


    @Override
    public String toString() {
        return "BaseResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
