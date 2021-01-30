package com.mersiades.awcweb.graphql;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.models.Playbook;
import com.mersiades.awccontent.models.PlaybookCreator;
import com.mersiades.awccontent.models.VehicleCreator;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awccontent.services.PlaybookCreatorService;
import com.mersiades.awccontent.services.PlaybookService;
import com.mersiades.awccontent.services.VehicleCreatorService;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private final UserService userService;
    private final GameRoleService gameRoleService;
    private final GameService gameService;
    private final MoveService moveService;
    private final PlaybookService playbookService;
    private final PlaybookCreatorService playbookCreatorService;
    private final VehicleCreatorService vehicleCreatorService;

    public Query(UserService userService,
                 GameRoleService gameRoleService,
                 GameService gameService,
                 MoveService moveService,
                 PlaybookService playbookService,
                 PlaybookCreatorService playbookCreatorService,
                 VehicleCreatorService vehicleCreatorService) {
        this.userService = userService;
        this.gameRoleService = gameRoleService;
        this.gameService = gameService;
        this.moveService = moveService;
        this.playbookService = playbookService;
        this.playbookCreatorService = playbookCreatorService;
        this.vehicleCreatorService = vehicleCreatorService;
    }

    @CrossOrigin
    public List<GameRole> gameRolesByUserId(String id) {
        System.out.println("Fetching GameRoles for user: " + id);
        return gameRoleService.findAllByUserId(id).collectList().block();
    }

    public Game game(String gameId) {
        System.out.println("Fetching Game by id: " + gameId);
        return gameService.findById(gameId).block();
    }

    public Game gameWithLimit(String gameId, Integer skip, Integer limit) {
        System.out.println("Fetching Game by id: " + gameId);
        return gameService.findByIdWithLimit(gameId, skip, limit).block();
    }

    public Game gameForPlayer(String gameId, String userId) {
        System.out.println("Fetching Game for player: " + gameId);

        // Get the Game
        Game game = gameService.findById(gameId).block();

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
        return gameService.findAllByInvitee(email).collectList().block();
    }

    public List<Move> allMoves() {
        return moveService.findAll().collectList().block();
    }

    public List<Playbook> playbooks() {
        return playbookService.findAll().collectList().block();
    }

    public Playbook playbook(PlaybookType playbookType) {
        return playbookService.findByPlaybookType(playbookType).block();
    }

    public PlaybookCreator playbookCreator(PlaybookType playbookType) {
        return playbookCreatorService.findByPlaybookType(playbookType).block();
    }

    public VehicleCreator vehicleCreator() {
        return vehicleCreatorService.findAll().take(1).blockFirst();
    }
}
