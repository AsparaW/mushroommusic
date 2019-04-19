package com.mantouland.mushroommusic.constant;

import android.Manifest;
import android.os.Environment;

/**
 * Created by asparaw on 2019/4/15.
 */
public class APPConstant {
    /***
     * string test data
     */
    public static final String testMusicUrl ="http://www.mantouland.com/music/test.mp3";
    public static final String testMusicUrl2 = "http://www.ytmp3.cn/down/60099.mp3";
    public static final String sdcardPath=Environment.getExternalStorageDirectory().getPath();
    public static final String localMusicPath = sdcardPath+"/Music/3L - 夏の通り雨.mp3";
    public static final String testPath2="/sdcard/Music/3L - 夏の通り雨.mp3";

    public static final String[] supportedFormat={".mp3",".flac"};

    public static final String APP_MUSIC_PATH=sdcardPath+"/MushroomMusic";
    public static final String[] APP_PERMISSION={Manifest.permission.INTERNET,Manifest.permission.
            READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};


    //singleton
    private static final APPConstant  instance= new APPConstant();
    private APPConstant(){
        //DO_NOTHING
    }
    public static APPConstant getInstance (){
        return instance;
    }
}
