package assignment3_colinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final LineSegment[] colinearPoints;
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
            return v.compareTo(b.v);
        }

        public int sameSlope(Slope b) {
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

        Node head = null;
        for (int i = 0; i < tmp.length; i++) {
            Slope[] arr = new Slope[tmp.length - 1];
            Slope[] aux = new Slope[tmp.length - 1];

            for (int k = 0, j = 0; j < tmp.length; j++) {
                if (j != i)
                    arr[k++] = new Slope(tmp[i], tmp[j]);
            }

            // sort by slope
            mergeSort(arr, aux, 0, arr.length - 1);

            int s = 0;
            int e = 0;
            int f = 0;
            int cnt = 0;
            while (f < arr.length) {
                int g = f + 1;

                while (g < arr.length && arr[f].sameSlope(arr[g]) == 0)
                    g++;

                if (g - f >= 3) {
                    s = f;
                    e = g;
                    if (arr[s].q.compareTo(arr[s].p) < 0)
                        head = insert(head, arr[s].q, arr[e - 1].q);
                    else
                        head = insert(head, arr[s].p, arr[e - 1].q);
                }

                f = g;
            }


        }

        colinearPoints = new LineSegment[nSegments];

        int i = 0;
        while (head != null) {
            colinearPoints[i++] = new LineSegment(head.i, head.f);
            head = head.next;
        }
    }

    private void merge(Slope[] a, Slope[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo;
        int j = mid + 1;

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

            while (it1.next != null) {
                if (it1.f.compareTo(f) == 0 || it1.f.compareTo(i) == 0)
                    return head;
                else if (it1.i.compareTo(f) == 0 || it1.i.compareTo(i) == 0)
                    return head;

                it1 = it1.next;
            }

            if (it1.f.compareTo(f) == 0 || it1.f.compareTo(i) == 0)
                return head;
            else if (it1.i.compareTo(f) == 0 || it1.i.compareTo(i) == 0)
                return head;

            it1.next = new Node(i, f);
            nSegments++;

            return head;
        }
    }


    public int numberOfSegments() {
        return nSegments;
    }     // the number of line segments

    public LineSegment[] segments() {
        return colinearPoints.clone();
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
