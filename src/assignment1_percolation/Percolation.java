package assignment1_percolation;

public class Percolation {

    private final Point[][] grid;
    private final boolean[][] tGrid;
    private final int gridSize;
    private int[][] sz;
    private int numOfOpenSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException();

        numOfOpenSite = 0;
        gridSize = n;

        grid = new Point[n + 2][n + 2];
        tGrid = new boolean[n + 2][n + 2];
        sz = new int[n + 2][n + 2];

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
            sz[1][i] = n;
            sz[n][i] = n;
            grid[1][i].setParent(grid[0][0]);
            grid[n][i].setParent(grid[n + 1][0]);
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

        public Point root() {
            Point p1 = this;
            while (!p1.equal(p1.getParent())) {
                p1 = p1.getParent().getParent();
            }
            return p1;
        }

        public boolean equal(Point p1) {
            return p1.row == row && p1.col == col;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

    private void union(Point p1, Point p2) {
        if (p1 == null || p2 == null)
            throw new IllegalArgumentException();

        Point root1 = p1.root();
        Point root2 = p2.root();

        if (root1.equal(root2)) return;

        if (sz[root1.row][root1.col] < sz[root2.row][root2.col]) {
            root1.setParent(root2);
            sz[root2.row][root2.col] += sz[root1.row][root1.col];
        }
        else {
            root2.setParent(root1);
            sz[root1.row][root1.col] += sz[root2.row][root2.col];
        }
    }

    private boolean isConnected(Point p1, Point p2) {
        if (p1 == null || p2 == null)
            throw new IllegalArgumentException();

        return p1.root().equal(p2.root());
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

            System.out.println("Size of current tree: " + sz[row][col]);
            System.out.println("root of the node: " + grid[row][col].root());
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

    private void showBlockGraph() {
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