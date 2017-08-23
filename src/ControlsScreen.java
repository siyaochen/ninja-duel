/**
 * ControlsScreen
 * class to set up the controls screen
 * @version 1.0
 * @author Siyao Chen, Vicki Xu
 * May 25, 2017
 */

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class ControlsScreen extends JFrame{

    JFrame controlFrame = new JFrame();

    //constructor
    ControlsScreen(){

        setTitle("Ninja Duel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 380);
        setResizable (false);
        setLocationRelativeTo(null);
        setUndecorated(false);

        CustomJPanel cPanel = new CustomJPanel();

        cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));

        JPanel buttonPan = new JPanel();
        buttonPan.setLayout(new BoxLayout(buttonPan, BoxLayout.X_AXIS));
        buttonPan.setMaximumSize(new Dimension(500,50));
        buttonPan.setOpaque(false);

        buttonPan.add(Box.createHorizontalStrut(160));

        CustomJButton backButton = new CustomJButton(160,50);
        backButton.addActionListener(new BackButtonListener());
        buttonPan.add(backButton);

        cPanel.add(Box.createRigidArea(new Dimension(0,286)));
        cPanel.add(buttonPan);
        add(cPanel);
        setVisible(true);
    }


    class BackButtonListener implements ActionListener {  //this is the required class definition
        public void actionPerformed(ActionEvent event)  {
            System.out.println("Start Button Pressed");
            controlFrame.dispose();
            setVisible(false);
            new MenuScreen(); //create a new FunkyFrame (another file that extends JFrame)
        }
    }

    class CustomJPanel extends JPanel{
        protected void paintComponent(Graphics g) {
            //Call to super method for all other necessary functions
            super.paintComponents(g);

            try {
                g.drawImage(ImageIO.read(new File("controls.png")), 0, 0, null);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    class CustomJButton extends JButton{

        public CustomJButton(int width, int height) {
            super();
            //remove the button's default coloring
            super.setContentAreaFilled(false);

            //sets the size
            setMaximumSize(new Dimension(width,height));
            //make it align center
            setAlignmentX(Component.CENTER_ALIGNMENT);
            //remove border
            setBorderPainted(false);
            //sets the text color to be white
            //remove the focus dotted border
            setFocusPainted(false);
            //sets the font of text on the button
            setFont(new Font("Serif", Font.BOLD, 1));

            setOpaque(false);
            setContentAreaFilled(false);

        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(new Color(0,0,0,0));

            //fills the background with the color determined above
            g.fillRect(0, 0, getWidth(), getHeight());

            //make call to the super method to paint the text above the background
            super.paintComponent(g);
        }
    }


}

