package app.controllers;

import app.entities.ConnectionPool;
import app.entities.Materialer;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.mappers.MaterialeMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou, Daniel Rouvillain
 */
public class AdminController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("admin", ctx -> adminpage(ctx, connectionPool));
        app.get("adminrediger", ctx -> adminpagerediger(ctx,connectionPool));
        app.post("adminrediger", ctx -> adminpagerediger(ctx, connectionPool));
        app.post("redigermateriale", ctx ->redigermateriale(ctx,connectionPool));
        app.post("updatename", ctx -> updatename(ctx, connectionPool));
        app.post("updateprice", ctx -> updateprice(ctx,connectionPool));
    }


    private static void adminpage(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");
        try {

            List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

            ctx.attribute("materialerList", materialerList);

            ctx.render("/admin.html");

        } catch (Exception e) {

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
            ctx.render("index.html");
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
            ctx.render("index.html");
        }
    }


/*
    private static void updateprice(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        try {
            int materialeId = Integer.parseInt(ctx.formParam("materialeId"));
            String materialeprisParam = ctx.formParam("materialepris");

            if (materialeprisParam != null && !materialeprisParam.isEmpty()) {
                double materialepris = Double.parseDouble(materialeprisParam);

                MaterialeMapper.updatePrice(materialeId, materialepris, connectionPool);

                List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

                ctx.attribute("materialeList", materialerList);
                ctx.render("adminrediger.html");
            } else {
                // Handle case where materialepris parameter is null or empty
                ctx.attribute("message", "materialepris parameter is missing or empty");
                ctx.render("index.html");
            }

        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }
*/
    private static void redigermateriale(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        try {
            int materialeId = Integer.parseInt(ctx.formParam("materialeId"));

            Materialer materialer = MaterialeMapper.getMaterialeById(materialeId, connectionPool);

            ctx.attribute("materialer",materialer);
            ctx.render("adminredigermateriale.html");

        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }

}
