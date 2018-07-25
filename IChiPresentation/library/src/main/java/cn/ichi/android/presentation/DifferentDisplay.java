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
    private boolean isCreated = false;

    private int imageDisplayTime = 5000;

    private Context outerContext;

    private Handler handler;
    private static int meesageType;
    private static MyList<String> mediaFiles = new MyList<String>();
    private static MyList<String> menuFiles = new MyList<String>();

    private static final int  CANCEL = 0;
    private static final int  ORDER = 0;
    private static final int  MEDIA_FILES = 1;
    private static final int  ADVERTISEMENT = 2;
    private static final int  MENUS = 3;
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
        videoExtensions.add("wmv");
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

        isCreated = true;

        initHeader();

        setShowType(showType);
    }


    @Override
    protected void onStop() {
        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }
        meesageType = CANCEL;
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


    public boolean isUsing(String file) {
        for(int i = 0; i < mediaFiles.size(); i++)
        {
            if (mediaFiles.get(i).equals(file)){
                return true;
            }
        }
        for(int i = 0; i < menuFiles.size(); i++)
        {
            if (menuFiles.get(i).equals(file)){
                return true;
            }
        }
        return false;
    }


    public void setShowType(int showType) {
        this.showType = showType;

        if (!isCreated) {
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


    public void setImageDisplayTime(int imageDisplayTime) {
        this.imageDisplayTime = imageDisplayTime;
    }


    public void ShowMenu() {
        if (menuFiles.size() > 0) {
            if (meesageType != MENUS) {
                meesageType = MENUS;
                sendHandleMessage(0, meesageType);
            }
        } else {
            ShowWaiting();
        }
    }


    public void ShowMedia() {
        if (mediaFiles.size() > 0) {
            if (meesageType != MEDIA_FILES) {
                meesageType = MEDIA_FILES;
                sendHandleMessage(0, meesageType);
            }
        } else {
            ShowWaiting();
        }
    }


    public void ShowWaiting() {
        meesageType = DOWNLOADING;
        sendHandleMessage(0, meesageType);
    }


    public void ShowAdvertisement() {
        if (meesageType != ADVERTISEMENT) {
            meesageType = ADVERTISEMENT;
            sendHandleMessage(0, meesageType);
        }
    }


    public void setOrder(Order order) {
        Message message = new Message();
        message.obj = order;
        message.what = ORDER;

        this.handler.sendMessage(message);
    }


    public void clearAllFiles(){
        mediaFiles.clear();
        menuFiles.clear();
    }


    public void clearMediaFiles() {
        mediaFiles.clear();
    }


    public void clearMenuFiles() {
        menuFiles.clear();
    }


    public boolean addMediaFile(String path) {
        String prefix = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        if (imgExtensions.indexOf(prefix) >= 0 || videoExtensions.indexOf(prefix) >= 0) {
            synchronized(mediaFiles) {
                return mediaFiles.add(path);
            }
        }
        return false;
    }


    public boolean addMenuFile(String path) {
        String prefix = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        if (imgExtensions.indexOf(prefix) >= 0) {
            synchronized(menuFiles) {
                return menuFiles.add(path);
            }
        }
        return false;
    }


    public void setMediaFile(String path, int mediaType) {
        synchronized(mediaFiles) {
            mediaFiles.clear();

            String prefix = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
            if ((mediaType & 1) == 1 && imgExtensions.indexOf(prefix) >= 0) {
                mediaFiles.add(path);
            }
            if ((mediaType & 2) == 2 && videoExtensions.indexOf(prefix) >= 0) {
                mediaFiles.add(path);
            }

            if (mediaFiles.size() > 0) {
                meesageType = MEDIA_FILES;
                sendHandleMessage(0, meesageType);
            }
        }
    }


    public void setMediaDir(String path, int mediaType) {

        File file = new File(path);
        File[] fs = file.listFiles();

        synchronized(mediaFiles) {
            mediaFiles.clear();
            for (File f : fs) {
                String fileName = f.getPath();
                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

                if ((mediaType & 1) == 1 && imgExtensions.indexOf(prefix) >= 0) {
                    mediaFiles.add(fileName);
                }
                if ((mediaType & 2) == 2 && videoExtensions.indexOf(prefix) >= 0) {
                    mediaFiles.add(fileName);
                }
            }

            if (mediaFiles.size() > 0) {
                meesageType = MEDIA_FILES;
                sendHandleMessage(0, meesageType);
            }
        }
    }


    private void sendHandleMessage(int index, int handleType) {
        if (index < 0) {
            return;
        }

        Message message = new Message();
        message.obj = index;
        message.what = handleType;
        this.handler.sendMessage(message);
    }


    private void ShowMediaFile(String fileName, int nextIndex) {
        if (fileName == null) {
            return;
        }

        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        if (imgExtensions.indexOf(prefix) >= 0)
        {
            ShowImageFile(fileName, nextIndex, MEDIA_FILES);
        }
        else if (videoExtensions.indexOf(prefix) >= 0)
        {
            ShowVideoFile(fileName, nextIndex);
        }
    }


    private void ShowImageFile(final String imgFile, final int nextIndex, final int meesageType) {
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

        if (nextIndex >= 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(imageDisplayTime);

                        sendHandleMessage(nextIndex, meesageType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    private void ShowVideoFile(final String videoFile, final int nextIndex) {
        if (videoFile == null){
            return;
        }

        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }
        videoView.setVideoPath(videoFile);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (nextIndex >= 0) {
                    sendHandleMessage(nextIndex, MEDIA_FILES);
                }
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                videoView.stopPlayback(); //播放异常，则停止播放，防止弹窗使界面阻塞
                if (nextIndex >= 0) {
                    sendHandleMessage(nextIndex, MEDIA_FILES);
                }
                return true;
            }
        });

        imageView.setVisibility(View.GONE);
        videoView.setVisibility(View.VISIBLE);
    }


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (!isCreated) {
                return;
            }

            final int type = msg.what;
            if (type == ORDER) {
                Order order = (Order)msg.obj;
                myProductAdapter.setOrderItems(order.items);
                myCouponAdapter.setOrderItems(order.coupons);
                String finalFee = String.format("%-6.2f", order.finalFee == null ? 0.0 : order.finalFee);
                txtTotal.setText("总金额：  " + finalFee + "元");

                return;
            }

            if (type != meesageType) {
                return;
            }

            Integer index = (Integer)msg.obj;
            String fileName = null;
            int nextIndex = -1;
            switch (type) {
                case MEDIA_FILES:
                    synchronized(mediaFiles) {
                        if (index < mediaFiles.size()) {
                            fileName = mediaFiles.get(index);
                            nextIndex = (index + 1) % mediaFiles.size();
                        }
                    }
                    ShowMediaFile(fileName, nextIndex);
                    break;

                case ADVERTISEMENT:

                    break;

                case MENUS:
                    synchronized(menuFiles) {
                        if (index < menuFiles.size()) {
                            fileName = menuFiles.get(index);
                            nextIndex = (index + 1) % menuFiles.size();
                        }
                    }
                    ShowImageFile(fileName, nextIndex, MENUS);
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
