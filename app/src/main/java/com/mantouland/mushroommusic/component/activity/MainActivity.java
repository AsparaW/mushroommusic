package com.mantouland.mushroommusic.component.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mantouland.mushroommusic.R;
import com.mantouland.mushroommusic.component.service.MusicService;
import com.mantouland.mushroommusic.constant.APPConstant;
import com.mantouland.mushroommusic.controller.FileHelper;
import com.mantouland.mushroommusic.controller.MusicController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private static final String TAG = "testmain";
    /**
     *
     */
    private ImageButton musicPlay;
    private ImageButton musicStop;
    private ImageButton musicLeft;
    private ImageButton musicRight;
    private ImageButton musicLoop;
    private TextView musicName;
    private MusicService.MyBinder musicBinder;
    private ServiceConnection connection;
    private ConstraintLayout mainLayout;

    private NotificationManager manager;
    private Notification notification;
    Intent intent ;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askPermission();
        setContentView(R.layout.activity_main);
        Intent musicIntent =new Intent(this,MusicService.class);
        connection= new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicBinder= (MusicService.MyBinder) service;
                MusicController.getInstance().setBinder(musicBinder);
                refreshUI();
                Log.d(TAG, "onServiceConnected: "+musicBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(musicIntent,connection,BIND_AUTO_CREATE);
        initMusicController();
        bindViews();
        setNotification();
    }
    void setNotification(){
        intent = new Intent(this,MainActivity.class);
        pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification= new NotificationCompat.Builder(this).setContentTitle("Player:").setChannelId("XXXXX").setSmallIcon(R.drawable.ic_launcher_foreground).setContentIntent(pendingIntent).build();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel("XXXXX","XXXXXX",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
        manager.notify(1,notification);

    }
    void bindViews(){
        musicLoop=findViewById(R.id.music_loop);
        musicPlay=findViewById(R.id.music_play);
        musicLeft=findViewById(R.id.music_left);
        musicRight=findViewById(R.id.music_right);
        musicStop=findViewById(R.id.music_stop);
        musicName=findViewById(R.id.music_name);
        mainLayout=findViewById(R.id.mainLayout);
        musicPlay.setOnClickListener(this);
        musicLoop.setOnClickListener(this);
        musicRight.setOnClickListener(this);
        musicLeft.setOnClickListener(this);
        musicStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.music_left:
                MusicController.getInstance().playLast();
                refreshUI();
                break;
            case R.id.music_right:
                MusicController.getInstance().playNext();
                refreshUI();
                break;
            case R.id.music_play:
                if (!MusicController.getInstance().nonempty){
                    MusicController.getInstance().playThis();
                }else {
                    MusicController.getInstance().pause();
                }
                refreshUI();
                break;
            case R.id.music_stop:
                MusicController.getInstance().stop();
                refreshUI();
                break;
            case R.id.music_loop:
                if (MusicController.getInstance().isLoop()){
                    MusicController.getInstance().setLoop(false);
                }else {
                    MusicController.getInstance().setLoop(true);
                }
                break;
        }
    }

    private boolean checkPermission() {
        boolean hasPermission = true;
            for (String permission:APPConstant.APP_PERMISSION){
                Log.d(TAG, "checkPermission: "+permission);
                hasPermission=ContextCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_GRANTED;
                if (!hasPermission)
                    break;
            }
        return hasPermission;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions
                (this, APPConstant.APP_PERMISSION, 1);
    }

    private void askPermission(){
        if (!checkPermission())
            requestPermission();
    }

    private void initMusicController(){
        MusicController.getInstance().setMusicList(FileHelper.getInstance().searchSDCard());
    }
    private void refreshUI(){
        String musicStringName = MusicController.getInstance().getNowPlaying().getName();
        musicName.setText(musicStringName);
        notification= new NotificationCompat.Builder(this).setContentTitle("Player:").setChannelId("XXXXX").setOngoing(true).setSmallIcon(R.drawable.ic_launcher_foreground).setContentText("now playing"+musicStringName).setContentIntent(pendingIntent).build();
        manager.notify(1,notification);
        if (MusicController.getInstance().isPlaying()){
            musicPlay.setImageResource(R.drawable.ic_pause_white_24dp);
        }else {
            musicPlay.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        }
    }

}
