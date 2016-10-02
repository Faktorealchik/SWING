package big01;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

/**
 * Created by Александр on 18.09.2016.
 */
public class Controller
{
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view)
    {
        this.view = view;
    }

    public void init()
    {
        createNewDocument();
    }

    public void exit()
    {
        System.exit(0);
    }

    public static void main(String[] args)
    {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();
    }

    public HTMLDocument getDocument()
    {
        return document;
    }

    public void resetDocument()
    {
        if (document != null)
        {
            document.removeUndoableEditListener(view.getUndoListener());
        }
        HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        document = (HTMLDocument) htmlEditorKit.createDefaultDocument();
        document.addUndoableEditListener(view.getUndoListener());
        view.update();
    }

    public void setPlainText(String text) // передача текста
    {
        if (document != null)
            resetDocument();
        StringReader reader = new StringReader(text);
        HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        try
        {
            htmlEditorKit.read(reader, document, 0);
        }
        catch (IOException | BadLocationException e)
        {
            ExceptionHandler.log(e);
        }
    }

    public String getPlainText() // получить текст
    {
        StringWriter writer = new StringWriter();
        HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        try
        {
            htmlEditorKit.write(writer, document, 0, document.getLength());
        }
        catch (IOException | BadLocationException e)
        {
            ExceptionHandler.log(e);
        }
        return writer.toString();

    }

    public void createNewDocument()
    {
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile = null;
    }

    public void openDocument()
    {
        view.selectHtmlTab();
        JFileChooser jfile = new JFileChooser();
        jfile.setFileFilter(new HTMLFileFilter());
        int m = jfile.showOpenDialog(view);
        if (m == JFileChooser.APPROVE_OPTION)
        {
            currentFile = jfile.getSelectedFile();
            resetDocument();
            view.setTitle(currentFile.getName());
            try (FileReader fileReader = new FileReader(currentFile))
            {
                HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
                htmlEditorKit.read(fileReader, document, 0);
            }
            catch (IOException | BadLocationException e)
            {
                ExceptionHandler.log(e);
            }
            view.resetUndo();
        }
    }

    public void saveDocument()
    {
        if (currentFile == null)
        {
            saveDocumentAs();
        } else
            view.selectHtmlTab();
        try (FileWriter writer = new FileWriter(currentFile)) //Создавать FileWriter на базе currentFile.
        {
            new HTMLEditorKit().write(writer, document, 0, document.getLength()); //Переписывать данные из документа document в объекта FileWriter-а
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e); //Метод не должен кидать исключения.
        }
    }

    public void saveDocumentAs()
    {
        view.selectHtmlTab();
        JFileChooser jfile = new JFileChooser();
        jfile.setFileFilter(new HTMLFileFilter());
        int m = jfile.showSaveDialog(view);
        if (m == JFileChooser.APPROVE_OPTION)
        {
            currentFile = jfile.getSelectedFile();
            view.setTitle(currentFile.getName());
            try (FileWriter writer = new FileWriter(currentFile))
            {
                HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
                htmlEditorKit.write(writer, document, 0, document.getLength());
            }
            catch (IOException | BadLocationException e)
            {
                ExceptionHandler.log(e);
            }
        }
    }
}

