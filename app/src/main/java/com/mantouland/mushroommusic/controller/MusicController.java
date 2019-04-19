package com.mantouland.mushroommusic.controller;

import android.util.Log;

import com.mantouland.mushroommusic.bean.MusicBean;
import com.mantouland.mushroommusic.component.service.MusicService;
import com.mantouland.mushroommusic.constant.APPConstant;

import java.util.List;

/**
 * Created by asparaw on 2019/4/16.
 */
public class MusicController {
    private static final String TAG = MusicController.class.toString();
    private static final String testPath=APPConstant.testPath2;
    private  MusicService.MyBinder binder;
    private List<MusicBean> musicList;
    private int nowPlayingIndex = 0;
    private boolean isLoop;
    private boolean isPlaying=false;
    public boolean nonempty =false;

    public boolean isPlaying() {
        return isPlaying;
    }

    private static class instanceHolder{
        private static final MusicController instance = new MusicController();
    }
    public static MusicController getInstance(){
        return instanceHolder.instance;
    }
    private MusicController(){
        //DO_NOTHING
    }

    public void setMusicList(List<MusicBean> musicList) {
        this.musicList = musicList;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
        binder.setLoop(isLoop);
    }

    public boolean isLoop() {
        return isLoop;
    }

    public List getMusicList() {
        return musicList;
    }

    public  void setBinder(MusicService.MyBinder binder) {
        Log.d(TAG, this.binder+"setBinder: "+binder);
        this.binder=binder;
    }

    private void play(String path){
        isPlaying=true;
        nonempty =true;
        if (binder!=null){
            Log.d(TAG, "ready to play: "+path+" at:"+nowPlayingIndex);
            path=path.trim();
            binder.play(path);
            binder.setLoop(isLoop);
        }
    }

    /**
     * pause and continue
     */
    public void pause(){
        binder.pause();
        isPlaying=binder.isPlaying();
    }
    public void playNext(){
            play(musicList.get(musicLooper(true)).getPath());
    }
    public void playThis(){
            play(musicList.get(nowPlayingIndex).getPath());
    }
    public void playLast(){
            play(musicList.get(musicLooper(false)).getPath());
    }
    public void stop(){
        isPlaying=false;
        nonempty=false;
        if (binder!=null){
            Log.d(TAG, "stop: ");
            binder.stop();
        }
    }


    private int musicLooper(boolean asc){
        if (asc){
            nowPlayingIndex++;
            if (nowPlayingIndex==musicList.size()){
                nowPlayingIndex=0;
            }
        }else{
            nowPlayingIndex--;
            if (nowPlayingIndex<0){
               nowPlayingIndex= musicList.size()-1;
            }
        }
        return nowPlayingIndex;
    }
    public MusicBean getNowPlaying(){
        Log.d(TAG, "getNowPlaying: "+musicList.get(nowPlayingIndex).getName()+" at"+nowPlayingIndex);
        return musicList.get(nowPlayingIndex);
    }

}
