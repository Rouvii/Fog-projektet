package app.controllers;

import app.entities.ConnectionPool;
import app.entities.User;
import io.javalin.Javalin;
import io.javalin.http.Context;
/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class OrdreController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("design", ctx -> designPage(ctx, connectionPool));


    }

    public static void designPage(Context ctx,ConnectionPool connectionPool){

        //User user =ctx.sessionAttribute("currentUser");


        try{


            ctx.render("/design.html");


        }catch (Exception e){

        }
    }




}
