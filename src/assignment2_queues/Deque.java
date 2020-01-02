package assignment2_queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    private class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T data;

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item data = current.getData();
            current = current.getNext();

            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty deque
    public Deque() {
        head = new Node<Item>();
        tail = new Node<Item>();

        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> p = new Node<Item>();
        p.setData(item);

        if (size == 0) {
            p.setPrev(null);
            p.setNext(null);

            head = p;
            tail = p;
        }
        else {
            head.setPrev(p);
            p.setNext(head);
            head = p;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> p = new Node<Item>();
        p.setData(item);

        if (size == 0) {
            p.setPrev(null);
            p.setNext(null);

            head = p;
            tail = p;
        }
        else {
            p.setPrev(tail);
            tail.setNext(p);
            tail = p;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException();

        Node<Item> t = head;
        Item r = t.getData();

        head = head.getNext();

        if (head != null)
            head.setPrev(null);

        t = null;
        size--;
        return r;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException();

        Node<Item> t = tail;
        Item r = t.getData();

        tail = tail.getPrev();

        if (tail != null)
            tail.setNext(null);

        t = null;
        size--;
        return r;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void print() {
        for (Item item : this) {
            System.out.print(item + " ");
        }

        System.out.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(1);
        deque.print();
        deque.addFirst(2);
        deque.print();
        deque.addFirst(3);
        deque.print();
        deque.addLast(4);
        deque.print();
        deque.addLast(5);
        deque.print();
        deque.removeFirst();
        deque.print();

    }

}