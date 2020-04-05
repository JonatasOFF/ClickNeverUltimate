package clicknever.models.controllers.mouse;

import clicknever.callback.CallBackListener;
import clicknever.models.controllers.Mouse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

import static clicknever.controllers.Controller.velocidade;
import static clicknever.handles.Handlers.isNewMouse;

public class MouseClicker {

    public static boolean isClickCanActive = true;

    private static boolean isTimeLeave = true;

    public static boolean isPauseClicker = false;

    public static boolean isClickerTime = false;

    public static boolean isClickerVezes = false;

    public static boolean isClicker = true;

    public static boolean isTime = false;

    private static int count;

    private static TableView<Mouse> tvTable;

    public static List<Mouse> mouses = new ArrayList<>();

    private static ComboBox cbIndexAtual;
    private static ComboBox cbIndexPara;

    private static int hours;
    private static int minutos;
    private static int segundos;

    private static Robot clickers;

    public static void active(TableView<Mouse> tableView, ComboBox smbIndexA, ComboBox smbParaIndex) {
        try {
            clickers = new Robot();
            tvTable = tableView;
            cbIndexAtual = smbIndexA;
            cbIndexPara = smbParaIndex;
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private static void pauseClicker() {
        if(isPauseClicker) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pauseClicker();
        }
    }


    public static void clickerVezes(Text lbText,int vezes) {
        isClickCanActive = true;

        count = vezes;
        //System.out.println(count);
        new Thread(() -> {
            try {
                for(; count > 0 && isClickCanActive; count--) {
                    //System.out.println(count + " " + isClickerVezes + " " + velocidade);
                    pauseClicker();
                    Robot clickers = new Robot();
                    clickers.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    clickers.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    clickers.delay(velocidade);
                }
            } catch (AWTException  e) {
                e.printStackTrace();
            }
            isClickerVezes = false;
            Platform.runLater(() -> lbText.setText("Acabou"));
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> lbText.setText("Tempo para Começar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     *
     * @param h horas
     * @param m minutos
     * @param s segundos
     * @param lbTime tempo a ser mudado
     */
    public static void clickerTime(int h, int m, int s, Label lbTime,Text lbText) {
        hours = h;
        minutos = m;
        segundos = s;

        isTimeLeave = true;
        isClickCanActive = true;

        timeGo(lbText,lbTime, () -> isTimeLeave = false);
        new Thread(() -> {
            while (isTimeLeave && isClickCanActive) {
                pauseClicker();
                clickers.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                clickers.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                clickers.delay(velocidade);
            }
        }).start();
    }


    private static void timeGo(Text lbText,Label lbTime, CallBackListener call) {
        new Thread(() -> {
            while (isTimeLeave && isClickCanActive) {
                if (minutos == 0 && hours != 0) {
                    hours--;
                    minutos = 60;
                }
                if (segundos == 0 && minutos > 0) {
                    minutos--;
                    segundos = 60;
                }
                segundos--;
                pauseClicker();
                //System.out.println(hours + ":" + minutos + ":" + segundos);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> lbTime.setText(hours + ":" + minutos + ":" + segundos));
            }
            call.callBack();
            isClickerTime = false;
            Platform.runLater(() -> lbText.setText("Acabou"));
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> lbText.setText("Tempo para Começar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static void newClickMouse() {
        int index = mouses.size();
        Mouse mouse = new Mouse(isClicker,isTime,++index).setName("");
        mouses.add(mouse);
        cbIndexPara.getEditor().clear();
        cbIndexAtual.getEditor().clear();
        attTableDatas();
        tvTable.refresh();
    }

    public static void attTableDatas() {
        isNewMouse = false;
        cbIndexPara.getEditor().clear();
        cbIndexAtual.getEditor().clear();
        List<Integer> list = new ArrayList<>();
        ObservableList<Mouse> mouseObservable = FXCollections.observableArrayList(mouses);
        mouseObservable.forEach(k -> {
            list.add(k.getIndex());
            if(k.isTime()) {
                k.setTimeInValue();
            } else {
                k.setValueInTime();
            }
        });
        ObservableList<Integer> mouseIndex = FXCollections.observableArrayList(list);
        try {
            tvTable.setItems(mouseObservable);
            cbIndexAtual.setItems(mouseIndex);
            cbIndexPara.setItems(mouseIndex);
            tvTable.refresh();
            Platform.runLater(() -> {
                cbIndexPara.getEditor().clear();
                cbIndexAtual.getEditor().clear();
                cbIndexPara.valueProperty().setValue("");
            });
            } catch (Exception e) {
            System.out.println("DEU UM ERRO AI CARALHO SEU FILHO DA PUTA");
        }
        isNewMouse = true;
    }
}
