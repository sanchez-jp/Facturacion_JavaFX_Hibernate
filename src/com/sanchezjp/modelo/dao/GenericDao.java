package com.sanchezjp.modelo.dao;

import java.io.Serializable;
import java.util.List;

public abstract class GenericDao<T, PK extends Serializable>{

    protected int firstRow = 0;
    protected boolean error;
    
    public abstract T get(PK id);
    public abstract T get(T entity);
    public abstract PK add(T entity);    
    public abstract boolean add(List<T> list);
    public abstract boolean update(T entity);

    public abstract boolean delete(T entity);
    public abstract List<T> listAll();
    public abstract List<T> listNext(int rows);
    public abstract boolean exist(T entity);

    public boolean isError(){
        return error;
    }

    public void moveFirst(){
        firstRow = 0;
    }
 }
