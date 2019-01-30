package jp.sinya.contactlocationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import jp.sinya.contactlocationdemo.model.Contact;
import jp.sinya.contactlocationdemo.view.MainView;
import jp.sinya.contactlocationdemo.view.impl.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainView {

    private EditText editText;
    private Button button;
    private TextView textView;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPresenter();
    }

    private void initView() {
        editText = findViewById(R.id.edit);
        button = findViewById(R.id.btn);
        textView = findViewById(R.id.tv);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchPhone(editText.getText().toString().trim());
            }
        });
    }

    private void initPresenter() {
        presenter = new MainPresenter(this);
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void closeLoad() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(Contact contact) {
        textView.setText(contact.toString());
    }
}
