package mhunterld;

import static java.awt.MouseInfo.getPointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class IClick implements MouseListener {

    int x;
    int y;
    boolean clicked;

    IClick() {
        clicked = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clicked = true;
        
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
