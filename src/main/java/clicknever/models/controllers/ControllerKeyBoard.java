package clicknever.models.controllers;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import static clicknever.models.controllers.mouse.MouseClicker.*;

public class ControllerKeyBoard extends ControllerNative implements NativeKeyListener {


    private void setClickStop() {
        isClickCanActive = false;
    }

    private void setPauseClicker() {
        isPauseClicker = !isPauseClicker;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        //System.out.println(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).toUpperCase());
        if((NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode())).toUpperCase().equals("F1")) {
            setClickStop();
        } else if((NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode())).toUpperCase().equals("F2")) {
            setPauseClicker();
        } else if((NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode())).toUpperCase().equals("F3")) {
            newClickMouse();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void activeKey(boolean isActive) throws NativeHookException {
        if (isActive) {
            GlobalScreen.addNativeKeyListener(this);
            GlobalScreen.registerNativeHook();
        } else {
            GlobalScreen.unregisterNativeHook();
        }
    }
}
