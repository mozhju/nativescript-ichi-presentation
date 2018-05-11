package cn.ichi.android.presentation;


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


/**
 * Created by mozj on 2018/5/9.
 */

public class BlackDisplay extends Presentation {

    private ImageView imageView;

    private Context outerContext;

    private Handler handler;


    public BlackDisplay(Context outerContext, Display display) {

        super(outerContext,display);

        //TODOAuto-generated constructor stub
        this.outerContext = outerContext;
        this.handler = new MyHandler();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diffrentdisplay_black);

        imageView = (ImageView)findViewById(R.id.imageView);
    }


    public void setImage(String path) {
        Message message = new Message();
        message.obj = path;
        message.what = 1;

        this.handler.sendMessage(message);
    }



    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if ( msg.what == 0 )
            {

            } else if ( msg.what == 1) {  // Image
                String path = (String)msg.obj;
                Bitmap bitmap = Utils.readImage(path);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }

                imageView.setVisibility(View.VISIBLE);
            } else if ( msg.what == 2 ) {

            }
        }
    }
}
