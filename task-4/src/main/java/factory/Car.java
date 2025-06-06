package factory;

public class Car extends Product {
    private Body body;
    private Accessory accessory;
    private Engine engine;
    public Car(String ID, Body body, Engine motor, Accessory accessory){
        super(ID);
        this.accessory = accessory;
        this.body = body;
        this.engine = motor;
    }
}