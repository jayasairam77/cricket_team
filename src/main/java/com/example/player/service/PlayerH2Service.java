/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */

// Write your code here

package com.example.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import com.example.player.repository.*;
import com.example.player.model.*;

@Service
public class PlayerH2Service implements PlayerRepository {
    @Autowired
    private JdbcTemplate db;

    @Override
    public void deletePlayer(int playerId) {
        db.update("delete from team where playerId = ?", playerId);
    }

    @Override
    public Player updatePlayer(int playerId, Player player) {
        if (player.getPlayerName() != null)
            db.update("update team set playerName = ?", player.getPlayerName());
        if (player.getJerseyNumber()!= 0)
            db.update("update team set jerseyNumber = ?", player.getJerseyNumber());
        if (player.getRole() != null)
            db.update("update team set role = ?", player.getRole());
        return getPlayerById(playerId);
    }

    @Override
    public Player addPlayer(Player player) {
        db.update("insert into team(playerName,jerseyNumber,role) values(?,?,?)", player.getPlayerName(),
                player.getJerseyNumber(), player.getRole());
        Player splayer = db.queryForObject("select * from team where playerName = ? and jerseyNumber = ? and role = ?",
                new PlayerRowMapper(), player.getPlayerName(), player.getJerseyNumber(), player.getRole());
        return splayer;
    }

    @Override
    public Player getPlayerById(int playerId) {
        try {
            Player player = db.queryForObject("select * from team where playerId = ?", new PlayerRowMapper(), playerId);
            return player;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ArrayList<Player> getPlayers() {
        List<Player> playerlist = db.query("select * from team", new PlayerRowMapper());
        ArrayList<Player> players = new ArrayList<>(playerlist);
        return players;
    }
}