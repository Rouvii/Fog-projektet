package app.mappers;
import app.entities.ConnectionPool;
import app.entities.User;
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
 * @author: Kevin Løvstad Schou, Matthias Sigurdsson
 */
public class UserMapper {

    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from users where email=? and password=? ";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                boolean admin = rs.getBoolean("admin");
                return new User(id, email, password, admin);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (Exception e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }




    public static void createuser(String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {

        String sql = "insert into users (email, password) values (?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }

    public static void insertUserDetails(int userId, String fornavn, String efternavn, String adresse, String telefon, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE users SET fornavn = ?, efternavn = ?, adresse = ?, telefon = ? WHERE user_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, fornavn);
            ps.setString(2, efternavn);
            ps.setString(3, adresse);
            ps.setString(4, telefon);
            ps.setInt(5, userId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved opdatering af bruger");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database fejl", e.getMessage());
        }
    }

}