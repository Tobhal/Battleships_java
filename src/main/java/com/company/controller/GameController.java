package com.company.controller;

import com.company.reposity.IGameRepository;
import io.javalin.http.Context;


public class GameController {
    private IGameRepository gameRepository;

    public GameController(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void getLobbies(Context ctx) {
        ctx.json(gameRepository.getAllLobbiesArray());
    }

    public void getLobby(Context ctx) {
        String lobby = ctx.pathParam("lobbyID");
        ctx.json(gameRepository.getLobby(lobby));
    }

    public void getPlayer(Context ctx) {
        String lobby = ctx.pathParam("lobbyID");
        String player = ctx.pathParam("playerID");
        ctx.json(gameRepository.getPlayer(lobby, player));
    }

}
