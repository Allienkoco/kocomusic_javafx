package com.koco;

import java.io.File;
import java.io.IOException;

import com.koco.model.music;
import com.koco.util.filePathUtil;
import com.koco.util.musiclistUtil;
import com.koco.view.IndexViewController;
import com.koco.view.SettingViewController;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * 主方法，界面的加载和数据的共享
 * 
 * @author koco
 *
 */

public class Main extends Application {
	// 主界面
	private Stage primaryStage;
	private BorderPane rootLayout;

	// 音乐列表
	private ObservableList<music> musicData;

	// 获取音乐列表的类
	private musiclistUtil musiclistutil;
	// 获取配置文件的路径的类
	public filePathUtil filePathUtil;

	// 构造方法
	public Main() {
		System.out.println("------------------------------------------start");
//		this.musiclistutil = new musiclistUtil();
//		this.filePathUtil = new filePathUtil();
//		musicData = musiclistutil.getMusicList();
	}

	// 返回musicData
	public ObservableList<music> getMusicData() {
		return musicData;
	}

	// 开始的界面加载
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("koco Music");
		this.primaryStage.getIcons().add(new Image("file:resources/images/koco.png"));
		//初始化界面
		this.musiclistutil = new musiclistUtil();
		this.filePathUtil = new filePathUtil();
		// musicData = musiclistutil.getMusicList();
		showTopbarView();
		flashView("");
		
	}

	/**
	 * 更新页面
	 */
	public void flashView(String name) {
		// 加载配置文件
		if ("".equals(name)) {
			this.filePathUtil = new filePathUtil();
			this.musiclistutil.setMusicList(this.filePathUtil.getPath().getMusicpathString());
		} else {
			this.musiclistutil.setMusicListI(name);
		}
		musicData = this.musiclistutil.getMusicList();
		showMainView();
	}

	/**
	 * 上面的topbar 可以设置打开关闭保存
	 */
	public void showTopbarView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Topbar.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 主界面，中间的部分
	 */
	public void showMainView() {
		try {
			//
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/index.fxml"));
			AnchorPane MusicOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(MusicOverview);
			IndexViewController controller = loader.getController();
			controller.setMusicList(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param 路径 乐库 和下载路径
	 * @return 关闭界面.
	 */
	public boolean showSettingDialog(String musicpath, String downloadpath) {
		try {
			//
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/SettingView.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// 创建一个画布 并属于原画布，绑定到一起
			Stage dialogStage = new Stage();
			dialogStage.setTitle("设置界面");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SettingViewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPath(downloadpath, musicpath, this);
			// 显示界面
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 返回主画布
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	// 启动方法
	public static void main(String[] args) {
		launch(args);
	}

}
