package app.mappers;

import app.entities.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class VariantMapperTest {


    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    @BeforeAll
  static void setUpClass()
    {
        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement())
            {
                stmt.execute("DROP TABLE IF EXISTS test.variant");
                stmt.execute("DROP TABLE IF EXISTS test.materialer");
                stmt.execute("DROP VIEW IF EXISTS variant_view");

                stmt.execute("DROP SEQUENCE IF EXISTS test.variant_variant_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.materialer_materiale_id_seq CASCADE;");

                stmt.execute("CREATE TABLE test.variant AS (SELECT * from public.variant) WITH NO DATA");
                stmt.execute("CREATE TABLE test.materialer AS (SELECT * from public.materialer) WITH NO DATA");
                stmt.execute("CREATE VIEW variant_view AS SELECT variant.variant_id, variant.længde, materialer.materiale_id, materialer.type, materialer.pris_pr_meter FROM test.variant JOIN test.materialer ON variant.materiale_id = materialer.materiale_id");

                stmt.execute("CREATE SEQUENCE test.variant_variant_id_seq");
                stmt.execute("ALTER TABLE test.variant ALTER COLUMN variant_id SET DEFAULT nextval('test.variant_variant_id_seq')");

                stmt.execute("CREATE SEQUENCE test.materialer_materiale_id_seq");
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

                stmt.execute("DELETE FROM test.variant");
                stmt.execute("DELETE FROM test.materialer");

                stmt.execute("INSERT INTO test.materialer (materiale_id, type, pris_pr_meter) " +
                        "VALUES (1, 'Spær', 180), (2, 'Stolper', 520), (3, 'Skrue box', 300)");

                stmt.execute("INSERT INTO test.variant (variant_id, længde,materiale_id) " +
                        "VALUES (1, 480,1), (2, 600,1), (3, 300,2), (4,550,1)");

                stmt.execute("SELECT setval('test.variant_variant_id_seq', COALESCE((SELECT MAX(variant_id) + 1 FROM test.variant), 1), false)");
                stmt.execute("SELECT setval('test.materialer_materiale_id_seq', COALESCE((SELECT MAX(materiale_id) + 1 FROM test.materialer), 1), false)");

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Database connection failed");
        }
    }



    @Test
    void getAllVariantsByMaterialId()
    {
        int materialId = 1;
        int expected=3;
        assertEquals(expected,VariantMapper.getAllVariantsByMaterialId(materialId,connectionPool).size());
    }
}