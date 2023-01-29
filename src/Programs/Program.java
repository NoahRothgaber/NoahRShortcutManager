package Programs;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public interface Program
{
    // The program interface assures that a program can launch, can find where its dependencies are located, and
    // can setup the command arraylist contained within the program

    Runtime currentRuntimeEnv = null;
    void whereIs() throws IOException;
    void whereIsHelper(String programPath,Scanner programScanner) throws IOException;
    void setupLocations(String programPath) throws IOException;
    void launch(int pathIndex) throws IOException;
}

