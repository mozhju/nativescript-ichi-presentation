package cn.ichi.android;


import android.app.Presentation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

/**
 * Created by mozj on 2018/5/9.
 */

public class DifferentDisplay extends Presentation {

    private ImageView imageView;
    private VideoView videoView;
    private ListView listProducts;
    private ListView listCoupons;
    private TextView txtTotal;

    private MyBaseAdapter myProductAdapter;
    private MyBaseAdapter myCouponAdapter;

    private Context outerContext;

    private Handler handler;


    public DifferentDisplay(Context outerContext, Display display) {

        super(outerContext,display);

        //TODOAuto-generated constructor stub
        this.outerContext = outerContext;
        this.handler = new MyHandler();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diffrentdisplay_basket);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.logo);

        videoView = (VideoView)findViewById(R.id.video_player_view);
        videoView.setMediaController(null);
        videoView.setVisibility(View.GONE);

        listProducts = (ListView)findViewById(R.id.listProduct);
        if (null == myProductAdapter) {
            myProductAdapter = new MyBaseAdapter(outerContext, null);
        }
        listProducts.setAdapter(myProductAdapter);

        listCoupons = (ListView)findViewById(R.id.listCoupon);
        if (null == myCouponAdapter) {
            myCouponAdapter = new MyBaseAdapter(outerContext, null);
        }
        listCoupons.setAdapter(myCouponAdapter);

        txtTotal = (TextView)findViewById(R.id.txtTotal);

        initHeader();
    }


    private void setHeaderText(View headerView, int id, String title){
        TextView textView2 = (TextView) headerView.findViewById(id);
        if (title == null || title.isEmpty()) {
            textView2.setVisibility(View.GONE);
        } else {
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(title);
            textView2.setTextSize(22.0f);
            textView2.setGravity(Gravity.CENTER);
            textView2.setBackgroundColor(Color.parseColor("#3399FF"));
        }
    }


    private void initHeader() {

        // 商品
        View productTitle = findViewById(R.id.productTitle);
        setHeaderText(productTitle, R.id.textView2, "商品");
        setHeaderText(productTitle, R.id.textView3, "数量");
        setHeaderText(productTitle, R.id.textView4, "金额");

        // 优惠
        View couponTitle = findViewById(R.id.couponTitle);
        setHeaderText(couponTitle, R.id.textView2, "优惠名称");
        setHeaderText(couponTitle, R.id.textView3, "");
        setHeaderText(couponTitle, R.id.textView4, "优惠金额");
    }


    public void setOrder(Order order) {
        Message message = new Message();
        message.obj = order;
        message.what = 0;

        this.handler.sendMessage(message);
    }


    public void setImage(String path) {
        Message message = new Message();
        message.obj = path;
        message.what = 1;

        this.handler.sendMessage(message);
    }


    public void setVideo(String path) {
        Message message = new Message();
        message.obj = path;
        message.what = 2;

        this.handler.sendMessage(message);
    }


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            if ( msg.what == 0 ) // Order
            {
                Order order = (Order)msg.obj;
                myProductAdapter.setOrderItems(order.items);
                myCouponAdapter.setOrderItems(order.coupons);
                String finalFee = String.format("%-6.2f", order.finalFee == null ? 0.0 : order.finalFee);
                txtTotal.setText("总金额：  " + finalFee + "元");
            } else if ( msg.what == 1) {  // Image
                String path = (String)msg.obj;
                Bitmap bitmap = Utils.readImage(path);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }

                imageView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
            } else if ( msg.what == 2 ) { // Video
                String path = (String)msg.obj;

                if (videoView.isPlaying()) {
                    videoView.stopPlayback();
                }
                videoView.setVideoPath(path);
                videoView.start();

                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
            }
        }
    }
}
