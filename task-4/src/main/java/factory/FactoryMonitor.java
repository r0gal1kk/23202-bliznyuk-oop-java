package factory;

import threadpool.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FactoryMonitor implements StorageListener {
    private final Storage<Car> carStorage;
    private final ThreadPool workers;
    private final Map<Class<? extends Product>, Storage<? extends Product>> detailStorages;
    private final Logger logger = LoggerFactory.getLogger(FactoryMonitor.class);

    public FactoryMonitor(Storage<Car> carStorage, ThreadPool workers, Map<Class<? extends Product>, Storage<? extends Product>> detailStorages) {
        this.carStorage = carStorage;
        this.workers = workers;
        this.detailStorages = detailStorages;
    }

    @Override
    public void onDetailAdded(Class<?> detailClass) {
        logger.info("Detail added: {}", detailClass.getSimpleName());
    }

    @Override
    public void onDetailRemoved(Class<?> detailClass) {
        if (detailClass.equals(Car.class)) {
            logger.info("Car removed from storage");
        }
    }
}
