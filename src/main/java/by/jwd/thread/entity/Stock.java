package by.jwd.thread.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Stock {
    
    private static final Logger logger = LogManager.getLogger();
    private static Lock lock = new ReentrantLock();
    private static Lock lock1 = new ReentrantLock();
    
    private static Stock instance = new Stock();
    private static AtomicBoolean isInitialized = new AtomicBoolean();
    private static final int DEFAULT_TERMINAL_QUANTITY = 2;
    private Set<Terminal> terminals;

    private Stock() {
        this.terminals = new HashSet<>();
        for(int i = 1; i < DEFAULT_TERMINAL_QUANTITY+1; i++) {
           this.terminals.add(new Terminal(i));
        }
    }

    public static Stock getInstance() {
        if (isInitialized.compareAndSet(false, true)) {
            try {
                lock.lock();
                if (isInitialized.compareAndSet(false, true)) {
                    instance = new Stock();
                }
                isInitialized.set(true);
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }
    
    public Terminal occupyTerminal() {
        Terminal terminal;
        try {
            lock1.lock();
            while (!hasFreeTerminal()) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    logger.log(Level.ERROR, e);
                }
            }
            terminal = terminals.stream().filter(term -> term.isOccupied() == false).findFirst().get();
            terminal.setOccupied(true);
        } finally {
            lock1.unlock();
        }
        logger.log(Level.INFO, "Truck " + Thread.currentThread().getName() + " occupies Terminal " + terminal.getTerminalNumber());
        return terminal;
    }
    
    private boolean hasFreeTerminal() {
        boolean hasFree = terminals.stream().anyMatch(t -> t.isOccupied() == false);
        return hasFree;
    }
    
    public void releaseTerminal(int terminalNumber) {
        Iterator<Terminal> iterator = terminals.iterator();
        while (iterator.hasNext()) {
            Terminal terminal = iterator.next();
            if (terminal.getTerminalNumber() == terminalNumber) {
                terminal.setOccupied(false);
                logger.log(Level.INFO, "Terminal " + terminal.getTerminalNumber() + " is ready to serve next truck");
                break;
            }
        }
    }

}
