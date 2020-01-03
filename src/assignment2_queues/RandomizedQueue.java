package assignment2_queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private int capacity;
    private int head;
    private int tail;
    private Item[] data;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        head = -1;
        tail = -1;
        capacity = 2;
        data = (Item[]) new Object[capacity];
    }


    private class RandomizedQueueIterator implements Iterator<Item> {

        private int tHead = head;
        private int tSize = size;
        private Item[] tData;

        public RandomizedQueueIterator() {

            int tmp = size;

            tData = (Item[]) new Object[capacity];

            for (int i = 0, j = head; i < size; i++) {
                tData[i] = data[j];
                j = (j + 1) % size;
            }

            tHead = 0;

            int i = head;

            while (tmp != 0) {
                int r = StdRandom.uniform(head, i + 1);
                swap(tData, i, r);
                i = (i + 1) % capacity;
                tmp--;
            }


        }

        public boolean hasNext() {
            return tSize != 0;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item r = tData[tHead];

            tHead++;
            tSize--;

            return r;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (size == capacity)
            resize(capacity * 2);

        if (size == 0) {
            head = 0;
            tail = 0;
            data[head] = item;
        }
        else {
            data[tail] = item;

            if (head > tail)
                swap(data, tail, StdRandom.uniform(tail, head));
            else if (tail > head)
                swap(data, tail, StdRandom.uniform(head, tail));

            tail = (tail + 1) % capacity;
        }
        size++;
    }

    private void swap(Item[] tData, int i, int j) {
        Item t = tData[i];
        tData[i] = tData[j];
        tData[j] = t;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();

        if (size == capacity / 4)
            resize(capacity / 2);

        Item r = data[head];
        data[head] = null;

        head = (head + 1) % capacity;

        size--;

        return r;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();

        Item r = null;

        while (r == null)
            r = data[StdRandom.uniform(size)];

        return r;

    }

    private void resize(int cap) {
        int oldCap = capacity;
        capacity = cap;
        Item[] t = (Item[]) new Object[capacity];

        for (int i = 0, j = head; i < size; i++) {
            t[i] = data[j];
            j = (j + 1) % oldCap;
        }

        head = 0;
        tail = size;

        data = null;
        data = t;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void print() {
        for (Item item : this) {
            System.out.print(item + " ");
        }

        System.out.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        q.enqueue("AHZKNUEESD");
        q.enqueue("RNFOQAWZXO");
        q.dequeue();
        q.enqueue("UREGFNDRGE");
        q.sample();
        q.enqueue("QHRTSRPZWT");

    }

}
