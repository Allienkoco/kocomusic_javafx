package com.koco.musicutil.qqmusic;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils.Null;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.koco.model.music;
import com.koco.model.userAgentUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QQmusic {
	// 获取歌曲的数量
	private int number = 20;
	// 获取歌曲列表
	private final static String MUSIC_LIST_URL = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?cr=1&catZhida=1&n=NUMBER&w=MUSIC_LIST_URL";
	// 获取单独歌曲的下载信息
	private final static String MUSIC_DOWN_URL = "https://u.y.qq.com/cgi-bin/musicu.fcg?data={\"req\":{\"module\":\"CDN.SrfCdnDispatchServer\",\"method\":\"GetCdnDispatch\",\"param\":{\"guid\":\"0\",\"calltype\":0,\"userip\":\"\"}},\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"0\",\"songmid\":[\"MUSIC_DOWN_URL\"],\"songtype\":[0],\"uin\":\"0\",\"loginflag\":1,\"platform\":\"20\"}},\"comm\":{\"uin\":0,\"format\":\"json\",\"ct\":24,\"cv\":0}}";
	private static String MUSIC_DOWNLOAD_URL = "http://dl.stream.qqmusic.qq.com";// or
																					// http://isure.stream.qqmusic.qq.com/";

	private ObservableList<music> musiclistI = FXCollections.observableArrayList();
	// 歌曲名称
	private String musicname = "";
	// 参数
	private String body = "";
	// 浏览器标识
	private userAgentUtil userAgents = new userAgentUtil();
	private String userAgent;
	// json数据初始化
	private JSONObject jsonbody;
	private JSONArray jsonMiusicListArray;
	private JSONObject jsonObjectMusic;
	private String song_mid = "";
	private music music;

	/**
	 * 初始化歌曲名称参数
	 * 
	 * @param nameString
	 */
	public QQmusic(String musicname) {
		super();
		this.musicname = musicname;
	}

	/**
	 * 设置搜索结果中音乐的个数
	 * 
	 * @param cOUNT
	 */
	public void setCOUNT(int number) {
		this.number = number;
	}

	/**
	 * 返回结果--》歌曲列表
	 * 
	 * @return
	 */
	public ObservableList<music> getMusiclistI() {
		getMusiclistfromI();
		return musiclistI;
	}

	/**
	 * 获取歌曲列表
	 */
	private void getMusiclistfromI() {
		userAgent = userAgents.getUserAgent();
		try {
			// System.out.println(MUSIC_LIST_URL.replace("MUSIC_LIST_URL", this.musicname));
			Response res = Jsoup
					.connect(MUSIC_LIST_URL.replace("MUSIC_LIST_URL", this.musicname).replace("NUMBER",
							String.valueOf(number)))
					.userAgent(userAgent).timeout(10000).ignoreContentType(true).execute();

			body = res.body().replace("callback(", "");
			body = body.substring(0, body.length() - 1);
			jsonbody = JSONObject.fromObject(body);

			// System.out.println("code:" + jsonbody.get("code"));

			if ("0".equals(jsonbody.get("code").toString())) {
				// 获取结果
				// System.out.println(jsonbody.toString());
				getmMusicListI(jsonbody);
			} else {
				// 没有获取结果
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("网络请求");
				alert.setContentText("请求地址出错");
				alert.showAndWait();
			}
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("网络请求");
			alert.setContentText("请求地址出错");
			alert.showAndWait();
			e.printStackTrace();
		}

	}

	/**
	 * 解析音乐列表中的音乐，获取每一首音乐的信息
	 * 
	 * @param body
	 */
	private void getmMusicListI(JSONObject jsonbody) {
		this.jsonMiusicListArray = jsonbody.getJSONObject("data").getJSONObject("song").getJSONArray("list");
		int size = jsonMiusicListArray.size();
		for (int i = 0; i < size; i++) {
			this.jsonObjectMusic = jsonMiusicListArray.getJSONObject(i);
			this.music = new music(jsonObjectMusic.get("songname").toString(), jsonObjectMusic.getJSONArray("singer").getJSONObject(0).get("name").toString(), (Integer) jsonObjectMusic.get("interval") / 60 + ":"
							+ (Integer) jsonObjectMusic.get("interval") % 60, musicname, jsonObjectMusic.get("albumname").toString(), "");
			
			this.song_mid = jsonObjectMusic.get("songmid").toString();
			// System.out.println(" 歌名:"+ jsonObjectMusic.get("songname") + " song_mid:" +
			// jsonObjectMusic.get("songmid"));
			getSongDownLodingI();
		}

	}

	/**
	 * 获取单个音乐的下载地址
	 */
	private void getSongDownLodingI() {
		try {
			// System.out.println(MUSIC_DOWN_URL.replace("MUSIC_DOWN_URL", this.song_mid));
			Response res = Jsoup.connect(MUSIC_DOWN_URL.replace("MUSIC_DOWN_URL", this.song_mid)).userAgent(userAgent)
					.timeout(10000).ignoreContentType(true).execute();

			this.body = res.body();
			this.jsonbody = JSONObject.fromObject(body);

			// System.out.println("code:" + jsonbody.get("code"));

			if ("0".equals(jsonbody.get("code").toString())) {
				// 获取结果
				this.music.setMusicmp3(MUSIC_DOWNLOAD_URL + "/" + jsonbody.getJSONObject("req_0").getJSONObject("data")
						.getJSONArray("midurlinfo").getJSONObject(0).get("purl").toString());
				this.musiclistI.add(this.music);
				// System.out.println(this.music);

			} else {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("网络请求");
				alert.setContentText("获取下载地址出错");
				alert.showAndWait();
			}
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("网络请求");
			alert.setContentText("获取下载地址出错");
			alert.showAndWait();
			e.printStackTrace();
			
		}

	}

	// 测试数据
	public static void main(String[] args) {
		QQmusic qmusic = new QQmusic("zhoujielun");
		qmusic.setCOUNT(2);
		ObservableList<music> musiclistI = qmusic.getMusiclistI();
		for (music music : musiclistI) {
			System.out.println(music.getI());
		}

	}

}
