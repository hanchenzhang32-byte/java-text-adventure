package org.uob.a1;

/**
 * Simple 2D map that stores room symbols in a grid.
 * The game uses this so that the player can type "map" and see the layout.
 */
public class Map {
    private int width;
    private int height;
    private char[][] grid;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new char[height][width];

        // initialise every cell with '.'
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                grid[r][c] = '.';
            }
        }
    }

    // put a room symbol on the map at the given position
    public void placeRoom(Position pos, char symbol) {
        int x = pos.x;   // column
        int y = pos.y;   // row
        if (y >= 0 && y < height && x >= 0 && x < width) {
            grid[y][x] = symbol;
        }
    }

    // return the whole map as text, line by line
    public String display() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                sb.append(grid[r][c]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
