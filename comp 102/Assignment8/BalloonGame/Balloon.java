// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 8
 * Name: Elisha Jones
 * Username: joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.util.*;
import java.awt.Color;
import java.io.*;

/**
 * Represents a balloon that can grow until it bursts.
 * While it is active (before it bursts) it is drawn as a coloured circle.
 * When is is no longer active (after being burst) it is a light grey circle
 */
public class Balloon{
    // Fields
    private double radius = 10;
    private double centerX, centerY;
    private Color color;
    private boolean active = true;

    /**
     * Construct a new Balloon object. 
     * Parameters are the coordinates of the center of the balloon 
     */
    public Balloon(double x, double y){
        this.centerX = x;
        this.centerY = y;
        this.color = Color.getHSBColor((float)Math.random(), 1.0f, 1.0f);
    }

    /**
     * Draw the balloon
     */
    public void draw(){
        if (this.active){
            UI.setColor(color);
            UI.fillOval(centerX-radius, centerY-radius, radius*2, radius*2);
            UI.setColor(Color.black);
            UI.drawOval(centerX-radius, centerY-radius, radius*2, radius*2);
        }
        else {
            UI.setColor(Color.lightGray);
            UI.fillOval(centerX-radius, centerY-radius, radius*2, radius*2);
        }
    }

    /**
     * Make the balloon larger by a random amount between 4 and 10
     */
    public void expand(){
        if (this.active){
            this.radius = this.radius + (Math.random()*6 + 4);
        }
    }

    /**
     * Returns true if the point (x,y) is on the balloon, and false otherwise
     */
    public boolean on(double x, double y){
        double dx = this.centerX - x;
        double dy = this.centerY - y;
        return ((dx*dx + dy*dy) < (this.radius * this.radius));
    }

    /**
     * Returns true if the balloon is still active (hasn't been burst) and false otherwise
     */
    public boolean isActive(){
        return this.active;
    }

    /**
     * Returns true if this Balloon is touching the other balloon, and false otherwise
     */
    public boolean isTouching(Balloon other){
        double dx = other.centerX - this.centerX;
        double dy = other.centerY - this.centerY;
        double touchingDist = other.radius + this.radius;
        return (Math.hypot(dx,dy) < touchingDist);
    }

    /** 
     * Calculates and returns the area of the balloon
     *  Returns it in "centi-pixels" (ie, number of pixels/100)
     *  to keep them in a reasonable range. 
     */
    public int size(){
        return  (int) ((this.radius * this.radius * Math.PI)/100);
    }

    /** 
     * Burst the balloon (draws it in gray, and pauses briefly)
     */
    public void burst(){
        this.active = false;
        this.draw();
        UI.sleep(20);
    }
}
