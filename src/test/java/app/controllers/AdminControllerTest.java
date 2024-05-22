package app.controllers;

import app.entities.ConnectionPool;
import app.entities.Materialer;
import app.entities.Order;
import app.exceptions.DatabaseException;
import app.mappers.MaterialeMapper;
import app.mappers.OrderlineMapper;
import app.mappers.OrdreMapper;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTest {




    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    @BeforeAll
    static void setUpClass()
    {
        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement())
            {
                stmt.execute("DROP VIEW IF EXISTS variant_view");
                stmt.execute("DROP TABLE IF EXISTS test.materialer");
                stmt.execute("DROP TABLE IF EXISTS test.ordre");
                stmt.execute("DROP TABLE IF EXISTS test.orderline");
                stmt.execute("DROP TABLE IF EXISTS test.status");


                stmt.execute("DROP SEQUENCE IF EXISTS test.materialer_materiale_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.order_order_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.orderline_orderline_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.status_status_id_seq CASCADE;");

                stmt.execute("CREATE TABLE test.orderline AS (SELECT * from public.orderline) WITH NO DATA");
                stmt.execute("CREATE TABLE test.materialer AS (SELECT * from public.materialer) WITH NO DATA");
                stmt.execute("CREATE TABLE test.ordre AS (SELECT * from public.ordre) WITH NO DATA");
                stmt.execute("CREATE TABLE test.status AS (SELECT * from public.status) WITH NO DATA");

                stmt.execute("CREATE SEQUENCE test.orderline_orderline_id_seq");
                stmt.execute("CREATE SEQUENCE test.materialer_materiale_id_seq");
                stmt.execute("CREATE SEQUENCE test.order_order_id_seq");
                stmt.execute("CREATE SEQUENCE test.status_status_id_seq");

                stmt.execute("ALTER TABLE test.orderline ALTER COLUMN orderline_id SET DEFAULT nextval('test.orderline_orderline_id_seq')");
                stmt.execute("ALTER TABLE test.ordre ALTER COLUMN order_id SET DEFAULT nextval('test.order_order_id_seq')");
                stmt.execute("ALTER TABLE test.materialer ALTER COLUMN materiale_id SET DEFAULT nextval('test.materialer_materiale_id_seq')");
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
                stmt.execute("DELETE FROM test.materialer");
                stmt.execute("DELETE FROM test.ordre");
                stmt.execute("DELETE FROM test.orderline");

                stmt.execute("INSERT INTO test.orderline (orderline_id, order_id, variant_id, quantity, description) " +
                        "VALUES (1, 1, 1, 2, 'test1'), (2, 1, 2, 3, 'test2'), (3, 1, 3, 4, 'test3'), (4, 2, 1, 2, 'test4'), (5, 2, 2, 3, 'test5'), (6, 2, 3, 4, 'test6')");

                stmt.execute("INSERT INTO test.materialer (materiale_id, type, pris_pr_meter) " +
                        "VALUES (1, 'Spær', 180), (2, 'Stolper', 520), (3, 'Skrue box', 300)");

                stmt.execute("INSERT INTO test.ordre (order_id, user_id, dato, længde, bredde, status_id, total_pris) " +
                        "VALUES (1, 1, '2021-05-01', 480, 600, 1, 1000), (2, 2, '2021-05-01', 480, 600, 1, 1000)");

                stmt.execute("INSERT INTO test.status (status_id, betalt, afsendt, afvist, modtaget) " +
                        "VALUES (1, 'true', 'false', 'false', 'false'), (2, 'true', 'true', 'false', 'false'),(3,'true','true','false','true'),(4,'false','false','true','false') ,(5,'false','false','false','false')");

                stmt.execute("SELECT setval('test.orderline_orderline_id_seq', COALESCE((SELECT MAX(orderline_id) + 1 FROM test.orderline), 1), false)");
                stmt.execute("SELECT setval('test.materialer_materiale_id_seq', COALESCE((SELECT MAX(materiale_id) + 1 FROM test.materialer), 1), false)");
                stmt.execute("SELECT setval('test.order_order_id_seq', COALESCE((SELECT MAX(order_id) + 1 FROM test.ordre), 1), false)");
    }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Database connection failed");
        }
    }

    @Test
    void deletemateriale()
    {

        Context ctx = Mockito.mock(Context.class);

        Mockito.when(ctx.formParam("materialeId")).thenReturn("1");

       List<Materialer> materialerListBefore = MaterialeMapper.getAllMaterialer(connectionPool);
       int before = materialerListBefore.size();


       AdminController.deletemateriale(ctx,connectionPool);

       List<Materialer>materialerListAfter = MaterialeMapper.getAllMaterialer(connectionPool);
       int after = materialerListAfter.size();

       assertEquals(before -1, after);


    }

    @Test
    void deleteOrder()
    {
        Context ctx = Mockito.mock(Context.class);

        Mockito.when(ctx.formParam("orderId")).thenReturn("1");


        try{
            int actualOrderlines = OrderlineMapper.countOrderlines(1,connectionPool);
            if(actualOrderlines > 0){
                OrderlineMapper.deleteOrderline(1,connectionPool);
            }

           List<Order>before = OrdreMapper.getAllOrders(connectionPool);
            OrdreMapper.deleteOrder(1,connectionPool);


            List<Order>after = OrdreMapper.getAllOrders(connectionPool);
            assertEquals(before.size()-1,after.size());
        }catch (DatabaseException e){
            e.printStackTrace();
            fail("Database connection failed");
        }



    }
}