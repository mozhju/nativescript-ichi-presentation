package cn.ichi.android.presentation;

import android.view.View;
import android.widget.TextView;


/**
 * Created by mozj on 2018/5/9.
 */

public class ListViewHandler {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;

    public ListViewHandler(View contentView) {
        this.textView1 = (TextView)contentView.findViewById(R.id.textView1);
        this.textView2 = (TextView)contentView.findViewById(R.id.textView2);
        this.textView3 = (TextView)contentView.findViewById(R.id.textView3);
        this.textView4 = (TextView)contentView.findViewById(R.id.textView4);
        this.textView5 = (TextView)contentView.findViewById(R.id.textView5);
    }

    public void SetText(OrderItem item) {
        float size = 20.0f;

        this.textView1.setText("");

        this.textView2.setText("" + (item.name == null ? "" : item.name));
        this.textView2.setTextSize(size);

        if (item.qty == null || item.qty == 0) {
            this.textView3.setVisibility(View.GONE);
        } else {
            this.textView3.setVisibility(View.VISIBLE);
            this.textView3.setText("" + item.qty);
            this.textView3.setTextSize(size);
        }

        this.textView4.setText("" + (item.fee == null? "" : String.format("￥%.2f", item.fee)));
        this.textView4.setTextSize(size);

        this.textView5.setText("");
    }
}
