package factory;

public abstract class Product {
    private final String ID;
    public Product(String ID) {
        this.ID = ID;
    }
    public String getID() {
        return ID;
    }
}