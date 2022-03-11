package org.generation.webproject.service;

import org.generation.webproject.repository.entity.Item;

import java.util.List;

public interface ItemService {

    //save method is for 2 purposes - Create new item & Update existing item
    Item save(Item item);

    //Delete item from database - based on item Id
    void delete(int itemId);

    //Read all item from database
    List<Item> all();

    //Read an item from database - based on item Id
    Item findById(int itemId);
}

