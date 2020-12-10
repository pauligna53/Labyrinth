package de.uniwue.gdp.labyrinth;

import de.uniwue.gdp.labyrinth.model.Maze;

import java.util.Arrays;
import java.util.Random;

public class Explorer {

    public static void main(String[] args) {
        Explorer explorer = new Explorer();
        Maze maze = new Maze() {
            @Override
            public int width() {
                return 0;
            }

            @Override
            public int height() {
                return 0;
            }

            @Override
            public boolean isWall(int direction) {
                return false;
            }

            @Override
            public void mark(int direction) {

            }

            @Override
            public int marks(int direction) {
                return 0;
            }

            @Override
            public void walk(int direction) {

            }
        };
        explorer.exploreMaze(maze);

    }

    public String exploreMaze(Maze z){
        Maze maze = new Maze() {
            @Override
            public int width() {
                return 0;
            }

            @Override
            public int height() {
                return 0;
            }

            @Override
            public boolean isWall(int direction) {
                return false;
            }

            @Override
            public void mark(int direction) {

            }

            @Override
            public int marks(int direction) {
                return 0;
            }

            @Override
            public void walk(int direction) {

            }
        };
        int width = z.width();
        int height = z.height();
        String[][] mymaze = new String[height][width];
        int i = 0;
        int j = 0;

        // creating the frame of the maze, made of "#"
        for (j = 0; j < width; j++) {   //upper border
            mymaze[0][j] = "#";
        }
        for (i = 0; i < height; i++) {   //right border
            mymaze[i][width-1] = "#";
        }
        for (i = 0; i < height; i++) {    //left border
            mymaze[i][0] = "#";
        }
        for (j = 0; j < width; j++) {    //lower border
            mymaze[height-1][j] = "#";
        }


        mymaze[i][j] = " "; //initialize the starting point
        String cardinalPoint = new String();
        cardinalPoint = "south";
        int ok = 0;

        while(ok == 0) {


            do { //keep walking and mapping each tile until you hit a crossing or a wall
                if(!maze.isWall(Maze.Direction.AHEAD)) {
                    maze.walk(Maze.Direction.AHEAD);
                    //figure out the actual moving direction and change i/j values accordingly

                    if (cardinalPoint.equals("south"))              //cardinal point stays the same as we're moving ahead
                        i++;
                    else if (cardinalPoint.equals("north"))
                        i--;
                    else if (cardinalPoint.equals("west"))
                        j--;
                    else if (cardinalPoint.equals("east"))
                        j++;

                }
                else if(!maze.isWall(Maze.Direction.LEFT)) {
                    //map AHEAD as a wall
                    if (cardinalPoint.equals("south"))
                        mymaze[i+1][j] = "#";
                    else if (cardinalPoint.equals("north"))
                        mymaze[i-1][j] = "#";
                    else if (cardinalPoint.equals("west"))
                        mymaze[i][j-1] = "#";
                    else if (cardinalPoint.equals("east"))
                        mymaze[i][j+1] = "#";


                    maze.walk(Maze.Direction.LEFT);
                    //figure out the actual moving direction and change i/j values  accordingly
                    if (cardinalPoint.equals("south"))
                    {
                        cardinalPoint = "east";
                        j++;
                    }
                    else if (cardinalPoint.equals("north"))
                    {
                            cardinalPoint = "west";
                            j--;
                    }
                    else if (cardinalPoint.equals("west"))
                    {
                        cardinalPoint = "south";
                        i++;
                    }
                    else if (cardinalPoint.equals("east")){
                        cardinalPoint = "north";
                        i--;
                    }

                }
                else if(!maze.isWall(Maze.Direction.RIGHT)) {
                    //map LEFT as a wall
                    if (cardinalPoint.equals("south"))
                        mymaze[i][j+1] = "#";
                    else if (cardinalPoint.equals("north"))
                        mymaze[i][j-1] = "#";
                    else if (cardinalPoint.equals("west"))
                        mymaze[i+1][j] = "#";
                    else if (cardinalPoint.equals("east"))
                        mymaze[i-1][j] = "#";

                    maze.walk(Maze.Direction.RIGHT);
                    //figure out the actual moving direction and change i/j values accordingly
                    if (cardinalPoint.equals("south"))
                    {
                        cardinalPoint = "west";
                        j--;
                    }
                    else if (cardinalPoint.equals("north"))
                    {
                        cardinalPoint = "east";
                        j++;
                    }
                    else if (cardinalPoint.equals("west"))
                    {
                        cardinalPoint = "north";
                        i--;
                    }
                    else if (cardinalPoint.equals("east")){
                        cardinalPoint = "south";
                        i++;
                    }
                }
                else{                                       //you've hit a dead end, go back
                    //map RIGHT as a wall
                    if (cardinalPoint.equals("south"))
                        mymaze[i][j-1] = "#";
                    else if (cardinalPoint.equals("north"))
                        mymaze[i][j+1] = "#";
                    else if (cardinalPoint.equals("west"))
                        mymaze[i-1][j] = "#";
                    else if (cardinalPoint.equals("east"))
                        mymaze[i+1][j] = "#";

                    maze.walk(Maze.Direction.BACK);

                    if (cardinalPoint.equals("south"))
                    {
                        cardinalPoint = "north";
                        i--;
                    }
                    else if (cardinalPoint.equals("north"))
                    {
                        cardinalPoint = "south";
                        i++;
                    }
                    else if (cardinalPoint.equals("west"))
                    {
                        cardinalPoint = "east";
                        j++;
                    }
                    else if (cardinalPoint.equals("east")){
                        cardinalPoint = "west";
                        j--;
                    }
                }
                mymaze[i][j] = " ";
            } while (!isItACrossing(maze));

            mymaze[i][j] = " "; //just got out of the WHILE, meaning we're currently on a crossing

            int newDirection = whichWay(maze);
            if(newDirection == 4)                             //exits the endless loop
                break;
            switch (newDirection) {                           //now that we have our new direction, we walk there, changing the cardinal point and
                case 0:                                     //matrix parameters accordingly. first case is LEFT
                    if (cardinalPoint.equals("south")) {
                        cardinalPoint = "east";
                        j++;
                        maze.walk(Maze.Direction.LEFT);
                    } else if (cardinalPoint.equals("east")) {
                        cardinalPoint = "north";
                        i--;
                        maze.walk(Maze.Direction.LEFT);
                    } else if (cardinalPoint.equals("north")) {
                        cardinalPoint = "west";
                        j--;
                        maze.walk(Maze.Direction.LEFT);
                    } else {
                        cardinalPoint = "south";
                        i++;
                        maze.walk(Maze.Direction.LEFT);
                    }

                case 1:                                    //ahead, direction doesn't change
                    if (cardinalPoint.equals("south")) {
                        i++;
                        maze.walk(Maze.Direction.AHEAD);
                    } else if (cardinalPoint.equals("east")) {
                        j++;
                        maze.walk(Maze.Direction.AHEAD);
                    } else if (cardinalPoint.equals("north")) {
                        i--;
                        maze.walk(Maze.Direction.AHEAD);
                    } else {
                        j--;
                        maze.walk(Maze.Direction.AHEAD);
                    }

                case 2:                                          //right
                    if (cardinalPoint.equals("south")) {
                        cardinalPoint = "west";
                        j--;
                        maze.walk(Maze.Direction.RIGHT);
                    } else if (cardinalPoint.equals("east")) {
                        cardinalPoint = "south";
                        i++;
                        maze.walk(Maze.Direction.RIGHT);
                    } else if (cardinalPoint.equals("north")) {
                        cardinalPoint = "east";
                        j++;
                        maze.walk(Maze.Direction.RIGHT);
                    } else {
                        cardinalPoint = "north";
                        i--;
                        maze.walk(Maze.Direction.RIGHT);
                    }

                case 3:                                        //back
                    if (cardinalPoint.equals("south")) {
                        cardinalPoint = "north";
                        i--;
                        maze.walk(Maze.Direction.BACK);
                    } else if (cardinalPoint.equals("east")) {
                        cardinalPoint = "west";
                        j--;
                        maze.walk(Maze.Direction.BACK);
                    } else if (cardinalPoint.equals("north")) {
                        cardinalPoint = "souht";
                        i++;
                        maze.walk(Maze.Direction.BACK);
                    } else {
                        cardinalPoint = "east";
                        j--;
                        maze.walk(Maze.Direction.BACK);
                    }


            }
        }

        String myMazeVectorized = new String();
        for (int m = 0; m < height; m++) {
            for (int n = 0; n < width ; n++) {
                myMazeVectorized = myMazeVectorized + mymaze[m][n];

            }
            myMazeVectorized = myMazeVectorized + "\n";
        }

        return myMazeVectorized;

    }
    //auxiliary function to determine whether the current field is at a crossing
    public boolean isItACrossing(Maze maze){
        int ok = 0;
        if(!maze.isWall(Maze.Direction.AHEAD))
            ok++;
        if(!maze.isWall(Maze.Direction.BACK))
            ok++;
        if(!maze.isWall(Maze.Direction.LEFT))
            ok++;
        if(!maze.isWall(Maze.Direction.RIGHT))
            ok++;
        if(ok >= 3)
            return true;
        else
            return false;
    }
    //auxiliary function to determine the amount of markings and choose the next action
    public int whichWay(Maze maze){
        int marksAhead = maze.marks(Maze.Direction.AHEAD);
        int marksBack = maze.marks(Maze.Direction.BACK);
        int marksLeft = maze.marks(Maze.Direction.LEFT);
        int marksRight = maze.marks(Maze.Direction.RIGHT);
        Random random = new Random();
        int rdirection3 = random.nextInt(3) ;         //all 3 directions are free
        int rdirection2 = random.nextInt(2);          //only 2 directions are reachable, the other one is a wall


        if(marksAhead + marksBack + marksLeft + marksRight == 0)            //if no markings
        {
            if(!maze.isWall(Maze.Direction.AHEAD) && !maze.isWall(Maze.Direction.LEFT) && maze.isWall(Maze.Direction.RIGHT))
                return rdirection2;                      //return Left or Ahead at random
            if(maze.isWall(Maze.Direction.AHEAD) && !maze.isWall(Maze.Direction.LEFT) && !maze.isWall(Maze.Direction.RIGHT))
            {
                int TwoORZero = random.nextBoolean() ? 0 : 2;         //variable randomly takes the value of 2 or 0, representing left or right
                return TwoORZero;
            }
            if(!maze.isWall(Maze.Direction.AHEAD) && !maze.isWall(Maze.Direction.RIGHT) && maze.isWall(Maze.Direction.LEFT))
            {
                int OneORTwo = random.nextBoolean() ? 1 : 2;         //variable randomly takes the value of 1 or 2, representing ahead or right
                return OneORTwo;
            }
        }
        else if (marksAhead + marksBack + marksLeft + marksRight > 0 && marksBack == 0)   //markings but none where u came from, turn back
                return 3;

        else if (marksAhead + marksBack + marksLeft + marksRight > 0 && marksBack > 0)   //if every direction is marked & none are walls
            if(!maze.isWall(Maze.Direction.AHEAD) && !maze.isWall(Maze.Direction.RIGHT) && !maze.isWall(Maze.Direction.LEFT)) {
                if (mininmum4(marksAhead, marksBack, marksLeft, marksRight) == 2)     //if all have 2 markings, then we're done
                    return 4; // code for being done
                else
                    return mininmum4(marksAhead, marksBack, marksLeft, marksRight); //return the direction with the least markings
            }

        else if (marksAhead + marksBack + marksLeft + marksRight > 0 && marksBack > 0)   //if every direction is marked & ahead is a wall
                if(maze.isWall(Maze.Direction.AHEAD) && !maze.isWall(Maze.Direction.RIGHT) && !maze.isWall(Maze.Direction.LEFT)) {
                    if (mininmum3(marksBack, marksLeft, marksRight) == 2)     //if all have 2 markings, then we're done
                        return 4; // code for being done
                    else
                        return mininmum3(marksBack, marksLeft, marksRight); //return the direction with the least markings
                }

        else if (marksAhead + marksBack + marksLeft + marksRight > 0 && marksBack > 0)   //if every direction is marked & left is a wall
                    if(maze.isWall(Maze.Direction.AHEAD) && !maze.isWall(Maze.Direction.RIGHT) && !maze.isWall(Maze.Direction.LEFT)) {
                        if (mininmum3(marksBack, marksAhead, marksRight) == 2)     //if all have 2 markings, then we're done
                            return 4; // code for being done
                        else
                            return mininmum3(marksBack, marksAhead, marksRight); //return the direction with the least markings
                    }

        else if (marksAhead + marksBack + marksLeft + marksRight > 0 && marksBack > 0)   //if every direction is marked & rigth is a wall
                        if(maze.isWall(Maze.Direction.AHEAD) && !maze.isWall(Maze.Direction.RIGHT) && !maze.isWall(Maze.Direction.LEFT)) {
                            if (mininmum3(marksBack, marksLeft, marksAhead) == 2)     //if all have 2 markings, then we're done
                                return 4; // code for being done
                            else
                                return mininmum3(marksBack, marksLeft, marksAhead); //return the direction with the least markings
                        }


        return 0;   //mandatory return statement, won't be used as the return will happen in one of the cases above

    }
    //auxiliary function to find the minimum of 4

    public int mininmum4(int a, int b, int c, int d){
            int[] tab = {a, b, c, d};
            int result = Arrays.stream(tab).min().getAsInt();
            return result;

    }
    //auxiliary function to find the minimum 3

    public int mininmum3(int a, int b, int c){
        int[] tab = {a, b, c};
        int result = Arrays.stream(tab).min().getAsInt();
        return result;

    }

}
//Paul Igna s394459

