package clicknever.models.controllers.mouse;

import clicknever.models.controllers.ControllerNative;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseListener;

import static clicknever.models.controllers.mouse.MouseClicker.newClickMouse;

public class ControllerMouse extends ControllerNative implements NativeMouseListener, NativeMouseInputListener {


    public static int mouseX;
    public static int mouseY;
    public static boolean isNewPosition = false;

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        if(isNewPosition) {
            isNewPosition = false;
            mouseX = nativeMouseEvent.getX();
            mouseY = nativeMouseEvent.getY();
            newClickMouse();
        }
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        mouseX = nativeMouseEvent.getX();
        mouseY = nativeMouseEvent.getY();
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void activeKey(boolean isActive) throws NativeHookException {
        if(isActive) {
            GlobalScreen.addNativeMouseListener(this);
            GlobalScreen.addNativeMouseMotionListener(this);
            GlobalScreen.registerNativeHook();
        } else {
            GlobalScreen.unregisterNativeHook();
        }
    }
}
