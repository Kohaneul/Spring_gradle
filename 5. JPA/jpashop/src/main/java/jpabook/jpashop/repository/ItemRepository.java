package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            em.merge(item);
            /**
             merge : 영속성 컨텍스트에서 id 값으로 엔티티 찾아온 후 파라미터로 넘어온 값으로
             모든 데이터를 다 바꿔치기하고 바꿔치기한 내용을 반환하는 객체를 관리함. 파라미터로 넘긴 객체는 관리x
             주의 : 모든 속성이 다 바뀌어서 필드가 없다면 null 값으로 업데이트 할 수도 있다.
             */
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){

        return em.createQuery("select i from Item i").getResultList();
    }

}
