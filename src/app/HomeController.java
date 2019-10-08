package app;

import com.jfoenix.controls.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import processes.*;

import java.util.ArrayList;
import java.util.Optional;

public class HomeController {

    @FXML
    private JFXButton playButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXButton createButton;
    @FXML
    private JFXButton reviewButton;
    @FXML
    private JFXButton quitButton;
    @FXML
    private TableView videoTableView;
    @FXML
    private JFXButton helpTableView;
    @FXML
    private JFXButton helpDeleteButton;
    @FXML
    private JFXButton helpCreateButton;
    @FXML
    private JFXButton helpPlayButton;
    @FXML
    private JFXButton helpReviewButton;
    @FXML
    private JFXButton helpVarPedia;
    @FXML
    private Label numVideoLabel;
    @FXML
    private StackPane stackPane;

    @FXML
    private void createVideo() {
    	Stage homeStage = (Stage) helpCreateButton.getScene().getWindow();
    	homeStage.hide();

    	Stage creationStage = new WindowBuilder().popWindow("NewVideoCreation", "Create a Video!").getStage();
    	creationStage.setOnCloseRequest(e -> {
    		updateVideoTable();
    		homeStage.show();
    	});
    }
    
    @FXML
    private void playVideo() {
    	VideoCreation videoCreation = (VideoCreation) videoTableView.getSelectionModel().getSelectedItem();
    	String videoName = videoCreation.getName();
    	if(videoName == null) return;

    	WindowBuilder windowBuilder = new WindowBuilder().popWindow("VideoPlayer", "Video Player");
    	FXMLLoader loader = windowBuilder.getLoader();
    	((VideoPlayerController) windowBuilder.getLoader().getController()).setSource(videoName);
    	windowBuilder.getStage().setOnCloseRequest(e -> ((VideoPlayerController) loader.getController()).shutdown());
    }
    
    @FXML
    private void deleteVideo() {
    	VideoCreation videoCreation = (VideoCreation) videoTableView.getSelectionModel().getSelectedItem();
    	String videoName = videoCreation.getName();
    	if(videoName == null) return;
    	System.out.println(videoName);
    	
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletion Process");
        alert.setHeaderText("Deletion Confirmation");
        alert.setContentText("Would you really like to delete " + videoName + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) return;
        
        Task<ArrayList<String>> task = new DeleteVideo(videoName);
        task.setOnSucceeded(event -> updateVideoTable());
        Thread thread = new Thread(task);
        thread.start();
    }

    @FXML
    private void reviewVideos() {

    }

    @FXML
    private void describeVarPedia() {

    }

    @FXML
    private void describeTableView() {

    }

    @FXML
    private void describeCreate() {

    }

    @FXML
    private void describeDelete() {

    }

    @FXML
    private void describePlay() {

    }

    @FXML
    private void describeReview() {

    }

    @FXML
    private void quit() {
    	quitButton.getScene().getWindow().hide();
    }

    private void updateVideoTable() {
        TableColumn<VideoCreation, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(70);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<VideoCreation, String> searchTermColumn = new TableColumn<>("Search Term");
        searchTermColumn.setMinWidth(80);
        searchTermColumn.setCellValueFactory(new PropertyValueFactory<>("searchTerm"));
        
        TableColumn<VideoCreation, String> numImagesColumn = new TableColumn<>("#Images");
        numImagesColumn.setMinWidth(80);
        numImagesColumn.setCellValueFactory(new PropertyValueFactory<>("numImages"));
        
        TableColumn<VideoCreation, String> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setMinWidth(80);
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        videoTableView.getItems().add(new VideoCreation("test", "again", 3));
        videoTableView.getColumns().addAll(nameColumn, searchTermColumn, numImagesColumn, ratingColumn);
    }
    
    @FXML
    private void initialize() {
    	updateVideoTable();
    }
    
    private int countWords(String input) {
        if (input == null || input.isEmpty()) {
          return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
      }

    private JFXDialog loadingDialog(String title) {
    	JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        JFXSpinner spinner = new JFXSpinner();
        spinner.setPrefSize(50, 50);
        dialogContent.setBody(spinner);
        JFXDialog dialog = new JFXDialog(stackPane, dialogContent, JFXDialog.DialogTransition.RIGHT);
        dialog.show();
        return dialog;
    }

}
