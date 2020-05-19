package ru.job4j.tracker.tracker;

import ru.job4j.tracker.model.Item;

import java.util.List;

/**
 * The interface performs actions with the item storage
 */
public interface ITracker {
    /**
     * Adds an item to the storage
     */
    Item add(Item item);

    /**
     * Replaces an item from the storage with the passed item
     */
    boolean replace(String id, Item item);

    /**
     * Deletes an item from the storage
     */
    boolean delete(String id);

    /**
     * Returns a list of all items in the storage
     */
    List<Item> findAll();

    /**
     * Returns a list of items with a specific name in the storage
     */
    List<Item> findByName(String key);

    /**
     * Returns an item with a specific id
     */
    Item findById(String id);
}