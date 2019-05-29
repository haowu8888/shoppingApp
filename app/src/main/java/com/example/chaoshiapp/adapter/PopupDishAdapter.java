package com.example.chaoshiapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaoshiapp.R;
import com.example.chaoshiapp.imp.ShopCartImp;
import com.example.chaoshiapp.model.Dish;
import com.example.chaoshiapp.model.ShopCart;

import java.util.ArrayList;

/**
 * Created by cheng on 16-12-23.
 */
//适配器
public class PopupDishAdapter extends RecyclerView.Adapter{

    private static String TAG = "PopupDishAdapter";
    private ShopCart shopCart;
    private Context context;
    private int itemCount;
    private ArrayList<Dish> dishList;
    private ShopCartImp shopCartImp;

    public PopupDishAdapter(Context context, ShopCart shopCart){
        this.shopCart = shopCart;
        this.context = context;
        this.itemCount = shopCart.getDishAccount();
        this.dishList = new ArrayList<>();
        dishList.addAll(shopCart.getShoppingSingleMap().keySet());
        Log.e(TAG, "PopupDishAdapter: "+this.itemCount );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_dish_item, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DishViewHolder dishholder = (DishViewHolder)holder;
        final Dish dish = getDishByPosition(position);
        if(dish!=null) {
            dishholder.right_dish_name_tv.setText(dish.getDishName());
            dishholder.right_dish_price_tv.setText(dish.getDishPrice() + "");

            int num = shopCart.getShoppingSingleMap().get(dish);

            dishholder.right_dish_add_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  Toast.makeText(view.getContext(), "你想与" + dish.getDishName() + "聊天", Toast.LENGTH_SHORT).show();

                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return this.itemCount;
    }

    public Dish getDishByPosition(int position){
       return dishList.get(position);
    }

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
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
