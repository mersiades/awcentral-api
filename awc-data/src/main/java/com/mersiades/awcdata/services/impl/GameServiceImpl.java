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

@Service
public class GameServiceImpl implements GameService {

    @Value("${spring.profiles.active:}")
    private String activeProfiles;

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
    public Mono<Game> setGameName(String gameId, String name) {
        return gameRepository.findById(gameId).flatMap(game1 -> {
            game1.setName(name);
            return gameRepository.save(game1);
        });
    }

    @Override
    public Flux<Game> findAllByInvitee(String email) {
        // Add a demo game if running demo profile
        if (activeProfiles != null && activeProfiles.equals("demo")) {
            GameRole nateGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(RoleType.MC).build();
            GameRole claireGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(RoleType.PLAYER).build();
            GameRole ruthGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(RoleType.PLAYER).build();

            User nate = User.builder().id(UUID.randomUUID().toString())
                    .email("nate@email.com").displayName("Nate").gameRoles(List.of(nateGameRole)).build();
            User claire = User.builder().id(UUID.randomUUID().toString())
                    .email("claire@email.com").displayName("Claire").gameRoles(List.of(claireGameRole)).build();
            User ruth = User.builder().id(UUID.randomUUID().toString())
                    .email("ruth@email.com").displayName("Ruth").gameRoles(List.of(ruthGameRole)).build();

            Look angel2 = new Look(PlaybookType.ANGEL, LookType.GENDER, "woman");
            Look angel6 = new Look(PlaybookType.ANGEL, LookType.CLOTHES, "utility wear");
            Look angel10 = new Look(PlaybookType.ANGEL, LookType.FACE, "strong face");
            Look angel15 = new Look(PlaybookType.ANGEL, LookType.EYES, "quick eyes");
            Look angel21 = new Look(PlaybookType.ANGEL, LookType.BODY, "compact body");

            CharacterStat angelCool = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.COOL).value(1).isHighlighted(false).build();
            CharacterStat angelHard = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.HARD).value(0).isHighlighted(true).build();
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
                    .build();

            Look brainer3 = new Look(PlaybookType.BRAINER, LookType.GENDER, "ambiguous");
            Look brainer6 = Look.builder().playbookType(PlaybookType.BRAINER)
                    .category(LookType.CLOTHES).look("high formal wear").build();
            Look brainer12 = Look.builder().playbookType(PlaybookType.BRAINER)
                    .category(LookType.FACE).look("pale face").build();
            Look brainer17 = Look.builder().playbookType(PlaybookType.BRAINER)
                    .category(LookType.EYES).look("dead eyes").build();
            Look brainer23 = Look.builder().playbookType(PlaybookType.BRAINER)
                    .category(LookType.BODY).look("awkward angular body").build();

            CharacterStat brainerCool = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.COOL).value(1).isHighlighted(false).build();
            CharacterStat brainerHard = CharacterStat.builder().id(UUID.randomUUID().toString())
                    .stat(StatType.HARD).value(1).isHighlighted(true).build();
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
                    .statsBlock(brainerStatsBlock)
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

    @Override
    public Mono<Game> performPrintMove(String gameId, String gameroleId, String characterId, String moveId) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        GameMessage gameMessage = GameMessage.builder()
                .id(UUID.randomUUID().toString())
                .gameId(gameId)
                .gameroleId(gameroleId)
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

        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performStatRollMove(String gameId, String gameroleId, String characterId, String moveId) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_STAT_MOVE);

        // This method handles both Moves and CharacterMoves.
        // First it tries to find the move in the List of CharacterMoves
        // If it doesn't find the move there, it searches the Moves collection in the db
        Optional<CharacterMove> moveOptional = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getId().equals(moveId)).findFirst();
        CharacterStat modifier;
        if (moveOptional.isPresent()) {
            modifier = getStatToRoll(character, moveOptional.get().getName(), moveOptional.get().getMoveAction().getStatToRollWith());
            gameMessage.setContent(moveOptional.get().getDescription());
            gameMessage.setTitle(String.format("%s: %s", character.getName(), moveOptional.get().getName()).toUpperCase());
        } else {
            Move move = moveService.findById(moveId).block();
            assert move != null;
            modifier = getStatToRoll(character, move.getName(), move.getMoveAction().getStatToRollWith());
            gameMessage.setTitle(String.format("%s: %s", character.getName(), move.getName()).toUpperCase());
            gameMessage.setContent(move.getDescription());
        }
        gameMessage.setRollModifier(modifier.getValue());
        gameMessage.setModifierStatName(modifier.getStat());
        gameMessage.setRollResult(gameMessage.getRoll1() + gameMessage.getRoll2() + modifier.getValue());
        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> performHxRollMove(String gameId, String gameroleId, String characterId, String moveId, String targetId) {
        Character character = characterService.findById(characterId).block();
        assert character != null;

        GameMessage gameMessage = getGameMessageWithDiceRolls(gameId, gameroleId, MessageType.ROLL_HX_MOVE);

        // Is the move a CharacterMove?
        Optional<CharacterMove> moveOptional = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getId().equals(moveId)).findFirst();

        if (moveOptional.isPresent()) {
            // Check if Character has Move to roll with a different stat
            CharacterStat alternateStat = checkForHxAlternative(character, moveOptional.get().getName());

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
            CharacterStat alternateStat = checkForHxAlternative(character, move.getName());

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
        return gameRepository.findById(gameId).map(game -> {
            game.getGameMessages().add(gameMessage);
            return game;
        }).flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> findByIdWithLimit(String gameId, Integer skip, Integer limit) {
        return gameRepository.findById(gameId, skip, limit);
    }

    private CharacterStat checkForHxAlternative(Character character, String moveName) {
        Optional<RollModifier> optionalRollModifier = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getRollModifier() != null && characterMove.getRollModifier().getMoveToModify().getName().equals(moveName))
                .map(Move::getRollModifier).findFirst();

        if (optionalRollModifier.isPresent()) {
            StatType statToFind = optionalRollModifier.get().getStatToRollWith();
            return character.getStatsBlock().getStats()
                    .stream().filter(characterStat -> characterStat.getStat().equals(statToFind)).findFirst().orElseThrow();
        } else {
            return null;
        }


    }


    private CharacterStat getStatToRoll(Character character, String moveName, StatType statToRollWith ) {
        // Only some characters will have a RollModifier for the Move they are rolling
        Optional<RollModifier> rollModifierOptional = character.getCharacterMoves()
                .stream().filter(characterMove -> characterMove.getRollModifier() != null && characterMove.getRollModifier().getMoveToModify().getName().equals(moveName))
                .map(Move::getRollModifier).findFirst();

        StatType statToFind;

        if (rollModifierOptional.isPresent()) {
            statToFind = rollModifierOptional.get().getStatToRollWith();
        } else {
            statToFind = statToRollWith;
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
                .gameroleId(gameroleId)
                .messageType(messageType)
                .sentOn(Instant.now().toString())
                .roll1(roll1)
                .roll2(roll2)
                .build();
    }

}
