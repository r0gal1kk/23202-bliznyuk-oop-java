package factory;

public interface Gettable<T> {
    T get() throws InterruptedException;
}
