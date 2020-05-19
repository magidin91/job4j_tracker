package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.comparator.ItemComparator;
import ru.job4j.tracker.comparator.ItemReverseComparator;
import ru.job4j.tracker.model.Item;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Item Id = null, т.к. мы его не задавали в тесте.
 */
public class ComparatorTest {
    @Test
    public void sort() {
        List<Item> items = Arrays.asList(new Item("b"), new Item("a"), new Item("c"));
        System.out.println(items);
        items.sort(new ItemComparator());
        List<Item> expected = Arrays.asList(new Item("a"), new Item("b"), new Item("c"));
        System.out.println(items);
        assertThat(items, is(expected));
    }

    @Test
    public void sortReverse() {
        List<Item> items = Arrays.asList(new Item("b"), new Item("a"), new Item("c"));
        System.out.println(items);
        items.sort(new ItemReverseComparator());
        List<Item> expected = Arrays.asList(new Item("c"), new Item("b"), new Item("a"));
        System.out.println(items);
        assertThat(items, is(expected));
    }
}