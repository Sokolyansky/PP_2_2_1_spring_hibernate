package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }


   @Override
   @SuppressWarnings("unchecked")
   public User getUser(String model, int series) {
      Query query = sessionFactory.getCurrentSession()
              .createQuery("from User as u where u.car.model = :model and u.car.series = :series")
              .setParameter("model", model)
              .setParameter("series", series);
      try {
         return (User) query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }
}
