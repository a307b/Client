package Doctor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Design/UserSearch.fxml"));
        primaryStage.setTitle("User Search");
        primaryStage.setScene(new Scene(root, 840, 580));
        primaryStage.show();
    }
}
