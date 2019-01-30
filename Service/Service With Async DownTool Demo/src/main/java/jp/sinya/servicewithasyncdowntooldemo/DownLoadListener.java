package jp.sinya.servicewithasyncdowntooldemo;

/**
 * Created by koizumisinya on 2017/02/15.
 */

public interface DownLoadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}
