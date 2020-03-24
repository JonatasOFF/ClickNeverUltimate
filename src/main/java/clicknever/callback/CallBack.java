package clicknever.callback;

import javafx.application.Platform;
import javafx.scene.text.Text;

import static clicknever.models.controllers.mouse.MouseClicker.isClickCanActive;

public class CallBack  {


    public static int timeForWait;

    public void timeForStart(Text lbText,CallBackListener call) {
        //System.out.println("Da um boquete aqui");
        new Thread(() -> {
            for(int i = timeForWait; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isClickCanActive) {
                    int finalI = i;
                    Platform.runLater(() -> lbText.setText("Tempo pra Iniciar " + finalI));

                } else {
                    break;
                }
            }

            Platform.runLater(() -> lbText.setText("Em Progresso"));
            call.callBack();

        }).start();

    }
}
