import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class HomeController {

    @FXML
    Label nameLabel;

    @FXML 
    Button deletebutton; 

    @FXML 
    Button updatebutton; 

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String username;

    public void displayName(String username) {
        nameLabel.setText("Hello, " + username);
    }

    public void setUsername(String username) {
        this.username = username;
        System.out.println("Username set: " + this.username);  // Debugging step
    }
    
  @FXML
private void deletebuttonHandler(ActionEvent event) {
    // confirmation dialogue
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete Account");
    alert.setHeaderText("Are you sure you want to permanently delete your account?");
    alert.setContentText("This action cannot be undone.");
    // Wait for user confirmation
    ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

    if (result == ButtonType.OK) {
        if (DatabaseHandler.deleteAccount(username)) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Account Deleted");
            successAlert.setHeaderText("Your account has been successfully deleted.");
            successAlert.showAndWait();
            // Close current window (Home Scene)
            Stage stage = (Stage) deletebutton.getScene().getWindow();
            stage.close();
            // Close the database connection
            DatabaseHandler.closeConnection();
        } else {
            //deletion fails
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Account deletion failed.");
            errorAlert.setContentText("Please try again later.");
            errorAlert.showAndWait();
        }
    }
}
}
//          @FXML
//     private void loginbuttonhandler(ActionEvent event) throws IOException {

//         String password = passwordtextfield.getText();

//         if (DatabaseHandler.validateLogin(username, password)) {
            
//             // // Load Scene2.fxml when login button is clicked
//             // FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
//             // // Load Scene2Controller
//             //  root = loader.load();
//             // HomeController homecontroller = loader.getController();
//             // // Pass username from textfield to displayName() method
//             // homecontroller.displayName(username);

//             // // Load stage and scene
//             //  stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//             //  scene = new Scene(root);
//             // stage.setScene(scene);
//             // stage.show();
//         }
//         else{
//             System.out.println("Unsuccessful");
//          }
//     }
// }
// }
    

    