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
import static com.mersiades.awccontent.content.LooksContent.*;
import static com.mersiades.awccontent.content.MovesContent.sufferVHarm;
import static com.mersiades.awccontent.content.MovesContent.*;
import static com.mersiades.awccontent.content.NamesContent.*;

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

        nameService.saveAll(List.of(
                nameAngel1,
                nameAngel2,
                nameAngel3,
                nameAngel4,
                nameAngel5,
                nameAngel6,
                nameAngel7,
                nameAngel8,
                nameAngel9,
                nameAngel10,
                nameAngel11,
                nameAngel12,
                nameAngel13,
                nameAngel14,
                nameAngel15,
                nameAngel16,
                nameAngel17,
                nameAngel18,
                nameAngel19,
                nameAngel20,
                nameAngel21,
                nameAngel22,
                nameAngel23,
                nameAngel24,
                nameAngel25,
                nameAngel26,
                nameAngel27,
                nameAngel28,
                nameBattlebabe1,
                nameBattlebabe2,
                nameBattlebabe3,
                nameBattlebabe4,
                nameBattlebabe5,
                nameBattlebabe6,
                nameBattlebabe7,
                nameBattlebabe8,
                nameBattlebabe9,
                nameBattlebabe10,
                nameBattlebabe11,
                nameBattlebabe12,
                nameBattlebabe13,
                nameBattlebabe14,
                nameBattlebabe15,
                nameBattlebabe16,
                nameBattlebabe17,
                nameBattlebabe18,
                nameBattlebabe19,
                nameBattlebabe20,
                nameBattlebabe21,
                nameBattlebabe22,
                nameBattlebabe23,
                nameBattlebabe24,
                nameBattlebabe25,
                nameBrainer1,
                nameBrainer2,
                nameBrainer3,
                nameBrainer4,
                nameBrainer5,
                nameBrainer6,
                nameBrainer7,
                nameBrainer8,
                nameBrainer9,
                nameBrainer10,
                nameBrainer11,
                nameBrainer12,
                nameBrainer13,
                nameBrainer14,
                nameBrainer15,
                nameBrainer16,
                nameBrainer17,
                nameBrainer18,
                nameBrainer19,
                nameChopper1,
                nameChopper2,
                nameChopper3,
                nameChopper4,
                nameChopper5,
                nameChopper6,
                nameChopper7,
                nameChopper8,
                nameChopper9,
                nameChopper10,
                nameChopper11,
                nameChopper12,
                nameChopper13,
                nameChopper14,
                nameChopper15,
                nameChopper16,
                nameChopper17,
                nameChopper18,
                nameChopper19,
                nameChopper20,
                nameChopper21,
                nameChopper22,
                nameChopper23,
                nameDriver1,
                nameDriver2,
                nameDriver3,
                nameDriver4,
                nameDriver5,
                nameDriver6,
                nameDriver7,
                nameDriver8,
                nameDriver9,
                nameDriver10,
                nameDriver11,
                nameDriver12,
                nameDriver13,
                nameDriver14,
                nameDriver15,
                nameDriver16,
                nameDriver17,
                nameDriver18,
                nameDriver19,
                nameDriver20,
                nameDriver21,
                nameDriver22,
                nameDriver23,
                nameDriver24,
                nameDriver25,
                nameDriver26,
                nameDriver27,
                nameGunlugger1,
                nameGunlugger2,
                nameGunlugger3,
                nameGunlugger4,
                nameGunlugger5,
                nameGunlugger6,
                nameGunlugger7,
                nameGunlugger8,
                nameGunlugger9,
                nameGunlugger10,
                nameGunlugger11,
                nameGunlugger12,
                nameGunlugger13,
                nameGunlugger14,
                nameGunlugger15,
                nameGunlugger16,
                nameGunlugger17,
                nameGunlugger18,
                nameGunlugger19,
                nameGunlugger20,
                nameGunlugger21,
                nameGunlugger22,
                nameGunlugger23,
                nameGunlugger24,
                nameGunlugger25,
                nameGunlugger26,
                nameGunlugger27,
                nameGunlugger28,
                nameGunlugger29,
                nameHardholder1,
                nameHardholder2,
                nameHardholder3,
                nameHardholder4,
                nameHardholder5,
                nameHardholder6,
                nameHardholder7,
                nameHardholder8,
                nameHardholder9,
                nameHardholder10,
                nameHardholder11,
                nameHardholder12,
                nameHardholder13,
                nameHardholder14,
                nameHardholder15,
                nameHardholder16,
                nameHardholder17,
                nameHardholder18,
                nameHardholder19,
                nameHocus,
                nameHocus1,
                nameHocus2,
                nameHocus3,
                nameHocus4,
                nameHocus5,
                nameHocus6,
                nameHocus7,
                nameHocus8,
                nameHocus9,
                nameHocus10,
                nameHocus11,
                nameHocus12,
                nameHocus13,
                nameHocus14,
                nameHocus15,
                nameHocus16,
                nameHocus17,
                nameHocus18,
                nameHocus19,
                nameHocus20,
                nameHocus21,
                nameMaestroD1,
                nameMaestroD2,
                nameMaestroD3,
                nameMaestroD4,
                nameMaestroD5,
                nameMaestroD6,
                nameMaestroD7,
                nameMaestroD8,
                nameMaestroD9,
                nameMaestroD10,
                nameMaestroD11,
                nameMaestroD12,
                nameMaestroD13,
                nameMaestroD14,
                nameMaestroD15,
                nameMaestroD16,
                nameMaestroD17,
                nameMaestroD18,
                nameMaestroD19,
                nameMaestroD20,
                nameMaestroD21,
                nameMaestroD22,
                nameMaestroD23,
                nameMaestroD24,
                nameSavvyhead1,
                nameSavvyhead2,
                nameSavvyhead3,
                nameSavvyhead4,
                nameSavvyhead5,
                nameSavvyhead6,
                nameSavvyhead7,
                nameSavvyhead8,
                nameSavvyhead9,
                nameSavvyhead10,
                nameSavvyhead11,
                nameSavvyhead12,
                nameSavvyhead13,
                nameSavvyhead14,
                nameSavvyhead15,
                nameSavvyhead16,
                nameSavvyhead17,
                nameSavvyhead18,
                nameSavvyhead19,
                nameSavvyhead20,
                nameSavvyhead21,
                nameSavvyhead22,
                nameSavvyhead23,
                nameSavvyhead24,
                nameSavvyhead25,
                nameSavvyhead26,
                nameSavvyhead27,
                nameSavvyhead28,
                nameSavvyhead29,
                nameSavvyhead30,
                nameSavvyhead31,
                nameSavvyhead32,
                nameSavvyhead33,
                nameSkinner1,
                nameSkinner2,
                nameSkinner3,
                nameSkinner4,
                nameSkinner5,
                nameSkinner6,
                nameSkinner7,
                nameSkinner8,
                nameSkinner9,
                nameSkinner10,
                nameSkinner11,
                nameSkinner12,
                nameSkinner13,
                nameSkinner14,
                nameSkinner15,
                nameSkinner16,
                nameSkinner17,
                nameSkinner18,
                nameSkinner19,
                nameSkinner20,
                nameSkinner21,
                nameSkinner22,
                nameSkinner23,
                nameSkinner24
        ));
    }

    private void loadLooks() {
        System.out.println("|| --- Loading playbook looks --- ||");
        lookService.saveAll(List.of(
                lookAngel1,
                lookAngel2,
                lookAngel3,
                lookAngel4,
                lookAngel5,
                lookAngel6,
                lookAngel7,
                lookAngel8,
                lookAngel9,
                lookAngel10,
                lookAngel11,
                lookAngel12,
                lookAngel13,
                lookAngel14,
                lookAngel15,
                lookAngel16,
                lookAngel17,
                lookAngel18,
                lookAngel19,
                lookAngel20,
                lookAngel21,
                lookAngel22,
                lookAngel23,
                lookAngel24,
                lookAngel25,
                lookAngel26,
                lookBattlebabe1,
                lookBattlebabe2,
                lookBattlebabe3,
                lookBattlebabe4,
                lookBattlebabe5,
                lookBattlebabe6,
                lookBattlebabe7,
                lookBattlebabe8,
                lookBattlebabe9,
                lookBattlebabe10,
                lookBattlebabe11,
                lookBattlebabe12,
                lookBattlebabe13,
                lookBattlebabe14,
                lookBattlebabe15,
                lookBattlebabe16,
                lookBattlebabe17,
                lookBattlebabe18,
                lookBattlebabe19,
                lookBattlebabe20,
                lookBattlebabe21,
                lookBattlebabe22,
                lookBattlebabe23,
                lookBattlebabe24,
                lookBattlebabe25,
                lookBattlebabe26,
                lookBrainer1,
                lookBrainer2,
                lookBrainer3,
                lookBrainer4,
                lookBrainer5,
                lookBrainer6,
                lookBrainer7,
                lookBrainer8,
                lookBrainer9,
                lookBrainer10,
                lookBrainer11,
                lookBrainer12,
                lookBrainer13,
                lookBrainer14,
                lookBrainer15,
                lookBrainer16,
                lookBrainer17,
                lookBrainer18,
                lookBrainer19,
                lookBrainer20,
                lookBrainer21,
                lookBrainer22,
                lookBrainer23,
                lookBrainer24,
                lookBrainer25,
                lookBrainer26,
                lookBrainer27,
                lookChopper1,
                lookChopper2,
                lookChopper3,
                lookChopper4,
                lookChopper5,
                lookChopper6,
                lookChopper7,
                lookChopper8,
                lookChopper9,
                lookChopper10,
                lookChopper11,
                lookChopper12,
                lookChopper13,
                lookChopper14,
                lookChopper15,
                lookChopper16,
                lookChopper17,
                lookChopper18,
                lookChopper19,
                lookChopper20,
                lookChopper21,
                lookChopper22,
                lookChopper23,
                lookDriver1,
                lookDriver2,
                lookDriver3,
                lookDriver4,
                lookDriver5,
                lookDriver6,
                lookDriver7,
                lookDriver8,
                lookDriver9,
                lookDriver10,
                lookDriver11,
                lookDriver12,
                lookDriver13,
                lookDriver14,
                lookDriver15,
                lookDriver16,
                lookDriver17,
                lookDriver18,
                lookDriver19,
                lookDriver20,
                lookDriver21,
                lookDriver22,
                lookDriver23,
                lookDriver24,
                lookDriver25,
                lookDriver26,
                lookDriver27,
                lookGunlugger1,
                lookGunlugger2,
                lookGunlugger3,
                lookGunlugger4,
                lookGunlugger5,
                lookGunlugger6,
                lookGunlugger7,
                lookGunlugger8,
                lookGunlugger9,
                lookGunlugger10,
                lookGunlugger11,
                lookGunlugger12,
                lookGunlugger13,
                lookGunlugger14,
                lookGunlugger15,
                lookGunlugger16,
                lookGunlugger17,
                lookGunlugger18,
                lookGunlugger19,
                lookGunlugger20,
                lookGunlugger21,
                lookGunlugger22,
                lookGunlugger23,
                lookGunlugger24,
                lookGunlugger25,
                lookGunlugger26,
                lookHardHolder1,
                lookHardHolder2,
                lookHardHolder3,
                lookHardHolder4,
                lookHardHolder5,
                lookHardHolder6,
                lookHardHolder7,
                lookHardHolder8,
                lookHardHolder9,
                lookHardHolder10,
                lookHardHolder11,
                lookHardHolder12,
                lookHardHolder13,
                lookHardHolder14,
                lookHardHolder15,
                lookHardHolder16,
                lookHardHolder17,
                lookHardHolder18,
                lookHardHolder19,
                lookHardHolder20,
                lookHardHolder21,
                lookHardHolder22,
                lookHardHolder23,
                lookHardHolder24,
                lookHardHolder25,
                lookHardHolder26,
                lookHardHolder27,
                lookHocus1,
                lookHocus2,
                lookHocus3,
                lookHocus4,
                lookHocus5,
                lookHocus6,
                lookHocus7,
                lookHocus8,
                lookHocus9,
                lookHocus10,
                lookHocus11,
                lookHocus12,
                lookHocus13,
                lookHocus14,
                lookHocus15,
                lookHocus16,
                lookHocus17,
                lookHocus18,
                lookHocus19,
                lookHocus20,
                lookHocus21,
                lookHocus22,
                lookHocus23,
                lookHocus24,
                lookHocus25,
                lookHocus26,
                lookHocus27,
                lookHocus28,
                lookMaestroD1,
                lookMaestroD2,
                lookMaestroD3,
                lookMaestroD4,
                lookMaestroD5,
                lookMaestroD6,
                lookMaestroD7,
                lookMaestroD8,
                lookMaestroD9,
                lookMaestroD10,
                lookMaestroD11,
                lookMaestroD12,
                lookMaestroD13,
                lookMaestroD14,
                lookMaestroD15,
                lookMaestroD16,
                lookMaestroD17,
                lookMaestroD18,
                lookMaestroD19,
                lookMaestroD20,
                lookMaestroD21,
                lookMaestroD22,
                lookMaestroD23,
                lookMaestroD24,
                lookMaestroD25,
                lookMaestroD26,
                lookMaestroD27,
                lookMaestroD28,
                lookMaestroD29,
                lookSavvyhead1,
                lookSavvyhead2,
                lookSavvyhead3,
                lookSavvyhead4,
                lookSavvyhead5,
                lookSavvyhead6,
                lookSavvyhead7,
                lookSavvyhead8,
                lookSavvyhead9,
                lookSavvyhead10,
                lookSavvyhead11,
                lookSavvyhead12,
                lookSavvyhead13,
                lookSavvyhead14,
                lookSavvyhead15,
                lookSavvyhead16,
                lookSavvyhead17,
                lookSavvyhead18,
                lookSavvyhead19,
                lookSavvyhead20,
                lookSavvyhead21,
                lookSavvyhead22,
                lookSavvyhead23,
                lookSkinner1,
                lookSkinner2,
                lookSkinner3,
                lookSkinner4,
                lookSkinner5,
                lookSkinner6,
                lookSkinner7,
                lookSkinner8,
                lookSkinner9,
                lookSkinner10,
                lookSkinner11,
                lookSkinner12,
                lookSkinner13,
                lookSkinner14,
                lookSkinner15,
                lookSkinner16,
                lookSkinner17,
                lookSkinner18,
                lookSkinner19,
                lookSkinner20,
                lookSkinner21,
                lookSkinner22,
                lookSkinner23,
                lookSkinner24,
                lookSkinner25,
                lookSkinner26,
                lookSkinner27,
                lookSkinner28,
                lookSkinner29
        ));


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
