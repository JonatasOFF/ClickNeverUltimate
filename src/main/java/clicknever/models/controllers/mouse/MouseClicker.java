package clicknever.models.controllers.mouse;

import clicknever.callback.CallBackListener;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.event.InputEvent;

import static clicknever.controllers.Controller.velocidade;

public class MouseClicker {


    public static boolean isClickCanActive = true;

    private static boolean isTimeLeave = true;

    public static boolean isPauseClicker = false;

    public static boolean isClickerTime = false;

    public static boolean isClickerVezes = false;

    private static int count;

    private static int hours;
    private static int minutos;
    private static int segundos;

    private static Robot clickers;

    public static void active() {
        try {
            clickers = new Robot();
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
}
