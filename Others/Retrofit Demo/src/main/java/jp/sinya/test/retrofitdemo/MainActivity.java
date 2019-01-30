package jp.sinya.test.retrofitdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.sinya.test.retrofitdemo.bean.PhoneResult;
import jp.sinya.test.retrofitdemo.utils.BaseCallBack;
import jp.sinya.test.retrofitdemo.utils.NetUtils;

public class MainActivity extends Activity implements View.OnClickListener {


    @Bind(R.id.edt_input)
    EditText edit_inputPhone;

    @Bind(R.id.btn_get)
    Button btn_Get;

    @Bind(R.id.tv_result)
    TextView tv_Result;

    private long eventTag;

    //接口地址URL全路径  http://apis.baidu.com/netpopo/shouji/query
    private static final String URL = "http://apis.baidu.com/netpopo/shouji/query";
    //基地址
    private static final String BASE_URL = "http://apis.baidu.com";

    private static final String API_KEY = "2f40cea713e8fa69884a66bdaea51beb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_get)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                getPhoneResult();
                break;
            default:
                break;
        }
    }

    private void setUI(PhoneResult result) {
        if (result.success) {
            PhoneResult.RetPhoneEntity phoneEntity = result.result;
            if (phoneEntity != null) {
                tv_Result.setText("查询结果： " //
                        + phoneEntity.company //
                        + "\nSIM类型： " + phoneEntity.cardtype //
                        + "\n号码归属： " + phoneEntity.province + phoneEntity.city);
            } else {
                tv_Result.setText("数据获取不到");
            }
        } else {
            tv_Result.setText("数据获取不到");
        }
    }

    private void getPhoneResult() {
        tv_Result.setText("");

        String number = edit_inputPhone.getText().toString().trim();
        requestPhone(number);
        //requestPhone2(number);
    }

    private void requestPhone(String number) {
        NetUtils.GetPhoneInfo(number);
    }

    private void requestPhone2(String number) {
        eventTag = System.currentTimeMillis();
        NetUtils.GetPhoneInfo(eventTag, number);
    }

    private void requestPhone3(String number) {
        NetUtils.GetPhoneInfo(number, new BaseCallBack.CallBackListener<PhoneResult>() {
            @Override
            public void success(PhoneResult result) {
                setUI(result);
            }

            @Override
            public void error() {
                tv_Result.setText("数据获取不到");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resultPhoneResult(PhoneResult result) {
        if (result.success) {
            setUI(result);
        } else {
            tv_Result.setText("数据获取不到");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resultPhoneResult2(PhoneResult result) {
        if (result.eventTag != 0 && eventTag == result.eventTag) {

            if (result.success) {
                setUI(result);
            } else {
                tv_Result.setText("数据获取不到");
            }
        }
    }


}
