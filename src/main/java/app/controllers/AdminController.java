package app.controllers;

import app.entities.ConnectionPool;
import app.entities.Materialer;
import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.mappers.MaterialeMapper;
import app.mappers.OrderlineMapper;
import app.mappers.OrdreMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * Purpose:
 *
 * @author: Daniel Rouvillain
 */
public class AdminController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("admin", ctx -> adminpage(ctx, connectionPool));
        app.get("adminrediger", ctx -> adminpagerediger(ctx, connectionPool));
        app.post("adminrediger", ctx -> adminpagerediger(ctx, connectionPool));
        app.post("redigermateriale", ctx -> redigermateriale(ctx, connectionPool));
        app.post("updatename", ctx -> updatename(ctx, connectionPool));
        app.post("updateprice", ctx -> updateprice(ctx, connectionPool));
        app.get("adminordre", ctx -> adminordrepage(ctx, connectionPool));
        app.post("addmateriale", ctx -> addmateriale(ctx, connectionPool));
        app.post("adminaddmateriale", ctx -> adminaddmaterialepage(ctx, connectionPool));
        app.post("deletemateriale", ctx -> deletemateriale(ctx, connectionPool));
        app.post("updatestatus", ctx -> updatestatus(ctx, connectionPool));
        app.post("deleteorder", ctx -> deleteOrder(ctx, connectionPool));


    }


    private static void adminpage(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");
        try {

            List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

            ctx.attribute("materialerList", materialerList);

            ctx.render("admin.html");

        } catch (Exception e) {
            ctx.render("admin.html");
            System.out.println("fejl");
        }


    }


    private static void adminpagerediger(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");
        try {

            List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

            ctx.attribute("materialeList", materialerList);

            ctx.render("adminrediger.html");

        } catch (Exception e) {
            ctx.render("admin.html");
            System.out.println("fejl");
        }


    }


    private static void updatename(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        try {
            int materialeId = Integer.parseInt(ctx.formParam("materialeId"));
            String type = ctx.formParam("name");

            MaterialeMapper.updateName(materialeId, type, connectionPool);

            List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

            ctx.attribute("materialeList", materialerList);
            ctx.render("adminrediger.html");

        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("admin.html");
        }
    }


    private static void updateprice(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        try {
            int materialeId = Integer.parseInt(ctx.formParam("materialeId"));
            double materialepris = Double.parseDouble(ctx.formParam("pris"));

            MaterialeMapper.updatePrice(materialeId, materialepris, connectionPool);

            List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

            ctx.attribute("materialeList", materialerList);
            ctx.render("adminrediger.html");

        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("admin.html");
        }
    }


    private static void redigermateriale(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        try {
            int materialeId = Integer.parseInt(ctx.formParam("materialeId"));

            Materialer materialer = MaterialeMapper.getMaterialeById(materialeId, connectionPool);

            ctx.attribute("materialer", materialer);
            ctx.render("adminredigermateriale.html");

        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("admin.html");
        }
    }


    private static void adminordrepage(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");
        try {

            List<Order> ordreList = OrdreMapper.getAllOrders(connectionPool);

            ctx.attribute("ordrelist", ordreList);

            ctx.render("adminordre.html");

        } catch (Exception e) {
            ctx.render("admin.html");
            System.out.println("fejl");
        }


    }

    private static void addmateriale(Context ctx, ConnectionPool connectionPool) {

        User user = ctx.sessionAttribute("currentUser");
        try {
            String type = ctx.formParam("name");
            int pris = Integer.parseInt(ctx.formParam("pris"));
            MaterialeMapper.addMateriale(type, pris, connectionPool);

            List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

            ctx.attribute("materialeList", materialerList);
            ctx.render("adminrediger.html");
        } catch (DatabaseException e) {
            ctx.render("admin.html");
        }
    }

    private static void adminaddmaterialepage(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");
        try {


            ctx.render("adminaddmateriale.html");

        } catch (Exception e) {
            ctx.render("admin.html");
            System.out.println("fejl");
        }


    }


    public static void deletemateriale(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        try {
            int materialeId = Integer.parseInt(ctx.formParam("materialeId"));

            MaterialeMapper.deleteMateriale(materialeId, connectionPool);

            List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

            ctx.attribute("materialeList", materialerList);
            ctx.render("adminrediger.html");

        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("admin.html");
        }

    }


    public static void updatestatus(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        try {
            int ordreId = Integer.parseInt(ctx.formParam("orderid"));
            int statusId = Integer.parseInt(ctx.formParam("statusid"));

            OrdreMapper.updateStatus(ordreId, statusId, connectionPool);

            List<Order> ordreList = OrdreMapper.getAllOrders(connectionPool);

            ctx.attribute("ordrelist", ordreList);
            ctx.render("adminordre.html");

        } catch (Exception e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("admin.html");
        }

    }


    public static void deleteOrder(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        try {

            int orderId = Integer.parseInt(ctx.formParam("ordreId"));
            if (OrderlineMapper.countOrderlines(orderId, connectionPool) > 0) {
                OrderlineMapper.deleteOrderline(orderId, connectionPool);
            }
            OrdreMapper.deleteOrder(orderId, connectionPool);

            List<Order> orderList = OrdreMapper.getAllOrders(connectionPool);

            ctx.attribute("ordrelist", orderList);
            ctx.render("adminordre.html");

        } catch (DatabaseException e) {

            ctx.attribute("message", e.getMessage());
            ctx.render("admin.html");
        }

    }


}
