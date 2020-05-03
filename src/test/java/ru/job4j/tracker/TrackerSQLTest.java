package ru.job4j.tracker;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TrackerSQLTest {

    @Test
    public void checkConnection() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(new ConnectionCreator().init()))) {
            assertNotNull(tracker.getConnection());
        }
    }

    @Test
    public void createItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(new ConnectionCreator().init()))) {
            tracker.add(new Item("name"));
            assertThat(tracker.findByName("name").size(), is(1));
        }
    }

    @Test
    public void replaceItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(new ConnectionCreator().init()))) {
            Item item = tracker.add(new Item("name"));
            String id = item.getId();
            Item item2 = new Item("name2");
            tracker.replace(id, item2);
            assertThat(tracker.findById(id).getName(), is("name2"));
        }
    }

    @Test
    public void deleteItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(new ConnectionCreator().init()))) {
            Item item = tracker.add(new Item("name"));
            String id = item.getId();
            tracker.delete(id);
            assertNull(tracker.findById(id));
        }
    }

    @Test
    public void findByIdItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(new ConnectionCreator().init()))) {
            Item item = tracker.add(new Item("name"));
            String id = item.getId();
            assertThat(tracker.findById(id), is(item));
        }
    }

    @Test
    public void findByNameItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(new ConnectionCreator().init()))) {
            Item item = tracker.add(new Item("name"));
            assertThat(tracker.findByName("name"), is(List.of(item)));
        }
    }

    @Test
    public void findAllItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(new ConnectionCreator().init()))) {
            Item item = tracker.add(new Item("name"));
            Item item2 = tracker.add(new Item("name2"));
            assertTrue(tracker.findAll().containsAll(List.of(item, item2)));
        }
    }
}