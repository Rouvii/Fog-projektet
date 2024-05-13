package app.controllers;

import app.entities.ConnectionPool;
import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.mappers.OrdreMapper;
import app.services.CarportSvg;
import app.services.Svg;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Date;
import java.util.List;
import java.util.Locale;

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
        app.get("orders", ctx -> userOrderPage(ctx, connectionPool));
        app.get("showOrder", ctx -> OrdreController.showOrder(ctx, connectionPool));

    }

    private static void finalDesignPage(Context ctx, ConnectionPool connectionPool) {

        try{
            User user = ctx.sessionAttribute("currentUser");
            String lengthStr = ctx.formParam("length");
            String widthStr = ctx.formParam("width");
            if (lengthStr != null && widthStr != null) {
                int length = Integer.valueOf(lengthStr);
                int width = Integer.valueOf(widthStr);
                ctx.sessionAttribute("length", length);
                ctx.sessionAttribute("width", width);
            }
            ctx.render("finalDesign.html");
        } catch (Exception e) {
            System.out.println("Error rendering finalDesign.html: " + e.getMessage());
            e.printStackTrace();
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
            Order order = new Order(length, width);
            OrdreMapper.createOrder(userId, connectionPool, order);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void userOrderPage(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");
        try {

            List<Order> ordreList = OrdreMapper.getAllOrdersPerUser(user.getUserId(), connectionPool);

            ctx.attribute("ordreList", ordreList);

            ctx.render("orders.html");

        } catch (Exception e) {

            System.out.println("fejl");
        }


    }
    public static void showOrder(Context ctx, ConnectionPool connectionPool){
        Locale.setDefault(new Locale("US"));
        CarportSvg svg= new CarportSvg(600,780);



        ctx.attribute("svg",svg.toString());
        ctx.render("showOrder.html");
    }

}
