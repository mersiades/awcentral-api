package com.mersiades.awccontent.content;

import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.models.MoveAction;
import com.mersiades.awccontent.models.StatModifier;
import org.bson.types.ObjectId;

import static com.mersiades.awccontent.constants.MoveNames.*;
import static com.mersiades.awccontent.enums.StatType.COOL;

public class MovesContent {

    /* ----------------------------- BASIC MOVES --------------------------------- */
    public static final MoveAction doSomethingUnderFireAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(COOL)
            .build();
    public static final Move doSomethingUnderFire = Move.builder()
            .id(new ObjectId().toString())
            .name(underFireName)
            .description("When you _**do something under fire**_, or dig in to endure fire, roll+cool.\n" +
                    "\n" +
                    "On a 10+, you do it.\n" +
                    "\n" +
                    "On a 7–9, you flinch, hesitate, or stall: the MC can offer you a worse outcome, a hard bargain, or an ugly choice.\n" +
                    "\n" +
                    "On a miss, be prepared for the worst.")
            .kind(MoveType.BASIC)
            .moveAction(doSomethingUnderFireAction)
            .playbook(null)
            .build();

    /* ----------------------------- PERIPHERAL MOVES --------------------------------- */

    /* ----------------------------- BATTLE MOVES --------------------------------- */

    /* ----------------------------- ROAD WAR MOVES --------------------------------- */

    /* ----------------------------- ANGEL MOVES --------------------------------- */

    /* ----------------------------- ANGEL KIT MOVES --------------------------------- */

    /* ----------------------------- BATTLEBABE MOVES --------------------------------- */

    /* ----------------------------- BRAINER MOVES --------------------------------- */

    /* ----------------------------- CHOPPER MOVES --------------------------------- */

    /* ----------------------------- DRIVER MOVES --------------------------------- */

    /* ----------------------------- GUNLUGGER MOVES --------------------------------- */

    /* ----------------------------- HARDHOLDER MOVES --------------------------------- */

    /* ----------------------------- HOCUS MOVES --------------------------------- */

    /* ----------------------------- MAESTRO D' MOVES --------------------------------- */

    /* ----------------------------- SAVVYHEAD MOVES --------------------------------- */

    /* ----------------------------- SKINNER MOVES --------------------------------- */

    /* ----------------------------- GENERIC STAT IMPROVEMENT MOVES --------------------------------- */

