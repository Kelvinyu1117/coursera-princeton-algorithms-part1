package assignment2_queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private int capacity;
    private Item[] arr;
    // construct an empty randomized queue
    public RandomizedQueue() {

    }


    private class RandomizedQueueIterator implements Iterator<Item> {

        public boolean hasNext() {
        }

        public Item next() {

        }

        public void remove() {
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
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
    }

    private void resize() {
        Item[] t = (Item[]) new Object[capacity * 2];

        for (int i = 0; i < capacity; i++)
            t[i] = arr[i];

        arr = null;
        arr = t;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
