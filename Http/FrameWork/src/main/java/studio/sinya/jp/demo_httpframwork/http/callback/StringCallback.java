package studio.sinya.jp.demo_httpframwork.http.callback;

import studio.sinya.jp.demo_httpframwork.http.utils.FileUtils;
import studio.sinya.jp.demo_httpframwork.http.utils.IOUtils;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/14 9:27
 * editor：
 * updateDate：2015/9/14 9:27
 */
public abstract class StringCallback extends AbstractCallback {
    @Override
    protected Object bindData(String content) {
        if (FileUtils.isValidate(path)) {
            return IOUtils.readFromFile(path);
        }

        return content;
    }
}
