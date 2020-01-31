package lift.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FinishedScreen extends MouseAdapter{
    private Test test;
    private Button backButton;
    private MainLoop mainLoop;
    
    public FinishedScreen(Test test, MainLoop mainLoop){
        this.test = test;
        this.backButton = new Button("MainMenu", 800, 600, 300, 80);
        this.mainLoop = mainLoop;
    }
    
    public void mouseReleased(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();
        if(mainLoop.getRunningState() == MainLoop.RUN_STATE.Finished) {
            if(backButton.clicked(mouseX, mouseY)) {
                mainLoop.setRunningState(MainLoop.RUN_STATE.Menu);
            }
        }
    }
    
    public void render(Graphics g) {
        int size = 50;
        Font font = new Font("ariel", 1, size);
        g.setFont(font);
        g.setColor(Color.WHITE);
        String title = "Finished";
        int stringX = Methods.getStringX(MainLoop.WIDTH/2, size, font, title, g);
        g.drawString(title, stringX, 100);
        
        title = "Steps Taken: " + Integer.toString(test.getSteps());
        stringX = Methods.getStringX(MainLoop.WIDTH/2, size, font, title, g);
        g.drawString("Steps Taken: " + Integer.toString(test.getSteps()), stringX, 200);
        
        backButton.render(g);
        
    }
}
