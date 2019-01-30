package studio.sinya.jp.demo_httpframwork.http.callback;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/14 9:26
 * editor：
 * updateDate：2015/9/14 9:26
 */
public abstract class  PathCallback extends AbstractCallback {
    @Override
    protected Object bindData(String content) {
        return path;
    }
}
