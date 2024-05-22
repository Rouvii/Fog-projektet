package app.mappers;

import app.entities.ConnectionPool;
import app.entities.Order;
import app.entities.OrderLine;

import app.entities.Variant;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou, Daniel Rouvillain
 */
public class OrderlineMapper {



    public static List<OrderLine> getOrderlinesForOrder(int orderId, ConnectionPool connectionPool) throws DatabaseException
    {
        List<OrderLine> orderlineList = new ArrayList<>();
        String sql = " SELECT n.type, v.længde, ol.quantity, ol.description, o.total_pris " +
                "FROM orderline ol " +
                "join ordre o on o.order_id = ol.order_id " +
                "join variant v on v.variant_id = ol.variant_id " +
                "join materialer n on n.materiale_id = v.materiale_id " +
                "WHERE o.order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                String type = rs.getString("type");
                int længde = rs.getInt("længde");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                int totalPris = rs.getInt("total_pris");
                orderlineList.add(new OrderLine(type,længde,quantity,description,totalPris));
            }
            return orderlineList;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl i database " +  e.getMessage());
        }
    }


    public static void createOrderLine(int orderId, ConnectionPool connectionPool, OrderLine orderLine, Variant variant) throws DatabaseException {
        String sql = "INSERT INTO orderline (order_id, variant_id, quantity, description) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ps.setInt(2, variant.getVariantId());
            ps.setInt(3, orderLine.getQuantity());
            ps.setString(4, orderLine.getDescription());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved oprettelse af ordre: " + e.getMessage());
        }
    }


    public static void deleteOrderline(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from orderline where order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();

                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 3) {
                throw new DatabaseException("Fejl ved sletning af ordre");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved sletning ", e.getMessage());
        }
    }

    public static int countOrderlines(int orderId, ConnectionPool connectionPool) {
        String sql = "SELECT COUNT(*) FROM orderline WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }



}
