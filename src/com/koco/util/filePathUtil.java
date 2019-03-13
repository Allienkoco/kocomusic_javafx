package com.koco.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.koco.model.filePath;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 程序路径的初始化文件 获取歌曲路径和下载路径
 * 
 * @author koco
 *
 */
public class filePathUtil {

	private final String settingpath=System.getProperty("user.dir")+File.separator;
	private filePath filePath;
	private File file;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	/**
	 * 获取路径信息
	 * 
	 * @return
	 */
	public filePath getPath() {
		loadPathFromFile();
		isfileDirectory();
		return this.filePath;
	}
	/**
	 * 
	 * @param path
	 * @return
	 */
	private void isfileDirectory() {
		File file = new File(filePath.getDownloadpathString());
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		File file2 = new File(filePath.getMusicpathString());
		if (!file2.isDirectory()) {
			file2.mkdirs();
		}
	}

	/**
	 * 设置路径信息
	 * 
	 * @param musicpath
	 * @param downloadpath
	 */
	public void setPathList(String musicpath, String downloadpath) {
		this.filePath.setMusicpathString(musicpath);
		this.filePath.setDownloadpathString(downloadpath);
		savePathToFile();
	}

	/**
	 * 加载文件内容
	 * 
	 */
	private void loadPathFromFile() {
		file=new File(settingpath+"Setting.xml");
		
		try {
			if (!file.isFile()) {
				file.createNewFile();
				this.filePath=new filePath();
				this.filePath.setDownloadpathString(settingpath+"music");
				this.filePath.setMusicpathString(settingpath+"music");
				savePathToFile();
			}
			
			objectInputStream=new ObjectInputStream(new FileInputStream(file));
			this.filePath=(filePath) objectInputStream.readObject();
			
			objectInputStream.close();
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("路径错误");
			alert.setContentText("不能加载数据:\n" + file.getPath());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 保存至文件
	 */
	private void savePathToFile() {
		file=new File(settingpath+"Setting.xml");
		try {
			if (file.isFile()) {
				file.delete();
			}
			file.createNewFile();
			
			objectOutputStream=new ObjectOutputStream(new FileOutputStream(file));
			objectOutputStream.writeObject(this.filePath);
			objectOutputStream.close();
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("路径错误");
			alert.setContentText("不能保存数据到:\n" + file.getPath());
			alert.showAndWait();
		}
	}

}
