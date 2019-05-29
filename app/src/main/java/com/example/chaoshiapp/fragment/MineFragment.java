package com.example.chaoshiapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chaoshiapp.Fragment4;
import com.example.chaoshiapp.LoginActivity;
import com.example.chaoshiapp.R;
import com.example.chaoshiapp.bean.User;

import org.litepal.LitePal;


/**
 * Created by Ivan on 15/9/22.
 */
public class MineFragment extends Fragment{
    private  ImageView dengluwan;
private ImageView tuichu;
private ImageView ditu;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_myself, container, false);

        SharedPreferences preferences = view.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        int user_id = preferences.getInt("user_id", 0);

        User user = LitePal.find(User.class, user_id);
        TextView username = view.findViewById(R.id.username);
        username.setText(user.getName());

        dengluwan=view.findViewById(R.id.dengluwan);

        String gifUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558867108057&di=21c7bc00884dd82ca7aca7110142fa71&imgtype=0&src=http%3A%2F%2Fimg.mp.sohu.com%2Fupload%2F20170722%2F90b6ef3ed69348738e017d8b7cc3a577.png";
        Glide.with( this.getActivity())
                .load( gifUrl )
                .placeholder(R.drawable.denglu )
                .error( R.drawable.denglu )
                .into( dengluwan );

        tuichu=view.findViewById(R.id.tuichu);
        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MineFragment.this.getActivity(), LoginActivity.class);
                intent.putExtra("tuichu",3);
                startActivity(intent);

            }
        });

        ditu=view.findViewById(R.id.adress);
        ditu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MineFragment.this.getActivity(), Fragment4.class);
                startActivity(intent);

            }
        });

        return view;
    }
}
