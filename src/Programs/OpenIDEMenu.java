package Programs;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class OpenIDEMenu implements Program
{
    private final Runtime currentRuntimeEnv = Runtime.getRuntime();

    private final ArrayList<String> locations = new ArrayList<>();
    String cLionPath = "clion";
    String pycharmPath = "pycharm";
    String intelliJPath = "intellij";
    File makeIDEs = new File("src/FileLocations/IDELocations");
    int totalLines = 0;

    @Override
    public void whereIs() throws IOException
    {
        // find intellij, clion, pycharm
        InputStream intelliJRead = currentRuntimeEnv.exec("where /r c:\\ idea64.exe").getInputStream();
        Scanner checkTheIntellij = new Scanner(intelliJRead);
        InputStream cLionRead = currentRuntimeEnv.exec("where /r c:\\ clion64.exe").getInputStream();
        Scanner checkTheClion = new Scanner(cLionRead);
        InputStream pycharmRead = currentRuntimeEnv.exec("where /r c:\\ pycharm64.exe").getInputStream();
        Scanner checkThePycharm = new Scanner(pycharmRead);
        countLines(); // this is required in order to properly build the file if its the first time running,
        whereIsHelper(intelliJPath, checkTheIntellij);
        whereIsHelper(cLionPath, checkTheClion);
        whereIsHelper(pycharmPath, checkThePycharm);
        //clion64.exe
    }


    @Override
    public void setupLocations(String programPath)
    {
        locations.add("cmd /c start " + programPath);
    }
    // must be ran for each ide

    @Override
    public void launch(int pathIndex) throws IOException
    {
        whereIs(); // run the code to find everything, this is the entry point
        currentRuntimeEnv.exec(locations.get(pathIndex));
        // find the path,
    }

    @Override
    public void whereIsHelper(String programPath, Scanner programScanner) throws IOException
    {
        boolean isProgramFile = false;
        if (makeIDEs.exists() && totalLines == 3)
        {
            Scanner updateProgramPath = new Scanner(makeIDEs);
            // Couldn't figure out how to read through to the correct line in the file in a nicer way
            switch(programPath)
            {
                case "intellij":
                    programPath = updateProgramPath.nextLine();
                    break;

                case "clion":
                    updateProgramPath.nextLine();
                    programPath = updateProgramPath.nextLine();
                    break;

                case "pycharm":
                    updateProgramPath.nextLine();
                    updateProgramPath.nextLine();
                    programPath = updateProgramPath.nextLine();
                    break;
            }
            setupLocations(programPath); // setup the location for the selected button
        }
        else
            // file either doesn't exist, or hasn't been fully built
            while (!isProgramFile)
            {
                // adds quotes to get cmd working
                String whatsNext;
                if (programScanner.hasNext())
                {
                    whatsNext = programScanner.nextLine();
                    if (whatsNext.contains("Program"))
                    {
                        int programQuote = whatsNext.indexOf("P");
                        StringBuilder itsNew = new StringBuilder();
                        itsNew.append(whatsNext, 0, programQuote).append("\"");
                        itsNew.append(whatsNext.substring(programQuote)).append("\" ");
                        programPath = itsNew.toString();
                        setupLocations(programPath);
                        isProgramFile = true;
                        System.out.println(itsNew);
                        try (FileWriter writeIt = new FileWriter(makeIDEs, true))
                                // File writer has to include TRUE, so it opens in append mode
                        {
                            writeIt.append(programPath).append("\n");
                        }
                    }
                } else
                {
                    throw new IOException("Current IDE not found");
                }
            }
        }

    private void countLines() throws FileNotFoundException
    {
        // counts the lines to ensure there's 3, if there's not, it will build the file from scratch
        if (makeIDEs.exists())
        {
            Scanner read = new Scanner(makeIDEs);
            while (read.hasNextLine())
            {
                totalLines++;
                read.nextLine();
            }
        }
    }
}
