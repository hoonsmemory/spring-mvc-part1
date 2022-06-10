package hoon.springmvc.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong();

    public Item save(Item item) {
        item.setId(sequence.incrementAndGet());
        store.put(item.getId(), item);

        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long id, ItemDto itemDto) {
        Item item = findById(id);
        item.setItemName(itemDto.getItemName());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
