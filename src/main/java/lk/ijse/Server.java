/* Created By Sithira Roneth
 * Date :1/12/24
 * Time :09:34
 * Project Name :Chat
 * */
package lk.ijse;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    @FXML
    private JFXComboBox<String> cmbEmoji;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtMsg;
    @FXML
    private TextArea txtArea;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message="";


    public void initialize() throws IOException {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(3000);
                txtArea.appendText("Online");

                Socket socket = serverSocket.accept();
                txtArea.appendText("\nClient Online");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (!message.equals("exit")) {
                    message = dataInputStream.readUTF();
                    txtArea.appendText("\nClient :" + message);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        cmbEmoji.getItems().addAll("😊", "😂", "😍", "👍", "🎉");

        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    btnSendOnAction(new ActionEvent());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        String emojiMessage = setEmoji(actionEvent);
        String textMessage = txtMsg.getText().trim();

        String combinedMessage = textMessage + (emojiMessage != null ? " " + emojiMessage : "");

        dataOutputStream.writeUTF(combinedMessage);
        dataOutputStream.flush();
        clearFiled();
    }
    public void clearFiled(){
        txtMsg.setText("");
        cmbEmoji.setValue("");
    }
    public String setEmoji(ActionEvent actionEvent){
        String selectedEmoji = cmbEmoji.getValue();

        if (selectedEmoji != null && !selectedEmoji.isEmpty()) {
            txtArea.appendText("\nServer: " + selectedEmoji);

            try {
                dataOutputStream.writeUTF(selectedEmoji);
                dataOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return selectedEmoji;
    }
}
