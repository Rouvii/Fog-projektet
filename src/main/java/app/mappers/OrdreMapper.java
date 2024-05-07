package app.mappers;

import app.entities.ConnectionPool;
import app.entities.Ordre;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class OrdreMapper {



    public static List<Ordre> getAllOrders (ConnectionPool connectionPool) {

        List<Ordre> orderList = new ArrayList<>();
        String sql = "select order_id,user_id,dato,bredde,længde,s.betalt,s.afsendt,s.afvist,s.modtaget,slut_pris " +
                "from ordre o " +
                "join status s on s.status_id = o.status_id " +
                "order by user_id ";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)

        )
        {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                Date dato = rs.getDate("dato");
                double længde = rs.getDouble("længde");
                double bredde = rs.getDouble("bredde");
                boolean betalt = rs.getBoolean("betalt");
                boolean afsendt = rs.getBoolean("afsendt");
                boolean afvist = rs.getBoolean("afvist");
                boolean modtaget = rs.getBoolean("modtaget");
                double slutPris = rs.getDouble("slut_pris");

                orderList.add(new Ordre(orderId,userId,dato,længde,bredde,betalt,afsendt,afvist,modtaget,slutPris));
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderList;


    }

    public static List<Ordre> getAllOrdersPerUser(int userId, ConnectionPool connectionPool) throws DatabaseException
    {

        List<Ordre> ordreList = new ArrayList<>();
        String sql = "select * from ordre where user_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int id = rs.getInt("order_id");
                Date dato = rs.getDate("dato");
                double længde = rs.getDouble("længde");
                double bredde = rs.getDouble("bredde");
                boolean betalt = rs.getBoolean("betalt");
                boolean afsendt = rs.getBoolean("afsendt");
                boolean afvist = rs.getBoolean("afvist");
                boolean modtaget = rs.getBoolean("modtaget");
                double slutPris = rs.getDouble("slut_pris");
                ordreList.add(new Ordre (id,userId,dato,længde,bredde,betalt,afsendt,afvist,modtaget,slutPris));
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl " +  e.getMessage());
        }
        return ordreList;
    }


}
