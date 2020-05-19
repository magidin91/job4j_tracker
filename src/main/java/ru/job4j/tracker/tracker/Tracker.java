package ru.job4j.tracker.tracker;

import ru.job4j.tracker.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The class performs actions with the item storage.
 * A List is used for storing items.
 */
public class Tracker implements ITracker {
    /**
     * A List for storing items.
     */
    private final List<Item> items;

    /**
     * By default, the ArrayList is used
     */
    public Tracker() {
        this.items = new ArrayList<>();
    }

    public Tracker(List<Item> items) {
        this.items = items;
    }

    public Item add(Item item) {
        item.setId(this.generateId());
        items.add(item);
        return item;
    }

    public boolean replace(String id, Item item) {
        boolean rsl = false;
        int index = indexOf(id);
        if (index != -1) {
            item.setId(id);
            items.set(index, item);
            rsl = true;
        }
        return rsl;
    }

    public boolean delete(String id) {
        boolean rsl = false;
        int index = indexOf(id);
        if (index != -1) {
            items.remove(index);
            rsl = true;
        }
        return rsl;
    }

    public ArrayList<Item> findAll() {
        ArrayList<Item> itemsWithoutNull = new ArrayList<>();
        for (Item item : items) {
            if (item != null) {
                itemsWithoutNull.add(item);
            }
        }
        return itemsWithoutNull;
    }

    public ArrayList<Item> findByName(String key) {
        ArrayList<Item> itemsEqualByName = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                itemsEqualByName.add(item);
            }
        }
        return itemsEqualByName;
    }

    public Item findById(String id) {
        Item rsl = null;
        int index = indexOf(id);
        if (index != -1) {
            rsl = items.get(index);
        }
        return rsl;
    }

    /**
     * Generates a unique item key for identification
     */
    private String generateId() {
        Random rm = new Random();
        return String.valueOf(rm.nextLong() + System.currentTimeMillis());
    }

    /**
     * Returns the item index in the List by id or -1 if there is no item with this id.
     */
    private int indexOf(String id) {
        int rsl = -1;
        for (int index = 0; index < items.size(); index++) {
            if (items.get(index).getId().equals(id)) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }
}