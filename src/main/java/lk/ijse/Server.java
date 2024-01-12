/* Created By Sithira Roneth
 * Date :1/12/24
 * Time :09:34
 * Project Name :Chat
 * */
package lk.ijse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    @FXML
    private TextField txtMsg;
    @FXML
    private TextArea txtArea;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message="";


    public void initialize(){
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(3000);
                txtArea.appendText("Server Typing");

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
    }
    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtMsg.getText().trim());
        dataOutputStream.flush();
        clearFiled();
    }
    public void clearFiled(){
        txtMsg.setText("");
    }
}
