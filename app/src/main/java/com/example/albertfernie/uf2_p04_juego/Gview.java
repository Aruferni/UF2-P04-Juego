package com.example.albertfernie.uf2_p04_juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by albertfernie on 31/01/2017.
 */
public class Gview extends View {

    private Timer timer=null ;
    private MyTimerTask task;
    private int interval=100;
    Bitmap bmp1, bmp2;
    float x = 0, y, x2, y2;
    int width, height;

    public Gview(Context context) {
        super(context);
        bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.raqueta);
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.bola_azul);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();
        canvas.drawColor(Color.YELLOW);
        if(x==0) canvas.drawBitmap(bmp1, 400, 1000, null);
        else canvas.drawBitmap(bmp1, x - 150, 1000, null);
        //canvas.drawBitmap(bmp2, x2 - 60, y2 - 60, null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        this.invalidate();
        return super.onTouchEvent(event);
    }

    public void taskTimer(){
        //el código que queremos ejecutar en timer
        this.invalidate();
    }

    private void startTimer(){
        task=new MyTimerTask(this);
        timer= new Timer("miTimer");
        timer.schedule(task, 0, interval);
    }

    private void stopTimer(){
        timer.cancel();
        timer=null; task=null;
    }

}

class MyTimerTask extends TimerTask {
    Handler handler = new Handler();
    Context context;
    Gview gview;

    public MyTimerTask(Gview gview){
        this.gview=gview;
    }

    public void run() {
        handler.post(new Runnable() {
            public void run() {
                gview.taskTimer();
            }
        });
    }
}
