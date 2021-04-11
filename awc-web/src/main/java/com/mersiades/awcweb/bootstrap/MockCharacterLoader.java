package com.mersiades.awcweb.bootstrap;

import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.models.uniquecreators.GangCreator;
import com.mersiades.awccontent.services.*;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.models.uniques.BrainerGear;
import com.mersiades.awcdata.models.uniques.CustomWeapons;
import com.mersiades.awcdata.models.uniques.Gang;
import com.mersiades.awcdata.repositories.CharacterRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mersiades.awcweb.bootstrap.MockUserLoader.*;


@Component
@Order(value = 2)
@Profile("dev")
public class MockCharacterLoader implements CommandLineRunner {

    final String KEYCLOAK_ID_1 = System.getenv("DAVE_ID");
    final String KEYCLOAK_ID_2 = System.getenv("SARA_ID");

//    final String SARA_AS_PLAYER_ID = "be6b09af-9c96-452a-8b05-922be820c88f";
//    final String JOHN_AS_PLAYER_ID = "5ffe67b72e21523778660910)";
//    final String MAYA_AS_PLAYER_ID = "5ffe67b72e21523778660911)";
//    final String AHMAD_AS_PLAYER_ID = "5ffe67b72e21523778660912)";
//    final String TAKESHI_AS_PLAYER_ID = "5ffe67b72e21523778660913)";

    private final GameRoleService gameRoleService;
    private final LookService lookService;
    private final CharacterService characterService;
    private final StatsOptionService statsOptionService;
    private final MoveService moveService;
    private final PlaybookCreatorService playbookCreatorService;
    private final VehicleCreatorService vehicleCreatorService;
    private final ThreatCreatorService threatCreatorService;

    @Autowired
    CharacterRepository characterRepository;

    public MockCharacterLoader(GameRoleService gameRoleService,
                               LookService lookService,
                               CharacterService characterService,
                               StatsOptionService statsOptionService,
                               MoveService moveService,
                               PlaybookCreatorService playbookCreatorService,
                               VehicleCreatorService vehicleCreatorService, ThreatCreatorService threatCreatorService) {
        this.gameRoleService = gameRoleService;
        this.lookService = lookService;
        this.characterService = characterService;
        this.statsOptionService = statsOptionService;
        this.moveService = moveService;
        this.playbookCreatorService = playbookCreatorService;
        this.vehicleCreatorService = vehicleCreatorService;
        this.threatCreatorService = threatCreatorService;
    }

    @Override
    public void run(String... args) {
        long characterCount = characterRepository.count();
        if (characterCount == 0) {
            loadMockCharacters();
            loadHx();
            loadThreats(); // characterCount is serving as a proxy for threatCount here
            loadNpcs(); // characterCount is serving as a proxy for npcCount here
        }

        System.out.println("Character count: " + characterRepository.count());
    }

    private void loadMockCharacters() {
        GameRole saraAsPlayer = gameRoleService.findById(SARA_AS_PLAYER_ID);
        assert saraAsPlayer != null;
        GameRole johnAsPlayer = gameRoleService.findById(JOHN_AS_PLAYER_ID);
        assert johnAsPlayer != null;
        GameRole mayaAsPlayer = gameRoleService.findById(MAYA_AS_PLAYER_ID);
        assert mayaAsPlayer != null;
        GameRole ahmadAsPlayer = gameRoleService.findById(AHMAD_AS_PLAYER_ID);
        assert ahmadAsPlayer != null;
        GameRole takeshiAsPlayer = gameRoleService.findById(TAKESHI_AS_PLAYER_ID);
        assert takeshiAsPlayer != null;

        CharacterHarm harm = CharacterHarm.builder()
                .hasChangedPlaybook(false)
                .hasComeBackHard(false)
                .hasComeBackWeird(false)
                .hasDied(false)
                .isStabilized(false)
                .value(0)
                .build();

        VehicleCreator vehicleCreator = vehicleCreatorService.findAll().get(0);
        assert vehicleCreator != null;

        // -------------------------------- Set up Sara's Angel ----------------------------------- //
        List<Look> angelLooks = getLooks(PlaybookType.ANGEL);

        StatsBlock angelStatsBlock1 = getStatsBlock(PlaybookType.ANGEL);

        List<Move> angelKitMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.UNIQUE)
                ;

