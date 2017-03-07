package com.example.ledcontrol;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.net.wifi.WifiManager;
import android.content.Context;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;



import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

import static android.R.id.content;


public class MainActivity extends AppCompatActivity   {

    private Button bt3;
    private Button bt9;
    private Button bt4;
    private WifiManager wifiManager;
    private ToggleButton tb;
    NotificationManager manager;
    int notification_ID;
    private  ImageView im;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler h=new Handler();//建立handeler
        im=(ImageView) findViewById(R.id.imageView);
        bt3 = (Button) findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        String message = "hello";
                        super.run();
                        try {
                            Socket socket = new Socket("192.168.191.1", 8080);
                            if (socket.isConnected() != false) {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this, "打开成功", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
                                }.start();
                                //因为控件不是线程安全的，所以子线程中涉及到的更新UI操作全都写入runnable对象、通过主线程的handler来post给UI。
                                final Runnable r=new Runnable() {
                                    @Override
                                    public void run() {
                                        im.setImageResource(R.mipmap.titi2);
                                    }
                                };
                                new Thread(){
                                    public void run(){
                                        h.post(r);//通过主线程的handler post给ui
                                    }
                                }.start();
                                //实现ui开关灯的效果
                            }
                            PrintWriter out = new PrintWriter(socket.getOutputStream());
                            out.println(message);
                            //关闭流
                            out.close();
                            //关闭Socket
                            socket.close();
                        } catch (IOException e) {
                            new Thread() {
                                @Override
                                public void run() {
                                    manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    Intent intent=new Intent(MainActivity.this,MainActivity.class);
                                    PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent,0);
                                    Notification.Builder builder=new Notification.Builder(MainActivity.this);
                                    builder.setSmallIcon(R.mipmap.im2);
                                    builder.setTicker("警告！！！");//状态栏的提示
                                    builder.setWhen(System.currentTimeMillis());//设置时间
                                    builder.setContentTitle("网络未连接");//设置标题
                                    builder.setContentText("请检查网络");//通知内容
                                    builder.setContentIntent(pendingIntent);//点击后的意图
                                    builder.setDefaults(Notification.DEFAULT_VIBRATE);//震动
                                    Notification notification=builder.build();
                                    manager.notify(notification_ID,notification);
                                    //toast显示
                                    Looper.prepare();
                                    Toast toast = Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_LONG);
                                    LinearLayout t = (LinearLayout) toast.getView();
                                    ImageView iv = new ImageView(MainActivity.this);
                                    iv.setImageResource(R.mipmap.image);
                                    t.addView(iv);
                                    toast.show();
                                    Looper.loop();
                                }
                            }.start();
                        }
                    }
                }.start();
            }
        });


        bt4 = (Button) findViewById(R.id.button4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    String message = "hehe";
                    public void run() {
                        super.run();
                        try {
                            Socket socket = new Socket("192.168.191.1", 8080);
                            if ((socket.isConnected() != false)) {
                                new Thread() {
                                    @Override
                                    public void run()  {
                                        Looper.prepare();
                                        Toast toast = Toast.makeText(MainActivity.this, "关闭成功", Toast.LENGTH_LONG);
                                        toast.show();
                                        Looper.loop();
                                    }
                                }.start();
                                final Runnable r=new Runnable() {
                                    @Override
                                    public void run() {
                                        im.setImageResource(R.mipmap.titi);
                                    }
                                };
                                new Thread(){
                                    public void run(){
                                        h.post(r);//通过主线程的handler post给ui
                                    }
                                }.start();
                                //实现ui开关灯的效果
                            }
                            PrintWriter out = new PrintWriter(socket.getOutputStream());
                            //获取socket中的输出流
                            out.println(message);
                            //关闭流
                            out.close();
                            //关闭Socket
                            socket.close();
                        } catch (IOException e) {
                            new Thread() {
                                @Override
                                public void run()  {
                                    //通知栏显示
                                    manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    Intent intent=new Intent(MainActivity.this,MainActivity.class);
                                    PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent,0);
                                    Notification.Builder builder=new Notification.Builder(MainActivity.this);
                                    builder.setSmallIcon(R.mipmap.im2);
                                    builder.setTicker("警告！！！");//状态栏的提示
                                    builder.setWhen(System.currentTimeMillis());//设置时间
                                    builder.setContentTitle("网络未连接");//设置标题
                                    builder.setContentText("请检查网络");//通知内容
                                    builder.setContentIntent(pendingIntent);//点击后的意图
                                    builder.setDefaults(Notification.DEFAULT_VIBRATE);//震动
                                    Notification notification=builder.build();
                                    manager.notify(notification_ID,notification);
                                    //toast显示
                                    Looper.prepare();
                                    Toast toast = Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_LONG);
                                    LinearLayout t = (LinearLayout) toast.getView();
                                    ImageView iv = new ImageView(MainActivity.this);
                                    iv.setImageResource(R.mipmap.image);
                                    t.addView(iv);
                                    toast.show();
                                    Looper.loop();
                                }
                            }.start();
                        }
                    }
                }.start();
            }
        });

        tb = (ToggleButton) findViewById(R.id.toggleButton);

        //给tb设置监听器
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager = (WifiManager) MainActivity.this
                            .getSystemService(Context.WIFI_SERVICE);
                   wifiManager.setWifiEnabled(true);

                } else {
                    wifiManager = (WifiManager) MainActivity.this
                            .getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(false);
                }
            }
        });


    }

}