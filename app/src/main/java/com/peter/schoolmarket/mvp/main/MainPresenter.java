package com.peter.schoolmarket.mvp.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mvp.login.LoginActivity;
import com.peter.schoolmarket.mvp.test.TestActivity;
import com.peter.schoolmarket.mvp.test.TestFragment;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by PetterChen on 2017/4/20.
 */

public class MainPresenter implements IMainPresenter {

    private AppCompatActivity context;
    private FragmentManager fm;
    TextView toolbarTitle;
    BottomNavigationBar bottomNavigationBar;
    TestFragment testFragment;
    private Realm realmDefault;

    public MainPresenter(AppCompatActivity context,Realm realm) {
        this.context = context;
        this.realmDefault=realm;
        fm = context.getFragmentManager();
        toolbarTitle=(TextView)context.findViewById(R.id.toolbar_title);
        bottomNavigationBar=(BottomNavigationBar)context.findViewById(R.id.bottom_navigation_bar);
    }

    @Override
    public void initMain(MaterialSearchView searchView, View header) {
        initSearchView(searchView);
        initHeader(header);
        initBottomMenu();
    }

    @Override
    public void sideJump(int id) {
        //根据点击按钮设置跳转
        String type = "";
        switch (id){
            case R.id.nav_buying:
                type = "待确认";
                break;
            case R.id.nav_bought:
                type = "已买";
                break;
            case R.id.nav_selling:
                type = "正在卖";
                break;
            case R.id.nav_sold:
                type = "已卖";
                break;
            case R.id.nav_share:
                type = "分享给朋友";
                break;
            case R.id.nav_setting:
                type = "设置";
                break;
            case R.id.nav_exit:
                logout();
                break;
            default:
                break;
        }
        if (type.equals("")){
            return;
        }
        Intent tradeIntent=new Intent(context, TestActivity.class);
        tradeIntent.putExtra("textString",type);
        context.startActivity(tradeIntent);
    }

    public void initSearchView(MaterialSearchView searchView) {
        searchView.setVoiceSearch(false);//关闭声音搜索
        searchView.setCursorDrawable(R.drawable.main_search_cursor);
        searchView.setSuggestions(context.getResources().getStringArray(R.array.query_suggestions));//建议候选词
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                Snackbar.make(context.findViewById(R.id.toolbar_container), "Query: " + query, Snackbar.LENGTH_LONG)
                        .show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    public void initHeader(View headerLayout) {
        User user= LoginInfoExecutor.getUser(context);
        SimpleDraweeView avatarUrl=(SimpleDraweeView) headerLayout.findViewById(R.id.header_portrait);
        TextView username=(TextView) headerLayout.findViewById(R.id.header_user_name);
        TextView phone=(TextView) headerLayout.findViewById(R.id.header_phone);
        avatarUrl.setImageURI(Uri.parse(AppConf.BASE_URL+user.getAvatarUrl()));
        username.setText(user.getUsername());
        phone.setText(user.getPhone());
    }

    public void initBottomMenu() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);//此模式显示文字
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)//点击无水波纹，可以设置为Item添加Badge
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_find, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_trade, "闲货"))
                //.addItem(new BottomNavigationItem(R.drawable.main_bottom_wish, "求购"))
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_msg, "消息"))
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_more, "更多"))
                .setActiveColor(R.color.black)
                .setInActiveColor(R.color.theme_primaryDark)
                .setFirstSelectedPosition(0)
                .initialise();

        toolbarTitle.setText("首页");
        showFragment(0);

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {//这里也可以使用SimpleOnTabSelectedListener
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
                switch (position) {
                    case 0:
                        toolbarTitle.setText("首页");
                        showFragment(0);
                        //此处设置点击后的事件，设置标题栏和fragment
                        break;
                    case 1:
                        toolbarTitle.setText("闲货");
                        showFragment(1);
                        break;
                    case 2:
                        toolbarTitle.setText("消息");
                        showFragment(2);
                        break;
                    case 3:
                        toolbarTitle.setText("更多");
                        showFragment(3);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中
            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中
            }
        });
    }

    public void showFragment(int position) {
        int type = -1;
        FragmentTransaction ft = fm.beginTransaction();
        hideAllFragment(ft);
        switch (position) {
            case 0 : type = 0;
                break;
            case 1 : type = 1;
                break;
            case 2 : type = 2;
                break;
            case 3 : type = 3;
                break;
        }
        if (testFragment != null) {
            ft.show(testFragment);
        } else {
            testFragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putString("textString", "" + type);
            testFragment.setArguments(bundle);
            ft.add(R.id.frame_layout, testFragment);
        }
        ft.commit();
    }

    public void hideAllFragment(FragmentTransaction ft) {
        if (testFragment != null) {
            ft.hide(testFragment);
        }
    }

    private void logout(){
        LoginInfoExecutor.logOut(context);
        Intent loginIntent=new Intent(context, LoginActivity.class);
        context.startActivity(loginIntent);
        context.finish();
    }
}