        AngelKit angelKit = AngelKit.builder().id(new ObjectId().toString())
                .hasSupplier(false)
                .angelKitMoves(angelKitMoves)
                .stock(2).build();

        PlaybookUnique angelUnique = PlaybookUnique.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.ANGEL_KIT)
                .angelKit(angelKit)
                .build();

        List<Move> angelMoves = moveService.findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals("SIXTH SENSE") ||
                        move.getName().equals("HEALING TOUCH")).collect(Collectors.toList());

        List<Move> angelDefaultMoves = moveService.
                findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.DEFAULT_CHARACTER);
        assert angelDefaultMoves != null;

        List<CharacterMove> characterMoves = createAndMergeCharacterMoves(angelMoves, angelDefaultMoves);

        // -------------------------------- Set up John's Battlebabe ----------------------------------- //
        List<Look> battlebabeLooks = getLooks(PlaybookType.BATTLEBABE);

        StatsBlock battlebabeStatsBlock = getStatsBlock(PlaybookType.BATTLEBABE);

        CustomWeapons customWeapons = CustomWeapons.builder()
                .id(new ObjectId().toString())
                .weapons(List.of("antique rifle (2-harm, load, valuable", "Ornate staff (1-harm, valuable)"))
                .build();

        PlaybookUnique battlebabeUnique = PlaybookUnique.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.CUSTOM_WEAPONS)
                .customWeapons(customWeapons)
                .build();

        List<Move> battlebabeMoves = moveService.findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals("DANGEROUS & SEXY") ||
                        move.getName().equals("PERFECT INSTINCTS")).collect(Collectors.toList());

        List<Move> battlebabeDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.DEFAULT_CHARACTER);
        assert battlebabeDefaultMoves != null;

        List<CharacterMove> characterMoves2 = createAndMergeCharacterMoves(battlebabeMoves, battlebabeDefaultMoves);

        // -------------------------------- Set up Maya's Brainer ----------------------------------- //
        List<Look> brainerLooks = getLooks(PlaybookType.BRAINER);

        StatsBlock brainerStatsBlock = getStatsBlock(PlaybookType.BRAINER);

        BrainerGear brainerGear = BrainerGear.builder()
                .id(new ObjectId().toString())
                .brainerGear(List.of("brain relay", "violation glove"))
                .build();

        PlaybookUnique brainerUnique = PlaybookUnique.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.BRAINER_GEAR)
                .brainerGear(brainerGear)
                .build();

        List<Move> brainerMoves = moveService.findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals("DEEP BRAIN SCAN") ||
                        move.getName().equals("PRETERNATURAL BRAIN ATTUNEMENT")).collect(Collectors.toList());

        List<Move> brainerDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.DEFAULT_CHARACTER);
        assert brainerDefaultMoves != null;

        List<CharacterMove> characterMoves3 = createAndMergeCharacterMoves(brainerMoves, brainerDefaultMoves);

        // -------------------------------- Set up Takeshi's's Chopper ----------------------------------- //
        GangCreator gangCreator = Objects.requireNonNull(playbookCreatorService.findByPlaybookType(PlaybookType.CHOPPER)
                ).getPlaybookUniqueCreator().getGangCreator();

        GangOption gangOption1 = gangCreator.getStrengths().get(0);
        GangOption gangOption2 = gangCreator.getStrengths().get(1);
        GangOption gangOption3 = gangCreator.getWeaknesses().get(0);

        List<Look> chopperLooks = getLooks(PlaybookType.CHOPPER);

        StatsBlock chopperStatsBlock = getStatsBlock(PlaybookType.CHOPPER);

        Vehicle chopperBike = Vehicle.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.BIKE)
                .name("Honda")
                // BIKE frame
                .vehicleFrame(vehicleCreator.getBikeCreator().getFrame())
                // +1handling
                .battleOptions(List.of(vehicleCreator.getBikeCreator().getBattleOptions().get(1)))
                .massive(0)
                .speed(0)
                .armor(0)
                .handling(1)
                .strengths(List.of("fast", "responsive"))
                .looks(List.of("flashy", "luxe"))
                .weaknesses(List.of("lazy", "sloppy"))
                .build();

        Gang gang = Gang.builder()
                .id(new ObjectId().toString())
                .size(GangSize.MEDIUM)
                .harm(3)
                .armor(1)
                .tags(List.of("savage", "vulnerable: breakdown"))
                .strengths(List.of(gangOption1, gangOption2))
                .weaknesses(List.of(gangOption3))
                .build();

        PlaybookUnique chopperUnique = PlaybookUnique.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.GANG)
                .gang(gang)
                .build();

        List<Move> chopperDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.CHOPPER, MoveType.DEFAULT_CHARACTER);
        assert chopperDefaultMoves != null;

        List<CharacterMove> characterMoves5 = new ArrayList<>();
        chopperDefaultMoves.forEach(move -> characterMoves5.add(CharacterMove.createFromMove(move)));

        // -------------------------------- Set up Ahmad's Driver ----------------------------------- //
        List<Look> driverLooks = getLooks(PlaybookType.DRIVER);

        StatsBlock driverStatsBlock = getStatsBlock(PlaybookType.DRIVER);

        List<Move> driverMoves = moveService.findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals("EYE ON THE DOOR") ||
                        move.getName().equals("COLLECTOR")).collect(Collectors.toList());

        List<Move> driverDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.DEFAULT_CHARACTER);
        assert driverDefaultMoves != null;

        List<CharacterMove> characterMoves4 = createAndMergeCharacterMoves(driverMoves, driverDefaultMoves);

        PlaybookCreator driverCreator = playbookCreatorService.findByPlaybookType(PlaybookType.DRIVER);
        assert driverCreator != null;


        Vehicle car1 = Vehicle.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.CAR)
                .name("Unnamed vehicle")
                // SMALL frame
                .vehicleFrame(vehicleCreator.getCarCreator().getFrames().get(1))
                // +1speed, +1armor
                .battleOptions(List.of(
                        vehicleCreator.getCarCreator().getBattleOptions().get(0),
                        vehicleCreator.getCarCreator().getBattleOptions().get(3)))
                .massive(1)
                .speed(1)
                .armor(1)
                .handling(0)
                .strengths(List.of("fast"))
                .looks(List.of("quirky", "vintage"))
                .weaknesses(List.of("loud"))
                .build();

        Vehicle car2 = Vehicle.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.CAR)
                .name("Bess")
                // LARGE frame
                .vehicleFrame(vehicleCreator.getCarCreator().getFrames().get(3))
                // +1massive, +1armor
                .battleOptions(List.of(
                        vehicleCreator.getCarCreator().getBattleOptions().get(2),
                        vehicleCreator.getCarCreator().getBattleOptions().get(3)))
                .massive(4)
                .speed(0)
                .armor(1)
                .handling(0)
                .strengths(List.of("rugged", "aggressive"))
                .weaknesses(List.of("cramped", "picky"))
                .looks(List.of("sleek", "powerful"))
                .build();

        Vehicle bike = Vehicle.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.BIKE)
                .name("Ducati Monster")
                // BIKE frame
                .vehicleFrame(vehicleCreator.getBikeCreator().getFrame())
                // +1speed
                .battleOptions(List.of(vehicleCreator.getBikeCreator().getBattleOptions().get(0)))
                .massive(0)
                .speed(1)
                .armor(0)
                .handling(0)
                .strengths(List.of("tight", "huge"))
                .looks(List.of("massively-chopped", "fat-ass"))
                .weaknesses(List.of("bucking", "unreliable"))
                .build();



        // -------------------------------- Create Sara's Angel ----------------------------------- //
        Character mockCharacter1 = Character.builder()
                .name("Doc")
                .playbook(PlaybookType.ANGEL)
                .looks(angelLooks)
                .gear(List.of("Shotgun", "Rusty screwdriver"))
                .statsBlock(angelStatsBlock1)
                .barter(2)
                .playbookUnique(angelUnique)
                .characterMoves(characterMoves)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(true)
                .vehicleCount(0)
                .battleVehicleCount(0)
                .build();

        // -------------------------------- Create John's Battlebabe ----------------------------------- //
        Character mockCharacter2 = Character.builder()
                .name("Scarlet")
                .playbook(PlaybookType.BATTLEBABE)
                .looks(battlebabeLooks)
                .gear(List.of("Black leather boots", "Broken motorcycle helmet"))
                .statsBlock(battlebabeStatsBlock)
                .barter(2)
                .playbookUnique(battlebabeUnique)
                .characterMoves(characterMoves2)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(0)
                .battleVehicleCount(0)
                .build();

        // -------------------------------- Create Maya's Brainer ----------------------------------- //
        Character mockCharacter3 = Character.builder()
                .name("Smith")
                .playbook(PlaybookType.BRAINER)
                .looks(brainerLooks)
                .gear(List.of("Sharp kitchen knife", "Wireless radio"))
                .statsBlock(brainerStatsBlock)
                .barter(2)
                .playbookUnique(brainerUnique)
                .characterMoves(characterMoves3)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(0)
                .battleVehicleCount(0)
                .build();

        // -------------------------------- Create Takeshi's Chopper ----------------------------------- //
        Character mockCharacter5 = Character.builder()
                .name("Dog")
                .playbook(PlaybookType.CHOPPER)
                .looks(chopperLooks)
                .gear(List.of("magnum (3-harm close reload loud)", "machete (3-harm hand messy"))
                .statsBlock(chopperStatsBlock)
                .barter(2)
                .playbookUnique(chopperUnique)
                .characterMoves(characterMoves5)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(1)
                .battleVehicleCount(0)
                .vehicles(List.of(chopperBike))
                .build();

        // -------------------------------- Create Ahmad's Driver ----------------------------------- //
        Character mockCharacter4 = Character.builder()
                .name("Phoenix")
                .playbook(PlaybookType.DRIVER)
                .looks(driverLooks)
                .gear(List.of("Leather jacket", ".38 revolver"))
                .statsBlock(driverStatsBlock)
                .barter(4)
                .characterMoves(characterMoves4)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(3)
                .battleVehicleCount(0)
                .vehicles(List.of(car1, car2, bike))
                .build();

        // ----------------------- Add Characters to players and save ----------------------------- //

        saveCharacter(saraAsPlayer, harm, mockCharacter1);
        saveCharacter(johnAsPlayer, harm, mockCharacter2);
        saveCharacter(mayaAsPlayer, harm, mockCharacter3);
        saveCharacter(ahmadAsPlayer, harm, mockCharacter4);
        saveCharacter(takeshiAsPlayer, harm, mockCharacter5);
    }

    private void loadHx() {
        GameRole saraAsPlayer = gameRoleService.findById(SARA_AS_PLAYER_ID);
        assert saraAsPlayer != null;
        GameRole johnAsPlayer = gameRoleService.findById(JOHN_AS_PLAYER_ID);
        assert johnAsPlayer != null;
        GameRole mayaAsPlayer = gameRoleService.findById(MAYA_AS_PLAYER_ID);
        assert mayaAsPlayer != null;
        GameRole ahmadAsPlayer = gameRoleService.findById(AHMAD_AS_PLAYER_ID);
        assert ahmadAsPlayer != null;
        GameRole takeshiAsPlayer = gameRoleService.findById(TAKESHI_AS_PLAYER_ID);
        assert takeshiAsPlayer != null;

        Character doc = saraAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert doc != null;
        Character scarlet = johnAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert scarlet != null;
        Character smith = mayaAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert smith != null;
        Character nee = ahmadAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert nee != null;
        Character dog = takeshiAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert dog != null;

        // ------------------------------ Doc's Hx --------------------------------- //
        HxStat doc1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(-2).build();

        HxStat doc2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(2).build();

        HxStat doc3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(3).build();

        HxStat doc4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(dog.getId()).characterName(dog.getName()).hxValue(1).build();
        doc.setHxBlock(List.of(doc1, doc2, doc3, doc4));

        // ------------------------------ Scarlet's Hx --------------------------------- //
        HxStat scarlet1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-1).build();

        HxStat scarlet2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(3).build();

        HxStat scarlet3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(-1).build();

        HxStat scarlet4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(dog.getId()).characterName(dog.getName()).hxValue(0).build();

        scarlet.setHxBlock(List.of(scarlet1, scarlet2, scarlet3, scarlet4));

        // ------------------------------ Smith's Hx --------------------------------- //
        HxStat smith1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(2).build();

        HxStat smith2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat smith3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(3).build();

        HxStat smith4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(dog.getId()).characterName(dog.getName()).hxValue(1).build();

        smith.setHxBlock(List.of(smith1, smith2, smith3, smith4));

        // ------------------------------ Nee's Hx --------------------------------- //
        HxStat nee1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-2).build();

        HxStat nee2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat nee3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(3).build();

        HxStat nee4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(dog.getId()).characterName(dog.getName()).hxValue(-1).build();

        nee.setHxBlock(List.of(nee1, nee2, nee3, nee4));

        // ------------------------------ Dog's Hx --------------------------------- //
        HxStat dog1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-2).build();

        HxStat dog2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat dog3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(3).build();

        HxStat dog4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(-1).build();

        dog.setHxBlock(List.of(dog1, dog2, dog3, dog4));

        // ------------------------------ Save to db --------------------------------- //
        characterService.saveAll(List.of(doc, scarlet, smith, nee, dog));
        gameRoleService.saveAll(List.of(saraAsPlayer, johnAsPlayer, mayaAsPlayer, ahmadAsPlayer, takeshiAsPlayer));

    }

    private void loadThreats() {
        GameRole daveAsMC = gameRoleService.findAllByUserId(KEYCLOAK_ID_1)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();
        assert daveAsMC != null;
        GameRole saraAsMC = gameRoleService.findAllByUserId(KEYCLOAK_ID_2)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();
        assert saraAsMC != null;
        ThreatCreator threatCreator = threatCreatorService.findAll().get(0);
        assert threatCreator != null;

        Threat mockThreat1 = Threat.builder()
                .id(new ObjectId().toString())
                .name("Tum Tum")
                .threatKind(ThreatType.WARLORD)
                .impulse(threatCreator.getThreats().stream()
                        .filter(threatCreatorContent -> threatCreatorContent.getThreatType().equals(ThreatType.WARLORD))
                        .findFirst().orElseThrow().getImpulses().get(0))
                .description("consectetur adipiscing elit. Cras semper augue est, vel consequat dolor volutpat in")
                .stakes("Maecenas vitae consequat justo, quis sollicitudin nulla. Phasellus pulvinar nunc eget mauris tristique, ut aliquam felis mattis. Nulla ultricies feugiat arcu non facilisis.")
                .build();
        Threat mockThreat2 = Threat.builder()
                .id(new ObjectId().toString())
                .name("Gnarly")
                .threatKind(ThreatType.GROTESQUE)
                .impulse(threatCreator.getThreats().stream()
                        .filter(threatCreatorContent -> threatCreatorContent.getThreatType().equals(ThreatType.GROTESQUE))
                        .findFirst().orElseThrow().getImpulses().get(0))
                .description("Maecenas tempus ac felis at sollicitudin. Etiam pulvinar, nibh eget fringilla pretium, sem sem ultricies augue, vitae condimentum enim nibh nec mi.")
                .build();
        Threat mockThreat3 = Threat.builder()
                .id(new ObjectId().toString())
                .name("Fleece")
                .threatKind(ThreatType.BRUTE)
                .impulse(threatCreator.getThreats().stream()
                        .filter(threatCreatorContent -> threatCreatorContent.getThreatType().equals(ThreatType.BRUTE))
                        .findFirst().orElseThrow().getImpulses().get(0))
                .description("Maecenas tempus ac felis at sollicitudin. Etiam pulvinar, nibh eget fringilla pretium, sem sem ultricies augue, vitae condimentum enim nibh nec mi.")
                .build();
        Threat mockThreat4 = Threat.builder()
                .id(new ObjectId().toString())
                .name("Wet Rot")
                .threatKind(ThreatType.AFFLICTION)
                .impulse(threatCreator.getThreats().stream()
                        .filter(threatCreatorContent -> threatCreatorContent.getThreatType().equals(ThreatType.AFFLICTION))
                        .findFirst().orElseThrow().getImpulses().get(0))
                .description("Maecenas tempus ac felis at sollicitudin. Etiam pulvinar, nibh eget fringilla pretium, sem sem ultricies augue, vitae condimentum enim nibh nec mi.")
                .build();


        daveAsMC.getThreats().add(mockThreat1);
        daveAsMC.getThreats().add(mockThreat2);
        saraAsMC.getThreats().add(mockThreat3);
        saraAsMC.getThreats().add(mockThreat4);
        gameRoleService.saveAll(List.of(daveAsMC, saraAsMC));
    }

    private void loadNpcs() {
        GameRole daveAsMC = gameRoleService.findAllByUserId(KEYCLOAK_ID_1)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();
        assert daveAsMC != null;
        GameRole saraAsMC = gameRoleService.findAllByUserId(KEYCLOAK_ID_2)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();
        assert saraAsMC != null;
        ThreatCreator threatCreator = threatCreatorService.findAll().get(0);
        assert threatCreator != null;

        Npc mockNpc1 = Npc.builder()
                .id(new ObjectId().toString())
                .name("Vision")
                .description("Badass truck; driver").build();
        Npc mockNpc2 = Npc.builder()
                .id(new ObjectId().toString())
                .name("Nbeke").build();
        Npc mockNpc3 = Npc.builder()
                .id(new ObjectId().toString())
                .name("Batty")
                .description("Overly polite gun for hire").build();
        Npc mockNpc4 = Npc.builder()
                .id(new ObjectId().toString())
                .name("Farley").build();

        daveAsMC.getNpcs().add(mockNpc1);
        daveAsMC.getNpcs().add(mockNpc2);
        saraAsMC.getNpcs().add(mockNpc3);
        saraAsMC.getNpcs().add(mockNpc4);
        gameRoleService.saveAll(List.of(saraAsMC, daveAsMC));
    }

    private List<CharacterMove> createAndMergeCharacterMoves(List<Move> choiceMoves, List<Move> defaultMoves) {

        List<CharacterMove> characterMoves = choiceMoves
                .stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        List<CharacterMove> characterDefaultMoves = defaultMoves
                .stream().map(CharacterMove::createFromMove).collect(Collectors.toList());

        characterMoves.addAll(characterDefaultMoves);
        return characterMoves;
    }

    private List<Look> getLooks(PlaybookType playbookType) {
        List<Look> looks = lookService.findAllByPlaybookType(playbookType);
        assert looks != null;

        Look genderLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookType.GENDER)).findFirst().orElseThrow();
        Look clothesLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookType.CLOTHES)).findFirst().orElseThrow();
        Look bodyLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookType.BODY)).findFirst().orElseThrow();
        Look faceLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookType.FACE)).findFirst().orElseThrow();
        Look eyesLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookType.EYES)).findFirst().orElseThrow();

        return List.of(genderLook, bodyLook, clothesLook, faceLook, eyesLook);
    }

    private StatsBlock getStatsBlock(PlaybookType playbookType) {
        StatsOption statsOption = statsOptionService.findAllByPlaybookType(playbookType).get(0);
        assert statsOption != null;

        CharacterStat cool = CharacterStat.builder().id(new ObjectId().toString())
                .stat(StatType.COOL).value(statsOption.getCOOL()).isHighlighted(false).build();
        CharacterStat hard = CharacterStat.builder().id(new ObjectId().toString())
                .stat(StatType.HARD).value(statsOption.getHARD()).isHighlighted(true).build();
        CharacterStat hot = CharacterStat.builder().id(new ObjectId().toString())
                .stat(StatType.HOT).value(statsOption.getHOT()).isHighlighted(true).build();
        CharacterStat sharp = CharacterStat.builder().id(new ObjectId().toString())
                .stat(StatType.SHARP).value(statsOption.getSHARP()).isHighlighted(false).build();
        CharacterStat weird = CharacterStat.builder().id(new ObjectId().toString())
                .stat(StatType.WEIRD).value(statsOption.getWEIRD()).isHighlighted(false).build();

        return StatsBlock.builder().id(new ObjectId().toString())
                .statsOptionId(statsOption.getId())
                .stats(List.of(cool, hard, hot, sharp, weird))
                .build();
    }

    private void saveCharacter(GameRole gameRole, CharacterHarm harm, Character character) {
        if (gameRole.getCharacters().size() == 0) {
            harm.setId(new ObjectId().toString());
            character.setHarm(harm);
            character.setExperience(0);
            gameRole.getCharacters().add(character);
            characterService.save(character);
            gameRoleService.save(gameRole);
        }
    }
}
