package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository //Component scan의 대상이 된다.
public class ItemRepository {
    private static final Map<Long, Item> store = new HashMap<>();  //static
    private static Long sequence = 0L;  //static


    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(),item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }


    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public Item updateItem(Long id,Item updateParam){
        Item findItem = findById(id);
        findItem.setItemName(updateParam.getItemName());    //파라미터 정보가 넘어옴
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        return findItem;
    }

    public void clearStore(){
        store.clear();
    }



}
