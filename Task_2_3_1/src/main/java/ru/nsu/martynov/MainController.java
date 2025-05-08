package ru.nsu.martynov;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class MainController {

    @FXML
    private Button btn1;
    private int x = 0;
    @FXML
    private void click(ActionEvent event) {
        btn1.setText(String.format("%d", ++x));
    }
    @FXML
    private void spend(ActionEvent event) {
        if (x >= 10) {
            btn1.setText(String.format("%d", x-=10));
        }
    }
}
