package jp.sinya.eventbusdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import jp.sinya.eventbusdemo.api.NameAPI;
import jp.sinya.eventbusdemo.bean.NameInfor;
import jp.sinya.eventbusdemo.bean.NameResult;
import jp.sinya.eventbusdemo.interfaces.NameInforService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    @Bind(R.id.activity_main_edt_input)
    EditText input;
    @Bind(R.id.activity_main_btn_request)
    Button btn;
    @Bind(R.id.activity_main_tv_result)
    TextView result;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.activity_main_btn_request)
    public void clickRequest() {
        Retrofit retrofit = new Retrofit.Builder()//
                .addConverterFactory(GsonConverterFactory.create())//
                .baseUrl(NameAPI.BASE_URL)//
                .build();

        NameInforService service = retrofit.create(NameInforService.class);
        Call<NameResult> call = service.getResult(NameAPI.API_KEY, input.getText().toString());
        call.enqueue(new Callback<NameResult>() {
            @Override
            public void onResponse(Call<NameResult> call, Response<NameResult> response) {
                Log.i("Sinya", "请求码" + response.code());
                NameResult result = response.body();

                Log.i("Sinya", "result = " + (result == null ? "null" : result.toString()));
                if (result != null) {
                    EventBus.getDefault().post(result.result);
                }
            }

            @Override
            public void onFailure(Call<NameResult> call, Throwable t) {

            }
        });
    }

    public void onEventMainThread(NameInfor infor) {
        Log.i("Sinya", "EventBus收到数据：" + new Gson().toJson(infor));
        result.setText(infor.intro);
    }
}
