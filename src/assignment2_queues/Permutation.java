package assignment2_queues;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import assignment2_queues.RandomizedQueue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            q.enqueue(value);
        }


        for (int i = 0; i < k; i++)
            StdOut.print(q.dequeue() + " ");
    }
}