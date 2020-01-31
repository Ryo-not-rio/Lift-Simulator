package lift.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import lift.main.MainLoop.RUN_STATE;

public class TestMenu extends MouseAdapter{
    private MainLoop mainLoop;
    private ArrayList<Slider> sliders = new ArrayList<Slider>();
    private ArrayList<TickButton> rangeTicks = new ArrayList<TickButton>();
    private Slider selectedSlider;
    private Button startButton;
    
    public TestMenu(MainLoop mainLoop) {
        this.sliders.add(new Slider("Floors", MainLoop.WIDTH/2 - 350, 200, 600, 80, 5, 165));
        this.rangeTicks.add(new TickButton(MainLoop.WIDTH/2 + 400, 200, 100, 80));
        this.sliders.add(new Slider("People", MainLoop.WIDTH/2 - 350, 350, 600, 80, 5, 1320));
        this.rangeTicks.add(new TickButton(MainLoop.WIDTH/2 + 400, 350, 100, 80));
        this.sliders.add(new Slider("Lifts", MainLoop.WIDTH/2 - 350, 500, 600, 80, 1, 15));
        this.rangeTicks.add(new TickButton(MainLoop.WIDTH/2 + 400, 500, 100, 80));
        
        this.startButton = new Button("Start", MainLoop.WIDTH/2 + 300, 620, 200, 80);
        this.mainLoop = mainLoop;
    }
    
    public void mousePressed(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();
        
        if(mainLoop.getRunningState() == MainLoop.RUN_STATE.TestMenu) {            
            for(int i=0; i<sliders.size(); i++) {
                if (sliders.get(i).clickedSlider(mouseX, mouseY)) {
                    selectedSlider = sliders.get(i);
                    selectedSlider.setMove(true);
                }
            }
        }
    }
    
    private void startTest() {        
        mainLoop.setTesting(true);
        Test test = new Test(mainLoop);
        
        if(rangeTicks.get(0).getTicked()) test.setMaxFloorNum(sliders.get(0).getValue());
        else test.setFloorNum(sliders.get(0).getValue());
        if(rangeTicks.get(1).getTicked()) test.setMaxPeopleNum(sliders.get(1).getValue());
        else test.setPeopleNum(sliders.get(1).getValue());
        if(rangeTicks.get(2).getTicked()) test.setMaxLiftNum(sliders.get(2).getValue());
        else test.setLiftNum(sliders.get(2).getValue());
        mainLoop.startRun(test, true);
    }
    
    public void mouseReleased(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();
        
        if(mainLoop.getRunningState() == MainLoop.RUN_STATE.TestMenu) {
            if(startButton.clicked(mouseX, mouseY)) {
                startTest();
            }
            
            if(selectedSlider != null) {
                selectedSlider.setMove(false);
            }
            
            for(int i=0; i<rangeTicks.size(); i++) {
                if (rangeTicks.get(i).clicked(mouseX, mouseY)) {
                    rangeTicks.get(i).select();
                }
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
        String title = "Test Menu";
        int stringX = Methods.getStringX(MainLoop.WIDTH/2, size, font, title, g);
        g.drawString(title, stringX, 100);
        
        title = "Range";
        stringX = Methods.getStringX(MainLoop.WIDTH/2 + 410, size, font, title, g);
        g.drawString(title, stringX, 100);
        
        //Rendering sliders
        for(int i=0; i<sliders.size(); i++) {
            sliders.get(i).render(g);
        }
        
        for(int i=0; i<rangeTicks.size(); i++) {
            rangeTicks.get(i).render(g);
        }
        
        //Rendering Buttons
        startButton.render(g);
    }

}
