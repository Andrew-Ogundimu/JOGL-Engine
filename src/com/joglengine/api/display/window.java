package com.joglengine.api.display;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.joglengine.api.event.Event;
import com.joglengine.api.object.Sprite;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;


public class Window implements GLEventListener, KeyListener {
    private GLCanvas canvas;
    private ArrayList<Sprite> sprites;
    public static GL3 OpenGL;
    private int[] dims;
    public boolean initFinished;
    private int fps;
    private ArrayList<Event> events;
    public Window(int width, int height,boolean zaxis) {
        sprites = new ArrayList<>();
        dims = new int[]{width,height};
        events = new ArrayList<>();
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities cap = new GLCapabilities(profile);
        canvas = new GLCanvas(cap);
        canvas.addGLEventListener(this);
        canvas.addKeyListener(this);
        JFrame win = new JFrame("Test");
        win.setSize(dims[0],dims[1]);
        win.add(canvas);
        win.setVisible(true);
        win.setDefaultCloseOperation(win.EXIT_ON_CLOSE);
        System.out.println("constructor finished.");
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        OpenGL = glAutoDrawable.getGL().getGL3();
        System.out.println("init open");
        initFinished = true;
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }


    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
    public ArrayList<Event> pollEvents() {
        ArrayList<Event> newQ = (ArrayList<Event>)events.clone();
        events.clear();
        return newQ;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        events.add(new Event(Event.KEY_DOWN,new int[]{keyEvent.getKeyCode()}));
        //System.out.println(keyEvent.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        events.add(new Event(Event.KEY_UP,new int[]{keyEvent.getKeyCode()}));
        //System.out.println(keyEvent.getKeyCode());
    }
    public void setFramerate(int framerate) { fps = framerate; }

    public int getFramerate() { return fps; }

    public void update() throws InterruptedException {
        Thread.sleep(1000/fps);
        //System.out.println("update screen");
    }

    public void draw(Sprite s) {
        s.bind();
    }
}
