package big01.listeners;


import big01.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * Created by Александр on 18.09.2016.
 */
public class UndoMenuListener implements MenuListener
{
    private View view;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;

    public UndoMenuListener(View view,JMenuItem udno, JMenuItem redo)
    {
        this.undoMenuItem = udno;
        this.view = view;
        this.redoMenuItem = redo;
    }

    @Override
    public void menuSelected(MenuEvent e)
    {
        undoMenuItem.setEnabled(view.canUndo());
        redoMenuItem.setEnabled(view.canRedo());
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
