package jp.sinya.formatphone.number.edit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEditPhoneNumber();
    }

    private void initEditPhoneNumber() {
        editText = findViewById(R.id.edit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.i("Sinya", "count:" + count);
                Log.i("Sinya", "start:" + start);
                Log.i("Sinya", "before:" + before);
                setTextFormat(charSequence, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //editText.setSelection(index);
            }
        });
    }


    private int index = 0;

    private void setTextFormat(CharSequence s, int start, int before, int inputCount) {
        Log.i("COCO", "onTextChanged: CharSequence " + s + "  start" + start + "  before:" + before + "  count:" + inputCount);

        if (s == null || s.length() == 0) {
            return;
        }
        //int startCursor = editText.getSelectionStart();
        int startCursor = start + inputCount;
        String phone = s.toString().replace(" ", "");
        boolean hasFormat = s.toString().contains(" ");

        if (!TextUtils.isEmpty(phone)) {
            if (hasFormat) {
                //去格式化
                if (inputCount == 1) {
                    if (3 < startCursor && startCursor <= 9) {
                        startCursor -= 1;
                    } else if (startCursor > 9) {
                        startCursor -= 2;
                    }
                } else if (before >= 1) { //多个字符剪贴, before > 1
                    if (3 < startCursor && startCursor < 9 && startCursor != 8) {
                        startCursor -= 1;
                    } else if (startCursor >= 9) {
                        startCursor -= 2;
                    }
                }
            } else if (s.toString().length() == 11) {
                if (3 < startCursor && startCursor < 8) {
                    startCursor += 1;
                } else if (startCursor >= 8) {
                    startCursor += 2;
                }
            }
            if (inputCount == 1) { // 输入字符串
                if (phone.length() == 11) {
                    StringBuilder builder = new StringBuilder(phone);
                    builder.insert(3, " ");
                    builder.insert(8, " ");
                    phone = builder.toString();

                    editText.setText(phone);
                } else if (phone.length() > 11) {
                    editText.setText(phone);
                }
            } else if (inputCount == 0) { // 删除字符串
                if (phone.length() == 11) {
                    StringBuilder builder = new StringBuilder(phone);
                    builder.insert(3, " ");
                    builder.insert(8, " ");
                    phone = builder.toString();

                }
                editText.setText(phone);
            }

            editText.setSelection(startCursor > phone.length() ? phone.length() : startCursor);
        }
    }

    private void setTextFormat(int start, int before, int inputCount) {

        String phone = editText.getText().toString().trim();
        phone = phone.replace(" ", "");

        if (!TextUtils.isEmpty(phone)) {
            if (inputCount == 1) {

                if (phone.length() == 11) {
                    StringBuilder builder = new StringBuilder(phone);
                    builder.insert(3, " ");
                    builder.insert(8, " ");
                    phone = builder.toString();

                    editText.setText(phone);
                } else if (phone.length() > 11) {
                    editText.setText(phone);
                }

            } else if (inputCount == 0) {
                if (phone.length() == 11) {
                    StringBuilder builder = new StringBuilder(phone);
                    builder.insert(3, " ");
                    builder.insert(8, " ");
                    phone = builder.toString();
                }
                editText.setText(phone);
            }
        }
    }

    public void clear(View view) {
        editText.setText("");

    }
}
