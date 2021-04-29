package com.mersiades.awcweb.bootstrap;

import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.models.uniquecreators.*;
import com.mersiades.awccontent.repositories.*;
import com.mersiades.awccontent.services.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.mersiades.awccontent.constants.MoveNames.*;
import static com.mersiades.awccontent.content.MovesContent.sufferVHarm;
import static com.mersiades.awccontent.content.MovesContent.*;

@Component
@Order(value = 0)
@Profile("!test")
public class GameDataLoader implements CommandLineRunner {

    private final PlaybookCreatorService playbookCreatorService;
    private final PlaybookService playbookService;
    private final NameService nameService;
    private final LookService lookService;
    private final StatsOptionService statsOptionService;
    private final MoveService moveService;
    private final StatModifierService statModifierService;
    private final VehicleCreatorService vehicleCreatorService;
    private final ThreatCreatorService threatCreatorService;
    private final McContentService mcContentService;

    @Autowired
    LookRepository lookRepository;

    @Autowired
    MoveRepository moveRepository;

    @Autowired
    NameRepository nameRepository;

    @Autowired
    PlaybookCreatorRepository playbookCreatorRepository;

    @Autowired
    PlaybookRepository playbookRepository;

    @Autowired
    StatsOptionRepository statsOptionRepository;

    @Autowired
    VehicleCreatorRepository vehicleCreatorRepository;

    @Autowired
    ThreatCreatorRepository threatCreatorRepository;

    @Autowired
    McContentRepository mcContentRepository;

    public GameDataLoader(PlaybookCreatorService playbookCreatorService,
                          PlaybookService playbookService,
                          NameService nameService,
                          LookService lookService,
                          StatsOptionService statsOptionService,
                          MoveService moveService,
                          StatModifierService statModifierService,
                          VehicleCreatorService vehicleCreatorService,
                          ThreatCreatorService threatCreatorService,
                          McContentService mcContentService) {
        this.playbookCreatorService = playbookCreatorService;
        this.playbookService = playbookService;
        this.nameService = nameService;
        this.lookService = lookService;
        this.statsOptionService = statsOptionService;
        this.moveService = moveService;
        this.statModifierService = statModifierService;
        this.vehicleCreatorService = vehicleCreatorService;
        this.threatCreatorService = threatCreatorService;
        this.mcContentService = mcContentService;
    }

    @Override
    public void run(String... args) {
        // Because only checking if empty before loading/creating items,
        // I'll need to drop db whenever adding new game data
        List<Move> moves = moveRepository.findAll();
        if (moves.isEmpty()) {
            loadMoves();
        }

        List<Name> names = nameRepository.findAll();
        if (names.isEmpty()) {
            loadNames();
        }

        List<Look> looks = lookRepository.findAll();
        if (looks.isEmpty()) {
            loadLooks();
        }

        List<StatsOption> statsOptions = statsOptionRepository.findAll();
        if (statsOptions.isEmpty()) {
            loadStatsOptions();
        }

        List<PlaybookCreator> playbookCreators = playbookCreatorRepository.findAll();
        if (playbookCreators.isEmpty()) {
            loadPlaybookCreators();
        }

        List<Playbook> playbooks = playbookRepository.findAll();
        if (playbooks.isEmpty()) {
            loadPlaybooks();
        }

        List<VehicleCreator> vehicleCreators = vehicleCreatorRepository.findAll();
        if (vehicleCreators.size() == 0) {
            loadVehicleCreator();
        }

        List<ThreatCreator> threatCreators = threatCreatorRepository.findAll();
        if (threatCreators.size() == 0) {
            loadThreatCreator();
        }

        List<McContent> mcContent = mcContentRepository.findAll();
        if (mcContent.size() == 0) {
            loadMcContent();
        }

        // 'Create if empty' conditionality is embedded in the createPlaybooks() method
        createPlaybooks();

        System.out.println("Look count: " + lookRepository.count());
        System.out.println("Move count: " + moveRepository.count());
        System.out.println("Name count: " + nameRepository.count());
        System.out.println("PlaybookCreator count: " + playbookCreatorRepository.count());
        System.out.println("CarCreator count: " + vehicleCreatorRepository.count());
        System.out.println("Playbook count: " + playbookRepository.count());
        System.out.println("VehicleCreator count: " + vehicleCreatorRepository.count());
        System.out.println("ThreatCreator count: " + threatCreatorRepository.count());
    }

    private void loadMoves() {

        statModifierService.saveAll(List.of(
                insanoMod,
                wacknutModifier,
                deepInsightsModifier,
                breathtakingModifier,
                sharpMax2Mod,
                coolMax2Mod,
                hardMax2Mod,
                hotMax2Mod,
                weirdMax2Mod,
                sharpMax3Mod,
                coolMax3Mod,
                hardMax3Mod,
                hotMax3Mod,
                weirdMax3Mod
        ));

        moveService.saveAll(List.of(
                // Basic moves
                doSomethingUnderFire,
                goAggro,
                sucker,
                doBattle,
                seduceOrManip,
                helpOrInterfere,
                readASitch,
                readAPerson,
                openBrain,
                lifestyleAndGigs,
                sessionEnd,
                // Peripheral moves
                sufferHarm,
                sufferVHarm,
                inflictHarmMove,
                healPcHarm,
                giveBarter,
                goToMarket,
                makeWantKnown,
                insight,
                augury,
                changeHighlightedStats,
                // Battle moves
                exchangeHarm,
                seizeByForce,
                assaultAPosition,
                keepHoldOfSomething,
                fightFree,
                defendSomeone,
                doSingleCombat,
                layDownFire,
                standOverwatch,
                keepAnEyeOut,
                beTheBait,
                beTheCat,
                beTheMouse,
                catOrMouseMove,
                // Road war moves
                boardAMovingVehicleMove,
                outdistanceAnotherVehicleMove,
                overtakeAnotherVehicleMove,
                dealWithBadTerrain,
                shoulderAnotherVehicle,
                // Angel moves
                angelSpecial,
                sixthSense,
                infirmary,
                profCompassion,
                battlefieldGrace,
                healingTouch,
                touchedByDeath,
                // Angel kit moves
                stabilizeAndHeal,
                speedTheRecoveryOfSomeone,
                reviveSomeone,
                treatAnNpc,
                // Battlebabe moves
                battlebabeSpecial,
                dangerousAndSexy,
                iceCold,
                merciless,
                visionsOfDeath,
                perfectInstincts,
                impossibleReflexes,
                // Brainer moves
                brainerSpecial,
                unnaturalLust,
                brainReceptivity,
                brainAttunement,
                brainScan,
                whisperProjection,
                puppetStrings,
                // Chopper moves
                chopperSpecial,
                packAlpha,
                fuckingThieves,
                // Driver moves
                driverSpecial,
                combatDriver,
                eyeOnTheDoor,
                weatherEye,
                reputationMove,
                daredevil,
                collectorMove,
                myOtherCarIsATank,
                // Gunlugger moves
                gunluggerSpecial,
                battleHardened,
                fuckThisShit,
                battlefieldInstincts,
                insanoLikeDrano,
                preparedForTheInevitable,
                bloodcrazed,
                notToBeFuckedWith,
                // Hardholder moves
                hardholderSpecial,
                leadership,
                wealth,
                // Hocus moves
                hocusSpecial,
                fortunes,
                frenzy,
                charismatic,
                fuckingWacknut,
                seeingSouls,
                divineProtection,
                // Maestro D' moves
                maestroSpecial,
                callThisHot,
                devilWithBlade,
                fingersInPie,
                everyBodyEats,
                justGiveMotive,
                // Savvyhead
                savvyheadSpecial,
                thingsSpeak,
                bonefeel,
                oftenerRight,
                frayingEdge,
                spookyIntense,
                deepInsights,
                // Skinner moves
                skinnerSpecial,
                breathtaking,
                artful,
                lost,
                anArrestingSkinner,
                hypnotic,
                // Generic stat improvement moves
                coolMax2,
                hardMax2,
                hotMax2,
                sharpMax2,
                weirdMax2,
                coolMax3,
                hardMax3,
                hotMax3,
                sharpMax3,
                weirdMax3,
                // Add character move improvement moves
                addAngelMove1,
                addAngelMove2,
                addBrainerMove1,
                addBrainerMove2,
                addBattleBabeMove1,
                addBattleBabeMove2,
                addBrainerMove1,
                addBrainerMove2,
                addDriverMove1,
                addDriverMove2,
                addGunluggerMove1,
                addGunluggerMove2,
                addHocusMove1,
                addHocusMove2,
                addMaestroDMove1,
                addMaestroDMove2,
                addSavvyheadMove1,
                addSavvyheadMove2,
                addSkinnerMove1,
                addSkinnerMove2,
                // Adjust unique improvement moves
                adjustAngelUnique1,
                adjustBrainerUnique1,
                adjustChopperUnique1,
                adjustChopperUnique2,
                adjustHardHolderUnique1,
                adjustHardHolderUnique2,
                adjustHardHolderUnique3,
                adjustHocusUnique1,
                adjustHocusUnique2,
                adjustMaestroDUnique1,
                adjustMaestroDUnique2,
                adjustSavvyheadUnique1,
                adjustSavvyheadUnique2,
                // Add other playbook move improvement moves
                addOtherPBMove1,
                addOtherPBMove2,
                // Add unique improvement moves
                addGangLeadership,
                addGangPackAlpha,
                addHolding,
                addWorkspace,
                addFollowers,
                addEstablishment,
                // Add vehicle improvement move
                addVehicle,
                // Ungiven future improvement moves
                genericIncreaseStat,
                retire,
                addSecondCharacter,
                changePlaybook,
                improveBasicMoves1,
                improveBasicMoves2
        ));
    }

    private void loadNames() {
        System.out.println("|| --- Loading playbook names --- ||");
        /* ----------------------------- ANGEL NAMES --------------------------------- */
        Name dou = Name.builder().playbookType(PlaybookType.ANGEL).name("Dou").build();
        Name bon = Name.builder().playbookType(PlaybookType.ANGEL).name("Bon").build();
        Name abe = Name.builder().playbookType(PlaybookType.ANGEL).name("Abe").build();
        Name boo = Name.builder().playbookType(PlaybookType.ANGEL).name("Boo").build();
        Name t = Name.builder().playbookType(PlaybookType.ANGEL).name("T").build();
        Name kal = Name.builder().playbookType(PlaybookType.ANGEL).name("Kal").build();
        Name charName = Name.builder().playbookType(PlaybookType.ANGEL).name("Char").build();
        Name jav = Name.builder().playbookType(PlaybookType.ANGEL).name("Jav").build();
        Name ruth = Name.builder().playbookType(PlaybookType.ANGEL).name("Ruth").build();
        Name wei = Name.builder().playbookType(PlaybookType.ANGEL).name("Wei").build();
        Name jay = Name.builder().playbookType(PlaybookType.ANGEL).name("Jay").build();
        Name nee = Name.builder().playbookType(PlaybookType.ANGEL).name("Nee").build();
        Name kim = Name.builder().playbookType(PlaybookType.ANGEL).name("Kim").build();
        Name lan = Name.builder().playbookType(PlaybookType.ANGEL).name("Lan").build();
        Name di = Name.builder().playbookType(PlaybookType.ANGEL).name("Di").build();
        Name dez = Name.builder().playbookType(PlaybookType.ANGEL).name("Dez").build();
        Name doc = Name.builder().playbookType(PlaybookType.ANGEL).name("Doc").build();
        Name core = Name.builder().playbookType(PlaybookType.ANGEL).name("Core").build();
        Name wheels = Name.builder().playbookType(PlaybookType.ANGEL).name("Wheels").build();
        Name buzz = Name.builder().playbookType(PlaybookType.ANGEL).name("Buzz").build();
        Name key = Name.builder().playbookType(PlaybookType.ANGEL).name("Key").build();
        Name line = Name.builder().playbookType(PlaybookType.ANGEL).name("Line").build();
        Name gabe = Name.builder().playbookType(PlaybookType.ANGEL).name("Gabe").build();
        Name biz = Name.builder().playbookType(PlaybookType.ANGEL).name("Biz").build();
        Name bish = Name.builder().playbookType(PlaybookType.ANGEL).name("Bish").build();
        Name inch = Name.builder().playbookType(PlaybookType.ANGEL).name("Inch").build();
        Name grip = Name.builder().playbookType(PlaybookType.ANGEL).name("Grip").build();
        Name setter = Name.builder().playbookType(PlaybookType.ANGEL).name("Setter").build();

        nameService.saveAll(List.of(dou, bon, abe, boo, t, kal, charName, jav, ruth, wei, jay, nee,
                kim, lan, di, dez, core, wheels, doc, buzz, key, line, gabe, biz, bish, inch, grip, setter))
        ;

        /* ----------------------------- BATTLEBABE NAMES --------------------------------- */
        Name snow = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Snow").build();
        Name crimson = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Crimson").build();
        Name shadow = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Shadow").build();
        Name azure = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Azure").build();
        Name midnight = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Midnight").build();
        Name scarlet = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Scarlet").build();
        Name violetta = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Violetta").build();
        Name amber = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Amber").build();
        Name rouge = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Rouge").build();
        Name damson = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Damson").build();
        Name sunset = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Sunset").build();
        Name emerald = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Emerald").build();
        Name ruby = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Ruby").build();
        Name raksha = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Raksha").build();
        Name kickskirt = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Kickskirt").build();
        Name kite = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Kite").build();
        Name monsoon = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Monsoon").build();
        Name smith = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Smith").build();
        Name beastie = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Beastie").build();
        Name baaba = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Baaba").build();
        Name melody = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Melody").build();
        Name mar = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Mar").build();
        Name tavi = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Tavi").build();
        Name absinthe = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Absinthe").build();
        Name honeytree = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Honeytree").build();

        nameService.saveAll(List.of(snow, crimson, shadow, beastie, azure, midnight, scarlet, violetta, amber, rouge,
                damson, sunset, emerald, ruby, raksha, kickskirt, kite, monsoon, smith, baaba, melody, mar, tavi,
                absinthe, honeytree));

        /* ----------------------------- BRAINER NAMES --------------------------------- */
        Name smith2 = Name.builder().playbookType(PlaybookType.BRAINER).name("Smith").build();
        Name jones = Name.builder().playbookType(PlaybookType.BRAINER).name("Jones").build();
        Name jackson = Name.builder().playbookType(PlaybookType.BRAINER).name("Jackson").build();
        Name marsh = Name.builder().playbookType(PlaybookType.BRAINER).name("Marsh").build();
        Name lively = Name.builder().playbookType(PlaybookType.BRAINER).name("Lively").build();
        Name burroughs = Name.builder().playbookType(PlaybookType.BRAINER).name("Burroughs").build();
        Name gritch = Name.builder().playbookType(PlaybookType.BRAINER).name("Gritch").build();
        Name joyette = Name.builder().playbookType(PlaybookType.BRAINER).name("Joyette").build();
        Name iris = Name.builder().playbookType(PlaybookType.BRAINER).name("Iris").build();
        Name marie = Name.builder().playbookType(PlaybookType.BRAINER).name("Marie").build();
        Name amiette = Name.builder().playbookType(PlaybookType.BRAINER).name("Amiette").build();
        Name suselle = Name.builder().playbookType(PlaybookType.BRAINER).name("Suselle").build();
        Name cybelle = Name.builder().playbookType(PlaybookType.BRAINER).name("Cybelle").build();
        Name pallor = Name.builder().playbookType(PlaybookType.BRAINER).name("Pallor").build();
        Name sin = Name.builder().playbookType(PlaybookType.BRAINER).name("Sin").build();
        Name charmer = Name.builder().playbookType(PlaybookType.BRAINER).name("Charmer").build();
        Name pity = Name.builder().playbookType(PlaybookType.BRAINER).name("Pity").build();
        Name brace = Name.builder().playbookType(PlaybookType.BRAINER).name("Brace").build();
        Name sundown = Name.builder().playbookType(PlaybookType.BRAINER).name("Sundown").build();

        nameService.saveAll(List.of(smith2, jones, jackson, marsh, lively, burroughs, gritch, joyette, iris, marie,
                amiette, suselle, cybelle, pallor, sin, charmer, pity, brace, sundown));

        /* ----------------------------- CHOPPER NAMES --------------------------------- */
        Name dog = Name.builder().playbookType(PlaybookType.CHOPPER).name("Dog").build();
        Name domino = Name.builder().playbookType(PlaybookType.CHOPPER).name("Domino").build();
        Name tBone = Name.builder().playbookType(PlaybookType.CHOPPER).name("T-bone").build();
        Name stinky = Name.builder().playbookType(PlaybookType.CHOPPER).name("Stinky").build();
        Name satan = Name.builder().playbookType(PlaybookType.CHOPPER).name("Satan").build();
        Name lars = Name.builder().playbookType(PlaybookType.CHOPPER).name("Lars").build();
        Name bullet = Name.builder().playbookType(PlaybookType.CHOPPER).name("Bullet").build();
        Name dice = Name.builder().playbookType(PlaybookType.CHOPPER).name("Dice").build();
        Name shitHead = Name.builder().playbookType(PlaybookType.CHOPPER).name("Shit head").build();
        Name halfPint = Name.builder().playbookType(PlaybookType.CHOPPER).name("Half pint").build();
        Name shooter = Name.builder().playbookType(PlaybookType.CHOPPER).name("Shooter").build();
        Name diamond = Name.builder().playbookType(PlaybookType.CHOPPER).name("Diamond").build();
        Name goldie = Name.builder().playbookType(PlaybookType.CHOPPER).name("Goldie").build();
        Name tinker = Name.builder().playbookType(PlaybookType.CHOPPER).name("Tinker").build();
        Name loose = Name.builder().playbookType(PlaybookType.CHOPPER).name("Loose").build();
        Name baby = Name.builder().playbookType(PlaybookType.CHOPPER).name("Baby").build();
        Name juck = Name.builder().playbookType(PlaybookType.CHOPPER).name("Juck").build();
        Name hammer = Name.builder().playbookType(PlaybookType.CHOPPER).name("Hammer").build();
        Name hooch = Name.builder().playbookType(PlaybookType.CHOPPER).name("Hooch").build();
        Name snakeEyes = Name.builder().playbookType(PlaybookType.CHOPPER).name("Snake eyes").build();
        Name pinkie = Name.builder().playbookType(PlaybookType.CHOPPER).name("Pinkie").build();
        Name wire = Name.builder().playbookType(PlaybookType.CHOPPER).name("Wire").build();
        Name blues = Name.builder().playbookType(PlaybookType.CHOPPER).name("Blues").build();

        nameService.saveAll(List.of(dog, domino, tBone, stinky, satan, lars, bullet, dice, shitHead, halfPint,
                shooter, diamond, goldie, tinker, loose, baby, juck, hammer, hooch, snakeEyes, pinkie, wire, blues));

        /* ----------------------------- DRIVER NAMES --------------------------------- */
        Name lauren = Name.builder().playbookType(PlaybookType.DRIVER).name("Lauren").build();
        Name audrey = Name.builder().playbookType(PlaybookType.DRIVER).name("Audrey").build();
        Name farley = Name.builder().playbookType(PlaybookType.DRIVER).name("Farley").build();
        Name sammy = Name.builder().playbookType(PlaybookType.DRIVER).name("Sammy").build();
        Name katherine = Name.builder().playbookType(PlaybookType.DRIVER).name("Katherine").build();
        Name marilyn = Name.builder().playbookType(PlaybookType.DRIVER).name("Marilyn").build();
        Name james = Name.builder().playbookType(PlaybookType.DRIVER).name("James").build();
        Name bridget = Name.builder().playbookType(PlaybookType.DRIVER).name("Bridget").build();
        Name paul = Name.builder().playbookType(PlaybookType.DRIVER).name("Paul").build();
        Name annette = Name.builder().playbookType(PlaybookType.DRIVER).name("Annette").build();
        Name marlene = Name.builder().playbookType(PlaybookType.DRIVER).name("Marlene").build();
        Name frankie = Name.builder().playbookType(PlaybookType.DRIVER).name("Frankie").build();
        Name marlon = Name.builder().playbookType(PlaybookType.DRIVER).name("Marlon").build();
        Name kim1 = Name.builder().playbookType(PlaybookType.DRIVER).name("Kim").build();
        Name errol = Name.builder().playbookType(PlaybookType.DRIVER).name("Errol").build();
        Name humphrey = Name.builder().playbookType(PlaybookType.DRIVER).name("Humphrey").build();
        Name phoenix = Name.builder().playbookType(PlaybookType.DRIVER).name("Phoenix").build();
        Name mustang = Name.builder().playbookType(PlaybookType.DRIVER).name("Mustang").build();
        Name impala = Name.builder().playbookType(PlaybookType.DRIVER).name("Impala").build();
        Name suv = Name.builder().playbookType(PlaybookType.DRIVER).name("Suv").build();
        Name cougar = Name.builder().playbookType(PlaybookType.DRIVER).name("Cougar").build();
        Name cobra = Name.builder().playbookType(PlaybookType.DRIVER).name("Cobra").build();
        Name dart = Name.builder().playbookType(PlaybookType.DRIVER).name("Dart").build();
        Name gremlin = Name.builder().playbookType(PlaybookType.DRIVER).name("Gremlin").build();
        Name grandCherokee = Name.builder().playbookType(PlaybookType.DRIVER).name("Grand Cherokee").build();
        Name jag = Name.builder().playbookType(PlaybookType.DRIVER).name("Jag").build();
        Name beemer = Name.builder().playbookType(PlaybookType.DRIVER).name("Beemer").build();

        nameService.saveAll(List.of(lauren, audrey, farley, sammy, katherine, marilyn, james, bridget, paul,
                annette, marlene, frankie, marlon, kim1, errol, humphrey, phoenix, mustang, impala, suv, cougar,
                cobra, dart, gremlin, grandCherokee, jag, beemer));

        /* ----------------------------- GUNLUGGER NAMES --------------------------------- */
        Name vonk = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Vonk the Sculptor").build();
        Name batty = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Batty").build();
        Name jonker = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Jonker").build();
        Name at = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("A.T.").build();
        Name rueWakeman = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Rue Wakeman").build();
        Name navarre = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Navarre").build();
        Name man = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Man").build();
        Name kartak = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Kartak").build();
        Name barbarossa = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Barbarossa").build();
        Name keeler = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Keeler").build();
        Name grekkor = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Grekkor").build();
        Name crille = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Crille").build();
        Name doom = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Doom").build();
        Name chaplain = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Chaplain").build();
        Name rex = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Rex").build();
        Name fido = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Fido").build();
        Name spot = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Spot").build();
        Name boxer = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Boxer").build();
        Name doberman = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Doberman").build();
        Name trey = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Trey").build();
        Name killer = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Killer").build();
        Name butch = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Butch").build();
        Name fifi = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Fifi").build();
        Name fluffy = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Fluffy").build();
        Name duke = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Duke").build();
        Name wolf = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Wolf").build();
        Name rover = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Rover").build();
        Name max = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Max").build();
        Name buddy = Name.builder().playbookType(PlaybookType.GUNLUGGER).name("Buddy").build();


        nameService.saveAll(List.of(vonk, batty, jonker, at, rueWakeman, navarre, man, kartak, barbarossa,
                keeler, grekkor, crille, doom, chaplain, rex, fido, spot, boxer, doberman, trey, killer, butch,
                fifi, fluffy, duke, wolf, rover, max, buddy));

        /* ----------------------------- HARDHOLDER NAMES --------------------------------- */
        Name hardholderName1 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Nbeke").build();
        Name hardholderName2 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Allison").build();
        Name hardholderName3 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Kobe").build();
        Name hardholderName4 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Kreider").build();
        Name hardholderName5 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Tranh").build();
        Name hardholderName6 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Marco").build();
        Name hardholderName7 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Sadiq").build();
        Name hardholderName8 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Vega").build();
        Name hardholderName9 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Lang").build();
        Name hardholderName10 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Lin").build();
        Name hardholderName11 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Jackson").build();
        Name hardholderName12 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Madame").build();
        Name hardholderName13 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Barbecue").build();
        Name hardholderName14 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Grandma").build();
        Name hardholderName15 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Uncle").build();
        Name hardholderName16 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Parson").build();
        Name hardholderName17 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Barnum").build();
        Name hardholderName18 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Colonel").build();
        Name hardholderName19 = Name.builder().playbookType(PlaybookType.HARDHOLDER).name("Mother Superior").build();

        nameService.saveAll(List.of(hardholderName1, hardholderName2, hardholderName3, hardholderName4,
                hardholderName5, hardholderName6, hardholderName7, hardholderName8, hardholderName9,
                hardholderName10, hardholderName11, hardholderName12, hardholderName13, hardholderName14,
                hardholderName15, hardholderName16, hardholderName17, hardholderName18, hardholderName19));

        /* ----------------------------- HOCUS NAMES --------------------------------- */
        Name hocus = Name.builder().playbookType(PlaybookType.HOCUS).name("Vision").build();
        Name hocus1 = Name.builder().playbookType(PlaybookType.HOCUS).name("Hope").build();
        Name hocus2 = Name.builder().playbookType(PlaybookType.HOCUS).name("Dust").build();
        Name hocus3 = Name.builder().playbookType(PlaybookType.HOCUS).name("Truth").build();
        Name hocus4 = Name.builder().playbookType(PlaybookType.HOCUS).name("Found").build();
        Name hocus5 = Name.builder().playbookType(PlaybookType.HOCUS).name("Always").build();
        Name hocus6 = Name.builder().playbookType(PlaybookType.HOCUS).name("Lost").build();
        Name hocus7 = Name.builder().playbookType(PlaybookType.HOCUS).name("Want").build();
        Name hocus8 = Name.builder().playbookType(PlaybookType.HOCUS).name("Must").build();
        Name hocus9 = Name.builder().playbookType(PlaybookType.HOCUS).name("Bright").build();
        Name hocus10 = Name.builder().playbookType(PlaybookType.HOCUS).name("Sorrow").build();
        Name hocus11 = Name.builder().playbookType(PlaybookType.HOCUS).name("Horse").build();
        Name hocus12 = Name.builder().playbookType(PlaybookType.HOCUS).name("Rabbit").build();
        Name hocus13 = Name.builder().playbookType(PlaybookType.HOCUS).name("Trout").build();
        Name hocus14 = Name.builder().playbookType(PlaybookType.HOCUS).name("Cat").build();
        Name hocus15 = Name.builder().playbookType(PlaybookType.HOCUS).name("Spider").build();
        Name hocus16 = Name.builder().playbookType(PlaybookType.HOCUS).name("Snake").build();
        Name hocus17 = Name.builder().playbookType(PlaybookType.HOCUS).name("Bat").build();
        Name hocus18 = Name.builder().playbookType(PlaybookType.HOCUS).name("Lizard").build();
        Name hocus19 = Name.builder().playbookType(PlaybookType.HOCUS).name("Jackal").build();
        Name hocus20 = Name.builder().playbookType(PlaybookType.HOCUS).name("Weaver Bird").build();
        Name hocus21 = Name.builder().playbookType(PlaybookType.HOCUS).name("Lark").build();

        nameService.saveAll(List.of(hocus, hocus1, hocus2, hocus3, hocus4, hocus5, hocus6, hocus7,
                hocus8, hocus9, hocus10, hocus11, hocus12, hocus13, hocus14, hocus15, hocus16,
                hocus17, hocus18, hocus19, hocus20, hocus21));

        /* ----------------------------- MAESTRO D' NAMES --------------------------------- */
        Name maestroD1 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Cookie").build();
        Name maestroD2 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Silver").build();
        Name maestroD3 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Smoky").build();
        Name maestroD4 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Slops").build();
        Name maestroD5 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Chief").build();
        Name maestroD6 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Rose").build();
        Name maestroD7 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Anika").build();
        Name maestroD8 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("JD").build();
        Name maestroD9 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Chairman").build();
        Name maestroD10 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Grave").build();
        Name maestroD11 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Saffron").build();
        Name maestroD12 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Life").build();
        Name maestroD13 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Yen").build();
        Name maestroD14 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Emmy").build();
        Name maestroD15 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Julia").build();
        Name maestroD16 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Jackbird").build();
        Name maestroD17 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Francois").build();
        Name maestroD18 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Esco").build();
        Name maestroD19 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Boiardi").build();
        Name maestroD20 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Mari").build();
        Name maestroD21 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Nan").build();
        Name maestroD22 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Rache").build();
        Name maestroD23 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Proper").build();
        Name maestroD24 = Name.builder().playbookType(PlaybookType.MAESTRO_D).name("Fall").build();

        nameService.saveAll(List.of(maestroD1, maestroD2, maestroD3, maestroD4, maestroD5, maestroD6, maestroD7,
                maestroD8, maestroD9, maestroD10, maestroD11, maestroD12, maestroD13, maestroD14, maestroD15,
                maestroD16, maestroD17, maestroD18, maestroD19, maestroD20, maestroD21, maestroD22, maestroD23,
                maestroD24));

        /* ----------------------------- SAVVYHEAD NAMES --------------------------------- */
        Name savvyhead1 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Leah").build();
        Name savvyhead2 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Joshua").build();
        Name savvyhead3 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Tai").build();
        Name savvyhead4 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Ethan").build();
        Name savvyhead5 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Bran").build();
        Name savvyhead6 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Jeremy").build();
        Name savvyhead7 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Amanuel").build();
        Name savvyhead8 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Justin").build();
        Name savvyhead9 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Jessica").build();
        Name savvyhead10 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Eliza").build();
        Name savvyhead11 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Dylan").build();
        Name savvyhead12 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Adnan").build();
        Name savvyhead13 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Alan").build();
        Name savvyhead14 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Nils").build();
        Name savvyhead15 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Ellen").build();
        Name savvyhead16 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Lee").build();
        Name savvyhead17 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Kim").build();
        Name savvyhead18 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Adele").build();
        Name savvyhead19 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Leone").build();
        Name savvyhead20 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Burdick").build();
        Name savvyhead21 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Oliver").build();
        Name savvyhead22 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Goldman").build();
        Name savvyhead23 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Whiting").build();
        Name savvyhead24 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Fauci").build();
        Name savvyhead25 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Hossfield").build();
        Name savvyhead26 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Lemma").build();
        Name savvyhead27 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Morrell").build();
        Name savvyhead28 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Ozair").build();
        Name savvyhead29 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Robinson").build();
        Name savvyhead30 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Lemieux").build();
        Name savvyhead31 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Whitmont").build();
        Name savvyhead32 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Cullen").build();
        Name savvyhead33 = Name.builder().playbookType(PlaybookType.SAVVYHEAD).name("Spector").build();

        nameService.saveAll(List.of(savvyhead1, savvyhead2, savvyhead3, savvyhead4, savvyhead5,
                savvyhead6, savvyhead7, savvyhead8, savvyhead9, savvyhead10, savvyhead11,
                savvyhead11, savvyhead12, savvyhead13, savvyhead14, savvyhead15, savvyhead16,
                savvyhead17, savvyhead18, savvyhead19, savvyhead20, savvyhead21, savvyhead22,
                savvyhead23, savvyhead24, savvyhead25, savvyhead26, savvyhead27, savvyhead28,
                savvyhead29, savvyhead30, savvyhead31, savvyhead32, savvyhead33));

        /* ----------------------------- SKINNER NAMES --------------------------------- */
        Name skinner1 = Name.builder().playbookType(PlaybookType.SKINNER).name("October").build();
        Name skinner2 = Name.builder().playbookType(PlaybookType.SKINNER).name("Venus").build();
        Name skinner3 = Name.builder().playbookType(PlaybookType.SKINNER).name("Mercury").build();
        Name skinner4 = Name.builder().playbookType(PlaybookType.SKINNER).name("Dune").build();
        Name skinner5 = Name.builder().playbookType(PlaybookType.SKINNER).name("Shade").build();
        Name skinner6 = Name.builder().playbookType(PlaybookType.SKINNER).name("Heron").build();
        Name skinner7 = Name.builder().playbookType(PlaybookType.SKINNER).name("Plum").build();
        Name skinner8 = Name.builder().playbookType(PlaybookType.SKINNER).name("Orchid").build();
        Name skinner9 = Name.builder().playbookType(PlaybookType.SKINNER).name("Storm").build();
        Name skinner10 = Name.builder().playbookType(PlaybookType.SKINNER).name("Dusk").build();
        Name skinner11 = Name.builder().playbookType(PlaybookType.SKINNER).name("Sword").build();
        Name skinner12 = Name.builder().playbookType(PlaybookType.SKINNER).name("Midnight").build();
        Name skinner13 = Name.builder().playbookType(PlaybookType.SKINNER).name("Hide").build();
        Name skinner14 = Name.builder().playbookType(PlaybookType.SKINNER).name("Frost").build();
        Name skinner15 = Name.builder().playbookType(PlaybookType.SKINNER).name("Lawn").build();
        Name skinner16 = Name.builder().playbookType(PlaybookType.SKINNER).name("June").build();
        Name skinner17 = Name.builder().playbookType(PlaybookType.SKINNER).name("Icicle").build();
        Name skinner18 = Name.builder().playbookType(PlaybookType.SKINNER).name("Tern").build();
        Name skinner19 = Name.builder().playbookType(PlaybookType.SKINNER).name("Lavender").build();
        Name skinner20 = Name.builder().playbookType(PlaybookType.SKINNER).name("Spice").build();
        Name skinner21 = Name.builder().playbookType(PlaybookType.SKINNER).name("Gazelle").build();
        Name skinner22 = Name.builder().playbookType(PlaybookType.SKINNER).name("Lion").build();
        Name skinner23 = Name.builder().playbookType(PlaybookType.SKINNER).name("Peacock").build();
        Name skinner24 = Name.builder().playbookType(PlaybookType.SKINNER).name("Grace").build();

        nameService.saveAll(List.of(skinner1, skinner2, skinner3, skinner4, skinner5, skinner6,
                skinner7, skinner8, skinner9, skinner10, skinner11, skinner12, skinner13,
                skinner14, skinner15, skinner16, skinner17, skinner18, skinner19, skinner20,
                skinner21, skinner22, skinner23, skinner24));
    }

