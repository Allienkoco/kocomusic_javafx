package com.koco.util;

import java.io.File;

import com.koco.model.music;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 根据文件夹路径，获取音乐的列表
 * 
 * @author koco
 *
 */
public class musiclistUtil {

	private static final int ObservableList = 0;
	private ObservableList<music> musiclist = FXCollections.observableArrayList();
	private ObservableList<music> musiclistI= FXCollections.observableArrayList();
	private MusicMp3Util mp3Util;

	/**
	 * 测试使用
	 * 
	 * @return
	 */
	public ObservableList<music> getMusicList() {

		return musiclist;
	}

	/**
	 * 设置文件路径 获取文件的music列表
	 * 
	 * @param pathurl
	 */
	public void setMusicList(String pathurl) {
		System.out.println("更新音乐的文件路径-------------------------------" + pathurl);
		musiclist.clear();
		File file = new File(pathurl);
		File[] listFiles = file.listFiles();
		for (File musicFile : listFiles) {
			String musicnameString = musicFile.getName();
			String musictypeString = musicnameString.substring(musicnameString.length() - 4, musicnameString.length())
					.toLowerCase();
			// System.out.println(musictypeString);
			if (musictypeString.equals(".mp3")) {
				musicnameString = musicnameString.substring(0, musicnameString.length() - 4);
				musiclist.add(new music(musicnameString, musicnameString, musicnameString, musicnameString,
						musicnameString, musicFile.getPath(), 1));
			} else if (musictypeString.equals("flac")) {
				musicnameString = musicnameString.substring(0, musicnameString.length() - 5);
				musiclist.add(new music(musicnameString, musicnameString, musicnameString, musicnameString,
						musicnameString, musicFile.getPath(), 1));
			}

		}
		if (musiclist.isEmpty()) {
			musiclist.add(new music("原点", "", "", "", "", "file:resources/music/原点.mp3", 1));
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("路径错误");
			alert.setContentText(pathurl + "文件夹为空");
			alert.showAndWait();
		}
	}

	/**
	 * 根据名称获取music列表
	 * 
	 */
	public void setMusicListI(String musicname) {
		mp3Util = new MusicMp3Util(musicname);
		mp3Util.setNumber(20);
		musiclistI = mp3Util.getMusiclist();
		if (musiclistI.isEmpty()) {
			musiclist.add(new music("原点", "", "", "", "", "file:resources/music/原点.mp3", 1));
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("搜索结果");
			alert.setContentText("为空");
			alert.showAndWait();
		} else {
			musiclist = musiclistI;
		}
	}
}
