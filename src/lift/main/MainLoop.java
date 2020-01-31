package lift.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * The MainLoop class initialises the program and provides the main loop which renders and 
 * executes the next step (tick) for each object.
 * 
 * 
 * 
 * @author R.S.
 *
 */
public class MainLoop extends Canvas implements Runnable{
    
    /*
     * HEIGHT, WIDTH: Preferred size of window to generate
     * actualHeight: Actual height created when the window object is generated
     * liftBrain: The algorithm to be used to control the algorithms
     * building: Contains all lifts and floor objects which contains person objects
     * 
     */
    private static final long serialVersionUID = 5638312786697430030L;

    public static final int HEIGHT = 800, WIDTH = (int) (HEIGHT * 1.5);
    public static int actualHeight;

    public static enum RUN_STATE {
        Running, Menu, Finished, TestMenu
    };
    
    private RUN_STATE runningState = RUN_STATE.Menu;
    private Thread thread;
    private boolean running = false;
    private Building building;
    private Test test;
    private Menu menu;
    private TestMenu testMenu;
    private FinishedScreen endScreen;
    private int loading = 0;
    public boolean testing = false;
    private float delaySeconds;
    private double ns = 1000000000 * this.delaySeconds;
    private LoadingScreen loadingScreen = new LoadingScreen();
    
    public Window window;
        
    /**
     * Initialises and creates all the objects necessary for the program.
     */
    
    public MainLoop() {
        this.window = new Window(WIDTH, HEIGHT, "Lift System", this);
        this.menu = new Menu(this);
        this.testMenu = new TestMenu(this);
        this.addMouseListener(menu);
        this.addMouseListener(testMenu);
        actualHeight = window.getHeight();
        
        this.start();
    }
    
    public void startRun(Test test, boolean testing) {        
        this.test = test;
        this.endScreen = new FinishedScreen(test, this);
        this.addMouseListener(endScreen);
        
        this.building = this.test.getBuilding();
        
        if(testing) delaySeconds = 0.2f;
        else delaySeconds = 0.5f;
        this.ns = 1000000000 * delaySeconds;
        
        this.runningState = RUN_STATE.Running;
        
        this.testing = testing;
    }
    
    public void setBuilding(Building building) {
        this.building = building;
    }
    
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Implementing the main loop for the program. It is a 
     * continuous loop where the number of frames per second are kept
     * track of and ticks are executed every specified number of frames.
     * Each object is rendered every loop.
     */
    public void run() {
        this.requestFocus(); // Automatically select generated window
        long lastTime = System.nanoTime();
        double delta = 0;
        long timer = System.currentTimeMillis();
        @SuppressWarnings("unused")
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / this.ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                delta = 0;
            }
            
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }
    
    public void tick() {
        if (this.runningState == RUN_STATE.Running) building.tick();
        else if(this.runningState == RUN_STATE.Menu) menu.tick();
        else if(this.runningState == RUN_STATE.TestMenu) testMenu.tick();
    }
    
    /*
     * Create a graphics object to be used to render every object,
     * create a blank canvas and render all objects contained in the
     * building object.
     */
    public void render() {
        if(!this.testing || this.testing) {
            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null) {
                this.createBufferStrategy(2);
                return;
            }
            
            Graphics g = bs.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            
            try { 
                if(this.runningState == RUN_STATE.Menu) {
                    this.menu.render(g);
                } else if(this.runningState == RUN_STATE.Running) {
                    this.building.render(g);
                } else if(this.runningState == RUN_STATE.Finished) {
                    this.endScreen.render(g);
                } else if(this.runningState == RUN_STATE.TestMenu) {
                    this.testMenu.render(g);
                }
            } 

            catch (Throwable e) { 
                e.printStackTrace(); 
            } 
            
            g.dispose();
            bs.show();
        } else {
            if(this.loading < 100) {
                BufferStrategy bs = this.getBufferStrategy();
                if (bs == null) {
                    this.createBufferStrategy(2);
                    return;
                }
                
                Graphics g = bs.getDrawGraphics();
                g.setColor(Color.black);
                g.fillRect(0, 0, WIDTH, HEIGHT);
            
                loadingScreen.render(g);
                loading++;
                
                g.dispose();
                bs.show();
            }
        }
    }
    
    public void setRunningState(RUN_STATE state) {
        this.runningState = state;
    }
    
    public RUN_STATE getRunningState() {
        return runningState;
    }
    
    public void setDelaySeconds(float seconds) {
        this.delaySeconds = seconds;
        this.ns = 1000000000 * seconds;
    }
    
    public void setTesting(boolean testing) {
        this.testing = testing;
    }
    
    public static void main(String args[]) {
        new MainLoop();
    }

}
