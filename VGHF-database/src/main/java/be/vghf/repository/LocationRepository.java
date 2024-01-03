package be.vghf.repository;

import be.vghf.domain.Game;
import be.vghf.domain.Location;

import java.util.List;

public class LocationRepository implements Repository{
    public LocationRepository(){}

    public List<Location> getAll(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Location.class);
        var root = query.from(Location.class);

        query.select(root);

        return GenericRepository.query(query);
    }
}
