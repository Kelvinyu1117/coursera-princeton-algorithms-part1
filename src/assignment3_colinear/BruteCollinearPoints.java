package assignment3_colinear;

public class BruteCollinearPoints {
    private LineSegment[] colinearPoints;
    private int nSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        nSegments = 0;

    }

    public int numberOfSegments() {
        return nSegments;
    }     // the number of line segments

    public LineSegment[] segments() {
        return colinearPoints;
    }
}
