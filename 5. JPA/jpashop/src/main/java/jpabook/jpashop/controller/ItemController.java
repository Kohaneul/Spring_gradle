package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(@ModelAttribute(name = "itemForm") BookForm form){
        return "items/createItemForm";
    }
    @PostMapping("/items/new")
    public String create(@Valid @ModelAttribute(name = "itemForm") BookForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "items/createItemForm";
        }
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setStockQuantity(form.getStockQuantity());
        Long bookId = itemService.saveItem(book);
        book.setId(bookId);
        return"redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    @GetMapping("/items/{id}/edit")
    public String updateItemForm(@PathVariable Long id, Model model){
        Book item = (Book) itemService.findOne(id);

        UpdateItemDto form = new UpdateItemDto();
        form.setId(id);
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form",form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{id}/edit")
    public String updateItemForm(@Valid @ModelAttribute UpdateItemDto form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "items/updateItemForm";
        }
        // 방법1 ) merge로 호출 (권장하지 않음) 전체를 병합하기 때문에 만약 누락된 값이 있다면 null로 표시됨.
//        Book book = new Book(); //준영속 ENTITY =>JPA가 관리 X
//        book.setId(form.getId());   //식별자(ID) 값이 존재 => 이미 JPA에 의해서 관리되고 있는 상황이었음
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setIsbn(form.getIsbn());
//        book.setAuthor(form.getAuthor());
//        itemService.saveItem(book);

        //방법2 ) 변경감지를 이용
        itemService.updateItem(form.getId(),form.getPrice(),form.getName(),form.getStockQuantity());




        return"redirect:/items";
    }
}
