package com.example.chaoshiapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaoshiapp.R;
import com.example.chaoshiapp.imp.ShopCartImp;
import com.example.chaoshiapp.model.Dish;
import com.example.chaoshiapp.model.DishMenu;
import com.example.chaoshiapp.model.ShopCart;
import com.example.chaoshiapp.test;

import java.util.ArrayList;

/**
 * Created by cheng on 16-11-10.
 */
public class RightDishAdapter extends RecyclerView.Adapter {
    private final int MENU_TYPE = 0;
    private final int DISH_TYPE = 1;
    private final int HEAD_TYPE = 2;

    private Context mContext;
    private ArrayList<DishMenu> mMenuList;
    private int mItemCount;
    private ShopCart shopCart;
    private ShopCartImp shopCartImp;

    public RightDishAdapter(Context mContext, ArrayList<DishMenu> mMenuList, ShopCart shopCart){
        this.mContext = mContext;
        this.mMenuList = mMenuList;
        this.mItemCount = mMenuList.size();
        this.shopCart = shopCart;
        for(DishMenu menu:mMenuList){
            mItemCount+=menu.getDishList().size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int sum=0;
        for(DishMenu menu:mMenuList){
            if(position==sum){
                return MENU_TYPE;
            }
            sum+=menu.getDishList().size()+1;
        }
        return DISH_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==MENU_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_menu_item, parent, false);
            MenuViewHolder viewHolder = new MenuViewHolder(view);
            return viewHolder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_dish_item, parent, false);
            DishViewHolder viewHolder = new DishViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(getItemViewType(position)==MENU_TYPE){
            MenuViewHolder menuholder = (MenuViewHolder)holder;
            if(menuholder!=null) {
                menuholder.right_menu_title.setText(getMenuByPosition(position).getMenuName());
                menuholder.right_menu_layout.setContentDescription(position+"");
            }
        }else {
            final DishViewHolder dishholder = (DishViewHolder) holder;
            if (dishholder != null) {

                final Dish dish = getDishByPosition(position);
                dishholder.right_dish_name_tv.setText(dish.getDishName());
                dishholder.right_dish_price_tv.setText(dish.getDishPrice()+"");


                int count = 0;
                if(shopCart.getShoppingSingleMap().containsKey(dish)){
                    count = shopCart.getShoppingSingleMap().get(dish);
                }
                dishholder.right_dish_add_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "你想查看" + dish.getDishName() + "详情", Toast.LENGTH_SHORT).show();
               Intent intent=new Intent(mContext, test.class);
                        intent.putExtra("name",dish.getDishName());
                mContext.startActivity(intent);
                    }
                });


            }
        }
    }

    public DishMenu getMenuByPosition(int position){
        int sum =0;
        for(DishMenu menu:mMenuList){
            if(position==sum){
                return menu;
            }
            sum+=menu.getDishList().size()+1;
        }
        return null;
    }

    public Dish getDishByPosition(int position){
        for(DishMenu menu:mMenuList){
            if(position>0 && position<=menu.getDishList().size()){
                return menu.getDishList().get(position-1);
            }
            else{
                position-=menu.getDishList().size()+1;
            }
        }
        return null;
    }

    public DishMenu getMenuOfMenuByPosition(int position){
        for(DishMenu menu:mMenuList){
            if(position==0)return menu;
            if(position>0 && position<=menu.getDishList().size()){
                return menu;
            }
            else{
                position-=menu.getDishList().size()+1;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    private class MenuViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout right_menu_layout;
        private TextView right_menu_title;
        public MenuViewHolder(View itemView) {
            super(itemView);
            right_menu_layout = (LinearLayout)itemView.findViewById(R.id.right_menu_item);
            right_menu_title = (TextView)itemView.findViewById(R.id.right_menu_tv);
        }
    }

    private class DishViewHolder extends RecyclerView.ViewHolder{
        private TextView right_dish_name_tv;
        private TextView right_dish_price_tv;

        private ImageView right_dish_add_iv;

        public DishViewHolder(View itemView) {
            super(itemView);
            right_dish_name_tv = (TextView)itemView.findViewById(R.id.right_dish_name);
            right_dish_price_tv = (TextView)itemView.findViewById(R.id.right_dish_price);

            right_dish_add_iv = (ImageView)itemView.findViewById(R.id.right_dish_add);
        }

    }
}
