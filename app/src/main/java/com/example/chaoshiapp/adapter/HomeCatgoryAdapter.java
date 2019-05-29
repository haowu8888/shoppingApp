package com.example.chaoshiapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaoshiapp.R;
import com.example.chaoshiapp.bean.HomeCategory;

import java.util.List;

/**
 * Created by Ivan on 15/9/30.
 */
//首页适配器定义
public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder> {


//定义下标返回值
    private  static int VIEW_TYPE_L=0;
    private  static int VIEW_TYPE_R=1;

    private LayoutInflater mInflater;

    private List<HomeCategory> mDatas;
//构造方法
    public HomeCatgoryAdapter(List<HomeCategory> datas)
    {
        mDatas = datas;


    }



    //用下标值type判断返回展示格式就行了
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {

        //实例化
        mInflater = LayoutInflater.from(viewGroup.getContext());
        //判断首页展示是两格式中的哪一个
        if(type == VIEW_TYPE_R){

            return  new ViewHolder(mInflater.inflate(R.layout.template_home_cardview,viewGroup,false));
        }

        return  new ViewHolder(mInflater.inflate(R.layout.template_home_cardview2,viewGroup,false));
    }
//绑定数据，获取值
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        HomeCategory category = mDatas.get(i);
        viewHolder.textTitle.setText(category.getName());
        viewHolder.imageViewBig.setImageResource(category.getImgBig());
        viewHolder.imageViewSmallTop.setImageResource(category.getImgSmallTop());
        viewHolder.imageViewSmallBottom.setImageResource(category.getImgSmallBottom());

        viewHolder.textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "请去热卖查看购买", Toast.LENGTH_SHORT).show();

            }
        });
        viewHolder.imageViewBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "请去热卖查看购买", Toast.LENGTH_SHORT).show();

            }
        });
        viewHolder.imageViewSmallTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "请去热卖查看购买", Toast.LENGTH_SHORT).show();

            }
        });
        viewHolder.imageViewSmallBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "请去热卖查看购买", Toast.LENGTH_SHORT).show();

            }
        });


    }

//返回集合长度
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

//position是首页展示listviewitem的下标
    @Override
    public int getItemViewType(int position) {
//判断单双行下标
        if(position % 2==0){
            return  VIEW_TYPE_R;
        }
        else return VIEW_TYPE_L;


    }
//首页展示的连两个布局除了位置基本一样 所以只要一个ViewHolder
    static  class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);

        }

}



}
