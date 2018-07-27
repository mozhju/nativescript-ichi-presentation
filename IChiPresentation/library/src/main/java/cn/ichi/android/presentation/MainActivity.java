package cn.ichi.android.presentation;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtQty;
    private EditText edtFee;

    private EditText edtImagePath;
    private EditText edtImageFile;
    private EditText edtVideoPath;
    private EditText edtVideoFile;

    private Button btnShow;
    private Button btnOrder;
    private Button btnBlack;

    private Button btnImagePath;
    private Button btnImageFile;
    private Button btnVideoPath;
    private Button btnVideoFile;
    private Button btnURL;
    private Button btnMenu;
    private Button btnMedia;
    private Button btnClean;


    private MyPresentation myPresentation;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String dir = "";
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStorageDirectory().getPath() + "/Presentation/";
        } else {
            dir = getFilesDir().getPath() + "/";
        }


        order = new Order();
        order.finalFee = 0.0;

        edtName = (EditText)findViewById(R.id.name);
        edtQty = (EditText)findViewById(R.id.qty);
        edtFee = (EditText)findViewById(R.id.fee);

        edtImagePath = (EditText)findViewById(R.id.image_path);
        edtImageFile = (EditText)findViewById(R.id.image_file);
        edtVideoPath = (EditText)findViewById(R.id.video_path);
        edtVideoFile = (EditText)findViewById(R.id.video_file);

        edtImagePath.setText(dir);
        edtImageFile.setText(dir + "071.jpg");
        edtVideoPath.setText(dir);
        edtVideoFile.setText(dir + "430.3gp");

        btnImagePath = (Button)findViewById(R.id.btnImagePath);
        btnImagePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation.generate();
                myPresentation.setShowType(1);
                myPresentation.showPresentation();
                myPresentation.setImage(edtImagePath.getText().toString());
            }
        });

        btnImageFile = (Button)findViewById(R.id.btnImageFile);
        btnImageFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation = new MyPresentation();
                myPresentation.generate();
                myPresentation.setShowType(1);
                myPresentation.showPresentation();
                myPresentation.setImage(edtImageFile.getText().toString());
            }
        });

        btnVideoPath = (Button)findViewById(R.id.btnVideoPath);
        btnVideoPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation = new MyPresentation();
                myPresentation.generate();
                myPresentation.setShowType(1);
                myPresentation.showPresentation();
                myPresentation.setVideo(edtVideoPath.getText().toString());
            }
        });

        btnVideoFile = (Button)findViewById(R.id.btnVideoFile);
        btnVideoFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation = new MyPresentation();
                myPresentation.generate();
                myPresentation.setShowType(1);
                myPresentation.showPresentation();
                myPresentation.setVideo(edtVideoFile.getText().toString());
            }
        });

        btnURL = (Button)findViewById(R.id.btnURL);
        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation = new MyPresentation();
                myPresentation.generate();
                myPresentation.setShowType(1);
                myPresentation.showPresentation();
//                myPresentation.downloadAndShow("http://192.168.1.5/Share/ad2.txt?time="+new Date().getTime());

                String json = "{\"mediaFiles\":[\"http://cos.i-chi.cn/xc/wanwang/7b6.mp4\",\"http://cos.i-chi.cn/xc/wanwang/30S_s2.mp4\"],\"menus\":[\"http://cos.i-chi.cn/xc/wanwang/menu1.jpg\",\"http://cos.i-chi.cn/xc/wanwang/menu2.jpg\"]}";
                myPresentation.setMediaJsonAndShow(json);
            }
        });

        btnMedia = (Button)findViewById(R.id.btnMedia);
        btnMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation = new MyPresentation();
                myPresentation.generate();
                myPresentation.setShowType(1);
                myPresentation.ShowMedia();
                myPresentation.showPresentation();
            }
        });;

        btnMenu = (Button)findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation = new MyPresentation();
                myPresentation.generate();
                myPresentation.setShowType(1);
                myPresentation.ShowMenu();
                myPresentation.showPresentation();
            }
        });

        btnClean = (Button)findViewById(R.id.btnClean);
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation = new MyPresentation();
                myPresentation.generate();
                myPresentation.cleanCacheFile(false);
            }
        });

        btnOrder = (Button)findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation = new MyPresentation();
                myPresentation.generate();
                myPresentation.setShowType(2);
                myPresentation.showPresentation();

                myPresentation.setVideo(edtVideoPath.getText().toString());

                final int maxCount = 10;
                new Thread(new Runnable(){
                    public void run() {
                        int count = maxCount;
                        while (count-- > 0) {
                            try {
                                OrderItem item = new OrderItem();
                                item.name = edtName.getText().toString();
                                item.qty = Integer.parseInt(edtQty.getText().toString());
                                item.fee = Double.parseDouble(edtFee.getText().toString());

                                if (order.items.size() < 5) {
                                    order.items.add(item);
                                }
                                order.finalFee += item.fee;

                                Thread.sleep(100);

                                String jsonOrder = orderToJson(order);
                                myPresentation.setOrder(jsonOrder);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        while (count++ < maxCount) {
                            try {
                                double fee = Double.parseDouble(edtFee.getText().toString());
                                OrderItem item = order.items.get(0);
                                item.qty += 1;
                                item.fee += fee;
                                order.finalFee += fee;

                                Thread.sleep(100);
                                String jsonOrder = orderToJson(order);
                                myPresentation.setOrder(jsonOrder);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        btnShow = (Button)findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation = new MyPresentation();
                myPresentation.generate();
                myPresentation.setShowType(1);
                myPresentation.showPresentation();
            }
        });

        btnBlack = (Button)findViewById(R.id.btnBlack);
        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation = new MyPresentation();
                myPresentation.generateBlack();
                myPresentation.showPresentation();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (myPresentation != null) {
            myPresentation.generate();
            myPresentation.showPresentation();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (myPresentation != null) {
            myPresentation.closePresentation();
        }
    }


    private String orderToJson(Order order){
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray items = new JSONArray();
            if (order.items!= null) {
                for (int i = 0; i < order.items.size(); i++) {
                    OrderItem orderItem = order.items.get(i);
                    JSONObject item = new JSONObject();
                    if (orderItem.name != null) item.put("name", orderItem.name);
                    if (orderItem.qty != null) item.put("qty", orderItem.qty);
                    if (orderItem.fee != null) item.put("fee", orderItem.fee);
                    if (orderItem.price != null) item.put("price", orderItem.price);
                    items.put(item);
                }
            }

            JSONArray coupons = new JSONArray();
            if (order.coupons != null) {
                for (int i = 0; i < order.coupons.size(); i++) {
                    OrderItem couponItem = order.coupons.get(i);
                    JSONObject coupon = new JSONObject();
                    if (couponItem.name != null) coupon.put("name", couponItem.name);
                    if (couponItem.qty != null) coupon.put("qty", couponItem.qty);
                    if (couponItem.fee != null) coupon.put("fee", couponItem.fee);
                    if (couponItem.price != null) coupon.put("price", couponItem.price);
                    coupons.put(coupon);
                }
            }

            jsonObject.put("items", items);
            jsonObject.put("coupons", coupons);
            if (order.finalFee != null) {
                jsonObject.put("finalFee", order.finalFee);
            }
        } catch (Exception ex) {
            ex.printStackTrace();;
        }
        return jsonObject.toString();
    }
}
