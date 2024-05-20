package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.mappers.OrderlineMapper;
import app.mappers.OrdreMapper;
import app.services.Calculator;
import app.services.CarportSvg;
import app.services.Svg;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.mappers.VariantMapper;

import java.sql.Date;
import java.util.List;
import java.util.Locale;


public class OrdreController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("design", ctx -> designPage(ctx, connectionPool));
        app.get("finalDesign", ctx -> finalDesignPage(ctx, connectionPool));
        app.post("finalDesign", ctx -> finalDesignPage(ctx, connectionPool));
        app.post("/createOrder", ctx -> placeOrdre(ctx, connectionPool));
        app.get("orders", ctx -> userOrderPage(ctx, connectionPool));
        app.get("showOrder", ctx -> OrdreController.showOrder(ctx, connectionPool));
        app.get("checkout",ctx ->checkout(ctx,connectionPool));

        app.get("/error", ctx -> ctx.render("error.html"));

    }

    private static void finalDesignPage(Context ctx, ConnectionPool connectionPool) {

        try {
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
            ctx.sessionAttribute("errorMessage", "Error rendering finalDesign.html: " + e.getMessage());
            ctx.redirect("/error");
        }
    }


    public static void designPage(Context ctx, ConnectionPool connectionPool) {

        User user = ctx.sessionAttribute("currentUser");

        try {
            ctx.render("design.html");

        } catch (Exception e) {
            ctx.sessionAttribute("errorMessage", "Error rendering design.html: " + e.getMessage());
            ctx.redirect("/error");
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
            Calculator calculator = new Calculator(width, length, connectionPool);
            calculator.calcCarport();
            System.out.println("Total price: " + calculator.getTotalPrice());
            Order order = new Order(length, width);
            OrdreMapper.createOrder(userId, connectionPool, order);

        } catch (Exception e) {
            ctx.sessionAttribute("errorMessage", "Error placing order: " + e.getMessage());
            ctx.redirect("/error");
        }
    }

    private static void userOrderPage(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        if (user == null) {
            ctx.redirect("/login");
            return;
        }
        try {

            List<Order> ordreList = OrdreMapper.getAllOrdersPerUser(user.getUserId(), connectionPool);
            ctx.attribute("ordreList", ordreList);
            ctx.render("orders.html");

        } catch (Exception e) {
            ctx.sessionAttribute("errorMessage", "Error retrieving orders: " + e.getMessage());
            ctx.redirect("/error");
        }


    }

    public static void showOrder(Context ctx, ConnectionPool connectionPool) {

        try {
            Locale.setDefault(new Locale("US"));
            int length = ctx.sessionAttribute("length");
            int width = ctx.sessionAttribute("width");

            CarportSvg carportSvg = new CarportSvg(width, length);
            Svg outerSvg = new Svg(0, 0, "0 0 1000 1000", "100%", "auto");

            int arrowOffset = 15;
            int rotation = 90;

            outerSvg.addArrow(width + arrowOffset, length, width + arrowOffset, arrowOffset, "stroke-width:1px;stroke:#000000;fill:#ffffff");
            outerSvg.addArrow(width, length + arrowOffset, arrowOffset, length + arrowOffset, "stroke-width:1px;stroke:#000000;fill:#ffffff");
            outerSvg.addText(width / 2 + arrowOffset, length + arrowOffset + 10, 0, width + " cm");
            outerSvg.addText(width + arrowOffset, length / 2 + arrowOffset + 10, rotation, length + " cm");


            String combined = outerSvg.addSvg(carportSvg.getCarportSvg()).toString();


            ctx.attribute("length", length);
            ctx.attribute("width", width);
            ctx.attribute("svg", combined);
            ctx.render("showOrder.html");
        } catch (Exception e) {
            ctx.sessionAttribute("errorMessage", "Error showing order: " + e.getMessage());
            ctx.redirect("/error");
        }
    }


    public static void createOrderAndInsertOrderLines(Context ctx, ConnectionPool connectionPool) {
        User currentUser = ctx.sessionAttribute("currentUser");
        int userId = currentUser.getUserId();

        try {
            int length = Integer.valueOf(ctx.formParam("length"));
            int width = Integer.valueOf(ctx.formParam("width"));
            ctx.sessionAttribute("length", length);
            ctx.sessionAttribute("width", width);
            Calculator calculator = new Calculator(width, length, connectionPool);
            calculator.calcCarport();
            ctx.sessionAttribute("calculator", calculator);
            System.out.println("Total price: " + calculator.getTotalPrice());
            Order order = new Order(length, width);
            int orderId = OrdreMapper.createOrder(userId, connectionPool, order);
            ctx.sessionAttribute("orderId", orderId);

            for (OrderLine orderLine : calculator.getOrderLines()) {

                Variant variant = VariantMapper.findVariantForOrderLine(orderLine,connectionPool);
                try {
                    OrderlineMapper.createOrderLine(orderId, connectionPool, orderLine, variant);
                } catch (DatabaseException e) {

                    System.err.println("Fejl ved indsættelse af OrderLine i databasen: " + e.getMessage());
                }
            }

            ctx.redirect("/showOrder");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void checkout(Context ctx, ConnectionPool connectionPool) {
        User currentUser = ctx.sessionAttribute("currentUser");
        int userId = currentUser.getUserId();

        try {
            // Hent orderId fra sessionen
            int orderId = ctx.sessionAttribute("orderId");
            List<OrderLine> orderLines = OrderlineMapper.getOrderlinesForOrder(orderId, connectionPool);
            Calculator calculator = ctx.sessionAttribute("calculator");
            ctx.attribute("orderLines", orderLines);
            ctx.attribute("calculator",calculator);
            ctx.render("checkout.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Der opstod en fejl: " + e.getMessage());
            ctx.render("error.html");
        }
    }

}
