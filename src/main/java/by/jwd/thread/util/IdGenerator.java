package by.jwd.thread.util;

public class IdGenerator {
    
    private static volatile long idCounter = 0;

    public static synchronized long generateId() {
        idCounter = idCounter + 1;
        return idCounter;
    }
}
