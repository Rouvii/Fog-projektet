package app.mappers;

import app.entities.ConnectionPool;
import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//Integrationtest for OrdreMapper
class OrdreMapperTest {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    @BeforeAll
    static void setupClass()
    {
        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement())
            {
                // The test schema is already created, so we only need to delete/create test tables
                stmt.execute("DROP TABLE IF EXISTS test.users");
                stmt.execute("DROP TABLE IF EXISTS test.ordre");
                stmt.execute("DROP TABLE IF EXISTS test.status");
                stmt.execute("DROP SEQUENCE IF EXISTS test.order_order_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.user_user_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.status_status_id_seq CASCADE;");
                // Create tables as copy of original public schema structure
                stmt.execute("CREATE TABLE test.status AS (SELECT * from public.status) WITH NO DATA");
                stmt.execute("CREATE TABLE test.users AS (SELECT * from public.users) WITH NO DATA");
                stmt.execute("CREATE TABLE test.ordre AS (SELECT * from public.ordre) WITH NO DATA");
                // Create sequences for auto generating id's for users and orders
                stmt.execute("CREATE SEQUENCE test.status_status_id_seq");
                stmt.execute("ALTER TABLE test.status ALTER COLUMN status_id SET DEFAULT nextval('test.status_status_id_seq')");
                stmt.execute("CREATE SEQUENCE test.user_user_id_seq");
                stmt.execute("ALTER TABLE test.users ALTER COLUMN user_id SET DEFAULT nextval('test.user_user_id_seq')");
                stmt.execute("CREATE SEQUENCE test.order_order_id_seq");
                stmt.execute("ALTER TABLE test.ordre ALTER COLUMN order_id SET DEFAULT nextval('test.order_order_id_seq')");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Database connection failed");
        }

    }


    @BeforeEach
    void setUp()
    {
        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement())
            {
                // Remove all rows from all tables
                stmt.execute("DELETE FROM test.ordre");
                stmt.execute("DELETE FROM test.users");
                stmt.execute("DELETE FROM test.status");

                stmt.execute("INSERT INTO test.status (status_id, betalt, afsendt, afvist, modtaget) " +
                        "VALUES (1, 'true', 'false', 'false', 'false'), (2, 'true', 'true', 'false', 'false'),(3,'true','true','false','true'),(4,'false','false','true','false') ,(5,'false','false','false','false')");
                stmt.execute("INSERT INTO test.users (user_id, email, password, admin) " +
                        "VALUES  (1, 'kevintest@mail.dk', '1234', 'false'),(2, 'rouvitest@mail.dk', '1234', 'true')");

                stmt.execute("INSERT INTO test.ordre (order_id, dato, user_id, længde, bredde, status_id, total_pris) " +
                        "VALUES (1, '2024-05-22',1, 780, 600, 5, 20000), (2, '2024-05-22',2,700, 540, 5, 15000), (3, '2024-05-22',2,600, 480, 5, 14000)") ;
                // Set sequence to continue from the largest member_id
                stmt.execute("SELECT setval('test.order_order_id_seq', COALESCE((SELECT MAX(order_id) + 1 FROM test.ordre), 1), false)");
                stmt.execute("SELECT setval('test.user_user_id_seq', COALESCE((SELECT MAX(user_id) + 1 FROM test.users), 1), false)");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Database connection failed");
        }
    }

    @Test
    void getAllOrders() throws DatabaseException {
        int expected=3;
        List<Order> actualOrders = OrdreMapper.getAllOrders(connectionPool);
        assertEquals(expected, actualOrders.size());
    }

    @Test
    void createOrder()
    {
        try
        {
            Date currentTime = Date.valueOf(LocalDate.now());
            int userId = 1;
            Order newOrder = new Order(4,currentTime,1,780,600,5,20000);
            OrdreMapper.createOrder(userId, connectionPool, newOrder);
            Order dbOrder = OrdreMapper.getOrderByOrdreId(newOrder.getOrdreId(), connectionPool);
            assertEquals(newOrder, dbOrder);
        }
        catch (DatabaseException e)
        {
            fail("Database fejl: " + e.getMessage());
        }
    }


}