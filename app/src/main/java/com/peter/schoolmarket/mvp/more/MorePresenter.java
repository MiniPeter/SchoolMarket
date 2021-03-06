package com.peter.schoolmarket.mvp.more;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Notice;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.mvp.more.notice.detail.NoticeDetailActivity;
import com.peter.schoolmarket.network.RetrofitConf;
import com.peter.schoolmarket.util.ResultInterceptor;
import com.peter.schoolmarket.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by PetterChen on 2017/5/5.
 */

public class MorePresenter implements IMorePresenter, IMoreListener {
    private IMoreView view;
    private Context context;
    private IMoreModel model;
    private int page = 1;
    private boolean isLoadNextPage = false;
    private Realm realm;
    RecyclerCommonAdapter<?> adapter;
    List<Notice> data = new ArrayList<>();

    public MorePresenter(Context context,IMoreView view) {
        this.context = context;
        this.view = view;
        model = new MoreModel();
    }


    @Override
    public void refresh(Realm realm) {
        view.setSearchFlag(false);
        //本来就有刷新出现
        isLoadNextPage = false;
        page = 1;
        model.noticeDataReq(this, page, realm);
    }

    @Override
    public void init(Realm realm) {
        view.setSearchFlag(false);
        initList(data);
        this.realm = realm;
        RealmQuery<Notice> query =  realm.where(Notice.class);
        RealmResults<Notice> results = query.findAll();
        List<Notice> list = realm.copyFromRealm(results);
        //data.addAll(realm.copyFromRealm(results));
        if (data.size()>0){
            //initList(data);
            if (data.size() > 8) {
                for (int i = 0; i < AppConf.size; i++) {
                    data.add(list.get(i));
                }
            } else {
                data.addAll(list);
            }
            adapter.notifyDataSetChanged();
        } else {
            //这里可以添加一个加载窗口
            view.showProgress();
            isLoadNextPage = false;
            page = 1;
            model.noticeDataReq(this, page, realm);
        }
    }

    private void initList(final List<Notice> notices) {
        adapter=new RecyclerCommonAdapter<Notice>(context,notices, R.layout.more_item) {
            @Override
            public void convert(RecyclerViewHolder holder, Notice item) {
                User user = realm.where(User.class).equalTo("id",item.getAuthorId()).findFirst();
                if (user != null) {
                    holder.setText(R.id.more_item_name,user.getUsername());
                }
                holder.setText(R.id.more_item_title,item.getTitle());
                holder.setText(R.id.more_item_content,item.getContent());
                String tem = TimeUtils.getDate(item.getCreateTime());
                holder.setText(R.id.more_item_time,tem);
            }
        };
        view.loadDataSuccess(adapter);
        adapter.setClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, int position) {
                Notice item=notices.get(position);

                //跳转到notice详情页面
                //Toast.makeText(context, "jump to notice detail", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, NoticeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("notice", item);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onReqComplete(Result<List<Notice>> result, Realm realm) {
        view.hideRefresh();
        view.hideProgress();
        if (!ResultInterceptor.instance.resultDataHandler(result)){//判断是否Result数据为空
            return;
        }
        if (isLoadNextPage && result.getData().size() <= data.size()) {
            Toast.makeText(context, "没有更多内容啦", Toast.LENGTH_SHORT).show();
        }
        data.clear();
        data.addAll(result.getData());
        adapter.notifyDataSetChanged();
        /*final List<Notice> noticeList = result.getData();
        initList(noticeList);*/
        final RealmResults<Notice> results = realm.where(Notice.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {//清空数据
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
        //Async
        realm.executeTransaction(new Realm.Transaction() {//重新加载数据
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(data);
            }
        });
    }

    @Override
    public void loadNextPage() {
        isLoadNextPage = true;
        view.showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (data.size() == page * AppConf.size) {
                    page++;
                }
                model.noticeDataReq(MorePresenter.this, page, realm);
            }
        }, 500);
        /*if (data.size() < (page * AppConf.size)) {
            if (page != 1) {
                Toast.makeText(context, "没有更多内容啦", Toast.LENGTH_SHORT).show();
            }
        } else {
            view.showProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    page++;
                    model.noticeDataReq(MorePresenter.this, page, realm);
                }
            }, 500);
        }*/
    }

    @Override
    public void loadSearchPage(String query) {
        view.showProgress();
        page = 1;
        isLoadNextPage = false;
        model.searchDataReq(this, query);
    }

    @Override
    public void onSearchReqComplete(Result<List<Notice>> result) {
        view.hideProgress();
        switch (result.getCode()) {
            case 100 :
                if (result.getData() != null) {
                    data.clear();
                    data.addAll(result.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    view.onSuccess("没有搜索到相关信息");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.showRefresh();
                            refresh(realm);
                        }
                    }, 500);
                }
                break;
            case 99 :
                view.onFail(result.getMsg());
                break;
            default:
                break;
        }
    }
}
