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


}
