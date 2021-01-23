package by.jwd.thread.main;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.thread.entity.Truck;
import by.jwd.thread.parser.TruckParser;
import by.jwd.thread.reader.TextReader;
import by.jwd.thread.reader.TextReaderException;

public class Main {
    
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        
        TextReader reader = new TextReader();
        TruckParser parser = new TruckParser();
        
        try {
            List<String> truckInfo = reader.readStringsFromFile("resources/data/trucks.txt");
            List<Truck> trucks = parser.parseTrucks(truckInfo);
            for (Truck truck : trucks) {
                truck.start();
            }
        } catch (TextReaderException e) {
            logger.log(Level.ERROR, e);
        }
        

    }

}
