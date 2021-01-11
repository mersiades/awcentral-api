package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.Look;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.repositories.GameRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    @Value("${spring.profiles.active:}")
    private String activeProfiles;

    private final GameRepository gameRepository;
    private final UserService userService;
    private final GameRoleService gameRoleService;
    private final CharacterService characterService;

    public GameServiceImpl(GameRepository gameRepository, UserService userService, GameRoleService gameRoleService, CharacterService characterService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.gameRoleService = gameRoleService;
        this.characterService = characterService;
    }

    @Override
    public Flux<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Mono<Game> findById(String id) {
        return gameRepository.findById(id);
    }

    @Override
    public Mono<Game> save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Flux<Game> saveAll(Flux<Game> games) {
        return gameRepository.saveAll(games);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game).log().block();
    }

    @Override
    public void deleteById(String id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Mono<Game> findByGameRoles(GameRole gameRole) {
        return gameRepository.findByGameRoles(gameRole);
    }

    @Override
    public Game createGameWithMC(String userId, String displayName, String email, String name) throws Exception {
        // Create the new game
        Game newGame = Game.builder().id(UUID.randomUUID().toString()).name(name).hasFinishedPreGame(false).build();

        User creator = userService.findOrCreateUser(userId, displayName, email);

        // Create an MC GameRole for the Game creator and add it to the Game
        GameRole mcGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(Roles.MC).build();
        newGame.getGameRoles().add(mcGameRole);
        newGame.setMc(creator);
        Game savedGame = gameRepository.save(newGame).block();

        assert creator != null;
        userService.addGameroleToUser(creator.getId(), mcGameRole);
        // Add the Game and User to the MC's GameRole
        mcGameRole.setGame(savedGame);
        mcGameRole.setUser(creator);
        gameRoleService.save(mcGameRole).block();

        return newGame;
    }

    @Override
    public Game addUserToGame(String gameId, String userId, String displayName, String email) throws Exception {
        User user = userService.findOrCreateUser(userId, displayName, email);

        // Create Player Gamerole for user
        GameRole gameRole = GameRole.builder().id(UUID.randomUUID().toString())
                .role(Roles.PLAYER)
                .build();

        Game game = gameRepository.findById(gameId).block();

        assert game != null;
        game.getGameRoles().add(gameRole);
        game.getPlayers().add(user);
        game.getInvitees().remove(email);
        gameRepository.save(game).block();

        assert user != null;
        userService.addGameroleToUser(user.getId(), gameRole);

        gameRole.setUser(user);
        gameRole.setGame(game);
        gameRoleService.save(gameRole).block();
        return game;
    }

    @Override
    public Game findAndDeleteById(String gameId) {
        Game game = gameRepository.findById(gameId).blockOptional().orElseThrow(NoSuchElementException::new);

        // Remove Gameroles from mc and players
        userService.removeGameroleFromUser(game.getMc().getId(), gameId);
        game.getPlayers().forEach(player -> userService.removeGameroleFromUser(player.getId(), gameId));

        // Delete Gameroles
        game.getGameRoles().forEach(gameRoleService::delete);

        // Delete Game
        this.delete(game);

        return game;
    }

    @Override
    public Game addInvitee(String gameId, String email) {
        Game game = findById(gameId).blockOptional().orElseThrow(NoSuchElementException::new);
        game.getInvitees().add(email);
        gameRepository.save(game).block();
        return game;
    }

    @Override
    public Game addCommsApp(String gameId, String app) {
        Game game = findById(gameId).blockOptional().orElseThrow(NoSuchElementException::new);
        game.setCommsApp(app);
        gameRepository.save(game).block();
        return game;
    }

    @Override
    public Game addCommsUrl(String gameId, String url) {
        Game game = findById(gameId).blockOptional().orElseThrow(NoSuchElementException::new);
        game.setCommsUrl(url);
        gameRepository.save(game).block();
        return game;
    }

    @Override
    public Game removeInvitee(String gameId, String email) {
        Game game = findById(gameId).blockOptional().orElseThrow(NoSuchElementException::new);
        game.getInvitees().remove(email);
        gameRepository.save(game).block();
        return game;
    }

    @Override
    public Flux<Game> findAllByInvitee(String email) {
        System.out.println("activeProfiles = " + activeProfiles);
        // Add a demo game if running demo profile
        if (activeProfiles != null && activeProfiles.equals("demo")) {
            GameRole nateGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(Roles.MC).build();
            GameRole claireGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(Roles.PLAYER).build();
            GameRole ruthGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(Roles.PLAYER).build();

            User nate = User.builder().id(UUID.randomUUID().toString())
                    .email("nate@email.com").displayName("Nate").gameRoles(List.of(nateGameRole)).build();
            User claire = User.builder().id(UUID.randomUUID().toString())
                    .email("claire@email.com").displayName("Claire").gameRoles(List.of(claireGameRole)).build();
            User ruth = User.builder().id(UUID.randomUUID().toString())
                    .email("ruth@email.com").displayName("Ruth").gameRoles(List.of(ruthGameRole)).build();

            Look angel2 = new Look(Playbooks.ANGEL, LookCategories.GENDER, "woman");
            Look angel6 = new Look(Playbooks.ANGEL, LookCategories.CLOTHES, "utility wear");
            Look angel10 = new Look(Playbooks.ANGEL, LookCategories.FACE, "strong face");
            Look angel15 = new Look(Playbooks.ANGEL, LookCategories.EYES, "quick eyes");
            Look angel21 = new Look(Playbooks.ANGEL, LookCategories.BODY, "compact body");

            Character claireChar = Character.builder()
                    .id(UUID.randomUUID().toString())
                    .playbook(Playbooks.ANGEL)
                    .looks(List.of(angel2, angel6, angel10, angel15, angel21))
                    .name("Nee")
                    .build();

            Look brainer3 = new Look(Playbooks.BRAINER, LookCategories.GENDER, "ambiguous");
            Look brainer6 = Look.builder().playbookType(Playbooks.BRAINER)
                    .category(LookCategories.CLOTHES).look("high formal wear").build();
            Look brainer12 = Look.builder().playbookType(Playbooks.BRAINER)
                    .category(LookCategories.FACE).look("pale face").build();
            Look brainer17 = Look.builder().playbookType(Playbooks.BRAINER)
                    .category(LookCategories.EYES).look("dead eyes").build();
            Look brainer23 = Look.builder().playbookType(Playbooks.BRAINER)
                    .category(LookCategories.BODY).look("awkward angular body").build();

            Character ruthChar = Character.builder()
                    .id(UUID.randomUUID().toString())
                    .playbook(Playbooks.BRAINER)
                    .looks(List.of(brainer3, brainer6, brainer12, brainer17, brainer23))
                    .name("Jackson")
                    .build();

            nateGameRole.setUser(nate);
            claireGameRole.setUser(claire);
            claireGameRole.setCharacters(List.of(claireChar));
            ruthGameRole.setUser(ruth);
            ruthGameRole.setCharacters(List.of(ruthChar));
            userService.saveAll(Flux.just(nate, claire, ruth)).log().blockLast();
            gameRoleService.saveAll(Flux.just(nateGameRole, claireGameRole, ruthGameRole)).blockLast();
            characterService.saveAll(Flux.just(claireChar, ruthChar)).log().blockLast();

            return gameRepository.findAllByInviteesContaining(email).log("Games found: ")
                    .defaultIfEmpty(Game.builder()
                            .name("Demo game")
                            .players(List.of(claire, ruth))
                            .mc(nate)
                            .gameRoles(List.of(nateGameRole, claireGameRole, ruthGameRole))
                            .commsApp("Discord")
                            .commsUrl("https://discord.com/not-a-real-discord-channel")
                            .build())
                    .flatMap(gameRepository::save).log();
        } else {
            return gameRepository.findAllByInviteesContaining(email);
        }
    }

    @Override
    public Mono<Game> finishPreGame(String gameId) {
        return findById(gameId).map(game -> {
            game.setHasFinishedPreGame(true);
            return game;
        }).flatMap(gameRepository::save);
    }

}
