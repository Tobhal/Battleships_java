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

    public void getLobbyID(Context ctx) {
        String lobbyID = ctx.pathParam("lobbyID");
        ctx.json(gameRepository.getLobbyID(lobbyID));
    }

    public void getLobbyName(Context ctx) {
        String lobbyName = ctx.pathParam("lobbyName");
        ctx.json(gameRepository.getLobbyName(lobbyName));
    }

}
