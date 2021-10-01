import java.util.ArrayList;

public class Queue<E> {

    private ArrayList<E> queue;

    public Queue() {
        this.queue = new ArrayList<E>();
    }

    public void add(E e) {
        queue.add(e);
    }

    public E pop() {
        return queue.remove(0);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }


}
