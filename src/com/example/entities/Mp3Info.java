package com.example.entities;

public class Mp3Info {
	private int id;//����id 
	private String title; //���ֱ���
	private String artist;//������
	private String url;//�ļ�·��
	private long duration;//ʱ��
	private long size;//�ļ���С
	
	public Mp3Info() {
		
	}
	
	public Mp3Info(int id, String title, String artist, String url,
			long duration, long size) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.url = url;
		this.duration = duration;
		this.size = size;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Mp3Info [id=" + id + ", title=" + title + ", artist=" + artist
				+ ", url=" + url + ", duration=" + duration + ", size=" + size
				+ "]";
	}
	
	
	
}
