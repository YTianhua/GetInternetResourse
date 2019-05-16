package com.yth520web.getinternetresourse;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList linkList = new ArrayList();//储存链接信息
    ArrayList titleList = new ArrayList();//储存标题信息
    List<Hz> hzList =new ArrayList<>();
    HzAdapter hzAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initHz();  //测试recyclerview是否正确》结果》正确

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        hzAdapter = new HzAdapter(hzList);
        recyclerView.setAdapter(hzAdapter);
        //手动刷新recyclerview，否则不显示
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHZ();
            }
        });
        //测试WebView
        /*WebView mWebView = (WebView)findViewById(R.id.mWebView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("https://one-piece.cn/post/10941/");*/
        /*快速注释一段代码Ctrl+Shift+/  */
        //只能打开百度的网页，而对于网页上的超链接等无法打开
        //使用jsoup获取海贼小站的资源

        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try{
                    doc = Jsoup.connect("https://one-piece.cn/").get();
                    Log.i("标题：",doc.title());
                    //获取a标签的信息，包括href和title
                    Elements aElements = doc.getElementsByTag("a");
                    int i=1;
                    //遍历数组或集合的方法 for(对象 任意一个名称 : 数组或集合){}
                   /* for(Element aElement:aElements){
                        Log.i("a包括：",""+aElement+i);
                        i++;
                    }*/
                   // 成功遍历对象aElemenys，其中包含60个a标签，
                    // 我需要看动漫，则从第29（28）个a标签开始，53（52）个标签结束

                    for(int x= 28;x<=52;x++){
                        Element element = aElements.get(x);//获取第29个到52个的对象
                        String link = element.attr("href");
                       // Log.i("获得link成功","第"+x+"个目标links："+link);
                        String title = element.attr("title");
                        //Log.i("获得title成功","第"+x+"个目标title："+title);

                        //成功获取链接和title，应该储存
                        // 我现在使用ArrayList储存，因为每次开启程序都需要刷新
                        linkList.add(link);
                        titleList.add(title);
                        /*while(x==51){

                            Log.i("","开始showArrayList()");
                        }*/
                    }
                    showArrayList();
                    Log.i("","11启动showArrayList()");
                }
                catch (Exception e){ Log.i("错误：",e+"");}
            }
        }).start();
        //showArrayList();
    }

    public void showArrayList(){
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.support_simple_spinner_dropdown_item,data);
        ListView mListView =(ListView)findViewById(R.id.mListView);
        mListView.setAdapter(adapter);*/
        //检测是否添加link和title成功
        hzList.clear();
          for(int i=0;i<linkList.size();i++){
             //Log.i("数据成功添加","linkList第"+(i+1)+"元素："+linkList.get(i));
             Hz add_title = new Hz((String) titleList.get(i),(String)linkList.get(i));
             hzList.add(add_title);
             //Log.i("hzList数据成功添加","hzList信息："+linkList.get(i));
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hzAdapter.notifyDataSetChanged();
            }
        });

        //成功添加到数组
    }
    public void refreshHZ(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showArrayList();
                        //hzAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    public void hzOnClick(View view){
        Bundle bundle = new Bundle();
        int position=bundle.getInt("position");
        Toast.makeText(this,"位置："+position
                , Toast.LENGTH_LONG).show();
    }
}