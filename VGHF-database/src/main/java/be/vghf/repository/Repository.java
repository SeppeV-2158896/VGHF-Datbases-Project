package be.vghf.repository;

import java.util.List;

public interface Repository {
    <T> List<T> getAll();
}
