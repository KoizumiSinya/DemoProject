package jp.sinya.eventbusdemo.bean;

/**
 * @author KoizumiSinya
 * @date 2016/07/22.
 */
public class NameResult {

    public NameInfor result;

    public int error_code;
    public String reason;

    @Override
    public String toString() {
        return "NameResult{" +
                "result=" + result +
                ", error_code=" + error_code +
                ", reason='" + reason + '\'' +
                '}';
    }
}
