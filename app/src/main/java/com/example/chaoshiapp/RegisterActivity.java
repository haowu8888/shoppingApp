package com.example.chaoshiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chaoshiapp.bean.User;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn2;
    private EditText name, account, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBtn2 = findViewById(R.id.registerBtn2);
        name = findViewById(R.id.name);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        registerBtn2.setOnClickListener(v -> {
            User user = new User();
            user.setName(name.getText().toString());
            user.setAccount(account.getText().toString());
            user.setPassword(password.getText().toString());
            boolean flag = user.save();
            if (flag) {
                Toast.makeText(this, "注册成功，欢迎登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
                RegisterActivity.this.finish();
            } else {
                Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
