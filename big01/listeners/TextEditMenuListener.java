package big01.listeners;


import big01.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

/**
 * Created by Александр on 18.09.2016.
 */
public class TextEditMenuListener implements MenuListener
{
    private View view;
    public TextEditMenuListener(View view){
        this.view=view;
    }

    @Override
    public void menuSelected(MenuEvent e)
    {
        JMenu jmenu= (JMenu) e.getSource();
        Component[] components= jmenu.getMenuComponents();
        for (Component c: components)
        {
            c.setEnabled(view.isHtmlTabSelected());
        }
    }

    @Override
    public void menuDeselected(MenuEvent e)
    {

    }

    @Override
    public void menuCanceled(MenuEvent e)
    {

    }
}
