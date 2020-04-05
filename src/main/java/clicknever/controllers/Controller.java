package clicknever.controllers;

import clicknever.callback.CallBack;
import clicknever.handles.Handlers;
import clicknever.models.controllers.ControllerKeyBoard;
import clicknever.models.controllers.Mouse;
import clicknever.models.controllers.mouse.ControllerMouse;
import clicknever.models.controllers.mouse.MouseClicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public TextField tfName;
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
    @FXML
    public ToggleGroup groupClickerOrTime;
    @FXML
    public RadioButton rbTimeMouse;
    @FXML
    public RadioButton rbClickerMouse;
    @FXML
    public TextField tfHMouse;
    @FXML
    public TextField tfMMouse;
    @FXML
    public TextField tfSMouse;
    @FXML
    public TextField tfClickersMouse;
    @FXML
    public TableColumn<Mouse,Integer> tcX;
    @FXML
    public TableColumn<Mouse,Integer> tcY;
    @FXML
    public TableColumn<Mouse,Integer> tcValue;
    @FXML
    public TableColumn<Mouse,String> tcName;
    @FXML
    public TableColumn<Mouse,String> tcType;
    @FXML
    public TableColumn<Mouse,Integer> tcIndex;
    @FXML
    public TableView<Mouse> tvMouse;
    @FXML
    public ComboBox cbIndex;
    @FXML
    public ComboBox cbIndexPara;


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


        MouseClicker.active(tvMouse,cbIndex,cbIndexPara);
        Handlers handle = new Handlers(groupVelocidade,groupClickerOrTime,tfName);
        handle.setRadioButtonsByHandle(rb1,rb2,rb3,rb4);
        handle.setTextsBeenNotHaveLetters(tfH,tfM,tfS,tfVezes,tfTimeWait,tfHMouse,tfMMouse,tfSMouse,tfClickersMouse);
        handle.setRadioButtonsByClickOrTime(rbClickerMouse,rbTimeMouse);
        handle.setComboBoxForChange(cbIndex,cbIndexPara);
        tcX.setCellValueFactory(new PropertyValueFactory<>("x"));
        tcY.setCellValueFactory(new PropertyValueFactory<>("y"));
        tcValue.setCellValueFactory(new PropertyValueFactory<>("count"));
        tcIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
        try {
            timeForWait = Integer.parseInt(tfTimeWait.getText());
        } catch (Exception e) {
            timeForWait = 0;
        }
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

    public void onClearTable() {

    }

    public void onExecuteMouseClicker() {

    }

    public void onSetPositionMouse() {

    }

    public void onDeleteIndex() {

    }

    public void onEditName(TableColumn.CellEditEvent<Mouse, String> mouseIntegerCellEditEvent) {
        mouseIntegerCellEditEvent.getNewValue();
    }
}
