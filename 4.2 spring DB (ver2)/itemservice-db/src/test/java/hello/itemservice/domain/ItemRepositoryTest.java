package hello.itemservice.domain;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.memory.MemoryItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Transactional  //트랜잭션 시작하고 문제없으면 커밋시키는 로직
@SpringBootTest //@SpringBootApplication 을 찾아내서 설정으로 사용이 된다.
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;  //테스트할때는 인터페이스로 하는게 좋다.

//@Transactional 어노테이션을 쓰게 되면 스프링 부트가 해당 코드 자동 생성시켜줌
//    @Autowired
//    PlatformTransactionManager transactionManager;
//    TransactionStatus status;
//    @BeforeEach
//    void beforeEach(){
//        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
//        //Test 시작 전에 트랜잭션 시작.
//    }


    @AfterEach
    void afterEach() {
        //MemoryItemRepository 의 경우 제한적으로 사용
        if (itemRepository instanceof MemoryItemRepository) {
            ((MemoryItemRepository) itemRepository).clearStore();
        }
        //@Transactional 어노테이션을 쓰게 되면 스프링 부트가 하기 코드 자동 생성하여 실행시킴
        //트랜잭션 롤백
        // transactionManager.rollback(status);
    }

    @Test
    //@Commit   결과를 보고싶을때 사용 @Rollback(false)와 같음
   // @Transactional
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId()).get();
//        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void updateItem() {
        //given
        Item item = new Item("item1", 10000, 10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        ItemUpdateDto updateParam = new ItemUpdateDto("item2", 20000, 30);
        itemRepository.update(itemId, updateParam);

        //then
        Item findItem = itemRepository.findById(itemId).get();
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

    @Test
    void findItems() {
        //given
        Item item1 = new Item("itemA-1", 10000, 10);
        Item item2 = new Item("itemA-2", 20000, 20);
        Item item3 = new Item("itemB-1", 30000, 30);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        //둘 다 없음 검증
        test(null, null, item1, item2, item3);  //상품정보, 가격조건이 없으면 아이템이 전부다 조회
        test("", null, item1, item2, item3);    //itemName의 길이가 0일때도 빈 문자열로 인식함

        //itemName 검증
        test("itemA", null, item1, item2);
        test("temA", null, item1, item2);
        test("itemB", null, item3);

        //maxPrice 검증
        test(null, 10000, item1);

        //둘 다 있음 검증
        test("itemA", 10000, item1);
    }


    void test(String itemName, Integer maxPrice, Item... items) {
        List<Item> result = itemRepository.findAll(new ItemSearchCond(itemName, maxPrice));
        assertThat(result).containsExactly(items);  //containsExactly = > items 객체의 순서까지 일치해야됨.
    }
}
