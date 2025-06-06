package factory;

import threadpool.Task;
import threadpool.ThreadPool;
import gui.FactoryGUI;
import javax.swing.Timer;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tasks.*;

public class Factory {
    private static boolean logSale;
    private final Map<Class<? extends Product>, Storage<? extends Product>> detailStorages;
    private final int suppliersDelay = 3000;
    private FactoryMonitor factoryMonitor;
    private int workersNum;
    private int dealersNum;
    private int accessorySuppliersNum;
    private int bodySuppliersNum;
    private int engineSuppliersNum;
    private Logger logger = LoggerFactory.getLogger(Factory.class.getName());
    private Properties config;

    private ThreadPool workerThreadPool;
    private ThreadPool supplierThreadPool;
    private ThreadPool dealerThreadPool;

    private Task supplyAccessories;
    private Task supplyBodies;
    private Task supplyEngines;
    private Task orderBuild;
    private Task orderSell;

    public Factory() {
        this.config = new Properties();
        config = ConfigHandler.readConfigFile();
        this.logSale = Boolean.parseBoolean(config.getProperty("LogSale"));
        if (logSale) {
            logger.info("Logging initialized");
        }
        this.detailStorages = new HashMap<>();
        initializeProduction();
        initializeStorages();
    }

    private void initializeStorages() {
        detailStorages.put(Body.class, new Storage<>(
                Integer.parseInt(config.getProperty("StorageBodySize")), factoryMonitor));
        detailStorages.put(Engine.class, new Storage<>(
                Integer.parseInt(config.getProperty("StorageMotorSize")), factoryMonitor));
        detailStorages.put(Accessory.class, new Storage<>(
                Integer.parseInt(config.getProperty("StorageAccessorySize")), factoryMonitor));
        detailStorages.put(Car.class, new Storage<>(
                Integer.parseInt(config.getProperty("StorageAutoSize")), factoryMonitor));
    }

    private void initializeProduction() {
        workersNum = Integer.parseInt(config.getProperty("Workers"));
        dealersNum = Integer.parseInt(config.getProperty("Dealers"));
        accessorySuppliersNum = Integer.parseInt(config.getProperty("AccessorySuppliers"));
        bodySuppliersNum = Integer.parseInt(config.getProperty("BodySuppliers"));
        engineSuppliersNum = Integer.parseInt(config.getProperty("MotorSuppliers"));
        int suppliersNum = accessorySuppliersNum + bodySuppliersNum + engineSuppliersNum;
        supplierThreadPool = new ThreadPool("Suppliers", suppliersNum);
        workerThreadPool = new ThreadPool("Workers", workersNum);
        dealerThreadPool = new ThreadPool("Dealers", dealersNum);

        this.factoryMonitor = new FactoryMonitor(
                (Storage<Car>) detailStorages.get(Car.class),
                workerThreadPool, detailStorages);

        logger.info("Production successfully initialized and ready to perform");
    }

    public void start() {
        logger.info("Production has been started");

        Storage<Car> carStorage = (Storage<Car>) detailStorages.get(Car.class);

        Storage<Engine> motorDetailStorage = (Storage<Engine>) detailStorages.get(Engine.class);
        Storage<Body> bodyDetailStorage = (Storage<Body>) detailStorages.get(Body.class);
        Storage<Accessory> accessoryDetailStorage = (Storage<Accessory>) detailStorages.get(Accessory.class);

        int accessorySuppliersDelay, bodySuppliersDelay, motorSuppliersDelay;
        accessorySuppliersDelay = bodySuppliersDelay = motorSuppliersDelay = suppliersDelay;
        int dealerDelay = 3000;

        supplyAccessories = new Supply<>(Accessory.class, accessoryDetailStorage, accessorySuppliersDelay);
        supplyBodies = new Supply<>(Body.class, bodyDetailStorage, bodySuppliersDelay);
        supplyEngines = new Supply<>(Engine.class, motorDetailStorage, motorSuppliersDelay);

        orderBuild = new CreateCar(detailStorages);
        orderSell = new SellCar(carStorage, dealerDelay);

        Thread production = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (carStorage.size() < carStorage.getCapacity()) {
                    supplierThreadPool.addTask(supplyAccessories);
                    supplierThreadPool.addTask(supplyBodies);
                    supplierThreadPool.addTask(supplyEngines);
                    workerThreadPool.addTask(orderBuild);
                }
                dealerThreadPool.addTask(orderSell);
            }
        });

        production.start();

        FactoryGUI gui = new FactoryGUI(
                supplyBodies, supplyEngines, supplyAccessories, orderSell,
                bodyDetailStorage.getCapacity(), motorDetailStorage.getCapacity(),
                accessoryDetailStorage.getCapacity(), carStorage.getCapacity(),
                bodySuppliersDelay, motorSuppliersDelay, accessorySuppliersDelay, dealerDelay
        );
        gui.setVisible(true);

        Timer timer = new Timer(1000, e -> {
            int total_sold_cars = ((SellCar) orderSell).getSoldCarsNum();
            gui.updateStats(
                    bodyDetailStorage.size(),
                    motorDetailStorage.size(),
                    accessoryDetailStorage.size(),
                    carStorage.size(),
                    total_sold_cars
            );
        });
        timer.start();
    }
}