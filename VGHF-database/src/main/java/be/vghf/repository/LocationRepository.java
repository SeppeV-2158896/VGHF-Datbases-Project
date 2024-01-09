package be.vghf.repository;

import be.vghf.domain.Console;
import be.vghf.domain.Game;
import be.vghf.domain.Location;
import be.vghf.domain.User;
import be.vghf.enums.LocationType;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public List<Location> getLocationByAddress(String[] addresses){
        ArrayList<Location> locations = new ArrayList<>();
        for(String str : addresses){
            var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
            var query = criteriaBuilder.createQuery(Location.class);
            var root = query.from(Location.class);

            Predicate[] predicates = new Predicate[4];

            predicates[0] = criteriaBuilder.like(root.get("streetName"),"%" + str + "%");
            predicates[1] = criteriaBuilder.like(root.get("postalCode"),"%" + str + "%");
            predicates[2] = criteriaBuilder.like(root.get("city"),"%" + str + "%");
            predicates[3] = criteriaBuilder.like(root.get("country"),"%" + str + "%");

            query.where(criteriaBuilder.or(predicates));

            Set<Location> queryResults = Set.copyOf(GenericRepository.query(query));
            locations.addAll(queryResults);
        }
        return locations;
    }

}
