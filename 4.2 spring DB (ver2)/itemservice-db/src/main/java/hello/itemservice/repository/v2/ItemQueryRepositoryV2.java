package hello.itemservice.repository.v2;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static hello.itemservice.domain.QItem.item;

@Repository
@Transactional
@Slf4j
public class ItemQueryRepositoryV2 {
    //복잡한 쿼리는 여기서 해결
    private final JPAQueryFactory query;

    public ItemQueryRepositoryV2(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }



    public List<Item> findAll(ItemSearchCond itemSearchCond){
        return query.select(item).from(item).where(likeItemName(itemSearchCond.getItemName()),maxPrice(itemSearchCond.getMaxPrice())).fetch();
    }

    private BooleanExpression likeItemName(String itemName){
        if(StringUtils.hasText(itemName)){
            return item.itemName.like("%"+itemName+"%");
        }
        return null;
    }

    private BooleanExpression maxPrice(Integer maxPrice){
        if(maxPrice!=null){
            return item.price.loe(maxPrice);
        }
        return null;
    }





}
