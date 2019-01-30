package jp.sinya.test.retrofitdemo.utils;

import android.text.TextUtils;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import jp.sinya.test.retrofitdemo.MyApplication;
import jp.sinya.test.retrofitdemo.bean.BaseResult;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Koizumi Sinya
 * @date 2017/05/21. 1:27
 * @edithor
 * @date
 */
public class BaseCallBack<T extends BaseResult> implements Callback<T> {

    public long eventTag;//eventbus 接收标志

    public CallBackListener listener;


    public BaseCallBack() {
    }

    public BaseCallBack(long eventTag) {
        this.eventTag = eventTag;
    }

    public BaseCallBack(CallBackListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        T result = response.body();

        if (response.isSuccessful()) {
            if (result != null) {
                if (result.status == 0) {
                    sendResult(result);

                } else {
                    showMsg(result, result.msg);
                }
            } else {
                showMsg(result, "数据为空");
            }

        } else {
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                showMsg(result, errorBody.toString());
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        showMsg(null, t.toString());
    }

    private <T extends BaseResult> void sendResult(T result) {
        if (result != null) {
            result.success = true;

            if (listener != null) {
                listener.success(result);
            } else {
                if (eventTag != 0) {
                    result.eventTag = eventTag;
                }
                EventBus.getDefault().post(result);
            }
        }
    }

    private <T extends BaseResult> void showMsg(T result, String msg) {

        if (MyApplication.context != null && !TextUtils.isEmpty(msg)) {
            Toast.makeText(MyApplication.context, msg, Toast.LENGTH_SHORT).show();
        }

        if (result != null) {
            result.success = false;

            if (listener != null) {
                listener.error();
            } else {
                EventBus.getDefault().post(result);
            }
        }

    }

    public interface CallBackListener<T extends BaseResult> {
        void success(T result);

        void error();
    }
}
