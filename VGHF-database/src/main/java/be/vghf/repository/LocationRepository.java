package be.vghf.repository;

import be.vghf.domain.Console;
import be.vghf.domain.Game;
import be.vghf.domain.Location;
import be.vghf.enums.LocationType;

import java.util.ArrayList;
import java.util.List;

public class LocationRepository implements Repository{
    public LocationRepository(){}

    public List<Location> getAll(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Location.class);
        var root = query.from(Location.class);

        query.select(root);

        return GenericRepository.query(query);
    }

    public List<Location> getLocationByStreet(String[] street){
        ArrayList<Location> locations = new ArrayList<>();
        for(String str : street){
            var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
            var query = criteriaBuilder.createQuery(Location.class);
            var root = query.from(Location.class);

            query.where(criteriaBuilder.like(root.get("streetName"), "%" + str + "%"));

            List<Location> queryResults = GenericRepository.query(query);

            for(Location location : queryResults){
                if(!locations.contains(location)){
                    locations.add(location);
                }
            }
        }
        return locations;
    }

}
