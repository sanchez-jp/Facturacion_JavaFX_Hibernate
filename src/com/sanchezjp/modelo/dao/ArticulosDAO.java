/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.modelo.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.sanchezjp.modelo.entities.Articulos;
import com.sanchezjp.modelo.entities.Clientes;
import java.text.Normalizer;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class ArticulosDAO extends GenericDao<Articulos, Integer> {

    private static ArticulosDAO articulosDAO;
    
    private ArticulosDAO(){
        super();
    }

    public static ArticulosDAO getDAO() {
        if (articulosDAO == null) {
            articulosDAO = new ArticulosDAO();
        }
        return articulosDAO;
    }
    
    @Override
    public Articulos get(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Articulos articulo = (Articulos) session.get(Articulos.class, id);

        transaction.commit();
        session.close();

        return articulo;
    }

    @Override
    public Articulos get(Articulos entity) {
        Articulos articulo;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "From Articulos a"
                + "WHERE a.codArticulo = :codArticulo";
        Query query = session.createQuery(sql);
        query.setParameter("codArticulo", entity.getCodArticulo());
        List<Articulos> list = query.list();
        if (!list.isEmpty()) {
            articulo = list.get(0);
        } else {
            articulo = null;
        }
        transaction.commit();
        session.close();
        return articulo;
    }

    @Override
    public Integer add(Articulos entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Integer id;
        error = false;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            id = null;
            error = true;
            if (transaction != null) {
                transaction.rollback();
            }
        }
        session.close();
        return id;
    }

    @Override
    public boolean add(List<Articulos> list) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        error = false;
        try {
            transaction = session.beginTransaction();
            list.stream().forEach((entity) -> {
                session.save(entity);
            });
            transaction.commit();
        } catch (Exception e) {
            error = true;
            if (transaction != null) {
                transaction.rollback();
            }
        }
        session.close();
        return error;
    }

    @Override
    public boolean update(Articulos entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        error = false;
        try {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception ex) {
            error = true;
            if (transaction != null) {
                transaction.rollback();
            }
        }
        session.close();
        return error;
    }

    @Override
    public boolean delete(Articulos entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        error = false;
        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception ex) {
            error = true;
            if (transaction != null) {
                transaction.rollback();
            }
        }
        session.close();
        return error;
    }

    @Override
    public List<Articulos> listAll() {
        List<Articulos> lista;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = sesion.beginTransaction();
            String sql = "From Articulos";
            Query query = sesion.createQuery(sql);
            lista = query.list();
            transaction.commit();
        } catch (Exception ex) {
            error = true;
            lista = null;
            if (transaction != null) {
                transaction.rollback();
            }
        }
        sesion.close();
        return lista;
    }

    @Override
    public List<Articulos> listNext(int rows) {
        List<Articulos> lista;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = sesion.beginTransaction();
            String sql = "From Articulos";
            Query query = sesion.createQuery(sql);
            query.setMaxResults(rows); // Establece el máximo de tuplas a leer
            lista = query.list();
            transaction.commit();
        } catch (Exception ex) {
            error = true;
            lista = null;
            if (transaction != null) {
                transaction.rollback();
            }
        }
        sesion.close();
        return lista;
    }

    @Override
    public boolean exist(Articulos entity) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.
                createQuery("select 1 from Articulos a where a.codArticulo = :key");
        query.setString("key", entity.getCodArticulo());
        return (query.uniqueResult() != null);
    }
    
    public List<Articulos> listBy(String campo, String valor) {

        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String parametro = Normalizer.normalize(valor, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        Criteria criteria = sesion.createCriteria(Articulos.class);
        List<Articulos> resultado = new ArrayList<>();
        criteria.add(Restrictions.like(campo, parametro+"%").ignoreCase());
        resultado = (List<Articulos>) criteria.list();

        return resultado;
    }

}
