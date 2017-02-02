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
    Bitmap raqueta, bola;
    float xRaqueta = 0, yRaqueta, xBola=0, yBola=0;
    int tamRaqueta, width, height, sentidoX=1, sentidoY=1;
    boolean inicio = true;

    public Gview(Context context) {
        super(context);
        raqueta = BitmapFactory.decodeResource(getResources(), R.drawable.raqueta);
        bola = BitmapFactory.decodeResource(getResources(), R.drawable.bola_azul);
        inicio();
        startTimer();
    }

    private void inicio(){
        tamRaqueta = 150;
        xRaqueta = width / 2;
        xBola = width / 2;
        yBola = height * (float) 0.75;
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();
        yRaqueta = height/10*9;
        canvas.drawColor(Color.YELLOW);
        canvas.drawBitmap(raqueta, xRaqueta - tamRaqueta, yRaqueta, null);
        canvas.drawBitmap(bola, xBola, yBola, null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        xRaqueta = event.getX();
        this.invalidate();
        //return super.onTouchEvent(event);
        return true;
    }

    public void taskTimer(){
        posicionBola();
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

    private void posicionBola(){
        int deltaX = 5, deltaY = 3;
        if(xBola>=width - 90) sentidoX = -1;
        if(yBola>=yRaqueta - 90) sentidoY = -1;
        if(xBola<=0) sentidoX = 1;
        if(yBola<=0) sentidoY = 1;
        xBola += deltaX * sentidoX;
        yBola += deltaY * sentidoY;
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
