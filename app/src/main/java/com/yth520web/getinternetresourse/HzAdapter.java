package com.yth520web.getinternetresourse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HzAdapter extends RecyclerView.Adapter<HzAdapter.ViewHolder> {
    private List<Hz> mHzList;
    Hz hz;
    static class ViewHolder extends RecyclerView.ViewHolder{
        //RecyclerView的点击事件
        View hzView;
        TextView hzName;
        public ViewHolder(View view){
            super(view);
            hzView = view; //点击事件
            hzName = (TextView)view.findViewById(R.id.hz_namae);
        }
    }
    public HzAdapter(List<Hz> hzList){
        mHzList = hzList;
    }
    //既然是继承RecyclerView，那么就要重写三个方法
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hz_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);//点击事件

        //点击事件
       holder.hzView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                //final Hz mHz = mHzList.get();
                /*Toast.makeText(v.getContext(),"位置："+position+"对象："+mHzList.get(position).getHzName()
                                +"链接;"+mHzList.get(position).getLink(), Toast.LENGTH_SHORT).show();*/
                //v.getContext().startActivities(in);
                //点击后查看海贼王具体章节漫画,adapter无法直接跳转，需要使用接口
                Intent mintent = new Intent();
                mintent.setClass(view.getContext(),BrowersShowHZ.class);
                mintent.putExtra("position",mHzList.get(position).getLink()+"");
                view.getContext().startActivity(mintent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hz hz = mHzList.get(position);
        holder.hzName.setText(hz.getHzName());

    }

    @Override
    public int getItemCount() {
        return mHzList.size();
    }

}
