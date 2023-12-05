// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 4
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/** Displays an animated story of at least two characters from a script in a file.
 *  Each line of a script file is an instruction.
 *  See "simple-script.txt" for an example.
 *
 *  These are the possible instructions (<..> is describing a value
 *    CHARACTER  <name> <type>
 *         creates a new Animal with the given name and type, but holds them off-screen
 *         <type> can be any of bird, dinosaur, dog, grasshopper, snake, tiger, turtle.
 *         This instruction must occur before any other instructions involving the animal
 *         It is OK to insist that all the CHARACTER instructions are at the beginning of
 *         the script.
 *    ENTER <name> <x> <y>   :
 *         make the animal with that name appear on the screen at the position <x>,<y>
 *    GO <name> <direction> <distance> :
 *         turn the animal with that name to the direction (left or right) and move by <distance>
 *    TELEPORT <name> <x> <y> :
 *         teleport the animal with that name to the new position
 *    EXIT <name>
 *         make the animal with that name disappear from the screen
 *    JUMP <name> <height> :
 *         make the animal with that name jump by the height
 *    SPEAK <name> <words> :
 *         make the animal with that name speak the words (can be multiple tokens)
 *    THINK/SHOUT/INTRODUCE work just like SPEAK.
 *    CAPTION <words>  :
 *         display the words as a caption at the top of the window.
 * 
 *  extras (for top marks)
 *    ASK <question words>, ......
 *    ELSE
 *    ENDASK
 *         ask the user a question and follow two different paths in the story depending
 *         on the answer:
 *           the instructions between ASK and ELSE if they said "yes"
 *           the instructions between ELSE and ENDASK if they said "no"
 *         You do not have to handle ASK instructions inside other ASK instructions,
 *         but you can if you want! (you might need to label the ASK/ELSE/ENDASK instructions!
 *    
 *    REPEAT <number>
 *    ENDREPEAT
 *         repeat the instructions between REPEAT and ENDREPEAT <number> times.
 *    
 */
public class AnimalStory{

    /**
     * storyFromFile opens a script file, and animates the story in the file
     *  by following all the instructions in the script
     *  Each line of the file is one instruction (see above).
     */
    public void storyFromFile(){
        /*# YOUR CODE HERE */

    }

    /** Set up the buttons */
    public void setupGUI (){
        UI.addButton("Clear", UI::clearGraphics );
        UI.addButton("Story", this::storyFromFile);
        UI.addButton("Quit", UI::quit );
    }        

    public static void main(String[] args){
        AnimalStory Ani = new AnimalStory();
        Ani.setupGUI();
    }

}
