import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MenuScreen extends JFrame {

    public static BufferedImage basicTileImage;
    public static BufferedImage unbreakableTileImage;
    public static BufferedImage breakableTileImage;
    public static BufferedImage p1Images;
    static Clip clip;

    JFrame menuFrame = new JFrame();

    //constructor
    public MenuScreen() {
        setTitle("Ninja Duel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 380);
        setResizable(false);
        setLocationRelativeTo(null);

        CustomJPanel pan = new CustomJPanel();

        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

        JPanel buttonPan = new JPanel();
        buttonPan.setLayout(new BoxLayout(buttonPan, BoxLayout.X_AXIS));
        buttonPan.setMaximumSize(new Dimension(500, 50));
        buttonPan.setOpaque(false);

        buttonPan.add(Box.createHorizontalStrut(60));

        CustomJButton startButton = new CustomJButton(160, 50);
        startButton.addActionListener(new StartButtonListener());
        buttonPan.add(startButton);

        buttonPan.add(Box.createHorizontalStrut(45));

        CustomJButton controlButton = new CustomJButton(160, 50);
        controlButton.addActionListener(new ControlButtonListener());
        buttonPan.add(controlButton);

        pan.add(Box.createRigidArea(new Dimension(0, 286)));
        pan.add(buttonPan);
        add(pan);
        setVisible(true);
    }

    //class for start button listener
    class StartButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            System.out.println("Start Button Pressed");
            menuFrame.dispose();
            setVisible(false);
            try {
                stopMusicFile();
                playMusicFile("resources/gameMusic.wav", true);
            } catch (IOException ee) {
            } catch (LineUnavailableException ee) {
            } catch (UnsupportedAudioFileException ee) {
            }
            new GameFrame();
        }

    }

    //class for the control button listener
    class ControlButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            System.out.println("Control Button Pressed");
            menuFrame.dispose();
            setVisible(false);
            new ControlsScreen();
        }

    }

    //main method
    public static void main(String[] args) {
        //basic Tile Sprite
        try {
            basicTileImage = (ImageIO.read(new File("resources/grass_texture.png")));
        } catch (Exception e) {
            System.out.println("error loading basic sprite");
        }

        //unbreakable tile sprite
        try {
            unbreakableTileImage = (ImageIO.read(new File("resources/house_sprite.png")));
        } catch (Exception e) {
            System.out.println("error loading unbreakable sprite");
        }

        //breakable tile sprite
        try {
            breakableTileImage = (ImageIO.read(new File("resources/tree_sprite.png")));
        } catch (Exception e) {
            System.out.println("error loading breakable sprite");
        }

        //play the music
        try {
            playMusicFile("resources/MenuMusic.wav", true);
        } catch (IOException ee) {
        } catch (LineUnavailableException ee) {
        } catch (UnsupportedAudioFileException ee) {
        }

        new MenuScreen();
    }


    /**
     * Method to play a music file.
     * @param file The file of music you would like to play.
     * @param loop The boolean for whether or not you would like to loop it.
     * @return Nothing, a void method.
     */
    public static void playMusicFile(String file, boolean loop)
            throws IOException, LineUnavailableException,
            UnsupportedAudioFileException {
        // Create a new file using the filename parameter.
        File sound = new File(file);

        // Make sure the file exists.
        if (!sound.exists()) {
            System.out.println("Sound file not found.");
            return;
        }

        // Play the audio file.
        AudioInputStream stream = AudioSystem.getAudioInputStream(sound);
        clip = AudioSystem.getClip();
        // Open the audio stream.
        clip.open(stream);
        // If loop boolean is true, then loop the sound file.
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.loop(0);
        }

    }


  /**
   * Method to stop any music file currently playing.
   */
    public static void stopMusicFile() throws LineUnavailableException {
        // If no music is being played, return.
        if (clip == null) {
            System.out.println("no music playing");
            return;
        }
        // Stop the music, and close the clip.
        clip.stop();
        clip.close();
    }


    class CustomJPanel extends JPanel {

        protected void paintComponent(Graphics g) {
            //Call to super method for all other necessary functions
            super.paintComponents(g);

            try {
                g.drawImage(ImageIO.read(new File("resources/start.png")), 0, 0, null);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    class CustomJButton extends JButton {

        public CustomJButton(int width, int height) {
            super();
            //remove the button's default coloring
            super.setContentAreaFilled(false);

            //sets the size
            setMaximumSize(new Dimension(width, height));
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
            g.setColor(new Color(0, 0, 0, 0));

            //fills the background with the color determined above
            g.fillRect(0, 0, getWidth(), getHeight());

            //make call to the super method to paint the text above the background
            super.paintComponent(g);
        }

    }

}