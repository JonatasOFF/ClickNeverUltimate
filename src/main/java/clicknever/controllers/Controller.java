package clicknever.controllers;

import clicknever.callback.CallBack;
import clicknever.handles.Handles;
import clicknever.models.controllers.ControllerKeyBoard;
import clicknever.models.controllers.mouse.ControllerMouse;
import clicknever.models.controllers.mouse.MouseClicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static clicknever.callback.CallBack.timeForWait;

import static clicknever.models.controllers.mouse.MouseClicker.*;


public class Controller implements Initializable {

    public static int velocidade = 0;

    @FXML
    public Label lbStatus;
    @FXML
    public TextField tfTimeWait;
    @FXML
    public RadioButton rb1;
    @FXML
    public RadioButton rb2;
    @FXML
    public RadioButton rb3;
    @FXML
    public RadioButton rb4;
    @FXML
    public ToggleGroup groupVelocidade;
    @FXML
    public Text lbText;
    @FXML
    public TextField tfVezes;
    @FXML
    public TextField tfH;
    @FXML
    public TextField tfM;
    @FXML
    public TextField tfS;
    @FXML
    public Label lbTime;


    private CallBack cb = new CallBack();




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        try {
            new ControllerKeyBoard().activeKey(true);
            new ControllerMouse().activeKey(true);
        } catch (NativeHookException e) {
            e.printStackTrace();
        }


        MouseClicker.active();
        Handles handle = new Handles(groupVelocidade);
        handle.setRadioButtonsByHandle(rb1,rb2,rb3,rb4);
        handle.setTextsBeenNotHaveLetters(tfH,tfM,tfS,tfVezes,tfTimeWait);
        timeForWait = Integer.parseInt(tfTimeWait.getText());
    }


    @FXML
    public void startClickers() {
        if(!isClickerTime || !isClickerVezes) {
            lbStatus.setText("ClickerVezes");
            isClickerVezes = true;
            cb.timeForStart(this.lbText, () -> clickerVezes(lbText, Integer.parseInt(tfVezes.getText())));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Outro Clicker");
            alert.setContentText("Outro Clicker já está em execução. Pare o atual para fazer esse funcionar");
        }
    }

    @FXML
    public void startClickersTime() {
        if(!isClickerTime || !isClickerVezes) {
            int h = Integer.parseInt(tfH.getText());
            int m = Integer.parseInt(tfM.getText());
            int s = Integer.parseInt(tfS.getText());
            lbStatus.setText("ClickerTime");
            isClickerTime = true;
            cb.timeForStart(this.lbText, () -> clickerTime(h,m,s,this.lbTime,lbText));

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Outro Clicker");
            alert.setContentText("Outro Clicker já está em execução. Pare o atual para fazer esse funcionar");
        }


    }
}
