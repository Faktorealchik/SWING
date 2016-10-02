package big01.actions;


import big01.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Александр on 18.09.2016.
 */
public class RedoAction extends AbstractAction
{
    private View view;

    public RedoAction(View view)
    {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        view.redo();
    }
}
