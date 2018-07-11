package cn.ichi.android.presentation;


import android.app.Presentation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


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

    private LinearLayout viewLayout;
    private LinearLayout orderLayout;
    private int showType = 1;

    private Context outerContext;

    private Handler handler;
    private int mediaType;
    private MyList<String> srcFiles = new MyList<String>();

    private static final int  ORDER = 0;
    private static final int  IMAGE_ONE = 1;
    private static final int  IMAGE_MULTS = 2;
    private static final int  VIDEO_ONE = 3;
    private static final int  VIDEO_MULTS = 4;

    private static final int  DOWNLOADING = 99;

    private List<String> imgExtensions = new ArrayList<String>();
    private List<String> videoExtensions = new ArrayList<String>();

    public DifferentDisplay(Context outerContext, Display display) {

        super(outerContext,display);

        //TODOAuto-generated constructor stub
        this.outerContext = outerContext;
        this.handler = new MyHandler();

        imgExtensions.add("bmp");
        imgExtensions.add("jpg");
        imgExtensions.add("png");

        videoExtensions.add("mp4");
        videoExtensions.add("3gp");
        videoExtensions.add("mov");
        videoExtensions.add("avi");
        videoExtensions.add("flv");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diffrentdisplay_basket);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.ichi_logo);

        videoView = (VideoView)findViewById(R.id.video_player_view);
        videoView.setMediaController(null);
        videoView.setVisibility(View.GONE);

        viewLayout = (LinearLayout)findViewById(R.id.layout_view);
        orderLayout = (LinearLayout)findViewById(R.id.layout_order);

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


        if (srcFiles.size() > 0) {
            sendHandleMessage(0, mediaType);
        }

        initHeader();

        setShowType(showType);
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


    public void setShowType(int showType) {
        this.showType = showType;

        if (viewLayout == null || orderLayout == null) {
            return;
        }

        switch (showType) {
            case 0:
                viewLayout.setVisibility(View.GONE);
                orderLayout.setVisibility(View.GONE);
                break;
            case 1:
                viewLayout.setVisibility(View.VISIBLE);
                orderLayout.setVisibility(View.GONE);
                break;
            case 2:
                viewLayout.setVisibility(View.VISIBLE);
                orderLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    public void setOrder(Order order) {
        Message message = new Message();
        message.obj = order;
        message.what = ORDER;

        this.handler.sendMessage(message);
    }


    public void clearFiles() {
        srcFiles.clear();

        mediaType = DOWNLOADING;
        sendHandleMessage(0, mediaType);
    }


    public void addDownLoadFile(String path) {
        String prefix = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        if (imgExtensions.indexOf(prefix) >= 0) {
            mediaType = IMAGE_MULTS;
            srcFiles.add(path);
        } else if (videoExtensions.indexOf(prefix) >= 0) {
            mediaType = VIDEO_MULTS;
            srcFiles.add(path);
        }

        if (srcFiles.size() == 1) {
            sendHandleMessage(0, mediaType);
        }
    }


    public void setImageFile(String path) {
        srcFiles.clear();

        String prefix = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        if (imgExtensions.indexOf(prefix) >= 0) {
            srcFiles.add(path);
        }

        mediaType = IMAGE_ONE;
        if (srcFiles.size() > 0) {
            sendHandleMessage(0, mediaType);
        }
    }


    public void setImageDir(String path) {

        File file = new File(path);
        File[] fs = file.listFiles();
        String[] fss = file.list();

        srcFiles.clear();
        for (File f : fs) {
            String fileName = f.getPath();
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            if (imgExtensions.indexOf(prefix) >= 0) {
                srcFiles.add(fileName);
            }
        }

        mediaType = IMAGE_MULTS;
        if (srcFiles.size() > 0) {
            sendHandleMessage(0, mediaType);
        }
    }


    public void setVideoFile(String path) {
        srcFiles.clear();

        String prefix = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        if (videoExtensions.indexOf(prefix) >= 0) {
            srcFiles.add(path);
        }
        mediaType = VIDEO_ONE;
        if (srcFiles.size() > 0) {
            sendHandleMessage(0, mediaType);
        }
    }


    public void setVideoDir(String path) {
        File file = new File(path);
        File[] fs = file.listFiles();

        srcFiles.clear();
        for (File f : fs) {
            String fileName = f.getPath();
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            if (videoExtensions.indexOf(prefix) >= 0) {
                srcFiles.add(fileName);
            }
        }

        mediaType = VIDEO_MULTS;
        if (srcFiles.size() > 0) {
            sendHandleMessage(0, mediaType);
        }

    }


    private void sendHandleMessage(int index, int handleType) {
        Message message = new Message();
        message.obj = index;
        message.what = handleType;
        this.handler.sendMessage(message);
    }


    private void ShowImageFile(final String imgFile) {
        if (imgFile == null){
            return;
        }

        Bitmap bitmap = Utils.readImage(imgFile);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }

        imageView.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
        videoView.setOnCompletionListener(null);
        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }
    }


    private void ShowVideo(final String videoFile, MediaPlayer.OnCompletionListener listener) {
        if (videoFile == null){
            return;
        }

        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }
        videoView.setVideoPath(videoFile);
        videoView.start();
        videoView.setOnCompletionListener(listener);

        imageView.setVisibility(View.GONE);
        videoView.setVisibility(View.VISIBLE);
    }


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            final int type = msg.what;
            if (type == ORDER) {
                Order order = (Order)msg.obj;
                myProductAdapter.setOrderItems(order.items);
                myCouponAdapter.setOrderItems(order.coupons);
                String finalFee = String.format("%-6.2f", order.finalFee == null ? 0.0 : order.finalFee);
                txtTotal.setText("总金额：  " + finalFee + "元");

                return;
            }

            if (type != mediaType) {
                return;
            }

            final Integer index = (Integer)msg.obj;
            String path = null;
            if (srcFiles.size() > 0) {
                path = srcFiles.get(index);
            }
            switch (type) {
                case IMAGE_ONE:
                    ShowImageFile(path);
                    break;

                case IMAGE_MULTS:
                    ShowImageFile(path);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);

                                if (srcFiles.size() > 0) {
                                    sendHandleMessage((index + 1) % srcFiles.size(), type);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;

                case VIDEO_ONE:
                case VIDEO_MULTS:
                    ShowVideo(path, new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if (srcFiles.size() > 0) {
                                sendHandleMessage((index + 1) % srcFiles.size(), type);
                            }
                        }
                    });
                    break;

                case DOWNLOADING:
                    imageView.setImageResource(R.drawable.ichi_downloading);
                    imageView.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.GONE);

                    break;
                default:
                    break;
            }
        }
    }
}
