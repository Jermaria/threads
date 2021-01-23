package by.jwd.thread.entity;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.thread.util.IdGenerator;

public class Truck extends Thread {
    
    private static final Logger logger = LogManager.getLogger();
    private static final int DEFAULT_LOADING_TIME = 3;
    private long truckId;
    private boolean isLoaded;
    private GoodsType goodsType;
    
    public Truck() {
        this.truckId = IdGenerator.generateId();
    }
    
    public Truck(GoodsType type) {
        this.truckId = IdGenerator.generateId();
        this.setName(String.valueOf(truckId));
        this.isLoaded = true;
        this.goodsType = type;
        if (type == GoodsType.PERISHABLE) {
            this.setPriority(Thread.MAX_PRIORITY);
        }
    }

    public long getTruckId() {
        return truckId;
    }

    public void setGoodsType(GoodsType type) {
        this.isLoaded = true;
        this.goodsType = type;
        if (type == GoodsType.PERISHABLE) {
            this.setPriority(Thread.MAX_PRIORITY);
        }
    }
    
    private void unload() throws InterruptedException {
        logger.log(Level.INFO, "Truck " + this.truckId + " starts unloading");
        TimeUnit.SECONDS.sleep(DEFAULT_LOADING_TIME);
        this.isLoaded = false;
        goodsType = null;
        logger.log(Level.INFO, "Truck " + this.truckId + " finished unloading");
    }
    
    private void load() throws InterruptedException {
        logger.log(Level.INFO, "Truck " + this.truckId + " starts loading");
        TimeUnit.SECONDS.sleep(DEFAULT_LOADING_TIME);
        this.isLoaded = false;
        goodsType = null;
        logger.log(Level.INFO, "Truck " + this.truckId + " finished loading");
    }


    @Override
    public void run() {
        Stock stock = Stock.getInstance();
        Terminal terminal = stock.occupyTerminal();
        try {
            if (isLoaded) {
                this.unload();
            } else {
                this.load();
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
        }
        stock.releaseTerminal(terminal.getTerminalNumber());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((goodsType == null) ? 0 : goodsType.hashCode());
        result = prime * result + (isLoaded ? 1231 : 1237);
        result = prime * result + (int) (truckId ^ (truckId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Truck other = (Truck) obj;
        if (goodsType != other.goodsType)
            return false;
        if (isLoaded != other.isLoaded)
            return false;
        if (truckId != other.truckId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Truck [truckId=");
        builder.append(truckId);
        builder.append(", isLoaded=");
        builder.append(isLoaded);
        builder.append(", goodsType=");
        builder.append(goodsType);
        builder.append("]");
        return builder.toString();
    }

}
