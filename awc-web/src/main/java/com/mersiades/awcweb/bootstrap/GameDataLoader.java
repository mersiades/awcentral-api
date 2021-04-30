package com.mersiades.awcweb.bootstrap;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.repositories.*;
import com.mersiades.awccontent.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mersiades.awccontent.content.LooksContent.*;
import static com.mersiades.awccontent.content.McContentContent.mcContent;
import static com.mersiades.awccontent.content.MovesContent.*;
import static com.mersiades.awccontent.content.NamesContent.*;
import static com.mersiades.awccontent.content.PlaybookCreatorsContent.*;
import static com.mersiades.awccontent.content.PlaybooksContent.*;
import static com.mersiades.awccontent.content.StatOptionsContent.*;
import static com.mersiades.awccontent.content.ThreatsCreatorContent.threatCreator;
import static com.mersiades.awccontent.content.VehicleCreatorContent.vehicleCreator;

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
        statsOptionService.saveAll(List.of(
                statsOptionAngel1,
                statsOptionAngel2,
                statsOptionAngel3,
                statsOptionAngel4,
                statsOptionBattlebabe1,
                statsOptionBattlebabe2,
                statsOptionBattlebabe3,
                statsOptionBattlebabe4,
                statsOptionBrainer1,
                statsOptionBrainer2,
                statsOptionBrainer3,
                statsOptionBrainer4,
                statsOptionChopper1,
                statsOptionChopper2,
                statsOptionChopper3,
                statsOptionChopper4,
                statsOptionDriver1,
                statsOptionDriver2,
                statsOptionDriver3,
                statsOptionDriver4,
                statsOptionGunlugger1,
                statsOptionGunlugger2,
                statsOptionGunlugger3,
                statsOptionGunlugger4,
                statsOptionHardHolder1,
                statsOptionHardHolder2,
                statsOptionHardHolder3,
                statsOptionHardHolder4,
                statsOptionHocus1,
                statsOptionHocus2,
                statsOptionHocus3,
                statsOptionHocus4,
                statsOptionMaestroD1,
                statsOptionMaestroD2,
                statsOptionMaestroD3,
                statsOptionMaestroD4,
                statsOptionSavvyhead1,
                statsOptionSavvyhead2,
                statsOptionSavvyhead3,
                statsOptionSavvyhead4,
                statsOptionSkinner1,
                statsOptionSkinner2,
                statsOptionSkinner3,
                statsOptionSkinner4
        ));
    }

    public void loadPlaybookCreators() {
        System.out.println("|| --- Loading playbook creators --- ||");

        /* ----------------------------- BATTLEBABE PLAYBOOK CREATOR --------------------------------- */


        /* ----------------------------- BRAINER PLAYBOOK CREATOR --------------------------------- */
        List<Move> brainerOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.CHARACTER);

        List<Move> brainerDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.DEFAULT_CHARACTER);

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

        // Driver has no PlaybookUniques; hav Vehicles instead

        List<Move> driverOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.CHARACTER);

        List<Move> driverDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.DEFAULT_CHARACTER);

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

        playbookCreatorService.saveAll(List.of(
                playbookCreatorAngel,
                playbookCreatorBattlebabe,
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
        vehicleCreatorService.save(vehicleCreator);
    }

    public void loadMcContent() {
        mcContentService.save(mcContent);
    }

    public void loadPlaybooks() {
        System.out.println("|| --- Loading playbooks --- ||");

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
