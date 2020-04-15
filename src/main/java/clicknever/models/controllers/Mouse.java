package clicknever.models.controllers;

import static clicknever.models.controllers.mouse.ControllerMouse.mouseX;
import static clicknever.models.controllers.mouse.ControllerMouse.mouseY;

public class Mouse {

    private int x;
    private int y;
    private boolean isClick;
    private boolean isTime;
    private String name;
    private int index;
    private String count;
    private int[] time;

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getName() {
        return name;
    }

    public Mouse setName(String name) {
        this.name = name;
        return this;
    }

    public Mouse(boolean isClick, boolean isTime, int index) {
        this.isClick = isClick;
        this.isTime = isTime;
        this.index = index;
        setPosition();
        setType();
    }

    private void setType() {
        if(isClick) {
            setType("Clicker");
        } else {
            setType("Time");
        }
    }


    public void resetValue() {
        count = "";
        time = new int[3];

    }



    //TODO: Criar Runtime para ClickMouse
    //TODO: Criar metodo de delete index (!Importante!)
    //TODO: Organizar Keyboard (e avisar usuario sobre)
    //TODO: ? Tutorial ?

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;

        setType();
    }

    public boolean isTime() {
        return isTime;
    }

    public void setTime(boolean time) {
        isTime = time;
        setType();
    }

    public String getCount() {
        return count != null ? count : "0";
    }

    public void setValue(String count) {
        this.count = count;
    }

    public int[] getTime() {
        return time;
    }

    public void setTime(int[] time) {
        this.time = time;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private void setPosition() {
        this.x = mouseX;
        this.y = mouseY;
    }

    public void setTimeInValue() {
        if(time != null) {
            setValue(this.time[0]  + ":" + this.time[1] + ":" + this.time[2]);
        } else {
            setValue(0 + ":" + 0 + ":" + 0);
        }
    }

    public void setValueInTime() {

        setValue(getCount().replaceAll("[^\\d]",""));
    }
}
