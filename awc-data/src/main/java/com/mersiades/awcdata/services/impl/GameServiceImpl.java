package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.enums.RoleType;
import com.mersiades.awccontent.enums.StatType;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.models.RollModifier;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awcdata.enums.MessageType;
import com.mersiades.awcdata.enums.ScriptChangeType;
import com.mersiades.awcdata.enums.ThreatMapLocation;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.repositories.GameRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.mersiades.awccontent.constants.MoveNames.*;
import static com.mersiades.awccontent.enums.StatType.HARD;
import static com.mersiades.awccontent.enums.StatType.HX;

@Service
public class GameServiceImpl implements GameService {

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

    public static final String SCRIPT_CHANGE_REWIND_TITLE = "SCRIPT CHANGE: REWIND";
    public static final String SCRIPT_CHANGE_FAST_FORWARD_TITLE = "SCRIPT CHANGE: FAST FORWARD";
    public static final String SCRIPT_CHANGE_PAUSE_TITLE = "SCRIPT CHANGE: PAUSE";
    public static final String SCRIPT_CHANGE_FRAME_TITLE = "SCRIPT CHANGE: FRAME BY FRAME";
    public static final String SCRIPT_CHANGE_RESUME_TITLE = "SCRIPT CHANGE: RESUME";
    public static final String SCRIPT_CHANGE_REPLAY_TITLE = "SCRIPT CHANGE: INSTANT REPLAY";
    public static final String SCRIPT_CHANGE_REWIND_CONTENT = "When you rewind, you back up to a specific point in a scene and do the scene over again avoiding whatever issue led to the rewind, and trying in a different way.";
    public static final String SCRIPT_CHANGE_FAST_FORWARD_CONTENT = "When you fast forward, you fade to black and advance time as needed to avoid content or elements of play, or just to move forward in time.";
    public static final String SCRIPT_CHANGE_PAUSE_CONTENT = "Call a pause if you need a break from an intense scene, to take a bio beak, or if you're seeking clarity or a discussion about the game or content.";
    public static final String SCRIPT_CHANGE_FRAME_CONTENT = "Call frame-by-frame before scenes with content you want to play through with care. During the scene, players will call occasional pauses to check in, and take the scene slow.";
    public static final String SCRIPT_CHANGE_RESUME_CONTENT = "Use resume to return to normal play at any time, as the player who called the original Script Change in effect.";
    public static final String SCRIPT_CHANGE_REPLAY_CONTENT = "Call an instant replay right after a scene to share enthusiasm about what happened, or to clarify details of the narrative.";

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
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game findById(String id) {
        return gameRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public List<Game> saveAll(List<Game> games) {
        return gameRepository.saveAll(games);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game);
    }

