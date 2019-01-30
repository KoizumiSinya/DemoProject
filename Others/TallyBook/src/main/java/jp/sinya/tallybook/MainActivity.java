package jp.sinya.tallybook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.List;

import jp.sinya.tallybook.model.Tally;
import jp.sinya.tallybook.presenter.MainPresenter;
import jp.sinya.tallybook.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private RecyclerView recyclerView;
    private TallyAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        initRecyclerView();
        initPresenter();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initPresenter() {
        presenter = new MainPresenter(this);
        presenter.queryTally();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog = inflater.inflate(R.layout.dialog_input, null);
        final EditText editTitle = dialog.findViewById(R.id.edit_title);
        final EditText editCost = dialog.findViewById(R.id.edit_cost);
        final DatePicker picker = dialog.findViewById(R.id.datepicker);
        builder.setView(dialog);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Tally tally = new Tally();
                    tally.setTitle(editTitle.getText().toString());
                    tally.setCost(Float.valueOf(editCost.getText().toString()));
                    String date = picker.getYear() + "-" + (picker.getMonth() + 1) + "-" + picker.getDayOfMonth();
                    tally.setDate(date);
                    presenter.saveTally(tally);
                } catch (Exception e) {

                }
            }
        });
        builder.setTitle("创建新账目");
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void addTally() {

    }

    @Override
    public void editTally() {

    }

    @Override
    public void deleteTally() {

    }

    @Override
    public void update(List dataList) {
        adapter = new TallyAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
    }
}
