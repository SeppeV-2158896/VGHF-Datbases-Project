package be.vghf.repository;

import be.vghf.domain.Console;
import be.vghf.domain.Dev_company;
import be.vghf.domain.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.Predicate;

public class Dev_companyRepository implements Repository{
    public Dev_companyRepository(){}

    public List<Dev_company> getAll(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Dev_company.class);
        var root = query.from(Dev_company.class);

        query.select(root);

        return GenericRepository.query(query);
    }

    public Set<Dev_company> getCompaniesByName(String[] name){
        Set<Dev_company> companies = new HashSet<>();
        for(String str : name){
            var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
            var query = criteriaBuilder.createQuery(Dev_company.class);
            var root = query.from(Dev_company.class);

            query.where(criteriaBuilder.like(root.get("companyName"), "%" + str + "%"));

            List<Dev_company> queryResults = GenericRepository.query(query);

            companies.addAll(queryResults);
        }
        return companies;
    }

    public Set<Dev_company> getCompanyByLocation(String[] locations) {
        Set<Dev_company> companies = new HashSet<>();
        for(String str : locations){
            var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
            var query = criteriaBuilder.createQuery(Dev_company.class);
            var root = query.from(Dev_company.class);

            Predicate[] predicates = new Predicate[5];

            predicates[0] = criteriaBuilder.like(root.get("streetName"),"%" + str + "%");
            predicates[1] = criteriaBuilder.like(root.get("bus"),"%" + str + "%");
            predicates[2] = criteriaBuilder.like(root.get("postalCode"),"%" + str + "%");
            predicates[3] = criteriaBuilder.like(root.get("city"),"%" + str + "%");
            predicates[4] = criteriaBuilder.like(root.get("country"),"%" + str + "%");

            query.where(criteriaBuilder.or(predicates));

            List<Dev_company> queryResults = GenericRepository.query(query);

            companies.addAll(queryResults);
        }
        return companies;
    }
}
