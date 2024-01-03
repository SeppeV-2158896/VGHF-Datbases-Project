package be.vghf.repository;

import be.vghf.domain.Game;
import be.vghf.domain.Location;

import java.util.List;

public class LocationRepository {
    public LocationRepository(){}

    public List<Location> getAllLocations(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Location.class);
        var root = query.from(Location.class);

        query.select(root);

        return Repository.query(query);
    }
}
