package com.example.chaoshiapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FirstActivity extends AppCompatActivity {
    private ImageView jiazai;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);


        jiazai=findViewById(R.id.jiazai);
        String gifUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558867572119&di=159326adf9e773203c530839e8a84305&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171116%2Ff1f09c16f4a64faaad782b296d1edef9.gif";
        Glide.with(this)
                .load( gifUrl )
                .placeholder(R.drawable.kongbai)
                .error(R.drawable.kongbai)
                .into( jiazai );


        SharedPreferences preferences = FirstActivity.this.getSharedPreferences("save", Context.MODE_PRIVATE);
        String state = preferences.getString("save_all", "false");
        if ("false".equals(state))
            initSave();
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(FirstActivity.this, LoginActivity.class);
            FirstActivity.this.startActivity(mainIntent);
            FirstActivity.this.finish();
        }, 2000);
    }

    private void initSave(){

        SharedPreferences preferences = FirstActivity.this.getSharedPreferences("save", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //如果不能找到Editor接口。尝试使用 SharedPreferences.Editor
        editor.putString("save_all", "true");
        //将保存的状态保存到缓存中
        editor.apply();
    }
}
