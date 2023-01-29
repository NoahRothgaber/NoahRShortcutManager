package Programs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class OpenChrome implements Program
{
    // This class finds chrome, (if the path is not already saved into a file) and then opens it with user set tabs
    private final Runtime currentRuntimeEnv = Runtime.getRuntime(); // only works for windows
    private final ArrayList<String> locations = new ArrayList<>();
    File makeChrome = new File("src/FileLocations/chromeLocation");

    public void whereIs() throws IOException
    {
        InputStream chromeRead =  currentRuntimeEnv.exec("where /r c:\\ chrome.exe").getInputStream();
        // This line is sick, it takes the output from the cmd command and pipes it into the input stream
        Scanner checkTheStream = new Scanner(chromeRead); // this reads the input stream
        // I think if i use the -ps command, I may be able to locate files quicker for new buttons, this would require
        // the user to open the specific program before they make it.
        boolean programFile = false;
        String chromePath;
        if(makeChrome.exists()) // if the file exists, we know the path
        {
            Scanner updateChromePath = new Scanner(makeChrome);
            chromePath = updateChromePath.nextLine();
            setupLocations(chromePath);
        }
        else
        {
            // otherwise let's look for it, make the file, append the chrome location to it
            while(!programFile)
            {
                String whatsNext;
                if (checkTheStream.hasNext())
                {
                    whatsNext = checkTheStream.nextLine();
                    if (whatsNext.contains("Program")) // the code only works for program files... for now
                    {
                        int programQuote = whatsNext.indexOf("P");
                        StringBuilder itsNew = new StringBuilder();
                        itsNew.append(whatsNext, 0, programQuote).append("\"");
                        ;
                        itsNew.append(whatsNext.substring(programQuote)).append("\" ");
                        // CMD doesn't like spaces, so it requires any paths with spaces to be in quotes.
                        chromePath = itsNew.toString();
                        // itsnew contains the command to open chrome, the next strings will use that and specify the urls
                        setupLocations(chromePath); // setup all the specified urls
                        programFile = true;
                        System.out.println(itsNew);
                        File makeChrome = new File("src/FileLocations/chromeLocation");
                        try (FileWriter writeIt = new FileWriter(makeChrome)) // write it works by this point
                        {
                            writeIt.write(chromePath);
                        }
                    }
                } else
                {
                    throw new IOException("Chrome not found");
                }

            }    // works, holy crap
        }
    }

    @Override
    public void whereIsHelper(String programPath, Scanner programScanner) throws IOException
    {
        // if i needed to scale this for any reason, this would work fine
    }

    public void setupLocations(String programPath)
    {
        locations.add("cmd /c start " + programPath +  " https://neccbb.blackboard.com/");
        locations.add("cmd /c start " + programPath +  " mynecc.necc.mass.edu");
        locations.add("cmd /c start " + programPath +  " youtube.com");
        locations.add("cmd /c start " + programPath + " outlook.com/student.necc.edu");
    }

    public void launch(int pathIndex) throws IOException
    {
        try
        {
            whereIs(); // Find chrome, or reference the file
            for (String command : locations)
            {
             currentRuntimeEnv.exec(command); // execute each launch chrome command,
            }

        }catch (IOException e) // Program will crash if chrome not detected
        {
            System.out.println("Chrome not accessible from program files. Is chrome installed?\n");
            e.printStackTrace();
        }
    }

}
