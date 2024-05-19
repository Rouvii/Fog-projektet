package app.mappers;

import app.entities.ConnectionPool;
import app.entities.Materialer;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class MaterialeMapper {


    public static List<Materialer> getAllMaterialer(ConnectionPool connectionPool) {

        List<Materialer> materialerList = new ArrayList<>();
        String sql = "select * from materialer order by materiale_id ";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)

        ) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int materialeId = rs.getInt("materiale_id");
                String type = rs.getString("type");
                int price = rs.getInt("pris_pr_meter");

                materialerList.add(new Materialer(materialeId, type, price));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return materialerList;


    }

    public static void updateName(int materialeId, String type, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "update materialer set type = ? where materiale_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, type);
            ps.setInt(2, materialeId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en task");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af en task", e.getMessage());
        }
    }


    public static void updatePrice(int materialeId, double materialePris, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "update materialer set pris_pr_meter = ? where materiale_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setDouble(1, materialePris);
            ps.setInt(2, materialeId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en task");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af en task", e.getMessage());
        }
    }

    public static Materialer getMaterialeById(int materialeId, ConnectionPool connectionPool) throws DatabaseException {
        Materialer materialer = null;

        String sql = "select * from materialer where materiale_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, materialeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("materiale_id");
                String type = rs.getString("type");
                int pris = rs.getInt("pris_pr_meter");
                materialer = new Materialer(id, type, pris);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af task med id = " + materialeId, e.getMessage());
        }
        return materialer;
    }


    public static void addMateriale(String type, int pris, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into materialer (type, pris_pr_meter) values (?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, type);
            ps.setInt(2, pris);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af materiale");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved oprettelse ", e.getMessage());

        }
    }
    public static void deleteMateriale(int materialeId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from materialer where materiale_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, materialeId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved sletning af materiale");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved sletning ", e.getMessage());
        }
    }

    public static int getPricePerMeterFromMaterialeId(int materialeId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT pris_pr_meter FROM materialer WHERE materiale_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, materialeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("pris_pr_meter");
            } else {
                throw new DatabaseException("Ingen materiale fundet med ID = " + materialeId);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af pris pr. meter: " + e.getMessage());
        }
    }



    }

