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
import com.sanchezjp.modelo.entities.Clientes;
import java.text.Normalizer;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class ClientesDAO extends GenericDao<Clientes, Integer> {

    private static ClientesDAO clientesDAO;
    
    private ClientesDAO(){
        super();
    }

    public static ClientesDAO getDAO() {
        if (clientesDAO == null) {
            clientesDAO = new ClientesDAO();
        }
        return clientesDAO;
    }

    @Override
    public Clientes get(Integer id) {
        // Antes de una transacción primero hay que abrir una sesión (igual en todos los métodos):
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        // Transacción: (no obligatorio en esta versión)
        Transaction transaction = sesion.beginTransaction();

        Clientes cliente = (Clientes) sesion.get(Clientes.class, id);

        transaction.commit();
        sesion.close();

        return cliente;
    }

    @Override
    public Clientes get(Clientes entity) {
        //Creamos el cliente
        Clientes cliente;

        Session sesion = HibernateUtil.getSessionFactory().openSession();
        //Creamos la transacción.
        Transaction transaccion = sesion.beginTransaction();

        //Preparamos la sentencia sql
        String sql = "From Clientes c "
                + "WHERE c.nif = :nif";
        Query query = sesion.createQuery(sql);
        query.setParameter("nif", entity.getNif());

        List<Clientes> lista = query.list();

        if (!lista.isEmpty()) {
            cliente = lista.get(0);
        } else {
            cliente = null;
        }
        //Cerramos la transacción.
        transaccion.commit();
        //Cerramos la sesión.
        sesion.close();
        //Devolvemos el cliente recogido.
        return cliente;
    }

    @Override
    public Integer add(Clientes entity) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        error = false;
        Integer id;
        try {
            transaction = sesion.beginTransaction();
            id = (Integer) sesion.save(entity);
            transaction.commit();
        } catch (Exception ex) {
            id = null;
            error = true;
            // Si la transacción fuera nula el rollback daría error
            if (transaction != null) {
                transaction.rollback();
            }
        }
        sesion.close();
        return id;
    }

    @Override
    public boolean add(List<Clientes> list) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        error = false;
        try {
            transaction = sesion.beginTransaction();
            list.stream().forEach((entity) -> {
                sesion.save(entity);
            });
            transaction.commit();
        } catch (Exception ex) {
            error = true;
            // Si la transacción fuera nula el rollback daría error
            if (transaction != null) {
                transaction.rollback();
            }
        }
        sesion.close();
        return error;
    }

    @Override
    public boolean update(Clientes entity) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        error = false;
        try {
            transaction = sesion.beginTransaction();     
            sesion.update(entity);
            transaction.commit();
        } catch (Exception ex) {
            error = true;
            // Si la transacción fuera nula el rollback daría error
            if (transaction != null) {
                transaction.rollback();
            }
        }
        sesion.close();
        return error;
    }

    @Override
    public boolean delete(Clientes entity) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        error = false;
        try {
            transaction = sesion.beginTransaction();
            sesion.delete(entity);
            transaction.commit();
        } catch (Exception ex) {
            error = true;
            // Si la transacción fuera nula el rollback daría error
            if (transaction != null) {
                transaction.rollback();
            }
        }
        sesion.close();
        return error;
    }

    @Override
    public List<Clientes> listAll() {
        List<Clientes> lista;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = sesion.beginTransaction();
            // SELECT sobre la clase Clientes (no sobre la tabla clientes)
            // HIBERNATE no trabaja nunca con las tablas, si no con las clases
            String sql = "From Clientes";
            Query query = sesion.createQuery(sql);
            lista = query.list();
            transaction.commit();
        } catch (Exception ex) {
            error = true;
            lista = null;
            // Si la transacción fuera nula el rollback daría error
            if (transaction != null) {
                transaction.rollback();
            }
        }
        sesion.close();
        return lista;
    }

    /**
     * PROBAR!!!---------------------------------------------
     *
     * @param rows
     * @return
     */
    @Override
    public List<Clientes> listNext(int rows) {
        List<Clientes> lista;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = sesion.beginTransaction();
            // SELECT sobre la clase Clientes (no sobre la tabla clientes)
            // HIBERNATE no trabaja nunca con las tablas, si no con las clases
            String sql = "From Clientes";
            Query query = sesion.createQuery(sql);
            query.setMaxResults(rows); // Establece el máximo de tuplas a leer
            lista = query.list();
            transaction.commit();
        } catch (Exception ex) {
            error = true;
            lista = null;
            // Si la transacción fuera nula el rollback daría error
            if (transaction != null) {
                transaction.rollback();
            }
        }
        sesion.close();
        return lista;
    }

    @Override
    public boolean exist(Clientes entity) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.
                createQuery("select 1 from Clientes c where c.nif = :key");
        query.setString("key", entity.getNif());
        return (query.uniqueResult() != null);
    }

    public List<Clientes> listBy(String campo, String valor) {

        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String parametro = Normalizer.normalize(valor, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        Criteria criteria = sesion.createCriteria(Clientes.class);
        List<Clientes> resultado = new ArrayList<>();
        criteria.add(Restrictions.like(campo, parametro+"%").ignoreCase());
        resultado = (List<Clientes>) criteria.list();

        return resultado;
    }
}
