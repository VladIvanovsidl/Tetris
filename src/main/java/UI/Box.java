package UI;

import javax.swing.*;

public class Box extends JPanel {
    private int color;

    public int getColor() {
        return color;
    }

    public Box(int x, int y) {
        color = 0;
        setBounds(x * Confing.SIZE, y * Confing.SIZE, Confing.SIZE, Confing.SIZE);
        setBackground(Confing.COLORS[0]);


    }

    public void setColor(int  color) {
        this.color = color;
        if (color>= 0 && color< Confing.COLORS.length)
            setBackground(Confing.COLORS[color]);


    }

}
