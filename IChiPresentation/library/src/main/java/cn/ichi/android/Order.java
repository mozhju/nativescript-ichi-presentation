package cn.ichi.android;

import java.util.ArrayList;

/**
 * Created by mozj on 2018/5/9.
 */

public class Order {
    public ArrayList<OrderItem> items;
    public ArrayList<OrderItem> coupons;
    public Double finalFee;

    public Order() {
        items = new ArrayList<OrderItem>();
        coupons = new ArrayList<OrderItem>();
        finalFee = 0.0;
    }
}
