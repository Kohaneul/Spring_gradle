package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor    // final이 붙은 변수의 생성자를 만들어줌
public class BasicItemController {
    private final ItemRepository itemRepository;


    //테스트용 데이터 추가
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,30));

    }




    @GetMapping
    public String items(Model model){
        List<Item> items= itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }


//    @PostMapping("/add")
//    public String  addItemV1(@RequestParam String itemName, @RequestParam Integer price, @RequestParam Integer quantity, Model model)
//    {
//        Item item = new Item(itemName,price,quantity);
//        Item items = itemRepository.save(item);
//        model.addAttribute("item",item);
//        return "basic/item";
//    }

//
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item")Item item, Model model){
//        itemRepository.save(item);
//        //ModelAttribute가 자동으로 만들어줘서 set 호출함
//        //ModelAttribute의 역할 : 1. 파라미터 넘김, 2. modelAttribute로 뷰에 넘김
//        return "basic/item";
//    }


//    @PostMapping("/add")    //ModelAttribute 생략시...
//    public String addItemV4( Item item){
//        //ModelAttribute의 name 을 지우면 클래스 명이 오게 된다.
//        //클래스명 : Item -> item으로....(첫글자를 소문자로 바꿈)
//        itemRepository.save(item);
//        return"basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item){
//        //ModelAttribute의 name 을 지우면 클래스 명이 오게 된다.
//        //클래스명 : Item -> item으로....(첫글자를 소문자로 바꿈)
//
//        itemRepository.save(item);
//        return"basic/item";
/*      해당 view로 이동하게 되면 작동은 하나, 새로고침을 눌렀을 경우
        웹브라우저는 마지막 작업 기준으로 새로고침이 되기 때문에 계속해서 item 객체가 생겨나게 된다.
        이를 막기 위해서 리다이랙트를 시켜주면 된다.
    */

//    }

//
//    @PostMapping("/add")
//    public String addItemV5(@ModelAttribute Item item){
//        itemRepository.save(item);
//
//        return "redirect:/basic/items/"+item.getId();
//    }


    @PostMapping("/add")
    public String adfa(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status",true);
    return "redirect:/basic/items/{itemId}";
    }


//    @PostMapping("/add")
//    public String addItemV6(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId",savedItem.getId());
//        redirectAttributes.addAttribute("status",true);
//
//        return "redirect:/basic/items/{itemId}";
//        //redirectattribue에 넣은 id값이 치환된다. status는 쿼리파라미터로 넘어가게 된다. url 인코딩 문제도 해결
//    }






    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editForm2(@PathVariable Long itemId, @ModelAttribute Item item){
        Item i = itemRepository.findById(itemId);

            itemRepository.updateItem(itemId,item);


        return "redirect:/basic/items/{itemId}";
        //리다이렉트 : 뷰 탬플릿을 호출하는 대신, 상세 화면이 있는 곳으로 이동(Controller=>상품 상세로 호출)

    }
//
//    @GetMapping("/{itemId}/edit")
//    public String editForm(@PathVariable long itemId,Model model){
//        Item item = itemRepository.findById(itemId);
//        model.addAttribute("item",item);
//        return "basic/editForm";
//    }
//
//    @PostMapping("/{itemId}/edit")
//    public String editForm(@PathVariable long itemId, @ModelAttribute Item item){
//        itemRepository.updateItem(itemId, item);
//        return "basic/item";
//    }











}
