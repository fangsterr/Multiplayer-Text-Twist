package server;

import java.io.*;
import java.util.*;

/**
 * Utility class to deal with text files with dictionary
 * 
 * @author AndyFang
 *
 */
public class FileUtil
{
    public static Set<String> loadFile(String fileName)
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line = in.readLine();
            Set<String> lines = new TreeSet<String>();
            while (line != null)
            {
                lines.add(line);
                line = in.readLine();
            }
            in.close();

            return lines;
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}