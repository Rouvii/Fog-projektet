package app.mappers;

import app.entities.ConnectionPool;
import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou, Daniel Rouvillain, Mads Oliver Rosengren
 */
public class OrdreMapper {



    public static List<Order> getAllOrders (ConnectionPool connectionPool) {

        List<Order> orderList = new ArrayList<>();
        String sql = "select order_id,user_id,dato,bredde,længde,s.status_id,s.betalt,s.afsendt,s.afvist,s.modtaget,o.total_pris " +
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
                int status = rs.getInt("status_id");
                boolean betalt = rs.getBoolean("betalt");
                boolean afsendt = rs.getBoolean("afsendt");
                boolean afvist = rs.getBoolean("afvist");
                boolean modtaget = rs.getBoolean("modtaget");
                int totalPris = rs.getInt("total_pris");

                orderList.add(new Order(orderId,userId,dato,status,længde,bredde,betalt,afsendt,afvist,modtaget,totalPris));
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderList;


    }

    public static List<Order> getAllOrdersPerUser(int userId, ConnectionPool connectionPool) throws DatabaseException
    {

        List<Order> ordreList = new ArrayList<>();
        String sql = "select order_id,user_id,dato,bredde,længde,s.betalt,s.afsendt,s.afvist,s.modtaget,total_pris " +
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
                int totalPris = rs.getInt("total_pris");
                ordreList.add(new Order(id,userId,dato,længde,bredde,betalt,afsendt,afvist,modtaget,totalPris));


            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl " +  e.getMessage());
        }
        return ordreList;
    }


    public static Order getOrderByOrdreId(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from ordre where order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                Date dato = rs.getDate("dato");
                int længde = rs.getInt("længde");
                int bredde = rs.getInt("bredde");
                int status = rs.getInt("status_id");
                int totalPris = rs.getInt("total_pris");

                return new Order(orderId, userId, dato, længde, bredde,status,totalPris);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af ordre: " + e.getMessage());
        }

        throw new DatabaseException("Ordre ikke fundet");
    }




    public static int createOrder(int userId, ConnectionPool connectionPool, Order order) throws DatabaseException {
        String sql = "INSERT INTO ordre (user_id, dato, længde, bredde, status_id,total_pris) VALUES (?, ?, ?, ?, ?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setInt(3, order.getLængde());
            ps.setInt(4, order.getBredde());
            ps.setInt(5, 5);
            ps.setInt(6, order.getTotalPris());

            ps.executeUpdate();


            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new DatabaseException("Fejl ved oprettelse af ordre, ingen ID fundet.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved oprettelse af ordre: " + e.getMessage());
        }
    }

    public static void updateStatus(int ordreId, int statusId, ConnectionPool connectionPool) {

        String sql = "UPDATE ordre SET status_id = ? WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, statusId);
            ps.setInt(2, ordreId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteOrder(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from ordre where order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();

                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved sletning af ordre");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved sletning ", e.getMessage());
        }
    }


    public static int getLenghtById(int orderId, ConnectionPool connectionPool) {
        String sql = "SELECT længde FROM ordre WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int længde = rs.getInt("længde");
                return længde;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static int getBreddeById(int orderId,ConnectionPool connectionPool){
        String sql = "SELECT bredde FROM ordre WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int bredde = rs.getInt("bredde");
                return bredde;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
   return 0;
    }


}
