package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item = new Item("itemA",1000,10);
        //when
        itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(item);   //저장된 값이 조회한 값과 같은가?
    }

    @Test
    void findAll(){
        //given
        Item itemA = new Item("itemA",1000,10);
        Item itemB = new Item("itemB",2000,20);

        itemRepository.save(itemA);
        itemRepository.save(itemB);
        //when
        List<Item> result = itemRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(itemA,itemB);   //result에 itemA와 itemB가 포함되어 있는거?
    }


    @Test
    void updateItem(){
        //given
        Item itemA = new Item("itemA",1000,10);
        Item savedItem = itemRepository.save(itemA);
        Long id = itemA.getId();

        //when
        Item updateParam = new Item("itemB",2000,20);
        itemRepository.updateItem(id,updateParam);

        //then
        Item findItem = itemRepository.findById(id);

        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());

        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());

    }


}