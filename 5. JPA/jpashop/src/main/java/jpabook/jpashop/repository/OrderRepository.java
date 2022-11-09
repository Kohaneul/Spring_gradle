package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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

    //동적쿼리
    public List<Order> findAllByString(OrderSearch orderSearch) {
//language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    //JPA에서 표준으로 제공하는 동적쿼리(JPA Criteria : jpql을 자바코드로)
    public List<Order> findAllByCriteria(OrderSearch orderSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);
        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if(orderSearch.getOrderStatus()!=null){
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        //회원 이름 검색
        if(StringUtils.hasText(orderSearch.getMemberName())){
            Predicate name = cb.like(m.get("name"),"%"+orderSearch.getMemberName()+"%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }



    public List<Order> findAllWithMemberDelivery() {
        List<Order> resultList = em.createQuery("select o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d", Order.class).getResultList();
        //패치조인 : member, delivery 이 fetch 타입이 lazy 여도 쿼리 조회시 이를 무시하고 한번에 가져옴.
        return resultList;
    }


//    // QueryDSL 로 작성
//    public List<Order> findAllByQueryDsl(OrderSearch orderSearch){
//        QOrder order = QOrder.
//    }

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
