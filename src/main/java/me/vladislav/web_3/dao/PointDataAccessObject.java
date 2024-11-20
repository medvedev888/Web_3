package me.vladislav.web_3.dao;

import me.vladislav.web_3.models.Point;
import me.vladislav.web_3.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class PointDataAccessObject implements DataAccessObject<Point> {

    @Override
    public void updatePoint(Point point) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.merge(point);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Optional<List<Point>> getList() {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            List<Point> listOfPoints = session.createQuery("FROM Point p ORDER BY p.id DESC", Point.class).getResultList();
            session.getTransaction().commit();
            return Optional.ofNullable(listOfPoints);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void save(Point point) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.save(point);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
