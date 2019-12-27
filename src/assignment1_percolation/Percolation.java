package assignment1_percolation;

import java.util.Arrays;

public class Percolation {

    private Point[][] grid;
    private boolean[][] tGrid;
    private int gridSize;
    private int numOfOpenSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException();

        numOfOpenSite = 0;
        gridSize = n;

        grid = new Point[n + 2][n + 2];
        tGrid = new boolean[n + 2][n + 2];

        grid[0][0] = new Point(0, 0);

        grid[n + 1][0] = new Point(n + 1, 0);

        // initialize the grids
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = new Point(i, j);
            }
        }

        // connect the first row and last row to the virtual sites
//        for (int i = 1; i <= n; i++) {
//            union(grid[1][i], grid[0][0]);
//            union(grid[n][i], grid[n + 1][1]);
//        }
    }

    private class Point {
        private int row;
        private int col;
        private Point parent;


        public Point() {
            row = 0;
            col = 0;
            parent = this;
        }

        public Point(int r, int c) {
            row = r;
            col = c;
        }

        public void setCoordinate(int tRow, int tCol) {
            this.row = tRow;
            this.col = tCol;
        }

        public void setParent(Point p) {
            this.parent = p;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public Point getParent() {
            return this.parent;
        }


        public void setRow(int row) {
            this.row = row;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public boolean isRoot() {
            return row == parent.row && col == parent.col;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

    private void union(Point p1, Point p2) {

    }

    private boolean isConnected(Point p1, Point p2) {
        return false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new IllegalArgumentException();


        numOfOpenSite++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new IllegalArgumentException();

        return tGrid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new IllegalArgumentException();
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnected(grid[0][0], grid[gridSize + 1][0]);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        for(int i = 1; i <= gridSize; i++) {
            for(int j = 1; j <= gridSize; j++) {
                res.append(grid[i][j]).append(" ");
            }
            res.append("\n");
        }

        return res.toString();
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);

        System.out.println(p.toString());
    }
}