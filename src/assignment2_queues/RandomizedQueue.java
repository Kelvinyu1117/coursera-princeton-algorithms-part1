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
        head = 0;
        tail = 0;
        capacity = 2;
        data = (Item[]) new Object[capacity];
    }


    private class RandomizedQueueIterator implements Iterator<Item> {

        private int tHead = head;
        private int tSize = size;
        private Item[] tData;

        public RandomizedQueueIterator() {
            tData = (Item[]) new Object[capacity];
            System.arraycopy(data, 0, tData, 0, capacity);
        }

        public boolean hasNext() {
            return tSize != 0;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            swap(tData, tHead, StdRandom.uniform(tHead, tail));

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

        if (tail == capacity)
            resize(capacity * 2);

        data[tail] = item;

        tail++;
        size++;
    }

    private void swap(Item[] tData, int i, int j) {
        Item t = tData[i];
        tData[i] = tData[j];
        tData[j] = t;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        if (size == capacity / 4)
            resize(capacity / 2);

        swap(data, head, StdRandom.uniform(head, tail));

        Item r = data[head];

        data[head] = null;
        head++;
        size--;


        return r;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        swap(data, head, StdRandom.uniform(head, tail));

        return data[head];
    }

    private void resize(int cap) {
        Item[] t = (Item[]) new Object[cap];
        int j = 0;
        for (int i = head; i < tail; i++) {
            t[j] = data[i];
            j++;
        }

        head = 0;
        tail = j;
        capacity = cap;
        data = null;
        data = t;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void print() {
        int c = 0;
        for (Item item : this) {
            System.out.print(item + " ");
            c++;
            if (c % 10 == 0)
                System.out.println();
        }

        System.out.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();


        for (int i = 0; i < 8; i++)
            q.enqueue("a");

        for (int i = 0; i < 6; i++)
            q.dequeue();

        q.dequeue();
    }

}
