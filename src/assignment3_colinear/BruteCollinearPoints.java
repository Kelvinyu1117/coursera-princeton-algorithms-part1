package assignment3_colinear;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] colinearPoints;
    private int nSegments;

    private class Node {
        private Node next;
        private Point i;
        private Point f;

        public Node(Point i, Point f) {
            this.i = i;
            this.f = f;
        }
    }


    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        Point[] tmp = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            else {
                if (i == 0) {
                    tmp[i] = points[i];
                }
                else {
                    int j = i - 1;
                    while (j >= 0 && points[i].compareTo(tmp[j]) < 0) {
                        tmp[j + 1] = tmp[j];
                        j--;
                    }
                    if (j >= 0 && points[i].compareTo(tmp[j]) == 0)
                        throw new IllegalArgumentException();
                    else
                        tmp[j + 1] = points[i];
                }
            }

        }

        nSegments = 0;

        Node head = null;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = i + 1; j < tmp.length; j++) {
                for (int k = j + 1; k < tmp.length; k++) {
                    for (int f = k + 1; f < tmp.length; f++) {
                        Double m1 = tmp[i].slopeTo(tmp[j]);
                        Double m2 = tmp[i].slopeTo(tmp[k]);
                        Double m3 = tmp[i].slopeTo(tmp[f]);

                        if (m1.compareTo(m2) == 0 && m1.compareTo(m3) == 0) {
                            head = insert(head, tmp[i], tmp[f]);
                        }
                    }
                }
            }
        }

        colinearPoints = new LineSegment[nSegments];
        int i = 0;
        while (head != null) {
            colinearPoints[i++] = new LineSegment(head.i, head.f);
            head = head.next;
        }
    }

    private Node insert(Node head, Point i, Point f) {
        if (head == null) {
            nSegments++;
            return new Node(i, f);
        }
        else {
            Node it1 = head;

            while (it1.next != null)
                it1 = it1.next;


            it1.next = new Node(i, f);
            nSegments++;

            return head;
        }
    }

    public int numberOfSegments() {
        return nSegments;
    }     // the number of line segments

    public LineSegment[] segments() {
        return colinearPoints;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
