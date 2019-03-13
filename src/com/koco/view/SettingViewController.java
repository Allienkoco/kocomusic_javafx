package com.koco.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



import com.koco.Main;
import com.sun.javafx.tk.FileChooserType;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * seting的controller类
 * 
 * @author koco
 *
 */
public class SettingViewController {
	@FXML
	private Button saveButton;
	@FXML
	private Button breakButton;
	@FXML
	private Button musicButton;
	@FXML
	private Button dowloadButton;
	@FXML
	private TextField musicField;
	@FXML
	private TextField dowloadField;

	private Stage dialogStage;
	private String musicPath;
	private String downloadPath;
	private boolean okClicked = false;
	private Main mainapp;

	/**
	 * 绑定初始化
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * 初始化界面
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * 传入 下载路径 和歌曲文件路径 初始化显示界面
	 * 
	 * @param downloadPath 下载路径
	 * @param musicPath    歌曲库路径
	 */
	public void setPath(String downloadPath, String musicPath, Main mainapp) {
		this.mainapp = mainapp;
		musicField.setText(musicPath);
		dowloadField.setText(downloadPath);
		this.musicPath = musicPath;
		this.downloadPath = downloadPath;
	}

	/**
	 * 获取用户的下载路径输入
	 */
	@FXML
	public void getUserDownPath() {
		dowloadField.setText(setPath("下载路径输入",dowloadField));
	}

	/**
	 * 获取用户的乐库路径输入
	 */
	@FXML
	public void getUserMusicsPath() {
		musicField.setText(setPath("乐库路径输入",musicField));
	}

	/**
	 * 弹框修改路径
	 * 
	 * @param 弹窗的名称（标题）
	 */
	public String setPath(String title,TextField textField) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle(title);
		directoryChooser.setInitialDirectory(null);
		File filepathFile = directoryChooser.showDialog(dialogStage);
		if (filepathFile != null) {
			return filepathFile.getPath();
		}
		return textField.getText();
	}

	/**
	 * 是否点击了 保存按钮 返回按钮的状态
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * 点击保存按钮的后续处理.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			this.musicPath = musicField.getText();
			this.downloadPath = dowloadField.getText();
			this.mainapp.filePathUtil.setPathList(this.musicPath, this.downloadPath);
			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * 点击退出的处理
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * 保存时的输入是否合法的检测
	 * 
	 * @return 正确与否
	 */
	private boolean isInputValid() {
		String errorMessage = "";
		if (musicField.getText() == null || musicField.getText().length() == 0) {
			errorMessage += "乐库路径为空了!\n";
		} else {
			if (!isfileDirectory(musicField.getText())) {
				errorMessage += "乐库路径错误!\n";
			}
		}
		if (dowloadField.getText() == null || dowloadField.getText().length() == 0) {
			errorMessage += "下载保存了路径为空了\n";
		} else {
			if (!isfileDirectory(dowloadField.getText())) {
				errorMessage += "下载路径错误!\n";
			}
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// 提示用户输入错误
			System.out.println(errorMessage);
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("输入错误");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}

	/**
	 * 路径String 参数
	 * 
	 * @return 路径信息是否为文件夹
	 */
	private boolean isfileDirectory(String path) {
		File file = new File(path);
		System.out.println("--------------更改的路径信息----------"+file.getPath());
		return file.isDirectory();
	}

}
