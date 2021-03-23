package com.mersiades.awcweb.graphql;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.services.*;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private final GameRoleService gameRoleService;
    private final GameService gameService;
    private final MoveService moveService;
    private final PlaybookService playbookService;
    private final PlaybookCreatorService playbookCreatorService;
    private final VehicleCreatorService vehicleCreatorService;
    private final ThreatCreatorService threatCreatorService;

    public Query(
                 GameRoleService gameRoleService,
                 GameService gameService,
                 MoveService moveService,
                 PlaybookService playbookService,
                 PlaybookCreatorService playbookCreatorService,
                 VehicleCreatorService vehicleCreatorService,
                 ThreatCreatorService threatCreatorService) {
        this.gameRoleService = gameRoleService;
        this.gameService = gameService;
        this.moveService = moveService;
        this.playbookService = playbookService;
        this.playbookCreatorService = playbookCreatorService;
        this.vehicleCreatorService = vehicleCreatorService;
        this.threatCreatorService = threatCreatorService;
    }

    @CrossOrigin
    public List<GameRole> gameRolesByUserId(String id) {
        System.out.println("Fetching GameRoles for user: " + id);
        return gameRoleService.findAllByUserId(id);
    }

    public Game game(String gameId) {
        System.out.println("Fetching Game by id: " + gameId);
        return gameService.findById(gameId);
    }

    public Game gameForPlayer(String gameId, String userId) {
        System.out.println("Fetching Game for player: " + gameId);

        // Get the Game
        Game game = gameService.findById(gameId);

        // Get the User's GameRole from the Game
        assert game != null;
        GameRole usersGameRole = game.getGameRoles().stream().filter(gameRole -> gameRole.getUser().getId().equals(userId)).findFirst().orElseThrow();

        // Remove all GameRoles
        game.getGameRoles().clear();

        // Reinstate User's GameRole
        game.getGameRoles().add(usersGameRole);

        // Return the game, but with only the User's (player) GameRole
        return game;
    }

    public List<Game> gamesForInvitee(String email) {
        System.out.println("Fetching Games for invitee: " + email);
        return gameService.findAllByInvitee(email);
    }

    public List<Move> allMoves() {
        return moveService.findAll();
    }

    public List<Playbook> playbooks() {
        return playbookService.findAll();
    }

    public Playbook playbook(PlaybookType playbookType) {
        return playbookService.findByPlaybookType(playbookType);
    }

    public PlaybookCreator playbookCreator(PlaybookType playbookType) {
        return playbookCreatorService.findByPlaybookType(playbookType);
    }

    public VehicleCreator vehicleCreator() {
        return vehicleCreatorService.findAll().get(0);
    }

    public ThreatCreator threatCreator() {
        return threatCreatorService.findAll().get(0);
    }
}
