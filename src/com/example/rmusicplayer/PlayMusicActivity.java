package com.example.rmusicplayer;

import java.util.List;

import com.example.dao.Mp3Dao;
import com.example.entities.Mp3Info;
import com.example.service.IMusicBinder;
import com.example.service.PlayMusicService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayMusicActivity extends Activity{
	private static final String TAG = "PlayMusicActivity";
	private String music_url;
	
	private Button btn_play;
	private Button btn_pause;
	private Button btn_stop;
	private Button btn_restart;
	private Button btn_next;
	private Button btn_pre;
	private SeekBar sb_palyprogress;
	
	private List<Mp3Info> mp3Infos;
	private Mp3Dao dao;
	private IMusicBinder imBinder;
	//播放的音乐文件在 MP3infos中的索引
	private int currentPosition;
	
	private ServiceConnection conn=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "bind palyMusicService success");
			imBinder=(IMusicBinder) service;
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playmusic);
		music_url=getIntent().getStringExtra("music_url");
		initView();
		setListener();
		fillDate();
		Intent service=new Intent(PlayMusicActivity.this,PlayMusicService.class);
		service.putExtra("music_url",music_url);
		bindService(service, conn, BIND_AUTO_CREATE);
		currentPosition=getCurrentPosition(music_url);
	}
	@Override
	protected void onDestroy() {
		unbindService(conn);
		conn=null;
		super.onDestroy();
	}
	private void initView(){
		btn_play=(Button)findViewById(R.id.btn_play);
		btn_pause=(Button)findViewById(R.id.btn_pause);
		btn_stop=(Button)findViewById(R.id.btn_stop);
		btn_restart=(Button)findViewById(R.id.btn_restart);
		btn_next=(Button)findViewById(R.id.btn_next);
		btn_pre=(Button)findViewById(R.id.btn_pre);
		sb_palyprogress=(SeekBar)findViewById(R.id.sb_palyprogress);
	}
	
	private void setListener(){
		btn_play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imBinder.playMusic(music_url);
				int progress=imBinder.getPlayProgress();
				Log.i(TAG,"progress"+ progress+"");
				sb_palyprogress.setMax(progress);
			}
		});
		btn_pause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imBinder.pauseMusic();
				if(imBinder.isPlaying()){
					btn_pause.setText("暂停");
				}else{
					btn_pause.setText("继续");
				}
			}
		});
		btn_stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imBinder.stopMusic();
			}
		});
		btn_restart.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				imBinder.restartMusic();
			}
		});
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imBinder.playNextMusic();
			}
		});
		btn_pre.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentPosition>0){
					currentPosition-=1;
					String path=mp3Infos.get(currentPosition).getUrl();
					imBinder.playMusic(path);
				}else{
					currentPosition=mp3Infos.size()-1;
					String path=mp3Infos.get(currentPosition).getUrl();
				    imBinder.playMusic(path);
				}
			}
		});
		
		sb_palyprogress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int progress=seekBar.getProgress();
				imBinder.setPlayProgress(progress);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				if(imBinder.isPlaying()){
					int progress=imBinder.getPlayProgress();
					Log.i(TAG,"progress"+ progress+"");
					seekBar.setMax(progress);
				}else{
					imBinder.playMusic(music_url);
				}
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});
	}
	
	private void fillDate(){
		dao=new Mp3Dao(this);
		mp3Infos=dao.getMp3Infos();
	}
	
	private int getCurrentPosition(String path){
		for(int i=0;i<mp3Infos.size();i++){
			Mp3Info mp3Info=mp3Infos.get(i);
			if(mp3Info.getUrl().equals(path)){
				return i;
			}
		}
		return 0;
	}
}
