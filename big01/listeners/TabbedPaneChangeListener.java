package big01.listeners;


import big01.View;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by Александр on 18.09.2016.
 */
public class TabbedPaneChangeListener implements ChangeListener
{
    private View view;

    public TabbedPaneChangeListener(View view)
    {
        this.view = view;
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        view.selectedTabChanged();
    }
}
