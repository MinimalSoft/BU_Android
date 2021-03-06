package com.MinimalSoft.BUniversitaria.Tabs;

import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.RecyclerArticles.Article;
import com.MinimalSoft.BUniversitaria.RecyclerArticles.ArticlesAdapter;
import com.MinimalSoft.BUniversitaria.RecyclerArticles.ArticlesCollector;

import java.util.List;

import android.os.Bundle;
import android.widget.Toast;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

public class Articles extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefresh;
    private ArticlesAdapter articlesAdapter;
    private View inflatedView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_refresher_list, container, false);
            swipeRefresh = (SwipeRefreshLayout) inflatedView.findViewById(R.id.refresher_swipeRefresh);
            RecyclerView recyclerView = (RecyclerView) inflatedView.findViewById(R.id.refresher_recyclerView);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflatedView.getContext());
            articlesAdapter = new ArticlesAdapter();

            swipeRefresh.setColorSchemeResources(R.color.red_900, R.color.brown_500, R.color.green_900, R.color.orange_600);
            swipeRefresh.setOnRefreshListener(this);
            recyclerView.setAdapter(articlesAdapter);
            recyclerView.setLayoutManager(layoutManager);
            onRefresh();
        }

        return inflatedView;
    }

    /*----OnRefreshListener Methods*/
    @Override
    public void onRefresh() {
        String api_url = getResources().getString(R.string.server_wp);
        new ArticlesCollector(this, api_url).execute();
    }

    public void onPostUpdateFailed(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
        swipeRefresh.setRefreshing(false);
    }

    public void onPostUpdateSucceed(List<Article> List) {
        articlesAdapter.updateArticles(List);
        swipeRefresh.setRefreshing(false);
    }
}