package com.example.chaoshiapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.chaoshiapp.bean.Tab;
import com.example.chaoshiapp.fragment.GouwucheFragment;
import com.example.chaoshiapp.fragment.HomeFragment;
import com.example.chaoshiapp.fragment.HotFragment;
import com.example.chaoshiapp.fragment.MineFragment;
import com.example.chaoshiapp.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

//AppCompatActivity继承FragmentActivity
public class MainActivity extends AppCompatActivity {
    //底部菜单FragmentTabHost
    private FragmentTabHost mTabhost;
    private LayoutInflater mInflater;
    private List<Tab> mTabs = new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();





    }
//封装
//初始化底部菜单
private void initTab(){
    Tab tab_home = new Tab(HomeFragment.class,R.string.home,R.drawable.selector_icon_home);
    Tab tab_hot = new Tab(HotFragment.class,R.string.hot,R.drawable.selector_icon_category);
    Tab tab_gouwuche = new Tab(GouwucheFragment.class,R.string.gouwuche,R.drawable.selector_icon_cart);
    Tab tab_mine = new Tab(MineFragment.class,R.string.mine,R.drawable.selector_icon_mine);
    mTabs.add(tab_home);
    mTabs.add(tab_hot);
    mTabs.add(tab_gouwuche);
    mTabs.add(tab_mine);

    //初始化
    mInflater = LayoutInflater.from(this);
    //找到FragmentTabHost里的tabhost
    mTabhost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
    //调用setup
    mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

    for (Tab tab:mTabs
         ) {
        //增加TabSpec
        TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));
        //调用buildIndicator方法
        tabSpec.setIndicator(buildIndicator(tab));
        mTabhost.addTab(tabSpec,tab.getFragment(),null);
    }



    //去分割线
    mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    //默认起始页
    int id = getIntent().getIntExtra("id",0);
   // String data = getIntent().getStringExtra("name");
    if (id == 1)
        mTabhost.setCurrentTab(2);
    else
        mTabhost.setCurrentTab(0);
}
////底部菜单的两个控件的页面，并找两个控件
private  View buildIndicator(Tab tab){
        View view =mInflater.inflate(R.layout.tab_indicator,null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        return  view;
    }

}
