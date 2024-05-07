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
        app.get("/design", ctx -> designPage(ctx, connectionPool));
        app.get("/finalDesign", ctx -> finalDesignPage(ctx,connectionPool));
        app.post("/createOrder", ctx -> placeOrdre(ctx,connectionPool));

    }

    private static void finalDesignPage(Context ctx, ConnectionPool connectionPool) {

        User user = ctx.sessionAttribute("currentUser");

        try{


            double length = Double.parseDouble(ctx.formParam("length"));
            double width = Double.parseDouble(ctx.formParam("width"));


            ctx.sessionAttribute("length", length);
            ctx.sessionAttribute("width", width);


            ctx.render("/finalDesign.html");


        }catch (Exception e){

        }




    }

    public static void designPage(Context ctx,ConnectionPool connectionPool){

        User user =ctx.sessionAttribute("currentUser");




        try{

            ctx.render("/design.html");


        }catch (Exception e){

        }
    }



    public static void placeOrdre(Context ctx, ConnectionPool connectionPool) {
        try {
            System.out.println("Længde fra form: " + ctx.formParam("length"));
            System.out.println("Bredde fra form: " + ctx.formParam("width"));

            double længde = Double.parseDouble(ctx.formParam("længde"));
            double bredde = Double.parseDouble(ctx.formParam("bredde"));


            Ordre order = new Ordre(længde, bredde);


            OrdreMapper.createOrder(connectionPool, order);


            ctx.redirect("/success.html");
        } catch (Exception e) {

            ctx.status(500).result("Fejl ved oprettelse af ordre: " + e.getMessage());
        }
    }


}
