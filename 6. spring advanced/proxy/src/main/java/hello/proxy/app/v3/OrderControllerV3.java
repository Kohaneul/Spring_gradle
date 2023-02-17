package hello.proxy.app.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {
    private final OrderServiceV3 orderService;

    @GetMapping("/v3/request")
    public String request(@RequestParam("itemId")String itemId){
        orderService.order(itemId);
        return "ok";
    }

    @GetMapping("/v3/no-log")
    public String noLog(){
        return "ok";
    }

}
