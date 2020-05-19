package ru.job4j.tracker.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.tracker.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The class performs actions with the item storage.
 * The database is used for storing data.
 */
public class TrackerSQL implements ITracker, AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackerSQL.class);

    private static final String INSERT_COMMAND = "insert into items (item) values (?)";
    private static final String UPDATE_COMMAND = "update items set item=? where id =?";
    private static final String DELETE_COMMAND = "delete from items where id=?";
    private static final String SELECT_ALL_COMMAND = "select* from items";
    private static final String SELECT_BY_NAME_COMMAND = "select* from items where item=?";
    private static final String SELECT_BY_ID_COMMAND = "select* from items where id=?";

    private final Connection connection;

    public TrackerSQL(Connection connection) {
        this.connection = connection;
        createTable();
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Сreates a table of items in the database, if it is not created
     */
    private void createTable() {
        try (Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS items (id serial primary key, item varchar(2000))");

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement st = connection.prepareStatement(INSERT_COMMAND, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, item.getName());
            st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                item.setId(String.valueOf(generatedKeys.getInt(1)));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        int itemId = Integer.parseInt(id);
        try (PreparedStatement st = connection.prepareStatement(UPDATE_COMMAND)) {
            st.setString(1, item.getName());
            st.setInt(2, itemId);
            result = st.executeUpdate() != 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        int itemId = Integer.parseInt(id);
        try (PreparedStatement st = connection.prepareStatement(DELETE_COMMAND)) {
            st.setInt(1, itemId);
            result = st.executeUpdate() != 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> itemList = new ArrayList<>();
        Item item;
        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery(SELECT_ALL_COMMAND)) {
                while (rs.next()) {
                    item = new Item(rs.getString("item"));
                    item.setId(String.valueOf(rs.getInt("id")));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return itemList;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> itemList = new ArrayList<>();
        Item item;
        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_NAME_COMMAND)) {
            st.setString(1, key);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    item = new Item(rs.getString("item"));
                    item.setId(String.valueOf(rs.getInt("id")));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return itemList;
    }

    @Override
    public Item findById(String id) {
        int itemId = Integer.parseInt(id);
        Item item = null;
        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID_COMMAND)) {
            st.setInt(1, itemId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    item = new Item(rs.getString("item"));
                    item.setId(String.valueOf(rs.getInt("id")));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return item;
    }

    /**
     * Close the connection (for using AutoCloseable)
     */
    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}