    @Override
    public void deleteById(String id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Game findByGameRoles(GameRole gameRole) {
        return gameRepository.findByGameRoles(gameRole);
    }

    @Override
    public Game findAndDeleteById(String gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(NoSuchElementException::new);

        // Remove Gameroles from mc and players
        userService.removeGameroleFromUser(game.getMc().getId().toString(), gameId);
        game.getPlayers().forEach(player -> userService.removeGameroleFromUser(player.getId().toString(), gameId));

        // Delete Gameroles
        game.getGameRoles().forEach(gameRoleService::delete);

        // Delete Game
        this.delete(game); // Maybe use: gameRepository.deleteById(id), or change the deleteById method to delete gameroles also

        return game;
    }

    @Override
    public List<Game> findAllByInvitee(String email) {
        String lowercaseEmail = email.toLowerCase();
        return gameRepository.findAllByInviteesContaining(lowercaseEmail);
    }

    // ---------------------------------------------- Game-related -------------------------------------------- //

    @Override
    public Game createGameWithMC(String userId, String displayName, String email, String name) throws Exception {
        // Create the new game
        Game newGame = Game.builder()
                .name(name)
                .hasFinishedPreGame(false)
                .showFirstSession(false)
                .build();

        User creator = userService.findOrCreateUser(userId, displayName, email);

        // Create an MC GameRole for the Game creator and add it to the Game
        GameRole mcGameRole = GameRole.builder()
                .id(new ObjectId().toString())
                .gameName(name)
                .role(RoleType.MC).build();
        newGame.getGameRoles().add(mcGameRole);
        newGame.setMc(creator);
        Game savedGame = gameRepository.save(newGame);

        assert creator != null;
        userService.addGameroleToUser(creator.getId(), mcGameRole);
        // Add the Game and User to the MC's GameRole
        mcGameRole.setGameId(savedGame.getId());
        mcGameRole.setUserId(creator.getId());
        gameRoleService.save(mcGameRole);

        return newGame;
    }

    @Override
    public Game setGameName(String gameId, String name) {
        Game game = getGame(gameId);
        game.setName(name);
        return gameRepository.save(game);

    }

    @Override
    public Game addInvitee(String gameId, String email) {
        Game game = getGame(gameId);
        String lowercaseEmail = email.toLowerCase();

        boolean isInviteeAlreadyAPlayer = game.getPlayers()
                .stream().anyMatch(player -> player.getEmail().equals(lowercaseEmail));
        boolean isInviteeAlreadyInvited = game.getInvitees()
                .stream().anyMatch(invitee -> invitee.equals(lowercaseEmail));
        boolean isInviteeTheMC = game.getMc().getEmail().equals(lowercaseEmail);

        if (!isInviteeAlreadyAPlayer && !isInviteeAlreadyInvited && !isInviteeTheMC) {
            game.getInvitees().add(lowercaseEmail);
            gameRepository.save(game);
        }
        return game;
    }

    @Override
    public Game removeInvitee(String gameId, String email) {
        Game game = getGame(gameId);
        game.getInvitees().remove(email);
        gameRepository.save(game);
        return game;
    }

    @Override
    public Game removePlayer(String gameId, String playerId) {
        Game game = getGame(gameId);
        User user = userService.findById(playerId);
        assert user != null;

        // Remove gameRole from User
        GameRole gameRoleToRemove = user.getGameRoles().stream()
                .filter(gameRole -> gameRole.getGameId().equals(gameId)).findFirst().orElseThrow(NoSuchElementException::new);
        user.getGameRoles().remove(gameRoleToRemove);

        // Remove gameRole from game
        List<GameRole> gamesFilteredGameRoles = game.getGameRoles().stream()
                .filter(gameRole -> !gameRole.getUserId().equals(playerId))
                .collect(Collectors.toList());
        game.setGameRoles(gamesFilteredGameRoles);

        // Remove player from game
        List<User> players = game.getPlayers()
                .stream().filter(user1 -> !user1.getId().equals(playerId)).collect(Collectors.toList());
        game.setPlayers(players);

        // Delete gameRole from db
        gameRoleService.delete(gameRoleToRemove);

        gameRepository.save(game);
        userService.save(user);
        return game;
    }

    @Override
    public Game addCommsApp(String gameId, String app) {
        Game game = getGame(gameId);
        game.setCommsApp(app);
        gameRepository.save(game);
        return game;
    }

    @Override
    public Game addCommsUrl(String gameId, String url) {
        Game game = getGame(gameId);
        game.setCommsUrl(url);
        gameRepository.save(game);
        return game;
    }

    @Override
    public Game addUserToGame(String gameId, String userId, String displayName, String email) throws Exception {
        Game game = getGame(gameId);
        assert game != null;
        User user = userService.findOrCreateUser(userId, displayName, email);

        // If the game already has the user in it, change nothing.
        if (game.getPlayers().contains(user)) {
            return game;
        }

        // Create Player Gamerole for user
        GameRole gameRole = GameRole.builder().id(new ObjectId().toString())
                .role(RoleType.PLAYER)
                .gameName(game.getName())
                .gameId(game.getId())
                .userId(user.getId())
                .build();

        game.getGameRoles().add(gameRole);
        game.getPlayers().add(user);
        game.getInvitees().remove(email);
        gameRepository.save(game);

        userService.addGameroleToUser(user.getId(), gameRole);
        gameRoleService.save(gameRole);
        return game;
    }

    // ---------------------------------------------- MC stuff -------------------------------------------- //

    @Override
    public Game finishPreGame(String gameId) {
        Game game = getGame(gameId);
        game.setHasFinishedPreGame(true);
        game.setShowFirstSession(true);
        return gameRepository.save(game);
    }

    @Override
    public Game closeFirstSession(String gameId) {
        Game game = getGame(gameId);
        game.setShowFirstSession(false);
        return gameRepository.save(game);
    }

    @Override
    public Game changeCharacterPosition(
            String gameId,
            String gameRoleId,
            String characterId,
            ThreatMapLocation newPosition
    ) {
        Game game = getGame(gameId);

        GameRole gameRole = game.getGameRoles().stream()
                .filter(gameRole1 -> gameRole1.getId().equals(gameRoleId))
                .findFirst().orElseThrow(NoSuchElementException::new);

        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId))
                .findFirst().orElseThrow(NoSuchElementException::new);

        character.setMapPosition(newPosition);

