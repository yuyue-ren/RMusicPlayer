package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.entities.Mp3Info;

public class Mp3Dao {
	private static final String TAG = "Mp3Dao";
	Context context;
	
	
	
	public Mp3Dao(Context context) {
		super();
		this.context = context;
	}


	/** 
	* 用于从数据库中查询歌曲的信息，保存在List当中 
	* 
	* @return 
	*/ 
	public List<Mp3Info> getMp3Infos(){
		Cursor cursor =context. getContentResolver().query(  
		        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,  
		        MediaStore.Audio.Media.DEFAULT_SORT_ORDER); 
		List<Mp3Info> mp3Infos=new ArrayList<Mp3Info>();
		while(cursor.moveToNext()){
			Mp3Info mp3Info=new Mp3Info();
			int id=cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
			String title = cursor.getString((cursor   
		            .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题  
	        String artist = cursor.getString(cursor  
	            .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家  
	        long duration = cursor.getLong(cursor  
	            .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长  
	        long size = cursor.getLong(cursor  
	            .getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小  
	        String url = cursor.getString(cursor  
	            .getColumnIndex(MediaStore.Audio.Media.DATA));  //文件路径  
	        int isMusic = cursor.getInt(cursor  
	                .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐  
	            if (isMusic != 0) {     //只把音乐添加到集合当中  
	                mp3Info.setId(id);  
	                mp3Info.setTitle(title);  
	                mp3Info.setArtist(artist);  
	                mp3Info.setDuration(duration);  
	                mp3Info.setSize(size);  
	                mp3Info.setUrl(url); 
	                Log.i(TAG, mp3Info.toString());
	                mp3Infos.add(mp3Info);  
	                }  
		}
		cursor.close();
		
		return mp3Infos;
	}
}