    private void loadLooks() {
        System.out.println("|| --- Loading playbook looks --- ||");
        /* ----------------------------- ANGEL LOOKS --------------------------------- */
        Look angel1 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.GENDER).look("man").build();
        Look angel2 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.GENDER).look("woman").build();
        Look angel3 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.GENDER).look("ambiguous").build();
        Look angel4 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.GENDER).look("transgressing").build();
        Look angel5 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.GENDER).look("concealed").build();
        Look angel6 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.CLOTHES).look("utility wear").build();
        Look angel7 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.CLOTHES).look("casual wear plus utility").build();
        Look angel8 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.CLOTHES).look("scrounge wear plus utility").build();
        Look angel9 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.FACE).look("kind face").build();
        Look angel10 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.FACE).look("strong face").build();
        Look angel11 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.FACE).look("rugged face").build();
        Look angel12 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.FACE).look("haggard face").build();
        Look angel13 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.FACE).look("pretty face").build();
        Look angel14 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.FACE).look("lively face").build();
        Look angel15 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.EYES).look("quick eyes").build();
        Look angel16 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.EYES).look("hard eyes").build();
        Look angel17 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.EYES).look("caring eyes").build();
        Look angel18 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.EYES).look("bright eyes").build();
        Look angel19 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.EYES).look("laughing eyes").build();
        Look angel20 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.EYES).look("clear eyes").build();
        Look angel21 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.BODY).look("compact body").build();
        Look angel22 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.BODY).look("stout body").build();
        Look angel23 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.BODY).look("spare body").build();
        Look angel24 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.BODY).look("big body").build();
        Look angel25 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.BODY).look("rangy body").build();
        Look angel26 = Look.builder().playbookType(PlaybookType.ANGEL).category(LookType.BODY).look("sturdy body").build();

        lookService.saveAll(List.of(angel1, angel2, angel3, angel4, angel5, angel6, angel7, angel8, angel9,
                angel10, angel11, angel12, angel13, angel14, angel15, angel16, angel17, angel18, angel19,
                angel20, angel21, angel22, angel23, angel24, angel25, angel26));

        /* ----------------------------- BATTLEBABE LOOKS --------------------------------- */
        Look battlebabe1 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.GENDER).look("man").build();
        Look battlebabe2 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.GENDER).look("woman").build();
        Look battlebabe3 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.GENDER).look("ambiguous").build();
        Look battlebabe4 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.GENDER).look("transgressing").build();
        Look battlebabe5 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.CLOTHES).look("formal wear").build();
        Look battlebabe6 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.CLOTHES).look("display wear").build();
        Look battlebabe7 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.CLOTHES).look("luxe wear").build();
        Look battlebabe8 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.CLOTHES).look("casual wear").build();
        Look battlebabe9 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.CLOTHES).look("showy armor").build();
        Look battlebabe10 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.FACE).look("smooth face").build();
        Look battlebabe11 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.FACE).look("sweet face").build();
        Look battlebabe12 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.FACE).look("handsome face").build();
        Look battlebabe13 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.FACE).look("sharp face").build();
        Look battlebabe14 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.FACE).look("girlish face").build();
        Look battlebabe15 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.FACE).look("boyish face").build();
        Look battlebabe16 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.FACE).look("striking face").build();
        Look battlebabe17 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.EYES).look("calculating eyes").build();
        Look battlebabe18 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.EYES).look("merciless eyes").build();
        Look battlebabe19 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.EYES).look("frosty eyes").build();
        Look battlebabe20 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.EYES).look("arresting eyes").build();
        Look battlebabe21 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.EYES).look("indifferent eyes").build();
        Look battlebabe22 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.BODY).look("sweet body").build();
        Look battlebabe23 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.BODY).look("slim body").build();
        Look battlebabe24 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.BODY).look("gorgeous body").build();
        Look battlebabe25 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.BODY).look("muscular body").build();
        Look battlebabe26 = Look.builder().playbookType(PlaybookType.BATTLEBABE).category(LookType.BODY).look("angular body").build();

        lookService.saveAll(List.of(battlebabe1, battlebabe2, battlebabe3, battlebabe4, battlebabe5, battlebabe6,
                battlebabe7, battlebabe8, battlebabe9, battlebabe10, battlebabe11, battlebabe12, battlebabe13,
                battlebabe14, battlebabe15, battlebabe16, battlebabe17, battlebabe18, battlebabe19, battlebabe20,
                battlebabe21, battlebabe22, battlebabe23, battlebabe24, battlebabe25, battlebabe26));

        /* ----------------------------- BRAINER LOOKS --------------------------------- */
        Look brainer1 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.GENDER).look("man").build();
        Look brainer2 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.GENDER).look("woman").build();
        Look brainer3 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.GENDER).look("ambiguous").build();
        Look brainer4 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.GENDER).look("transgressing").build();
        Look brainer5 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.GENDER).look("concealed").build();
        Look brainer6 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.CLOTHES).look("high formal wear").build();
        Look brainer7 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.CLOTHES).look("clinical wear").build();
        Look brainer8 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.CLOTHES).look("fetish-bondage wear").build();
        Look brainer9 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.CLOTHES).look("environmental wear improper to the local environment").build();
        Look brainer10 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.FACE).look("scarred face").build();
        Look brainer11 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.FACE).look("smooth face").build();
        Look brainer12 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.FACE).look("pale face").build();
        Look brainer13 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.FACE).look("bony face").build();
        Look brainer14 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.FACE).look("plump moist face").build();
        Look brainer15 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.FACE).look("sweet face").build();
        Look brainer16 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.EYES).look("soft eyes").build();
        Look brainer17 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.EYES).look("dead eyes").build();
        Look brainer18 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.EYES).look("deep eyes").build();
        Look brainer19 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.EYES).look("caring eyes").build();
        Look brainer20 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.EYES).look("pale eyes").build();
        Look brainer21 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.EYES).look("ruined eyes").build();
        Look brainer22 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.EYES).look("wet eyes").build();
        Look brainer23 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.BODY).look("awkward angular body").build();
        Look brainer24 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.BODY).look("soft body").build();
        Look brainer25 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.BODY).look("slight body").build();
        Look brainer26 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.BODY).look("crippled body").build();
        Look brainer27 = Look.builder().playbookType(PlaybookType.BRAINER).category(LookType.BODY).look("fat body").build();

        lookService.saveAll(List.of(brainer1, brainer2, brainer3, brainer4, brainer5, brainer6, brainer7, brainer8,
                brainer9, brainer10, brainer11, brainer12, brainer13, brainer14, brainer15, brainer16, brainer17,
                brainer18, brainer19, brainer20, brainer21, brainer22, brainer23, brainer24, brainer25, brainer26,
                brainer27));

        /* ----------------------------- CHOPPER LOOKS --------------------------------- */
        Look chopper1 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.GENDER).look("man").build();
        Look chopper2 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.GENDER).look("woman").build();
        Look chopper3 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.GENDER).look("ambiguous").build();
        Look chopper4 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.GENDER).look("transgressing").build();
        Look chopper5 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.CLOTHES).look("combat biker wear").build();
        Look chopper6 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.CLOTHES).look("showy biker wear").build();
        Look chopper7 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.CLOTHES).look("scrounge biker wear").build();
        Look chopper8 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.CLOTHES).look("S&M biker wear").build();
        Look chopper9 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.FACE).look("weathered face").build();
        Look chopper10 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.FACE).look("strong face").build();
        Look chopper11 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.FACE).look("rugged face").build();
        Look chopper12 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.FACE).look("narrow face").build();
        Look chopper13 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.FACE).look("busted face").build();
        Look chopper14 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.EYES).look("narrow eyes").build();
        Look chopper15 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.EYES).look("scorched eyes").build();
        Look chopper16 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.EYES).look("calculating eyes").build();
        Look chopper17 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.EYES).look("weary eyes").build();
        Look chopper18 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.EYES).look("kind eyes").build();
        Look chopper19 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.BODY).look("squat body").build();
        Look chopper20 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.BODY).look("rangy body").build();
        Look chopper21 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.BODY).look("wiry body").build();
        Look chopper22 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.BODY).look("sturdy body").build();
        Look chopper23 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.BODY).look("fat body").build();

        lookService.saveAll(List.of(chopper1, chopper2, chopper3, chopper4, chopper5, chopper6, chopper7, chopper8,
                chopper9, chopper10, chopper11, chopper12, chopper13, chopper14, chopper15, chopper16, chopper17,
                chopper18, chopper19, chopper20, chopper21, chopper22, chopper23));

        /* ----------------------------- DRIVER LOOKS --------------------------------- */
        Look driver1 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.GENDER).look("man").build();
        Look driver2 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.GENDER).look("woman").build();
        Look driver3 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.GENDER).look("ambiguous").build();
        Look driver4 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.GENDER).look("transgressing").build();
        Look driver5 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.CLOTHES).look("vintage wear").build();
        Look driver6 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.CLOTHES).look("casual wear").build();
        Look driver7 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.CLOTHES).look("utility wear").build();
        Look driver8 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.CLOTHES).look("leather wear").build();
        Look driver9 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.CLOTHES).look("showy scrounge wear").build();
        Look driver10 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.FACE).look("handsome face").build();
        Look driver11 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.FACE).look("gorgeous face").build();
        Look driver12 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.FACE).look("stern face").build();
        Look driver13 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.FACE).look("fine-boned face").build();
        Look driver14 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.FACE).look("worn face").build();
        Look driver15 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.FACE).look("crooked face").build();
        Look driver16 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.EYES).look("cool eyes").build();
        Look driver17 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.EYES).look("hooded eyes").build();
        Look driver18 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.EYES).look("hard eyes").build();
        Look driver19 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.EYES).look("sad eyes").build();
        Look driver20 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.EYES).look("cold eyes").build();
        Look driver21 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.EYES).look("pale eyes").build();
        Look driver22 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.BODY).look("slim body").build();
        Look driver23 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.BODY).look("pudgy body").build();
        Look driver24 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.BODY).look("stocky body").build();
        Look driver25 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.BODY).look("solid body").build();
        Look driver26 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.BODY).look("tall body").build();
        Look driver27 = Look.builder().playbookType(PlaybookType.DRIVER).category(LookType.BODY).look("strong body").build();

        lookService.saveAll(List.of(driver1, driver2, driver3, driver4, driver5, driver6, driver7, driver8, driver9,
                driver10, driver11, driver12, driver13, driver14, driver15, driver16, driver17, driver18, driver19,
                driver20, driver21, driver22, driver23, driver24, driver25, driver26, driver26, driver27));

        /* ----------------------------- GUNLUGGER LOOKS --------------------------------- */
        Look gunlugger1 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.GENDER).look("man").build();
        Look gunlugger2 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.GENDER).look("woman").build();
        Look gunlugger3 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.GENDER).look("ambiguous").build();
        Look gunlugger4 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.GENDER).look("transgressing").build();
        Look gunlugger5 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.GENDER).look("concealed").build();
        Look gunlugger6 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.CLOTHES).look("scrounged mismatched armor").build();
        Look gunlugger7 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.CLOTHES).look("battered old armor").build();
        Look gunlugger8 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.CLOTHES).look("custom homemade armor").build();
        Look gunlugger10 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.FACE).look("scarred face").build();
        Look gunlugger11 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.FACE).look("blunt face").build();
        Look gunlugger12 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.FACE).look("bony face").build();
        Look gunlugger13 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.FACE).look("dull face").build();
        Look gunlugger14 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.FACE).look("worn face").build();
        Look gunlugger15 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.FACE).look("blasted face").build();
        Look gunlugger16 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.EYES).look("mad eyes").build();
        Look gunlugger17 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.EYES).look("raging eyes").build();
        Look gunlugger18 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.EYES).look("wise eyes").build();
        Look gunlugger19 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.EYES).look("sad eyes").build();
        Look gunlugger20 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.EYES).look("little piggy eyes").build();
        Look gunlugger21 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.EYES).look("cunning eyes").build();
        Look gunlugger22 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.BODY).look("hard body").build();
        Look gunlugger23 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.BODY).look("stocky body").build();
        Look gunlugger24 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.BODY).look("stringy body").build();
        Look gunlugger25 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.BODY).look("battered body").build();
        Look gunlugger26 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.BODY).look("overbuilt body").build();
        Look gunlugger27 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.BODY).look("compact body").build();
        Look gunlugger9 = Look.builder().playbookType(PlaybookType.GUNLUGGER).category(LookType.BODY).look("huge body").build();

        lookService.saveAll(List.of(gunlugger1, gunlugger2, gunlugger3, gunlugger4, gunlugger5, gunlugger6, gunlugger7,
                gunlugger8, gunlugger9, gunlugger10, gunlugger11, gunlugger12, gunlugger13, gunlugger14, gunlugger15,
                gunlugger16, gunlugger17, gunlugger18, gunlugger19, gunlugger20, gunlugger21, gunlugger22, gunlugger23,
                gunlugger24, gunlugger25, gunlugger26, gunlugger27));

        /* ----------------------------- HARDHOLDER LOOKS --------------------------------- */
        Look hardHolder1 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.GENDER).look("man").build();
        Look hardHolder2 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.GENDER).look("woman").build();
        Look hardHolder3 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.GENDER).look("ambiguous").build();
        Look hardHolder4 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.GENDER).look("transgressing").build();
        Look hardHolder5 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.CLOTHES).look("luxe wear").build();
        Look hardHolder6 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.CLOTHES).look("display wear").build();
        Look hardHolder7 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.CLOTHES).look("fetish wear").build();
        Look hardHolder8 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.CLOTHES).look("casual wear").build();
        Look hardHolder9 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.CLOTHES).look("junta wear").build();
        Look hardHolder10 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.FACE).look("strong face").build();
        Look hardHolder11 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.FACE).look("stern face").build();
        Look hardHolder12 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.FACE).look("cruel face").build();
        Look hardHolder13 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.FACE).look("soft face").build();
        Look hardHolder14 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.FACE).look("aristocratic face").build();
        Look hardHolder15 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.FACE).look("gorgeous").build();
        Look hardHolder16 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.EYES).look("cool eyes").build();
        Look hardHolder17 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.EYES).look("commanding eyes").build();
        Look hardHolder18 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.EYES).look("languid eyes").build();
        Look hardHolder19 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.EYES).look("sharp eyes").build();
        Look hardHolder20 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.EYES).look("forgiving eyes").build();
        Look hardHolder21 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.EYES).look("generous eyes").build();
        Look hardHolder22 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.BODY).look("massive body").build();
        Look hardHolder23 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.BODY).look("soft body").build();
        Look hardHolder24 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.BODY).look("wiry body").build();
        Look hardHolder25 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.BODY).look("fat body").build();
        Look hardHolder26 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.BODY).look("tall spare body").build();
        Look hardHolder27 = Look.builder().playbookType(PlaybookType.HARDHOLDER).category(LookType.BODY).look("sensual body").build();

        lookService.saveAll(List.of(hardHolder1, hardHolder2, hardHolder3, hardHolder4, hardHolder5, hardHolder6,
                hardHolder7, hardHolder8, hardHolder9, hardHolder10, hardHolder11, hardHolder12, hardHolder13,
                hardHolder14, hardHolder15, hardHolder16, hardHolder17, hardHolder18, hardHolder19, hardHolder20,
                hardHolder21, hardHolder22, hardHolder23, hardHolder24, hardHolder25, hardHolder26, hardHolder27));

        /* ----------------------------- HOCUS LOOKS --------------------------------- */
        Look hocus1 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.GENDER).look("man").build();
        Look hocus2 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.GENDER).look("woman").build();
        Look hocus3 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.GENDER).look("ambiguous").build();
        Look hocus4 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.GENDER).look("transgressing").build();
        Look hocus5 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.GENDER).look("concealed").build();
        Look hocus6 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.CLOTHES).look("tattered vestments").build();
        Look hocus7 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.CLOTHES).look("formal vestments").build();
        Look hocus8 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.CLOTHES).look("scrounge vestments").build();
        Look hocus9 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.CLOTHES).look("fetish vestments").build();
        Look hocus10 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.CLOTHES).look("tech vestments").build();
        Look hocus11 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.FACE).look("innocent face").build();
        Look hocus12 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.FACE).look("dirty face").build();
        Look hocus13 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.FACE).look("determined face").build();
        Look hocus14 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.FACE).look("open face").build();
        Look hocus15 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.FACE).look("severe face").build();
        Look hocus16 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.FACE).look("ascetic face").build();
        Look hocus17 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.EYES).look("mesmerizing eyes").build();
        Look hocus18 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.EYES).look("dazed eyes").build();
        Look hocus19 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.EYES).look("forgiving eyes").build();
        Look hocus20 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.EYES).look("suspicious eyes").build();
        Look hocus21 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.EYES).look("clear eyes").build();
        Look hocus22 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.EYES).look("burning eyes").build();
        Look hocus23 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.EYES).look("bony body").build();
        Look hocus24 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.BODY).look("lanky body").build();
        Look hocus25 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.BODY).look("soft body").build();
        Look hocus26 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.BODY).look("fit body").build();
        Look hocus27 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.BODY).look("graceful body").build();
        Look hocus28 = Look.builder().playbookType(PlaybookType.HOCUS).category(LookType.BODY).look("fat body").build();

        lookService.saveAll(List.of(hocus1, hocus2, hocus3, hocus4, hocus5, hocus6, hocus7, hocus8, hocus9, hocus10,
                hocus11, hocus12, hocus13, hocus14, hocus15, hocus16, hocus17, hocus18, hocus19, hocus20, hocus21,
                hocus22, hocus23, hocus24, hocus25, hocus26, hocus27, hocus28));

        /* ----------------------------- MAESTRO D' LOOKS --------------------------------- */
        Look maestroD1 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.GENDER).look("man").build();
        Look maestroD2 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.GENDER).look("woman").build();
        Look maestroD3 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.GENDER).look("ambiguous").build();
        Look maestroD4 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.GENDER).look("transgressing").build();
        Look maestroD5 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.CLOTHES).look("casual wear").build();
        Look maestroD30 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.CLOTHES).look("display wear").build();
        Look maestroD6 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.CLOTHES).look("vintage wear").build();
        Look maestroD7 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.CLOTHES).look("butcher wear").build();
        Look maestroD8 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.CLOTHES).look("fetish wear").build();
        Look maestroD9 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.CLOTHES).look("immaculate whites").build();
        Look maestroD10 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.FACE).look("pinched face").build();
        Look maestroD11 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.FACE).look("boyish face").build();
        Look maestroD12 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.FACE).look("pretty face").build();
        Look maestroD13 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.FACE).look("expressive face").build();
        Look maestroD14 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.FACE).look("full face").build();
        Look maestroD15 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.FACE).look("tattooed face").build();
        Look maestroD16 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.FACE).look("porcelain face").build();
        Look maestroD17 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.FACE).look("scarred face").build();
        Look maestroD18 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.EYES).look("cool eyes").build();
        Look maestroD19 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.EYES).look("bright eyes").build();
        Look maestroD20 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.EYES).look("inquisitive eyes").build();
        Look maestroD21 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.EYES).look("frank eyes").build();
        Look maestroD22 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.EYES).look("mischievous eyes").build();
        Look maestroD23 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.EYES).look("one eye").build();
        Look maestroD24 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.BODY).look("fat body").build();
        Look maestroD25 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.BODY).look("restless body").build();
        Look maestroD26 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.BODY).look("stubby body").build();
        Look maestroD27 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.BODY).look("curvy body").build();
        Look maestroD28 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.BODY).look("unusual body").build();
        Look maestroD29 = Look.builder().playbookType(PlaybookType.MAESTRO_D).category(LookType.BODY).look("lean body").build();

        lookService.saveAll(List.of(maestroD1, maestroD2, maestroD3, maestroD4, maestroD5, maestroD6, maestroD7,
                maestroD8, maestroD9, maestroD10, maestroD11, maestroD12, maestroD13, maestroD14, maestroD15,
                maestroD16, maestroD17, maestroD18, maestroD19, maestroD20, maestroD21, maestroD22, maestroD23,
                maestroD24, maestroD25, maestroD26, maestroD27, maestroD28, maestroD29, maestroD30));

        /* ----------------------------- SAVVYHEAD LOOKS --------------------------------- */
        Look savvyhead1 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.GENDER).look("man").build();
        Look savvyhead2 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.GENDER).look("woman").build();
        Look savvyhead3 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.GENDER).look("ambiguous").build();
        Look savvyhead4 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.GENDER).look("transgressing").build();
        Look savvyhead5 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.CLOTHES).look("utility wear plus tech").build();
        Look savvyhead6 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.CLOTHES).look("scrounge wear plus tech").build();
        Look savvyhead7 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.CLOTHES).look("vintage wear plus tech").build();
        Look savvyhead8 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.CLOTHES).look("tech wear").build();
        Look savvyhead9 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.FACE).look("plain face").build();
        Look savvyhead10 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.FACE).look("pretty face").build();
        Look savvyhead11 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.FACE).look("open face").build();
        Look savvyhead12 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.FACE).look("expressive face").build();
        Look savvyhead13 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.EYES).look("squinty eyes").build();
        Look savvyhead14 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.EYES).look("calm eyes").build();
        Look savvyhead15 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.EYES).look("dancing eyes").build();
        Look savvyhead16 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.EYES).look("quick eyes").build();
        Look savvyhead17 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.EYES).look("appraising eyes").build();
        Look savvyhead18 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.BODY).look("fat body").build();
        Look savvyhead19 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.BODY).look("slight body").build();
        Look savvyhead20 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.BODY).look("hunched body").build();
        Look savvyhead21 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.BODY).look("wiry body").build();
        Look savvyhead22 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.BODY).look("stumpy body").build();
        Look savvyhead23 = Look.builder().playbookType(PlaybookType.SAVVYHEAD).category(LookType.BODY).look("strange body").build();

        lookService.saveAll(List.of(savvyhead1, savvyhead2, savvyhead3, savvyhead4, savvyhead5, savvyhead6, savvyhead7,
                savvyhead8, savvyhead9, savvyhead10, savvyhead11, savvyhead12, savvyhead13, savvyhead14, savvyhead15,
                savvyhead16, savvyhead17, savvyhead18, savvyhead19, savvyhead20, savvyhead21, savvyhead22, savvyhead23));

        /* ----------------------------- SKINNER LOOKS --------------------------------- */
        Look skinner1 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.GENDER).look("man").build();
        Look skinner2 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.GENDER).look("woman").build();
        Look skinner3 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.GENDER).look("ambiguous").build();
        Look skinner4 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.GENDER).look("transgressing").build();
        Look skinner5 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.GENDER).look("androgyne").build();
        Look skinner6 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.CLOTHES).look("display wear").build();
        Look skinner7 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.CLOTHES).look("showy scrounge wear").build();
        Look skinner8 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.CLOTHES).look("luxe wear").build();
        Look skinner9 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.CLOTHES).look("fetish wear").build();
        Look skinner10 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.CLOTHES).look("casual wear").build();
        Look skinner11 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.FACE).look("striking face").build();
        Look skinner12 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.FACE).look("sweet face").build();
        Look skinner13 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.FACE).look("strange face").build();
        Look skinner14 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.FACE).look("cute face").build();
        Look skinner15 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.FACE).look("beautiful face").build();
        Look skinner16 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.EYES).look("laughing eyes").build();
        Look skinner17 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.EYES).look("mocking eyes").build();
        Look skinner18 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.EYES).look("dark eyes").build();
        Look skinner19 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.EYES).look("shadowed eyes").build();
        Look skinner20 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.EYES).look("troubled eyes").build();
        Look skinner21 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.EYES).look("arresting eyes").build();
        Look skinner22 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.EYES).look("bright eyes").build();
        Look skinner23 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.EYES).look("cool eyes").build();
        Look skinner24 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.BODY).look("slim body").build();
        Look skinner25 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.BODY).look("toned body").build();
        Look skinner26 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.BODY).look("fat body").build();
        Look skinner27 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.BODY).look("unnatural body").build();
        Look skinner28 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.BODY).look("young body").build();
        Look skinner29 = Look.builder().playbookType(PlaybookType.SKINNER).category(LookType.BODY).look("lush body").build();

        lookService.saveAll(List.of(skinner1, skinner2, skinner3, skinner4, skinner5, skinner6, skinner7, skinner8,
                skinner9, skinner10, skinner11, skinner12, skinner13, skinner14, skinner15, skinner16, skinner17,
                skinner18, skinner19, skinner20, skinner21, skinner22, skinner23, skinner24, skinner25, skinner26,
                skinner27, skinner28, skinner29));
    }

    public void loadStatsOptions() {
        System.out.println("|| --- Loading playbook stats options --- ||");
        /* ----------------------------- ANGEL STATS OPTIONS --------------------------------- */
        StatsOption angel1 = StatsOption.builder().playbookType(PlaybookType.ANGEL).COOL(1).HARD(0).HOT(1).SHARP(2).WEIRD(-1).build(); // 3
        StatsOption angel2 = StatsOption.builder().playbookType(PlaybookType.ANGEL).COOL(1).HARD(1).HOT(0).SHARP(2).WEIRD(-1).build(); // 3
        StatsOption angel3 = StatsOption.builder().playbookType(PlaybookType.ANGEL).COOL(-1).HARD(1).HOT(0).SHARP(2).WEIRD(1).build(); // 3
        StatsOption angel4 = StatsOption.builder().playbookType(PlaybookType.ANGEL).COOL(2).HARD(0).HOT(-1).SHARP(2).WEIRD(-1).build(); // 2
        statsOptionService.saveAll(List.of(angel1, angel2, angel3, angel4));

        /* ----------------------------- BATTLEBABE STATS OPTIONS --------------------------------- */
        StatsOption battlebabe1 = StatsOption.builder().playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-1).HOT(1).SHARP(1).WEIRD(0).build(); // 4
        StatsOption battlebabe2 = StatsOption.builder().playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-1).HOT(2).SHARP(0).WEIRD(-1).build(); // 3
        StatsOption battlebabe3 = StatsOption.builder().playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-2).HOT(1).SHARP(1).WEIRD(1).build(); // 4
        StatsOption battlebabe4 = StatsOption.builder().playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(0).HOT(1).SHARP(1).WEIRD(-1).build(); // 3
        statsOptionService.saveAll(List.of(battlebabe1, battlebabe2, battlebabe3, battlebabe4));

        /* ----------------------------- BRAINER STATS OPTIONS --------------------------------- */
        StatsOption brainer1 = StatsOption.builder().playbookType(PlaybookType.BRAINER).COOL(1).HARD(1).HOT(-2).SHARP(1).WEIRD(2).build(); // 3
        StatsOption brainer2 = StatsOption.builder().playbookType(PlaybookType.BRAINER).COOL(0).HARD(0).HOT(1).SHARP(0).WEIRD(2).build(); // 3
        StatsOption brainer3 = StatsOption.builder().playbookType(PlaybookType.BRAINER).COOL(1).HARD(-2).HOT(-1).SHARP(2).WEIRD(2).build(); // 2
        StatsOption brainer4 = StatsOption.builder().playbookType(PlaybookType.BRAINER).COOL(2).HARD(-1).HOT(-1).SHARP(0).WEIRD(2).build(); // 2
        statsOptionService.saveAll(List.of(brainer1, brainer2, brainer3, brainer4));

        /* ----------------------------- CHOPPER STATS OPTIONS --------------------------------- */
        StatsOption chopper1 = StatsOption.builder().playbookType(PlaybookType.CHOPPER).COOL(1).HARD(2).HOT(-1).SHARP(1).WEIRD(0).build(); // 3
        StatsOption chopper2 = StatsOption.builder().playbookType(PlaybookType.CHOPPER).COOL(1).HARD(2).HOT(1).SHARP(0).WEIRD(1).build(); // 4
        StatsOption chopper3 = StatsOption.builder().playbookType(PlaybookType.CHOPPER).COOL(1).HARD(2).HOT(0).SHARP(1).WEIRD(1).build(); // 5
        StatsOption chopper4 = StatsOption.builder().playbookType(PlaybookType.CHOPPER).COOL(2).HARD(2).HOT(-1).SHARP(0).WEIRD(1).build(); // 4
        statsOptionService.saveAll(List.of(chopper1, chopper2, chopper3, chopper4));

        /* ----------------------------- DRIVER STATS OPTIONS --------------------------------- */
        StatsOption driver1 = StatsOption.builder().playbookType(PlaybookType.DRIVER).COOL(2).HARD(-1).HOT(1).SHARP(1).WEIRD(0).build(); // 3
        StatsOption driver2 = StatsOption.builder().playbookType(PlaybookType.DRIVER).COOL(2).HARD(0).HOT(1).SHARP(1).WEIRD(-1).build(); // 3
        StatsOption driver3 = StatsOption.builder().playbookType(PlaybookType.DRIVER).COOL(2).HARD(1).HOT(-1).SHARP(0).WEIRD(1).build(); // 3
        StatsOption driver4 = StatsOption.builder().playbookType(PlaybookType.DRIVER).COOL(2).HARD(-2).HOT(0).SHARP(2).WEIRD(1).build(); // 3
        statsOptionService.saveAll(List.of(driver1, driver2, driver3, driver4));

        /* ----------------------------- GUNLUGGER STATS OPTIONS --------------------------------- */
        StatsOption gunlugger1 = StatsOption.builder().playbookType(PlaybookType.GUNLUGGER).COOL(1).HARD(2).HOT(-1).SHARP(1).WEIRD(0).build(); // 3
        StatsOption gunlugger2 = StatsOption.builder().playbookType(PlaybookType.GUNLUGGER).COOL(-1).HARD(2).HOT(-2).SHARP(1).WEIRD(2).build(); // 2
        StatsOption gunlugger3 = StatsOption.builder().playbookType(PlaybookType.GUNLUGGER).COOL(1).HARD(2).HOT(-2).SHARP(2).WEIRD(-1).build(); // 2
        StatsOption gunlugger4 = StatsOption.builder().playbookType(PlaybookType.GUNLUGGER).COOL(2).HARD(2).HOT(-2).SHARP(0).WEIRD(0).build(); // 2
        statsOptionService.saveAll(List.of(gunlugger1, gunlugger2, gunlugger3, gunlugger4));

        /* ----------------------------- HARDHOLDER STATS OPTIONS --------------------------------- */
        StatsOption hardHolder1 = StatsOption.builder().playbookType(PlaybookType.HARDHOLDER).COOL(-1).HARD(2).HOT(1).SHARP(1).WEIRD(0).build(); // 3
        StatsOption hardHolder2 = StatsOption.builder().playbookType(PlaybookType.HARDHOLDER).COOL(1).HARD(2).HOT(1).SHARP(1).WEIRD(-2).build(); // 3
        StatsOption hardHolder3 = StatsOption.builder().playbookType(PlaybookType.HARDHOLDER).COOL(-2).HARD(2).HOT(0).SHARP(2).WEIRD(0).build(); // 2
        StatsOption hardHolder4 = StatsOption.builder().playbookType(PlaybookType.HARDHOLDER).COOL(0).HARD(2).HOT(1).SHARP(-1).WEIRD(1).build(); // 3

        statsOptionService.saveAll(List.of(hardHolder1, hardHolder2, hardHolder3, hardHolder4));

        /* ----------------------------- HOCUS STATS OPTIONS --------------------------------- */
        StatsOption hocus1 = StatsOption.builder().playbookType(PlaybookType.HOCUS).COOL(0).HARD(1).HOT(-1).SHARP(1).WEIRD(2).build(); // 3
        StatsOption hocus2 = StatsOption.builder().playbookType(PlaybookType.HOCUS).COOL(1).HARD(-1).HOT(1).SHARP(0).WEIRD(2).build(); // 3
        StatsOption hocus3 = StatsOption.builder().playbookType(PlaybookType.HOCUS).COOL(-1).HARD(1).HOT(0).SHARP(1).WEIRD(2).build(); // 3
        StatsOption hocus4 = StatsOption.builder().playbookType(PlaybookType.HOCUS).COOL(1).HARD(0).HOT(1).SHARP(-1).WEIRD(2).build(); // 3

        statsOptionService.saveAll(List.of(hocus1, hocus2, hocus3, hocus4));

        /* ----------------------------- MAESTRO D' STATS OPTIONS --------------------------------- */
        StatsOption maestroD1 = StatsOption.builder().playbookType(PlaybookType.MAESTRO_D).COOL(1).HARD(-1).HOT(2).SHARP(0).WEIRD(1).build(); // 3
        StatsOption maestroD2 = StatsOption.builder().playbookType(PlaybookType.MAESTRO_D).COOL(0).HARD(1).HOT(2).SHARP(1).WEIRD(-1).build(); // 3
        StatsOption maestroD3 = StatsOption.builder().playbookType(PlaybookType.MAESTRO_D).COOL(-1).HARD(2).HOT(2).SHARP(0).WEIRD(-1).build(); // 2
        StatsOption maestroD4 = StatsOption.builder().playbookType(PlaybookType.MAESTRO_D).COOL(0).HARD(0).HOT(2).SHARP(1).WEIRD(0).build(); // 3

        statsOptionService.saveAll(List.of(maestroD1, maestroD2, maestroD3, maestroD4));

        /* ----------------------------- SAVVYHEAD STATS OPTIONS --------------------------------- */
        StatsOption savvyhead1 = StatsOption.builder().playbookType(PlaybookType.SAVVYHEAD).COOL(-1).HARD(0).HOT(1).SHARP(1).WEIRD(2).build(); // 3
        StatsOption savvyhead2 = StatsOption.builder().playbookType(PlaybookType.SAVVYHEAD).COOL(0).HARD(-1).HOT(-1).SHARP(2).WEIRD(2).build(); // 2
        StatsOption savvyhead3 = StatsOption.builder().playbookType(PlaybookType.SAVVYHEAD).COOL(1).HARD(-1).HOT(0).SHARP(1).WEIRD(2).build(); // 3
        StatsOption savvyhead4 = StatsOption.builder().playbookType(PlaybookType.SAVVYHEAD).COOL(1).HARD(1).HOT(-1).SHARP(0).WEIRD(2).build(); // 3

        statsOptionService.saveAll(List.of(savvyhead1, savvyhead2, savvyhead3, savvyhead4));

        /* ----------------------------- SKINNER STATS OPTIONS --------------------------------- */
        StatsOption skinner1 = StatsOption.builder().playbookType(PlaybookType.SKINNER).COOL(1).HARD(-1).HOT(2).SHARP(1).WEIRD(0).build(); // 3
        StatsOption skinner2 = StatsOption.builder().playbookType(PlaybookType.SKINNER).COOL(0).HARD(0).HOT(2).SHARP(0).WEIRD(1).build(); // 3
        StatsOption skinner3 = StatsOption.builder().playbookType(PlaybookType.SKINNER).COOL(-1).HARD(0).HOT(2).SHARP(2).WEIRD(-1).build(); // 2
        StatsOption skinner4 = StatsOption.builder().playbookType(PlaybookType.SKINNER).COOL(1).HARD(1).HOT(2).SHARP(1).WEIRD(-2).build(); // 3

        statsOptionService.saveAll(List.of(skinner1, skinner2, skinner3, skinner4));
    }

    public void loadPlaybookCreators() {
        System.out.println("|| --- Loading playbook creators --- ||");

        String IMPROVEMENT_INSTRUCTIONS_FOR_APP = "Whenever you roll a highlighted stat, and whenever you reset your Hx with someone, an experience circle will be marked. When the 5th is marked, you can make an improvement.\n" +
                "\n" +
                "Each time you improve, choose one of the options. Check it off; you can’t choose it again.";

        String IMPROVEMENT_INSTRUCTIONS = "Whenever you roll a highlighted stat, and whenever you reset your Hx with someone, mark an experience circle. When you mark the 5th, improve and erase.\n" +
                "\n" +
                "Each time you improve, choose one of the options. Check it off; you can’t choose it again.";

        String HX_INSTRUCTIONS_START = "Everyone introduces their characters by name, look and outlook. Take your turn.\n" +
                "\n" +
                "List the other characters’ names.\n" +
                "\n";

        String HX_INSTRUCTIONS_END = "\n" +
                "On the others’ turns, answer their questions as you like.\n" +
                "\n" +
                "At the end, choose one of the characters with the highest Hx on your sheet. Ask that player which of your stats is most interesting, and highlight it. The MC will have you highlight a second stat too.";

        // Grab common improvement moves
        Move genericIncreaseStat = moveRepository.findByKind(MoveType.GENERIC_INCREASE_STAT);
        Move retire = moveRepository.findByKind(MoveType.RETIRE);
        Move addSecondCharacter = moveRepository.findByKind(MoveType.ADD_SECOND_CHARACTER);
        Move changePlaybook = moveRepository.findByKind(MoveType.CHANGE_PLAYBOOK);
        Move improveBasicMoves1 = moveRepository.findAllByKind(MoveType.IMPROVE_BASIC_MOVES).get(0);
        Move improveBasicMoves2 = moveRepository.findAllByKind(MoveType.IMPROVE_BASIC_MOVES).get(1);
        List<Move> improveStatMoves = moveRepository.findAllByKind(MoveType.IMPROVE_STAT);
        List<Move> addOtherPlaybookMovesMoves = moveRepository.findAllByKind(MoveType.ADD_OTHER_PB_MOVE);

        /* ----------------------------- ANGEL PLAYBOOK CREATOR --------------------------------- */
        List<Move> angelOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.CHARACTER);

        List<Move> angelDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.DEFAULT_CHARACTER);

        AngelKitCreator angelKitCreator = AngelKitCreator.builder()
                .id(new ObjectId().toString())
                .angelKitInstructions("Your angel kit has all kinds of crap in it: scissors, rags, tape, needles, clamps, gloves, chill coils, wipes, alcohol, injectable tourniquets & bloodslower, instant blood packets (coffee reddener), tubes of meatmesh, bonepins & site injectors, biostabs, chemostabs, narcostabs (chillstabs) in quantity, and a roll of heart jumpshock patches for when it comes to that. It’s big enough to fill the trunk of a car.\n" +
                        "\n" +
                        "When you use it, spend its stock; you can spend 0–3 of its stock per use.\n" +
                        "\n" +
                        "You can resupply it for 1-barter per 2-stock, if your circumstances let you barter for medical supplies.\n" +
                        "\n" +
                        "It begins play holding 6-stock.")
                .startingStock(6)
                .build();
        PlaybookUniqueCreator angelUniqueCreator = PlaybookUniqueCreator.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.ANGEL_KIT)
                .angelKitCreator(angelKitCreator)
                .build();
        GearInstructions angelGearInstructions = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("You get:")
                .youGetItems(List.of("fashion suitable to your look, including at your option a piece worth 1-armor (you detail)"))
                .introduceChoice("Small practical weapons")
                .numberCanChoose(1)
                .chooseableGear(List.of(".38 revolver (2-harm close reload loud)",
                        "9mm (2-harm close loud)",
                        "big knife (2-harm hand)",
                        "sawed-off (3-harm close reload messy)",
                        "stun gun (s-harm hand reload)"))
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .startingBarter(2)
                .build();

        List<String> improveStatMovesAngel = List.of(sharpMax3Name, coolMax2Name, hardMax2Name, weirdMax2Name);

        Move addCharMoveAngel1 = moveRepository.findByName(addAngelMove1Name);
        Move addCharMoveAngel2 = moveRepository.findByName(addAngelMove2Name);
        Move getSupplier = moveRepository.findByName(adjustAngelUnique1Name);

        List<Move> improvementMovesAngel = improveStatMoves.stream()
                .filter(move -> improveStatMovesAngel.contains(move.getName())).collect(Collectors.toList());

        improvementMovesAngel.add(improveStatMoves.stream()
                .filter(move -> move.getName().equals(hardMax2Name)).findFirst().orElseThrow());

        improvementMovesAngel.addAll(List.of(addCharMoveAngel1, addCharMoveAngel2, getSupplier));
        improvementMovesAngel.addAll(addOtherPlaybookMovesMoves);

        ImprovementBlock improvementBlockAngel = ImprovementBlock.builder()
                .playbookType(PlaybookType.ANGEL)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
                .futureImprovementMoves(List.of(genericIncreaseStat, retire, addSecondCharacter, changePlaybook,
                        improveBasicMoves1, improveBasicMoves2))
                .improvementMoves(improvementMovesAngel)
                .build();
        PlaybookCreator angelCreator = PlaybookCreator.builder()
                .playbookType(PlaybookType.ANGEL)
                .gearInstructions(angelGearInstructions)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS)
                .improvementBlock(improvementBlockAngel)
                .movesInstructions("You get all the basic moves. Choose 2 angel moves.\n" +
                        "\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**keeping an eye out**_, and _**baiting a trap**_, as well as the rules for harm.")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask 1, 2, or all 3:\n" +
                        "\n" +
                        "- *Which one of you do I figure is doomed to self-destruction?* Give that character -2 for Hx.\n" +
                        "- *Which one of you put a hand in when it mattered, and helped me save a life?* Give that character +2 for Hx." +
                        "- *Which one of you has been beside me all along, and has seen everything I’ve seen?* Give that character +3 for Hx.\n" +
                        "\n" +
                        "Give everyone else +1 for Hx. You keep your eyes open.\n" +
                        HX_INSTRUCTIONS_END)
                .playbookUniqueCreator(angelUniqueCreator)
                .optionalMoves(angelOptionalMoves)
                .defaultMoves(angelDefaultMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .defaultVehicleCount(0)
                .build();


        /* ----------------------------- BATTLEBABE PLAYBOOK CREATOR --------------------------------- */
        List<Move> battlebabeOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.CHARACTER);

        List<Move> battlebabeDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.DEFAULT_CHARACTER);

        TaggedItem firearmBase1 = TaggedItem.builder().id(new ObjectId().toString()).description("handgun").tags(List.of("2-harm", "close", "reload", "loud")).build();
        TaggedItem firearmBase2 = TaggedItem.builder().id(new ObjectId().toString()).description("shotgun").tags(List.of("3-harm", "close", "reload", "messy")).build();
        TaggedItem firearmBase3 = TaggedItem.builder().id(new ObjectId().toString()).description("rifle").tags(List.of("2-harm", "far", "reload", "loud")).build();
        TaggedItem firearmBase4 = TaggedItem.builder().id(new ObjectId().toString()).description("crossbow").tags(List.of("2-harm", "close", "slow")).build();

        ItemCharacteristic firearmOption1 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("ornate").tag("+valuable").build();
        ItemCharacteristic firearmOption2 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("antique").tag("+valuable").build();
        ItemCharacteristic firearmOption3 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("semiautomatic").tag("-reload").build();
        ItemCharacteristic firearmOption4 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("3-round burst").tag("+1harm").build();
        ItemCharacteristic firearmOption5 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("automatic").tag("+area").build();
        ItemCharacteristic firearmOption6 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("silenced").tag("-loud").build();
        ItemCharacteristic firearmOption7 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("hi-powered").tag("close/far, or +1harm at far").build();
        ItemCharacteristic firearmOption8 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("ap ammo").tag("+ap").build();
        ItemCharacteristic firearmOption9 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("scoped").tag("+far, or +1harm at far").build();
        ItemCharacteristic firearmOption10 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("big").tag("+1harm").build();

        TaggedItem handBase1 = TaggedItem.builder().id(new ObjectId().toString()).description("staff").tags(List.of("1-harm", "hand", "area")).build();
        TaggedItem handBase2 = TaggedItem.builder().id(new ObjectId().toString()).description("haft").tags(List.of("1-harm", "hand")).build();
        TaggedItem handBase3 = TaggedItem.builder().id(new ObjectId().toString()).description("handle").tags(List.of("1-harm", "hand")).build();
        TaggedItem handBase4 = TaggedItem.builder().id(new ObjectId().toString()).description("chain").tags(List.of("1-harm", "hand", "area")).build();

        ItemCharacteristic handOption1 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("ornate").tag("+valuable").build();
        ItemCharacteristic handOption2 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("antique").tag("+valuable").build();
        ItemCharacteristic handOption3 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("head").tag("+1harm").build();
        ItemCharacteristic handOption4 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("spikes").tag("+1harm").build();
        ItemCharacteristic handOption5 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("blade").tag("+1harm").build();
        ItemCharacteristic handOption6 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("long blade*").tag("+2harm").build();
        ItemCharacteristic handOption7 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("heavy blade*").tag("+2harm").build();
        ItemCharacteristic handOption8 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("blades*").tag("+2harm").build();
        ItemCharacteristic handOption9 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("hidden").tag("+infinite").build();

        CustomWeaponsCreator customWeaponsCreator = CustomWeaponsCreator.builder()
                .id(new ObjectId().toString())
                .firearmsTitle("CUSTOM FIREARMS")
                .firearmsBaseInstructions("Base (choose 1):")
                .firearmsBaseOptions(List.of(firearmBase1, firearmBase2, firearmBase3, firearmBase4))
                .firearmsOptionsInstructions("Options (choose 2):")
                .firearmsOptionsOptions(List.of(firearmOption1, firearmOption2, firearmOption3, firearmOption4,
                        firearmOption5, firearmOption6, firearmOption7, firearmOption8, firearmOption9, firearmOption10))
                .handTitle("CUSTOM HAND WEAPONS")
                .handBaseInstructions("Base (choose 1):")
                .handBaseOptions(List.of(handBase1, handBase2, handBase3, handBase4))
                .handOptionsInstructions("Options (choose 2, * counts as 2 options):")
                .handOptionsOptions(List.of(handOption1, handOption2, handOption3, handOption4, handOption5,
                        handOption6, handOption7, handOption8, handOption9))
                .build();

        PlaybookUniqueCreator battlebabeUniqueCreator = PlaybookUniqueCreator.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.CUSTOM_WEAPONS)
                .customWeaponsCreator(customWeaponsCreator)
                .build();

        GearInstructions battlebabeGearInstructions = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("You get:")
                .youGetItems(List.of("fashion suitable to your look, including at your option fashion worth 1-armor or body armor worth 2-armor (you detail)"))
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .startingBarter(4)
                .build();

        PlaybookCreator battlebabePlaybookCreator = PlaybookCreator.builder()
                .playbookType(PlaybookType.BATTLEBABE)
                .gearInstructions(battlebabeGearInstructions)
                .improvementInstructions("Whenever you roll a highlighted stat, and whenever you reset your Hx with someone, mark an experience circle. When you mark the 5th, improve and erase.\n" +
                        "Each time you improve, choose one of the options. Check it off; you can’t choose it again.")
                .movesInstructions("You get all the basic moves. Choose 2 battlebabe moves.\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**standing overwatch**_, _**boarding a moving vehicle**_, and the _**subterfuge**_ moves.")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask the other players which of their characters you can trust.\n" +
                        "\n" +
                        "- *Give the characters you can trust -1 Hx.*\n" +
                        "- *Give the characters you can’t trust +3 Hx.*\n" +
                        "\n" +
                        "You are indifferent to what is safe, and drawn to what is not.\n" +
                        HX_INSTRUCTIONS_END)
                .playbookUniqueCreator(battlebabeUniqueCreator)
                .optionalMoves(battlebabeOptionalMoves)
                .defaultMoves(battlebabeDefaultMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .defaultVehicleCount(0)
                .build();

        /* ----------------------------- BRAINER PLAYBOOK CREATOR --------------------------------- */
        List<Move> brainerOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.CHARACTER);

        List<Move> brainerDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.DEFAULT_CHARACTER);

        BrainerGearCreator brainerGearCreator = BrainerGearCreator.builder()
                .id(new ObjectId().toString())
                .gear(List.of("implant syringe (tag hi-tech)" +
                                "_After you’ve tagged someone, if a brainer move allows you to inflict harm on them, inflict +1harm._",
                        "brain relay (area close hi-tech)" +
                                "_For purposes of brainer moves, if someone can see your brain relay, they can see you._",
                        "receptivity drugs (tag hi-tech)" +
                                "_Tagging someone gives you +1hold if you then use a brainer move on them._",
                        "violation glove (hand hi-tech)" +
                                "_For purposes of brainer moves, mere skin contact counts as time and intimacy._",
                        "pain-wave projector (1-harm ap area loud reload hi-tech)" +
                                "_Goes off like a reusable grenade. Hits everyone but you._",
                        "deep ear plugs (worn hi-tech)" +
                                "_Protects the wearer from all brainer moves and gear._"))
                .build();

        PlaybookUniqueCreator brainerUniqueCreator = PlaybookUniqueCreator.builder().type(UniqueType.BRAINER_GEAR)
                .id(new ObjectId().toString())
                .brainerGearCreator(brainerGearCreator)
                .build();

        GearInstructions brainerGearInstructions = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("You get:")
                .youGetItems(List.of("fashion suitable to your look, including at your option a piece worth 1-armor (you detail)"))
                .introduceChoice("Small fancy weapons")
                .numberCanChoose(1)
                .chooseableGear(List.of("silenced 9mm (2-harm close hi-tech)", "ornate dagger (2-harm hand valuable)", "hidden knives (2-harm hand infinite)", "scalpels (3-harm intimate hi-tech)", "antique handgun (2-harm close reload loud valuable)"))
                .startingBarter(8)
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .build();

        PlaybookCreator playbookCreatorBrainer = PlaybookCreator.builder()
                .playbookType(PlaybookType.BRAINER)
                .gearInstructions(brainerGearInstructions)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS)
                .movesInstructions("You get all the basic moves. Choose 2 brainer moves.\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**keeping an eye out**_, _**baiting a trap**_, and _**turning the tables**_.")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask 1, 2 or all 3:\n" +
                        "\n" +
                        "- *Which one of you has slept in my presence (knowingly or un-)?* Give that character +2 for Hx.\n" +
                        "- *Which one of you have I been watching carefully, in secret?* Give that character +2 for Hx.\n" +
                        "- *Which one of you most evidently dislikes and distrusts me?* Give that character +3 for Hx.\n" +
                        "\n" +
                        "Give everyone else +1 for Hx. You have weird insights into everyone.\n" +
                        HX_INSTRUCTIONS_END)
                .playbookUniqueCreator(brainerUniqueCreator)
                .optionalMoves(brainerOptionalMoves)
                .defaultMoves(brainerDefaultMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .defaultVehicleCount(0)
                .build();

        /* ----------------------------- CHOPPER PLAYBOOK CREATOR --------------------------------- */

        List<Move> chopperOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.CHOPPER, MoveType.CHARACTER);

        List<Move> chopperDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.CHOPPER, MoveType.DEFAULT_CHARACTER);

        GangOption gangOption1 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang consists of 30 or so violent bastards. Medium instead of small.")
                .modifier("MEDIUM")
                .build();

        GangOption gangOption2 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang's well-armed. +1harm")
                .modifier("+1harm")
                .build();

        GangOption gangOption3 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang's well-armored. +1armor")
                .modifier("+1armor")
                .build();

        GangOption gangOption4 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang's well-disciplined. Drop savage.")
                .tag("-savage")
                .build();

        GangOption gangOption5 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang's nomadic at heart, and able to maintain and repair its own bikes without a home base. It gets +mobile.")
                .tag("+mobile")
                .build();

        GangOption gangOption6 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang's self-sufficient, able to provide for itself by raiding and scavenging. It gets +rich")
                .tag("+rich")
                .build();

        GangOption gangOption7 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang's bikes are in bad shape and need constant attention. Vulnerable: breakdown.")
                .tag("+vulnerable: breakdown")
                .build();

        GangOption gangOption8 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang's bikes are picky and high-maintenance. Vulnerable: grounded.")
                .tag("+vulnerable: grounded")
                .build();

        GangOption gangOption9 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang's loose-knit, with members coming and going as they choose. Vulnerable: desertion")
                .tag("+vulnerable: desertion")
                .build();

        GangOption gangOption10 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang is in significant debt to someone powerful. Vulnerable: obligation.")
                .tag("+vulnerable: obligation")
                .build();

        GangOption gangOption11 = GangOption.builder()
                .id(new ObjectId().toString())
                .description("your gang is filthy and unwell. Vulnerable: disease.")
                .tag("+vulnerable: disease")
                .build();

        GangCreator gangCreator = GangCreator.builder()
                .id(new ObjectId().toString())
                .intro("By default, your gang consists of about 15 violent bastards with scavenged and makeshift weapons and armor, and no fucking discipline at all (2-harm gang small savage 1-armor)")
                .defaultSize(GangSize.SMALL)
                .defaultArmor(1)
                .defaultHarm(2)
                .strengthChoiceCount(2)
                .weaknessChoiceCount(1)
                .defaultTags(List.of("+savage"))
                .strengths(List.of(gangOption1, gangOption2, gangOption3, gangOption4, gangOption5, gangOption6))
                .weaknesses(List.of(gangOption7, gangOption8, gangOption9, gangOption10, gangOption11))
                .build();

        PlaybookUniqueCreator chopperUniqueCreator = PlaybookUniqueCreator.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.GANG)
                .gangCreator(gangCreator)
                .build();

        GearInstructions chopperGearInstructions = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("In addition to your bike and gang, you get:")
                .youGetItems(List.of("fashion suitable to your look, worth 1-armor or 2-armor (you detail)"))
                .introduceChoice("No-nonsense weapons")
                .numberCanChoose(2)
                .chooseableGear(List.of("magnum (3-harm close reload loud)",
                        "smg (2-harm close autofire load)",
                        "sawed-off (3-harm close reload messy)",
                        "crowbar (2-harm hand messy)",
                        "machete (3-harm hand messy)",
                        "crossbow (2-harm close slow)",
                        "wrist crossbow (1-harm close slow)"))
                .startingBarter(2)
                .withMC("If you’d like to start play with a prosthetic, get with the MC.")
                .build();

        PlaybookCreator playbookCreatorChopper = PlaybookCreator.builder()
                .playbookType(PlaybookType.CHOPPER)
                .gearInstructions(chopperGearInstructions)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS)
                .movesInstructions("You get all the basic moves. You  get both chopper moves.\n" +
                        "You can use all the battle moves, and probably will, but when you gotta start somewhere. When you get teh chance, look up _**seize by force**_, _**laying down fire**_, and the _**road war**_ moves, as well as the rules for how gangs inflict and suffer harm.")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask 1, 2 or all 3:\n" +
                        "\n" +
                        "- *Which one of you used to ride with my gang?* Give that character +1 for Hx.\n" +
                        "- *Which one of you figures that you could take me out in a fight, if it came to it?* Give that character +2 for Hx.\n" +
                        "- *Which one of you once stood up to me, gang and all?* Give that character +3 for Hx.\n" +
                        "\n" +
                        "Give everyone else -1 for Hx. You don't really care much about, y'know, people.\n" +
                        HX_INSTRUCTIONS_END)
                .playbookUniqueCreator(chopperUniqueCreator)
                .optionalMoves(chopperOptionalMoves)
                .defaultMoves(chopperDefaultMoves)
                .defaultMoveCount(3)
                .moveChoiceCount(0)
                .defaultVehicleCount(1)
                .build();

        /* ----------------------------- DRIVER PLAYBOOK CREATOR --------------------------------- */

        // Driver has no PlaybookUnique; hav Vehicles instead

        List<Move> driverOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.CHARACTER);

        List<Move> driverDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.DEFAULT_CHARACTER);
        GearInstructions driverGearInstructions = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("In addition to your car, you get:")
                .youGetItems(List.of("fashion suitable to your look, including at your option a piece worth 1-armor (you detail)"))
                .introduceChoice("Handy weapons")
                .numberCanChoose(1)
                .chooseableGear(List.of(
                        ".38 revolver (2-harm close reload loud)",
                        "9mm (2-harm close loud)",
                        "big knife (2-harm hand)",
                        "sawed-off (3-harm close reload messy)",
                        "machete (3-harm hand messy)",
                        "magnum (3-harm close reload loud"
                ))
                .startingBarter(4)
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .build();

        PlaybookCreator playbookCreatorDriver = PlaybookCreator.builder()
                .playbookType(PlaybookType.DRIVER)
                .gearInstructions(driverGearInstructions)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS)
                .movesInstructions("You get all the basic moves. Choose 2 driver moves.\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**standing overwatch**_, the _**road war**_ moves, and the rules for how vehicles suffer harm")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask 1, 2 or all 3:\n" +
                        "\n" +
                        "- *Which one of you got me out of some serious shit?* Give that character +1 for Hx.\n" +
                        "- *Which one of you has been with me for days on the road?* Give that character +2 for Hx.\n" +
                        "- *Which one of you have I caught sometimes staring out at the horizon?* Give that character +3 for Hx.\n" +
                        "\n" +
                        "Give everyone else +1 for Hx. You aren't naturally inclined to get too close to too many people.\n" +
                        HX_INSTRUCTIONS_END)
                .optionalMoves(driverOptionalMoves)
                .defaultMoves(driverDefaultMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .defaultVehicleCount(1)
                .build();

        /* ----------------------------- GUNLUGGER PLAYBOOK CREATOR --------------------------------- */

        List<Move> gunluggerOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.GUNLUGGER, MoveType.CHARACTER);

        List<Move> gunluggerDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.GUNLUGGER, MoveType.DEFAULT_CHARACTER);

        GearInstructions gearInstructionsGunlugger = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("You get:")
                .youGetItems(List.of("armor worth 2-armor (you detail)"))
                .startingBarter(2)
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .build();

        WeaponsCreator weaponsCreator = WeaponsCreator.builder()
                .id(new ObjectId().toString())
                .bfoGunOptionCount(1)
                .seriousGunOptionCount(2)
                .backupWeaponsOptionCount(1)
                .bigFuckOffGuns(List.of(
                        "silenced sniper rifle (3-harm far hi-tech)",
                        "mg (3-harm close/far area messy)",
                        "assault rifle (3-harm close/far loud autofire)",
                        "grenade launcher (4-harm close area messy)"
                ))
                .seriousGuns(List.of(
                        "hunting rifle (3-harm far loud)",
                        "shotgun (3-harm close messy)",
                        "smg (2-harm close autofire loud)",
                        "magnum (3-harm close reload loud)",
                        "grenade tube (4-harm close area reload messy)"
                ))
                .backupWeapons(List.of(
                        "9mm (2-harm close loud)",
                        "big-ass knife (2-harm hand)",
                        "machete (3-harm hand messy)",
                        "many knives (2-harm hand infinite)",
                        "grenades (4-harm hand area reload messy)"
                ))
                .build();

        PlaybookUniqueCreator gunluggerUniqueCreator = PlaybookUniqueCreator.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.WEAPONS)
                .weaponsCreator(weaponsCreator)
                .build();

        PlaybookCreator playbookCreatorGunlugger = PlaybookCreator.builder()
                .playbookType(PlaybookType.GUNLUGGER)
                .gearInstructions(gearInstructionsGunlugger)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS)
                .movesInstructions("You get all the basic moves. Choose 3 gunlugger moves.\n" +
                        "You can use all the battle moves, and probably will, but you gotta start somewhere. When you get the chance, look up _**seize by force**_ and  _**laying down fire**_.")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask 1, 2 or all 3:\n" +
                        "\n" +
                        "- *Which one of you left me bleeding, and did nothing for me?* Give that character -2 for Hx.\n" +
                        "- *Which one of you has fought shoulder to shoulder with me?* Give that character +2 for Hx.\n" +
                        "- *Which one of you is prettiest and/or smartest?* Give that character +3 for Hx.\n" +
                        "\n" +
                        "Give everyone else -1 for Hx. You find no particular need to understand most people.\n" +
                        HX_INSTRUCTIONS_END)
                .defaultMoves(gunluggerDefaultMoves)
                .optionalMoves(gunluggerOptionalMoves)
                .playbookUniqueCreator(gunluggerUniqueCreator)
                .defaultMoveCount(1)
                .moveChoiceCount(3)
                .defaultVehicleCount(0)
                .build();

        /* ----------------------------- HARDHOLDER PLAYBOOK CREATOR --------------------------------- */
        List<Move> hardholderDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.HARDHOLDER, MoveType.DEFAULT_CHARACTER);

        GearInstructions gearInstructionsHardholder = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("In addition to your holding, detail your personal fashion.\n" +
                        "\n" +
                        "You can have, for your personal use, with the MC's approval, a few pieces of non-specialized gear or weapons from any character playbook.")
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .build();

        HoldingOption holdingOption1 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your population in large, 200-300 souls. Surplus: +1barter, want: +disease")
                .surplusChange(1)
                .wantChange(List.of("+disease"))
                .newHoldingSize(HoldingSize.LARGE)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption2 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your population in small, 50-60 souls. Want: anxiety instead of want: hungry")
                .surplusChange(-2)
                .wantChange(List.of("+anxiety", "-hungry"))
                .newHoldingSize(HoldingSize.SMALL)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption3 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("for gigs, add lucrative raiding. Surplus: +1barter, want: +reprisals")
                .surplusChange(1)
                .wantChange(List.of("+reprisals"))
                .newHoldingSize(null)
                .gigChange("+lucrative raiding")
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption4 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("for gigs, add protection tribute. Surplus: +1barter, want: +obligation")
                .surplusChange(1)
                .wantChange(List.of("+obligation"))
                .newHoldingSize(null)
                .gigChange("+protection tribute")
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption5 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("for gigs, add a manufactory. Surplus: +1barter, want: +idle")
                .surplusChange(1)
                .wantChange(List.of("+idle"))
                .newHoldingSize(null)
                .gigChange("+a manufactory")
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption6 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("for gigs, add a bustling, widely-known market commons. Surplus: +1barter, want: +strangers")
                .surplusChange(1)
                .wantChange(List.of("+strangers"))
                .newHoldingSize(null)
                .gigChange("+market commons")
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption7 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your gang is large instead of medium, 60 violent bastards or so.")
                .surplusChange(-2)
                .wantChange(null)
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(GangSize.LARGE)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption8 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your gang is well-disciplined. Drop unruly.")
                .surplusChange(-2)
                .wantChange(null)
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange("-unruly")
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption9 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your armory is sophisticated and extensive. Your gang gets +1harm.")
                .surplusChange(-2)
                .wantChange(null)
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(1)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption10 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your garage includes 7 battle vehicles, plus a couple more utility vehicles if you want them.")
                .surplusChange(-2)
                .wantChange(null)
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(6)
                .newBattleVehicleCount(7)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption11 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your compound is tall, deep and mighty, of stone and iron. Your gang gets +2armor with fighting in its defense.")
                .surplusChange(-2)
                .wantChange(null)
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(2)
                .build();
        HoldingOption holdingOption12 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your population is filthy and unwell. Want: +disease.")
                .surplusChange(-2)
                .wantChange(List.of("+disease"))
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption13 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your population is lazy and drug-stupored. Want: +famine.")
                .surplusChange(-2)
                .wantChange(List.of("+famine"))
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption14 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your population is decadent and perverse. Surplus: -1barter, want: +savagery.")
                .surplusChange(-1)
                .wantChange(List.of("+savagery"))
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption15 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your holding owes protection to tribute. Surplus: -1barter, want: +reprisals")
                .surplusChange(-1)
                .wantChange(List.of("+reprisals"))
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption16 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your gang is small instead of medium, only 10-20 violent bastards.")
                .surplusChange(-2)
                .wantChange(null)
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(GangSize.SMALL)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption17 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your gang is a pack of fucking hyenas. Want: +savagery.")
                .surplusChange(-2)
                .wantChange(null)
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange("+savagery")
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption18 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your armory is for shit. Your gang gets -1harm.")
                .surplusChange(-2)
                .wantChange(null)
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-1)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption19 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your garage is for shit. It has only 4 vehicles, and only 2 of them are suitable for battle.")
                .surplusChange(-2)
                .wantChange(null)
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(2)
                .newBattleVehicleCount(2)
                .newArmorBonus(-1)
                .build();
        HoldingOption holdingOption20 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("your compound is mostly tents, lean-tos and wooden walls. Your gang gets no armor bonus when fighting to defend it.")
                .surplusChange(-2)
                .wantChange(null)
                .newHoldingSize(null)
                .gigChange(null)
                .newGangSize(null)
                .gangTagChange(null)
                .gangHarmChange(-2)
                .newVehicleCount(-1)
                .newBattleVehicleCount(-1)
                .newArmorBonus(0)
                .build();

        HoldingCreator holdingCreator = HoldingCreator.builder()
                .id(new ObjectId().toString())
                .defaultHoldingSize(HoldingSize.MEDIUM)
                .instructions("By default, your holding has:\n" +
                        "\n" +
                        "- 75-150 souls.\n" +
                        "- for gigs, a mix of hunting, crude farming, and scavenging. (surplus: 1-barter, want: hungry)\n" +
                        "- a makeshift compound of concrete, sheet metal and rebar. Your gang gets +1armor when fighting in its defense.\n" +
                        "- an armory of scavenged and makeshift weapons.\n" +
                        "- a garage of 4 utility vehicles and 4 specialized battle vehicles (detail with the MC).\n" +
                        "- a gang of about 40 violent bastards (2-harm gang medium unruly 1-armor).\n")
                .defaultGigs(List.of("hunting", "crude farming", "scavenging"))
                .defaultWant("hungry")
                .defaultArmorBonus(1)
                .defaultSurplus(1)
                .defaultVehiclesCount(4)
                .defaultBattleVehicleCount(4)
                .defaultGangSize(GangSize.MEDIUM)
                .defaultGangHarm(2)
                .defaultGangArmor(1)
                .defaultGangTag("unruly")
                .strengthCount(4)
                .weaknessCount(2)
                .strengthOptions(List.of(holdingOption1, holdingOption2, holdingOption3, holdingOption4, holdingOption5,
                        holdingOption6, holdingOption7, holdingOption8, holdingOption9, holdingOption10, holdingOption11))
                .weaknessOptions(List.of(holdingOption12, holdingOption13, holdingOption14, holdingOption15,
                        holdingOption16, holdingOption17, holdingOption18, holdingOption19, holdingOption20))
                .build();

        PlaybookUniqueCreator playbookUniqueCreatorHardHolder = PlaybookUniqueCreator.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.HOLDING)
                .holdingCreator(holdingCreator)
                .build();


        PlaybookCreator playbookCreatorHardHolder = PlaybookCreator.builder()
                .playbookType(PlaybookType.HARDHOLDER)
                .gearInstructions(gearInstructionsHardholder)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS)
                .movesInstructions("You get all the basic moves. You get both hardholder moves.\n" +
                        "You can use all the battle moves, and probably will, but you gotta start somewhere. When you get the chance, look up _**seize by force**_ and the rules for how gangs inflict and suffer harm.")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask either or both:\n" +
                        "\n" +
                        "- *Which one of you has been with me since before?* Give that character +2 for Hx.\n" +
                        "- *Which one of you has betrayed or stolen from me?* Give that character +3 for Hx.\n" +
                        "\n" +
                        "Give everyone else +1 for Hx. It's in your interests to know everyone's business.\n" +
                        HX_INSTRUCTIONS_END)
                .playbookUniqueCreator(playbookUniqueCreatorHardHolder)
                .defaultVehicleCount(4)
                .defaultMoves(hardholderDefaultMoves)
                .moveChoiceCount(0)
                .defaultMoveCount(3)
                .build();

        /* ----------------------------- HOCUS PLAYBOOK CREATOR --------------------------------- */
        List<Move> hocusDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.HOCUS, MoveType.DEFAULT_CHARACTER);

        List<Move> hocusMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.HOCUS, MoveType.CHARACTER);

        GearInstructions gearInstructionsHocus = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("In addition to your followers, detail your fashion according to your look. But apart from that and some barter, you have no gear to speak of.")
                .startingBarter(4)
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .build();

        FollowersOption followersOption1 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers are dedicated to you. Surplus: +1barter, and replace want: desertion with want: hungry.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(1)
                .fortuneChange(-1)
                .surplusChange(null)
                .wantChange(List.of("+hungry", "-desertion"))
                .build();
        FollowersOption followersOption2 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers are involved in successful commerce. +1fortune.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(1)
                .surplusChange(null)
                .wantChange(null)
                .build();
        FollowersOption followersOption3 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers, taken as a body, constitute a powerful psychic antenna. Surplus +augury.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(-1)
                .surplusChange("+augury")
                .wantChange(null)
                .build();
        FollowersOption followersOption4 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers are joyous and celebratory. Surplus: +party.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(-1)
                .surplusChange("+party")
                .wantChange(null)
                .build();
        FollowersOption followersOption5 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers are rigorous and argumentative. Surplus: +insight.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(-1)
                .surplusChange("+insight")
                .wantChange(null)
                .build();
        FollowersOption followersOption6 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers are hard-working, no-nonsense. +1barter.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(1)
                .fortuneChange(-1)
                .surplusChange(null)
                .wantChange(null)
                .build();
        FollowersOption followersOption7 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers are eager, enthusiastic and successful recruiters. Surplus: +growth.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(-1)
                .surplusChange("+growth")
                .wantChange(null)
                .build();
        FollowersOption followersOption8 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("You have few followers, 10 or fewer. Surplus: -1barter.")
                .newNumberOfFollowers(10)
                .surplusBarterChange(-1)
                .fortuneChange(-1)
                .surplusChange(null)
                .wantChange(null)
                .build();
        FollowersOption followersOption9 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers aren't really yours, more like you're theirs. Want: judgement instead of want: desertion.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(-1)
                .surplusChange(null)
                .wantChange(List.of("+judgement", "-desertion"))
                .build();
        FollowersOption followersOption10 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers rely entirely on you for their lives and needs. Want: +desperation.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(-1)
                .surplusChange(null)
                .wantChange(List.of("+desperation"))
                .build();
        FollowersOption followersOption11 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers are drug-fixated. Surplus: +stupor.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(-1)
                .surplusChange("+stupor")
                .wantChange(null)
                .build();
        FollowersOption followersOption12 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers disdain fashion, luxury and convention. Want: +disease.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(-1)
                .surplusChange(null)
                .wantChange(List.of("+disease"))
                .build();
        FollowersOption followersOption13 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers disdain law, peace, reason and society. Surplus: +violence.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(-1)
                .surplusChange("+violence")
                .wantChange(null)
                .build();
        FollowersOption followersOption14 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("Your followers are decadent and perverse. Want: +savagery.")
                .newNumberOfFollowers(-1)
                .surplusBarterChange(-2)
                .fortuneChange(-1)
                .surplusChange(null)
                .wantChange(List.of("+savagery"))
                .build();

        FollowersCreator followersCreator = FollowersCreator.builder()
                .id(new ObjectId().toString())
                .instructions("By default you have around 20 followers, loyal to you but not fanatical. They have their own lives apart from you, integrated into the local population (fortune+1 surplus: 1-barter want: desertion)")
                .defaultNumberOfFollowers(20)
                .defaultSurplusBarter(1)
                .defaultFortune(1)
                .strengthCount(2)
                .weaknessCount(2)
                .travelOptions(List.of("travel with you", "congregate in their own communities"))
                .characterizationOptions(List.of("your cult", "your scene", "your family", "your staff", "your students", "your court"))
                .defaultWants(List.of("desertion"))
                .strengthOptions(List.of(followersOption1, followersOption2, followersOption3, followersOption4,
                        followersOption5, followersOption6, followersOption7))
                .weaknessOptions(List.of(followersOption8, followersOption9, followersOption10, followersOption11,
                        followersOption12, followersOption13, followersOption14))
                .build();

        PlaybookUniqueCreator playbookUniqueCreatorHocus = PlaybookUniqueCreator.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.FOLLOWERS)
                .followersCreator(followersCreator)
                .build();

        PlaybookCreator playbookCreatorHocus = PlaybookCreator.builder()
                .playbookType(PlaybookType.HOCUS)
                .gearInstructions(gearInstructionsHocus)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS)
                .movesInstructions("You get all the basic moves. You get _**fortunes**_, and the choose 2 more hocus moves.\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**seize by force**_, _**keeping an eye out**_ and the rules for how gangs inflict and suffer harm.")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask either or both:\n" +
                        "\n" +
                        "- *Which one of you are my followers?* Give that character +2 for Hx.\n" +
                        "- *One of you, I've seen your soul. Which one?* Give that character +3 for Hx.\n" +
                        "\n" +
                        "Give everyone else +1 for Hx. You're a good and quick judge of others.\n" +
                        HX_INSTRUCTIONS_END)
                .playbookUniqueCreator(playbookUniqueCreatorHocus)
                .defaultVehicleCount(0)
                .defaultMoves(hocusDefaultMoves)
                .optionalMoves(hocusMoves)
                .moveChoiceCount(2)
                .defaultMoveCount(2)
                .build();

        /* ----------------------------- MAESTRO D' PLAYBOOK CREATOR --------------------------------- */
        List<Move> maestroDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.MAESTRO_D, MoveType.DEFAULT_CHARACTER);

        List<Move> maestroMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.MAESTRO_D, MoveType.CHARACTER);

        GearInstructions gearInstructionsMaestro = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("In addition to your establishment, you get:")
                .youGetItems(List.of(
                        "a wicked blade, like a kitchen knife or 12\" razor-sharp scissors (2-harm hand)",
                        "fashion suitable to your look, including at your option a piece worth 1-armor (you detail)"))
                .startingBarter(2)
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .build();

        SecurityOption securityOption1 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("a real gang (3-harm gang small 1-armor)")
                .value(2)
                .build();

        SecurityOption securityOption2 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("a convenient shotgun (3-harm close reload messy)")
                .value(1)
                .build();

        SecurityOption securityOption3 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("a bouncer who knows his biz (2-harm 1-armor)")
                .value(1)
                .build();

        SecurityOption securityOption4 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("plywood & chickenwire (1-armor)")
                .value(1)
                .build();

        SecurityOption securityOption5 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("secrecy, passwords, codes & signals, invites-only, vouching etc.")
                .value(1)
                .build();

        SecurityOption securityOption6 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("everybody's packing: your cast & crew are a gang (2-harm gang small 0-armor)")
                .value(1)
                .build();

        SecurityOption securityOption7 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("a warren of dead-ends, hideaways and boltholes")
                .value(1)
                .build();

        SecurityOption securityOption8 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("no fixed location, always new venues")
                .value(1)
                .build();

        EstablishmentCreator establishmentCreator = EstablishmentCreator.builder()
                .id(new ObjectId().toString())
                .mainAttractionCount(1)
                .sideAttractionCount(2)
                .attractions(List.of("luxury food", "music", "fashion", "lots of food", "sex", "spectacle", "easy food",
                        "games", "art", "drinks", "coffee", "drugs", "sports", "fights", "scene (see and be)"))
                .atmospheres(List.of("bustle", "intimacy", "smoke", "shadows", "perfume", "slime", "velvet", "fantasy",
                        "brass", "lights", "acoustics", "anonymity", "meat", "eavesdropping", "blood", "intrigue",
                        "violence", "nostalgia", "spice", "quiet", "luxury", "nudity", "restraint", "forgetting",
                        "pain", "kink", "candy", "protection", "grime", "noise", "dancing", "chill", "masks",
                        "fresh fruit", "a cage"))
                .atmosphereCount(List.of(3, 4))
                .regularsNames(List.of("Lamprey", "Ba", "Camo", "Toyota", "Lits"))
                .regularsQuestions(List.of(
                        "Who's your best regular?",
                        "Who's your worst regular?"
                ))
                .interestedPartyNames(List.of("Been", "Rolfball", "Gams"))
                .interestedPartyQuestions(List.of("Who wants in on it?", "Who do you owe for it?", "Who wants it gone?"))
                .securityOptions(List.of(securityOption1, securityOption2, securityOption3, securityOption4, securityOption5,
                        securityOption6, securityOption7, securityOption8))
                .build();

        PlaybookUniqueCreator playbookUniqueCreatorMaestro = PlaybookUniqueCreator.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.ESTABLISHMENT)
                .establishmentCreator(establishmentCreator)
                .build();

        PlaybookCreator playbookCreatorMaestro = PlaybookCreator.builder()
                .playbookType(PlaybookType.MAESTRO_D)
                .gearInstructions(gearInstructionsMaestro)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS)
                .movesInstructions("You get all the basic moves. Choose 2 maestro d' moves.\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**seize by force**_, _**baiting a trap**_ and _**turning the tables**_.")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask either or both:\n" +
                        "\n" +
                        "- *Which of you do I find most attractive?* Give that character +2 for Hx.\n" +
                        "- *Which of you is my favorite?* Give that character +3 for Hx.\n" +
                        "\n" +
                        "Give everyone else +1 for Hx. It's your business to see people clearly.\n" +
                        HX_INSTRUCTIONS_END)
                .playbookUniqueCreator(playbookUniqueCreatorMaestro)
                .defaultVehicleCount(0)
                .defaultMoves(maestroDefaultMoves)
                .optionalMoves(maestroMoves)
                .moveChoiceCount(2)
                .defaultMoveCount(1)
                .build();

        /* ----------------------------- SAVVYHEAD PLAYBOOK CREATOR --------------------------------- */
        List<Move> savvyheadDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.SAVVYHEAD, MoveType.DEFAULT_CHARACTER);

        List<Move> savvyheadMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.SAVVYHEAD, MoveType.CHARACTER);

        GearInstructions gearInstructionsSavvyhead = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("In addition to your workspace, detail your personal fashion, and any personal piece or three of normal gear or weaponry.")
                .startingBarter(6)
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .build();

        WorkspaceCreator workspaceCreator = WorkspaceCreator.builder()
                .id(new ObjectId().toString())
                .itemsCount(3)
                .workspaceInstructions("When you go into your workspace and dedicate yourself to making a thing, or to getting to the bottom of some shit, decide what an tell the MC.\n" +
                        "\n" +
                        "The MC will tell you 'sure, no problem, but...' and then 1 to 4 of the following things:\n" +
                        "\n" +
                        "- It's going to take hours/days/weeks/months of work.\n" +
                        "- First you'll have to get/build/fix/figure out _______.\n" +
                        "- You're going to need _______ to help you with it.\n" +
                        "- It's going to cost a fuckton of jingle.\n" +
                        "- The best you'll be able to do is a crap version, weak and unreliable.\n" +
                        "- It's going to mean exposing yourself (plus colleagues) to serious danger.\n" +
                        "- You're going to have to add ______ to your workplace first.\n" +
                        "- It's going to take several/dozens/hundreds of tries.\n" +
                        "- You're going to have to take ______ apart to do it.\n" +
                        "\n" +
                        "The MC might connect them all with 'and', or might throw in a merciful 'or'.\n" +
                        "\n" +
                        "Once you've completed the necessaries, you can go ahead and accomplish the thing itself. The MC will stat it up, or spill, or whatever it calls for."
                )
                .projectInstructions("During play, it's your job to have your character start and pursue projects. They can be any projects you want, both long term and short-.\n" +
                        "\n" +
                        "Begin by thinking up the project you're working this very morning, as play begins."
                )
                .workspaceItems(List.of(
                        "a garage", "a darkroom",
                        "a controlled growing environment",
                        "skilled labor (Carna, Thuy, Pamming eg)",
                        "a junkyard of raw materials",
                        "a truck or van",
                        "weird-ass electronica",
                        "machining tools",
                        "transmitters & receivers",
                        "a proving range",
                        "a relic of the golden age past",
                        "booby traps"
                ))
                .build();

        PlaybookUniqueCreator playbookUniqueCreatorSavvyhead = PlaybookUniqueCreator.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.WORKSPACE)
                .workspaceCreator(workspaceCreator)
                .build();

        PlaybookCreator playbookCreatorSavvyhead = PlaybookCreator.builder()
                .playbookType(PlaybookType.SAVVYHEAD)
                .gearInstructions(gearInstructionsSavvyhead)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS)
                .movesInstructions("You get all the basic moves. Choose 2 savvyhead moves.\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**keep an eye out**_, _**baiting a trap**_ and _**turning the tables**_, as well as the rules for how vehicles suffer harm.")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask either or both:\n" +
                        "\n" +
                        "- *Which of you is most strange?* Give that character +1 for Hx.\n" +
                        "- *Which one of you is the biggest potential problem?* Give that character +2 for Hx.\n" +
                        "\n" +
                        "Give everyone else -1 for Hx. You've got other stuff to do and other stuff to learn.\n" +
                        HX_INSTRUCTIONS_END)
                .playbookUniqueCreator(playbookUniqueCreatorSavvyhead)
                .defaultVehicleCount(0)
                .defaultMoves(savvyheadDefaultMoves)
                .optionalMoves(savvyheadMoves)
                .moveChoiceCount(2)
                .defaultMoveCount(1)
                .build();

        /* ----------------------------- SKINNER PLAYBOOK CREATOR --------------------------------- */
        List<Move> skinnerOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.SKINNER, MoveType.CHARACTER);

        List<Move> skinnerDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.SKINNER, MoveType.DEFAULT_CHARACTER);

        GearInstructions gearInstructionsSkinner = GearInstructions.builder()
                .id(new ObjectId().toString())
                .gearIntro("You get:")
                .youGetItems(List.of("fashion suitable to your look (you detail)"))
                .startingBarter(2)
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .build();

        SkinnerGearItem item1 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("sleeve pistol (2-harm close reload loud)")
                .build();
        SkinnerGearItem item2 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("ornate dagger (2-harm hand valuable)")
                .build();
        SkinnerGearItem item3 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("hidden knives (2-harm hand infinite)")
                .build();
        SkinnerGearItem item4 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("ornate sword (3-harm hand valuable)")
                .build();
        SkinnerGearItem item5 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("antique handgun (2-harm close reload loud valuable)")
                .build();
        SkinnerGearItem item6 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("antique coins (worn valuable)")
                .note("Drilled with holes for jewelry")
                .build();
        SkinnerGearItem item7 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("eyeglasses (worn valuable)")
                .note("You may use these for +1sharp when your eyesight matters, but if you do, without them you get -1sharp when your eyesight matters.")
                .build();
        SkinnerGearItem item8 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("long gorgeous coat (worn valuable)")
                .build();
        SkinnerGearItem item9 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("spectacular tattoos (implanted)")
                .build();
        SkinnerGearItem item10 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("skin & hair kit (applied valuable)")
                .note("Soaps, ochres, paints, creams, salves. Using it lets you take +1hot forward.")
                .build();
        SkinnerGearItem item11 = SkinnerGearItem.builder()
                .id(new ObjectId().toString())
                .item("a pet (valuable alive")
                .note("Your choice and yours to detail.")
                .build();

        SkinnerGearCreator skinnerGearCreator = SkinnerGearCreator.builder()
                .id(new ObjectId().toString())
                .graciousWeaponCount(1)
                .luxeGearCount(2)
                .graciousWeaponChoices(List.of(item1, item2, item3, item4, item5))
                .luxeGearChoices(List.of(item6, item7, item8, item9, item10, item11))
                .build();

        PlaybookUniqueCreator playbookUniqueCreatorSkinner = PlaybookUniqueCreator.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.SKINNER_GEAR)
                .skinnerGearCreator(skinnerGearCreator)
                .build();

        PlaybookCreator playbookCreatorSkinner = PlaybookCreator.builder()
                .playbookType(PlaybookType.SKINNER)
                .gearInstructions(gearInstructionsSkinner)
                .improvementInstructions(IMPROVEMENT_INSTRUCTIONS)
                .movesInstructions("You get all the basic moves. Choose 2 skinner moves.\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**standing overwatch**_, _**keeping an eye out**_, _**baiting a trap**_, and _**turning the tables**_.")
                .hxInstructions(HX_INSTRUCTIONS_START +
                        "Go around again for Hx. On your turn, ask 1, 2 or all 3:\n" +
                        "\n" +
                        "- *Which one of you is my friend?* Give that character +2 for Hx.\n" +
                        "- *Which one of you is my lover?* Give that character +1 for Hx.\n" +
                        "- *Which one of you is in love with me?* Give that character -1 for Hx.\n" +
                        "\n" +
                        "Give everyone else +1 or -1 for Hx, as you choose.\n" +
                        HX_INSTRUCTIONS_END)
                .playbookUniqueCreator(playbookUniqueCreatorSkinner)
                .defaultVehicleCount(0)
                .defaultMoves(skinnerDefaultMoves)
                .optionalMoves(skinnerOptionalMoves)
                .moveChoiceCount(2)
                .defaultMoveCount(1)
                .build();

        playbookCreatorService.saveAll(List.of(angelCreator,
                battlebabePlaybookCreator,
                playbookCreatorBrainer,
                playbookCreatorChopper,
                playbookCreatorDriver,
                playbookCreatorGunlugger,
                playbookCreatorHardHolder,
                playbookCreatorHocus,
                playbookCreatorMaestro,
                playbookCreatorSavvyhead,
                playbookCreatorSkinner
        ));
    }

    public void loadVehicleCreator() {
        VehicleFrame bikeFrame = VehicleFrame.builder()
                .id(new ObjectId().toString())
                .frameType(VehicleFrameType.BIKE)
                .massive(0)
                .examples("Road bike, trail bike, low-rider")
                .battleOptionCount(1)
                .build();

        VehicleFrame smallFrame = VehicleFrame.builder()
                .id(new ObjectId().toString())
                .frameType(VehicleFrameType.SMALL)
                .massive(1)
                .examples("Compact, buggy")
                .battleOptionCount(2)
                .build();

        VehicleFrame mediumFrame = VehicleFrame.builder()
                .id(new ObjectId().toString())
                .frameType(VehicleFrameType.MEDIUM)
                .massive(2)
                .examples("Coupe, sedan, jeep, pickup, van, limo, 4x4, tractor")
                .battleOptionCount(2)
                .build();

        VehicleFrame largeFrame = VehicleFrame.builder()
                .id(new ObjectId().toString())
                .frameType(VehicleFrameType.LARGE)
                .massive(3)
                .examples("Semi, bus, ambulance, construction/utility")
                .battleOptionCount(2)
                .build();

        VehicleBattleOption battleOption1 = VehicleBattleOption.builder()
                .id(new ObjectId().toString())
                .battleOptionType(BattleOptionType.SPEED)
                .name("+1speed")
                .build();

        VehicleBattleOption battleOption2 = VehicleBattleOption.builder()
                .id(new ObjectId().toString())
                .battleOptionType(BattleOptionType.HANDLING)
                .name("+1handling")
                .build();

        VehicleBattleOption battleOption3 = VehicleBattleOption.builder()
                .id(new ObjectId().toString())
                .battleOptionType(BattleOptionType.MASSIVE)
                .name("+1massive")
                .build();

        VehicleBattleOption battleOption4 = VehicleBattleOption.builder()
                .id(new ObjectId().toString())
                .battleOptionType(BattleOptionType.ARMOR)
                .name("+1armor")
                .build();

        List<String> bikeStrengths = List.of("fast",
                "rugged",
                "aggressive",
                "tight",
                "huge",
                "responsive");
        List<String> bikeLooks = List.of(
                "sleek",
                "vintage",
                "massively-chopped",
                "muscular",
                "flashy",
                "luxe",
                "roaring",
                "fat-ass");
        List<String> bikeWeaknesses = List.of("slow",
                "sloppy",
                "guzzler",
                "lazy",
                "unreliable",
                "cramped",
                "loud",
                "picky",
                "rabbity");

        BikeCreator bikeCreator = BikeCreator.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.BIKE)
                .introInstructions("By default, your bike has speed=0, handling=0, 0-armor and the massive rating of its frame.")
                .frame(bikeFrame)
                .strengths(bikeStrengths)
                .looks(bikeLooks)
                .weaknesses(bikeWeaknesses)
                .battleOptions(List.of(battleOption1, battleOption2))
                .build();

        List<String> carStrengths = List.of("fast",
                "rugged",
                "aggressive",
                "tight",
                "huge",
                "responsive",
                "off-road",
                "uncomplaining",
                "capacious",
                "workhorse",
                "easily repaired");

        List<String> carLooks = List.of(
                "sleek",
                "vintage",
                "muscular",
                "flashy",
                "luxe",
                "pristine",
                "powerful",
                "quirky",
                "pretty",
                "handcrafted",
                "spikes & plates",
                "garish");

        List<String> carWeaknesses = List.of("slow",
                "sloppy",
                "guzzler",
                "lazy",
                "unreliable",
                "cramped",
                "loud",
                "picky",
                "rabbity");

        CarCreator carCreator = CarCreator.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.CAR)
                .introInstructions("By default, your vehicle has speed=0, handling=0, 0-armor and the massive rating of its frame.")
                .frames(List.of(bikeFrame, smallFrame, mediumFrame, largeFrame))
                .strengths(carStrengths)
                .looks(carLooks)
                .weaknesses(carWeaknesses)
                .battleOptions(List.of(battleOption1, battleOption2, battleOption3, battleOption4))
                .build();

        VehicleBattleOption battleOption5 = VehicleBattleOption.builder()
                .id(new ObjectId().toString())
                .battleOptionType(BattleOptionType.WEAPON)
                .name("Mounted machine guns (3-harm close/far area messy)")
                .build();

        VehicleBattleOption battleOption6 = VehicleBattleOption.builder()
                .id(new ObjectId().toString())
                .battleOptionType(BattleOptionType.WEAPON)
                .name("Mounted grenade launcher (4-harm close area messy)")
                .build();

        VehicleBattleOption battleOption7 = VehicleBattleOption.builder()
                .id(new ObjectId().toString())
                .battleOptionType(BattleOptionType.WEAPON)
                .name("Ram or ramming spikes (as a weapon, vehicle inflicts +1harm)")
                .build();

        VehicleBattleOption battleOption8 = VehicleBattleOption.builder()
                .id(new ObjectId().toString())
                .battleOptionType(BattleOptionType.WEAPON)
                .name("Mounted 50cal mg (5-harm far area messy)")
                .build();

        VehicleBattleOption battleOption9 = VehicleBattleOption.builder()
                .id(new ObjectId().toString())
                .battleOptionType(BattleOptionType.WEAPON)
                .name("Mounted boarding platform or harness (+1 to attempts to board another vehicle from this one)")
                .build();

        BattleVehicleCreator battleVehicleCreator = BattleVehicleCreator.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.BATTLE)
                .battleVehicleOptions(List.of(battleOption1, battleOption2, battleOption3, battleOption4, battleOption5,
                        battleOption6, battleOption7, battleOption8, battleOption9))
                .build();

        VehicleCreator vehicleCreator = VehicleCreator.builder()
                .carCreator(carCreator)
                .bikeCreator(bikeCreator)
                .battleVehicleCreator(battleVehicleCreator)
                .build();

        vehicleCreatorService.save(vehicleCreator);
    }

    public void loadMcContent() {
        TickerList agenda = TickerList.builder()
                .id(new ObjectId().toString())
                .title("Agenda")
                .items(List.of("Make Apocalypse World seem real.",
                        "Make the player's characters' lives not boring.",
                        "Play to find out what happens."))
                .build();
        TickerList alwaysSay = TickerList.builder()
                .id(new ObjectId().toString())
                .title("Always say")
                .items(List.of("Always say what the principles demand.",
                        "Always say what the rules demand.",
                        "Always say what your prep demands.",
                        "Always say what honesty demands.")
                ).build();

        TickerList principles = TickerList.builder()
                .id(new ObjectId().toString())
                .title("The principles")
                .items(List.of(
                        "Barf forth apocalyptica.",
                        "Address yourself to the characters, not the players.",
                        "Make your move, but misdirect.",
                        "Make your move, but never speak its name.",
                        "Look through crosshairs.",
                        "Name everyone, make everyone human.",
                        "Ask provocative questions and build on the answers.",
                        "Respond with fuckery and intermittent rewards.",
                        "Be a fan of the characters.",
                        "Think offscreen, too.",
                        "Sometimes, disclaim decision-making."
                ))
                .build();
        TickerList moves = TickerList.builder()
                .id(new ObjectId().toString())
                .title("Your moves")
                .items(List.of("Separate them.",
                        "Capture someone.",
                        "Put someone in a spot.",
                        "Trade harm for harm (_as established_).",
                        "Announce off-screen badness.",
                        "Announce future badness.",
                        "Inflict harm (_as established_).",
                        "Take away their stuff.",
                        "Make them buy.",
                        "Activate their stuff's downside.",
                        "Tell them the possible consequences and ask.",
                        "Offer an opportunity, with or without a cost.",
                        "Turn their move back on them.",
                        "Make a threat move (_from one of your threats_).",
                        "After every move: \"what do you do?\""))
                .build();
        TickerList essentialThreats = TickerList.builder()
                .id(new ObjectId().toString())
                .title("Essential threats")
                .items(List.of("Where the PCs are, create as a landscape.",
                        "For any PC's gang, create as brutes.",
                        "For any PC's other NPCs, create as brutes, plus a grotesque and/or a wannabe warlord.",
                        "For any PC's vehicles, create as vehicles.",
                        "In any local populations, create an affliction"
                ))
                .build();
        TickerList moreThings = TickerList.builder()
                .id(new ObjectId().toString())
                .title("A few more things to do")
                .items(List.of(
                        "Make maps.",
                        "Turn questions back on the asker or over to the group at large.",
                        "Digress occasionally.",
                        "Elide the action sometimes, and zoom in on its details other times.",
                        "Go around the table.",
                        "Take breaks and take your time"
                ))
                .build();
        ContentItem decisionMaking = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("Decision-making")
                .content("In order to play to find out what happens, you'll need to pass decision-making off sometimes.\n" +
                        "\n" +
                        "Whenever something comes up that you'd prefer not to decide by personal whim and will, _don't_.\n" +
                        "\n" +
                        "The game gives your four key tools you can use to disclaim responsibility. You can:\n" +
                        "\n" +
                        "- Put it in your NPCs' hands.\n" +
                        "- Put it in the players' hands.\n" +
                        "- Create a countdown.\n" +
                        "- Make it a stakes question.")
                .build();
        ContentItem duringCharacterCreation = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("During character creation")
                .content("While the players are making their characters, there are some things to get out up-front:\n" +
                        "\n" +
                        "- Your characters don't have to be friends, but they should definitely be allies.\n" +
                        "- Your characters are unique in Apocalypse World.\n" +
                        "- 1-armor can be armor or clothing. 2-armor is armor.\n" +
                        "- Is _barter_ a medium of exchange? What do you use?\n" +
                        "- I'm not out to get you. I'm here to find out what's going to happen. Same as you!")
                .build();
        TickerList duringFirstSession = TickerList.builder()
                .id(new ObjectId().toString())
                .title("During the first session")
                .items(List.of(
                        "MC the game. Bring it.",
                        "Describe. Barf forth apocalyptica.",
                        "Springboard off character creation.",
                        "Ask questions all the time.",
                        "Leave yourself things to wonder about. Note them on the threat map.",
                        "Look for where they're not in control.",
                        "Push there.",
                        "Nudge the players to have their characters make their moves.",
                        "Give every character good screen time with other characters.",
                        "Leap forward with named, human NPCs.",
                        "Hell, have a fight.",
                        "Work on your threat map and essential threats."
                        )
                )
                .build();
        ContentItem threatMapInstructions = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("The threat map")
                .content("During play, keep notes on the threats in the world by noting them on your threat map.\n" +
                        "\n" +
                        "The innermost circle is for the PCs and their resources. There, list the PCs' gangs, followers, crews, vehicles, and everything else they won that you'll be responsible to play. Most of your essential threats go here.\n" +
                        "\n" +
                        "In the next circle out, \"closer\", is for the NPCs that surround them and their immediate landscape.\n" +
                        "\n" +
                        "The third circle, \"farther\", is for things that they would have to travel in order to encounter.\n" +
                        "\n" +
                        "Things that they have only heard rumors of, or ideas you have that have not yet introduced, you can write in the outside circle, as \"notional\"." +
                        "\n" +
                        "North, south, east and west are for geography. Up and down are for above and below.\n" +
                        "\n" +
                        "In is for threats within the local or surrounding landscape or population, out is for threats originating in the world's psychic maelstrom or even elsewhere."
                )
                .build();
        TickerList afterFirstSession = TickerList.builder()
                .id(new ObjectId().toString())
                .title("After the first session")
                .items(List.of("Go back over the threat map. Pull it apart into individual threats.",
                        "Consider the resources that are available to each of them, and the resources that aren't.",
                        "Create them as threats, using the threat creation rules.",
                        "Before the second session, be sure you've created your essential threats.")
                )
                .build();
        ContentItem harm = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("Harm")
                .content("_**Harm as established**_ equals the inflicter's weapon's harm minus the sufferer's armor.\n" +
                        "\n" +
                        "When you suffer harm, make the harm move.")
                .build();
        ContentItem exchangingHarm = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("Exchanging harm")
                .content("When you _**exchange harm**_, both side simultaneously inflict and suffer harm as established:\n" +
                        "\n" +
                        "- _You inflict harm equal to the harm rating of your weapon, minus the armor rating of your enemy's armor._\n" +
                        "- _You suffer harm equal to the harm rating of your enemy's weapon, minus the armor rating of your own armor._")
                .build();
        ContentItem degrees = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("Degrees of harm")
                .content("For each 1-harm you suffer, mark a segment of your harm countdown clock. Start in the 12:00-3:00 segment and continue around clockwise.\n" +
                        "\n" +
                        "The three segments before 9:00 are less serious, the three segments after 9:00 are more serious. Mark the 11:00-12:00 segment and your character's life has become untenable.\n" +
                        "\n" +
                        "Before 6:00 harm will go away by itself with time.\n" +
                        "\n" +
                        "6:00-9:00, the harm won't get worse or better by itself.\n" +
                        "\n" +
                        "After 9:00, unstabilized harm will get worse by itself, stabilized harm will not, and it'll get better only with medical treatment.\n" +
                        "\n" +
                        "When life becomes untenable, choose 1:\n" +
                        "\n" +
                        "- _Come back with -1hard_\n" +
                        "- _Come back with +1weird_\n" +
                        "- _Change to a new playbook_\n" +
                        "- _Die_"
                )
                .build();
        ContentItem npcHarm = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("When an NPC takes harm")
                .content("_**1-harm**: cosmetic damage, pain, concussion, fear if the NPC's likely to be afraid of pain._\n" +
                        "\n" +
                        "_**2-harm**: wounds, unconsciousness, bad pain,broken bones, shock. Likely fatal, occasionally immediately fatal._\n" +
                        "\n" +
                        "_**3-harm**: give it 50-50 it's immediately fatal. Otherwise, terrible wounds, shock, death soon._\n" +
                        "\n" +
                        "_**4-harm**: usually immediately fatal, but sometimes the poor fuck has to wait to die, mangled and ruined._\n" +
                        "\n" +
                        "_**5-harm and more**: fatal and increasingly bodily destructive._"
                )
                .build();
        ContentItem gangAsWeapon = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("Using a gang as a weapon")
                .content("When you have a gang, you can _**sucker someone**_, _**go aggro on them**_, or make a _**battle move**_, using your gang as a weapon.\n" +
                        "\n" +
                        "When you do, you roll the dice and make your choices, but it’s your gang that inflicts and suffers harm, not you yourself.\n" +
                        "\n" +
                        "Gangs _**inflict and suffer harm as established**_, as usual: they inflict harm equal to their own harm rating, minus their enemy’s armor rating, and the suffer harm equal to their enemy’s harm rating minus their own armor. Harm = weapon - armor.\n" +
                        "\n" +
                        "However, if there’s a _**size mismatch**_, the bigger gang inflicts +1harm and gets +1armor for each step of size difference:\n" +
                        "\n" +
                        "- *Against a single person, a small gang inflicts +1harm and gets +1armor. A medium gang inflicts +2harm and gets +2armor, and a large gang inflicts +3harm and gets +3armor.*\n" +
                        "- *Against a small gang, a medium gang inflicts +1harm and gets +1armor, and a large gang inflicts +2harm and gets +2armor.*\n" +
                        "- *Give you something they think you want, or tell you what you want to hear.*\n" +
                        "- *Against a medium gang, a large gang inflicts +1harm and gets +1armor.*")
                .build();
        ContentItem gangHarm = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("When a gang takes harm")
                .content("_**1-harm**: a few injuries, one or two serious, no fatalities._\n" +
                        "\n" +
                        "_**2-harm**: many injuries, several serious, a couple of fatalities._\n" +
                        "\n" +
                        "_**3-harm**: widespread injuries, many serious, several fatalities._\n" +
                        "\n" +
                        "_**4-harm**: widespread serious injuries, many fatalities._\n" +
                        "\n" +
                        "_**5-harm and more**: widespread fatalities, few survivors._\n" +
                        "\n" +
                        "_**Does the gang hold together?**_\n" +
                        "With a strong, present leader, a gang will hold together if it suffers up to 4-harm.\n" +
                        "\n" +
                        "If the leader is weak or absent, it'll hold together if it suffers up to 3-harm.\n" +
                        "\n" +
                        "If the leader is both weak and absent, it'll hold together if it suffers 1- or 2-harm.\n" +
                        "\n" +
                        "If it has no leader, it'll hold together if it suffers 1-harm, but no more.\n" +
                        "\n" +
                        "A PC gang leader can hold a gang together by imposing her will on it, if she has pack alpha, by ordering it to hold discipline, or if she has leadership, but otherwise it follows these rules, same as for NPCs.\n" +
                        "\n" +
                        "_**What about PC gang members?**_\n" +
                        "If a PC is a member of a gang taking harm, how much harm teh PC takes depends on her role in the gang.\n" +
                        "\n" +
                        "If she's a leader or prominent, visible member, she suffers the same harm the gang does.\n" +
                        "\n" +
                        "If she's just someone in the gang, or if she's intentionally protecting herself from harm instead of fighting with the gang, she suffers 1-harm less."
                )
                .build();
        ContentItem vehicleAsWeapon = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("Using a vehicle as a weapon")
                .content("When you you're behind the wheel, you can _**sucker someone**_, _**go aggro on them**_, or make a _**battle move**_, using your vehicle as a weapon.\n" +
                        "\n" +
                        "When you do, you roll the dice and make your choices, but it’s your vehicle that inflicts and suffers harm, not you yourself.\n" + "\n" +
                        "\n" +
                        "_**Against a person**_\n" +
                        "\n" +
                        "- _A glancing hit from a moving vehicle inflicts 2-harm (ap)._\n" +
                        "- _A direct hit from a moving vehicle inflicts 3-harm (ap) plus its massive._\n" +
                        "\n" +
                        "_**Against another vehicle:**_\n" +
                        "\n" +
                        "- _A glancing hit inflicts v-harm._\n" +
                        "- _A direct hit inflicts 3-harm plus its massive, minus the target vehicle's massive and armor. Treat 0-harm and less as v-harm._\n" +
                        "- _When you're able to ram or T-bone another vehicle, you inflict the harm of a direct hit (3-harm + massive, minus your target's armor + massive) and suffer the harm of a glancing hit (v-harm)._\n" +
                        "\n" +
                        "_**Against a building or structure:**_\n" +
                        "\n" +
                        "- _A glancing hit from a moving vehicle inflicts 2-harm._\n" +
                        "- _A direct hit from a moving vehicle inflicts 3-harm plus its massive, minus the structure's armor._"
                )
                .build();
        ContentItem vehicleHarm = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("When a vehicle suffers harm")
                .content("Vehicles can suffer regular harm, from bullets, explosions and direct hits from other vehicles, or v-harm, from glancing hits.\n" +
                        "\n" +
                        "When a vehicle suffers regular harm, there are two considerations: how much damage the vehicle itself suffers, and how much blows through to the people inside.\n" +
                        "\n" +
                        "_**1-harm**: cosmetic damage. Bullet holes, broken glass, smoke. **0-harm can blow through** to passengers._\n" +
                        "\n" +
                        "_**2-harm**: functional damage. Fuel leak, shot-out tires, engine stall, problems with steering, braking or acceleration. Can be field-patched. **1-harm can blow through** to passengers._\n" +
                        "\n" +
                        "_**3-harm**: serious damage. Functional damage affecting multiple functions, but can be field patched. **2-harm can blow through** to passengers._\n" +
                        "\n" +
                        "_**4-harm**: breakdown. Catastrophic functional damage, can be repaired in the garage but not in the field, or can be used for parts. **3-harm can blow through** to passengers._\n" +
                        "\n" +
                        "_**5-harm and more**: total destruction. **Full harm can blow through** to passengers, plus they can suffer additional harm if the vehicle explodes or crashes._\n" +
                        "\n" +
                        "Whether harm blows through to a vehicle's driver and passengers, doesn't blow through, or just hits them too without having to blow through, depends on the MC's judgement of the circumstances and the vehicle.")
                .build();
        ContentItem vharm = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("V-harm")
                .content("_**V-harm as established**_ is the attacking car's massive minus the defending car's massive or handling, defender's choice.\n" +
                        "\n" +
                        "When you _**suffer vh-harm**_, roll+v-harm suffered.\n" +
                        "\n" +
                        "On a 10+, you lose control, and your attacker chooses 1:\n" +
                        "\n" +
                        "- _You crash._\n" +
                        "- _You spin out._\n" +
                        "- _Choose 2 from the 7-9 list below._\n" +
                        "\n" +
                        "On a 7-9, you're forced to swerve. Your attacker chooses 1:\n" +
                        "\n" +
                        "- _You give ground._\n" +
                        "- _You're driven off course, or forced into a new course._\n" +
                        "- _Your car takes 1-harm ap, right in the transmission._\n" +
                        "\n" +
                        "On a miss, you swerve but recover without disadvantage."
                )
                .build();
        ContentItem buildingHarm = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("When a building takes harm")
                .content("As with vehicles, when a building suffers harm, there are two considerations: structural damage to the building itself, and how much of the harm blows through to the people inside.\n" +
                        "\n" +
                        "Harm to buildings and other structures is non-cumulative. Don't bother tracking a building's harm on a countdown. Shooting a building 3 times with your 3-harm shotgun doesn't add up to 9-harm and make the building collapse.\n" +
                        "\n" +
                        "_**When a building or structure suffers...**_\n" +
                        "\n" +
                        "_**1-harm - 3-harm**: cosmetic damage. Bullet holes, broken glass, scorch marks, chipped surfaces. **0-harm can blow through** to inhabitants._\n" +
                        "\n" +
                        "_**4-harm - 6-harm**: severe cosmetic damage. Many holes or large holes, no intact glass, burning or smoldering. **2-harm can blow through** to inhabitants._\n" +
                        "\n" +
                        "_**7-harm - 8-harm**: structural damage. Strained load-bearing walls or pillars, partial collapse, raging fire. **4-harm can blow through** to inhabitants.  Further structural damage can lead to full collapse._\n" +
                        "\n" +
                        "_**9-harm and more**: destruction. **Full harm can blow through** to inhabitants, plus they can suffer additional harm as the building or structure collapses._\n" +
                        "\n" +
                        "Whether harm actually blows through to a building's inhabitants depends on the MC's judgement of the circumstances and the building. Don't stand near the windows!")
                .build();
        ContentItem dHarm = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("D-harm")
                .content("A person suffers d-harm from deprivation. d-harm is strictly for acute cases of deprivation. For scarcity and chronic deprivation, create affliction threats instead.\n" +
                        "\n" +
                        "Individual NPCs simply suffer the effects of d-harm as follows.\n" +
                        "\n" +
                        "For PCs, suffering d-harm, tell them the effects they're suffering, and if they can't or don't alleviate their deprivation, inflict regular harm alongside it, in increments of 1-harm ap.\n" +
                        "\n" +
                        "For a population suffering d-harm, the two questions are how it behaves, and how long it can last before breaking up, tearing itself apart, or dying.\n" +
                        "\n" +
                        "_**d-harm (air)**_, asphyxiation: Difficulty breathing, panic, convulsions, paralysis, unconsciousness, brain damage, death within minutes.\n" +
                        "\n" +
                        "Inflicted on a population: Immediate panic. Social cohesion breaks down basically at once into a survival-driven desperation to find air.\n" +
                        "\n" +
                        "_**d-harm (warmth)**_, hypothermia: shivering, hunger, dizziness, confusion, drowsiness, frostbite, delirium, unconsciousness, irregular heartbeat, death in an hour or more, depending on the cold.\n" +
                        "\n" +
                        "Inflicted on a population: Huddling together, despair, lethargy, resignation. Isolated individuals suffer worsening individual symptoms, so social cohesion can last basically as long as the individuals can.\n" +
                        "\n" +
                        "_**d-harm (cool)**_, heat stroke: headache, dehydration, weakness or cramps, confusion, fever, vomiting, seizures, unconsciousness, death in an hour or more, depending on the heat.\n" +
                        "\n" +
                        "Inflicted on a population: Desperation, panic, lethargy, resignation. Social cohesion can last as long as the individuals can, as the less vulnerable individuals try to help the more vulnerable.\n" +
                        "\n" +
                        "_**d-harm (water)**_, dehydration: desperation, headache, confusion, delirium, collapse, death in 3 days.\n" +
                        "\n" +
                        "Inflicted on a population: Rationing and hoarding, desperation, infighting. Social cohesion can last up to a week before breaking down into violence or dispersal.\n" +
                        "\n" +
                        "_**d-harm (food)**_, starvation: irritability, hunger, weakness, diarrhea, lethargy, dehydration, muscular atrophy, heart failure and death within 2-3 months.\n" +
                        "\n" +
                        "Inflicted on a population: Rationing and hoarding, desperation, infighting. Social cohesion can last up to 2 weeks before breaking down into violence, cannibalism, or dispersal.\n" +
                        "\n" +
                        "_**d-harm (sleep)**_, sleep deprivation: irritability, disorientation, nodding off, depression, headache, hallucinations, mania, personality changes, bizarre behavior.\n" +
                        "\n" +
                        "Inflicted on a population: Malaise, infighting, tantrums, desperation. For long-term acute sleep deprivation, create affliction threats instead."
                )
                .build();
        ContentItem psiHarm = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("&Psi;-harm")
                .content("A person suffers &Psi;-harm from exposure to the world's psychic maelstrom.\n" +
                        "\n" +
                        "When an NPC suffers &Psi;-harm, create her as a threat if necessary, and then choose any or all:\n" +
                        "\n" +
                        "- _She aggressively pursues her threat impulse. Make moves on her behalf as hard and as direct as you can._\n" +
                        "- _Her sanity shatters. She is incoherent, raving, raging or unresponsive, alive but gone._\n" +
                        "- _She abruptly changes threat type._\n" +
                        "\n" +
                        "_**For players' characters:**_\n" +
                        "When you suffer &Psi;-harm, roll+&Psi;-harm suffered (typically, roll+1).\n" +
                        "\n" +
                        "On a 10+, the MC can choose 1:\n" +
                        "\n" +
                        "- _You're out of action: unconscious, trapped, incoherent or panicked._\n" +
                        "- _You're out of your own control. You come to yourself again a few seconds later, having done I-don't-know-what._\n" +
                        "- _Choose 2 from the list below._\n" +
                        "\n" +
                        "On a 7-9, the MC can choose 1:\n" +
                        "\n" +
                        "- _You lose your footing._\n" +
                        "- _You lose your grip on whatever you're holding._\n" +
                        "- _You lose track of someone or something you're attending to._\n" +
                        "- _You miss noticing something important._\n" +
                        "- _You take a single concrete action of the MC's choosing._\n" +
                        "\n" +
                        "On a miss, you keep it together and overcome the -harm with no effect.")
                .build();
        ContentItem sHarm = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("S-harm")
                .content("s-harm means stun. It disables its target without causing any regular harm. Use it on a PC, and doing anything at all means doing it under fire; the fire is \"you're stunned\".")
                .build();
        ContentItem lifestyle = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("Lifestyle")
                .content("If the player pays _**1-barter**_ at the beginning of the session, the character has the same quality of life as most people around her.\n" +
                        "\n" +
                        "If she pays _**2-barter**_ at the beginning of the session, she has a quality of life that is substantially, notably better. Whatever other people eat, drink, and wear, she's eating, drinking, and wearing better. She sleeps more comfortably and safer, and has more control over her personal environment.\n" +
                        "\n" +
                        "If she pays _**0-barter**_, it could mean that at the beginning of the session she's desperately hungry and dying of thirst, but first ask some questions.\n" +
                        "\n" +
                        "First, to the other players: \"okay, so who's going to pay 1-barter to keep her alive?\"\n" +
                        "\n" +
                        "If none of them can or will, choose:\n" +
                        "\n" +
                        "- _Go straight to, yes, she's desperately hungry and dying of thirst. Inflict harm as established. Take away her stuff._\n" +
                        "- _Choose a suitable NPC -- Rolfball, for instance -- and say \"oh, that's okay, Rolfball's got you covered. That's good with you, yeah?\" Now there's a debt between her and your NPC, and you can bring it into play whenever you like._\n" +
                        "- _Say, \"well, okay, who do you think should pay to keep you alive? Rolfball? Fish? Who?\" You can either negotiate an appropriate arrangement in summary, or else jump into play: have her read a person, seduce and manipulate, go aggro, or whatever she needs to do to get her way._\n" +
                        "\n" +
                        "And remember that if she doesn't pay, and the other PCs don't pay, and no NPC pays, then tough luck. She's desperately hungry, dying of thirst, and you should inflict harm and take away her stuff."
                )
                .build();
        ContentItem gigs = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("Gigs")
                .content("When a player tells you that her character would like to _**work a gig**_, ask her what she has in mind. She might pick one from her list, or she might suggest a new one.\n" +
                        "\n" +
                        "It's up to you to decide whether to say yes, no, or not yet, like \"that's a good idea, but first you'll have to...\"\n" +
                        "\n" +
                        "Once you've settles that question and the character works the gig, you have to decide or find out how it goes. Successful? Unsuccessful? Dangerous? Easy?\n" +
                        "\n" +
                        "- _You can just decide._\n" +
                        "- _You can play it out in full._\n" +
                        "- _You can call for a move, or a quick snowball or moves, to summarize what happens._\n" +
                        "\n" +
                        "No matter which way you do it, the baseline for the pay is, when they work a gig, they get _**3-barter**_. You're allowed to pay 2- or 4-barter when you feel like it should, but save them for exceptional outcomes.")
                .build();
        ContentItem creatingVehicle = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("Creating a vehicle")
                .content("_These are rules for a wider range of vehicles than is usually available to the PCs._\n" +
                        "\n" +
                        "By default, any vehicle has speed=0, handling=0, 0-armor, and the massive rating of its frame.\n" +
                        "\n" +
                        "Name its frame and assign it a massive from 0 up. Use these examples to guide you:\n" +
                        "\n" +
                        "- _Massive=0: bike, jetski, canoe, ultralight, 1 battle option._\n" +
                        "- _Massive=1: trike or big 4-wheeler, tiny car, rowboat. 2 battle options._\n" +
                        "- _Massive=2: car, small speedboat, autogyro. 2 battle options._\n" +
                        "- _Massive=3: huge road vehicle, speedboat, yacht, small helicopter, small prop plane. 2 battle options._\n" +
                        "- _Massive=4: huge construction vehicle, and actual tank, tour boat, helicopter, small jet, single train car. 3 battle options._\n" +
                        "- _Massive=5: large tour boat, large helicopter, small passenger plane or jet. 4 battle options._\n" +
                        "- _Massive over 5: for each 1massive over, add +1battle option._\n" +
                        "\n" +
                        "_**Strengths**_ (choose 1 or 2): Fast, rugged, aggressive, tight, huge, off-road, responsive, uncomplaining, capacious, workhorse, easily-repaired, or make one up.\n" +
                        "\n" +
                        "_**Looks**_ (choose 1 or 2): Sleek, vintage, pristine, powerful, luxe, flashy, muscular, quirky, pretty handcrafted, spikes & plates, garish, cobbled-together, hardworked, or make one up.\n" +
                        "\n" +
                        "_**Weakness**_ (choose 1 or 2): Slow, loud, lazy, sloppy, cramped, picky, guzzler, unreliable, rabbity, temperamental, or make one up.\n" +
                        "\n" +
                        "_**Battle options**_ (choose according to frame): +1speed, +1handling, +1massive, +1armor. You can double up if you choose."
                )
                .build();
        ContentItem creatingBattleVehicle = ContentItem.builder()
                .id(new ObjectId().toString())
                .title("Creating a specialized battle vehicle")
                .content("Create the vehicle as normal, and then choose 2:\n" +
                        "\n" +
                        "- _+1 battle option (+1speed, +1handling, +1massive, or +1 armor)_\n" +
                        "- _Mounted machine guns (3-harm close/far area messy)_\n" +
                        "- _Mounted grenade launcher (4-harm close area messy)_\n" +
                        "- _Ram or ramming spikes (as a weapon, vehicle inflicts +1harm)_\n" +
                        "- _Mounted 50cal mg (5-harm far area messy)_\n" +
                        "- _Mounted boarding platform or harness (+1 to attempts to board another vehicle from this one)_")
                .build();
        FirstSessionContent firstSessionContent = FirstSessionContent.builder()
                .id(new ObjectId().toString())
                .intro("The players have it easy. They have these fun little procedures to go through and then they're ready to play.\n" +
                        "\n" +
                        "Your job is harder, you have a lot more to set up then they do.\n" +
                        "\n" +
                        "They each have one character to create, you have the whole bedamned world, so you get the whole first session to create it in."
                )
                .duringCharacterCreation(duringCharacterCreation)
                .duringFirstSession(duringFirstSession)
                .threatMapInstructions(threatMapInstructions)
                .afterFirstSession(afterFirstSession)
                .build();

        McContent mcContent = McContent.builder()
                .firstSessionContent(firstSessionContent)
                .decisionMaking(decisionMaking)
                .core(List.of(agenda, alwaysSay, principles, moves, essentialThreats, moreThings))
                .harm(List.of(harm, exchangingHarm, degrees, npcHarm, gangAsWeapon, gangHarm, vehicleAsWeapon, vehicleHarm, vharm, buildingHarm, dHarm, psiHarm, sHarm))
                .selected(List.of(lifestyle, gigs, creatingVehicle, creatingBattleVehicle))
                .build();

        mcContentService.save(mcContent);
    }

    public void loadPlaybooks() {
        System.out.println("|| --- Loading playbooks --- ||");
        /* ----------------------------- ANGEL PLAYBOOK --------------------------------- */

        Playbook angel = Playbook.builder()
                .playbookType(PlaybookType.ANGEL)
                .barterInstructions("At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
                        "If you need jingle during a session, tell the MC you’d like to work a gig. Your gigs:\n" +
                        "\n" +
                        "- *Tend to the health of a dozen families or more*\n" +
                        "- *Serve a wealthy NPC as angel on call*\n" +
                        "- *Serve a warlord NPC as combat medic*\n" +
                        "- *Others, as you negotiate them*\n" +
                        "\n" +
                        "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                        "\n" +
                        "- *a night in high luxury & company*\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *repair of a piece of hi-tech gear*\n" +
                        "- *a session’s hire of a violent individual as bodyguard*\n" +
                        "- *a few sessions’ tribute to a warlord; a few sessions’ maintenance and repairs for a hi-performance vehicle well-used*\n" +
                        "- *bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                        "\n" +
                        "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.")
                .intro("When you’re lying in the dust of Apocalypse World guts aspilled, for whom do you pray? The gods? They’re long gone. Your beloved comrades? Fuckers all, or you wouldn’t be here to begin with. Your precious old mother? She’s a darling but she can’t put an intestine back inside so it’ll stay. No, you pray for some grinning kid or veteran or just someone with a heartshocker and a hand with sutures and a 6-pack of morphine. And when that someone comes, _that’s_ an angel.")
                .introComment("Angels are medics. If you want everybody to love you, or at least rely on you, play an angel. Warning: if things are going well, maybe nobody will rely on you. Make interesting relationships so you’ll stay relevant. Or sabotage things, I guess.")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/angel-white-transparent.png")
                .build();

        Playbook battlebabe = Playbook.builder()
                .playbookType(PlaybookType.BATTLEBABE)
                .barterInstructions("At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
                        "\n" +
                        "If you need jingle during a session, tell the MC you’d like to work a gig. Your gigs:\n" +
                        "\n" +
                        "- *Extort, raid, or rob a wealthy population*\n" +
                        "- *Execute a murder on behalf of a wealthy NPC*\n" +
                        "- *Serve a wealthy NPC as a bodyguard*\n" +
                        "- *Others, as you negotiate them*\n" +
                        "\n" +
                        "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                        "\n" +
                        "- *a night in high luxury & company*\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *repair of a piece of hi-tech gear*\n" +
                        "- *a session’s hire of a violent individual as bodyguard*\n" +
                        "- *a few sessions’ tribute to a warlord; a few sessions’ maintenance and repairs for a hi-performance vehicle well-used*\n" +
                        "- *bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                        "\n" +
                        "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.")
                .intro("Even in a place as dangerous as Apocalypse World, battlebabes are, well. They’re the ones you should walk away from, eyes down, but you can’t. They’re the ones like the seductive blue crackling light, y’know? You mistake looking at them for falling in love, and you get too close and it’s a zillion volts and your wings burn off like paper.")
                .introComment("Dangerous.\n" +
                        "\n" +
                        "Battlebabes are good in battle, of course, but they’re wicked social too. If you want to play somebody dangerous and provocative, play a battlebabe. Warning: you might find that you’re better at making trouble than getting out of it. If you want to play the baddest ass, play a gunlugger instead.")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/battlebabe-white-transparent.png")
                .build();

        Playbook brainer = Playbook.builder()
                .playbookType(PlaybookType.BRAINER)
                .barterInstructions("At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
                        "\n" +
                        "If you need jingle during a session, tell the MC you’d like to work a gig. Your gigs:\n" +
                        "\n" +
                        "- *Interrogate a warlord NPC’s prisoners*\n" +
                        "- *Extort or blackmail a wealthy NPC*\n" +
                        "- *Serve a wealthy NPC as kept brainer*\n" +
                        "- *Others, as you negotiate them*\n" +
                        "\n" +
                        "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                        "\n" +
                        "- *a night in high luxury & company*\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *repair of a piece of hi-tech gear*\n" +
                        "- *a session’s hire of a violent individual as bodyguard*\n" +
                        "- *a few sessions’ tribute to a warlord; a few sessions’ maintenance and repairs for a hi-performance vehicle well-used*\n" +
                        "- *bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                        "\n" +
                        "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.")
                .intro("Brainers are the weird psycho psychic mindfucks of Apocalypse World. They have brain control, puppet strings, creepy hearts, dead souls, and eyes like broken things. They stand in your peripheral vision and whisper into your head, staring. They clamp lenses over your eyes and read your secrets.\n" +
                        "\n" +
                        "They’re just the sort of tasteful accoutrement that no well-appointed hardhold can do without.")
                .introComment("Brainers are spooky, weird, and really fun to play. Their moves are powerful but strange. If you want everybody else to be at least a little bit afraid of you, a brainer is a good choice. Warning: you’ll be happy anyway, but you’ll be happiest if somebody wants to have sex with you even though you’re a brainer. Angle for that if you can.")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/brainer-white-transparent.png")
                .build();

        Playbook chopper = Playbook.builder()
                .playbookType(PlaybookType.CHOPPER)
                .barterInstructions("At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
                        "\n" +
                        "If you need jingle during a session, tell the MC you’d like to work a gig. Your gigs:\n" +
                        "\n" +
                        "- *Extort, raid, or rob a wealthy population*\n" +
                        "- *Execute a murder on behalf of a wealthy NPC*\n" +
                        "- *Serve a wealthy NPC as a bodyguard*\n" +
                        "- *Others, as you negotiate them*\n" +
                        "\n" +
                        "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                        "\n" +
                        "- *a night in high luxury & company*\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *repair of a piece of hi-tech gear*\n" +
                        "- *a session’s hire of a violent individual as bodyguard*\n" +
                        "- *a few sessions’ tribute to a warlord; a few sessions’ maintenance and repairs for a hi-performance vehicle well-used*\n" +
                        "- *bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                        "\n" +
                        "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.")
                .intro("Apocalypse World is all scarcity, of course it is. There’s not enough wholesome food, not enough untainted water, not enough security, not enough light, not enough electricity, not enough children, not enough hope.\n" +
                        "\n" +
                        "However, the Golden Age Past did leave us two things: enough gasoline, enough bullets. Come the end, I guess the fuckers didn’t need them like they thought they would.\n" +
                        "\n" +
                        "So chopper, there you are. Enough for you.")
                .introComment("Choppers lead biker gangs. They’re powerful but lots of their power is external, in their gang. If you want weight to throw around, play a chopper—but if you want to be really in charge, play a hardholder instead. Warning: externalizing your power means drama. Expect drama.")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/chopper-white-transparent.png")
                .build();

        Playbook driver = Playbook.builder()
                .playbookType(PlaybookType.DRIVER)
                .barterInstructions("At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
                        "\n" +
                        "If you need jingle during a session, tell the MC you’d like to work a gig. Your gigs:\n" +
                        "\n" +
                        "- *Serve a wealthy NPC as driver*\n" +
                        "- *Serve a wealthy NPC as courier*\n" +
                        "- *Others, as you negotiate them*\n" +
                        "\n" +
                        "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                        "\n" +
                        "- *a night in high luxury & company*\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *repair of a piece of hi-tech gear*\n" +
                        "- *a session’s hire of a violent individual as bodyguard*\n" +
                        "- *a few sessions’ tribute to a warlord; a few sessions’ maintenance and repairs for a hi-performance vehicle well-used*\n" +
                        "- *bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                        "\n" +
                        "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.")
                .intro("Came the apocalypse, and the infrastructure of the Golden Age tore apart. Roads heaved and split. Lines of life and communication shattered. Cities, cut off from one another, raged like smashed anthills, then burned, then fell.\n" +
                        "\n" +
                        "A few living still remember it: every horizon scorching hot with civilization in flames, light to put out the stars and moon, smoke to put out the sun.\n" +
                        "\n" +
                        "In Apocalypse World the horizons are dark, and no roads go to them.")
                .introComment("Drivers have cars, meaning mobility, freedom, and places to go. If you can’t see the post-apocalypse without cars, you gotta be a driver. Warning: your loose ties can accidentally keep you out of the action. Commit to the other characters to stay in play.")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/driver-white-transparent.png")
                .build();

        Playbook gunlugger = Playbook.builder()
                .playbookType(PlaybookType.GUNLUGGER)
                .barterInstructions("At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
                        "\n" +
                        "If you need jingle during a session, tell the MC you’d like to work a gig. Your gigs:\n" +
                        "\n" +
                        "- *Extort, raid, or rob a wealthy population*\n" +
                        "- *Execute a murder on behalf of a wealthy NPC*\n" +
                        "- *Serve a wealthy NPC as a bodyguard*\n" +
                        "- *Others, as you negotiate them*\n" +
                        "\n" +
                        "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                        "\n" +
                        "- *a night in high luxury & company*\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *repair of a piece of hi-tech gear*\n" +
                        "- *a session’s hire of a violent individual as bodyguard*\n" +
                        "- *a few sessions’ tribute to a warlord; a few sessions’ maintenance and repairs for a hi-performance vehicle well-used*\n" +
                        "- *bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                        "\n" +
                        "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.")
                .intro("Apocalypse World is a mean, ugly, violent place. Law and society have broken down completely. What’s yours is yours only while you can hold it in your hands. There’s no peace. There’s no stability but what you carve, inch by inch, out of the concrete and dirt, and then defend with murder and blood.\n" +
                        "\n" +
                        "Sometimes the obvious move is the right one.")
                .introComment("Gunluggers are the baddest asses. Their moves are simple, direct and violent. Crude, even. If you want to take no shit, play a gunlugger. Warning: like angels, if things are going well, you might be kicking your heels. Interesting relationships can keep you in the scene.")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/gunlugger-white-transparent.png")
                .build();

        Playbook hardholder = Playbook.builder()
                .playbookType(PlaybookType.HARDHOLDER)
                .barterInstructions("Your holding provides for your day-to-day living, so while you’re there governing it, you need not spend barter for your lifestyle at the beginning of the session.\n" +
                        "\n" +
                        "When you give gifts, here’s what might count as a gift worth 1-barter:\n" +
                        "\n" +
                        "- *a month’s hospitality, including a place to live and meals in common with others*\n" +
                        "- *a night in high luxury & company*\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *repair of a piece of hi-tech gear by your fave savvyhead or techso*\n" +
                        "- *a week’s bestowal of the protective companionship of one of your battlebabes, gunluggers, or gang members*\n" +
                        "- *a month’s maintenance and repairs for a hi-performance vehicle well-used; a half-hour’s worth of your undivided attention, in private audience*\n" +
                        "- *or, of course, oddments worth 1-barter*\n" +
                        "  \n" +
                        "In times of abundance, your holding’s surplus is yours to spend personally as you see fit. (Suppose that your citizen’s lives are the more abundant too, in proportion.) You can see what 1-barter is worth, from the above. For better stuff, be prepared to make unique arrangements, probably by treating with another hardholder nearby.")
                .intro("There is no government, no society, in Apocalypse World. When hardholders ruled whole continents, when they waged war on the other side of the world instead of with the hold across the burn-flat, when their armies numbered in the hundreds of thousands and they had fucking _boats_ to hold their fucking _airplanes_ on, that was the golden age of legend. Now, anyone with a concrete compound and a gang of gunluggers can claim the title. You, you got something to say about it?")
                .introComment("Hardholders are landlords, warlords, governors of their own little strongholds. If anybody plays a hardholder, the game’s going to have a serious and immobile home base. If you want to be the one who owns it, it better be you. Warning: don’t be a hardholder unless you want the burdens.")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/hardholder-white-transparent.png")
                .build();

        Playbook hocus = Playbook.builder()
                .playbookType(PlaybookType.HOCUS)
                .barterInstructions("At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
                        "\n" +
                        "If you need jingle during a session, tell the MC you’d like to work a gig. Your gigs:\n" +
                        "\n" +
                        "- *Serve a wealthy NPC as auger and advisor*\n" +
                        "- *Serve a population as counselor and ceremonist*\n" +
                        "- *Serve a wealthy NPC as ceremonist*\n" +
                        "- *Others, as you negotiate them*\n" +
                        "\n" +
                        "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                        "\n" +
                        "- *a night in high luxury & company*\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *repair of a piece of hi-tech gear*\n" +
                        "- *a session’s hire of a violent individual as bodyguard*\n" +
                        "- *a few sessions’ tribute to a warlord; a few sessions’ maintenance and repairs for a hi-performance vehicle well-used*\n" +
                        "- *bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                        "\n" +
                        "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.")
                .intro("Now it should be crystal fucking obvious that the gods have abandoned Apocalypse World. Maybe in the golden age, with its one nation under god and its in god we trust, maybe then the gods were real. Fucked if I know. All I know is that now they’re gone daddy gone.\n" +
                        "\n" +
                        "My theory is that these weird hocus fuckers, when they say “the gods,” what they really mean is the miasma left over from the explosion of psychic hate and desperation that gave Apocalypse World its birth. Friends, that’s our creator now.")
                .introComment("Hocuses have cult followers the way choppers have gangs. They’re strange, social, public and compelling. If you want to sway mobs, play a hocus. Warning: things are going to come looking for you. Being a cult leader means having to deal with your fucking cult.")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/hocus-white-transparent.png")
                .build();

        Playbook maestroD = Playbook.builder()
                .playbookType(PlaybookType.MAESTRO_D)
                .barterInstructions("Your establishment provides for your day-to-day living, so while you’re open for business, you need not spend barter for your lifestyle at the beginning of the session.\n" +
                        "\n" +
                        "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                        "\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *a session’s hire of a violent individual as bodyguard*\n" +
                        "- *the material costs for crash resuscitation by a medic*\n" +
                        "- *a few sessions’ tribute to a warlord; bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                        "\n" +
                        "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.")
                .intro("In the golden age of legend, there was this guy named Maestro. He was known for dressing up real dap and wherever he went, the people had much luxe tune. There was this other guy named Maitre d’. He was known for dressing up real dap and wherever he went, the people had all the food they could eat and the fanciest of it.\n" +
                        "\n" +
                        "Here in Apocalypse World, those two guys are dead. They died and the fat sizzled off them, they died same as much-luxe-tune and all-you-can-eat. The maestro d’ now, he can’t give you what those guys used to could, but fuck it, maybe he can find you a little somethin somethin to take off the edge.")
                .introComment("The maestro d’ runs a social establishment, like a bar, a drug den or a bordello. If you want to be sexier than a hardholder, with fewer obligations and less shit to deal with, play a maestro d’. Warning: fewer obligations and less shit, not none and none.")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/maestrod-white-transparent.png")
                .build();

        Playbook savvyhead = Playbook.builder()
                .playbookType(PlaybookType.SAVVYHEAD)
                .barterInstructions("At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
                        "\n" +
                        "If you need jingle during a session, tell the MC you’d like to work a gig. Your gigs:\n" +
                        "\n" +
                        "- *Maintain a wealthy NPC’s �nicky or fragile tech*\n" +
                        "- *Repair a wealthy NPC’s hi-tech equipment*\n" +
                        "- *Conduct research for a wealthy NPC*\n" +
                        "- *Others, as you negotiate them*\n" +
                        "\n" +
                        "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                        "\n" +
                        "- *a night in high luxury & company*\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *repair of a piece of hi-tech gear*\n" +
                        "- *a session’s hire of a violent individual as bodyguard*\n" +
                        "- *a few sessions’ tribute to a warlord; a few sessions’ maintenance and repairs for a hi-performance vehicle well-used*\n" +
                        "- *bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                        "\n" +
                        "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.")
                .intro("If there’s one fucking thing you can count on in Apocalypse World, it’s: things break.")
                .introComment("Savvyheads are techies. They have really cool abilities in the form of their workspace, and a couple of fun reality-bending moves. Play a savvyhead if you want to be powerful and useful as an ally, but maybe not the leader yourself. Warning: your workspace depends on resources, and lots of them, so make friends with everyone you can.")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/savvyhead-white-transparent.png")
                .build();

        Playbook skinner = Playbook.builder()
                .playbookType(PlaybookType.SKINNER)
                .barterInstructions("At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
                        "\n" +
                        "If you need jingle during a session, tell the MC you’d like to work a gig. Your gigs:\n" +
                        "\n" +
                        "- *Perform for a public audience*\n" +
                        "- *Appear at the side of a wealthy NPC*\n" +
                        "- *Perform for a private audience*\n" +
                        "- *Others, as you negotiate them*\n" +
                        "\n" +
                        "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                        "\n" +
                        "- *a night in high luxury & company*\n" +
                        "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                        "- *repair of a piece of hi-tech gear*\n" +
                        "- *a session’s hire of a violent individual as bodyguard*\n" +
                        "- *a few sessions’ tribute to a warlord; a few sessions’ maintenance and repairs for a hi-performance vehicle well-used*\n" +
                        "- *bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                        "\n" +
                        "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.")
                .intro("Even in the filth of Apocalypse World, there’s food that isn’t death on a spit, music that isn’t shrieking hyenas, thoughts that aren’t afraid, bodies that aren’t used meat, sex that isn’t rutting, dancing that’s real. There are moments that are more than stench, smoke, rage and blood.\n" +
                        "\n" +
                        "Anything beautiful left in this ugly ass world, skinners hold it. Will they share it with you? What do you offer _them_?")
                .introComment("Skinners are pure hot. They’re entirely social and they have great, directly manipulative moves. Play a skinner if you want to be unignorable. Warning: skinners have the tools, but unlike hardholders, choppers and hocuses, they don’t have a steady influx of motivation. You’ll have most fun if you can roll your own.\n")
                .playbookImageUrl("https://awc-images.s3-ap-southeast-2.amazonaws.com/skinner-white-transparent.png")
                .build();

        playbookService.saveAll(List.of(angel, battlebabe, brainer, chopper, driver, gunlugger, hardholder,
                maestroD, hocus, savvyhead, skinner));
    }

    private void createPlaybooks() {
        fleshOutPlaybookAndSave(PlaybookType.ANGEL);
        fleshOutPlaybookAndSave(PlaybookType.BATTLEBABE);
        fleshOutPlaybookAndSave(PlaybookType.BRAINER);
        fleshOutPlaybookAndSave(PlaybookType.CHOPPER);
        fleshOutPlaybookAndSave(PlaybookType.DRIVER);
        fleshOutPlaybookAndSave(PlaybookType.GUNLUGGER);
        fleshOutPlaybookAndSave(PlaybookType.HARDHOLDER);
        fleshOutPlaybookAndSave(PlaybookType.HOCUS);
        fleshOutPlaybookAndSave(PlaybookType.MAESTRO_D);
        fleshOutPlaybookAndSave(PlaybookType.SAVVYHEAD);
        fleshOutPlaybookAndSave(PlaybookType.SKINNER);
    }

    private void loadThreatCreator() {

        String createThreatInstructions = "To create a threat:\n" +
                "\n" +
                "- Choose its kind, name it, and copy over its impulse. Describe it and list its cast.\n" +
                "- Place it on the threat map. If it's in motion, mark its direction with an arrow.\n" +
                "- List its stakes question(s).\n" +
                "- if it's connected to other threats, list them.\n" +
                "- If it calls for a custom move or a countdown, create it.";

        String essentialThreatInstructions = "- Where the PCs are, create as landscape.\n" +
                "- For any PC's gang, create a brutes.\n" +
                "- For any PC's other NPCs, create as brutes, plus a grotesque and/or a wannabe warlord.\n" +
                "- For any PCs' vehicles, create as vehicles.\n" +
                "- In any local population, create an affliction.";

        ThreatCreatorContent warlord = ThreatCreatorContent.builder()
                .id(new ObjectId().toString())
                .threatType(ThreatType.WARLORD)
                .impulses(List.of(
                        "Slaver (to own or sell people)",
                        "Hive queen (to consume and swarm)",
                        "Prophet (to denounce and overthrow",
                        "Dictator (to control)",
                        "Collector (to own)",
                        "Alpha wolf (to hunt and dominate"))
                .moves(List.of(
                        "Push the battle moves.",
                        "Outflank someone, corner someone, encircle someone.",
                        "Attack someone suddenly, directly, and very hard.",
                        "Attack someone cautiously, holding reserves.",
                        "Seize someone or something, for leverage or information.",
                        "Make a show of force.",
                        "Make a show of discipline.",
                        "Offer to negotiate. Demand concession or obedience.",
                        "Claim territory: move into it, blockade it, assault it.",
                        "Buy out someone's allies.",
                        "Make a careful study of someone and attack where they're weak."
                ))
                .build();

        ThreatCreatorContent grotesque = ThreatCreatorContent.builder()
                .id(new ObjectId().toString())
                .threatType(ThreatType.GROTESQUE)
                .impulses(List.of(
                        "Cannibal (craves satiety and plenty)",
                        "Mutant (craves restitution, recompense)",
                        "Pain addict (craves pain, its own or others)",
                        "Disease vector (craves contact, intimate and/or anonymous)",
                        "Mindfucker (craves mastery)",
                        "Perversion of birth (craves overthrow, chaos, the ruination of all)"
                ))
                .moves(List.of(
                        "Push reading a person.",
                        "Display the nature of the world to its inhabitants.",
                        "Display the contents of its heart.",
                        "Attack someone from behind or otherwise by stealth.",
                        "Attack someone face-on, but without threat or warning.",
                        "Insult, Affront, offend or provoke someone.",
                        "Offer something to someone, or do something for someone, with strings attached.",
                        "Put it in someone's path, part of someone's day or life.",
                        "Threaten someone, directly or else by implication.",
                        "Steal something from someone.",
                        "Seize and hold someone.",
                        "Ruin something. Befoul rot, desecrate, corrupt, adulter it."
                ))
                .build();

        ThreatCreatorContent brute = ThreatCreatorContent.builder()
                .id(new ObjectId().toString())
                .threatType(ThreatType.BRUTE)
                .impulses(List.of(
                        "Hunting pack (to victimize anyone vulnerable)",
                        "Sybarites (to consume someone's resources)",
                        "Enforcers (to victimize anyone who stands out)",
                        "Cult (to victimize & incorporate people)",
                        "Mob (to riot, burn, kill scapegoats)",
                        "Family (to close ranks, protect their own)"
                ))
                .moves(List.of(
                        "Push reading a situation.",
                        "Burst out in uncoordinated, undirected violence.",
                        "Make a coordinated attack with a coherent objective.",
                        "Tell stories (truth, lies, allegories, homilies)",
                        "Demand consideration or indulgence.",
                        "Rigidly follow or defy authority.",
                        "Cling to or defy reason.",
                        "Make a show of solidarity and power.",
                        "Ask for help or for someone's participation."
                ))
                .build();

        ThreatCreatorContent affliction = ThreatCreatorContent.builder()
                .id(new ObjectId().toString())
                .threatType(ThreatType.AFFLICTION)
                .impulses(List.of(
                        "Disease (to saturate a population)",
                        "Condition (to expose people to danger)",
                        "Custom (to promote and justify violence)",
                        "Delusion (to dominate people's choices and actions)",
                        "Sacrifice (to leave people bereft)",
                        "Barrier (to impoverish people)"
                ))
                .moves(List.of(
                        "Push reading a situation.",
                        "Someone neglects duties, responsibilities, obligations.",
                        "Someone flies into a rage.",
                        "Someone takes self-destructive, fruitless or hopeless action.",
                        "Someone approaches, seeking help.",
                        "Someone approaches, seeking comfort.",
                        "Someone proclaims the affliction to be a just punishment.",
                        "Someone proclaims the affliction to be, in fact, a blessing.",
                        "Someone refuses or fails to adapt to new circumstances.",
                        "Someone brings friends or loved ones along."
                ))
                .build();

        ThreatCreatorContent landscape = ThreatCreatorContent.builder()
                .id(new ObjectId().toString())
                .threatType(ThreatType.LANDSCAPE)
                .impulses(List.of(
                        "Prison (to contain, to deny egress)",
                        "Breeding pit (to generate badness)",
                        "Furnace (to consume things)",
                        "Mirage (to entice and betray people)",
                        "Maze (to trap, to frustrate passage)",
                        "Fortress (to deny access)"
                ))
                .moves(List.of(
                        "Push terrain.",
                        "Reveal something to someone.",
                        "Display something for all to see.",
                        "Hide something.",
                        "Bar the way.",
                        "Open the way.",
                        "Provide another way.",
                        "Shift, move, rearrange.",
                        "Offer a guide.",
                        "Present a guardian.",
                        "Disgorge something.",
                        "take something away; lost, used up, destroyed."
                ))
                .build();

        ThreatCreatorContent terrain = ThreatCreatorContent.builder()
                .id(new ObjectId().toString())
                .threatType(ThreatType.TERRAIN)
                .impulses(List.of(
                        "A precipice (to send someone over)",
                        "A wall (to bring someone up short)",
                        "An overhang (to bring danger down upon someone)",
                        "An exposed place (to expose someone to danger)",
                        "Shifting ground (to cost someone their bearing)",
                        "Broken ground (to break what crosses)"
                ))
                .moves(List.of(
                        "Push dealing with bad terrain.",
                        "Inflict harm (1-harm or v-harm).",
                        "Stall someone.",
                        "Isolate someone.",
                        "Bring someone somewhere.",
                        "Hide evidence.",
                        "Give someone a vantage point.",
                        "Give someone a secure position."
                ))
                .build();

        ThreatCreatorContent vehicle = ThreatCreatorContent.builder()
                .id(new ObjectId().toString())
                .threatType(ThreatType.VEHICLE)
                .impulses(List.of(
                        "Relentless bitch (to keep moving)",
                        "Cagey bastard (to protect what it carries)",
                        "Wild devil (to defy danger)",
                        "Vicious beast (to kill and destroy)",
                        "Bold fucker (to dominate the road)"
                ))
                .moves(List.of(
                        "Leap off the road.",
                        "Swerve across the road.",
                        "Smash into an obstacle.",
                        "Smash through an obstacle.",
                        "Veer stupidly into danger.",
                        "Tear past.",
                        "Turn too early or too late.",
                        "Shoulder another vehicle aggressively.",
                        "Ram another vehicle from behind.",
                        "T-bone another vehicle.",
                        "Brake abruptly."
                ))
                .build();

        List<String> threatNames = List.of("Tum Tum", "Gnarly", "Fleece", "White", "Lala", "Bill",
                "Crine", "Mercer", "Preen", "Ik", " Shan", "Isle", "Ula", "Joe's Girl", "Dremmer",
                "Balls", "Amy", "Rufe", "Jackabacka", "Ba", "Mice", "Dog head", "Hugo", "Roark",
                "Monk", "Pierre", "Norvell", "H", "Omie Wise", "Corbett", "Jeanette", "Rum", "Peppering",
                "Brain", "Matilda", "Rothschild", "Wisher", "Partridge", "Brace Win", "Bar", "Krin",
                "Parcher", "Millions", "Grome", "Foster", "Mill", "Dustwich", "Newton", "Tao", "Missed",
                "III", "Princy", "East Harrow", "Kettle", "Putrid", "Last", "Twice", "Clarion", "Abondo",
                "Mimi", "Fianelly", "Pellet", "Li", "Harridan", "Rice", "Do", "Winkle", "Fuse", "Visage");
        ThreatCreator threatCreator = ThreatCreator.builder()
                .createThreatInstructions(createThreatInstructions)
                .essentialThreatInstructions(essentialThreatInstructions)
                .threats(List.of(warlord, grotesque, brute, affliction, landscape, terrain, vehicle))
                .threatNames(threatNames)
                .build();

        threatCreatorService.save(threatCreator);
    }

    private void fleshOutPlaybookAndSave(PlaybookType playbookType) {
        Playbook playbook = playbookService.findByPlaybookType(playbookType);
        assert playbook != null;
        if (playbook.getCreator() == null) {
            PlaybookCreator playbookCreator = playbookCreatorService.findByPlaybookType(playbookType);
            assert playbookCreator != null;

            List<Name> names = nameService.findAllByPlaybookType(playbookType);
            assert names != null;

            List<Look> looks = lookService.findAllByPlaybookType(playbookType);
            assert looks != null;

            List<StatsOption> statsOptions = statsOptionService.findAllByPlaybookType(playbookType);
            assert statsOptions != null;

            statsOptions.forEach(statsOption -> playbookCreator.getStatsOptions().add(statsOption));
            names.forEach(name -> playbookCreator.getNames().add(name));
            looks.forEach(look -> playbookCreator.getLooks().add(look));
            playbookCreatorService.save(playbookCreator);
            playbook.setCreator(playbookCreator);
            playbookService.save(playbook);
        }
    }

}
