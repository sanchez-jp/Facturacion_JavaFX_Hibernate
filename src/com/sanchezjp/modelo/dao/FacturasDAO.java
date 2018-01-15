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
import com.sanchezjp.modelo.entities.Facturas;

/**
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class FacturasDAO extends GenericDao<Facturas, Integer> {

    @Override
    public Facturas get(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Facturas articulo = (Facturas) session.get(Facturas.class, id);

        transaction.commit();
        session.close();

        return articulo;
    }

    @Override
    public Facturas get(Facturas entity) {
        Facturas factura;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "From Facturas f"
                + "WHERE YEAR(f.fechaFactura) = :year "
                + "AND numFactura = :numFactura";
        Query query = session.createQuery(sql);
        query.setParameter("year", entity.getAnioFactura());
        query.setParameter("numFactura", entity.getNumFactura());
        List<Facturas> list = query.list();
        if (!list.isEmpty()) {
            factura = list.get(0);
        } else {
            factura = null;
        }
        transaction.commit();
        session.close();
        return factura;
    }

    @Override
    public Integer add(Facturas entity) {
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
    public boolean add(List<Facturas> list) {
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
    public boolean update(Facturas entity) {
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
    public boolean delete(Facturas entity) {
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
    public List<Facturas> listAll() {
        List<Facturas> lista;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = sesion.beginTransaction();
            String sql = "From Facturas";
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
    public List<Facturas> listNext(int rows) {
        List<Facturas> lista;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = sesion.beginTransaction();
            String sql = "From Facturas";
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
    public boolean exist(Facturas entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
