package GUI;

import javax.swing.*;
import java.awt.*;

class Cell extends JPanel {
    JLabel label = new JLabel((ImageIcon) null);
    public Cell() {
        setBackground(Color.white);
        label.setBorder(BorderFactory.createEmptyBorder(25 /*top*/, 0, 0, 0));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label);
    }
    public void setIcon(Icon icon) {
        label.setIcon(icon);
        //label = new JLabel(icon, JLabel.CENTER);
    }
}