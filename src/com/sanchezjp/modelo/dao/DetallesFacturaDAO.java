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
import com.sanchezjp.modelo.entities.DetallesFactura;

/**
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class DetallesFacturaDAO extends GenericDao<DetallesFactura, Integer> {
    
    @Override
    public DetallesFactura get(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        DetallesFactura articulo = (DetallesFactura) session.get(DetallesFactura.class, id);

        transaction.commit();
        session.close();

        return articulo;
    }

    @Override
    public DetallesFactura get(DetallesFactura entity) {
        DetallesFactura detallesFactura;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "From DetallesFactura df"
                + "WHERE df.DetallesFacturaId = :id ";
        Query query = session.createQuery(sql);
        query.setParameter("id", entity.getId());
        List<DetallesFactura> list = query.list();
        if (!list.isEmpty()) {
            detallesFactura = list.get(0);
        } else {
            detallesFactura = null;
        }
        transaction.commit();
        session.close();
        return detallesFactura;
    }

    @Override
    public Integer add(DetallesFactura entity) {
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
    public boolean add(List<DetallesFactura> list) {
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
    public boolean update(DetallesFactura entity) {
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
    public boolean delete(DetallesFactura entity) {
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
    public List<DetallesFactura> listAll() {
        List<DetallesFactura> lista;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = sesion.beginTransaction();
            String sql = "From DetallesFactura";
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
    public List<DetallesFactura> listNext(int rows) {
        List<DetallesFactura> lista;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = sesion.beginTransaction();
            String sql = "From DetallesFactura";
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
    public boolean exist(DetallesFactura entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
