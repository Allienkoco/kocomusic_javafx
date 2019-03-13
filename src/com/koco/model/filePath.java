package com.koco.model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB的模型类 保存路径信息
 * 
 * @author koco
 *
 */
public class filePath implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1216513213513513351L;
	private String musicpathString;
	private String downloadpathString;
	
	public String getMusicpathString() {
		return musicpathString;
	}

	public void setMusicpathString(String musicpathString) {
		this.musicpathString = musicpathString;
	}

	public String getDownloadpathString() {
		return downloadpathString;
	}

	public void setDownloadpathString(String downloadpathString) {
		this.downloadpathString = downloadpathString;
	}

}
