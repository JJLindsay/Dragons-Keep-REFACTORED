package view;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/1/2015
 *
 * This class starts the actual game
 *
 * Purpose: Start the game without revealing its mechanics
 */
public class StartGame
{
    public static void main(String[] args)
    {
//        String word = "\nAre you going to collect this?";
//        System.out.println(word.contains("Are you"));
        View view = new View();
        view.gameMenus();
    }
}
