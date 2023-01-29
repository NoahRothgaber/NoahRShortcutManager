package ItsGettingGuiInHere;

import Managers.GUIManager;
import Programs.OpenIDEMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class IDEGui implements Gui
{
    JFrame mainFrame;
    JPanel mainPanel;
    JButton intelliJButton;
    JButton clionButton;
    JButton pycharmButton;
    JButton exitButton;

    ArrayList<JButton> buttons = new ArrayList<>(); // all buttons on the screen

    GUIManager manageGui;
    // I used a nice font for this, but my system didn't agree with it being used
    // File fontFile = new File("src/ItsGettingGuiInHere/Baskerville-Old-Face_6117.ttf"); // typewriter font
    /*Font baskervilleOld = Font.createFont
            (Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("Baskerville-Old-Face_6117.ttf"));
            // why dont you let me set size ????
    Font baskervilleOldFace = baskervilleOld.deriveFont(20f);// font size magic but like why is this required?

     */
    public IDEGui() throws IOException, FontFormatException
    {
    }

    @Override
    public void setupGui() // interface method,
    {
        mainFrame = new JFrame("Noah's School Manager");
        mainFrame.setSize(400, 400);
        mainPanel = new JPanel(new GridLayout(4, 1));
        mainFrame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource
                ("N.png"))).getImage()); // this worked for another project i used, it works here too!
        mainPanel.setFocusable(false); // removes weird rectangle around text
        intelliJButton = new JButton("Launch Intellij");
        clionButton = new JButton("Launch CLion");
        pycharmButton = new JButton("Launch Pycharm");
        exitButton = new JButton("Return to Main Menu");
        // add buttons to array list
        buttons.add(intelliJButton);
        buttons.add(clionButton);
        buttons.add(pycharmButton);
        buttons.add(exitButton);
        // setup buttons, uses a new color for each
        setupJButton(buttons.get(0), new Color(146, 52, 146));
        setupJButton(buttons.get(1), new Color(52, 100, 175));
        setupJButton(buttons.get(2), new Color(125, 185, 70));
        setupJButton(buttons.get(3), new Color(200, 60, 70));
        // setup all the action listeners
        for(int i = 0; i < 4; i++)
        {
            setupActionListeners(i);
        }
        // add buttons to panel
        for(JButton button : buttons)
        {
            mainPanel.add(button);
        }
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(mainPanel);
    }

    @Override
    public void setupActionListeners(int buttonIndex)
    {
        // the action listeners for the IDE buttons are the same, but the exit button has a different listener
        if(buttonIndex == 3)
        {
            // ide suggested using a lambda, I like it, this one is for exiting
            buttons.get(3).addActionListener(e ->
                    manageGui.showAndHide(manageGui.getIdeGui().mainFrame, manageGui.getMainGui().getMainFrame()));
        }
        else
        {
            // launches the proper IDE
            buttons.get(buttonIndex).addActionListener(e ->
            {
                OpenIDEMenu runIDE = new OpenIDEMenu();
                try
                {
                    runIDE.launch(buttonIndex);
                } catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    @Override
    public JFrame getMainFrame() // part of the interface, useful for hiding
    {
        return mainFrame;
    }

    private void setupJButton(JButton button, Color buttonColor)
    {
        // sets font, background, resizes, and removes the odd rectangle
        //button.setFont(baskervilleOldFace);
        button.setBackground(buttonColor);
        button.setFocusable(false);
        button.setSize(125, 200);
    }

    public void setGuiManager(GUIManager manager)
    {
        manageGui = manager;
    }
    // setter for the gui manager, this allows any gui to access the others, and hide them
}
