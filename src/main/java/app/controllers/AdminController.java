package app.controllers;

import app.entities.ConnectionPool;
import app.entities.Materialer;
import app.entities.User;
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


    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("admin", ctx -> adminpage(ctx, connectionPool));
       // app.get("adminrediger",ctx -> ctx.render("adminrediger.html"));
      //  app.post("adminrediger",ctx -> adminpagerediger(ctx,connectionPool));
    }



    private static void adminpage(Context ctx, ConnectionPool connectionPool)
    {
        User user = ctx.sessionAttribute("currentUser");
        try{

            List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

            ctx.attribute("materialerList",materialerList);

            ctx.render("/admin.html");

        } catch (Exception e) {

            System.out.println("fejl");
        }


    }

    /*
    private static void adminpagerediger(Context ctx, ConnectionPool connectionPool)
    {
        User user = ctx.sessionAttribute("currentUser");
        try{

            List<Materialer> materialerList = MaterialeMapper.getAllMaterialer(connectionPool);

            ctx.attribute("materialerList",materialerList);

            ctx.render("/adminrediger.html");

        } catch (Exception e) {

            System.out.println("fejl");
        }


    }
    */

}
