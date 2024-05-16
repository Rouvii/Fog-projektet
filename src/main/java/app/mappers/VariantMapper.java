package app.mappers;

import app.entities.ConnectionPool;
import app.entities.Materialer;
import app.entities.Order;
import app.entities.Variant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class VariantMapper {

    public static List<Variant> getAllVariantsByMaterialId(int materialId, ConnectionPool connectionPool) {

        List<Variant> variantList = new ArrayList<>();
        String sql="select * from variant_view where materiale_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)

        )
        {
            ps.setInt(1, materialId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            int materialeId = rs.getInt("materiale_id");
            int variantId = rs.getInt("variant_id");
            int length = rs.getInt("længde");
            String type = rs.getString("type");
            double price = rs.getDouble("pris_pr_meter");
            Materialer materialer = new Materialer(materialeId,type,price);
            Variant variant = new Variant(variantId,materialer,length);
            variantList.add(variant);

            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return variantList;


    }

}
