package assignment1_percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private final double[] samples;
    private final int numOfTrials;
    private final double resMean;
    private final double resStddev;
    private final double resConHi;
    private final double resConLo;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        numOfTrials = trials;


        samples = new double[numOfTrials];

        for (int i = 0; i < numOfTrials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int r = StdRandom.uniform(n) + 1;
                int c = StdRandom.uniform(n) + 1;
                p.open(r, c);
            }

            double m = p.numberOfOpenSites();
            samples[i] = m / (n * n);
        }

        resMean = StdStats.mean(samples);
        resStddev = StdStats.stddev(samples);
        resConHi = resMean - 1.96 * resStddev / Math.sqrt(numOfTrials);
        resConLo = resMean + 1.96 * resStddev / Math.sqrt(numOfTrials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return resMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return resStddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return resConHi;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return resConLo;
    }

    public String toString() {
        return "mean                    = " + mean() + "\n" +
                "stddev                  = " + stddev() + "\n" +
                "95% confidence interval = [" + confidenceLo() + ", " + confidenceHi() + "]";
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        StdOut.print(ps.toString());
    }

}