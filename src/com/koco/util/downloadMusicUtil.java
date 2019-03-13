package com.koco.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 下载网络文件
 * @author koco
 *
 */
public class downloadMusicUtil {
	
	private String musicDString;
	private String mudicUrlString;
	private URL url;
	private File file;
	private FileOutputStream fileOutputStream;
	private int bytesum = 0;
	private int byteread = 0;

	/**
	 * 一个文件地址
	 * 一个请求地址
	 * @param musicDString
	 * @param mudicUrlString
	 */
	public downloadMusicUtil(String musicDString, String mudicUrlString) {
		super();
		this.musicDString = musicDString;
		this.mudicUrlString = mudicUrlString;
		downloadNet();
	}

	private void downloadNet(){
		// 下载网络文件
		
		try {
			url = new URL(mudicUrlString);
		} catch (MalformedURLException e1) {
//			Alert alert = new Alert(AlertType.WARNING);
//			alert.setHeaderText("输入错误");
//			alert.setContentText(musicmp3Field.getText()+"\n------"+this.mainapp.filePathUtil.getPath().getDownloadpathString()+File.separatorChar);
//			alert.showAndWait();
			e1.printStackTrace();
		}

		try {
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			file = new File(musicDString);
			if (file.isFile()) {
				file.delete();
			}
			file.createNewFile();
			fileOutputStream = new FileOutputStream(musicDString);

			byte[] buffer = new byte[1024];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				System.out.println(bytesum);
				fileOutputStream.write(buffer, 0, byteread);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
