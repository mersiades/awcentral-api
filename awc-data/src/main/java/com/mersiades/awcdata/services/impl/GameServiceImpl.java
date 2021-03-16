package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.enums.LookType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.enums.RoleType;
import com.mersiades.awccontent.enums.StatType;
import com.mersiades.awccontent.models.Look;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.models.RollModifier;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awcdata.enums.MessageType;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.repositories.GameRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.mersiades.awccontent.constants.MoveNames.*;
import static com.mersiades.awccontent.enums.StatType.HARD;
import static com.mersiades.awccontent.enums.StatType.HX;

@Service
public class GameServiceImpl implements GameService {

    @Value("${spring.profiles.active}")
    private String activeProfiles;

    private static final String GANG_CONTENT = "When you have a gang, you can _**sucker someone**_, _**go aggro on them**_, or make a _**battle move**_, using your gang as a weapon.\n" +
            "\n" +
            "When you do, you roll the dice and make your choices, but it’s your gang that inflicts and suffers harm, not you yourself.\n" +
            "\n" +
            "Gangs inflict and suffer harm as established, as usual: they inflict harm equal to their own harm rating, minus their enemy’s armor rating, and the suffer harm equal to their enemy’s harm rating minus their own armor. Harm = weapon - armor.\n" +
            "\n" +
            "However, if there’s a size mismatch, the bigger gang inflicts +1harm and gets +1armor for each step of size difference:\n" +
            "\n" +
            "- *Against a single person, a small gang inflicts +1harm and gets +1armor. A medium gang inflicts +2harm and gets +2armor, and a large gang inflicts +3harm and gets +3armor.*\n" +
            "- *Against a small gang, a medium gang inflicts +1harm and gets +1armor, and a large gang inflicts +2harm and gets +2armor.*\n" +
            "- *Give you something they think you want, or tell you what you want to hear.*\n" +
            "- *Against a medium gang, a large gang inflicts +1harm and gets +1armor.*\n" +
            "\n" +
            "When gang takes harm:\n" +
            "\n" +
            "_**1-harm**: a few injuries, one or two serious, no fatalities._\n" +
            "\n" +
            "_**2-harm**: many injuries, several serious, a couple of fatalities._\n" +
            "\n" +
            "_**3-harm**: widespread injuries, many serious, several fatalities._\n" +
            "\n" +
            "_**4-harm**: widespread serious injuries, many fatalities._\n" +
            "\n" +
            "_**5-harm and more**: widespread fatalities, few survivors._\n" +
            "\n" +
            "Also see the rules about a gang holding together after it takes harm.";

    private final GameRepository gameRepository;
    private final UserService userService;
    private final GameRoleService gameRoleService;
    private final CharacterService characterService;
    private final MoveService moveService;

