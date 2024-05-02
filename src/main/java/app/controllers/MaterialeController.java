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
 * @author Daniel Rouvillain
 */

public class MaterialeController {
/*
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.post("updatetask", ctx -> updatetask(ctx, connectionPool));
    }




    private static void updatetask(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        try {
            int materialeId = Integer.parseInt(ctx.formParam("materialeId"));
            double price = Double.parseDouble(ctx.formParam("materialepris"));

           // MaterialeMapper.update(materialeId, pris, connectionPool);

           // List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

            ctx.attribute("materialeList", materialerList);
            ctx.render("adminrediger.html");

        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }
    */

}
