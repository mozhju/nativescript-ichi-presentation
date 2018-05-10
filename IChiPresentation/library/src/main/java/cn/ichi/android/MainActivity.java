package cn.ichi.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtQty;
    private EditText edtFee;

    private Button btnShow;
    private Button btnOrder;


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
                myPresentation.setOrder(order);

                new Thread(new Runnable(){
                    public void run(){
                        int count = 3000;
                        while (count-- > 0) {
                            try {
                                OrderItem item = new OrderItem();
                                item.name = edtName.getText().toString();
                                item.qty = Integer.parseInt(edtQty.getText().toString());
                                item.fee = Double.parseDouble(edtFee.getText().toString());

                                if (order.items.size() < 10) {
                                    order.items.add(item);
                                }
                                order.finalFee += item.fee;

                                Thread.sleep(300);
                                myPresentation.setOrder(order);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        while (count++ < 3000) {
                            try {
                                double fee = Double.parseDouble(edtFee.getText().toString());
                                OrderItem item = order.items.get(0);
                                item.qty += 1;
                                item.fee += fee;
                                order.finalFee += fee;

                                Thread.sleep(300);
                                myPresentation.setOrder(order);
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
    }
}
