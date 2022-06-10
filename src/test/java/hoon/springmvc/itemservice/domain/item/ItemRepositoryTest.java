package hoon.springmvc.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    @DisplayName("")
    public void find() throws Exception
    {
        // given
        Item item1 = new Item("itemA", 10000, 10);
        itemRepository.save(item1);
        Item item2 = new Item("itemB", 10000, 10);
        itemRepository.save(item2);

        // when
        List<Item> allItems = itemRepository.findAll();

        // then
        assertThat(allItems.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("")
    public void save() throws Exception
    {
        // given
        Item item = new Item("itemA", 10000, 10);

        // when
        Item saveItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(item.getId());

        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    @DisplayName("")
    public void updateAll() throws Exception
    {
        //given
        Item item = new Item("item1", 10000, 10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();
        //when
        ItemDto updateParam = new ItemDto("item2", 20000, 30);
        itemRepository.update(itemId, updateParam);
        Item findItem = itemRepository.findById(itemId);
        //then
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());

        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

}