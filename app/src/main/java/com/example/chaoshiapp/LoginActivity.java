package com.example.chaoshiapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chaoshiapp.bean.User;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private TextView registerBtn;
    private EditText account, password;
    private ImageView degnlu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
degnlu=findViewById(R.id.denglu);

        Intent intent1 = getIntent();
        int tuichu = intent1.getIntExtra("tuichu",0);


        String gifUrl = "http://img3.imgtn.bdimg.com/it/u=158094193,417757867&fm=214&gp=0.jpg";
        Glide.with(this)
                .load( gifUrl )
                .placeholder(R.drawable.denglu )
                .error( R.drawable.denglu )
                .into( degnlu );



        //SharedPreferences 保存数据的实现代码
        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id", 0);



        if (tuichu==3){

        }
        else {
            if (user_id != 0) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                //关闭当前登录界面，否则在主界面按后退键还会回到登录界面
                LoginActivity.this.finish();
            }
        }
        loginBtn.setOnClickListener(v -> {
            List<User> user = LitePal.where("account = ? and password = ?", account.getText().toString(), password.getText().toString()).find(User.class);
            if (user.size() == 0) {
                Toast.makeText(this, "登录失败，用户名或密码错误", Toast.LENGTH_SHORT).show();
            } else {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                //如果不能找到Editor接口。尝试使用 SharedPreferences.Editor
                editor.putInt("user_id", user.get(0).getId());
                //我将用户信息保存到其中，你也可以保存登录状态
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

}
