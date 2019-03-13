package com.koco.util;

import com.koco.model.music;
import com.koco.musicutil.qqmusic.QQmusic;

import javafx.collections.ObservableList;

public class MusicMp3Util {
	private ObservableList<music> musiclist;
	private int number = 20;
	private String musicName;
	
	public MusicMp3Util() {
		super();
	}
	public MusicMp3Util(String musicName) {
		super();
		this.musicName = musicName;
	}
	/**
	 * 获取歌曲
	 * @return
	 */
	public ObservableList<music> getMusiclist() {
		QQmusic qmusic=new QQmusic(musicName);
		qmusic.setCOUNT(number);
		this.musiclist=qmusic.getMusiclistI();
		return this.musiclist;
	}
	/**
	 *设置歌曲的数量
	 * @param number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

}
