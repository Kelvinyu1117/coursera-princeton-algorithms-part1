package assignment3_colinear;

import assignment3_colinear.LineSegment;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] colinearPoints;
    private int nSegments;


    private class Slope {
        private Point p; // start point
        private Point q;
        private Double v;

        public Slope(Point p, Point q) {
            this.p = p;
            this.q = q;

            v = p.slopeTo(q);
        }

        public int compareTo(Slope b) {
            if (v.compareTo(b.v) == 0)
                return q.compareTo(b.q);
            else
                return v.compareTo(b.v);
        }
    }

    private class Node {
        private Node next;
        private Point i;
        private Point f;

        public Node(Point i, Point f) {
            this.i = i;
            this.f = f;
        }
    }

    public FastCollinearPoints(Point[] points) {
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

        for (int i = 0; i < tmp.length; i++) {
            Slope[] arr = new Slope[tmp.length - (i + 1)];
            Slope[] aux = new Slope[tmp.length - (i + 1)];

            for (int k = 0, j = i + 1; j < tmp.length; j++) {
                arr[k++] = new Slope(tmp[i], tmp[j]);
            }

            // sort by slope
            mergeSort(arr, aux, 0, arr.length - 1);

            int e = 0;
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) == 0)
                    e = j;
            }


            int abc = 0;
        }
    }

    private void merge(Slope[] a, Slope[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo;
        int j = mid + 1;
        System.out.println("--------------------");
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[i].compareTo(aux[j]) < 0) a[k] = aux[i++];
            else a[k] = aux[j++];
        }
    }

    private void mergeSort(Slope[] a, Slope[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(a, aux, lo, mid);
        mergeSort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
