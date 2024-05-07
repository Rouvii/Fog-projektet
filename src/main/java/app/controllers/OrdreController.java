package app.controllers;

import app.entities.ConnectionPool;
import app.entities.User;
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
        app.get("/finalDesign", ctx -> finalDesignPage(ctx,connectionPool));
        app.post("/createOrder", ctx -> placeOrdre(ctx,connectionPool));

    }

    private static void finalDesignPage(Context ctx, ConnectionPool connectionPool) {


        try{


            ctx.render("/finalDesign.html");


        }catch (Exception e){

        }




    }

    public static void designPage(Context ctx,ConnectionPool connectionPool){

        //User user =ctx.sessionAttribute("currentUser");


        try{


            ctx.render("/design.html");


        }catch (Exception e){

        }
    }



    public static void placeOrdre (Context ctx, ConnectionPool connectionPool){

        double længde = ctx.formParam("length", Double.class).value();
        double bredde = ctx.formParam("width", Double.class).value();




    }


}
