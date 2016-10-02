package big01;

import big01.listeners.FrameListener;
import big01.listeners.TabbedPaneChangeListener;
import big01.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Александр on 18.09.2016.
 */
public class View extends JFrame implements ActionListener
{
    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane(); //это будет панель с двумя вкладками
    private JTextPane htmlTextPane = new JTextPane(); //это будет компонент для визуального редактирования html. Он будет размещен на первой вкладке.
    private JEditorPane plainTextPane = new JEditorPane(); //это будет компонент для редактирования html в видетекста, он будет отображать код html (теги и их содержимое).
    private UndoManager undoManager = new UndoManager(); //
    private UndoListener undoListener = new UndoListener(undoManager);

    public View()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public Controller getController()
    {
        return controller;
    }

    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        switch (s){
            case "Новый": controller.createNewDocument(); break;
            case "Открыть": controller.openDocument(); break;
            case "Сохранить": controller.saveDocument(); break;
            case "Сохранить как...": controller.saveDocumentAs(); break;
            case "Выход": controller.exit(); break;
            case "О программе": showAbout(); break;
        }
    }

    public void init()
    {
        initGui();
        FrameListener listener = new FrameListener(this);
        addWindowListener(listener);
        setVisible(true);
    }

    public void exit()
    {
        controller.exit();
    }

    public void initMenuBar()// инициализация меню
    {
        JMenuBar menuBar = new JMenuBar();
        MenuHelper.initFileMenu(this, menuBar);
        MenuHelper.initEditMenu(this, menuBar);
        MenuHelper.initStyleMenu(this, menuBar);
        MenuHelper.initAlignMenu(this, menuBar);
        MenuHelper.initColorMenu(this, menuBar);
        MenuHelper.initFontMenu(this, menuBar);
        MenuHelper.initHelpMenu(this, menuBar);
        getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    public void initEditor()// инициализация редактора
    {
        htmlTextPane.setContentType("text/html"); // устанавливаем тип html
        JScrollPane pane = new JScrollPane(htmlTextPane); // создаю новый компонент на основе htmlTextPane
        tabbedPane.add("HTML", pane); // добавляю вкладочку HTML

        JScrollPane pane1 = new JScrollPane(plainTextPane); // еще компонент
        tabbedPane.add("Текст", pane1);// добавляю вкладку

        tabbedPane.setPreferredSize(new Dimension(800, 600)); // устанавливаю оптимальный размер
        TabbedPaneChangeListener tabbed = new TabbedPaneChangeListener(this);
        tabbedPane.addChangeListener(tabbed);  // устанавливаю новый Listener
        getContentPane().add(tabbedPane, BorderLayout.CENTER); // добавляю его по центру

    }

    public void initGui() // инициализация графического интерфейса
    {
        initMenuBar();
        initEditor();
        pack();
    }


    public void selectedTabChanged()
    {
        if (tabbedPane.getSelectedIndex() == 0) // если на хтмл стоит
        {
            controller.setPlainText(plainTextPane.getText()); // установить полученный текст в контроллер
        } else if (tabbedPane.getSelectedIndex() == 1) // иначе
        {
            plainTextPane.setText(controller.getPlainText());
        }
        resetUndo();
    }

    public boolean canUndo()
    {
        return undoManager.canUndo();
    }

    public UndoListener getUndoListener()
    {
        return undoListener;
    }

    public boolean isHtmlTabSelected()
    {
        return tabbedPane.getSelectedIndex() == 0;
    }

    public boolean canRedo()
    {
        return undoManager.canRedo();
    }

    public void resetUndo()
    {
        try
        {
            undoManager.discardAllEdits();
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public void undo()
    {
        try
        {
            undoManager.undo();
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public void redo()
    {
        try
        {
            undoManager.redo();
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public void selectHtmlTab()
    {
        tabbedPane.setSelectedIndex(0); // устанавливает HTML
        resetUndo();// все стирает с html
    }

    public void update()
    {
        htmlTextPane.setDocument(controller.getDocument()); // устанавливает документ из контроллера
    }

    public void showAbout()
    {
        JOptionPane.showMessageDialog(getContentPane(), " version: 1.0\n Developer:Aleksandr", "about program", JOptionPane.INFORMATION_MESSAGE);

    }
}
