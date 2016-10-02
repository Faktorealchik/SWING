package big01.listeners;


import big01.View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Александр on 18.09.2016.
 */
public class FrameListener extends WindowAdapter
{
    private View view;

    @Override
    public void windowClosing(WindowEvent e)
    {
        view.exit(); // вызвает controller.exit();
    }

    public FrameListener(View view)
    {
        this.view = view;
    }
}
