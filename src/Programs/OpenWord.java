package Programs;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class OpenWord implements Program
{
    // This program opens a word file located in the source folder, If you "save as" this file in word, you can make
    // a safe copy of it

    String wordFileLocation;
    String wordCommand;


    private final Runtime currentRuntimeEnv = Runtime.getRuntime(); // only works for windows

    @Override
    public void whereIs() throws IOException
    {
        File wordFile = new File("FileLocations/TemplateAssignment.docx");
        wordFileLocation =  wordFile.getAbsolutePath();
        int insertAt = wordFileLocation.indexOf("FileLocations\\");
        String temp = wordFileLocation.substring(0,insertAt);
        temp = temp + "src\\" +
                wordFileLocation.substring(insertAt);
        wordFileLocation = temp;
        wordCommand = "cmd /c start \"\" \"" + wordFileLocation + "\"";
    }

    @Override
    public void whereIsHelper(String programPath, Scanner programScanner) throws IOException
    {
        // This isn't necessary but allows for program to scale, while maintaining style");
    }



    @Override
    public void setupLocations(String programPath) throws IOException
    {
        // Same goes for this, For example: I could make this class capable of opening multiple reference documents
    }

    @Override
    public void launch(int pathIndex) throws IOException
    {
        whereIs();
        setupLocations(wordFileLocation);
        currentRuntimeEnv.exec(wordCommand);
    }
}
