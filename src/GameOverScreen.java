/**
 * GameOverScreen
 * class to create game over screen
 * @version
 * @author
 * May 23, 2017
 */


import javax.swing.*;
import java.awt.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

class GameOverScreen extends JFrame{

    JFrame gameOverScreen = new JFrame();
    public Player player;

    //constructor
    public GameOverScreen(Player player){

        setTitle("Ninja Duel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 380);
        setResizable (false);
        setLocationRelativeTo(null);
        setUndecorated(false);

        this.player = player;

        CustomJPanel pan = new CustomJPanel();

        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

        JPanel buttonPan = new JPanel();
        buttonPan.setLayout(new BoxLayout(buttonPan, BoxLayout.X_AXIS));
        buttonPan.setMaximumSize(new Dimension(500,50));
        buttonPan.setOpaque(false);

        buttonPan.add(Box.createHorizontalStrut(60));

        CustomJButton restartButton = new CustomJButton(160,50);
        restartButton.addActionListener(new RestartButtonListener());
        buttonPan.add(restartButton);

        buttonPan.add(Box.createHorizontalStrut(45));

        CustomJButton exitButton = new CustomJButton(160,50);
        exitButton.addActionListener(new ExitButtonListener());
        buttonPan.add(exitButton);

        pan.add(Box.createRigidArea(new Dimension(0,286)));
        pan.add(buttonPan);
        add(pan);
        setVisible(true);
    }

    //class for start button listener
    class RestartButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event)  {
            System.out.println("restart Button Pressed");
            gameOverScreen.dispose();
            setVisible(false);
            
            try {
            // Stop the previous music, and play the menu music.
            MenuScreen.stopMusicFile();
            MenuScreen.playMusicFile("MenuMusic.wav", true);
          } catch (IOException ee) {
          } catch (LineUnavailableException ee) {
          } catch (UnsupportedAudioFileException ee) {
          }
            
            new MenuScreen();
        }

    }


    //class for the control button listener
    class ExitButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event)  {
            System.out.println("exit Button Pressed");
            gameOverScreen.dispose();
            setVisible(false);
            System.exit(0);
        }

    }

    class CustomJPanel extends JPanel {

        protected void paintComponent(Graphics g) {
            //Call to super method for all other necessary functions
            super.paintComponents(g);

            if (player.playerNum == 1) {
                try {
                    g.drawImage(ImageIO.read(new File("win_screen2.png")), 0, 0, null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (player.playerNum == 2) {
                try {
                    g.drawImage(ImageIO.read(new File("win_screen1.png")), 0, 0, null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
            //sets the buttons to be invisible
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


