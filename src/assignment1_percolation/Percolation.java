package assignment1_percolation;

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
        while (!(p1.getParent().row == p1.row && p1.getParent().col == p1.col)) {
            p1 = p1.getParent();
            cnt1++;
        }

        while (!(p2.getParent().row == p2.row && p2.getParent().col == p2.col)) {
            p2 = p2.getParent();
            cnt2++;
        }

        if (p1.row == 0 && p1.col == 0 || p1.row == gridSize && p1.col == 0 || cnt1 >= cnt2) {
            p2.setParent(p1);
        } else {
            p1.setParent(p2);
        }
    }

    private boolean isConnected(Point p1, Point p2) {
        if (p1 == null || p2 == null)
            throw new IllegalArgumentException();

        while (!(p1.getParent().row == p1.row && p1.getParent().col == p1.col)) {
            p1 = p1.getParent();
        }

        while (!(p2.getParent().row == p2.row && p2.getParent().col == p2.col)) {
            p2 = p2.getParent();
        }

        return p1.row == p2.row && p1.col == p2.col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new IllegalArgumentException();

        if (!isOpen(row, col)) {
            tGrid[row][col] = true;
            numOfOpenSite++;

            if (row - 1 > 0 && tGrid[row - 1][col])
                union(grid[row][col], grid[row - 1][col]);

            if (row + 1 <= gridSize && tGrid[row + 1][col])
                union(grid[row][col], grid[row + 1][col]);

            if (col - 1 > 0 && tGrid[row][col - 1])
                union(grid[row][col], grid[row][col - 1]);

            if (col + 1 <= gridSize && tGrid[row][col + 1])
                union(grid[row][col], grid[row][col + 1]);
        }
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

        return isConnected(grid[row][col], grid[0][0]) && tGrid[row][col];
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

    }
}