package jp.sinya.commonselectwheeldialog.widget.bean;

import java.util.List;

/**
 * @author KoizumiSinya
 * @date 2016/08/04. 11:52
 * @editor
 * @date
 * @describe
 */
public class SelectBean {

    // [+] TYPE 常量

    /**
     * 年
     */
    public static final int YEAR = 1;

    /**
     * 年 月
     */
    public static final int MONTH = 2;

    /**
     * 年 月 日
     */
    public static final int DATE = 3;

    /**
     * 年 月 日 时
     */
    public static final int HOUR = 4;

    /**
     * 年 月 日 时 分
     */
    public static final int MIN = 5;

    /**
     * 年 季度
     */
    public static final int QUARTER = 6;

    /**
     * 其它
     */
    public static final int OTHER = 0;

    // [-] TYPE 常量


    /**
     * 默认被选中的
     */
    public String key;

    /**
     * 开始位置或开始时间
     */
    public int start = -1;

    /**
     * 结束位置或结束时间
     */
    public int end = -1;

    public int type;

    public List<SelectItem> item;

    public SelectBean() {

    }

    public SelectBean(int type, String key) {
        this.type = type;
        this.key = key;
    }

    public SelectBean(int type, int start, int end, String key) {
        this(type, key);
        this.start = start;
        this.end = end;
    }

    public SelectBean(List<SelectItem> item, int type, int start, int end, String key) {
        this(type, start, end, key);
        this.item = item;
    }
}