        characterService.save(character);
        gameRoleService.save(gameRole);
        return gameRepository.save(game);
    }

    // ---------------------------------------------- Move categories -------------------------------------------- //

    @Override
    public Game performPrintMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove) {
        Game game = getGame(gameId);
        Character character = characterService.findById(characterId);
        assert character != null;

        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
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
            Move move = moveService.findById(moveId);
            assert move != null;
            gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
            gameMessage.setContent(move.getDescription());
        }

        if (isGangMove) {
            gameMessage.setContent(gameMessage.getContent() + "\n\n" + GANG_CONTENT);
        }
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game performBarterMove(String gameId, String gameroleId, String characterId, String moveId, int barter) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);

        // Get Character
        Character character = characterService.findById(characterId);
        assert character != null;

        // Find move
        Move move = moveService.findById(moveId);
        assert move != null;

        // Adjust barter
        character.setBarter(character.getBarter() - barter);
        Character savedCharacter = characterService.save(character);
        assert savedCharacter != null;

        // Save Character on GameRole
        gameRole.setCharacters(List.of(savedCharacter));
        gameRoleService.save(gameRole);

        // Create message
        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
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
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game performStockMove(String gameId, String gameroleId, String characterId, String moveName, int stockSpent) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        // Find User's Character
        Character userCharacter = characterService.findById(characterId);
        assert userCharacter != null;

        Move move = moveService.findByName(moveName);
        assert move != null;

        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.STOCK_MOVE)
                .sentOn(Instant.now().toString()).build();

        // Adjust stock on user's Character
        Character character = gameRole.getCharacters()
                .stream().filter(character1 -> character1.getId().equals(characterId))
                .findFirst().orElseThrow();

        character.getPlaybookUniques().getAngelKit()
                .setStock(character.getPlaybookUniques().getAngelKit().getStock() - stockSpent);

        characterService.save(character);
        gameRoleService.save(gameRole);

        gameMessage.setContent(move.getDescription());
        gameMessage.setTitle(String.format("%s: %s", userCharacter.getName(), move.getName()).toUpperCase());
        gameMessage.setStockSpent(stockSpent);
        gameMessage.setCurrentStock(userCharacter.getPlaybookUniques().getAngelKit().getStock() - stockSpent);

        // Save message to Game and return game
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    // ---------------------------------------------- Roll move categories -------------------------------------------- //

    @Override
    public Game performStatRollMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        Character character = characterService.findById(characterId);
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_STAT_MOVE);

        // This method handles both Moves and CharacterMoves.
        // First it tries to find the move in the List of CharacterMoves
        // If it doesn't find the move there, it searches the Moves collection in the db
        Optional<CharacterMove> moveOptional = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getId().equals(moveId)).findFirst();
        CharacterStat modifier;
        String moveName;
        Hold hold1 = Hold.builder().id(new ObjectId().toString()).build();
        Hold hold2 = Hold.builder().id(new ObjectId().toString()).build();
        Hold hold3 = Hold.builder().id(new ObjectId().toString()).build();

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
            Move move = moveService.findById(moveId);
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

        // TODO: add twelvePlusInstructions to gameMessage if the character has advanced the Move

        if (modifier.getIsHighlighted()) {
            character.setExperience(character.getExperience() + 1);
            gameMessage.setContent(gameMessage.getContent() +
                    "\n" +
                    "\n" +
                    "An experience point has been gained for rolling a highlighted stat.");
        }

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

        characterService.save(character);

        gameRole.setCharacters(List.of(character));
        gameRoleService.save(gameRole);

        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game performSpeedRollMove(String gameId, String gameroleId, String characterId, String moveId, int modifier) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        Character character = characterService.findById(characterId);
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_STAT_MOVE);

        Move move = moveService.findById(moveId);
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

        if (modifyingStat.getIsHighlighted()) {
            character.setExperience(character.getExperience() + 1);
            gameMessage.setContent(gameMessage.getContent() +
                    "\n" +
                    "\n" +
                    "An experience point has been gained for rolling a highlighted stat.");
        }

        if (character.getHasPlusOneForward()) {
            gameMessage.setUsedPlusOneForward(true);
            gameMessage.setRollResult(gameMessage.getRollResult() + 1);
            character.setHasPlusOneForward(false);
        }


        characterService.save(character);
        gameRole.setCharacters(List.of(character));
        gameRoleService.save(gameRole);
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    // ---------------------------------------------- Specific moves -------------------------------------------- //

    @Override
    public Game performWealthMove(String gameId, String gameroleId, String characterId) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        Character character = characterService.findById(characterId);
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

        String content;
        if (gameMessage.getRollResult() > 6) {
            int sessionBarter = character.getPlaybookUniques().getHolding().getSurplus();
            character.getPlaybookUniques().getHolding().setBarter(sessionBarter);
            content = String.format("The holding's barter for the session is now **%s**\n" +
                    "\n", sessionBarter);
        } else {
            character.getPlaybookUniques().getHolding().setBarter(0);
            content = "The holding's barter for the session is now **0**\n" +
                    "\n";
        }
        content += move.getDescription();
        gameMessage.setContent(content);

        if (modifier.getIsHighlighted()) {
            character.setExperience(character.getExperience() + 1);
            gameMessage.setContent(gameMessage.getContent() +
                    "\n" +
                    "\n" +
                    "An experience point has been gained for rolling a highlighted stat.");
        }

        characterService.save(character);
        gameRole.setCharacters(List.of(character));
        gameRoleService.save(gameRole);
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game performFortunesMove(String gameId, String gameroleId, String characterId) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        Character character = characterService.findById(characterId);
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_STAT_MOVE);

        // This method handles both Moves and CharacterMoves.
        // First it tries to find the move in the List of CharacterMoves
        // If it doesn't find the move there, it searches the Moves collection in the db
        CharacterMove move = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getName().equals(fortunesName)).findFirst().orElseThrow();
        int modifier = character.getPlaybookUniques().getFollowers().getFortune();

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
            int sessionBarter = character.getPlaybookUniques().getFollowers().getSurplusBarter();
            character.getPlaybookUniques().getFollowers().setBarter(sessionBarter);
            content = String.format("The followers' barter for the session is now **%s**\n" +
                    "\n", sessionBarter);
        } else {
            character.getPlaybookUniques().getFollowers().setBarter(0);
            content = "The followers' barter for the session is now **0**\n" +
                    "\n";
        }
        content += move.getDescription();
        gameMessage.setContent(content);

        characterService.save(character);
        gameRole.setCharacters(List.of(character));
        gameRoleService.save(gameRole);
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game performHelpOrInterfereMove(String gameId, String gameroleId, String characterId, String moveId, String targetId) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        Character character = characterService.findById(characterId);
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
            Move move = moveService.findById(moveId);
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

        characterService.save(character);
        gameRole.setCharacters(List.of(character));
        gameRoleService.save(gameRole);
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game performMakeWantKnownMove(String gameId, String gameroleId, String characterId, String moveId, int barter) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        Character character = characterService.findById(characterId);
        assert character != null;
        character.setBarter(character.getBarter() - barter);

        // Find move
        Move move = moveService.findById(moveId);
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


        Character savedCharacter = characterService.save(character);
        assert savedCharacter != null;
        gameRole.setCharacters(List.of(savedCharacter));
        gameRoleService.save(gameRole);
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game performSufferHarmMove(String gameId, String gameroleId, String characterId, String moveId, int harm) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        // Find Character
        Character character = characterService.findById(characterId);
        assert character != null;

        // Find move (SUFFER HARM)
        Move move = moveService.findById(moveId);
        assert move != null;

        character.getHarm().setValue(character.getHarm().getValue() + harm);
        Character savedCharacter = characterService.save(character);
        assert savedCharacter != null;

        // Create GameMessage
        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.SUFFER_HARM_MOVE);
        gameMessage.setContent(move.getDescription());
        gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
        gameMessage.setHarmSuffered(harm);
        gameMessage.setCurrentHarm(savedCharacter.getHarm().getValue());
        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + gameMessage.getHarmSuffered());


        gameRole.setCharacters(List.of(savedCharacter));
        gameRoleService.save(gameRole);
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game performSufferVHarmMove(String gameId, String gameroleId, String characterId, int harm) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        Character character = getCharacter(gameRole, characterId);
        Move move = moveService.findByName(sufferVHarm);
        assert move != null;

        // Create GameMessage
        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.SUFFER_V_HARM_MOVE);
        gameMessage.setContent(move.getDescription());
        gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
        gameMessage.setHarmSuffered(harm);
        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + gameMessage.getHarmSuffered());
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game performInflictHarmMove(String gameId,
                                       String gameroleId,
                                       String otherGameroleId,
                                       String characterId,
                                       String otherCharacterId,
                                       int harm) {

        Game game = getGame(gameId);
        GameRole otherGameRole = gameRoleService.findById(otherGameroleId);
        Character userCharacter = characterService.findById(characterId);
        Character otherCharacter = getCharacter(otherGameRole, otherCharacterId);
        Move inflictHarmMove = moveService.findByName(inflictHarmName);
        assert inflictHarmMove != null;
        boolean hasIncrementedExperience = false;

        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.ADJUST_HX_MOVE)
                .sentOn(Instant.now().toString()).build();

        // Check if harm will take hxValue to 4 or over and flag experience increase
        HxStat hxStat = otherCharacter.getHxBlock()
                .stream().filter(hxStat1 -> hxStat1.getCharacterId().equals(characterId)).findFirst().orElseThrow(NoSuchElementException::new);
        if (hxStat.getHxValue() + harm >= 4) {
            hasIncrementedExperience = true;
        }

        otherCharacter.setHxBlock(otherCharacter.getHxBlock()
                .stream().peek(hxStat2 -> {
                    if (hxStat2.getCharacterId().equals(characterId)) {
                        hxStat2.setHxValue(hxStat2.getHxValue() + harm);

                        // If this brings hx value to 4 or higher, reset to 1 and add experience to the harm-sufferer
                        if (hxStat2.getHxValue() >= 4) {
                            hxStat2.setHxValue(1);
                            otherCharacter.setExperience(otherCharacter.getExperience() + 1);
                        }
                    }
                }).collect(Collectors.toList()));
        otherCharacter.getHarm().setValue(otherCharacter.getHarm().getValue() + harm);

        String contentLine1 = String.format("%s suffered %s-harm at the hand of %s.",
                otherCharacter.getName(),
                harm,
                userCharacter.getName());

        String contentLine2;

        if (hasIncrementedExperience) {
            contentLine2 = String.format("%n%nTheir Hx has been reset to 1 and experience has been marked.");
        } else {
            contentLine2 = String.format("%n%n%s's Hx with %s has been increased by **%s**",
                    otherCharacter.getName(),
                    userCharacter.getName(),
                    harm);
        }

        gameMessage.setContent(contentLine1 + contentLine2);

        gameMessage.setTitle(String.format("%s: %s", userCharacter.getName(), inflictHarmMove.getName()).toUpperCase());

        game.getGameMessages().add(gameMessage);

        characterService.save(otherCharacter);
        gameRoleService.save(otherGameRole);
        return gameRepository.save(game);
    }

    @Override
    public Game performHealHarmMove(String gameId,
                                    String gameroleId,
                                    String otherGameroleId,
                                    String characterId,
                                    String otherCharacterId,
                                    int harm) {

        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        GameRole otherGameRole = gameRoleService.findById(otherGameroleId);
        Character userCharacter = getCharacter(gameRole, characterId);
        Character otherCharacter = getCharacter(otherGameRole, otherCharacterId);
        Move healHarmMove = moveService.findByName(healHarmName);
        assert healHarmMove != null;
        boolean hasIncrementedExperience = false;

        // Check if harm will take hxValue to 4 or over and flag experience increase
        HxStat hxStat = userCharacter.getHxBlock()
                .stream().filter(hxStat1 -> hxStat1.getCharacterId().equals(otherCharacterId)).findFirst().orElseThrow(NoSuchElementException::new);
        if (hxStat.getHxValue() + harm >= 4) {
            hasIncrementedExperience = true;
        }

        // Adjust Hx on user's Character
        userCharacter.setHxBlock(userCharacter.getHxBlock()
                .stream().peek(hxStat2 -> {
                    if (hxStat2.getCharacterId().equals(otherCharacterId)) {
                        hxStat2.setHxValue(hxStat2.getHxValue() + harm);
                        // If this brings hx value to 4 or higher, reset to 1 and add experience to the healer
                        if (hxStat2.getHxValue() >= 4) {
                            hxStat2.setHxValue(1);
                            userCharacter.setExperience(userCharacter.getExperience() + 1);
                        }
                    }
                }).collect(Collectors.toList()));

        // Adjust harm on other Character
        otherCharacter.getHarm().setValue(otherCharacter.getHarm().getValue() - harm);

        String contentLine1 = String.format("%s healed %s of %s-harm.",
                userCharacter.getName(),
                otherCharacter.getName(),
                harm);

        String contentLine2;

        if (hasIncrementedExperience) {
            contentLine2 = String.format("%n%n%s's Hx has been reset to 1 and their experience has been marked, and %s's harm has been decreased by **%s**.",
                    userCharacter.getName(),
                    otherCharacter.getName(),
                    harm);
        } else {
            contentLine2 = String.format("%n%n%s's Hx with %s has been increased by **%s**, and %s's harm has been decreased by **%s**.",
                    userCharacter.getName(),
                    otherCharacter.getName(),
                    harm,
                    otherCharacter.getName(),
                    harm);
        }

        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.ADJUST_HX_MOVE)
                .content(contentLine1 + contentLine2)
                .title(String.format("%s: %s", userCharacter.getName(), healHarmMove.getName()).toUpperCase())
                .sentOn(Instant.now().toString()).build();

        game.getGameMessages().add(gameMessage);

        characterService.saveAll(List.of(userCharacter, otherCharacter));
        gameRoleService.saveAll(List.of(gameRole, otherGameRole));
        return gameRepository.save(game);
    }

    @Override
    public Game performAngelSpecialMove(String gameId,
                                        String gameroleId,
                                        String otherGameroleId,
                                        String characterId,
                                        String otherCharacterId) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        GameRole otherGameRole = gameRoleService.findById(otherGameroleId);
        Character userCharacter = getCharacter(gameRole, characterId);
        Character otherCharacter = getCharacter(otherGameRole, otherCharacterId);
        CharacterMove angelSpecialMove = getCharacterMoveByName(userCharacter, angelSpecialName);
        assert angelSpecialMove != null;
        boolean hasIncrementedExperience = false;

        // Adjust Hx on user's Character
        userCharacter.setHxBlock(userCharacter.getHxBlock()
                .stream().peek(hxStat -> {
                    if (hxStat.getCharacterId().equals(otherCharacterId)) {
                        hxStat.setHxValue(3);
                    }
                }).collect(Collectors.toList()));

        // Check if harm will take hxValue to 4 or over and flag experience increase
        HxStat hxStat = otherCharacter.getHxBlock()
                .stream().filter(hxStat1 -> hxStat1.getCharacterId().equals(characterId)).findFirst().orElseThrow(NoSuchElementException::new);
        if (hxStat.getHxValue() + 1 >= 4) {
            hasIncrementedExperience = true;
        }

        // Adjust Hx on other Character
        otherCharacter.setHxBlock(otherCharacter.getHxBlock()
                .stream().peek(hxStat2 -> {
                    if (hxStat2.getCharacterId().equals(characterId)) {
                        hxStat2.setHxValue(hxStat2.getHxValue() + 1);
                        // If this brings hx value to 4 or higher, reset to 1 and add experience to the harm-sufferer
                        if (hxStat2.getHxValue() >= 4) {
                            hxStat2.setHxValue(1);
                            otherCharacter.setExperience(otherCharacter.getExperience() + 1);
                        }
                    }
                }).collect(Collectors.toList()));

        String contentPart1 = String.format("%s and %s shagged, and now %s's Hx with %s is **3**, ",
                userCharacter.getName(),
                otherCharacter.getName(),
                userCharacter.getName(),
                otherCharacter.getName());

        String contentPart2;

        if (hasIncrementedExperience) {
            contentPart2 = String.format("and %s's Hx with %s has been reset to 1 and experience marked.",
                    otherCharacter.getName(),
                    userCharacter.getName());
        } else {
            contentPart2 = String.format("and %s's Hx with %s has increased by **1**.",
                    otherCharacter.getName(),
                    userCharacter.getName());
        }

        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.ADJUST_HX_MOVE)
                .content(contentPart1 + contentPart2)
                .title(String.format("%s: %s", userCharacter.getName(), angelSpecialMove.getName()).toUpperCase())
                .sentOn(Instant.now().toString()).build();
        game.getGameMessages().add(gameMessage);

        characterService.saveAll(List.of(userCharacter, otherCharacter));
        gameRoleService.saveAll(List.of(gameRole, otherGameRole));
        return gameRepository.save(game);
    }

    @Override
    public Game performChopperSpecialMove(String gameId,
                                          String gameroleId,
                                          String otherGameroleId,
                                          String characterId,
                                          String otherCharacterId,
                                          int hxChange) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        GameRole otherGameRole = gameRoleService.findById(otherGameroleId);
        Character userCharacter = getCharacter(gameRole, characterId);
        Character otherCharacter = getCharacter(otherGameRole, otherCharacterId);
        CharacterMove chopperSpecialMove = getCharacterMoveByName(userCharacter, chopperSpecialName);
        assert chopperSpecialMove != null;

        // Adjust Hx on user's Character
        userCharacter.setHxBlock(userCharacter.getHxBlock()
                .stream().peek(hxStat -> {
                    if (hxStat.getCharacterId().equals(otherCharacterId)) {
                        hxStat.setHxValue(hxStat.getHxValue() + hxChange);
                    }
                }).collect(Collectors.toList()));

        // Adjust Hx on other Character
        otherCharacter.setHxBlock(otherCharacter.getHxBlock()
                .stream().peek(hxStat -> {
                    if (hxStat.getCharacterId().equals(characterId)) {
                        hxStat.setHxValue(3);
                    }
                }).collect(Collectors.toList()));

        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.ADJUST_HX_MOVE)
                .content(String.format("%s and %s shagged, and now %s's Hx with %s is **3**, and %s's Hx with %s has %s by **1**.",
                        userCharacter.getName(),
                        otherCharacter.getName(),
                        otherCharacter.getName(),
                        userCharacter.getName(),
                        userCharacter.getName(),
                        otherCharacter.getName(),
                        hxChange == 1 ? "increased" : "decreased"
                ))
                .title(String.format("%s: %s", userCharacter.getName(), chopperSpecialMove.getName()).toUpperCase())
                .sentOn(Instant.now().toString()).build();

        game.getGameMessages().add(gameMessage);

        characterService.saveAll(List.of(userCharacter, otherCharacter));
        gameRoleService.saveAll(List.of(gameRole, otherGameRole));
        return gameRepository.save(game);
    }

    @Override
    public Game performGunluggerSpecialMove(String gameId,
                                            String gameroleId,
                                            String otherGameroleId,
                                            String characterId,
                                            String otherCharacterId,
                                            boolean addPlus1Forward) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        GameRole otherGameRole = gameRoleService.findById(otherGameroleId);
        Character userCharacter = getCharacter(gameRole, characterId);
        Character otherCharacter = getCharacter(otherGameRole, otherCharacterId);
        CharacterMove gunluggerSpecialMove = getCharacterMoveByName(userCharacter, gunluggerSpecialName);

        assert gunluggerSpecialMove != null;

        // Add +1forward to user's Character
        userCharacter.setHasPlusOneForward(true);

        // Add +1forward to other character (maybe)
        if (addPlus1Forward) {
            otherCharacter.setHasPlusOneForward(true);
        }

        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.ADJUST_HX_MOVE)
                .title(String.format("%s: %s", userCharacter.getName(), gunluggerSpecialMove.getName()).toUpperCase())
                .sentOn(Instant.now().toString()).build();


        String content = String.format("%s and %s had sex. %s has gained +1forward, ",
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
        game.getGameMessages().add(gameMessage);

        characterService.saveAll(List.of(userCharacter, otherCharacter));
        gameRoleService.saveAll(List.of(gameRole, otherGameRole));
        return gameRepository.save(game);
    }

    @Override
    public Game performHocusSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId) {
        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        GameRole otherGameRole = gameRoleService.findById(otherGameroleId);
        Character userCharacter = getCharacter(gameRole, characterId);
        Character characterOther = getCharacter(otherGameRole, otherCharacterId);
        CharacterMove hocusSpecialMove = getCharacterMoveByName(userCharacter, hocusSpecialName);
        assert hocusSpecialMove != null;

        Hold hold1 = Hold.builder()
                .id(new ObjectId().toString())
                .moveName(hocusSpecialName)
                .moveDescription(hocusSpecialMove.getDescription())
                .rollResult(0)
                .build();

        Hold hold2 = Hold.builder()
                .id(new ObjectId().toString())
                .moveName(hocusSpecialName)
                .moveDescription(hocusSpecialMove.getDescription())
                .rollResult(0)
                .build();

        userCharacter.getHolds().add(hold1);
        characterOther.getHolds().add(hold2);

        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.PRINT_MOVE)
                .title(String.format("%s: %s", userCharacter.getName().toUpperCase(), hocusSpecialMove.getName()))
                .sentOn(Instant.now().toString()).build();

        String content = String.format("%s and %s had sex. They have both gained 1 hold.\n" +
                        "\n",
                userCharacter.getName(),
                characterOther.getName()
        );
        content += hocusSpecialMove.getDescription();
        gameMessage.setContent(content);

        game.getGameMessages().add(gameMessage);

        characterService.saveAll(List.of(userCharacter, characterOther));
        gameRoleService.saveAll(List.of(gameRole, otherGameRole));
        return gameRepository.save(game);
    }

    @Override
    public Game performSkinnerSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, boolean plus1ForUser, boolean plus1ForOther) {
        Game game = gameRepository.findById(gameId).orElseThrow(NoSuchElementException::new);
        GameRole gameRoleUser = gameRoleService.findById(gameroleId);
        assert gameRoleUser != null;
        GameRole gameRoleOther = gameRoleService.findById(otherGameroleId);
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
                .id(new ObjectId().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(MessageType.PRINT_MOVE)
                .sentOn(Instant.now().toString()).build();

        gameMessage.setContent(content);
        gameMessage.setTitle(String.format("%s: %s", characterUser.getName().toUpperCase(), skinnerSpecialMove.getName()));

        characterService.saveAll(List.of(characterUser, characterOther));
        gameRoleService.saveAll(List.of(gameRoleUser, gameRoleOther));
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game performStabilizeAndHealMove(String gameId, String gameroleId, String characterId, int stockSpent) {

        Game game = getGame(gameId);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        Character userCharacter = getCharacter(gameRole, characterId);

        Move stabilizeMove = moveService.findByName(stabilizeAndHealName);
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
        userCharacter.getPlaybookUniques().getAngelKit()
                .setStock(userCharacter.getPlaybookUniques().getAngelKit().getStock() - stockSpent);

        if (userCharacter.getHasPlusOneForward()) {
            userCharacter.setHasPlusOneForward(false);
        }

        gameMessage.setStockSpent(stockSpent);
        gameMessage.setCurrentStock(userCharacter.getPlaybookUniques().getAngelKit().getStock());
        game.getGameMessages().add(gameMessage);

        characterService.save(userCharacter);
        gameRoleService.save(gameRole);
        return gameRepository.save(game);
    }

    @Override
    public Game performJustGiveMotivationMove(String gameId, String gameroleId, String characterId, String targetId) {
        Game game = gameRepository.findById(gameId).orElseThrow(NoSuchElementException::new);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        Character character = characterService.findById(characterId);
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

        characterService.save(character);
        gameRole.setCharacters(List.of(character));
        gameRoleService.save(gameRole);
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    // ---------------------------------------------- Other -------------------------------------------- //

    @Override
    public Game spendHold(String gameId, String gameroleId, String characterId, Hold hold) {
        Game game = gameRepository.findById(gameId).orElseThrow(NoSuchElementException::new);
        GameRole gameRole = gameRoleService.findById(gameroleId);
        Character character = characterService.findById(characterId);
        assert character != null;

        // Remove the Hold from the list
        character.setHolds(character.getHolds().stream().filter(hold1 -> !hold1.getId().equals(hold.getId())).collect(Collectors.toList()));

        // Add +1forward for BONEFEEL hold
        if (hold.getMoveName().equals(bonefeelName) && hold.getRollResult() >= 10) {
            character.setHasPlusOneForward(true);
        }

        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
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

        characterService.save(character);
        gameRole.setCharacters(List.of(character));
        gameRoleService.save(gameRole);
        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
    }

    @Override
    public Game changeScript(String gameId, ScriptChangeType scriptChangeType, String comment) {
        Game game = getGame(gameId);

        String title = "";
        String content = comment == null || comment.equals("") ? "" : "Comment: _**" + comment + "**_\n \n";

        switch (scriptChangeType) {
            case REWIND:
                title = SCRIPT_CHANGE_REWIND_TITLE;
                content += SCRIPT_CHANGE_REWIND_CONTENT + "\n\n";
                break;
            case FAST_FORWARD:
                title = SCRIPT_CHANGE_FAST_FORWARD_TITLE;
                content += SCRIPT_CHANGE_FAST_FORWARD_CONTENT + "\n\n";
                break;
            case PAUSE:
                title = SCRIPT_CHANGE_PAUSE_TITLE;
                content += SCRIPT_CHANGE_PAUSE_CONTENT + "\n\n";
                break;
            case FRAME_BY_FRAME:
                title = SCRIPT_CHANGE_FRAME_TITLE;
                content += SCRIPT_CHANGE_FRAME_CONTENT + "\n\n";
                break;
            case RESUME:
                title = SCRIPT_CHANGE_RESUME_TITLE;
                content += SCRIPT_CHANGE_RESUME_CONTENT + "\n\n";
                break;
            case INSTANT_REPLAY:
                title = SCRIPT_CHANGE_REPLAY_TITLE;
                content += SCRIPT_CHANGE_REPLAY_CONTENT + "\n\n";
                break;
        }

        GameMessage gameMessage = GameMessage.builder()
                .id(new ObjectId().toString())
                .gameId(gameId)
                .messageType(MessageType.SCRIPT_CHANGE)
                .sentOn(Instant.now().toString())
                .title(title)
                .content(content)
                .build();

        game.getGameMessages().add(gameMessage);
        return gameRepository.save(game);
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
                .id(new ObjectId().toString())
                .gameId(gameId)
                .gameRoleId(gameroleId)
                .messageType(messageType)
                .sentOn(Instant.now().toString())
                .roll1(roll1)
                .roll2(roll2)
                .build();
    }

    private Game getGame(String gameId) {
        return gameRepository.findById(gameId).orElseThrow(NoSuchElementException::new);
    }

    private CharacterMove getCharacterMoveByName(Character character, String moveName) {
        return character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getName().equals(moveName))
                .findFirst().orElseThrow();
    }

    private Character getCharacter(GameRole gameRole, String characterId) {
        return gameRole.getCharacters()
                .stream().filter(character1 -> character1.getId().equals(characterId))
                .findFirst().orElseThrow();
    }


}
