package com.example.chaoshiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chaoshiapp.model.Dish;
import com.example.chaoshiapp.shoppingcartactivity.ShoppingCartBean;

import org.litepal.LitePal;

import java.util.ArrayList;

public class test extends AppCompatActivity {
    private TextView detail_prices;
    private TextView detail_titles;
    private ImageView  detail_images;
private Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        detail_prices = findViewById(R.id.detail_price);
        detail_titles = findViewById(R.id.detail_title);
        detail_images=findViewById(R.id.detail_image);


        Intent intent = getIntent();
        String data = intent.getStringExtra("name");


        ArrayList<Dish> dishes3 = (ArrayList<Dish>) LitePal.where("dishname like ?", data).find(Dish.class);
        Dish dish = dishes3.get(0);
        double dishPrice = dish.getDishPrice();
        String dishName = dish.getDishName();
        int img = dish.getImg();
        detail_prices.setText(dishPrice+"");
        detail_titles.setText(dishName);
        Glide.with(this).load(img).into(detail_images);

        btn1=findViewById(R.id.gouwu1);
        btn2=findViewById(R.id.gouwu2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(test.this, MainActivity.class);
                intent1.putExtra("id",1);
               // intent1.putExtra("name",data);


                ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
                shoppingCartBean.setImg(img);
                shoppingCartBean.setShoppingName(dishName);
                shoppingCartBean.setPrice(dishPrice);
                shoppingCartBean.setDressSize(20);
                shoppingCartBean.setCount(1);
                shoppingCartBean.save();

                startActivity(intent1);


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(test.this, MainActivity.class);
                intent1.putExtra("id",1);



                ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
                shoppingCartBean.setImg(img);
                shoppingCartBean.setShoppingName(dishName);
                shoppingCartBean.setPrice(dishPrice);
                shoppingCartBean.setDressSize(20);
                shoppingCartBean.setCount(1);
                shoppingCartBean.save();

                startActivity(intent1);


            }
        });





    }

}




