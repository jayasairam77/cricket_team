/*
 * 
 * You can use the following import statemets
 * 
 * import org.springframework.web.bind.annotation.*;
 * import java.util.*;
 * 
 */
package com.example.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.player.service.*;
import com.example.player.model.*;

@RestController
public class PlayerController {
    @Autowired
    public PlayerH2Service pservice;

    @DeleteMapping("/players/{playerId}")
    public void deletePlayer(@PathVariable("playerId") int playerId) {
        pservice.deletePlayer(playerId);
    }

    @PutMapping("/players/{playerId}")
    public Player updatePlayer(@PathVariable("playerId") int playerId, @RequestBody Player player) {
        return pservice.updatePlayer(playerId, player);
    }

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player player) {
        return pservice.addPlayer(player);
    }

    @GetMapping("/players/{playerId}")
    public Player getPlayerById(@PathVariable("playerId") int playerId) {
        return pservice.getPlayerById(playerId);
    }

    @GetMapping("/players")
    public ArrayList<Player> getPlayers() {
        return pservice.getPlayers();
    }

}