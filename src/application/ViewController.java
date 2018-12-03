package application;



import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class ViewController  implements Initializable  {
	int flag = 0; // Cờ kiểm tra xem có bài hát nào đã được bật lên không 
	int flag1 = 0;
	File[] listFile ;
	int i = 0;
	File file1;
	int lengthListFile;
	String number = ""; // tao bien String de chua id cua button 
	int numberInt = 0;  // bien chua id cua button kieu int
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	@FXML
	private Slider slider;// thanh am luong
	@FXML
	private Slider seeSlider;// thanh tua nhanh
	@FXML
	private MediaPlayer mediaPlayer;
	@FXML
	private MediaView mediaShow;
	private String filePath;
	@FXML
	private Button Library;
	@FXML
	private Button Stop;
	@FXML
	private Button Prev;
	@FXML
	private Button Play;
	@FXML
	private Button Next;
	@FXML
	private Button repeat;
	@FXML
	private Button Folder;
	@FXML
	private Button Songs;
	@FXML
	private ScrollPane scrollPane;
	
	
	@FXML
	private void handlerButtonAction(ActionEvent event) {
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter filter= new FileChooser.ExtensionFilter("select file(*.mp3);(*.mp4);(*.wav);(*.wma)", "*.mp3","*.mp4","*.wav","*.wma" );
			fileChooser.getExtensionFilters().add(filter);
			File file = fileChooser.showOpenDialog(null);
			if(file != null) {
				if(flag == 1) {
					mediaPlayer.stop();
				}
				if(flag1 == 1) {
					mediaPlayer.stop();
				}
				filePath = file.toURI().toString();
				Media media = new Media(filePath);	
				mediaPlayer = new MediaPlayer(media);
				mediaShow.setMediaPlayer(mediaPlayer);
				mediaShow.setX(400);
	        	mediaShow.setY(350);
	        
	        	//scrollPane.setVisible(false);
	        	flag = 1;
					
	}else {
		
		System.out.println("nothing");
	}
	}
	 @FXML
	 private void playButton(ActionEvent event) {
		//Status t = mediaPlayer.getStatus();
		  if(mediaPlayer.getStatus() == Status.PLAYING) {
			  mediaPlayer.pause();
			  Play.setId("Pause");
		  }
		 else {
			 mediaPlayer.play();
			 Play.setId("Play");
		 }
	 }
	 @FXML
	 private void stopButton(ActionEvent event) {
		 mediaPlayer.stop();
	 }
	 @FXML
	 private void chooseFolder(ActionEvent event) {
		 DirectoryChooser directoryChooser = new DirectoryChooser();
		 File dir = directoryChooser.showDialog(null);
		 VBox re = new VBox();
		 scrollPane.setContent(re);
		 re.setSpacing(0);
		 if (dir != null) {
			 listFile = dir.listFiles();
			 lengthListFile = listFile.length; // lay chieu dai cua danh sach bai hat
			 Media media = new Media(listFile[0].toURI().toASCIIString()); // lấy path của file đầu tiên làm mặc định
			 if(flag != 1) {
				 mediaPlayer = new MediaPlayer(media);
				 mediaShow.setMediaPlayer(mediaPlayer);
			 }
			 String m = "trang";
			 String Hover = "-fx-border-width:0;-fx-background-color: #a29bfe";
			 for(File file: listFile) {
				 String styles1 = "-fx-border-width:0;-fx-background-color: transparent;";
				 String styles2 ="-fx-border-width:0;-fx-background-color: #dfe6e9;";
				 Button button = new Button(); //tạo nút mới
        		 button.setText(file.getName()); // đặt tên nút
        		 button.setMaxSize(scrollPane.getPrefWidth(),40 );
        		 //button.setTextAlignment(TextAlignment.LEFT);
        		 switch(m) {
        		 case "trang":
        			 button.setStyle(styles1);
        			 m = "den";
        			 break;
        		 case "den":
        			 button.setStyle(styles2);
        			 m = "trang";
        			 break;
        		 }
        		 String temp = button.getStyle();
        		 button.setOnMouseEntered(e -> button.setStyle(Hover));
        		 button.setOnMouseExited(e -> button.setStyle(temp));
        		 button.setId(Integer.toString(i));
        		 button.setOnAction(click->{   // tạo một event trong nút 
         		 mediaPlayer.stop(); // dừng cái đang phát hiện tại
         		 mediaPlayer = new MediaPlayer(new Media(file.toURI().toASCIIString())); //đổi thành media mới
         		 Play.setId("Play");
      			 mediaPlayer.play();
      			 number = button.getId();// lay id cua button 
      			 numberInt = Integer.parseInt(number);
      			 flag1 = 1;
         		 });
        		 re.getChildren().add(button); // tạo nút xong xuôi thì thêm vào VBOX -> quay lại dòng 90
        		 i = i+1;
			 }
         	
         } else {
             System.out.println("nothing");
         }
     }
	 @FXML
	 private void showSong(ActionEvent event) {
		 System.out.println("you click me");
		 scrollPane.setVisible(true);
	 }
	 @FXML
	 private void playNext(ActionEvent event) {
		if(numberInt !=(lengthListFile-1) ) { // kiem tra id button cung tuc la thu tu cua bai hat co nam o cuoi cung hay khong
		numberInt = numberInt +1; // neu bai khong nam o cuoi thi so thu tu bai hat cong them 1
		mediaPlayer.stop(); // dung bai hat hien tai
		mediaPlayer = new MediaPlayer(new Media(listFile[numberInt].toURI().toString()));
	
		mediaPlayer.play();// play bai ke tiep
		}else {
			numberInt = 0; // neu thu tu bai hat la cuoi cung thi gan id button bang khong de play bai hat dau tien
			mediaPlayer.stop();
			mediaPlayer = new MediaPlayer(new Media(listFile[numberInt].toURI().toString()));
			
			mediaPlayer.play();// play bai hat dau tien trong danh sach
		}
		 
	 }
	 @FXML
	 private void playPrev(ActionEvent event) {
		 if(numberInt != 0) {// kiem tra id button cung tuc la thu tu bai hat co phai la bai hat dau tien trong danh sach khong 
		 numberInt = numberInt -1;// neu khong phai thi so thu tu bai hat tru di 1
		 mediaPlayer.stop();// dung bai hat hien tai
		 mediaPlayer = new MediaPlayer(new Media(listFile[numberInt].toURI().toString()));
		
		 mediaPlayer.play();// play bai hat da phat luc truoc
		 }else {
			 numberInt = lengthListFile-1;// neu thu tu bai hat la dau tien thi so thu tu bai hat se bang cheu dai danh sach bai hat tru di 1
			 mediaPlayer.stop();
			 mediaPlayer = new MediaPlayer(new Media(listFile[numberInt].toURI().toString()));
			
			 mediaPlayer.play();// play bai hat cuoi cung
		 }
		 
		 
	 }
	
		 
	 }
	 		


