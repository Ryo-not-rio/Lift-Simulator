package lift.main;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * The Methods class provides only static methods to be reused in the program.
 * 
 * getStringX and getStringY provides coordinates to align strings to the centre
 * give a coordinate and the size of font and the string.
 * 
 * @author R.S.
 *
 */

public class Methods {
    
    public final static int getStringX(int x, int size, Font font, String string, Graphics g) {
        FontMetrics metrics = g.getFontMetrics(font);
        return x + ((size - metrics.stringWidth(string)) / 2);
    }
    
    public final static int getStringY(int y, int size, Font font, String string, Graphics g, int floorHeight) {
        FontMetrics metrics = g.getFontMetrics(font);
        return y + ((floorHeight - metrics.getHeight()) / 2) + metrics.getAscent();
    }
    
    public final static int fixLimit(double d, int max, int min) {
        if(d < min) return min;
        else if(d > max) return max;
        return (int) d;
    }
    
    public final static int getMaxIndex(int[] array) {
        int max = 0;
        int index = 0;
        for(int i=0; i<array.length; i++) {
            if(array[i] > max) {
                max = array[i];
                index = i;
            }
        }
        return index;
    }
}
