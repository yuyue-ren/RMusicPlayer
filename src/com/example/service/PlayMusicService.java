package com.example.service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.example.dao.Mp3Dao;
import com.example.entities.Mp3Info;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class PlayMusicService extends Service{
	private static final String TAG = "PlayMusicService";
	private MediaPlayer mediaPlayer;
	private MusicBinder musicBinder;
	private String musicUrl;
	//播放状态 默认顺序播放
	private int playState;
	//顺序播放
	public static final int ordePlay=0;
	//随机播放
	public static final int randomPlay=1;
	//重复不放
	public static final int repeatPlay=2;
	
	//数据....
	private List<Mp3Info> mp3Infos;
	private Mp3Dao dao;
	//播放的音乐文件在 MP3infos中的索引
	private int currentPosition;
	@Override
	public IBinder onBind(Intent intent) {
		
		return musicBinder;
	}
	@Override
	public void onCreate() {
		
		super.onCreate();
		
		musicBinder=new MusicBinder();
		dao=new Mp3Dao(this);
		mp3Infos=dao.getMp3Infos();
		Log.i(TAG, "PlayMusicService OnCreate");
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		musicUrl=intent.getStringExtra("music_url");
		Log.i(TAG, "PlayMusicService onStartCommand");
		mediaPlayer.reset();
		try {
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(musicUrl);
			mediaPlayer.prepareAsync();
			mediaPlayer.start();
			Log.i(TAG, "palyMusic");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		if(mediaPlayer!=null){
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer=null;
			
		}
		super.onDestroy();
	}
	class MusicBinder extends Binder implements IMusicBinder{

		@Override
		public void playMusic(String path) {
			//避免多次点击播放 不能释放mediaplayer
			stopMusic();
			musicUrl=path;
			currentPosition=getCurrentPosition(path);
			mediaPlayer=new MediaPlayer();
			mediaPlayer.reset();
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					mp.start();
					Log.i(TAG, "palyMusic");
				}
			});
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					if(playState==ordePlay){
						playNextMusic();
					}else if(playState==randomPlay){ 
						Random random=new Random();
						currentPosition=random.nextInt(mp3Infos.size());
						playMusicByPosition(currentPosition);
					}
				}
			});
			try {
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer.setDataSource(musicUrl);
				mediaPlayer.prepareAsync();
			
			} catch (Exception e) {
				Log.i(TAG, "播放异常啦╮(╯▽╰)╭");
				Toast.makeText(PlayMusicService.this, "播放异常啦╮(╯▽╰)╭", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}

		@Override
		public void pauseMusic() {
			if(mediaPlayer==null){
				return;
			}
			if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
				mediaPlayer.pause();
			}else{
				mediaPlayer.start();
			}
		}

		
		@Override
		public void stopMusic() {
			if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
				mediaPlayer.stop();
				mediaPlayer.release();
				mediaPlayer=null;
			}
		}

		@Override
		public void restartMusic() {
			if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
				mediaPlayer.seekTo(0);
				
			}
		}

		@Override
		public int getPlayProgress() {
			if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
				return mediaPlayer.getDuration();
			}
			return 0;
		}

		@Override
		public void setPlayProgress(int progress) {
			if(mediaPlayer!=null){
				mediaPlayer.seekTo(progress);
			}
		}

		@Override
		public boolean isPlaying() {
			if(mediaPlayer!=null){
				return mediaPlayer.isPlaying();
			}
			return false;
		}


		@Override
		public void playPreMusic() {
			if(currentPosition>0){
				currentPosition-=1;
				playMusicByPosition(currentPosition);
			}else{
				currentPosition=mp3Infos.size()-1;
				playMusicByPosition(currentPosition);
			}
		}

		@Override
		public void playNextMusic() {
			if(currentPosition<mp3Infos.size()-1){
				currentPosition+=1;
				playMusicByPosition(currentPosition);
			}else{
				currentPosition=0;
				playMusicByPosition(currentPosition);
			}
		}

		@Override
		public void playMusicByPosition(int position) {
			String path=mp3Infos.get(currentPosition).getUrl();
			playMusic(path);
		}

		@Override
		public void setPlaybackOrder(int order) {
			playState=order;
		}

		

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
