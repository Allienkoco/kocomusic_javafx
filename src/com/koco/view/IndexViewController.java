package com.koco.view;

import java.io.File;
import java.time.Duration;

import javax.security.auth.kerberos.KerberosKey;

import com.koco.Main;
import com.koco.model.music;
import com.koco.util.MusicMp3Util;
import com.koco.util.downloadMusicUtil;
import com.koco.util.musiclistUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class IndexViewController {
	@FXML
	private TableView<music> musicTable;
	@FXML
	private TableColumn<music, String> musicNameColumn;
	@FXML
	private TableColumn<music, String> musicAuthorColumn;
	@FXML
	private TableColumn<music, String> musicTimeColumn;
	@FXML
	private TableColumn<music, String> musicArrangementColumn;
	@FXML
	private TableColumn<music, String> musicSpecialColumn;
	@FXML
	private ToggleButton toggleButton_1;
	@FXML
	private ToggleButton toggleButton_2;
	@FXML
	private Button settingButton;
	@FXML
	private Button rightButton;
	@FXML
	private Button leftButton;
	@FXML
	private Button startButton;
	@FXML
	private Button searchButton;
	@FXML
	private Button downloadButton;
	@FXML
	private Slider musicslider;
	@FXML
	private TextField textField;
	@FXML
	private Label gotimeLabel;

	@FXML
	private Label musicNameLabel;
	@FXML
	private Label musicAuthorLabel;
	@FXML
	private Label musictimeLabel;
	@FXML
	private Label musicArrangementLabel;
	@FXML
	private TextField musicmp3Field;

	private Main mainapp;
	private ObservableList<music> musicData;
	private ToggleGroup toggleGroup;

	// 音乐播放
	private MediaPlayer mediaPlayer;
	private boolean doingmusic = false;// 是否有音乐播放
	private boolean start = false;// 音乐的状态
	private music doingMusic;
	private downloadMusicUtil dMusicUtil;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public IndexViewController() {

	}

	/**
	 * 设置本地和网络 按钮互斥
	 */
	private void setToggleGroup() {
		this.toggleGroup = new ToggleGroup();
		toggleButton_1.setToggleGroup(toggleGroup);
		toggleButton_2.setToggleGroup(toggleGroup);
	}

	/**
	 * 播放音乐
	 * 
	 * @param music
	 */
	private void musicplay(music music) {
		if (music != null) {
			if (doingmusic) {
				this.mediaPlayer.dispose();
			}
			if (music.getI() == 1) {
				// String
				// path=IndexOutOfBoundsException.class.getResource(music.getMusicpath()).toString();
				// String path=new File(music.getMusicpath().toString()).toURI().toString();
				this.mediaPlayer = new MediaPlayer(
						new Media(new File(music.getMusicpath().toString()).toURI().toString()));
			} else {
				this.mediaPlayer = new MediaPlayer(new Media(music.getMusicmp3()));
			}
			doingmusic = true;
			start = true;
			doingMusic = music;
			this.mediaPlayer.setAutoPlay(true);
			System.out.println(this.mediaPlayer.getStopTime().toMinutes());
//			musicslider.setMin(0.0);
//			musicslider.setMax(this.mediaPlayer.getStopTime().toMinutes());
//			musictimeLabel.setText(this.mediaPlayer.getStopTime().toMinutes() + "s");
//
//			musicslider.setValue(this.mediaPlayer.getCurrentTime().toMinutes());
//			gotimeLabel.setText(musicslider.getValue() + "s");
		}

	}

	/**
	 * 初始化界面状态
	 */
	@FXML
	private void initialize() {
		
		musicNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMusicnameProperty());
		musicAuthorColumn.setCellValueFactory(cellData -> cellData.getValue().getMusicauthorProperty());
		musicTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getMusictimeProperty());
		musicArrangementColumn.setCellValueFactory(cellData -> cellData.getValue().getMusicarrangementProperty());
		musicSpecialColumn.setCellValueFactory(cellData -> cellData.getValue().getMusicspecialProperty());

		setToggleGroup();
		//清空列表
		showMusicDetails(null);

		musicTable.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				if (event.getClickCount() == 2) {
					System.out.println("shuangji");
					// 双击添加到下面的播放中去
					if (doingmusic) {
						mediaPlayer.dispose();
					}
					musicplay(musicTable.getSelectionModel().getSelectedItem());

				}
				showMusicDetails(musicTable.getSelectionModel().getSelectedItem());
				

			}
		});
	}

	/**
	 * 设置列表中的内容
	 */
	public void setMusicList(Main mainapp) {
		this.mainapp = mainapp;
		this.musicData = mainapp.getMusicData();
		musicTable.setItems(musicData);
	}

	/**
	 * 将信息显示到右边展示出来
	 */
	private void showMusicDetails(music music) {
		if (music != null) {
			// Fill the labels with info from the person object.
			musicNameLabel.setText(music.getMusicname());
			musicAuthorLabel.setText(music.getMusicauthor());
			musicArrangementLabel.setText(music.getMusicarrangement());
			if (music.getI() == 0) {
				musicmp3Field.setText(music.getMusicmp3());
			}else {
				musicmp3Field.setText(music.getMusicpath());
			}	

		} else {
			musicNameLabel.setText("");
			musicAuthorLabel.setText("");
			musicArrangementLabel.setText("");
			musicmp3Field.setText("");
		}
	}

	/**
	 * 点击设置按钮
	 */
	@FXML
	private void handleSettingButton() {
		// ----------------------------------------------------------文件配置
		String musicpath = this.mainapp.filePathUtil.getPath().getMusicpathString();
		String downloadpath = this.mainapp.filePathUtil.getPath().getDownloadpathString();
		boolean okClicked = mainapp.showSettingDialog(musicpath, downloadpath);
		if (okClicked) {
			// 配置信息更新，刷新音乐列表
			this.mainapp.flashView("");
		}
	}

	/**
	 * 点击搜索按钮
	 */
	@FXML
	private void handleSearchButton() {
		if (toggleButton_2.isSelected()&&!("".equals(textField.getText()))&&textField.getText()!=null) {
			System.out.println(textField.getText());
			this.mainapp.flashView(textField.getText());	
		}
	}

	/**
	 * 点击下载按钮
	 */
	@FXML
	private void handleDownloadButton() {
		
		if (toggleButton_2.isSelected()&&musicmp3Field.getText().indexOf("http://")!=-1) {
			dMusicUtil=new downloadMusicUtil(this.mainapp.filePathUtil.getPath().getDownloadpathString()+File.separatorChar+musicNameLabel.getText()+musicAuthorLabel+".mp3", musicmp3Field.getText());
		}
		//dMusicUtil=new downloadMusicUtil(this.mainapp.filePathUtil.getPath().getDownloadpathString()+File.separatorChar+musicNameLabel.getText()+musicAuthorLabel+".mp3", musicmp3Field.getText());
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("输入错误");
		alert.setContentText(musicmp3Field.getText()+"\n------"+this.mainapp.filePathUtil.getPath().getDownloadpathString()+File.separatorChar);
		alert.showAndWait();
	}

	/**
	 * 点击左,前一首按钮
	 */
	@FXML
	private void handleleftButton() {
		if (doingMusic != null) {
			int i = musicData.size();
			for (int j = i; j % i <= i; j++) {
				if (musicData.get(j % i).equals(doingMusic)) {
					musicplay(musicData.get((j - 1) % i));
					musicTable.getSelectionModel().select(musicData.get((j - 1) % i));
					showMusicDetails(musicData.get((j - 1) % i));
					break;
				}
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("错误信息");
			alert.setContentText("选择音乐,双击进行播放");
			alert.showAndWait();
		}
	}

	/**
	 * 点击播放 暂停按钮
	 */
	@FXML
	private void handlestartButton() {
		if (doingMusic != null) {
			if (start) {
				this.mediaPlayer.pause();
				start = false;
			} else {
				this.mediaPlayer.play();
				start = true;
			}

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("错误信息");
			alert.setContentText("选择音乐,双击进行播放");
			alert.showAndWait();
		}
	}
	/**
	 * 点击右边 后一首
	 */
	@FXML
	private void handlerightButton() {
		if (doingMusic != null) {
			int i = musicData.size();
			for (int j = i; j % i <= i; j++) {
				if (musicData.get(j % i).equals(doingMusic)) {
					musicplay(musicData.get((j + 1) % i));
					//musicTable.getTransforms();
					musicTable.getSelectionModel().select(musicData.get((j + 1) % i));
					showMusicDetails(musicData.get((j + 1) % i));
					break;
				}
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("错误信息");
			alert.setContentText("选择音乐,双击进行播放");
			alert.showAndWait();
		}
	}

}
