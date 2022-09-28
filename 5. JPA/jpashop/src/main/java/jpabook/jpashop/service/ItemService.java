package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long saveItem(Item item){
        itemRepository.save(item);
        return item.getId();
    }
    @Transactional
    public void updateItem(Long id, int price, String name, int stockQuantity){
        Item item = itemRepository.findOne(id); //ID 기반으로 영속상태 ENTITY 찾아옴

        item.change(price,name,stockQuantity);
        // 이미 영속상태이기 떄문에 jpa가 알아서 트랜잭션 커밋 시점에 flush를 작동하여 변경된 내용을 찾아서 알아서 업데이트 해줌
    }

    public Item findOne(Long id){
      return  itemRepository.findOne(id);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }
}
