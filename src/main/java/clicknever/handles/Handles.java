package clicknever.handles;

import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.util.Arrays;

import static clicknever.controllers.Controller.velocidade;
import static clicknever.callback.CallBack.timeForWait;

public class Handles {

    private ToggleGroup toggleGroup;


    private void verificarVelocidade(String id) {
        switch (id) {
            case ("rb1"):
                velocidade = 1000;
                System.out.println("1c/s");
                break;
            case ("rb2"):
                velocidade = 100;
                System.out.println("0.1c/s");
                break;
            case ("rb3"):
                velocidade = 10;
                System.out.println("0.01c/s");
                break;
            case ("rb4"):
                velocidade = 1;
                System.out.println("0.001c/s");
                break;
        }
    }

    public void setRadioButtonsByHandle(RadioButton... nodes) {
        RadioButton radioButtonInicialization = (RadioButton) toggleGroup.getSelectedToggle();
        verificarVelocidade(radioButtonInicialization.getId());
        Arrays.asList(nodes).forEach(k -> k.selectedProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton radioButtonNow = (RadioButton) toggleGroup.getSelectedToggle();
            if(newValue) {
                switch (radioButtonNow.getId()) {
                    case ("rb1"):
                        velocidade = 1000;
                        break;
                    case ("rb2"):
                        velocidade = 100;
                        break;
                    case ("rb3"):
                        velocidade = 10;
                        break;
                    case ("rb4"):
                        velocidade = 1;
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Cuidado");
                        alert.setContentText("Essa escolha pode apresentar 'perdas de clickers'");
                        alert.show();
                        break;
                }
            }
        }));
    }

    public void setTextsBeenNotHaveLetters(TextField... nodes) {
        Arrays.asList(nodes).forEach(k -> k.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                k.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (newValue.matches("\\d\\d\\d") && !k.getId().equals("tfVezes")) {
                k.setText(k.getText(0, 2) + "");
            }
            if(k.getId().equals("tfTimeWait")) {
                try {
                    timeForWait = Integer.parseInt(k.getText());
                } catch (NumberFormatException ignore) {
                    timeForWait = 5;
                }
            }
        }));
    }

    public Handles(ToggleGroup toggleGroup) {
        this.toggleGroup = toggleGroup;
    }
}
