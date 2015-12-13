package com.example.adapter;

import java.util.List;

import com.example.entities.Mp3Info;
import com.example.rmusicplayer.R;
import com.example.utils.MyTimeUtils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter{
	
	private Context context;
	private List<Mp3Info> mp3Infos;
	
	
	
	public MusicAdapter(Context context, List<Mp3Info> mp3Infos) {
		super();
		this.context = context;
		this.mp3Infos = mp3Infos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mp3Infos.size();
	}

	@Override
	public Object getItem(int position) {
		
		return mp3Infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.list_item_music, parent, false);
			viewHolder=new ViewHolder();
			viewHolder.tv_title=(TextView)convertView.findViewById(R.id.tv_title);
			viewHolder.tv_duration=(TextView)convertView.findViewById(R.id.tv_duration);
			viewHolder.tv_artist=(TextView)convertView.findViewById(R.id.tv_artist);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		Mp3Info mp3Info=mp3Infos.get(position);
		viewHolder.tv_title.setText(mp3Info.getTitle());
		viewHolder.tv_duration.setText(MyTimeUtils.ms2s(mp3Info.getDuration()));
		viewHolder.tv_artist.setText(mp3Info.getArtist());
		
		return convertView;
	}

	class ViewHolder{
		TextView tv_title;
		TextView tv_duration;
		TextView tv_artist;
	}
}
