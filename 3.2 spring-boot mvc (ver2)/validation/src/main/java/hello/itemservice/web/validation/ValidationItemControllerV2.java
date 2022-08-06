package hello.itemservice.web.validation;

import com.sun.jdi.Field;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;
    @InitBinder //해당 컨트롤러에만 영향을 준다.
    public void init(WebDataBinder dataBinder){
        //Controller가 호출될때 WebDataBinder에 의해서 itemValidator을 적용할 수 있다.
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }


    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //BindingResult : 객체의 반환된 결과가 담김 , ModelAttribute 뒤에 와야 함. (왜냐하면 ModelAttribute에 담은 객체에 대한 바인딩 결과를 표현해줘야 하기 때문)
    //bindingResult가 없으면 spring 입장에서 오류발생시 오류페이지만 넘긴채 컨트롤러 호출x
    //bindingResult가 있다면 오류발생해도 컨트롤러 호출후 문제 정보를 bindingResult에 담아서 메세지 표출함

  //  @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수 입니다.")); //ModelAttribute에 담기는 이름을 넣어주면 된다.
            //binding 에러는 FieldError 라고 하는 Spring이 제공하는 객체가 있음
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item","가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            //BindingResult는 자동으로 view에 넘어가기 때문에 model 객체에 넣지 않아도 된다.
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }




    //V1의 방식으로 한다면 오류메세지가 뜨게되면 기존에 입력했던 값이 사라지는 단점이 있다.
    //V2 : 기존 값도 유지하되, 오류메세지까지 표시하는 방법이다.
  //  @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //objectName:객체이름, field:필드이름, rejectedObject:거절당한 값, bindingFailure:(거절당한)값은 잘 넘어오지 않았는지..?
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,null,null,"상품 이름은 필수 입니다.")); //ModelAttribute에 담기는 이름을 넣어주면 된다.
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,null,null,"가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,null,null,"수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item",null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            //BindingResult는 자동으로 view에 넘어가기 때문에 model 객체에 넣지 않아도 된다.
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

 //   @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("objectName={}",bindingResult.getObjectName());
        log.info("target={}",bindingResult.getTarget());
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //objectName:객체이름, field:필드이름, rejectedObject:거절당한 값, bindingFailure:(거절당한)값은 잘 넘어오지 않았는지..?
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,new String[]{"required.item.itemName"},null,null)); //ModelAttribute에 담기는 이름을 넣어주면 된다.
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,new String[]{"range.item.price"},new Object[]{1000,1000000},null));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,new String[]{"max.item.quantity"},new Object[]{9999},null));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,resultPrice},null));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            //BindingResult는 자동으로 view에 넘어가기 때문에 model 객체에 넣지 않아도 된다.
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

 //  @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("objectName={}",bindingResult.getObjectName());
        log.info("target={}",bindingResult.getTarget());
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //errorCode에 errors.properties 에서 오류코드 맨 앞 키 값만 가져온다.
            bindingResult.rejectValue("itemName","required");   //오류코드 맨 앞만
        }

//        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"itemName","required"); 와 동일하게 쓸 수 있지만
        //해당 코드는 공백같은 단순한 기능만 제공함


        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price","range", new Object[]{1000,1000000},null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.rejectValue("quantity","max",new Object[]{9999},null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            //BindingResult는 자동으로 view에 넘어가기 때문에 model 객체에 넣지 않아도 된다.
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

  //  @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //검증단계
        itemValidator.validate(item,bindingResult);

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            //BindingResult는 자동으로 view에 넘어가기 때문에 model 객체에 넣지 않아도 된다.
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        //컨트롤러 호출 되기 전 파라미터 바인딩 후 검증 support메소드 ->validate
        // @Validated : 검증기를 실행하라
        // WebDataBinder에 있는 검증기를 실행하게 된다.
        // @Validated를 넣어주면 자동으로 item 객체를 검증해주고 결과는 Validated 객체에 넣어준다


        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            //BindingResult는 자동으로 view에 넘어가기 때문에 model 객체에 넣지 않아도 된다.
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

