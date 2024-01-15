/* Created By Sithira Roneth
 * Date :1/16/24
 * Time :00:58
 * Project Name :Chat
 * */
package lk.ijse;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;

public class Login {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtPass;

    String id = "User";
    String pw = "1234";
    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtId.getText().equals(id)) {
            if (txtPass.getText().equals(pw)) {
                root.getChildren().clear();
                root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/server.fxml")));
            }
        }else {
            String error = "Incorrect username or password .\nplease check it";
            new Alert(Alert.AlertType.ERROR,error).show();
        }
    }
}
