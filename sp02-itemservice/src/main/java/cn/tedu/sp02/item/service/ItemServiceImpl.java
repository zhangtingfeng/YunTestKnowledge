package cn.tedu.sp02.item.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Override
    public List<Item> getItems(String orderId) {
        List<Item> list = new LinkedList<>();
        list.add(new Item(1, "商品1", 1));
        list.add(new Item(2, "商品1", 4));
        list.add(new Item(3, "商品1", 2));
        list.add(new Item(4, "商品1", 6));
        list.add(new Item(5, "商品1", 2));
        return list;
    }

    @Override
    public void decreaseNumbers(List<Item> items) {
        for (Item item : items) {
            log.info("减少商品库存：" + item);
        }
    }
}