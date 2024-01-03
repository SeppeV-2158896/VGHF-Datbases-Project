package be.vghf.repository;

import be.vghf.domain.Dev_company;
import be.vghf.domain.Game;

import java.util.List;

public class Dev_companyRepository {
    public Dev_companyRepository(){}

    public List<Dev_company> getAllCompanies(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Dev_company.class);
        var root = query.from(Dev_company.class);

        query.select(root);

        return GenericRepository.query(query);
    }
}
