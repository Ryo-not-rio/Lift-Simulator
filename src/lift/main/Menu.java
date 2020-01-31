package lift.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Menu extends MouseAdapter{
    private MainLoop mainLoop;
    private ArrayList<Slider> sliders = new ArrayList<Slider>();
    private Slider selectedSlider;
    private Button startButton;
    private Button testButton;
    
    public Menu(MainLoop mainLoop) {
        this.sliders.add(new Slider("Floors", MainLoop.WIDTH/2 - 300, 200, 600, 80, 5, 165));
        this.sliders.add(new Slider("People", MainLoop.WIDTH/2 - 300, 350, 600, 80, 5, 1320));
        this.sliders.add(new Slider("Lifts", MainLoop.WIDTH/2 - 300, 500, 600, 80, 1, 15));
        this.startButton = new Button("Start", MainLoop.WIDTH/2 + 300, 620, 200, 80);
        this.testButton = new Button("Test", MainLoop.WIDTH/2 - 500, 620, 200, 80);
        this.mainLoop = mainLoop;
    }
    
    public void mousePressed(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();
        
        if(mainLoop.getRunningState() == MainLoop.RUN_STATE.Menu) {            
            for(int i=0; i<sliders.size(); i++) {
                if (sliders.get(i).clickedSlider(mouseX, mouseY)) {
                    selectedSlider = sliders.get(i);
                    selectedSlider.setMove(true);
                }
            }
        }
    }
    
    public void mouseReleased(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();
        
        if(mainLoop.getRunningState() == MainLoop.RUN_STATE.Menu) {
            if(startButton.clicked(mouseX, mouseY)) {
                mainLoop.setTesting(false);
                Test test = new Test(mainLoop);
                test.setFloorNum(sliders.get(0).getValue());
                test.setPeopleNum(sliders.get(1).getValue());
                test.setLiftNum(sliders.get(2).getValue());
                mainLoop.startRun(test, false);
            }
            
            if(testButton.clicked(mouseX, mouseY)) {
                mainLoop.setRunningState(MainLoop.RUN_STATE.TestMenu);
            }
            
            
            if(selectedSlider != null) {
                selectedSlider.setMove(false);
            }
        }
    }
    
    public void tick() {
        if(selectedSlider!=null) {
            if(selectedSlider.getMove()) {
                selectedSlider.setX();
            }
        }
    }
    
    public void render(Graphics g) {
        //showing title
        int size = 50;
        Font font = new Font("ariel", 1, size);
        g.setFont(font);
        g.setColor(Color.WHITE);
        String title = "Lift thing";
        int stringX = Methods.getStringX(MainLoop.WIDTH/2, size, font, title, g);
        g.drawString(title, stringX, 100);
        
        //Rendering sliders
        for(int i=0; i<sliders.size(); i++) {
            sliders.get(i).render(g);
        }
        
        //Rendering Buttons
        startButton.render(g);
        testButton.render(g);
    }
}
