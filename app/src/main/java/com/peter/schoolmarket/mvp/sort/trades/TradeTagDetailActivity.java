package com.peter.schoolmarket.mvp.sort.trades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.DividerItemGridDecoration;
import com.peter.schoolmarket.adapter.recycler.DividerItemNormalDecoration;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.mvp.base.BaseActivity;

/**
 * Created by PetterChen on 2017/4/29.
 */

public class TradeTagDetailActivity extends BaseActivity implements ITradeTagDetailView {

    protected RecyclerView recyclerView;
    TradeTagDetailPresenter presenter;
    private ImageView back;
    SwipeRefreshLayout refreshLayout;
    MaterialDialog progress;
    TextView title;
    private String tagName;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.trade_tag_detail_activity);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        tagName=bundle.getString("tagName");

        recyclerView = (RecyclerView) findViewById(R.id.trade_tag_detail_list);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.trade_tag_detail_refresh);
        presenter = new TradeTagDetailPresenter(this, this);
        back = (ImageView) findViewById(R.id.trade_tag_back);
        title = (TextView) findViewById(R.id.trade_tag_title);

        title.setText(tagName);

        progress = new MaterialDialog.Builder(this)
                .content("正在加载...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平进度条
                .title("请稍等")
                .build();

        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (TradeTagDetailActivity.this).finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(TradeTagDetailActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemNormalDecoration(this, DividerItemNormalDecoration.VERTICAL_LIST));
        presenter.init(tagName);
    }

    @Override
    public void loadDataSuccess(RecyclerCommonAdapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadDataFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if (!progress.isShowing()) {
            progress.show();
        }
    }

    @Override
    public void hideRefresh() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void hideProgress() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }
}
