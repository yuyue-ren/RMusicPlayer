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
	* ���ڴ����ݿ��в�ѯ��������Ϣ��������List���� 
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
		            .getColumnIndex(MediaStore.Audio.Media.TITLE)));//���ֱ���  
	        String artist = cursor.getString(cursor  
	            .getColumnIndex(MediaStore.Audio.Media.ARTIST));//������  
	        long duration = cursor.getLong(cursor  
	            .getColumnIndex(MediaStore.Audio.Media.DURATION));//ʱ��  
	        long size = cursor.getLong(cursor  
	            .getColumnIndex(MediaStore.Audio.Media.SIZE));  //�ļ���С  
	        String url = cursor.getString(cursor  
	            .getColumnIndex(MediaStore.Audio.Media.DATA));  //�ļ�·��  
	        int isMusic = cursor.getInt(cursor  
	                .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//�Ƿ�Ϊ����  
	            if (isMusic != 0) {     //ֻ��������ӵ����ϵ���  
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
