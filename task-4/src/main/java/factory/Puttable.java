package factory;

public interface Puttable<T> {
    void put(T item) throws InterruptedException;
}