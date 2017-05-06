package com.peter.schoolmarket.mvp.find;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.mvp.trade.detail.TradeDetailActivity;
import com.peter.schoolmarket.util.ResultInterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by PetterChen on 2017/4/28.
 */

public class FindPresenter implements IFindPresenter, IGainListener {

    private IFindModel model;
    private IFindView view;
    private Context context;

    public FindPresenter(Context context,IFindView view) {
        this.context = context;
        this.view = view;
        this.model=new FindModel();
    }

    @Override
    public void initView(Realm realm) {

        RealmQuery<Trade> query =  realm.where(Trade.class);
        RealmResults<Trade> results = query.findAll();
        List<Trade> data =  realm.copyFromRealm(results);

        if (data.size()>0){
            initList(data);
        }else {
            //这里可以添加一个加载窗口
            view.showProgress();
            model.tradesDataReq(this, 0, realm);
        }
    }

    @Override
    public void refreshView(Realm realm) {
        //进行网络请求，查看是否更新数据
        //view.showProgress();
        model.tradesDataReq(this, 0, realm);
    }

    public void initList(final List<Trade> trades) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Trade>(context,trades, R.layout.find_item) {
            @Override
            public void convert(RecyclerViewHolder holder, Trade item) {
                holder.setText(R.id.deal_title,item.getTitle());
                holder.setFrescoImg(R.id.deal_img, Uri.parse(item.getImgUrls()));
                holder.setFrescoImg(R.id.author_img,Uri.parse(item.getAuthorImg()));
                holder.setText(R.id.author_name,item.getAuthorName());
                holder.setText(R.id.deal_price,"￥ "+item.getNowPrice());
            }
        };
        view.loadDataSuccess(adapter);
        adapter.setClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Trade item=trades.get(position);

                //跳转到分类商品详情页面
                //Toast.makeText(context, "jump", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context, TradeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("trade", item);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public void onReqComplete(Result<List<Trade>> result, Realm realm) {
        view.hideRefresh();
        view.hideProgress();
        if (!ResultInterceptor.instance.resultDataHandler(result)){//判断是否Result数据为空
            return;
        }
        final List<Trade> tradeList = result.getData();
        initList(tradeList);
        final RealmResults<Trade> results = realm.where(Trade.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {//清空数据
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
        realm.executeTransactionAsync(new Realm.Transaction() {//重新加载数据
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(tradeList);
            }
        });
    }
}
