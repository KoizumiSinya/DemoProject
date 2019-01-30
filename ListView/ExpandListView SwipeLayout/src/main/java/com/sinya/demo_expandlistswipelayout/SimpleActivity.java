package com.sinya.demo_expandlistswipelayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sinya.demo_expandlistswipelayout.widget.ExpandListWithMenuView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author KoizumiSinya
 * @date 2016/1/15.
 */
public class SimpleActivity extends Activity implements ExpandListWithMenuView.onExpandListItemClickLister {

    private Context context;
    private String[] groupTitle = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private HashMap<String, List<String>> data;
    private List<String> childString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        context = this;
        initData();

        ExpandListWithMenuView expand_list = (ExpandListWithMenuView) findViewById(R.id.expand_list);
        expand_list.setListData(context, groupTitle, data);
        expand_list.setOnExpandListClickListener(this);

    }


    private void initData() {
        data = new HashMap<>();

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("阿里巴巴");
        childString.add("爱心伟业大药房");
        childString.add("安体普专卖");
        childString.add("阿依艾工程软件(大连)有限公司北京办事处");
        //}
        data.put("A", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("北京盛彩佳印彩印设计有限公司");
        childString.add("北京恒通永昌建材经营部");
        childString.add("边航轮船有限公司");
        childString.add("贝乐香港实业有限公司");
        //}
        data.put("B", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("参生堂");
        childString.add("晨星广告");
        childString.add("城市漫步网站");
        //}
        data.put("C", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("东莞市高强信实业有限公司深圳办事处");
        childString.add("大中华国际集团");
        childString.add("东园演艺吧");
        childString.add("东信网络技术有限公司");
        childString.add("大地展示");
        childString.add("迪哥保健品有限公司");
        childString.add("大宇電子科技有限公司");
        childString.add("多科水力分析有限公司");
        //}
        data.put("D", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("恩曼技术有限公司");
        childString.add("二连浩特市北方国际贸易公司北京经营部");
        //}
        data.put("E", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("福临装饰工程部");
        childString.add("奋进达福田店");
        childString.add("富创广告");
        childString.add("方圆达会计师事务所");
        childString.add("飞顺达实业有限公司");
        childString.add("佛尘阁佛具流通处");
        childString.add("芬优公司");
        childString.add("福田区金地工业区");
        childString.add("风笛商务调查机构");
        childString.add("丰颖企业有限公司");
        //}
        data.put("F", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("国辉通讯");
        childString.add("盖拉斯电子有限公司深圳代表处");
        childString.add("格多达科技有限公司");
        childString.add("广兴翌地毯工艺品商店");
        childString.add("国墙广告");
        childString.add("功明设计广告公司");
        childString.add("港边公司");
        childString.add("国际通");
        childString.add("冠图信息咨询有限公司");
        //}
        data.put("G", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("海大承网络技术有限公司(深圳办事处)");
        childString.add("环球商旅网");
        childString.add("华理汽车维修中心");
        //}
        data.put("H", childString);
    }

    @Override
    public void onClick(View v, int groupPosition, int childPosition) {
        switch (v.getId()) {
            case R.id.swipe_layout:
                Toast.makeText(context, "点击Item：" + data.get(groupTitle[groupPosition]).get(childPosition), Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_img:
                Toast.makeText(context, "点击头像：" + data.get(groupTitle[groupPosition]).get(childPosition), Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_button:
                Toast.makeText(context, "点击删除：" + data.get(groupTitle[groupPosition]).get(childPosition), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    @Override
    public void onLongClick(View v, int groupPosition, int childPosition) {

    }
}
