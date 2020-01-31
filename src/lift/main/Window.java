package lift.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * The Window class initialises and creates a JFrame object.
 * 
 * @author R.S.
 *
 */

public class Window extends Canvas {

    private static final long serialVersionUID = -5528816561237195534L;
    private JFrame frame;

    public Window(int width, int height, String title, MainLoop mainloop) {
        frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // start window at middle
        frame.add(mainloop);
        frame.setVisible(true);
    }
    
    public int getHeight() {
        return frame.getContentPane().getHeight();
    }
    
    public JFrame getJFrame() {
        return frame;
    }
}
