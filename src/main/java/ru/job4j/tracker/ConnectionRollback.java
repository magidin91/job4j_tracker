package ru.job4j.tracker;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection, which rollback all commits.
 * It is used for integration test.
 */
public class ConnectionRollback {

    /**
     * Метод создает соедениение с autocommit=false и делает rollback, когда соединение закрывается. (метод возвращает
     * прокси для объекта connection)
     *
     * @param connection connection.
     * @return Connection object.
     * @throws SQLException possible exception.
     */
    public static Connection create(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        return (Connection) Proxy.newProxyInstance(
                ConnectionRollback.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    Object rsl = null;
                    if ("close".equals(method.getName())) {
                        connection.rollback();
                        connection.close();
                    } else {
                        rsl = method.invoke(connection, args);
                    }
                    return rsl;
                }
        );
    }
}
