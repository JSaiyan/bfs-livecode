import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Search 
{
     /**
     * Finds the location of the nearest reachable cheese from the rat's position.
     * Returns a 2-element int array: [row, col] of the closest 'c'. If there are multiple
     * cheeses that are tied for the same shortest distance to reach, return
     * any one of them.
     * 
     * 'R' - the rat's starting position (exactly one)
     * 'o' - open space the rat can walk on
     * 'w' - wall the rat cannot pass through
     * 'c' - cheese that the rat wants to reach
     * 
     * If no rat is found, throws EscapedRatException.
     * If more than one rat is found, throws CrowdedMazeException.
     * If no cheese is reachable, throws HungryRatException
     *
     * oooocwco
     * woowwcwo
     * ooooRwoo
     * oowwwooo
     * oooocooo
     *
     * The method will return [0,4] as the nearest cheese.
     *
     * @param maze 2D char array representing the maze
     * @return int[] location of the closest cheese in row, column format
     * @throws EscapedRatException if there is no rat in the maze
     * @throws CrowdedMazeException if there is more than one rat in the maze
     * @throws HungryRatException if there is no reachable cheese
     * 
     * bfs when they asking for shortest route or least amount / length
     * dfs is how many / count 
     * 
     */

    
    public static int[] nearestCheese(char[][] maze) throws EscapedRatException, CrowdedMazeException, HungryRatException 
    {
        //find your starting point
        int [] start = findRat(maze);

        boolean [][] visited = new boolean[maze.length][maze[0].length];
        Queue<int[]> queue = new LinkedList<>();

        //put start in the queue
        //initialy queue will now have start
        queue.add(start);

        //means it will contain something 
        while(!queue.isEmpty())
        {
            //poll is pop in queue
            //this will now take start and put it into current 
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            if(visited[row][col])
            {
                continue;
            }
            visited[row][col] = true;

            //if we found cheese
            if(maze[row][col] == 'c')
            {
                //return location of c
                return current;
            }

            //adding possible moves each location we go.
            queue.addAll(possibleMoves(current, maze));

        }

        throw new HungryRatException();

    }

    public static List<int[]> possibleMoves(int[]current, char[][]maze)
    {
        //taking the int out of the array 
        int row = current[0];
        int col = current[1];

        //up, down, left, right
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        List<int[]> neighbors = new ArrayList<>();

        //each row in our 2d matrix
        for (int[] direction : directions)
        {
            //create a new varaible
            //moves
            int changeR = direction[0];
            int changeC = direction[1];
            int newR = changeR + row;
            int newC = changeC + col;

            if (newR >= 0 && newR < maze.length && newC >= 0 && newC < maze[newR].length && maze[newR][newC] != 'w')
            {
                int[] validLocation = new int[] {newR, newC};
                neighbors.add(validLocation);
            }
        }
        return neighbors;

    }




    //this method helps establish where explorer starts
    //start method
    public static int[] findRat(char[][] maze) throws EscapedRatException, CrowdedMazeException
    {
        
        int[] ratLocation = null;

        for (int row = 0; row < maze.length; row++)
        {
            for(int col = 0; col < maze[0].length; col++)
            {
                //check to see if R is in the maze
                if(maze[row][col] == 'R')
                {
                    //check to see if theres more than one R
                    if(ratLocation != null)
                    {
                        throw new CrowdedMazeException();
                    }
                    ratLocation = new int[] {row, col};
                    // ratLocation[0] == row;
                    // ratLocation[1] == col;
                }
                
            }
        }
        //if ratlocaton is null we cant find it
        if (ratLocation == null)
        {
            throw new EscapedRatException();
        }

        return ratLocation;

        
    }



}