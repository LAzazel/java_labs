package lab9;

public final class BlockingCircularBuffer<T> {
    private static final class Node<T> {
        private T value;
        private Node<T> next;
    }

    private final Node<T> first;
    private Node<T> head;
    private Node<T> tail;
    private final int capacity;
    private int size;

    public BlockingCircularBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Розмір буфера має бути більшим за нуль.");
        }
        this.capacity = capacity;
        first = new Node<>();
        Node<T> current = first;
        for (int i = 1; i < capacity; i++) {
            current.next = new Node<>();
            current = current.next;
        }
        current.next = first;
        head = first;
        tail = first;
    }

    public synchronized void put(T value) throws InterruptedException {
        while (size == capacity) {
            wait();
        }
        tail.value = value;
        tail = tail.next;
        size++;
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (size == 0) {
            wait();
        }
        T value = head.value;
        head.value = null;
        head = head.next;
        size--;
        notifyAll();
        return value;
    }

    public synchronized int size() {
        return size;
    }
}
