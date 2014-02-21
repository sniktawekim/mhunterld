package mhunterld;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

public class IClick extends MouseInputAdapter {

    int eventX;
    int eventY;
    int x = 0;
    int y = 0;
    boolean clicked;
    private boolean pressed = false;

    IClick() {
        clicked = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        eventX = e.getX();
        eventY = e.getY();

    }

    public void mouseDragged(MouseEvent e) {
        pressed = true;
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clicked = true;
        pressed = false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean getClicked() {
        if (clicked) {
            clicked = false;
            return true;
        } else {
            return false;
        }

    }

    public int getEX() {
        return eventX;
    }

    public int getEY() {
        return eventY;
    }

    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getPress() {
        return pressed;
    }
}
