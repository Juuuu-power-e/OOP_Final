package mainFrame;

import java.awt.event.ActionListener;
import java.util.Observer;

public interface MyComponent {
    void updateText();
    void setActionListener(ActionListener actionListener);
}
