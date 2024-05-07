package app.controllers;

import app.entities.ConnectionPool;
import app.entities.Ordre;
import app.entities.User;
import app.mappers.OrdreMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou, Daniel Rouvillain
 */
public class OrdreController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("design", ctx -> designPage(ctx, connectionPool));
        app.get("orders",ctx -> myorders(ctx,connectionPool));

    }

    public static void designPage(Context ctx,ConnectionPool connectionPool){

        //User user =ctx.sessionAttribute("currentUser");


        try{


            ctx.render("/design.html");


        }catch (Exception e){

        }
    }




public static void myorders(Context ctx, ConnectionPool connectionPool){

    User user = ctx.sessionAttribute("currentUser");

    try {
        List<Ordre> ordreList = OrdreMapper.getAllOrdersPerUser(user.getUserId(),connectionPool);
        ctx.attribute("ordreList",ordreList);
        ctx.render("orders.html");
    } catch (Exception e) {
        ctx.attribute("message",e.getMessage());
        ctx.render("index.html");
    }

}


}
