package app.mappers;

import app.entities.ConnectionPool;
import app.entities.Ordre;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
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
                int længde = rs.getInt("længde");
                int bredde = rs.getInt("bredde");
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
        String sql = "select order_id,user_id,dato,bredde,længde,s.betalt,s.afsendt,s.afvist,s.modtaget,slut_pris " +
                "from ordre o " +
                "join status s on s.status_id = o.status_id " +
                "where user_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("order_id");
                Date dato = rs.getDate("dato");
                int længde = rs.getInt("længde");
                int bredde = rs.getInt("bredde");
                boolean betalt = rs.getBoolean("betalt");
                boolean afsendt = rs.getBoolean("afsendt");
                boolean afvist = rs.getBoolean("afvist");
                boolean modtaget = rs.getBoolean("modtaget");
                double slutPris = rs.getDouble("slut_pris");
                ordreList.add(new Ordre (id,userId,dato,længde,bredde,betalt,afsendt,afvist,modtaget,slutPris));


            }
            System.out.println(ordreList);
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl " +  e.getMessage());
        }
        return ordreList;
    }



    public static void createOrder(int userId, ConnectionPool connectionPool, Ordre order) throws DatabaseException {
        String sql = "INSERT INTO ordre (user_id, dato, længde, bredde, status_id) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userId); // Set user_id in the SQL statement
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setInt(3, order.getLængde());
            ps.setInt(4, order.getBredde());
            ps.setInt(5, 5);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved oprettelse af ordre: " + e.getMessage());
        }
    }

}
