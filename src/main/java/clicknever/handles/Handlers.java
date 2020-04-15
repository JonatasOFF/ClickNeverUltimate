package clicknever.handles;

import clicknever.models.controllers.Mouse;
import javafx.scene.control.*;

import java.util.Arrays;
import java.util.Collections;

import static clicknever.callback.CallBack.timeForWait;
import static clicknever.controllers.Controller.velocidade;
import static clicknever.models.controllers.mouse.MouseClicker.*;

public class Handlers {

    private TextField tfName;

    private ToggleGroup tgVelocidade;
    private ToggleGroup tgClickOrTime;
    private TextField tfClickersMouseHandler;

    public static int indexAtual = 0;
    private static int indexPara = 0;

    public static boolean isNewMouse = true;

    private TextField tfHMouse;
    private TextField tfSMouse;
    private TextField tfMMouse;
    private RadioButton rbTimeMouse;
    private RadioButton rbClickerMouse;


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
        RadioButton radioButtonInicialization = (RadioButton) tgVelocidade.getSelectedToggle();
        verificarVelocidade(radioButtonInicialization.getId());
        Arrays.asList(nodes).forEach(k -> k.selectedProperty().addListener((observable, oldValue, newValue) -> {

            RadioButton radioButtonNow = (RadioButton) tgVelocidade.getSelectedToggle();
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


    public void setRadioButtonsByClickOrTime(RadioButton ... nodes) {
        Arrays.asList(nodes).forEach(k -> {
            if(k.getId().equals("rbTimeMouse")) {
                rbTimeMouse = k;
            } else {
                rbClickerMouse = k;
            }
            k.selectedProperty().addListener((observable, oldValue, newValue) -> {
                RadioButton radioButtonNow = (RadioButton) tgClickOrTime.getSelectedToggle();
                if(newValue) {
                    switch (radioButtonNow.getId()) {
                        case ("rbTimeMouse"):
                            isTime = true;
                            isClicker = false;
                            rbClickerMouse.selectedProperty().setValue(false);
                            rbTimeMouse.selectedProperty().setValue(true);
                            tfHMouse.setDisable(false);
                            tfMMouse.setDisable(false);
                            tfSMouse.setDisable(false);
                            tfClickersMouseHandler.setDisable(true);
                            break;
                        case ("rbClickerMouse"):
                            isClicker = true;
                            isTime = false;
                            rbClickerMouse.selectedProperty().setValue(true);
                            rbTimeMouse.selectedProperty().setValue(false);
                            tfHMouse.setDisable(true);
                            tfMMouse.setDisable(true);
                            tfSMouse.setDisable(true);
                            tfClickersMouseHandler.setDisable(false);
                            break;
                    }
                    Mouse mouse = indexAtual - 1 == -1 ? mouses.get(0) : mouses.get(indexAtual - 1);
                    mouse.setClick(isClicker);
                    mouse.setTime(isTime);
                    attTableDatas();
                }
            });
        });
    }

    public void setComboBoxForChange(ComboBox... nodes) {
        Arrays.asList(nodes).forEach(k -> {
            if(k.getId().equals("cbIndex")) {
                k.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if(isNewMouse) {
                        indexAtual = newValue != null ? (int) newValue : 0;
                        Mouse mouse = indexAtual - 1 == -1 ? mouses.get(0) : mouses.get(indexAtual - 1);
                        System.out.println("Index que ta seetado PRA SER CHAMADO PORRA " + indexAtual);

                        if (mouse.isClick()) {
                            tfClickersMouseHandler.setText(mouse.getCount());
                            rbClickerMouse.selectedProperty().setValue(true);
                            rbTimeMouse.selectedProperty().setValue(false);
                            tfHMouse.setDisable(true);
                            tfMMouse.setDisable(true);
                            tfSMouse.setDisable(true);
                            tfClickersMouseHandler.setDisable(false);
                        } else {
                            rbClickerMouse.selectedProperty().setValue(false);
                            rbTimeMouse.selectedProperty().setValue(true);
                            tfHMouse.setDisable(false);
                            tfMMouse.setDisable(false);
                            tfSMouse.setDisable(false);
                            tfClickersMouseHandler.setDisable(true);
                            tfHMouse.setText(String.valueOf(mouse.getTime()[0]));
                            tfMMouse.setText(String.valueOf(mouse.getTime()[1]));
                            tfSMouse.setText(String.valueOf(mouse.getTime()[2]));
                        }
                        tfName.setText(mouse.getName());
                        System.out.println("Gato bola " + indexAtual  + " " + mouse.getCount());
                    }

                });
            } else {
                k.valueProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        if (newValue != null && indexAtual != 0 && isNewMouse) {
                            indexPara = (int) newValue;

                            mouses.get(indexAtual - 1).setIndex(indexPara);
                            mouses.get(indexPara - 1).setIndex(indexAtual);
                            Collections.swap(mouses, indexAtual- 1, indexPara - 1);
                            Mouse mouse = indexAtual - 1 == -1 ? mouses.get(0) : mouses.get(indexAtual - 1);
                            System.out.println("Index que ta seetado PRA SER CHAMADO PORRA " + indexPara);

                            if (mouse.isClick()) {
                                tfClickersMouseHandler.setText(mouse.getCount());
                                rbClickerMouse.selectedProperty().setValue(true);
                                rbTimeMouse.selectedProperty().setValue(false);
                                tfHMouse.setDisable(true);
                                tfMMouse.setDisable(true);
                                tfSMouse.setDisable(true);
                                tfClickersMouseHandler.setDisable(false);
                            } else {
                                rbClickerMouse.selectedProperty().setValue(false);
                                rbTimeMouse.selectedProperty().setValue(true);
                                tfHMouse.setDisable(false);
                                tfMMouse.setDisable(false);
                                tfSMouse.setDisable(false);
                                tfClickersMouseHandler.setDisable(true);
                                tfHMouse.setText(String.valueOf(mouse.getTime()[0]));
                                tfMMouse.setText(String.valueOf(mouse.getTime()[1]));
                                tfSMouse.setText(String.valueOf(mouse.getTime()[2]));
                            }
                            tfName.setText(mouse.getName());
                            attTableDatas();
                        }
                    } catch (Exception e ) {e.printStackTrace();}
                });
            }
        });
    }

    public void setTextsBeenNotHaveLetters(TextField... nodes) {
        Arrays.asList(nodes).forEach(k -> {
            this.tfClickersMouseHandler = k.getId().equals("tfClickersMouse") ? k : tfClickersMouseHandler;
            this.tfHMouse = k.getId().equals("tfHMouse") ? k : tfHMouse;
            this.tfMMouse = k.getId().equals("tfMMouse") ? k : tfMMouse;
            this.tfSMouse = k.getId().equals("tfSMouse") ? k : tfSMouse;
            k.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*")) {
                k.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (newValue.matches("\\d\\d\\d") && !k.getId().equals("tfVezes") && !k.getId().equals("tfClickersMouse")) {
                k.setText(k.getText(0, 2) + "");
            }
            if(k.getId().equals("tfTimeWait")) {
                try {
                    timeForWait = Integer.parseInt(k.getText());
                } catch (NumberFormatException ignore) {
                    timeForWait = 5;
                }
            }
            if(k.getId().equals("tfClickersMouse") || k.getId().equals("tfMMouse") || k.getId().equals("tfHMouse") || k.getId().equals("tfSMouse")) {
                    Mouse mouse = indexAtual - 1 == -1 ? mouses.get(0) : mouses.get(indexAtual -1);
                    if (mouse.isClick()) {
                        System.out.println(newValue);
                        mouse.setValue(newValue);
                        attTableDatas();
                    } else {
                        int[] i = {
                                !tfHMouse.getText().equals("") ? Integer.parseInt(tfHMouse.getText()) : 0,
                                !tfMMouse.getText().equals("") ? Integer.parseInt(tfMMouse.getText()) : 0,
                                !tfSMouse.getText().equals("") ? Integer.parseInt(tfSMouse.getText()) : 0};
                        mouse.setTime(i);
                        attTableDatas();
                    }

            }
        });
        });
        tfName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && indexAtual != 0) {
                mouses.get(indexAtual - 1)
                        .setName(newValue);

                attTableDatas();
            }
        });
    }

    public Handlers(ToggleGroup tgVelocidade, ToggleGroup tgClickOrTime, TextField tfName) {
        this.tgVelocidade = tgVelocidade;
        this.tgClickOrTime = tgClickOrTime;
        this.tfName = tfName;
    }
}
