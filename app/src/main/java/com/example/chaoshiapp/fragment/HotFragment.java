package com.example.chaoshiapp.fragment;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chaoshiapp.R;
import com.example.chaoshiapp.adapter.LeftMenuAdapter;
import com.example.chaoshiapp.adapter.RightDishAdapter;
import com.example.chaoshiapp.imp.ShopCartImp;
import com.example.chaoshiapp.model.Dish;
import com.example.chaoshiapp.model.DishMenu;
import com.example.chaoshiapp.model.ShopCart;
import com.example.chaoshiapp.wiget.FakeAddImageView;
import com.example.chaoshiapp.wiget.PointFTypeEvaluator;
import com.example.chaoshiapp.wiget.ShopCartDialog;

import org.litepal.LitePal;

import java.util.ArrayList;


/**
 * Created by Ivan on 15/9/22.
 */
public class HotFragment extends Fragment implements LeftMenuAdapter.onItemSelectedListener, ShopCartImp, ShopCartDialog.ShopCartDialogImp {

    private final static String TAG = "MainActivity";
    private RecyclerView leftMenu;//左侧菜单栏
    private RecyclerView rightMenu;//右侧菜单栏
    private TextView headerView;
    private LinearLayout headerLayout;//右侧菜单栏最上面的菜单
    private LinearLayout bottomLayout;
    private DishMenu headMenu;
    private LeftMenuAdapter leftAdapter;
    private RightDishAdapter rightAdapter;
    private ArrayList<DishMenu> dishMenuList;//数据源
    private boolean leftClickType = false;//左侧菜单点击引发的右侧联动
    private ShopCart shopCart;
    //    private FakeAddImageView fakeAddImageView;
    private ImageView shoppingCartView;
    private FrameLayout shopingCartLayout;
    private TextView totalPriceTextView;
    private TextView totalPriceNumTextView;
    private RelativeLayout mainLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_hot, container, false);

        initData();
        initView(view);
        initAdapter();
        return view;
    }

    private void initView(View view) {
        mainLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
        leftMenu = (RecyclerView) view.findViewById(R.id.left_menu);
        rightMenu = (RecyclerView) view.findViewById(R.id.right_menu);
        headerView = (TextView) view.findViewById(R.id.right_menu_tv);
        headerLayout = (LinearLayout) view.findViewById(R.id.right_menu_item);
//        fakeAddImageView = (FakeAddImageView)findViewById(R.id.right_dish_fake_add);
        bottomLayout = (LinearLayout) view.findViewById(R.id.shopping_cart_bottom);
        shoppingCartView = (ImageView) view.findViewById(R.id.shopping_cart);
        shopingCartLayout = (FrameLayout) view.findViewById(R.id.shopping_cart_layout);
        totalPriceTextView = (TextView) view.findViewById(R.id.shopping_cart_total_tv);
        totalPriceNumTextView = (TextView) view.findViewById(R.id.shopping_cart_total_num);

        leftMenu.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rightMenu.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        rightMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(1) == false) {//无法下滑
                    showHeadView();
                    return;
                }
                View underView = null;
                if (dy > 0)
                    underView = rightMenu.findChildViewUnder(headerLayout.getX(), headerLayout.getMeasuredHeight() + 1);
                else
                    underView = rightMenu.findChildViewUnder(headerLayout.getX(), 0);
                if (underView != null && underView.getContentDescription() != null) {
                    int position = Integer.parseInt(underView.getContentDescription().toString());
                    DishMenu menu = rightAdapter.getMenuOfMenuByPosition(position);

                    if (leftClickType || !menu.getMenuName().equals(headMenu.getMenuName())) {
                        if (dy > 0 && headerLayout.getTranslationY() <= 1 && headerLayout.getTranslationY() >= -1 * headerLayout.getMeasuredHeight() * 4 / 5 && !leftClickType) {// underView.getTop()>9
                            int dealtY = underView.getTop() - headerLayout.getMeasuredHeight();
                            headerLayout.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+headerLayout.getTranslationY()+"   "+headerLayout.getBottom()+"  -  "+headerLayout.getMeasuredHeight() );
                        } else if (dy < 0 && headerLayout.getTranslationY() <= 0 && !leftClickType) {
                            headerView.setText(menu.getMenuName());
                            int dealtY = underView.getBottom() - headerLayout.getMeasuredHeight();
                            headerLayout.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+headerLayout.getTranslationY()+"   "+headerLayout.getBottom()+"  -  "+headerLayout.getMeasuredHeight() );
                        } else {
                            headerLayout.setTranslationY(0);
                            headMenu = menu;
                            headerView.setText(headMenu.getMenuName());
                            for (int i = 0; i < dishMenuList.size(); i++) {
                                if (dishMenuList.get(i) == headMenu) {
                                    leftAdapter.setSelectedNum(i);
                                    break;
                                }
                            }
                            if (leftClickType) leftClickType = false;
                            Log.e(TAG, "onScrolled: " + menu.getMenuName());
                        }
                    }
                }
            }
        });

        shopingCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCart(view);

            }
        });
    }

    private void initData() {
        shopCart = new ShopCart();
        dishMenuList = new ArrayList<>();
        LitePal.deleteAll(Dish.class);
        initDatas();
        ArrayList<Dish> dishes = (ArrayList<Dish>) LitePal.where("menuname like ?", "零食").find(Dish.class);
        DishMenu lingshi = new DishMenu("零食", dishes);
        dishMenuList.add(lingshi);

        ArrayList<Dish> dishes1 = (ArrayList<Dish>) LitePal.where("menuname like ?", "水果").find(Dish.class);
        DishMenu shuiguo = new DishMenu("水果", dishes1);
        dishMenuList.add(shuiguo);

        ArrayList<Dish> dishes2 = (ArrayList<Dish>) LitePal.where("menuname like ?", "日常").find(Dish.class);
        DishMenu richang = new DishMenu("日常", dishes2);
        dishMenuList.add(richang);

        ArrayList<Dish> dishes3 = (ArrayList<Dish>) LitePal.where("menuname like ?", "衣服").find(Dish.class);
        DishMenu yifu = new DishMenu("衣服", dishes3);
        dishMenuList.add(yifu);
    }
    private void initDatas() {
        Dish dish11 = new Dish();
        dish11.setDishName("手撕肉干");
        dish11.setDishPrice(50);
        dish11.setDishAmount(10);
        dish11.setImg(R.drawable.xq11);
        dish11.setMenuname("零食");
        dish11.save();

        Dish dish12 = new Dish();
        dish12.setDishName("大碗宽面");
        dish12.setDishPrice(40);
        dish12.setDishAmount(10);
        dish12.setImg(R.drawable.xq12);
        dish12.setMenuname("零食");
        dish12.save();

        Dish dish13 = new Dish();
        dish13.setDishName("华味亨");
        dish13.setDishPrice(14);
        dish13.setDishAmount(10);
        dish13.setImg(R.drawable.xq13);
        dish13.setMenuname("零食");
        dish13.save();

        Dish dish14 = new Dish();
        dish14.setDishName("奶油面包");
        dish14.setDishPrice(22);
        dish14.setDishAmount(10);
        dish14.setImg(R.drawable.xq14);
        dish14.setMenuname("零食");
        dish14.save();

        Dish dish15 = new Dish();
        dish15.setDishName("三只松鼠");
        dish15.setDishPrice(34);
        dish15.setDishAmount(10);
        dish15.setImg(R.drawable.xq15);
        dish15.setMenuname("零食");
        dish15.save();

        Dish dish16 = new Dish();
        dish16.setDishName("旺旺雪饼");
        dish16.setDishPrice(32);
        dish16.setDishAmount(10);
        dish16.setImg(R.drawable.xq16);
        dish16.setMenuname("零食");
        dish16.save();

        Dish dish17 = new Dish();
        dish17.setDishName("特制鱼干");
        dish17.setDishPrice(65);
        dish17.setDishAmount(10);
        dish17.setImg(R.drawable.xq17);
        dish17.setMenuname("零食");
        dish17.save();

        Dish dish18 = new Dish();
        dish18.setDishName("坚果盒子");
        dish18.setDishPrice(34);
        dish18.setDishAmount(10);
        dish18.setImg(R.drawable.xq18);
        dish18.setMenuname("零食");
        dish18.save();

        Dish dish19 = new Dish();
        dish19.setDishName("肉松饼");
        dish19.setDishPrice(78);
        dish19.setDishAmount(10);
        dish19.setImg(R.drawable.xq19);
        dish19.setMenuname("零食");
        dish19.save();











        Dish dish21= new Dish();
        dish21.setDishName("苹果");
        dish21.setDishPrice(24);
        dish21.setDishAmount(10);
        dish21.setImg(R.drawable.xq21);
        dish21.setMenuname("水果");
        dish21.save();

        Dish dish22= new Dish();
        dish22.setDishName("橘子");
        dish22.setDishPrice(12);
        dish22.setDishAmount(10);
        dish22.setImg(R.drawable.xq22);
        dish22.setMenuname("水果");
        dish22.save();

        Dish dish23= new Dish();
        dish23.setDishName("西瓜");
        dish23.setDishPrice(14);
        dish23.setDishAmount(10);
        dish23.setImg(R.drawable.xq23);
        dish23.setMenuname("水果");
        dish23.save();

        Dish dish24= new Dish();
        dish24.setDishName("芒果");
        dish24.setDishPrice(21);
        dish24.setDishAmount(10);
        dish24.setImg(R.drawable.xq24);
        dish24.setMenuname("水果");
        dish24.save();

        Dish dish25= new Dish();
        dish25.setDishName("菠萝");
        dish25.setDishPrice(19);
        dish25.setDishAmount(10);
        dish25.setImg(R.drawable.xq25);
        dish25.setMenuname("水果");
        dish25.save();

        Dish dish26= new Dish();
        dish26.setDishName("香蕉");
        dish26.setDishPrice(7);
        dish26.setDishAmount(10);
        dish26.setImg(R.drawable.xq26);
        dish26.setMenuname("水果");
        dish26.save();

        Dish dish27= new Dish();
        dish27.setDishName("香梨");
        dish27.setDishPrice(6);
        dish27.setDishAmount(10);
        dish27.setImg(R.drawable.xq27);
        dish27.setMenuname("水果");
        dish27.save();

        Dish dish28= new Dish();
        dish28.setDishName("柚子");
        dish28.setDishPrice(30);
        dish28.setDishAmount(10);
        dish28.setImg(R.drawable.xq28);
        dish28.setMenuname("水果");
        dish28.save();

        Dish dish29= new Dish();
        dish29.setDishName("柿子");
        dish29.setDishPrice(4);
        dish29.setDishAmount(10);
        dish29.setImg(R.drawable.xq29);
        dish29.setMenuname("水果");
        dish29.save();







        Dish dish31 = new Dish();
        dish31.setDishName("碗筷");
        dish31.setDishPrice(37);
        dish31.setDishAmount(10);
        dish31.setImg(R.drawable.xq31);
        dish31.setMenuname("日常");
        dish31.save();

        Dish dish32 = new Dish();
        dish32.setDishName("洗衣液");
        dish32.setDishPrice(24);
        dish32.setDishAmount(10);
        dish32.setImg(R.drawable.xq32);
        dish32.setMenuname("日常");
        dish32.save();

        Dish dish33 = new Dish();
        dish33.setDishName("垃圾袋");
        dish33.setDishPrice(6);
        dish33.setDishAmount(10);
        dish33.setImg(R.drawable.xq33);
        dish33.setMenuname("日常");
        dish33.save();

        Dish dish34 = new Dish();
        dish34.setDishName("拖把");
        dish34.setDishPrice(57);
        dish34.setDishAmount(10);
        dish34.setImg(R.drawable.xq34);
        dish34.setMenuname("日常");
        dish34.save();

        Dish dish35 = new Dish();
        dish35.setDishName("扫把");
        dish35.setDishPrice(56);
        dish35.setDishAmount(10);
        dish35.setImg(R.drawable.xq35);
        dish35.setMenuname("日常");
        dish35.save();

        Dish dish36 = new Dish();
        dish36.setDishName("纸巾");
        dish36.setDishPrice(7);
        dish36.setDishAmount(10);
        dish36.setImg(R.drawable.xq36);
        dish36.setMenuname("日常");
        dish36.save();

        Dish dish37 = new Dish();
        dish37.setDishName("拖鞋");
        dish37.setDishPrice(16);
        dish37.setDishAmount(10);
        dish37.setImg(R.drawable.xq37);
        dish37.setMenuname("日常");
        dish37.save();

        Dish dish38 = new Dish();
        dish38.setDishName("雨伞");
        dish38.setDishPrice(24);
        dish38.setDishAmount(10);
        dish38.setImg(R.drawable.xq38);
        dish38.setMenuname("日常");
        dish38.save();

        Dish dish39 = new Dish();
        dish39.setDishName("脸盆");
        dish39.setDishPrice(15);
        dish39.setDishAmount(10);
        dish39.setImg(R.drawable.xq39);
        dish39.setMenuname("日常");
        dish39.save();









        Dish dish41 = new Dish();
        dish41.setDishName("大衣");
        dish41.setDishPrice(90);
        dish41.setDishAmount(10);
        dish41.setImg(R.drawable.xq41);
        dish41.setMenuname("衣服");
        dish41.save();

        Dish dish42 = new Dish();
        dish42.setDishName("衬衫");
        dish42.setDishPrice(70);
        dish42.setDishAmount(10);
        dish42.setImg(R.drawable.xq42);
        dish42.setMenuname("衣服");
        dish42.save();

        Dish dish43 = new Dish();
        dish43.setDishName("棉衣");
        dish43.setDishPrice(80);
        dish43.setDishAmount(10);
        dish43.setImg(R.drawable.xq43);
        dish43.setMenuname("衣服");
        dish43.save();

        Dish dish44 = new Dish();
        dish44.setDishName("马甲");
        dish44.setDishPrice(50);
        dish44.setDishAmount(10);
        dish44.setImg(R.drawable.xq44);
        dish44.setMenuname("衣服");
        dish44.save();

        Dish dish45 = new Dish();
        dish45.setDishName("裙子");
        dish45.setDishPrice(67);
        dish45.setDishAmount(10);
        dish45.setImg(R.drawable.xq45);
        dish45.setMenuname("衣服");
        dish45.save();

        Dish dish46 = new Dish();
        dish46.setDishName("皮衣");
        dish46.setDishPrice(78);
        dish46.setDishAmount(10);
        dish46.setImg(R.drawable.xq46);
        dish46.setMenuname("衣服");
        dish46.save();

        Dish dish47 = new Dish();
        dish47.setDishName("短袖");
        dish47.setDishPrice(36);
        dish47.setDishAmount(10);
        dish47.setImg(R.drawable.xq47);
        dish47.setMenuname("衣服");
        dish47.save();

        Dish dish48 = new Dish();
        dish48.setDishName("短裤");
        dish48.setDishPrice(43);
        dish48.setDishAmount(10);
        dish48.setImg(R.drawable.xq48);
        dish48.setMenuname("衣服");
        dish48.save();

        Dish dish49 = new Dish();
        dish49.setDishName("长裤");
        dish49.setDishPrice(92);
        dish49.setDishAmount(10);
        dish49.setImg(R.drawable.xq49);
        dish49.setMenuname("衣服");
        dish49.save();





    }

    private void initAdapter() {
        leftAdapter = new LeftMenuAdapter(this.getActivity(), dishMenuList);
        rightAdapter = new RightDishAdapter(this.getActivity(), dishMenuList, shopCart);
        rightMenu.setAdapter(rightAdapter);
        leftMenu.setAdapter(leftAdapter);
        leftAdapter.addItemSelectedListener(this);
        rightAdapter.setShopCartImp(this);
        initHeadView();
    }

    private void initHeadView() {
        headMenu = rightAdapter.getMenuOfMenuByPosition(0);
        headerLayout.setContentDescription("0");
        headerView.setText(headMenu.getMenuName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        leftAdapter.removeItemSelectedListener(this);
    }

    private void showHeadView() {
        headerLayout.setTranslationY(0);
        View underView = rightMenu.findChildViewUnder(headerView.getX(), 0);
        if (underView != null && underView.getContentDescription() != null) {
            int position = Integer.parseInt(underView.getContentDescription().toString());
            DishMenu menu = rightAdapter.getMenuOfMenuByPosition(position + 1);
            headMenu = menu;
            headerView.setText(headMenu.getMenuName());
            for (int i = 0; i < dishMenuList.size(); i++) {
                if (dishMenuList.get(i) == headMenu) {
                    leftAdapter.setSelectedNum(i);
                    break;
                }
            }
        }
    }

    @Override
    public void onLeftItemSelected(int position, DishMenu menu) {
        int sum = 0;
        for (int i = 0; i < position; i++) {
            sum += dishMenuList.get(i).getDishList().size() + 1;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) rightMenu.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(sum, 0);
        leftClickType = true;
    }

    @Override
    public void add(View view, int position) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        shoppingCartView.getLocationInWindow(cartLocation);
        rightMenu.getLocationInWindow(recycleLocation);

        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();

        startP.x = addLocation[0];
        startP.y = addLocation[1] - recycleLocation[1];
        endP.x = cartLocation[0];
        endP.y = cartLocation[1] - recycleLocation[1];
        controlP.x = endP.x;
        controlP.y = startP.y;

        final FakeAddImageView fakeAddImageView = new FakeAddImageView(this.getActivity());
        mainLayout.addView(fakeAddImageView);
        fakeAddImageView.setImageResource(R.drawable.ic_add_circle_blue_700_36dp);
        fakeAddImageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        fakeAddImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        fakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(fakeAddImageView, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new AccelerateInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fakeAddImageView.setVisibility(View.GONE);
                mainLayout.removeView(fakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(shoppingCartView, "scaleX", 0.6f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(shoppingCartView, "scaleY", 0.6f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
        animatorSet.setDuration(800);
        animatorSet.start();

        showTotalPrice();
    }

    @Override
    public void remove(View view, int position) {
        showTotalPrice();
    }

    private void showTotalPrice() {
        if (shopCart != null && shopCart.getShoppingTotalPrice() > 0) {
            totalPriceTextView.setVisibility(View.VISIBLE);
            totalPriceTextView.setText("￥ " + shopCart.getShoppingTotalPrice());
            totalPriceNumTextView.setVisibility(View.VISIBLE);
            totalPriceNumTextView.setText("" + shopCart.getShoppingAccount());

        } else {
            totalPriceTextView.setVisibility(View.GONE);
            totalPriceNumTextView.setVisibility(View.GONE);
        }
    }

    private void showCart(View view) {
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            ShopCartDialog dialog = new ShopCartDialog(this.getActivity(), shopCart, R.style.cartdialog);
            Window window = dialog.getWindow();
            dialog.setShopCartDialogImp(this);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            params.dimAmount = 0.5f;
            window.setAttributes(params);
        }
    }

    @Override
    public void dialogDismiss() {
        showTotalPrice();
        rightAdapter.notifyDataSetChanged();
    }


}
