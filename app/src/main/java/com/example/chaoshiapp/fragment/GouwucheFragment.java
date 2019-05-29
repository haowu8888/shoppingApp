package com.example.chaoshiapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chaoshiapp.MainActivity;
import com.example.chaoshiapp.R;
import com.example.chaoshiapp.shoppingcartactivity.ShoppingCartAdapter;
import com.example.chaoshiapp.shoppingcartactivity.ShoppingCartBean;
import com.example.chaoshiapp.widget.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Ivan on 15/9/22.
 */
public class GouwucheFragment extends Fragment implements View.OnClickListener
        , ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface{
    Button btnBack;
    //全选
    CheckBox ckAll;
    //总额
    TextView tvShowPrice;
    //结算
    TextView tvSettlement;
    //编辑
    TextView btnEdit;//tv_edit

    ListView list_shopping_cart;
    ImageView ivShowPic;

    private ShoppingCartAdapter shoppingCartAdapter;
    private boolean flag = false;
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    private boolean mSelect;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private boolean isGetData = false;//刷新用的
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gouwuche, container, false);
        initView(view);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this.getActivity()));
    return view;

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
//            GetData();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }


    private void initView(View view) {

        btnBack= (Button) view.findViewById(R.id.btn_back);
        ckAll= (CheckBox) view.findViewById(R.id.ck_all);
        tvShowPrice= (TextView) view.findViewById(R.id.tv_show_price);
        tvSettlement= (TextView) view.findViewById(R.id.tv_settlement);
        btnEdit= (TextView) view.findViewById(R.id.bt_header_right);
        list_shopping_cart= (ListView) view.findViewById(R.id.list_shopping_cart);
        ivShowPic = (ImageView) view.findViewById(R.id.iv_show_pic);
        btnEdit.setOnClickListener(this);
        ckAll.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        initData();
    }

    //初始化数据
    protected void initData() {
        List<ShoppingCartBean> shoppingCartBeans = LitePal.findAll(ShoppingCartBean.class);
        for (int i = 0; i <shoppingCartBeans.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeans.get(i);

            shoppingCartBean.setShoppingName(shoppingCartBean.getShoppingName());
            shoppingCartBean.setDressSize(shoppingCartBean.getDressSize());
            shoppingCartBean.setId(i);
            shoppingCartBean.setPrice(shoppingCartBean.getPrice());
            shoppingCartBean.setCount(shoppingCartBean.getCount());

            if (shoppingCartBean.getShoppingName().equals("手撕肉干")) {
                shoppingCartBean.setImageUrl("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3145048822,975095942&fm=26&gp=0.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("大碗宽面")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i4/3693245722/O1CN01ks7Ixp1s8iosDv834_!!3693245722.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("华味亨")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i1/750756158/O1CN01ob50WD1vMPTipj2LM_!!750756158.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("奶油面包")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i3/2455221099/TB2ozQJjwJlpuFjSspjXXcT.pXa_!!2455221099.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("三只松鼠")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i1/880734502/O1CN01dRZ3jT1j7xZ6j5y02_!!0-item_pic.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("旺旺雪饼")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i4/1588913126/O1CN01tiDdPC1YxkaPibxn4_!!1588913126.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("特制鱼干")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i1/1808120453/TB23.5ozSFmpuFjSZFrXXayOXXa_!!1808120453.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("坚果盒子")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i3/2094823701/O1CN01FMPsUJ1dD6K0zXWdv_!!2094823701.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("肉松饼")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i2/880734502/O1CN01JchHyN1j7xb0RKTNG_!!880734502.jpg");
            }

            if (shoppingCartBean.getShoppingName().equals("苹果")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i2/2939922051/O1CN01V7vIdm1R1OrR3DDGv_!!2939922051.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("橘子")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i4/3580384160/O1CN01zUygWv1gbK8lnBtv2_!!3580384160.png");
            }
            if (shoppingCartBean.getShoppingName().equals("西瓜")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i4/2939922051/O1CN01WKDIgV1R1OrSc5SOZ_!!2939922051.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("芒果")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i2/3015214310/O1CN01OTfjCD1hi1XT7iLNH_!!3015214310.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("菠萝")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i1/4066692754/O1CN01ZzQkKQ1WDNGfT2fhb_!!4066692754.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("香梨")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i3/1758843131/O1CN01hpiR4L1Z02c5NWNza_!!1758843131.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("柚子")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i4/2627785630/O1CN0103puA81rSaNM9JolH_!!2627785630.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("柿子")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i3/2891705366/TB2.sEcXtfJ8KJjy0FeXXXKEXXa_!!2891705366.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("香蕉")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i4/3448645475/O1CN01kxKjd21qJaxR5y87j_!!3448645475.jpg");
            }

            if (shoppingCartBean.getShoppingName().equals("碗筷")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i3/3284690895/O1CN014XIFpC1ITx0avBDn4_!!3284690895.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("洗衣液")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i1/916243692/O1CN01nQ0MDi1d8ymXe2wpU_!!0-item_pic.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("垃圾袋")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i1/725677994/O1CN01e0arFV28vIgG8erAj_!!725677994.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("拖把")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i4/651093327/O1CN01BrtHD91aRoCvsNUzl_!!651093327.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("扫把")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i2/916730030/TB27jrBX8bM8KJjSZFFXXaynpXa_!!916730030.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("纸巾")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i1/725677994/O1CN01cLrfbr28vIgIunATN_!!725677994.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("拖鞋")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i2/2939664514/TB2rZe.s4SYBuNjSsphXXbGvVXa_!!2939664514.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("雨伞")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i3/832806898/O1CN012A7vQj20pKXRAKPww_!!832806898.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("脸盆")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i1/2068437008/O1CN01NTRK7z21di8GZdliE_!!2068437008.jpg");
            }

            if (shoppingCartBean.getShoppingName().equals("大衣")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i4/2891862917/O1CN015xo1K61XQ1qD5zR1a_!!0-item_pic.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("衬衫")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i3/2077774836/TB1hdaduuuSBuNjSsziXXbq8pXa_!!0-item_pic.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("棉衣")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i4/923679625/O1CN01tW84Xc2KyIeNzUmIt_!!0-item_pic.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("马甲")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i3/717204128/TB2bZe.jWSWBuNjSsrbXXa0mVXa_!!717204128-0-item_pic.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("裙子")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i2/2228361831/O1CN01VgkLQG1POdhllw9NZ_!!0-item_pic.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("皮衣")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i2/752378797/O1CN01rXFvxj2Er4hrICWMV_!!752378797.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("短袖")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i3/2270438858/O1CN018HeeFV2FJ0rYE3U13_!!0-item_pic.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("短裤")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i4/385132127/O1CN01C5jGAf1RaCxnFvzfc_!!0-item_pic.jpg");
            }
            if (shoppingCartBean.getShoppingName().equals("长裤")) {
                shoppingCartBean.setImageUrl("http://img.alicdn.com/bao/uploaded/i1/2214592689/O1CN01c0BXQu1VjbUpGE56y_!!0-item_pic.jpg");
            }


           shoppingCartBean.setImg(shoppingCartBean.getImg());
            shoppingCartBeanList.add(shoppingCartBean);


        }





        shoppingCartAdapter = new ShoppingCartAdapter(this.getActivity());
        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);
        list_shopping_cart.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.setShoppingCartBeanList(shoppingCartBeanList);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全选按钮
            case R.id.ck_all:
                if (shoppingCartBeanList.size() != 0) {
                    if (ckAll.isChecked()) {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(true);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(false);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.bt_header_right:
                flag = !flag;
                if (flag) {
                    btnEdit.setText("完成");
                    shoppingCartAdapter.isShow(false);
                } else {
                    btnEdit.setText("编辑");
                    shoppingCartAdapter.isShow(true);

                }
                break;
            case R.id.tv_settlement: //结算
                lementOnder();
                LitePal.deleteAll(ShoppingCartBean.class);
                Intent intent=new Intent(this.getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_back:
                onDestroyView();
                break;
        }


    }


     //结算订单、支付

    private void lementOnder() {
        //选中的需要提交的商品清单
        for (ShoppingCartBean bean:shoppingCartBeanList ){
            boolean choosed = bean.isChoosed();
            if (choosed){
                String shoppingName = bean.getShoppingName();
                int count = bean.getCount();
                double price = bean.getPrice();
                int size = bean.getDressSize();
                String attribute = bean.getAttribute();
                int id = bean.getId();
                Log.d(TAG,id+"----id---"+shoppingName+"---"+count+"---"+price+"--size----"+size+"--attr---"+attribute);
            }
        }
        ToastUtil.showL(this.getActivity(),"总价："+totalPrice);

        //跳转到支付界面
    }
     //单选

    @Override
    public void checkGroup(int position, boolean isChecked) {
        shoppingCartBeanList.get(position).setChoosed(isChecked);
        if (isAllCheck())
            ckAll.setChecked(true);
        else
            ckAll.setChecked(false);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
    // 遍历list集合

    private boolean isAllCheck() {

        for (ShoppingCartBean group : shoppingCartBeanList) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }
    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isChoosed()) {
                totalCount++;
                totalPrice += shoppingCartBean.getPrice() * shoppingCartBean.getCount();

            }
        }


       tvShowPrice.setText("合计:" + totalPrice);
        tvSettlement.setText("结算(" + totalCount + ")");
    }
    /**
     * 增加
     *  position      组元素位置
     *  showCountView 用于展示变化后数量的View
     * isChecked     子元素选中与否
     */
    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        int currentCount = shoppingCartBean.getCount();
        currentCount++;
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
    /**
     * 删减
     *position      组元素位置
     * showCountView 用于展示变化后数量的View
     * isChecked     子元素选中与否
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        int currentCount = shoppingCartBean.getCount();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
    /**
     * 删除
     *position
     */
    @Override
    public void childDelete(int position) {
        shoppingCartBeanList.remove(position);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }






}



