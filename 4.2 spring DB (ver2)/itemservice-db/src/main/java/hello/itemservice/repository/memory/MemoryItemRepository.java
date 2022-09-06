package hello.itemservice.repository.memory;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = findById(itemId).orElseThrow();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));  //Optional.of : Null값을 허용하지 않는다 Optional.ofNullable : Null값을 허용한다.
    }



    @Override
    public List<Item> findAll(ItemSearchCond cond){ //상품 검색조건이 전달이 되면
        String itemName = cond.getItemName();   //아이템이름
        Integer maxPrice = cond.getMaxPrice();  //최대가격을 꺼냄
        return store.values().stream().filter(  //store에 저장된 값을 stream으로 뒤져와서
                item->{
                    if(ObjectUtils.isEmpty(itemName)){  //검색조건을 안쓰게 되면 전체를 반환
                        return true;
                    }
                    return item.getItemName().contains(itemName);   //아이템 이름을포함하고 있는것만 통과
                }).filter(item->{
                    if(ObjectUtils.isEmpty(maxPrice)){  //최대값이 비어있으면 전체반환
                        return true;
                    }
                    return item.getPrice()<=maxPrice;   //최대가격보다 작은 값을 반환
            }).collect(Collectors.toList());
    }


    public void clearStore() {
        store.clear();
    }

}
