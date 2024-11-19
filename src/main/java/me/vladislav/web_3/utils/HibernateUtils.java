package me.vladislav.web_3.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    public static Session getSession() {
        SessionFactory sessionFactory = getSessionFactory();
        return sessionFactory.openSession();
    }

    public static SessionFactory getSessionFactory() {
        Configuration configuration = getConfiguration().configure();
        return configuration.buildSessionFactory();
    }

    public static Configuration getConfiguration() {
        return new Configuration();
    }


}
