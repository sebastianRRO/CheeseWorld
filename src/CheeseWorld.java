//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

//*******************************************************************************
// Class Definition Section

public class CheeseWorld implements Runnable {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    public Image cheesePic;
    public Image mousePic;

    public Mouse mouse1;
    //where we declare the variable cheesy
    // cheese[]= type of object, and cheesy is the array

    public Cheese theCheese;
    public ArrayList<Cheese> cheesy = new ArrayList<Cheese>();


    //Declare the objects used in the program
    //These are things that are made up of more than one variable type


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        CheeseWorld myApp = new CheeseWorld();   //creates a new instance of the game
        new Thread(myApp).start();//creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public CheeseWorld() {

        setUpGraphics();

        //variable and objects
        //load images
        cheesePic = Toolkit.getDefaultToolkit().getImage("cheese.gif"); //load the picture
        mousePic = Toolkit.getDefaultToolkit().getImage("jerry.gif"); //load the picture


        //create (construct) the objects needed for the game
        mouse1 = new Mouse(200, 300, 4, 4, mousePic);
        theCheese = new Cheese(400, 300, 3, -4, cheesePic);

        // consturcy my cheesy array
        //cheesy.length is the size of the array

        for(int i=0; i < 100; i++) {
            int randomXpos = (int) (Math.random()*1000);
            int randomYpos = (int) (Math.random()*700);
            int randomDX = (int) (Math.random()*5);
            int randomDY = (int) (Math.random()*5);
            cheesy.add(new Cheese(randomXpos, randomYpos, randomDX, randomDY, cheesePic));
        }



    }// CheeseWorld()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up

    public void moveThings() {
        //calls the move( ) code in the objects
        mouse1.move();
        theCheese.move();
        for (Cheese c: cheesy) {
            c.move();
        }
        //equivelent
//        for(int i=0; i <cheesy.size;i++) {
//            cheesy[i].move();
//        }

    }

    public void checkIntersections() {
        for (Cheese c: cheesy) {
            if (mouse1.rec.intersects(c.rec)) {
                c.isAlive = false;
                mouse1.width = mouse1.width + 1;
                mouse1.height = mouse1.height + 1;
            }
        }

//        for(int i=0; i <cheesy.length; i++) {
//            if (mouse1.rec.intersects(cheesy[i].rec)) {
//                cheesy[i].isAlive = false;
//                mouse1.width = mouse1.width + 1;
//                mouse1.height = mouse1.height + 1;
//            }
//        }



    }

    //paints things on the screen using bufferStrategy
    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //put your code to draw things on the screen here.
        g.drawImage(mouse1.pic, mouse1.getxpos() , mouse1.ypos, mouse1.width, mouse1.height, null);
        g.drawImage(theCheese.pic, theCheese.xpos, theCheese.ypos, theCheese.width, theCheese.height, null);

//
//        for(int i=0; i <cheesy.length;i++) {
//            if (cheesy[i].isAlive == true) {
//                g.drawImage(cheesy[i].pic, cheesy[i].xpos, cheesy[i].ypos, cheesy[i].width, cheesy[i].height, null);
//            }
//        }
        for (Cheese c: cheesy) {
            if (c.isAlive == true) {
                g.drawImage(c.pic, c.xpos, c.ypos, c.width, c.height, null);
            }
        g.dispose();
        bufferStrategy.show();
        }
    }

    public void run(){
        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            checkIntersections();
            render();  // paint the graphics

            //Makes a timer
            pause(10);
        }
    }

    //Graphics setup method
    public void setUpGraphics() {
        frame = new JFrame("CheeseWorld");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

}
