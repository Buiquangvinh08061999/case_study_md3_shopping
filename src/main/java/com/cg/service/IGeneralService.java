package com.cg.service;
import com.cg.model.User;

import java.util.List;

public interface IGeneralService<T> {
    List<T> findAll();

    void deleteId(String id);

    boolean create(T t);

    T findById(int userId);

    boolean update(T t );



}
