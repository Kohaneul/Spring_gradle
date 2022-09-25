package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        // id 값이 없으면(새로 생성하는 객체)
        if(item.getId()==null){
            em.persist(item);
        }
        else{   //이미 DB에 등록된걸 다시 가져옴
            em.merge(item); //update
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i").getResultList();
    }

}
