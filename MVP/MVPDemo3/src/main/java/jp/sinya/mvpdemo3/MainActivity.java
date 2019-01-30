package jp.sinya.mvpdemo3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jp.sinya.mvpdemo3.mvp.view.contact.ContactListActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openContactList(View view) {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivity(intent);
    }
}
