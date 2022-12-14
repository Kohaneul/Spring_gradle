package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@Transactional  //JPA의 모든 데이터 변경은 트렌젝션 안에서 움직인다.
public class JpaItemRepository implements ItemRepository {

    private final EntityManager em; //의존관계 주입

    public JpaItemRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Item save(Item item) {
        em.persist(item);   //persist : 영구히 저장한다(저장의 개념)
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = em.find(Item.class, itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        //트랜잭션이 커밋되는 시점에 update 쿼리를 만들어서 실제 db 반영
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String jpql="select i from Item i"; //Item Entity 자체를 반환
        //jpql : 객체지향 쿼리 언어
        //동적쿼리 작성
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        if(StringUtils.hasText(itemName)||maxPrice!=null){
            jpql+=" where";
        }
        boolean andFlag = false;
        if(StringUtils.hasText(itemName)){
            jpql+=" i.itemName like concat('%',:itemName,'%')";
            andFlag = true;
        }

        if(maxPrice!=null){
            if(andFlag){
                jpql+=" and";
            }
            jpql+=" i.price<= :maxPrice";
        }

        log.info("jpql={}",jpql);

        TypedQuery<Item> query = em.createQuery(jpql,Item.class);
        if(StringUtils.hasText(itemName)){
            query.setParameter("itemName",itemName);
        }
        if(maxPrice!=null){
            query.setParameter("maxPrice",maxPrice);
        }

        return query.getResultList();
    }
}
