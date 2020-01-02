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

        private int curr = head;

        public boolean hasNext() {
            return curr != tail;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item r = data[curr];

            curr = (curr + 1) % capacity;
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

        if(size == capacity)
            resize();

        data[tail] = item;

        if(head != tail)
            swap(tail, StdRandom.uniform(head, tail));

        tail = (tail + 1) % capacity;
        size++;
    }

    private void swap(int i, int j) {
        Item t = data[i];
        data[i] = data[j];
        data[j] = t;
    }
    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();

        Item r = data[head];
        head = (head + 1) % capacity;

        size--;

        return r;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();

        return data[StdRandom.uniform(size)];
    }

    private void resize() {
        capacity *= 2;
        Item[] t = (Item[]) new Object[capacity];

        for (int i = 0, j = head; i < size; i++) {
            t[i] = data[j];
            j++;
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
        RandomizedQueue<Integer> q = new RandomizedQueue<>();

       for(int i = 0; i < 50; i++)
           q.enqueue(i);

        q.print();

        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.print();
    }

}
