package app.controllers;

import app.entities.ConnectionPool;
import app.entities.Ordre;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.mappers.OrdreMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Date;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class OrdreController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("design", ctx -> designPage(ctx, connectionPool));
        app.get("finalDesign", ctx -> finalDesignPage(ctx, connectionPool));
        app.post("finalDesign", ctx -> finalDesignPage(ctx,connectionPool));
        app.post("/createOrder", ctx -> placeOrdre(ctx,connectionPool));

    }

    private static void finalDesignPage(Context ctx, ConnectionPool connectionPool) {

        User user = ctx.sessionAttribute("currentUser");

        try{

            int length = Integer.valueOf(ctx.formParam("length"));
            int width = Integer.valueOf(ctx.formParam("width"));

            ctx.sessionAttribute("length", length);
            ctx.sessionAttribute("width", width);


            ctx.render("finalDesign.html");



        }catch (NumberFormatException e){
            e.getMessage();
        }




    }

    public static void designPage(Context ctx,ConnectionPool connectionPool){

        User user =ctx.sessionAttribute("currentUser");




        try{



            ctx.render("design.html");


        }catch (Exception e){

        }
    }


    public static void placeOrdre(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");
        int userId = user.getUserId();

        try {
            int length = Integer.valueOf(ctx.formParam("length"));
            int width = Integer.valueOf(ctx.formParam("width"));
            ctx.sessionAttribute("length", length);
            ctx.sessionAttribute("width", width);
            Ordre order = new Ordre(length, width);
            OrdreMapper.createOrder(userId, connectionPool, order);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
