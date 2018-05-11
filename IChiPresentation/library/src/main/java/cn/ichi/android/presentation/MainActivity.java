package cn.ichi.android.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtQty;
    private EditText edtFee;

    private Button btnShow;
    private Button btnOrder;
    private Button btnBlack;


    private MyPresentation myPresentation;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPresentation = new MyPresentation();

        order = new Order();
        order.finalFee = 0.0;

        edtName = (EditText)findViewById(R.id.name);
        edtQty = (EditText)findViewById(R.id.qty);
        edtFee = (EditText)findViewById(R.id.fee);

        btnShow = (Button)findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation.generate();
                myPresentation.showPresentation();
                String jsonOrder = orderToJson(order);
                myPresentation.setOrder(jsonOrder);

                final int maxCount = 30;
                new Thread(new Runnable(){
                    public void run(){
                        int count = maxCount;
                        while (count-- > 0) {
                            try {
                                OrderItem item = new OrderItem();
                                item.name = edtName.getText().toString();
                                item.qty = Integer.parseInt(edtQty.getText().toString());
                                item.fee = Double.parseDouble(edtFee.getText().toString());

                                if (order.items.size() < 20) {
                                    order.items.add(item);
                                }
                                order.finalFee += item.fee;

                                Thread.sleep(300);

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

                                Thread.sleep(300);
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

        btnOrder = (Button)findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderItem item = new OrderItem();
                item.name = edtName.getText().toString();
                item.qty = Integer.parseInt(edtQty.getText().toString());
                item.fee = Double.parseDouble(edtFee.getText().toString());

                order.items.add(item);
                order.coupons.add(item);
                order.finalFee += item.fee;
            }
        });

        btnBlack = (Button)findViewById(R.id.btnBlack);
        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresentation.generateBlack();
                myPresentation.showPresentation();
            }
        });
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
