package ItsGettingGuiInHere;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import Managers.GUIManager;
import Programs.*;

public class MainGui implements Gui
{
    JFrame mainFrame;
    JPanel mainPanel;
    JButton launchChromeMenu;
    JButton launchIdeMenu;
    JButton launchWordMenu;
    ArrayList<JButton> buttons = new ArrayList<>();
    // JButton launch

    GUIManager manageGui;

    File fontFile = new File("src/ItsGettingGuiInHere/Baskerville-Old-Face_6117.ttf");
    /* Font baskervilleOld = Font.createFont
            (Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("Baskerville-Old-Face_6117.ttf"));// why dont you let me set size ????
    Font baskervilleOldFace = baskervilleOld.deriveFont(20f);// font size magic


     */
    public MainGui() throws IOException, FontFormatException
    {
    }

    public void setupGui()
    {
        manageGui.assignGui(this, 0);
        mainFrame = new JFrame("Noah's School Manager");
        mainFrame.setSize(400, 400);
        mainPanel = new JPanel(new GridLayout(3, 1));
        mainFrame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource
                ("N.png"))).getImage());
        mainPanel.setFocusable(false);
        launchChromeMenu = new JButton("Launch Chrome");
        launchIdeMenu = new JButton("Launch IDE Menu");
        launchWordMenu = new JButton("Launch Student Word Doc");
        // click save as to move document
        buttons.add(launchChromeMenu);
        buttons.add(launchIdeMenu);
        buttons.add(launchWordMenu);
        setupActionListeners(0);
        setupActionListeners(1);
        setupActionListeners(2);
        setupJButton(launchChromeMenu, new Color(240, 150, 30));
        setupJButton(launchIdeMenu, new Color(200, 175, 45));
        setupJButton(launchWordMenu, new Color(100,100,175));
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainPanel.setMinimumSize(new Dimension(300, 300));
        mainFrame.add(mainPanel);
        manageGui.setupGui(manageGui.getIdeGui());
    }

    public void setupActionListeners(int buttonIndex)
    {
        switch(buttonIndex)
        {
            case 0: // chrome
                buttons.get(0).addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        OpenChrome runChrome = new OpenChrome();
                        try
                        {
                            runChrome.launch(-1);
                        } catch (IOException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                break;

            case 1:
                // The chrome one works by calling the exact function, but the IDE one uses a separate gui
                // Do I use a gui manager? Or do I have the main gui contain other smaller guis?

                // SOLUTION WAS TO SET RELATIONSHIPS IN MAIN AS A STATIC METHOD, GREAT NEWS
                // all guis point to the same gui manager, which contains all the guis, pretty cool stuff
                buttons.get(1).addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) // hide the main window when the ide screen is up
                    {
                        manageGui.showAndHide(manageGui.getMainGui().getMainFrame(), manageGui.getIdeGui().mainFrame);
                    }
                });
                break;

            case 2:
               buttons.get(2).addActionListener(new ActionListener()
               {
                   @Override
                   public void actionPerformed(ActionEvent e)
                   {
                       OpenWord opened = new OpenWord();
                       try
                       {
                           opened.launch(-1);
                       } catch (IOException ex)
                       {
                           throw new RuntimeException(ex);
                       }
                   }
               });

        }

        }

        // was in the middle of adding a switch statement to attempt to launch correct prorgram. Chrome currently works
        // but other programs need to be called properly, if this works right, the program will scale nicely.


    public JFrame getMainFrame()
    {
        return mainFrame;
    }

    private void setupJButton(JButton button, Color buttonColor)
    {
        //button.setFont(baskervilleOldFace);
        button.setBackground(buttonColor);
        button.setFocusable(false);
        button.setSize(50, 25);
        mainPanel.add(button);
    }

    public void setGuiManager(GUIManager manager)
    {
        manageGui = manager;
    }

}