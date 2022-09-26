package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class,id);
    }

    public List<Order> findAll(){
        return em.createQuery("select o from Order o").getResultList();
    }

    public List<Order> findAll(OrderSearch orderSearch){
        return em.createQuery("select o from Order o").getResultList();
    }
//
//    public List<Order> findAll(String name, OrderStatus orderStatus){
//        String query  = "select o from Order o";
//        if(name==null && orderStatus==null){
//            return em.createQuery(query).getResultList();
//        }
//        else{
//            query+=" where";
//            if(name!=null){
//                query+=" name = :name";
//            }
//            if(orderStatus!=null){
//                query+=" orderStatus = :orderStatus";
//            }
//            return em.createQuery(query).setParameter("name",name).setParameter("orderStatus",orderStatus).getResultList();
//
//        }
//


}
