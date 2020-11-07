package com.company.model.file;

import com.company.model.lobby.Lobby;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RWFile {

    public static void read(String filePath, HashMap<String, Lobby> lobbyHashMap) {
        File file = new File(filePath);

        ObjectMapper mapper = new ObjectMapper();
        Lobby[] lobbyMapper = new Lobby[0];

        try {
            lobbyMapper = mapper.readValue(file, Lobby[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Lobby> lobbies = new ArrayList<>(Arrays.asList(lobbyMapper));

        for (Lobby lobby : lobbies) {
            lobbyHashMap.put(lobby.getName(), lobby);
        }
    }

    public static void save(String savePath, HashMap<String, Lobby> lobbyHashMap) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File(savePath), new ArrayList<>(lobbyHashMap.values()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
