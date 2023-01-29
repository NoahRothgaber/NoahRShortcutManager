import ItsGettingGuiInHere.IDEGui;
import ItsGettingGuiInHere.MainGui;
import Managers.GUIManager;
import Managers.ProgramManager;

import java.awt.*;
import java.io.IOException;


/*
Here's a brief description of the program

"This program is used to make a graphical user interface for launching commonly used programs,
It currently is capable of launching:
Google Chrome with school tabs
CLion C++/C IDE
IntelliJ Java IDE
Pycharm Python IDE
:
The code is designed to use different interfaces for GUIS, Programs, and Managers

The code uses a gui manager which allows any individual gui to reference others,
allowing them to hide or show the correct one allowing a seamless transition between screens.
The main menu is the hub, so anytime a sub menu needs to be exited, it returns to the main menu.

The backend uses CMD prompt commands to find the absolute path, parse those absolute paths into a readable format
for the "run" command, and then finally runs them when interacting with the buttons in the gui"
 */
public class Main
{
    public static void main(String[] args) throws IOException, FontFormatException
    {
        GUIManager guiManager = new GUIManager();
        setupRelationships(guiManager);
        ProgramManager programs = new ProgramManager(); // doesn't have functionality yet
        guiManager.setupGui(guiManager.getMainGui());
    }

    public static void setupRelationships(GUIManager guiManager) throws IOException, FontFormatException
    {
        // a really cool method I am proud of, makes an instance of each gui class, sets the manager's references
        // for the types of guis to point to the new instances, then take those instances and sets their manager, to
        // reference the same manager
        MainGui mainGui = new MainGui();
        IDEGui ideGui = new IDEGui();
        guiManager.assignGui(mainGui, 0);
        guiManager.assignGui(ideGui, 1);
        mainGui.setGuiManager(guiManager);
        ideGui.setGuiManager(guiManager);
    }
}
