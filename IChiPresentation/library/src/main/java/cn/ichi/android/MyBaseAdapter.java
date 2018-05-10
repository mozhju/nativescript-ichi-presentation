package cn.ichi.android;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mozj on 2018/5/9.
 */

public class MyBaseAdapter extends BaseAdapter {

    private ArrayList<OrderItem> listData;
    private LayoutInflater mInflater;

    public MyBaseAdapter(Context mContext, ArrayList<OrderItem> orderItems) {
        this.mInflater = LayoutInflater.from(mContext);
        if (null == orderItems) {
            orderItems = new ArrayList<>();
        }
        this.listData = orderItems;
    }


    public void setOrderItems(ArrayList<OrderItem> mOrderItems) {
        if (null == mOrderItems) {
            mOrderItems = new ArrayList<>();
        }
        this.listData = mOrderItems;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listData.size();
    }

    @Override
    public OrderItem getItem(int position) {
        // TODO Auto-generated method stub
        if (position < listData.size()) {
            return listData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        if (position < listData.size()) {
            return position;
        }
        return -1;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        if (position < listData.size()) {
            ListViewHandler handler = null;
            if(contentView == null){
                contentView = mInflater.inflate(R.layout.a_item, null);  //使用表头布局test1.xml
                handler = new ListViewHandler(contentView);   //与表头的文本控件一一对应

                contentView.setTag(handler);
            }else{
                handler = (ListViewHandler)contentView.getTag();
            }

            //为ListView中的TextView布局控件添加内容
            OrderItem item = listData.get(position);
            handler.SetText(item);
        }

        return contentView;
    }

}
