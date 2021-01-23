package by.jwd.thread.parser;

import java.util.ArrayList;
import java.util.List;

import by.jwd.thread.entity.GoodsType;
import by.jwd.thread.entity.Truck;

public class TruckParser {
    
    public List<Truck> parseTrucks(List<String> truckInfo) {
        List<Truck> trucks = new ArrayList<>();
        for(String truck : truckInfo) {
            GoodsType type = GoodsType.valueOf(truck); 
            trucks.add(new Truck(type)); 
        }
        return trucks;
    }
}
