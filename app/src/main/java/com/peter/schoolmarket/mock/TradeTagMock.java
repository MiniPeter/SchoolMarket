package com.peter.schoolmarket.mock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PetterChen on 2017/4/29.
 */

public class TradeTagMock {

    /**
     *
     *
     *{
     *  "authorImg": [
     *      "https://jcalaz.github.io/img/sort_clothes.jpeg"
     *  ],
     *  "imgUrls": [
     *      "https://jcalaz.github.io/img/sort_body.jpg"
     *  ],
     *  "authorName": "安琪"
     *  "nowPrice": 9,
     *  "status": 0,
     *  "title": "3瓶脉动饮料"authorPhone
     *},
     */
    public Result<List<Trade>> getTrades(){
        String jsonStr="[\n" +
                "  {\n" +
                "    \"id\": \"0001\",\n" +
                "    \"authorImg\": \"http://opeuvb611.bkt.clouddn.com/sort_avater_cluo.jpg\",\n" +
                "    \"imgUrls\": \"http://opeuvb611.bkt.clouddn.com/apple_phone.jpg\",\n" +
                "    \"authorName\": \"tom\",\n" +
                "    \"nowPrice\": 2000,\n" +
                "    \"originalPrice\": 5000,\n" +
                "    \"authorPhone\": \"18202429136\",\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"苹果手机\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"0002\",\n" +
                "    \"authorImg\": \"http://opeuvb611.bkt.clouddn.com/sort_clothes.jpeg\",\n" +
                "    \"imgUrls\": \"http://opeuvb611.bkt.clouddn.com/battledore.jpg\",\n" +
                "    \"authorName\": \"peter\",\n" +
                "    \"nowPrice\": 50,\n" +
                "    \"originalPrice\": 150,\n" +
                "    \"authorPhone\": \"18202429136\",\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"羽毛球拍\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"0003\",\n" +
                "    \"authorImg\": \"http://opeuvb611.bkt.clouddn.com/sort_body.jpg\",\n" +
                "    \"imgUrls\": \"http://opeuvb611.bkt.clouddn.com/book.jpg\",\n" +
                "    \"authorName\": \"nice\",\n" +
                "    \"nowPrice\": 20,\n" +
                "    \"originalPrice\": 50,\n" +
                "    \"authorPhone\": \"18202429136\",\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"传统民谣书系\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"0004\",\n" +
                "    \"authorImg\": \"http://opeuvb611.bkt.clouddn.com/sort_avater_cluo.jpg\",\n" +
                "    \"imgUrls\": \"http://opeuvb611.bkt.clouddn.com/mac_computer.jpg\",\n" +
                "    \"authorName\": \"tom\",\n" +
                "    \"nowPrice\": 5000,\n" +
                "    \"originalPrice\": 14000,\n" +
                "    \"authorPhone\": \"18202429136\",\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"苹果电脑\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"0005\",\n" +
                "    \"authorImg\": \"http://opeuvb611.bkt.clouddn.com/sort_clothes.jpeg\",\n" +
                "    \"imgUrls\": \"http://opeuvb611.bkt.clouddn.com/table_lamp.jpeg\",\n" +
                "    \"authorName\": \"nice\",\n" +
                "    \"nowPrice\": 30,\n" +
                "    \"originalPrice\": 100,\n" +
                "    \"authorPhone\": \"18202429136\",\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"台灯\"\n" +
                "  }\n" +
                "]";
        List<Trade> trades= new Gson().fromJson(jsonStr, new TypeToken<List<Trade>>(){}.getType());
        Result<List<Trade>> result=new Result<>();
        result.setCode(100);
        result.setMsg("");
        result.setData(trades);
        return result;
    }

    public Result<String> getReleaseTradeResult() {
        String json="{\"code\":100,\"msg\":\"操作成功\"}";
        return new Gson().fromJson(json,  new TypeToken<Result<String>>() {}.getType());
    }

    public Result<Trade> getTradeDetail(){
        Trade trade=new Trade();
        trade.setTitle("Mac 电脑");
        /*User user=new User();
        user.setId("002");
        user.setUsername("admin");
        user.setAvatarUrl("https://jcalaz.github.io/img/sort_rent.jpg");
        trade.setAuthor(user);*/
        trade.setAuthorId("002");
        trade.setAuthorName("admin2");
        trade.setAuthorImg("https://jcalaz.github.io/img/sort_rent.jpg");
        trade.setDescribe("2014年在京东购买，还在保修期，I7CPU，独立显卡，8成新");
        trade.setNowPrice(3000);
        trade.setOriginalPrice(8000);
        trade.setCreateTime(System.currentTimeMillis());
        trade.setImgUrls("https://jcalaz.github.io/img/sort_computer.jpg");
        Result<Trade> result=new Result<>();
        result.setCode(100);
        result.setMsg("");
        result.setData(trade);
        return result;
    }
}
