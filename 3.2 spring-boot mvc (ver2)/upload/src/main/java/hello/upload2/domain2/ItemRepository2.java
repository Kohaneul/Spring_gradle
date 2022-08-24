package hello.upload2.domain2;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository2 {

    private Map<Long,Item2> store = new HashMap<>();
    private static long sequence = 0L;

    public Item2 save(Item2 item){
        item.setId(++sequence);
        store.put(item.getId(),item);
        return item;
    }

    public Item2 findById(long id){
        return store.get(id);
    }


}
