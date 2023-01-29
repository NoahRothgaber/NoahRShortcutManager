package Managers;

import ItsGettingGuiInHere.Gui;
import ItsGettingGuiInHere.IDEGui;
import ItsGettingGuiInHere.MainGui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUIManager
{
    // This class is used to manage the different kinds of GUIS, the manager contains all the different types,

    public MainGui getMainGui()
    {
        return mainGui;
    }

    public IDEGui getIdeGui()
    {
        return ideGui;
    }

    MainGui mainGui;
    IDEGui ideGui;

    public GUIManager() throws IOException, FontFormatException
    {
    }

    public void setupGui(Gui gui)
    {
        gui.setupGui();
    }

    public void showAndHide(JFrame hide, JFrame show) // give the active frame, hide it, show the inactive frame
    {
        hide.setVisible(false);
        show.setVisible(true);
    }

    public void assignGui(Gui assign, int which) // assign the GUI manager to the correct instances of the gui objects
    {
        switch(which)
        {
            case 0:
                mainGui = (MainGui) assign;
                break;

            case 1:
                ideGui = (IDEGui) assign;
                break;
        }
    }
}
