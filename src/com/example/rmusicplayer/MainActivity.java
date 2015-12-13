package com.example.rmusicplayer;

import java.util.List;

import com.example.adapter.MusicAdapter;
import com.example.dao.Mp3Dao;
import com.example.entities.Mp3Info;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	private ListView lv_music;
	private List<Mp3Info> mp3Infos;
	private Mp3Dao mp3Dao;
	private MusicAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv_music=(ListView)findViewById(R.id.lv_music);
		fillDate();
		adapter=new MusicAdapter(this, mp3Infos);
		lv_music.setAdapter(adapter);
		
		//lv_music设置点击事件 跳转到播放页面
		lv_music.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Mp3Info mp3Info=mp3Infos.get(position);
				Intent intent=new Intent(MainActivity.this,PlayMusicActivity.class);
				intent.putExtra("music_url", mp3Info.getUrl());
				startActivity(intent);
			}
			
		});
		
	}

	private void fillDate(){
		mp3Dao=new Mp3Dao(this);
		mp3Infos=mp3Dao.getMp3Infos();
	}
}
