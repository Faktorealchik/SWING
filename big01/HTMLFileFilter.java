package big01;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Александр on 18.09.2016.
 */
public class HTMLFileFilter extends FileFilter
{
    @Override
    public boolean accept(File f)
    {
        String name = f.getName().toLowerCase();
        return name.endsWith("html") ||name.endsWith("htm") ||f.isDirectory();
    }

    @Override
    public String getDescription()
    {
        return "HTML и HTM файлы";
    }
}
