package cn.tedu.sp02.item.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import cn.tedu.sp01.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Value("${server.port}")
    private int port;

    @Autowired
    private HttpServletRequest request;

    ////http://localhost:8001/item/getItems/35   http://219.235.6.205:8001/item/getItems/35
    @GetMapping("/getItems/{orderId}")
    public JsonResult<?> getItems(@PathVariable String orderId) {
        ////http://localhost:3001/  headRouter=item-service/item/getItems/999
        log.info("orderId=" + orderId + ",port" + port);
        List<Item> items = itemService.getItems(orderId);
        return JsonResult.ok().data(items).msg("port=" + port);
    }

    /*
    http://localhost:8001/decreaseNumber


        [{"id":1,"name":"商品1","number":1},{"id":2,"name":"商品1","number":4},{"id":3,"name":"商品1","number":2},{"id":4,"name":"商品1","number":6},{"id":5,"name":"商品1","number":2}]


    {
        "code": 200,
        "msg": "减少商品库存成功",
        "data": null
    }
       */
    @PostMapping("/decreaseNumber/{orderId}")
    public JsonResult decreaseNumber(@RequestBody List<Item> items, @PathVariable String orderId) {
        /// ////http://localhost:8001/item/decreaseNumber
        itemService.decreaseNumbers(items);
        return JsonResult.ok().msg("减少商品库存成功");
    }

    @PostMapping("/decreaseNumber1")
    public JsonResult decreaseNumber1(@RequestBody List<Item> items) {
        itemService.decreaseNumbers(items);
        return JsonResult.ok().data(items).msg("返回对象体成功");
    }

    @PostMapping("/decreaseNumber2")
    public JsonResult decreaseNumber2(@RequestBody Item items) {

        return JsonResult.ok().data(items).msg("返回对象体成功");
    }

    @PostMapping("/decreaseNumber3")
    public JsonResult decreaseNumber3(@RequestBody Item items) {

        return JsonResult.ok().data(items).msg("返回对象体成功3");
    }

    @PostMapping("/decreaseNumber4")
    public JsonResult decreaseNumber4(@RequestBody Item items) {
        String strHeadpath = request.getHeader("headRouter");
        return JsonResult.ok().data(items).msg(items + "返回对象体成功4" + strHeadpath);
    }
}