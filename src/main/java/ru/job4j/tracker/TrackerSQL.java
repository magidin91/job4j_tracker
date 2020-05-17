package ru.job4j.tracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackerSQL implements ITracker, AutoCloseable {
    private static final Logger LOG = LogManager.getLogger(TrackerSQL.class.getName());
    private Connection connection;

    public TrackerSQL(Connection connection) {
        this.connection = connection;
        createTable();
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Метод создает таблицу, если она не создана
     */
    private void createTable() {
        try (Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS items (id serial primary key, item varchar(2000))");

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Метод получает Item с введенным пользователем именем, добавляет его в таблицу и устанавливает поле id
     */
    @Override
    public Item add(Item item) {
        try (PreparedStatement st = connection.prepareStatement("insert into items (item) values (?)", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, item.getName());
            st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                item.setId(String.valueOf(generatedKeys.getInt(1)));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return item;
    }

    /**
     * Метод редактирует объект Item c указанным id
     *
     * @return true, если строка изменена
     */
    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        int itemId = Integer.parseInt(id);
        try (PreparedStatement st = connection.prepareStatement("update items set item=? where id =?")) {
            st.setString(1, item.getName());
            st.setInt(2, itemId);
            result = st.executeUpdate() != 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Метод удалет объект Item(строку) c указанным id
     *
     * @return true, если строка удалена
     */
    @Override
    public boolean delete(String id) {
        boolean result = false;
        int itemId = Integer.parseInt(id);
        try (PreparedStatement st = connection.prepareStatement("delete from items where id=?")) {
            st.setInt(1, itemId);
            result = st.executeUpdate() != 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> itemList = new ArrayList<>();
        Item item;
        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery("select* from items")) {
                while (rs.next()) {
                    item = new Item(rs.getString("item"));
                    item.setId(String.valueOf(rs.getInt("id")));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return itemList;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> itemList = new ArrayList<>();
        Item item;
        try (PreparedStatement st = connection.prepareStatement("select* from items where item=?")) {
            st.setString(1, key);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    item = new Item(rs.getString("item"));
                    item.setId(String.valueOf(rs.getInt("id")));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return itemList;
    }

    @Override
    public Item findById(String id) {
        int itemId = Integer.parseInt(id);
        Item item = null;
        try (PreparedStatement st = connection.prepareStatement("select* from items where id=?")) {
            st.setInt(1, itemId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    item = new Item(rs.getString("item"));
                    item.setId(String.valueOf(rs.getInt("id")));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return item;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}