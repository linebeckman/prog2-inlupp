package se.su.inlupp;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Gui extends Application {

  private Stage stage;
  private FileChooser fileChooser = new FileChooser();
  private ImageView imgView = new ImageView();

  public void start(Stage primaryStage) {
    Graph<String> graph = new ListGraph<String>();
    stage = primaryStage;

    BorderPane root = new BorderPane();
    Pane center = new Pane();
    center.getChildren().add(imgView);
    root.setCenter(center);
    

    MenuBar menuBar = new MenuBar();

    Menu menu = new Menu("file");
    menuBar.getMenus().add(menu);

    MenuItem newMap = new MenuItem("New map");
    menu.getItems().add(newMap);
    newMap.setOnAction(new LoadMapHandler());
    MenuItem open = new MenuItem("Open");
    menu.getItems().add(open);
    MenuItem save = new MenuItem("Save");
    menu.getItems().add(save);
    MenuItem saveImg = new MenuItem("Save Image");
    menu.getItems().add(saveImg);
    MenuItem exit = new MenuItem("Exit");
    menu.getItems().add(exit);

    Button findPath = new Button("Find Path");
    Button showConn = new Button("Show Connection");
    Button newPlace = new Button("New Place");
    Button newConn = new Button("New Connection");
    Button changeConn = new Button("Change Connection");

    FlowPane buttonPane = new FlowPane(findPath, showConn, newPlace, newConn, changeConn);
    buttonPane.setAlignment(Pos.TOP_CENTER);

    VBox top = new VBox(menuBar, buttonPane);
    root.setTop(top);

    Scene scene = new Scene(root, 640, 480);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  private void open(String filePath) {
    Image img = new Image(filePath);
    imgView.setImage(img);
  }

  class LoadMapHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      File file = fileChooser.showOpenDialog(stage);
      
      if (file != null) {
        open(file.toURI().toString());
        // changed = false;
      }
    
    }
    

  }
}
