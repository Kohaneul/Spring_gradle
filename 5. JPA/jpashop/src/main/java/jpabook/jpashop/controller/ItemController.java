package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public String updateItem(@PathVariable Long id, Model model){
        Item item = itemService.findOne(id);
        model.addAttribute("item",item);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{id}/edit")
    public String updateItem(@PathVariable Long id, @Valid @ModelAttribute(name = "itemForm") BookUpdateForm form,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "items/updateItemForm";
        }
        Item item = itemService.findOne(id);

        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());


        return"redirect:/items";
    }
}
