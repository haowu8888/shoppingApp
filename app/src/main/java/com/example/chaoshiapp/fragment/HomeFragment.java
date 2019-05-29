package com.example.chaoshiapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.chaoshiapp.R;
import com.example.chaoshiapp.adapter.DividerItemDecortion;
import com.example.chaoshiapp.adapter.HomeCatgoryAdapter;
import com.example.chaoshiapp.bean.HomeCategory;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by Ivan on 15/9/25.
 */
public class HomeFragment extends Fragment {
    //轮播图的
    private SliderLayout mSliderLayout;
    private PagerIndicator indicator;
    //首页显示数据的
    private RecyclerView mRecyclerView;
    //HomeCatgoryAdapter适配器
    private HomeCatgoryAdapter mAdatper;
    //


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //轮播图
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        //轮播图循环图标
        indicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        //轮播图方法初始化
        initSlider();
        //首页展示
        initRecyclerView(view);

        return view;
    }


    //首页展示方法
    private void initRecyclerView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        List<HomeCategory> datas = new ArrayList<>(15);

        HomeCategory category = new HomeCategory("聚划算", R.drawable.xq11, R.drawable.xq12, R.drawable.xq13);
        datas.add(category);
        category = new HomeCategory("旗舰推荐", R.drawable.xq21, R.drawable.xq22, R.drawable.xq23);
        datas.add(category);
        category = new HomeCategory("天天特卖", R.drawable.xq31, R.drawable.xq32, R.drawable.xq33);
        datas.add(category);
        category = new HomeCategory("有好货", R.drawable.xq41, R.drawable.xq42, R.drawable.xq43);
        datas.add(category);





    //往HomeCatgoryAdapter适配器加入数据
        mAdatper = new HomeCatgoryAdapter(datas);
    //布局绑定适配器
        mRecyclerView.setAdapter(mAdatper);
        //DividerItemDecortion里设置的每个item的格式，用来隔离美观
        mRecyclerView.addItemDecoration(new DividerItemDecortion());
        //设置新的打开布局
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
    //轮播图方法
    private void initSlider() {


        TextSliderView textSliderView = new TextSliderView(this.getActivity());
        textSliderView.image("https://img.alicdn.com/tfs/TB1NLYoV3HqK1RjSZFPXXcwapXa-750-291.jpg_Q90.jpg");
        textSliderView.description("满399减100");
    //监听事件
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView baseSliderView) {

               Toast.makeText(HomeFragment.this.getActivity(),"满399减100",Toast.LENGTH_LONG).show();


            }
        });

        TextSliderView textSliderView2 = new TextSliderView(this.getActivity());
        textSliderView2.image("https://img.alicdn.com/tfs/TB1mpt0WcfpK1RjSZFOXXa6nFXa-750-291.jpg_Q90.jpg");
        textSliderView2.description("第二件0元");
    //监听事件

        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView baseSliderView) {

                Toast.makeText(HomeFragment.this.getActivity(),"第二件0元",Toast.LENGTH_LONG).show();

            }
        });

        TextSliderView textSliderView3 = new TextSliderView(this.getActivity());
        textSliderView3.image("https://gw.alicdn.com/tfs/TB1gdkRVFzqK1RjSZFCXXbbxVXa-750-291.jpg_Q90.jpg");
        textSliderView3.description("限时一分抢");
    //监听事件
        textSliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView baseSliderView) {

                Toast.makeText(HomeFragment.this.getActivity(),"限时一分抢", Toast.LENGTH_LONG).show();

            }
        });
        mSliderLayout.addSlider(textSliderView);
        mSliderLayout.addSlider(textSliderView2);
        mSliderLayout.addSlider(textSliderView3);


        //效果设置
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

       // mSliderLayout.setCustomAnimation(new DescriptionAnimation());
       mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
        //使用其他的图标效果
        mSliderLayout.setCustomIndicator(indicator);
        //动画
       // mSliderLayout.setCustomAnimation(new DescriptionAnimation());



//轮播图方法
        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.d(TAG,"onPageScrolled");
            }
            @Override
            public void onPageSelected(int i) {
                Log.d(TAG,"onPageSelected");
            }
            @Override
            public void onPageScrollStateChanged(int i) {
                Log.d(TAG,"onPageScrollStateChanged");
            }
        });
    }




    @Override
    public void onDestroy() {
        super.onDestroy();

        mSliderLayout.stopAutoCycle();
    }

}