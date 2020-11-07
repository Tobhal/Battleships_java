package com.company;


import com.company.controller.GameController;
import com.company.reposity.GameRepository;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;
import org.jetbrains.annotations.NotNull;

public class Application {
    public static void main(String[] args) {

        Javalin app = Javalin.create().start();
        app.config.enableWebjars();
        app.before("/", ctx -> ctx.redirect("/main-view"));

        // Pages
        app.get("/main-view", new VueComponent("main-view"));
        app.get("/lobby/:lobbyID", new VueComponent("lobby-view"));
        app.get("/lobby/:lobbyName", new VueComponent("lobby-view"));

        app.get("test", new VueComponent("Test"));

        // Controller
        GameController gameController = new GameController(new GameRepository());

        // API
        app.get("/api/lobbies", gameController::getLobbies);
        app.get("/api/lobby/:lobbyID", gameController::getLobbyID);
        app.get("/api/lobby/:lobbyName", gameController::getLobbyName);


    }
}
/*

 */