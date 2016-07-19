package com.keegan.kandroid.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.keegan.core.ActionCallbackListener;
import com.keegan.kandroid.R;
import com.keegan.kandroid.adapter.CouponListAdapter;
import com.keegan.model.CouponEntity;

import java.util.List;

/**
 * 券列表
 *
 * @version 1.0 创建时间：15/6/28
 */
public class CouponListActivity extends KBaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private CouponListAdapter couponListAdapter;

    private int currentPage;

    @Override
    protected void initVariables() {
        currentPage = 1;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        setContentView(R.layout.activity_coupon_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 需要重置当前页为第一页，并且清掉数据
                currentPage = 1;
                couponListAdapter.clearItems();
                loadData();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        couponListAdapter = new CouponListAdapter(this);
        listView.setAdapter(couponListAdapter);
    }

    @Override
    protected void loadData() {
        this.appAction.listCoupon(currentPage, new ActionCallbackListener<List<CouponEntity>>() {
            @Override
            public void onSuccess(List<CouponEntity> data) {
                if(!data.isEmpty()) {
                    if(currentPage == 1) { // 第一页
                        couponListAdapter.setItems(data);
                    } else { // 分页数据，其他也数据的追加
                        couponListAdapter.addItems(data);
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