    public GameServiceImpl(GameRepository gameRepository,
                           UserService userService,
                           GameRoleService gameRoleService,
                           CharacterService characterService,
                           MoveService moveService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.gameRoleService = gameRoleService;
        this.characterService = characterService;
        this.moveService = moveService;
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
    public Game findAndDeleteById(String gameId) {
        Game game = gameRepository.findById(gameId).blockOptional().orElseThrow(NoSuchElementException::new);

        // Remove Gameroles from mc and players
        userService.removeGameroleFromUser(game.getMc().getId(), gameId);
        game.getPlayers().forEach(player -> userService.removeGameroleFromUser(player.getId(), gameId));

        // Delete Gameroles
        game.getGameRoles().forEach(gameRoleService::delete);

        // Delete Game
        this.delete(game); // Maybe use: gameRepository.deleteById(id), or change the deleteById method to delete gameroles also

        return game;
    }

    @Override
    public Flux<Game> findAllByInvitee(String email) {
        // Add a demo game if running demo profile
        if (activeProfiles != null && (activeProfiles.equals("demo") || activeProfiles.equals("staging"))) {
            CharacterHarm harm = CharacterHarm.builder()
                    .id(UUID.randomUUID().toString())
                    .hasChangedPlaybook(false)
                    .hasComeBackHard(false)
                    .hasComeBackWeird(false)
                    .hasDied(false)
                    .isStabilized(false)
                    .value(0)
                    .build();

            GameRole nateGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(RoleType.MC).build();
            GameRole claireGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(RoleType.PLAYER).build();
            GameRole ruthGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(RoleType.PLAYER).build();

            User nate = User.builder().id(UUID.randomUUID().toString())
                    .email("nate@email.com").displayName("Nate").gameRoles(List.of(nateGameRole)).build();
            User claire = User.builder().id(UUID.randomUUID().toString())
                    .email("claire@email.com").displayName("Claire").gameRoles(List.of(claireGameRole)).build();
            User ruth = User.builder().id(UUID.randomUUID().toString())
                    .email("ruth@email.com").displayName("Ruth").gameRoles(List.of(ruthGameRole)).build();

            Look angel2 = Look.builder()
                    .id(UUID.randomUUID().toString())
                    .playbookType(PlaybookType.ANGEL)
                    .category(LookType.GENDER)
                    .look("woman")
                    .build();
            Look angel6 = Look.builder()
                    .id(UUID.randomUUID().toString())
                    .playbookType(PlaybookType.ANGEL)
                    .category(LookType.CLOTHES)
                    .look("utility wear")
                    .build();
            Look angel10 = Look.builder()
                    .id(UUID.randomUUID().toString())
                    .playbookType(PlaybookType.ANGEL)
                    .category(LookType.FACE)
                    .look("strong face")
                    .build();
            Look angel15 = Look.builder()
                    .id(UUID.randomUUID().toString())
                    .playbookType(PlaybookType.ANGEL)
                    .category(LookType.EYES)
                    .look("quick eyes")
                    .build();
            Look angel21 = Look.builder()
                    .id(UUID.randomUUID().toString())
                    .playbookType(PlaybookType.ANGEL)
                    .category(LookType.BODY)
                    .look("compact body")
                    .build();


            CharacterStat angelCool = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.COOL).value(1).isHighlighted(false).build();
            CharacterStat angelHard = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(HARD).value(0).isHighlighted(true).build();
            CharacterStat angelHot = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.HOT).value(1).isHighlighted(true).build();
            CharacterStat angelSharp = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.SHARP).value(2).isHighlighted(false).build();
            CharacterStat angelWeird = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.WEIRD).value(-1).isHighlighted(false).build();

            StatsBlock angelStatsBlock1 = StatsBlock.builder().id(UUID.randomUUID().toString())
                    .statsOptionId("demo-stats-option-id-1")
                    .stats(List.of(angelCool, angelHard, angelHot, angelSharp, angelWeird))
                    .build();

            Character claireChar = Character.builder()
                    .id(UUID.randomUUID().toString())
                    .playbook(PlaybookType.ANGEL)
                    .looks(List.of(angel2, angel6, angel10, angel15, angel21))
                    .statsBlock(angelStatsBlock1)
                    .name("Nee")
                    .harm(harm)
                    .build();

            Look brainer3 = Look.builder()
                    .id(UUID.randomUUID().toString())
                    .playbookType(PlaybookType.BRAINER)
                    .category(LookType.GENDER)
                    .look("ambiguous")
                    .build();

            Look brainer6 = Look.builder()
                    .id(UUID.randomUUID().toString())
                    .playbookType(PlaybookType.BRAINER)
                    .category(LookType.CLOTHES)
                    .look("high formal wear")
                    .build();
            Look brainer12 = Look.builder()
                    .playbookType(PlaybookType.BRAINER)
                    .id(UUID.randomUUID().toString())
                    .category(LookType.FACE)
                    .look("pale face")
                    .build();
            Look brainer17 = Look.builder()
                    .id(UUID.randomUUID().toString())
                    .playbookType(PlaybookType.BRAINER)
                    .category(LookType.EYES)
                    .look("dead eyes")
                    .build();
            Look brainer23 = Look.builder()
                    .id(UUID.randomUUID().toString())
                    .playbookType(PlaybookType.BRAINER)
                    .category(LookType.BODY)
                    .look("awkward angular body").build();

            CharacterStat brainerCool = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.COOL).value(1).isHighlighted(false).build();
            CharacterStat brainerHard = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(HARD).value(1).isHighlighted(true).build();
            CharacterStat brainerHot = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.HOT).value(-2).isHighlighted(true).build();
            CharacterStat brainerSharp = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.SHARP).value(1).isHighlighted(false).build();
            CharacterStat brainerWeird = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.WEIRD).value(2).isHighlighted(false).build();

            StatsBlock brainerStatsBlock = StatsBlock.builder().id(UUID.randomUUID().toString())
                    .statsOptionId("demo-stats-option-id-2")
                    .stats(List.of(brainerCool, brainerHard, brainerHot, brainerSharp, brainerWeird))
                    .build();

            Character ruthChar = Character.builder()
                    .id(UUID.randomUUID().toString())
                    .playbook(PlaybookType.BRAINER)
                    .looks(List.of(brainer3, brainer6, brainer12, brainer17, brainer23))
                    .name("Jackson")
                    .harm(harm)
                    .statsBlock(brainerStatsBlock)
                    .build();

            nateGameRole.setUser(nate);
            claireGameRole.setUser(claire);
            claireGameRole.setCharacters(List.of(claireChar));
            ruthGameRole.setUser(ruth);
            ruthGameRole.setCharacters(List.of(ruthChar));
            userService.saveAll(Flux.just(nate, claire, ruth)).blockLast();
            gameRoleService.saveAll(Flux.just(nateGameRole, claireGameRole, ruthGameRole)).blockLast();
            characterService.saveAll(Flux.just(claireChar, ruthChar)).blockLast();

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

    // ---------------------------------------------- Game-related -------------------------------------------- //

    @Override
    public Game createGameWithMC(String userId, String displayName, String email, String name) throws Exception {
        // Create the new game
        Game newGame = Game.builder().id(UUID.randomUUID().toString()).name(name).hasFinishedPreGame(false).build();

        User creator = userService.findOrCreateUser(userId, displayName, email);

        // Create an MC GameRole for the Game creator and add it to the Game
        GameRole mcGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(RoleType.MC).build();
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
    public Mono<Game> setGameName(String gameId, String name) {
        return gameRepository.findById(gameId).flatMap(game1 -> {
            game1.setName(name);
            return gameRepository.save(game1);
        });
    }

    @Override
    public Game addInvitee(String gameId, String email) {
        Game game = findById(gameId).blockOptional().orElseThrow(NoSuchElementException::new);
        game.getInvitees().add(email);
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
    public Game addUserToGame(String gameId, String userId, String displayName, String email) throws Exception {
        User user = userService.findOrCreateUser(userId, displayName, email);

        // Create Player Gamerole for user
        GameRole gameRole = GameRole.builder().id(UUID.randomUUID().toString())
                .role(RoleType.PLAYER)
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

    // ---------------------------------------------- MC stuff -------------------------------------------- //

    @Override
    public Mono<Game> finishPreGame(String gameId) {
        return findById(gameId).map(game -> {
            game.setHasFinishedPreGame(true);
            return game;
        }).flatMap(gameRepository::save);
    }

    // ---------------------------------------------- Move categories -------------------------------------------- //

    @Override
    public Mono<Game> performPrintMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        GameMessage gameMessage = GameMessage.builder()
                .id(UUID.randomUUID().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.PRINT_MOVE)
                .sentOn(Instant.now().toString())
                .build();

        // This method handles both Moves and CharacterMoves.
        // First it tries to find the move in the List of CharacterMoves
        // If it doesn't find the move there, it searches the Moves collection in the db
        Optional<CharacterMove> moveOptional = character.getCharacterMoves().stream().filter(characterMove -> characterMove.getId().equals(moveId)).findFirst();

        if (moveOptional.isPresent()) {
            gameMessage.setContent(moveOptional.get().getDescription());
            gameMessage.setTitle(String.format("%s: %s", character.getName(), moveOptional.get().getName()).toUpperCase());
        } else {
            Move move = moveService.findById(moveId).block();
            assert move != null;
            gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
            gameMessage.setContent(move.getDescription());
        }

        if (isGangMove) {
            gameMessage.setContent(gameMessage.getContent() + "\n\n" + GANG_CONTENT);
        }

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performBarterMove(String gameId, String gameroleId, String characterId, String moveId, int barter) {
        // Get Character
        Character character = characterService.findById(characterId).block();
        assert character != null;

        // Adjust barter
        character.setBarter(character.getBarter() - barter);
        Character savedCharacter = characterService.save(character).block();
        assert savedCharacter != null;
        // Save Character on GameRole
        gameRoleService.findById(gameroleId).map(gameRole1 -> {
            gameRole1.setCharacters(List.of(savedCharacter));
            return gameRole1;
        }).flatMap(gameRoleService::save).block();

        // Find move
        Move move = moveService.findById(moveId).block();
        assert move != null;

        // Create message
        GameMessage gameMessage = GameMessage.builder()
                .id(UUID.randomUUID().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.BARTER_MOVE)
                .sentOn(Instant.now().toString())
                .barterSpent(barter)
                .currentBarter(character.getBarter())
                .content(move.getDescription())
                .title(String.format("%s: %s", character.getName(), move.getName()).toUpperCase())
                .build();

        // Save message to Game and return game
        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performStockMove(String gameId, String gameroleId, String characterId, String moveName, int stockSpent) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    // Find User's Character
                    Character userCharacter = characterService.findById(characterId).block();
                    assert userCharacter != null;

                    Move move = moveService.findByName(moveName).block();
                    assert move != null;

                    GameMessage gameMessage = GameMessage.builder()
                            .id(UUID.randomUUID().toString())
                            .gameId(gameId)
                            .gameRoleId(gameroleId)
                            .messageType(MessageType.STOCK_MOVE)
                            .sentOn(Instant.now().toString()).build();

                    // Adjust stock on user's Character
                    gameRoleService.findById(gameroleId).map(gameRole1 -> {
                        Character character = gameRole1.getCharacters()
                                .stream().filter(character1 -> character1.getId().equals(characterId))
                                .findFirst().orElseThrow();

                        character.getPlaybookUnique().getAngelKit()
                                .setStock(character.getPlaybookUnique().getAngelKit().getStock() - stockSpent);

                        characterService.save(character).block();

                        return gameRole1;
                    }).flatMap(gameRoleService::save).block();

                    gameMessage.setContent(move.getDescription());
                    gameMessage.setTitle(String.format("%s: %s", userCharacter.getName(), move.getName()).toUpperCase());
                    gameMessage.setStockSpent(stockSpent);
                    gameMessage.setCurrentStock(userCharacter.getPlaybookUnique().getAngelKit().getStock());
                    game.getGameMessages().add(gameMessage);
                    return Mono.just(game);
                })
                .flatMap(gameRepository::save);
    }

    // ---------------------------------------------- Roll move categories -------------------------------------------- //

    @Override
    public Mono<Game> performStatRollMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_STAT_MOVE);

        // This method handles both Moves and CharacterMoves.
        // First it tries to find the move in the List of CharacterMoves
        // If it doesn't find the move there, it searches the Moves collection in the db
        Optional<CharacterMove> moveOptional = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getId().equals(moveId)).findFirst();
        CharacterStat modifier;
        String moveName;
        Hold hold1 = Hold.builder().id(UUID.randomUUID().toString()).build();
        Hold hold2 = Hold.builder().id(UUID.randomUUID().toString()).build();
        Hold hold3 = Hold.builder().id(UUID.randomUUID().toString()).build();

        if (moveOptional.isPresent()) {
            moveName = moveOptional.get().getName();
            modifier = getStatToRoll(character, moveOptional.get());
            gameMessage.setContent(moveOptional.get().getDescription());
            gameMessage.setTitle(String.format("%s: %s", character.getName(), moveOptional.get().getName()).toUpperCase());
            hold1.setMoveName(moveName);
            hold1.setMoveDescription(moveOptional.get().getDescription());
            hold2.setMoveName(moveName);
            hold2.setMoveDescription(moveOptional.get().getDescription());
            hold3.setMoveName(moveName);
            hold3.setMoveDescription(moveOptional.get().getDescription());
        } else {
            Move move = moveService.findById(moveId).block();
            assert move != null;
            moveName = move.getName();
            modifier = getStatToRoll(character, move);
            gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
            gameMessage.setContent(move.getDescription());
            hold1.setMoveName(moveName);
            hold1.setMoveDescription(move.getDescription());
            hold2.setMoveName(moveName);
            hold2.setMoveDescription(move.getDescription());
            hold3.setMoveName(moveName);
            hold3.setMoveDescription(move.getDescription());
        }
        gameMessage.setRollModifier(modifier.getValue());
        gameMessage.setModifierStatName(modifier.getStat());
        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + modifier.getValue());
        hold1.setRollResult(gameMessage.getRollResult());
        hold2.setRollResult(gameMessage.getRollResult());
        hold3.setRollResult(gameMessage.getRollResult());


        // Uses a +1forward if the character has one
        if (character.getHasPlusOneForward()) {
            gameMessage.setUsedPlusOneForward(true);
            gameMessage.setRollResult(gameMessage.getRollResult() + 1);
            character.setHasPlusOneForward(false);
        }

        // Handles one pattern for awarding holds
        if (List.of(readPersonName, brainScanName, puppetStringsName, artfulName).contains(moveName)) {
            if (gameMessage.getRollResult() >= 10) {

                character.getHolds().addAll(List.of(hold1, hold2, hold3));
            } else if (gameMessage.getRollResult() >= 7) {
                character.getHolds().add(hold1);
            }
        }

        // Handles second pattern for awarding holds
        if (List.of(keepEyeOutName, hypnoticName).contains(moveName)) {
            if (gameMessage.getRollResult() >= 10) {
                character.getHolds().addAll(List.of(hold1, hold2, hold3));
            } else if (gameMessage.getRollResult() >= 7) {
                character.getHolds().addAll(List.of(hold1, hold2));
            } else {
                character.getHolds().add(hold1);
            }
        }

        // Handles awarding holds for DANGEROUS & SEXY
        if (moveName.equals(dangerousAndSexyName)) {
            if (gameMessage.getRollResult() >= 10) {
                character.getHolds().addAll(List.of(hold1, hold2));
            } else if (gameMessage.getRollResult() >= 7) {
                character.getHolds().add(hold1);
            }
        }

        // Handles awarding holds for BONEFEEL
        if (moveName.equals(bonefeelName)) {
            if (gameMessage.getRollResult() >= 7) {
                character.getHolds().add(hold1);
            }
        }

        // Awards +1forward on high rolls for particular moves
        if (gameMessage.getRollResult() >= 10 && List.of(catOrMouseName, reputationName, leadershipName, lostName).contains(moveName)) {
            character.setHasPlusOneForward(true);
        }

        if (isGangMove) {
            gameMessage.setContent(gameMessage.getContent() + "\n\n" + GANG_CONTENT);
        }

        characterService.save(character).block();
        gameRoleService.findById(gameroleId).map(gameRole -> {
            gameRole.setCharacters(List.of(character));
            return gameRole;
        }).flatMap(gameRoleService::save).block();

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performSpeedRollMove(String gameId, String gameroleId, String characterId, String moveId, int modifier) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_STAT_MOVE);

        Move move = moveService.findById(moveId).block();
        assert move != null;

        String additionalModifierName;

        switch (move.getName()) {
            case boardVehicleName:
                additionalModifierName = "SPEED DIFF.";
                break;
            case dealWithTerrainName:
                additionalModifierName = "HANDLING";
                break;
            case overtakeVehicleName:
                // Deliberately falls through
            case outdistanceVehicleName:
                additionalModifierName = "REL. SPEED";
                break;
            default:
                additionalModifierName = "SPEED";
                break;
        }

        CharacterStat modifyingStat = getStatToRoll(character, move);
        gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
        gameMessage.setContent(move.getDescription());

        gameMessage.setRollModifier(modifyingStat.getValue());
        gameMessage.setModifierStatName(modifyingStat.getStat());
        gameMessage.setAdditionalModifierValue(modifier);
        gameMessage.setAdditionalModifierName(additionalModifierName);
        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + modifyingStat.getValue() + modifier);


        if (character.getHasPlusOneForward()) {
            gameMessage.setUsedPlusOneForward(true);
            gameMessage.setRollResult(gameMessage.getRollResult() + 1);
            character.setHasPlusOneForward(false);
        }


        characterService.save(character).block();
        gameRoleService.findById(gameroleId).map(gameRole -> {
            gameRole.setCharacters(List.of(character));
            return gameRole;
        }).flatMap(gameRoleService::save).block();

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    // ---------------------------------------------- Specific moves -------------------------------------------- //

    @Override
    public Mono<Game> performWealthMove(String gameId, String gameroleId, String characterId) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_STAT_MOVE);

        // This method handles both Moves and CharacterMoves.
        // First it tries to find the move in the List of CharacterMoves
        // If it doesn't find the move there, it searches the Moves collection in the db
        CharacterMove move = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getName().equals(wealthName)).findFirst().orElseThrow();
        CharacterStat modifier = getStatToRoll(character, move);

        gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());

        gameMessage.setRollModifier(modifier.getValue());
        gameMessage.setModifierStatName(modifier.getStat());
        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + modifier.getValue());

        if (character.getHasPlusOneForward()) {
            gameMessage.setUsedPlusOneForward(true);
            gameMessage.setRollResult(gameMessage.getRollResult() + 1);
            character.setHasPlusOneForward(false);
        }

        String content = "";
        if (gameMessage.getRollResult() > 6) {
            int sessionBarter = character.getPlaybookUnique().getHolding().getSurplus();
            character.getPlaybookUnique().getHolding().setBarter(sessionBarter);
            content = String.format("The holding's barter for the session is now **%s**\n" +
                    "\n", sessionBarter);
        } else {
            character.getPlaybookUnique().getHolding().setBarter(0);
            content = "The holding's barter for the session is now **0**\n" +
                    "\n";
        }
        content += move.getDescription();
        gameMessage.setContent(content);

        characterService.save(character).block();
        gameRoleService.findById(gameroleId).map(gameRole -> {
            gameRole.setCharacters(List.of(character));
            return gameRole;
        }).flatMap(gameRoleService::save).block();

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performFortunesMove(String gameId, String gameroleId, String characterId) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_STAT_MOVE);

        // This method handles both Moves and CharacterMoves.
        // First it tries to find the move in the List of CharacterMoves
        // If it doesn't find the move there, it searches the Moves collection in the db
        CharacterMove move = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getName().equals(fortunesName)).findFirst().orElseThrow();
        int modifier = character.getPlaybookUnique().getFollowers().getFortune();

        gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());

        gameMessage.setAdditionalModifierName("FORTUNE");
        gameMessage.setAdditionalModifierValue(modifier);
        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + modifier);

        if (character.getHasPlusOneForward()) {
            gameMessage.setUsedPlusOneForward(true);
            gameMessage.setRollResult(gameMessage.getRollResult() + 1);
            character.setHasPlusOneForward(false);
        }

        String content;
        if (gameMessage.getRollResult() > 6) {
            int sessionBarter = character.getPlaybookUnique().getFollowers().getSurplusBarter();
            character.getPlaybookUnique().getFollowers().setBarter(sessionBarter);
            content = String.format("The followers' barter for the session is now **%s**\n" +
                    "\n", sessionBarter);
        } else {
            character.getPlaybookUnique().getFollowers().setBarter(0);
            content = "The followers' barter for the session is now **0**\n" +
                    "\n";
        }
        content += move.getDescription();
        gameMessage.setContent(content);

        characterService.save(character).block();
        gameRoleService.findById(gameroleId).map(gameRole -> {
            gameRole.setCharacters(List.of(character));
            return gameRole;
        }).flatMap(gameRoleService::save).block();

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performHelpOrInterfereMove(String gameId, String gameroleId, String characterId, String moveId, String targetId) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_HX_MOVE);

        // Is the move a CharacterMove?
        Optional<CharacterMove> moveOptional = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getId().equals(moveId)).findFirst();

        if (moveOptional.isPresent()) {
            // Check if Character has Move to roll with a different stat
            CharacterStat alternateStat = checkForHxAlternative(character, moveOptional.get());

            // Set stat value and name
            if (alternateStat != null) {
                gameMessage.setRollModifier(alternateStat.getValue());
                gameMessage.setModifierStatName(alternateStat.getStat());
            } else {
                int hxValue = character.getHxBlock().stream().filter(hxStat -> hxStat.getCharacterId().equals(targetId))
                        .map(HxStat::getHxValue)
                        .findFirst().orElseThrow();
                gameMessage.setRollModifier(hxValue);
                gameMessage.setModifierStatName(StatType.HX);
            }

            // Set content and title
            gameMessage.setContent(moveOptional.get().getDescription());
            gameMessage.setTitle(String.format("%s: %s", character.getName(), moveOptional.get().getName()).toUpperCase());


        } else {
            // Find move
            Move move = moveService.findById(moveId).block();
            assert move != null;
            // Check if Character has Move to roll with a different stat
            CharacterStat alternateStat = checkForHxAlternative(character, move);

            // Set stat value and name
            if (alternateStat != null) {
                gameMessage.setRollModifier(alternateStat.getValue());
                gameMessage.setModifierStatName(alternateStat.getStat());
            } else {
                int hxValue = character.getHxBlock().stream().filter(hxStat -> hxStat.getCharacterId().equals(targetId))
                        .map(HxStat::getHxValue)
                        .findFirst().orElseThrow();
                gameMessage.setRollModifier(hxValue);
                gameMessage.setModifierStatName(StatType.HX);
            }
            // Set content and title
            gameMessage.setContent(move.getDescription());
            gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
        }

        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + gameMessage.getRollModifier());

        if (character.getHasPlusOneForward()) {
            gameMessage.setUsedPlusOneForward(true);
            gameMessage.setRollResult(gameMessage.getRollResult() + 1);
            character.setHasPlusOneForward(false);
        }
        characterService.save(character).block();
        gameRoleService.findById(gameroleId).map(gameRole -> {
            gameRole.setCharacters(List.of(character));
            return gameRole;
        }).flatMap(gameRoleService::save).block();

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performMakeWantKnownMove(String gameId, String gameroleId, String characterId, String moveId, int barter) {
        Character character = characterService.findById(characterId).block();
        assert character != null;
        character.setBarter(character.getBarter() - barter);

        // Find move
        Move move = moveService.findById(moveId).block();
        assert move != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_BARTER_MOVE);

        gameMessage.setContent(move.getDescription());
        gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
        gameMessage.setBarterSpent(barter);
        gameMessage.setCurrentBarter(character.getBarter());
        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + barter);

        if (character.getHasPlusOneForward()) {
            gameMessage.setUsedPlusOneForward(true);
            gameMessage.setRollResult(gameMessage.getRollResult() + 1);
            character.setHasPlusOneForward(false);
        }


        Character savedCharacter = characterService.save(character).block();
        assert savedCharacter != null;

        // Save Character on GameRole
        gameRoleService.findById(gameroleId).map(gameRole1 -> {
            gameRole1.setCharacters(List.of(savedCharacter));
            return gameRole1;
        }).flatMap(gameRoleService::save).block();

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performSufferHarmMove(String gameId, String gameroleId, String characterId, String moveId, int harm) {
        // Find Character
        Character character = characterService.findById(characterId).block();
        assert character != null;

        // Find move (SUFFER HARM)
        Move move = moveService.findById(moveId).block();
        assert move != null;

        character.getHarm().setValue(character.getHarm().getValue() + harm);
        Character savedCharacter = characterService.save(character).block();
        assert savedCharacter != null;

        // Save Character on GameRole
        gameRoleService.findById(gameroleId).map(gameRole1 -> {
            gameRole1.setCharacters(List.of(savedCharacter));
            return gameRole1;
        }).flatMap(gameRoleService::save).block();

        // Create GameMessage
        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.SUFFER_HARM_MOVE);
        gameMessage.setContent(move.getDescription());
        gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
        gameMessage.setHarmSuffered(harm);
        gameMessage.setCurrentHarm(savedCharacter.getHarm().getValue());
        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + gameMessage.getHarmSuffered());

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performInflictHarmMove(String gameId,
                                             String gameroleId,
                                             String otherGameroleId,
                                             String characterId,
                                             String otherCharacterId,
                                             int harm) {

        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    // Find User's Character
                    Character userCharacter = characterService.findById(characterId).block();
                    assert userCharacter != null;

                    // Find other Character
                    Character otherCharacter = characterService.findById(otherCharacterId).block();
                    assert otherCharacter != null;

                    Move inflictHarmMove = moveService.findByName(inflictHarmName).block();
                    assert inflictHarmMove != null;

                    GameMessage gameMessage = GameMessage.builder()
                            .id(UUID.randomUUID().toString())
                            .gameId(gameId)
                            .gameRoleId(gameroleId)
                            .messageType(MessageType.ADJUST_HX_MOVE)
                            .sentOn(Instant.now().toString()).build();

                    gameRoleService.findById(otherGameroleId).map(gameRole1 -> {
                        Character character = gameRole1.getCharacters()
                                .stream().filter(character1 -> character1.getId().equals(otherCharacterId))
                                .findFirst().orElseThrow();

                        character.setHxBlock(character.getHxBlock()
                                .stream().map(hxStat -> {
                                    if (hxStat.getCharacterId().equals(characterId)) {
                                        hxStat.setHxValue(hxStat.getHxValue() + harm);
                                    }
                                    return hxStat;
                                }).collect(Collectors.toList()));

                        character.getHarm().setValue(character.getHarm().getValue() + harm);

                        characterService.save(character).block();

                        return gameRole1;
                    }).flatMap(gameRoleService::save).block();

                    gameMessage.setContent(String.format("%s suffered %s-harm at the hand of %s.%n%n%s's Hx with %s has been increased by **%s**",
                            otherCharacter.getName(),
                            harm,
                            userCharacter.getName(),
                            otherCharacter.getName(),
                            userCharacter.getName(),
                            harm
                    ));
                    gameMessage.setTitle(String.format("%s: %s", userCharacter.getName(), inflictHarmMove.getName()).toUpperCase());
                    game.getGameMessages().add(gameMessage);
                    return Mono.just(game);
                })
                .flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performHealHarmMove(String gameId,
                                          String gameroleId,
                                          String otherGameroleId,
                                          String characterId,
                                          String otherCharacterId,
                                          int harm) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    // Find User's Character
                    Character userCharacter = characterService.findById(characterId).block();
                    assert userCharacter != null;

                    // Find other Character
                    Character otherCharacter = characterService.findById(otherCharacterId).block();
                    assert otherCharacter != null;

                    Move healHarmMove = moveService.findByName(healHarmName).block();
                    assert healHarmMove != null;

                    GameMessage gameMessage = GameMessage.builder()
                            .id(UUID.randomUUID().toString())
                            .gameId(gameId)
                            .gameRoleId(gameroleId)
                            .messageType(MessageType.ADJUST_HX_MOVE)
                            .sentOn(Instant.now().toString()).build();


                    // Adjust Hx on user's Character
                    gameRoleService.findById(gameroleId).map(gameRole1 -> {
                        Character character = gameRole1.getCharacters()
                                .stream().filter(character1 -> character1.getId().equals(characterId))
                                .findFirst().orElseThrow();

                        character.setHxBlock(character.getHxBlock()
                                .stream().map(hxStat -> {
                                    if (hxStat.getCharacterId().equals(otherCharacterId)) {
                                        hxStat.setHxValue(hxStat.getHxValue() + harm);
                                    }
                                    return hxStat;
                                }).collect(Collectors.toList()));

                        characterService.save(character).block();

                        return gameRole1;
                    }).flatMap(gameRoleService::save).block();

                    // Adjust harm on other Character
                    gameRoleService.findById(otherGameroleId).map(gameRole1 -> {
                        Character character = gameRole1.getCharacters()
                                .stream().filter(character1 -> character1.getId().equals(otherCharacterId))
                                .findFirst().orElseThrow();

                        character.getHarm().setValue(character.getHarm().getValue() - harm);

                        characterService.save(character).block();

                        return gameRole1;
                    }).flatMap(gameRoleService::save).block();

                    gameMessage.setContent(String.format("%s healed %s of %s-harm.%n%n%s's Hx with %s has been increased by **%s**, and %s's harm has been decreased by **%s**.",
                            userCharacter.getName(),
                            otherCharacter.getName(),
                            harm,
                            userCharacter.getName(),
                            otherCharacter.getName(),
                            harm,
                            otherCharacter.getName(),
                            harm
                    ));
                    gameMessage.setTitle(String.format("%s: %s", userCharacter.getName(), healHarmMove.getName()).toUpperCase());
                    game.getGameMessages().add(gameMessage);
                    return Mono.just(game);
                })
                .flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performAngelSpecialMove(String gameId,
                                              String gameroleId,
                                              String otherGameroleId,
                                              String characterId,
                                              String otherCharacterId) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    // Find User's Character
                    Character userCharacter = characterService.findById(characterId).block();
                    assert userCharacter != null;

                    // Find other Character
                    Character otherCharacter = characterService.findById(otherCharacterId).block();
                    assert otherCharacter != null;

                    CharacterMove angelSpecialMove = userCharacter.getCharacterMoves()
                            .stream().filter(characterMove -> characterMove.getName().equals(angelSpecialName))
                            .findFirst().orElseThrow();
                    assert angelSpecialMove != null;

                    GameMessage gameMessage = GameMessage.builder()
                            .id(UUID.randomUUID().toString())
                            .gameId(gameId)
                            .gameRoleId(gameroleId)
                            .messageType(MessageType.ADJUST_HX_MOVE)
                            .sentOn(Instant.now().toString()).build();


                    // Adjust Hx on user's Character
                    gameRoleService.findById(gameroleId).map(gameRole1 -> {
                        Character character = gameRole1.getCharacters()
                                .stream().filter(character1 -> character1.getId().equals(characterId))
                                .findFirst().orElseThrow();

                        character.setHxBlock(character.getHxBlock()
                                .stream().map(hxStat -> {
                                    if (hxStat.getCharacterId().equals(otherCharacterId)) {
                                        hxStat.setHxValue(3);
                                    }
                                    return hxStat;
                                }).collect(Collectors.toList()));

                        characterService.save(character).block();

                        return gameRole1;
                    }).flatMap(gameRoleService::save).block();

                    // Adjust Hx on other Character
                    gameRoleService.findById(otherGameroleId).map(gameRole1 -> {
                        Character character = gameRole1.getCharacters()
                                .stream().filter(character1 -> character1.getId().equals(otherCharacterId))
                                .findFirst().orElseThrow();

                        character.setHxBlock(character.getHxBlock()
                                .stream().map(hxStat -> {
                                    if (hxStat.getCharacterId().equals(characterId)) {
                                        hxStat.setHxValue(hxStat.getHxValue() + 1);
                                    }
                                    return hxStat;
                                }).collect(Collectors.toList()));

                        characterService.save(character).block();

                        return gameRole1;
                    }).flatMap(gameRoleService::save).block();

                    gameMessage.setContent(String.format("%s and %s shagged, and now %s's Hx with %s is **3**, and %s's Hx with %s has increased by **1**.",
                            userCharacter.getName(),
                            otherCharacter.getName(),
                            userCharacter.getName(),
                            otherCharacter.getName(),
                            otherCharacter.getName(),
                            userCharacter.getName()
                    ));
                    gameMessage.setTitle(String.format("%s: %s", userCharacter.getName(), angelSpecialMove.getName()).toUpperCase());
                    game.getGameMessages().add(gameMessage);
                    return Mono.just(game);
                })
                .flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performChopperSpecialMove(String gameId,
                                                String gameroleId,
                                                String otherGameroleId,
                                                String characterId,
                                                String otherCharacterId,
                                                int hxChange) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    // Find User's Character
                    Character userCharacter = characterService.findById(characterId).block();
                    assert userCharacter != null;
                    System.out.println("hxChange = " + hxChange);

                    // Find other Character
                    Character otherCharacter = characterService.findById(otherCharacterId).block();
                    assert otherCharacter != null;

                    CharacterMove chopperSpecialMove = userCharacter.getCharacterMoves()
                            .stream().filter(characterMove -> characterMove.getName().equals(chopperSpecialName))
                            .findFirst().orElseThrow();
                    assert chopperSpecialMove != null;

                    GameMessage gameMessage = GameMessage.builder()
                            .id(UUID.randomUUID().toString())
                            .gameId(gameId)
                            .gameRoleId(gameroleId)
                            .messageType(MessageType.ADJUST_HX_MOVE)
                            .sentOn(Instant.now().toString()).build();


                    // Adjust Hx on user's Character
                    gameRoleService.findById(gameroleId).map(gameRole1 -> {
                        Character character = gameRole1.getCharacters()
                                .stream().filter(character1 -> character1.getId().equals(characterId))
                                .findFirst().orElseThrow();

                        character.setHxBlock(character.getHxBlock()
                                .stream().map(hxStat -> {
                                    if (hxStat.getCharacterId().equals(otherCharacterId)) {
                                        hxStat.setHxValue(hxStat.getHxValue() + hxChange);
                                    }
                                    return hxStat;
                                }).collect(Collectors.toList()));

                        characterService.save(character).block();

                        return gameRole1;
                    }).flatMap(gameRoleService::save).block();

                    // Adjust Hx on other Character
                    gameRoleService.findById(otherGameroleId).map(gameRole1 -> {
                        Character character = gameRole1.getCharacters()
                                .stream().filter(character1 -> character1.getId().equals(otherCharacterId))
                                .findFirst().orElseThrow();

                        character.setHxBlock(character.getHxBlock()
                                .stream().map(hxStat -> {
                                    if (hxStat.getCharacterId().equals(characterId)) {
                                        hxStat.setHxValue(3);
                                    }
                                    return hxStat;
                                }).collect(Collectors.toList()));

                        characterService.save(character).block();

                        return gameRole1;
                    }).flatMap(gameRoleService::save).block();

                    gameMessage.setContent(String.format("%s and %s shagged, and now %s's Hx with %s is **3**, and %s's Hx with %s has %s by **1**.",
                            userCharacter.getName(),
                            otherCharacter.getName(),
                            otherCharacter.getName(),
                            userCharacter.getName(),
                            userCharacter.getName(),
                            otherCharacter.getName(),
                            hxChange == 1 ? "increased" : "decreased"
                    ));
                    gameMessage.setTitle(String.format("%s: %s", userCharacter.getName(), chopperSpecialMove.getName()).toUpperCase());
                    game.getGameMessages().add(gameMessage);
                    return Mono.just(game);
                })
                .flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performGunluggerSpecialMove(String gameId,
                                                  String gameroleId,
                                                  String otherGameroleId,
                                                  String characterId,
                                                  String otherCharacterId,
                                                  boolean addPlus1Forward) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    // Find User's Character
                    Character userCharacter = characterService.findById(characterId).block();
                    assert userCharacter != null;

                    CharacterMove gunluggerSpecialMove = userCharacter.getCharacterMoves()
                            .stream().filter(characterMove -> characterMove.getName().equals(gunluggerSpecialName))
                            .findFirst().orElseThrow();
                    assert gunluggerSpecialMove != null;

                    // Find other Character
                    Character otherCharacter = characterService.findById(otherCharacterId).block();
                    assert otherCharacter != null;


                    // Add +1forward to user's Character
                    gameRoleService.findById(gameroleId).map(gameRole1 -> {
                        Character character = gameRole1.getCharacters()
                                .stream().filter(character1 -> character1.getId().equals(characterId))
                                .findFirst().orElseThrow();

                        character.setHasPlusOneForward(true);

                        characterService.save(character).block();

                        return gameRole1;
                    }).flatMap(gameRoleService::save).block();

                    // Add +1forward to other character (maybe)
                    if (addPlus1Forward) {
                        gameRoleService.findById(otherGameroleId).map(gameRole1 -> {
                            Character character = gameRole1.getCharacters()
                                    .stream().filter(character1 -> character1.getId().equals(otherCharacterId))
                                    .findFirst().orElseThrow();

                            character.setHasPlusOneForward(true);

                            characterService.save(character).block();

                            return gameRole1;
                        }).flatMap(gameRoleService::save).block();
                    }

                    GameMessage gameMessage = GameMessage.builder()
                            .id(UUID.randomUUID().toString())
                            .gameId(gameId)
                            .gameRoleId(gameroleId)
                            .messageType(MessageType.ADJUST_HX_MOVE)
                            .sentOn(Instant.now().toString()).build();


                    String content = String.format("%s and %s had sex. %s has gained +1forward.",
                            userCharacter.getName(),
                            otherCharacter.getName(),
                            userCharacter.getName()
                    );

                    if (addPlus1Forward) {
                        content += String.format("and %s got +1forward too.", otherCharacter.getName());
                    } else {
                        content += String.format("but %s didn't.", otherCharacter.getName());
                    }

                    gameMessage.setContent(content);
                    gameMessage.setTitle(String.format("%s: %s", userCharacter.getName(), gunluggerSpecialMove.getName()).toUpperCase());
                    game.getGameMessages().add(gameMessage);
                    return Mono.just(game);
                })
                .flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performHocusSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId) {

        GameRole gameRoleUser = gameRoleService.findById(gameroleId).block();
        assert gameRoleUser != null;
        GameRole gameRoleOther = gameRoleService.findById(otherGameroleId).block();
        assert gameRoleOther != null;
        Character characterUser = gameRoleUser.getCharacters().stream()
                .filter(character -> character.getId().equals(characterId)).findFirst().orElseThrow();
        assert characterUser != null;
        Character characterOther = gameRoleOther.getCharacters().stream()
                .filter(character -> character.getId().equals(otherCharacterId)).findFirst().orElseThrow();
        assert characterOther != null;
        CharacterMove hocusSpecialMove = characterUser.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getName().equals(hocusSpecialName))
                .findFirst().orElseThrow();
        assert hocusSpecialMove != null;

        Hold hold1 = Hold.builder()
                .id(UUID.randomUUID().toString())
                .moveName(hocusSpecialName)
                .moveDescription(hocusSpecialMove.getDescription())
                .rollResult(0)
                .build();

        Hold hold2 = Hold.builder()
                .id(UUID.randomUUID().toString())
                .moveName(hocusSpecialName)
                .moveDescription(hocusSpecialMove.getDescription())
                .rollResult(0)
                .build();

        characterUser.getHolds().add(hold1);
        characterOther.getHolds().add(hold2);

        GameMessage gameMessage = GameMessage.builder()
                .id(UUID.randomUUID().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.PRINT_MOVE)
                .sentOn(Instant.now().toString()).build();

        String content = String.format("%s and %s had sex. They have both gained 1 hold.\n" +
                        "\n",
                characterUser.getName(),
                characterOther.getName()
        );

        content += hocusSpecialMove.getDescription();

        gameMessage.setContent(content);
        gameMessage.setTitle(String.format("%s: %s", characterUser.getName().toUpperCase(), hocusSpecialMove.getName()));

        characterService.saveAll(Flux.just(characterUser, characterOther)).blockLast();
        gameRoleService.saveAll(Flux.just(gameRoleUser, gameRoleOther)).blockLast();

        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    game.getGameMessages().add(gameMessage);
                    return Mono.just(game);
                })
                .flatMap(gameRepository::save).log();
    }

    @Override
    public Mono<Game> performSkinnerSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, boolean plus1ForUser, boolean plus1ForOther) {
        GameRole gameRoleUser = gameRoleService.findById(gameroleId).block();
        assert gameRoleUser != null;
        GameRole gameRoleOther = gameRoleService.findById(otherGameroleId).block();
        assert gameRoleOther != null;
        Character characterUser = gameRoleUser.getCharacters().stream()
                .filter(character -> character.getId().equals(characterId)).findFirst().orElseThrow();
        assert characterUser != null;
        Character characterOther = gameRoleOther.getCharacters().stream()
                .filter(character -> character.getId().equals(otherCharacterId)).findFirst().orElseThrow();
        assert characterOther != null;
        CharacterMove skinnerSpecialMove = characterUser.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getName().equals(skinnerSpecialName))
                .findFirst().orElseThrow();
        assert skinnerSpecialMove != null;

        String content = skinnerSpecialMove.getDescription();

        if (plus1ForUser) {
            characterUser.setHasPlusOneForward(true);
            content += String.format("\n" +
                    "+1forward has been added to %s.", characterUser.getName());
        }

        if (plus1ForOther) {
            characterOther.setHasPlusOneForward(true);
            content += String.format("\n" +
                    "+1forward has been added to %s, also.", characterOther.getName());
        }

        GameMessage gameMessage = GameMessage.builder()
                .id(UUID.randomUUID().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.PRINT_MOVE)
                .sentOn(Instant.now().toString()).build();

        gameMessage.setContent(content);
        gameMessage.setTitle(String.format("%s: %s", characterUser.getName().toUpperCase(), skinnerSpecialMove.getName()));

        characterService.saveAll(Flux.just(characterUser, characterOther)).blockLast();
        gameRoleService.saveAll(Flux.just(gameRoleUser, gameRoleOther)).blockLast();

        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    game.getGameMessages().add(gameMessage);
                    return Mono.just(game);
                })
                .flatMap(gameRepository::save).log();
    }

    @Override
    public Mono<Game> performStabilizeAndHealMove(String gameId, String gameroleId, String characterId, int stockSpent) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    // Find User's Character
                    Character userCharacter = characterService.findById(characterId).block();
                    assert userCharacter != null;

                    Move stabilizeMove = moveService.findByName(stabilizeAndHealName).block();
                    assert stabilizeMove != null;

                    GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_STOCK_MOVE);


                    gameMessage.setContent(stabilizeMove.getDescription());
                    gameMessage.setTitle(String.format("%s: %s", userCharacter.getName(), stabilizeMove.getName()).toUpperCase());
                    gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + stockSpent);
                    if (userCharacter.getHasPlusOneForward()) {
                        gameMessage.setUsedPlusOneForward(true);
                        gameMessage.setRollResult(gameMessage.getRollResult() + 1);
                    }

                    // Adjust stock on user's Character
                    gameRoleService.findById(gameroleId).map(gameRole1 -> {
                        Character character = gameRole1.getCharacters()
                                .stream().filter(character1 -> character1.getId().equals(characterId))
                                .findFirst().orElseThrow();

                        character.getPlaybookUnique().getAngelKit()
                                .setStock(character.getPlaybookUnique().getAngelKit().getStock() - stockSpent);

                        if (character.getHasPlusOneForward()) {
                            character.setHasPlusOneForward(false);
                        }

                        characterService.save(character).block();

                        return gameRole1;
                    }).flatMap(gameRoleService::save).block();


                    gameMessage.setStockSpent(stockSpent);
                    gameMessage.setCurrentStock(userCharacter.getPlaybookUnique().getAngelKit().getStock() - stockSpent);
                    game.getGameMessages().add(gameMessage);
                    return Mono.just(game);
                })
                .flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performJustGiveMotivationMove(String gameId, String gameroleId, String characterId, String targetId) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_STAT_MOVE);

        CharacterMove move = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getName().equals(justGiveMotiveName)).findFirst().orElseThrow();

        int modifier;

        if (targetId == null) {
            modifier = character.getStatsBlock().getStats()
                    .stream().filter(characterStat -> characterStat.getStat().equals(HARD)).findFirst().orElseThrow().getValue();
        } else {
            modifier = character.getHxBlock()
                    .stream().filter(hxStat -> hxStat.getCharacterId().equals(targetId)).findFirst().orElseThrow().getHxValue();
        }

        gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
        gameMessage.setContent(move.getDescription());
        gameMessage.setRollModifier(modifier);
        gameMessage.setModifierStatName(targetId == null ? HARD : HX);
        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + modifier);

        // Uses a +1forward if the character has one
        if (character.getHasPlusOneForward()) {
            gameMessage.setUsedPlusOneForward(true);
            gameMessage.setRollResult(gameMessage.getRollResult() + 1);
            character.setHasPlusOneForward(false);
        }

        characterService.save(character).block();
        gameRoleService.findById(gameroleId).map(gameRole -> {
            gameRole.setCharacters(List.of(character));
            return gameRole;
        }).flatMap(gameRoleService::save).block();

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    // ---------------------------------------------- Other -------------------------------------------- //

    @Override
    public Mono<Game> spendHold(String gameId, String gameroleId, String characterId, Hold hold) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        // Remove the Hold from the list
        character.setHolds(character.getHolds().stream().filter(hold1 -> !hold1.getId().equals(hold.getId())).collect(Collectors.toList()));

        // Add +1forward for BONEFEEL hold
        if (hold.getMoveName().equals(bonefeelName) && hold.getRollResult() >= 10) {
            character.setHasPlusOneForward(true);
        }

        GameMessage gameMessage = GameMessage.builder()
                .id(UUID.randomUUID().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.PRINT_MOVE)
                .sentOn(Instant.now().toString())
                .title(character.getName().toUpperCase() + ": SPENDS A HOLD")
                .content(String.format(
                                "%s\n" +
                                "\n" +
                                "_%s has %s %s left._",
                        hold.getMoveDescription(),
                        character.getName(),
                        character.getHolds().size(),
                        character.getHolds().size() == 1 ? "hold" : "holds"
                ))
                .build();

        characterService.save(character).block();
        gameRoleService.findById(gameroleId).map(gameRole -> {
            gameRole.setCharacters(List.of(character));
            return gameRole;
        }).flatMap(gameRoleService::save).block();

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    private CharacterStat checkForHxAlternative(Character character, Move move) {
        Optional<RollModifier> optionalRollModifier = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getRollModifier() != null && characterMove.getRollModifier().getMovesToModify().contains(move))
                .map(Move::getRollModifier).findFirst();

        if (optionalRollModifier.isPresent()) {
            StatType statToFind = optionalRollModifier.get().getStatToRollWith();
            return character.getStatsBlock().getStats()
                    .stream().filter(characterStat -> characterStat.getStat().equals(statToFind)).findFirst().orElseThrow();
        } else {
            return null;
        }
    }

    private CharacterStat getStatToRoll(Character character, Move move) {
        // Only some characters will have a RollModifier for the Move they are rolling
        Optional<RollModifier> rollModifierOptional = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getRollModifier() != null && characterMove.getRollModifier().getMovesToModify().contains(move))
                .map(Move::getRollModifier).findFirst();

        StatType statToFind;

        if (rollModifierOptional.isPresent()) {
            statToFind = rollModifierOptional.get().getStatToRollWith();
        } else {
            statToFind = move.getMoveAction().getStatToRollWith();
        }

        return character.getStatsBlock().getStats()
                .stream().filter(characterStat -> characterStat.getStat().equals(statToFind)).findFirst().orElseThrow();
    }

    private GameMessage getGameMessageWithDiceRolls(String gameId, String gameroleId, MessageType messageType) {
        Random random = new Random();
        int roll1 = random.nextInt(6) + 1;
        int roll2 = random.nextInt(6) + 1;

        return GameMessage.builder()
                .id(UUID.randomUUID().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(messageType)
                .sentOn(Instant.now().toString())
                .roll1(roll1)
                .roll2(roll2)
                .build();
    }


}
