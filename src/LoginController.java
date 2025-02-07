import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.sound.midi.SysexMessage;


public class LoginController {
    
    
    @FXML
    private Label usernamelabel;

    @FXML
    private Label passwordlabel;

    @FXML
    private TextField usernametextfield;

    @FXML
    private TextField passwordtextfield;

    @FXML
    private Button loginbutton;

    @FXML
    private ImageView wazelogo;

    @FXML
    private ImageView wazetext;

    @FXML
    private Label faketext;

    @FXML
    private Button createbutton;

    @FXML 
    private Label normaltext;

   private Stage stage;
   private Scene scene;
   private Parent root;

    @FXML
    private void loginbuttonhandler(ActionEvent event) throws IOException {

        String username = usernametextfield.getText();
        String password = passwordtextfield.getText();

        System.out.println("username: " + username);
        System.err.println("password: " + password);

        if (DatabaseHandler.validateLogin(username, password)) {
            //load homepage when 'login' is clicked
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            // Load homecontroller
            root = loader.load();
            HomeController homecontroller = loader.getController();
            // Pass username from textfield to displayName() method
            homecontroller.displayName(username);

            HomeController homeController = loader.getController();
            homeController.setUsername(username); //username is passed to the homecontroller 

            // Load stage and scene
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            System.out.println("Unsuccessful");
        }
    }
}