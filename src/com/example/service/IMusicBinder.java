package com.example.service;

public interface IMusicBinder {
	void playMusic(String path);
	void pauseMusic();
	void stopMusic();
	void restartMusic();
	int getPlayProgress();
	void setPlayProgress(int progress);
	boolean isPlaying();
	void playNextMusic();
	void playPreMusic();
	void playMusicByPosition(int position);
	void setPlaybackOrder(int order);
}
