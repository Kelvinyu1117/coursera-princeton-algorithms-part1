package assignment1_percolation;

import java.util.Objects;

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
        for (int i = 1; i <= n; i++) {
            union(grid[1][i], grid[0][0]);
            union(grid[n][i], grid[n + 1][0]);
        }
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
            parent = this;
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

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row && col == point.col;
        }

        public int hashCode() {
            return Objects.hash(row, col, parent);
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

    private void union(Point p1, Point p2) {
        if (p1 == null || p2 == null)
            throw new IllegalArgumentException();

        int cnt1 = 0;
        int cnt2 = 0;
        while (!p1.getParent().equals(p1)) {
            p1 = p1.getParent();
            cnt1++;
        }

        while (!p2.getParent().equals(p2)) {
            p2 = p2.getParent();
            cnt2++;
        }

        if (cnt1 >= cnt2) {
            p2.setParent(p1);
        } else {
            p1.setParent(p2);
        }
    }

    private boolean isConnected(Point p1, Point p2) {
        if (p1 == null || p2 == null)
            throw new IllegalArgumentException();

        while (!p1.getParent().equals(p1)) {
            p1 = p1.getParent();
        }

        while (!p2.getParent().equals(p2)) {
            p2 = p2.getParent();
        }

        return p1.equals(p2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new IllegalArgumentException();

        tGrid[row][col] = true;

        if (row - 1 > 0 && tGrid[row - 1][col])
            union(grid[row][col], grid[row - 1][col]);

        if (row + 1 <= gridSize && tGrid[row + 1][col])
            union(grid[row][col], grid[row + 1][col]);

        if (col - 1 > 0 && tGrid[row][col - 1])
            union(grid[row][col], grid[row][col - 1]);

        if (col + 1 <= gridSize && tGrid[row][col + 1])
            union(grid[row][col], grid[row][col + 1]);


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

        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;

        if (row - 1 > 0 && tGrid[row - 1][col])
            up = true;

        if (row + 1 <= gridSize && tGrid[row + 1][col])
            down = true;

        if (col - 1 > 0 && tGrid[row][col - 1])
            left = true;

        if (col + 1 <= gridSize && tGrid[row][col + 1])
            right = true;

        if (row - 1 < 1) {
            if (col == 1)
                return right && down;
            else if (col == gridSize)
                return left && down;
            else
                return left && right && down;
        } else if (row + 1 > gridSize) {
            if (col == 1)
                return right && up;
            else if (col == gridSize)
                return left && up;
            else
                return left && right && up;
        } else if (col - 1 < 1) {
            return up && down && right;
        } else if (col + 1 > gridSize) {
            return up && down && left;
        } else {
            return up && down && left && right;
        }
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

        for (int i = 1; i <= gridSize; i++) {
            for (int j = 1; j <= gridSize; j++) {
                res.append(grid[i][j]).append(" ");
            }
            res.append("\n");
        }

        return res.toString();
    }

    public void showBlockGraph() {
        for (int i = 1; i <= gridSize; i++) {
            for (int j = 1; j <= gridSize; j++) {
                System.out.print((tGrid[i][j] ? "o" : "x") + " ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(2);
        p.showBlockGraph();

        p.open(1, 1);
        p.open(2, 2);
        p.open(1, 2);

        System.out.println();
        p.showBlockGraph();

        System.out.println("Percolation: " + (p.percolates() ? "Yes" : "No"));
    }
}