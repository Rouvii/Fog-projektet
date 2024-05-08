
package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.entities.ConnectionPool;
import app.mappers.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("index", ctx -> ctx.render("index.html"));
        app.get("login", ctx -> loginpage(ctx, connectionPool));
        app.get("logout", ctx -> logout(ctx));
        app.get("createUser", ctx -> ctx.render("createUser.html"));
        app.post("createUser", ctx -> createUser(ctx, connectionPool));
        app.post("insertUserDetails", ctx -> insertUserDetails(ctx, connectionPool));
    }

    private static void createUser(Context ctx, ConnectionPool connectionPool)
    {

        // Hent form parametre
        int length = Integer.valueOf(ctx.formParam("length"));
        int width = Integer.valueOf(ctx.formParam("width"));


        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");


        if (password1.equals(password2))
        {
            try
            {
                UserMapper.createuser(username, password1, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet med brugernavn: " + username +
                        ". Nu skal du logge på.");
                ctx.render("index.html");
            }

            catch (DatabaseException e)
            {
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen, eller log ind");
                ctx.render("createuser.html");
            }
        } else
        {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("createuser.html");
        }

    }

    private static void logout(Context ctx)
    {

        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

    public static void loginpage(Context ctx, ConnectionPool connectionPool)
    {
        User user = ctx.sessionAttribute("currentUser");
        try {
            ctx.render("login.html");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    public static void login(Context ctx, ConnectionPool connectionPool)
    {
        // Hent form parametre
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String lengthStr = ctx.formParam("length");
        String widthStr = ctx.formParam("width");

        int length = 0;
        int width = 0;

        if (lengthStr != null && !lengthStr.isEmpty()) {
            length = Integer.valueOf(lengthStr);
        }

        if (widthStr != null && !widthStr.isEmpty()) {
            width = Integer.valueOf(widthStr);
        }

        // Check om bruger findes i DB med de angivne username + password
        try
        {
            User user = UserMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            ctx.attribute("message", "Du er nu logget ind");

            if ("admin".equals(user.getrole())){
                ctx.redirect("/admin");
            } else if (length == 0 && width == 0){
                ctx.redirect("/index");
            } else {
                ctx.redirect("/finalDesign");
            }
        }
        catch (DatabaseException e)
        {
            // Hvis nej, send tilbage til login side med fejl besked
            ctx.attribute("message", e.getMessage() );
            ctx.render("login.html");
        }
    }



    public static void insertUserDetails(Context ctx, ConnectionPool connectionPool)
    {
     String fornavn = ctx.formParam("fornavn");
        String efternavn = ctx.formParam("efternavn");
        String adresse = ctx.formParam("adresse");
        String telefon = ctx.formParam("telefon");


        try {
            UserMapper.insertUserDetails(fornavn, efternavn, adresse, telefon, connectionPool);

          //  ctx.render("index.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());

        }
    }
}
