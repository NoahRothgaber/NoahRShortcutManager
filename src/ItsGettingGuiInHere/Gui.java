/*
Noah Rothgaber
This is the interface that relates different gui types. I have included an IDE gui and the main menu gui, with room for
additional other "gui's" or windows
 */

package ItsGettingGuiInHere;

import javax.swing.*;

public interface Gui
{
    void setupGui();
    void setupActionListeners(int index);

    JFrame getMainFrame();


}
