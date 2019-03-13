package com.koco.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 属性为 private StringProperty musicname;//歌名 private StringProperty
 * musicauthor;//演唱者 private DoubleProperty musictime;//歌曲长度 private
 * StringProperty musicarrangement;//作曲 private StringProperty musicspecial;//专辑
 * private StringProperty musicmp3;//下载路径 private StringProperty
 * musicpath;//本地路径 private IntegerProperty i;//本地音乐标识
 * 
 * @author koco
 *
 */
public class music {

	private StringProperty musicname;// 歌名
	private StringProperty musicauthor;
	private StringProperty musictime;
	private StringProperty musicarrangement;
	private StringProperty musicspecial;
	private StringProperty musicmp3;
	private StringProperty musicpath;
	private IntegerProperty i;

	public music() {
		super();
		this.musicname = new SimpleStringProperty("没有找到......");
		this.musicauthor = new SimpleStringProperty("没有找到......");
		this.musictime = new SimpleStringProperty("没有找到......");
		this.musicarrangement = new SimpleStringProperty("没有找到......");
		this.musicspecial = new SimpleStringProperty("没有找到......");
		this.musicmp3 = new SimpleStringProperty("没有找到......");
		this.musicpath = new SimpleStringProperty("没有找到......");
		this.i = new SimpleIntegerProperty(0);
	}

	/**
	 * 下载视图 private StringProperty musicname;//歌名 private StringProperty
	 * musicauthor;//演唱者 private DoubleProperty musictime;//歌曲长度 private
	 * StringProperty musicarrangement;//作曲 private StringProperty musicspecial;//专辑
	 * private StringProperty musicmp3;//下载路径 private StringProperty
	 * musicpath;//本地路径 private IntegerProperty i;//本地音乐标识
	 * 
	 * @param musicname
	 * @param musicauthor
	 * @param musictime
	 * @param musicarrangement
	 * @param musicspecial
	 * @param musicmp3
	 */
	public music(String musicname, String musicauthor, String musictime, String musicarrangement, String musicspecial,
			String musicmp3) {
		super();
		this.musicname = new SimpleStringProperty(musicname);
		this.musicauthor = new SimpleStringProperty(musicauthor);
		this.musictime = new SimpleStringProperty(musictime);
		this.musicarrangement = new SimpleStringProperty(musicarrangement);
		this.musicspecial = new SimpleStringProperty(musicspecial);
		this.musicmp3 = new SimpleStringProperty(musicmp3);
		this.i = new SimpleIntegerProperty(0);
		this.musicpath = new SimpleStringProperty("没有找到......");
	}

	/**
	 * 本地音乐视图 private StringProperty musicname;//歌名 private StringProperty
	 * musicauthor;//演唱者 private DoubleProperty musictime;//歌曲长度 private
	 * StringProperty musicarrangement;//作曲 private StringProperty musicspecial;//专辑
	 * private StringProperty musicmp3;//下载路径 private StringProperty
	 * musicpath;//本地路径 private IntegerProperty i;//本地音乐标识
	 * 
	 * @param musicname
	 * @param musicauthor
	 * @param musictime
	 * @param musicarrangement
	 * @param musicspecial
	 * @param musicpath
	 * @param i
	 */
	public music(String musicname, String musicauthor, String musictime, String musicarrangement, String musicspecial,
			String musicpath, int i) {
		super();
		this.musicname = new SimpleStringProperty(musicname);
		this.musicauthor = new SimpleStringProperty(musicauthor);
		this.musictime = new SimpleStringProperty(musictime);
		this.musicarrangement = new SimpleStringProperty(musicarrangement);
		this.musicspecial = new SimpleStringProperty(musicspecial);
		this.musicmp3 = new SimpleStringProperty("没有找到......");
		this.musicpath = new SimpleStringProperty(musicpath);
		this.i = new SimpleIntegerProperty(1);
	}

	public String getMusicpath() {
		return musicpath.get();
	}

	public void setMusicpath(String musicpath) {
		this.musicpath.set(musicpath);
	}

	public Integer getI() {
		return i.get();
	}

	public void setI(Integer i) {
		this.i.set(i);
	}

	public StringProperty getMusicpathProperty() {
		return musicpath;
	}

	public void setMusicpathProperty(StringProperty musicpath) {
		this.musicpath = musicpath;
	}

	public IntegerProperty getIProperty() {
		return i;
	}

	public void setIProperty(IntegerProperty i) {
		this.i = i;
	}

	public String getMusicname() {
		return musicname.get();
	}

	public StringProperty getMusicnameProperty() {
		return musicname;
	}

	public StringProperty getMusicauthorProperty() {
		return musicauthor;
	}

	public String getMusicauthor() {
		return musicauthor.get();
	}

	public StringProperty getMusictimeProperty() {
		return musictime;
	}

	public String getMusictime() {
		return musictime.get();
	}

	public StringProperty getMusicarrangementProperty() {
		return musicarrangement;
	}

	public String getMusicarrangement() {
		return musicarrangement.get();
	}

	public StringProperty getMusicmp3Property() {
		return musicmp3;
	}

	public String getMusicmp3() {
		return musicmp3.get();
	}

	public StringProperty getMusicspecialProperty() {
		return musicspecial;
	}

	public String getMusicspecial() {
		return musicspecial.get();
	}

	public void setMusicspecial(StringProperty musicspecial) {
		this.musicspecial = musicspecial;
	}

	public void setMusicspecial(String musicspecial) {
		this.musicspecial.set(musicspecial);
	}

	public void setMusicname(String musicname) {
		this.musicname.set(musicname);
	}

	public void setMusicauthor(String musicauthor) {
		this.musicauthor.set(musicauthor);
	}

	public void setMusictime(String musictime) {
		this.musictime.set(musictime);
	}

	public void setMusicarrangement(String musicarrangement) {
		this.musicarrangement.set(musicarrangement);
	}

	public void setMusicmp3(String musicmp3) {
		this.musicmp3.set(musicmp3);
	}

	@Override
	public String toString() {
		return "Music [musicname=" + musicname + ", musicauthor=" + musicauthor + ", musictime=" + musictime
				+ ", musicarrangement=" + musicarrangement + ", musicspecial=" + musicspecial + ", musicmp3=" + musicmp3
				+ "]";
	}

}
