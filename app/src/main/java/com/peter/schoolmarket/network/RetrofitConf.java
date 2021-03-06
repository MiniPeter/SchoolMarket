package com.peter.schoolmarket.network;

/**
 * Created by PetterChen on 2017/4/13.
 */

public interface RetrofitConf {
    int DEFAULT_TIMEOUT = 5;

    //-----------------------------显示图片------------------------
    String base_img = "SchoolMarketServer/images/";

    //-----------------------------用户相关---------------------------
    String login="SchoolMarketServer/Login";//用户登录+
    String register="SchoolMarketServer/Register";//用户注册+
    String register_next="SchoolMarketServer/RegisterNext";//用户注册下一步，设置学校，手机号+
    String get_users="SchoolMarketServer/GetUsers";
    String get_msgs = "SchoolMarketServer/GetMsgs";

    //----------------------------notice----------------------------
    String get_notices_data = "SchoolMarketServer/GetNotices";//获取notice 数据
    String create_notice = "SchoolMarketServer/AddNotice"; //添加notice
    String get_my_notices = "SchoolMarketServer/GetMyNotices";
    String delete_notice = "SchoolMarketServer/DeleteNotice";
    String get_search_notices = "SchoolMarketServer/GetSearchNotices";//取得搜索数据


    //----------------------------商品相关----------------------------
    String create_trade="SchoolMarketServer/AddTrade";//发布商品
    String get_trades="SchoolMarketServer/GetTrades";//获取该学校的商品列表
    String get_tag_trades="SchoolMarketServer/GetTagTrades";//获取该分类下所有商品列表
    String get_drawer_trades = "SchoolMarketServer/GetDrawerTrades";
    String place_order = "SchoolMarketServer/PlaceOrder";//下单
    String confirm_trade = "SchoolMarketServer/ConfirmTrade";//确认收货
    String confirm_money = "SchoolMarketServer/ConfirmMoney";//确认收款
    String cancel_order = "SchoolMarketServer/CancelOrder";//取消订单
    String cancel_trade = "SchoolMarketServer/CancelTrade";//商品下架
    String get_search_trades = "SchoolMarketServer/GetSearchTrades";//取得搜索数据
    String get_tag_search_trades = "SchoolMarketServer/GetTagSearchTrades";

}