    public static final StatModifier sharpMax2Mod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.SHARP)
            .modification(1)
            .maxLimit(2)
            .build();

    public static final Move sharpMax2 = Move.builder()
            .id(new ObjectId().toString())
            .name(sharpMax2Name)
            .description("get +1sharp (max sharp+2)\n")
            .statModifier(sharpMax2Mod)
            .kind(MoveType.IMPROVE_STAT)
            .stat(null)
            .build();
    public static final StatModifier coolMax2Mod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.COOL)
            .modification(1)
            .maxLimit(2)
            .build();

    public static final Move coolMax2 = Move.builder()
            .id(new ObjectId().toString())
            .name(coolMax2Name)
            .description("get +1cool (max sharp+2)\n")
            .statModifier(coolMax2Mod)
            .kind(MoveType.IMPROVE_STAT)
            .stat(null)
            .build();

    public static final StatModifier hardMax2Mod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.HARD)
            .modification(1)
            .maxLimit(2)
            .build();

    public static final Move hardMax2 = Move.builder()
            .id(new ObjectId().toString())
            .name(hardMax2Name)
            .description("get +1hard (max hard+2)\n")
            .statModifier(hardMax2Mod)
            .kind(MoveType.IMPROVE_STAT)
            .stat(null)
            .build();

    public static final StatModifier hotMax2Mod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.HOT)
            .modification(1)
            .maxLimit(2)
            .build();

    public static final Move hotMax2 = Move.builder()
            .id(new ObjectId().toString())
            .name(hotMax2Name)
            .description("get +1hot (max hot+2)\n")
            .statModifier(hotMax2Mod)
            .kind(MoveType.IMPROVE_STAT)
            .stat(null)
            .build();

    public static final StatModifier weirdMax2Mod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.WEIRD)
            .modification(1)
            .maxLimit(2)
            .build();

    public static final Move weirdMax2 = Move.builder()
            .id(new ObjectId().toString())
            .name(weirdMax2Name)
            .description("get +1weird (max weird+2)\n")
            .statModifier(weirdMax2Mod)
            .kind(MoveType.IMPROVE_STAT)
            .stat(null)
            .build();

    public static final StatModifier sharpMax3Mod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.SHARP)
            .modification(1)
            .maxLimit(3)
            .build();

    public static final Move sharpMax3 = Move.builder()
            .id(new ObjectId().toString())
            .name(sharpMax3Name)
            .description("get +1sharp (max sharp+3)\n")
            .statModifier(sharpMax3Mod)
            .kind(MoveType.IMPROVE_STAT)
            .stat(null)
            .build();
    public static final StatModifier coolMax3Mod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.COOL)
            .modification(1)
            .maxLimit(3)
            .build();

    public static final Move coolMax3 = Move.builder()
            .id(new ObjectId().toString())
            .name(coolMax3Name)
            .description("get +1cool (max sharp+3)\n")
            .statModifier(coolMax3Mod)
            .kind(MoveType.IMPROVE_STAT)
            .stat(null)
            .build();

    public static final StatModifier hardMax3Mod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.HARD)
            .modification(1)
            .maxLimit(3)
            .build();

    public static final Move hardMax3 = Move.builder()
            .id(new ObjectId().toString())
            .name(hardMax3Name)
            .description("get +1hard (max hard+3)\n")
            .statModifier(hardMax3Mod)
            .kind(MoveType.IMPROVE_STAT)
            .stat(null)
            .build();

    public static final StatModifier hotMax3Mod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.HOT)
            .modification(1)
            .maxLimit(3)
            .build();

    public static final Move hotMax3 = Move.builder()
            .id(new ObjectId().toString())
            .name(hotMax3Name)
            .description("get +1hot (max hot+3)\n")
            .statModifier(hotMax3Mod)
            .kind(MoveType.IMPROVE_STAT)
            .stat(null)
            .build();

    public static final StatModifier weirdMax3Mod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.WEIRD)
            .modification(1)
            .maxLimit(3)
            .build();

    public static final Move weirdMax3 = Move.builder()
            .id(new ObjectId().toString())
            .name(weirdMax3Name)
            .description("get +1weird (max weird+3)\n")
            .statModifier(weirdMax3Mod)
            .kind(MoveType.IMPROVE_STAT)
            .stat(null)
            .build();

    /* ----------------------------- ADD CHARACTER MOVE IMPROVEMENT MOVES --------------------------------- */

    public static final Move addAngelMove1 = Move.builder()
            .id(new ObjectId().toString())
            .name(addAngelMove1Name)
            .description("get a new angel move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.ANGEL)
            .build();

    public static final Move addAngelMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name(addAngelMove2Name)
            .description("get a new angel move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.ANGEL)
            .build();

    public static final Move addBattleBabeMove1 = Move.builder()
            .id(new ObjectId().toString())
            .name(addBattleBabeMove1Name)
            .description("get a new battlebabe move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.BATTLEBABE)
            .build();

    public static final Move addBattleBabeMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name(addBattleBabeMove2Name)
            .description("get a new battlebabe move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.BATTLEBABE)
            .build();

    public static final Move addBrainerMove1 = Move.builder()
            .id(new ObjectId().toString())
            .name(addBrainerMove1Name)
            .description("get a new brainer move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.BRAINER)
            .build();

    public static final Move addBrainerMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name(addBrainerMove2Name)
            .description("get a new brainer move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.BRAINER)
            .build();

    public static final Move addDriverMove1 = Move.builder()
            .id(new ObjectId().toString())
            .name(addDriverMove1Name)
            .description("get a new driver move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.DRIVER)
            .build();

    public static final Move addDriverMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name(addDriverMove2Name)
            .description("get a new driver move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.DRIVER)
            .build();

    public static final Move addGunluggerMove1 = Move.builder()
            .id(new ObjectId().toString())
            .name(addGunluggerMove1Name)
            .description("get a new gunlugger move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.GUNLUGGER)
            .build();

    public static final Move addGunluggerMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name(addGunluggerMove2Name)
            .description("get a new gunlugger move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.GUNLUGGER)
            .build();

    public static final Move addHocusMove1 = Move.builder()
            .id(new ObjectId().toString())
            .name(addHocusMove1Name)
            .description("get a new hocus move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.HOCUS)
            .build();

    public static final Move addHocusMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name(addHocusMove2Name)
            .description("get a new hocus move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.HOCUS)
            .build();

    public static final Move addMaestroDMove1 = Move.builder()
            .id(new ObjectId().toString())
            .name(addMaestroDMove1Name)
            .description("get a new maestro d' move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.MAESTRO_D)
            .build();

    public static final Move addMaestroDMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name(addMaestroDMove2Name)
            .description("get a new maestro d' move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.MAESTRO_D)
            .build();

    public static final Move addSavvyheadMove1 = Move.builder()
            .id(new ObjectId().toString())
            .name(addSavvyheadMove1Name)
            .description("get a new savvyhead move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.SAVVYHEAD)
            .build();

    public static final Move addSavvyheadMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name(addSavvyheadMove2Name)
            .description("get a new savvyhead move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.SAVVYHEAD)
            .build();

    public static final Move addSkinnerMove1 = Move.builder()
            .id(new ObjectId().toString())
            .name(addSkinnerMove1Name)
            .description("get a new skinner move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.SKINNER)
            .build();

    public static final Move addSkinnerMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name(addSkinnerMove2Name)
            .description("get a new skinner move\n")
            .kind(MoveType.ADD_CHARACTER_MOVE)
            .playbook(PlaybookType.SKINNER)
            .build();

    /* ----------------------------- ADJUST UNIQUE IMPROVEMENT MOVES --------------------------------- */

    public static final Move adjustAngelUnique1 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustAngelUnique1Name)
            .description("get a supplier (_cf_, detail with the MC)\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.ANGEL)
            .build();

    public static final Move adjustBrainerUnique1 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustBrainerUnique1Name)
            .description("get 2 new or replacement brainer gear (you choose)\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.BRAINER)
            .build();

    public static final Move adjustChopperUnique1 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustChopperUnique1Name)
            .description("choose a new option for your gang\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.CHOPPER)
            .build();

    public static final Move adjustChopperUnique2 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustChopperUnique2Name)
            .description("choose a new option for your gang\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.CHOPPER)
            .build();

    public static final Move adjustHardHolderUnique1 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustHardHolderUnique1Name)
            .description("choose a new option for your holding\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.HARDHOLDER)
            .build();

    public static final Move adjustHardHolderUnique2 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustHardHolderUnique2Name)
            .description("choose a new option for your holding\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.HARDHOLDER)
            .build();

    public static final Move adjustHardHolderUnique3 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustHardHolderUnique3Name)
            .description("erase an option from your holding\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.HARDHOLDER)
            .build();

    public static final Move adjustHocusUnique1 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustHocusUnique1Name)
            .description("choose a new option for your followers\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.HOCUS)
            .build();

    public static final Move adjustHocusUnique2 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustHocusUnique2Name)
            .description("choose a new option for your followers\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.HOCUS)
            .build();

    public static final Move adjustMaestroDUnique1 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustMaestroDUnique1Name)
            .description("add a security to your establishment\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.MAESTRO_D)
            .build();

    public static final Move adjustMaestroDUnique2 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustMaestroDUnique2Name)
            .description("resolve somebody's interest in your establishment\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.MAESTRO_D)
            .build();

    public static final Move adjustSavvyheadUnique1 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustSavvyheadUnique1Name)
            .description("add 2 options to your workspace\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.SAVVYHEAD)
            .build();

    public static final Move adjustSavvyheadUnique2 = Move.builder()
            .id(new ObjectId().toString())
            .name(adjustSavvyheadUnique2Name)
            .description("add life support to your workspace, and now you can work on people there too\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.SAVVYHEAD)
            .build();

    /* ----------------------------- ADD OTHER PLAYBOOK MOVE IMPROVEMENT MOVES --------------------------------- */

    public static final Move addOtherPBMove1 = Move.builder()
            .id(new ObjectId().toString())
            .name("ADD MOVE FROM OTHER PLAYBOOK 1")
            .description("get a move from another playbook\n")
            .kind(MoveType.ADD_OTHER_PB_MOVE)
            .build();

    public static final Move addOtherPBMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name("ADD MOVE FROM OTHER PLAYBOOK 2")
            .description("get a move from another playbook\n")
            .kind(MoveType.ADD_OTHER_PB_MOVE)
            .build();

    /* ----------------------------- ADD UNIQUE IMPROVEMENT MOVES --------------------------------- */

    public static final Move addGangLeadership = Move.builder()
            .id(new ObjectId().toString())
            .name("ADD GANG AND LEADERSHIP")
            .description("get a gang (you detail) and _**leadership**_\n")
            .kind(MoveType.ADD_UNIQUE)
            .playbook(PlaybookType.HARDHOLDER)
            .build();

    public static final Move addGangPackAlpha = Move.builder()
            .id(new ObjectId().toString())
            .name("ADD GANG AND LEADERSHIP")
            .description("get a gang (you detail) and _**pack alpha**_\n")
            .kind(MoveType.ADD_UNIQUE)
            .playbook(PlaybookType.CHOPPER)
            .build();

    public static final Move addHolding = Move.builder()
            .id(new ObjectId().toString())
            .name("ADD HOLDING")
            .description("get a holding (you detail) and _**wealth**_\n")
            .kind(MoveType.ADD_UNIQUE)
            .playbook(PlaybookType.HARDHOLDER)
            .build();

    public static final Move addWorkspace = Move.builder()
            .id(new ObjectId().toString())
            .name("ADD WORKSPACE")
            .description("get a garage (workspace, you detail) and crew\n")
            .kind(MoveType.ADD_UNIQUE)
            .playbook(PlaybookType.SAVVYHEAD)
            .build();

    public static final Move addFollowers = Move.builder()
            .id(new ObjectId().toString())
            .name("ADD FOLLOWERS")
            .description("get followers (you detail) and _**fortunes**_\n")
            .kind(MoveType.ADD_UNIQUE)
            .playbook(PlaybookType.HOCUS)
            .build();

    public static final Move addEstablishment = Move.builder()
            .id(new ObjectId().toString())
            .name("ADD ESTABLISHMENT")
            .description("get an establishment (you detail)\n")
            .kind(MoveType.ADD_UNIQUE)
            .playbook(PlaybookType.HOCUS)
            .build();

    /* ----------------------------- ADD VEHICLE IMPROVEMENT MOVES --------------------------------- */

    public static final Move addVehicle = Move.builder()
            .id(new ObjectId().toString())
            .name("ADD VEHICLE")
            .description("get a vehicle (you detail)\n")
            .kind(MoveType.ADD_VEHICLE)
            .build();

    /* ----------------------------- UNGIVEN FUTURE IMPROVEMENT MOVES --------------------------------- */

    public static final Move genericIncreaseStat = Move.builder()
            .id(new ObjectId().toString())
            .name("GENERIC INCREASE STAT")
            .description("get +1 to any stat (max stat+3)\n")
            .kind(MoveType.GENERIC_INCREASE_STAT)
            .build();

    public static final Move retire = Move.builder()
            .id(new ObjectId().toString())
            .name("RETIRE")
            .description("retire your character to safety\n")
            .kind(MoveType.RETIRE)
            .build();

    public static final Move addSecondCharacter = Move.builder()
            .id(new ObjectId().toString())
            .name("ADD SECOND CHARACTER")
            .description("create a second character to play\n")
            .kind(MoveType.ADD_SECOND_CHARACTER)
            .build();

    public static final Move changePlaybook = Move.builder()
            .id(new ObjectId().toString())
            .name("CHANGE PLAYBOOK")
            .description("change your character to a new playbook\n")
            .kind(MoveType.CHANGE_PLAYBOOK)
            .build();

    public static final Move improveBasicMoves1 = Move.builder()
            .id(new ObjectId().toString())
            .name("IMPROVE BASIC MOVES 1")
            .description("choose 3 basic moves and advance them\n")
            .kind(MoveType.IMPROVE_BASIC_MOVES)
            .build();

    public static final Move improveBasicMoves2 = Move.builder()
            .id(new ObjectId().toString())
            .name("IMPROVE BASIC MOVES 2")
            .description("advance the other three basic moves\n")
            .kind(MoveType.IMPROVE_BASIC_MOVES)
            .build();
}
