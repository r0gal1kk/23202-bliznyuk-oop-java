package tasks;

import factory.*;
import threadpool.Task;

import java.util.Map;

public class CreateCar implements Task {
    private final Map<Class<? extends Product>, Storage<? extends Product>> detailStorages;
    private final Puttable<Car> carStorage;

    public CreateCar(Map<Class<? extends Product>, Storage<? extends Product>> detailStorages) {
        this.detailStorages = detailStorages;
        this.carStorage = (Storage<Car>) detailStorages.get(Car.class);
    }
    @Override
    public void execute() throws InterruptedException {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Body body = (Body) detailStorages.get(Body.class).get();
                Engine motor = (Engine) detailStorages.get(Engine.class).get();
                Accessory accessory = (Accessory) detailStorages.get(Accessory.class).get();
                Car car = new Car(IdGenerator.generateId(Car.class), body, motor, accessory);
                carStorage.put(car);
            }
        } catch (InterruptedException e){
            throw e;
        }
    }
    @Override
    public String getTaskName(){
        return "Worker " + this + " assembled car: ";
    }
    @Override
    public void setParameters(int parameter){
    }
}