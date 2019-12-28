package assignment1_percolation;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;


public class PercolationStats {
    private double[] samples;
    private int numOfTrials;
    private int gridSize;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        gridSize = n;
        numOfTrials = trials;

        Percolation p = new Percolation(gridSize);
        samples = new double[numOfTrials];

        for (int i = 0; i < numOfTrials; i++) {
            while (!p.percolates()) {
                int r = StdRandom.uniform(n) + 1;
                int c = StdRandom.uniform(n) + 1;
                p.open(r, c);
            }

            double m = p.numberOfOpenSites();
            samples[i] = m / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(samples);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(samples);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(numOfTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(numOfTrials);
    }

    public String toString() {
        return "mean                    = " + mean() + "\n" +
                "stddev                  = " + stddev() + "\n" +
                "95% confidence interval = [" + confidenceLo() + ", " + confidenceHi() + "]";
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(200, 100);
        System.out.println(p.toString());
    }

}
