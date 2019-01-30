package studio.sinya.jp.demo_calculator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {

    /**
     * 输入的第一个数
     */
    private EditText number1;

    /**
     * 输入的第二个数
     */
    private EditText number2;

    /**
     * 显示运算结果
     */
    private TextView number3;

    /**
     * 运算符号
     */
    private TextView operator;

    /**
     * 选择运算符号 加 减 乘 除
     */

    private Button addBtn;
    private Button subBtn;
    private Button mulBtn;
    private Button divBtn;

    /**
     * 运算结果
     */
    private double result = 0;

    /**
     * 处理运算的广播
     */
    private BroadcastReceiverCounter br;
    private static final String BROADCAST_INTENT = "broadcast_intent";

    /**
     * 异步更新UI的handler
     */
    private Handler handler = new Handler();

    private Intent intentBroad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        //乘数 被乘数 结果
        number1 = (EditText) findViewById(R.id.number1);
        number2 = (EditText) findViewById(R.id.number2);
        number3 = (TextView) findViewById(R.id.result);

        //给editText绑上一个更改文本监听事件 在监听到修改文本之后会调用count()方法
        number1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                count();
            }
        });

        number2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                count();
            }
        });


        //运算符
        operator = (TextView) findViewById(R.id.operator);

        //选择运算符按钮
        addBtn = (Button) findViewById(R.id.addition);
        subBtn = (Button) findViewById(R.id.subtraction);
        mulBtn = (Button) findViewById(R.id.multiplication);
        divBtn = (Button) findViewById(R.id.division);

        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        mulBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);

        //注册广播
        br = new BroadcastReceiverCounter();
        IntentFilter intentFilter = new IntentFilter(BROADCAST_INTENT);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(br, intentFilter);

        //设置其对应的广播Intent
        intentBroad = new Intent(BROADCAST_INTENT);
        intentBroad.addCategory(Intent.CATEGORY_DEFAULT);
    }


    @Override
    public void onClick(View v) {

        //变更运算符号
        switch (v.getId()) {

            case R.id.addition:
                intentBroad.putExtra("code", '+');
                break;

            case R.id.subtraction:
                intentBroad.putExtra("code", '-');
                break;

            case R.id.multiplication:
                intentBroad.putExtra("code", '*');
                break;

            case R.id.division:
                intentBroad.putExtra("code", '/');
                break;
        }

        count();

    }

    /**
     * 处理运算操作的判断和发送
     */
    private void count() {

        //从EditText上获取的String
        String a, b;

        //用于保存所输入的两个数值
        double x = 0, y = 0;

        a = number1.getText().toString();
        b = number2.getText().toString();

        //下面这里需要理解，逻辑稍微复杂

        if (TextUtils.isEmpty(a) || TextUtils.isEmpty(b)) {
            //如果两个editText为空且点击了切换运算符号的时候，是不需要发送广播异步处理运算结果，仅仅是更换运算符，因此会处理以下步骤：

            //①因为点击运算符的时候，已经给intentBroad 的Extra绑定上code运算符，所以可以通过intentBroad直接拿
            //②把当前的运算符作为默认值，所以需要通过operator的getText获取，并处理成char类型
            //③设置Text需要String类型，所以再转换一次
            char defaultCode = operator.getText().toString().toCharArray()[0];
            char changCode = intentBroad.getCharExtra("code", defaultCode);
            String code = String.valueOf(changCode);

            //不进行运算，但修改运算符
            operator.setText(code);
            number3.setText("");

        } else {

            try {

                //转换用户输入的两个运算参数（防止恶意输入非数字）
                x = Double.parseDouble(a);
                y = Double.parseDouble(b);

                //将两个运算参数通过intentBroad 的Extra绑进去
                intentBroad.putExtra("num1", x);
                intentBroad.putExtra("num2", y);

                //发送广播 通知运算操作
                sendBroadcast(intentBroad);
            } catch (Exception e) {
                showToast("输入错误，请重新输入");
            }
        }
    }


    private void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public class BroadcastReceiverCounter extends BroadcastReceiver {

        private char code;
        private double num1;
        private double num2;

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("Sinya", "接收到广播");

            //通过intent把之前 intentBroad 绑进去的运算符、两个运算参数提取出来

            //运算符的默认值就是获取当前的operator的getText文本，即是运算符。防止用户并没有使用切换运算符，intentBroad没有传递过来导致为空
            code = intent.getCharExtra("code", operator.getText().toString().toCharArray()[0]);
            num1 = intent.getDoubleExtra("num1", 0);
            num2 = intent.getDoubleExtra("num2", 0);

            //真正的计算操作在这里进行
            switch (code) {

                case '+':
                    result = num1 + num2;
                    break;

                case '-':
                    result = num1 - num2;
                    break;

                case '*':
                    result = num1 * num2;
                    break;

                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        showToast("被除数不能为零");
                    }
                    break;

                default:
                    break;
            }

            //通过handler异步更新UI 也就是把运算结果显示出来
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //更新运算符
                    operator.setText(String.valueOf(code));
                    //更新结果
                    number3.setText(String.valueOf(result));
                }
            });

        }
    }
}
