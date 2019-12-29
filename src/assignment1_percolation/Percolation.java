package assignment1_percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;
    private final int gridSize;
    private final int dim;
    private boolean[][] tGrid;
    private int numOfOpenSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        uf1 = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 2);

        tGrid = new boolean[n][n];
        dim = n;
        gridSize = n * n;
        numOfOpenSite = 0;

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > dim || col < 1 || col > dim)
            throw new IllegalArgumentException();

        if (!isOpen(row, col)) {
            tGrid[row - 1][col - 1] = true;
            numOfOpenSite++;

            if (row == 1) {
                uf1.union(map2DTo1D(row, col), 0);
                uf2.union(map2DTo1D(row, col), 0);
            }

            if (row == dim) {
                uf1.union(map2DTo1D(row, col), gridSize + 1);
            }

            if (row - 1 > 0 && tGrid[row - 1 - 1][col - 1]) {
                uf1.union(map2DTo1D(row, col), map2DTo1D(row - 1, col));
                uf2.union(map2DTo1D(row, col), map2DTo1D(row - 1, col));
            }

            if (row + 1 <= dim && tGrid[row + 1 - 1][col - 1]) {
                uf1.union(map2DTo1D(row, col), map2DTo1D(row + 1, col));
                uf2.union(map2DTo1D(row, col), map2DTo1D(row + 1, col));
            }

            if (col - 1 > 0 && tGrid[row - 1][col - 1 - 1]) {
                uf1.union(map2DTo1D(row, col), map2DTo1D(row, col - 1));
                uf2.union(map2DTo1D(row, col), map2DTo1D(row, col - 1));
            }


            if (col + 1 <= dim && tGrid[row - 1][col + 1 - 1]) {
                uf1.union(map2DTo1D(row, col), map2DTo1D(row, col + 1));
                uf2.union(map2DTo1D(row, col), map2DTo1D(row, col + 1));
            }

        }
    }

    private int map2DTo1D(int row, int col) {
        return (row - 1) * dim + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > dim || col < 1 || col > dim)
            throw new IllegalArgumentException();

        return tGrid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > dim || col < 1 || col > dim)
            throw new IllegalArgumentException();

        return uf2.connected(0, map2DTo1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf1.connected(0, gridSize + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}