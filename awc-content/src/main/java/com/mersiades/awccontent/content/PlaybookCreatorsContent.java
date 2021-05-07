package com.mersiades.awccontent.content;

import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.models.uniquecreators.*;
import org.bson.types.ObjectId;

import java.util.List;

import static com.mersiades.awccontent.content.MovesContent.*;

public class PlaybookCreatorsContent {

    public static final String IMPROVEMENT_INSTRUCTIONS_FOR_APP = "Each time you improve, choose one of the options. Check it off; you can’t choose it again.";

    public static final String IMPROVEMENT_INSTRUCTIONS = "Whenever you roll a highlighted stat, and whenever you reset your Hx with someone, mark an experience circle. When you mark the 5th, improve and erase.\n" +
            "\n" +
            "Each time you improve, choose one of the options. Check it off; you can’t choose it again.";

    public static final String HX_INSTRUCTIONS_START = "Everyone introduces their characters by name, look and outlook. Take your turn.\n" +
            "\n" +
            "List the other characters’ names.\n" +
            "\n";

    public static final String HX_INSTRUCTIONS_END = "\n" +
            "On the others’ turns, answer their questions as you like.\n" +
            "\n" +
            "At the end, choose one of the characters with the highest Hx on your sheet. Ask that player which of your stats is most interesting, and highlight it. The MC will have you highlight a second stat too.";

    public static final List<Move> futureImprovementMoves = List.of(
            genericIncreaseStat,
            retire,
            addSecondCharacter,
            changePlaybook,
            improveBasicMoves1,
            improveBasicMoves2
    );

    /* ----------------------------- ANGEL PLAYBOOK CREATOR --------------------------------- */

    public static final AngelKitCreator angelKitCreator = AngelKitCreator.builder()
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
    public static final PlaybookUniqueCreator angelUniqueCreator = PlaybookUniqueCreator.builder()
            .id(new ObjectId().toString())
            .type(UniqueType.ANGEL_KIT)
            .angelKitCreator(angelKitCreator)
            .build();
    public static final GearInstructions angelGearInstructions = GearInstructions.builder()
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


    public static final List<Move> improvementMovesAngel = List.of(
            sharpMax3,
            coolMax2,
            hardMax2,
            secondHardMax2,
            weirdMax2,
            addAngelMove1,
            addAngelMove2,
            adjustAngelUnique1,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockAngel = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.ANGEL)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesAngel)
            .build();

    public static final List<Move> angelOptionalMoves = List.of(sixthSense, infirmary, profCompassion, battlefieldGrace, healingTouch, touchedByDeath);

    public static final List<Move> angelDefaultMoves = List.of(angelSpecial);

    public static final PlaybookCreator playbookCreatorAngel = PlaybookCreator.builder()
            .id(new ObjectId().toString())
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
    public static final TaggedItem firearmBase1 = TaggedItem.builder().id(new ObjectId().toString()).description("handgun").tags(List.of("2-harm", "close", "reload", "loud")).build();
    public static final TaggedItem firearmBase2 = TaggedItem.builder().id(new ObjectId().toString()).description("shotgun").tags(List.of("3-harm", "close", "reload", "messy")).build();
    public static final TaggedItem firearmBase3 = TaggedItem.builder().id(new ObjectId().toString()).description("rifle").tags(List.of("2-harm", "far", "reload", "loud")).build();
    public static final TaggedItem firearmBase4 = TaggedItem.builder().id(new ObjectId().toString()).description("crossbow").tags(List.of("2-harm", "close", "slow")).build();

    public static final ItemCharacteristic firearmOption1 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("ornate").tag("+valuable").build();
    public static final ItemCharacteristic firearmOption2 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("antique").tag("+valuable").build();
    public static final ItemCharacteristic firearmOption3 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("semiautomatic").tag("-reload").build();
    public static final ItemCharacteristic firearmOption4 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("3-round burst").tag("+1harm").build();
    public static final ItemCharacteristic firearmOption5 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("automatic").tag("+area").build();
    public static final ItemCharacteristic firearmOption6 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("silenced").tag("-loud").build();
    public static final ItemCharacteristic firearmOption7 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("hi-powered").tag("close/far, or +1harm at far").build();
    public static final ItemCharacteristic firearmOption8 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("ap ammo").tag("+ap").build();
    public static final ItemCharacteristic firearmOption9 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("scoped").tag("+far, or +1harm at far").build();
    public static final ItemCharacteristic firearmOption10 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("big").tag("+1harm").build();

    public static final TaggedItem handBase1 = TaggedItem.builder().id(new ObjectId().toString()).description("staff").tags(List.of("1-harm", "hand", "area")).build();
    public static final TaggedItem handBase2 = TaggedItem.builder().id(new ObjectId().toString()).description("haft").tags(List.of("1-harm", "hand")).build();
    public static final TaggedItem handBase3 = TaggedItem.builder().id(new ObjectId().toString()).description("handle").tags(List.of("1-harm", "hand")).build();
    public static final TaggedItem handBase4 = TaggedItem.builder().id(new ObjectId().toString()).description("chain").tags(List.of("1-harm", "hand", "area")).build();

    public static final ItemCharacteristic handOption1 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("ornate").tag("+valuable").build();
    public static final ItemCharacteristic handOption2 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("antique").tag("+valuable").build();
    public static final ItemCharacteristic handOption3 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("head").tag("+1harm").build();
    public static final ItemCharacteristic handOption4 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("spikes").tag("+1harm").build();
    public static final ItemCharacteristic handOption5 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("blade").tag("+1harm").build();
    public static final ItemCharacteristic handOption6 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("long blade*").tag("+2harm").build();
    public static final ItemCharacteristic handOption7 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("heavy blade*").tag("+2harm").build();
    public static final ItemCharacteristic handOption8 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("blades*").tag("+2harm").build();
    public static final ItemCharacteristic handOption9 = ItemCharacteristic.builder().id(new ObjectId().toString()).description("hidden").tag("+infinite").build();

    public static final CustomWeaponsCreator customWeaponsCreator = CustomWeaponsCreator.builder()
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

    public static final PlaybookUniqueCreator battlebabeUniqueCreator = PlaybookUniqueCreator.builder()
            .id(new ObjectId().toString())
            .type(UniqueType.CUSTOM_WEAPONS)
            .customWeaponsCreator(customWeaponsCreator)
            .build();

    public static final GearInstructions battlebabeGearInstructions = GearInstructions.builder()
            .id(new ObjectId().toString())
            .gearIntro("You get:")
            .youGetItems(List.of("fashion suitable to your look, including at your option fashion worth 1-armor or body armor worth 2-armor (you detail)"))
            .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
            .startingBarter(4)
            .build();

    public static final List<Move> improvementMovesBattlebabe = List.of(
            hardMax2,
            hotMax2,
            sharpMax2,
            weirdMax2,
            addBattleBabeMove1,
            addBattleBabeMove2,
            // TODO: Add move for getting an ally
            addGangLeadership,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockBattlebabe = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.BATTLEBABE)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesBattlebabe)
            .build();

    public static final List<Move> battlebabeOptionalMoves = List.of(dangerousAndSexy, iceCold, merciless, visionsOfDeath, perfectInstincts, impossibleReflexes);

    public static final List<Move> battlebabeDefaultMoves = List.of(battlebabeSpecial);

    public static final PlaybookCreator playbookCreatorBattlebabe = PlaybookCreator.builder()
            .id(new ObjectId().toString())
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
            .improvementBlock(improvementBlockBattlebabe)
            .build();

    /* ----------------------------- BRAINER PLAYBOOK CREATOR --------------------------------- */
    public static final BrainerGearCreator brainerGearCreator = BrainerGearCreator.builder()
            .id(new ObjectId().toString())
            .defaultItemCount(2)
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

    public static final PlaybookUniqueCreator brainerUniqueCreator = PlaybookUniqueCreator.builder().type(UniqueType.BRAINER_GEAR)
            .id(new ObjectId().toString())
            .brainerGearCreator(brainerGearCreator)
            .build();

    public static final GearInstructions brainerGearInstructions = GearInstructions.builder()
            .id(new ObjectId().toString())
            .gearIntro("You get:")
            .youGetItems(List.of("fashion suitable to your look, including at your option a piece worth 1-armor (you detail)"))
            .introduceChoice("Small fancy weapons")
            .numberCanChoose(1)
            .chooseableGear(List.of("silenced 9mm (2-harm close hi-tech)", "ornate dagger (2-harm hand valuable)", "hidden knives (2-harm hand infinite)", "scalpels (3-harm intimate hi-tech)", "antique handgun (2-harm close reload loud valuable)"))
            .startingBarter(8)
            .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
            .build();

    public static final List<Move> improvementMovesBrainer = List.of(
            coolMax2,
            sharpMax2,
            hardMax2,
            secondHardMax2,
            addBrainerMove1,
            addBrainerMove2,
            adjustBrainerUnique1,
            addHolding,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockBrainer = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.BRAINER)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesBrainer)
            .build();

    public static final List<Move> brainerOptionalMoves = List.of(unnaturalLust, brainReceptivity, brainScan, whisperProjection, puppetStrings);

    public static final List<Move> brainerDefaultMoves = List.of(brainerSpecial);

    public static final PlaybookCreator playbookCreatorBrainer = PlaybookCreator.builder()
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
            .improvementBlock(improvementBlockBrainer)
            .defaultMoveCount(1)
            .moveChoiceCount(2)
            .defaultVehicleCount(0)
            .build();

    /* ----------------------------- CHOPPER PLAYBOOK CREATOR --------------------------------- */
    public static final GangOption gangOption1 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang consists of 30 or so violent bastards. Medium instead of small.")
            .modifier("MEDIUM")
            .build();

    public static final GangOption gangOption2 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang's well-armed. +1harm")
            .modifier("+1harm")
            .build();

    public static final GangOption gangOption3 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang's well-armored. +1armor")
            .modifier("+1armor")
            .build();

    public static final GangOption gangOption4 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang's well-disciplined. Drop savage.")
            .tag("-savage")
            .build();

    public static final GangOption gangOption5 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang's nomadic at heart, and able to maintain and repair its own bikes without a home base. It gets +mobile.")
            .tag("+mobile")
            .build();

    public static final GangOption gangOption6 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang's self-sufficient, able to provide for itself by raiding and scavenging. It gets +rich")
            .tag("+rich")
            .build();

    public static final GangOption gangOption7 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang's bikes are in bad shape and need constant attention. Vulnerable: breakdown.")
            .tag("+vulnerable: breakdown")
            .build();

    public static final GangOption gangOption8 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang's bikes are picky and high-maintenance. Vulnerable: grounded.")
            .tag("+vulnerable: grounded")
            .build();

    public static final GangOption gangOption9 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang's loose-knit, with members coming and going as they choose. Vulnerable: desertion")
            .tag("+vulnerable: desertion")
            .build();

    public static final GangOption gangOption10 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang is in significant debt to someone powerful. Vulnerable: obligation.")
            .tag("+vulnerable: obligation")
            .build();

    public static final GangOption gangOption11 = GangOption.builder()
            .id(new ObjectId().toString())
            .description("your gang is filthy and unwell. Vulnerable: disease.")
            .tag("+vulnerable: disease")
            .build();

    public static final GangCreator gangCreator = GangCreator.builder()
            .id(new ObjectId().toString())
            .intro("By default, your gang consists of about 15 violent bastards with scavenged and makeshift weapons and armor, and no fucking discipline at all (2-harm gang small savage 1-armor)")
            .defaultSize(GangSize.SMALL)
            .defaultArmor(1)
            .defaultHarm(2)
            .strengthChoiceCount(2)
            .weaknessChoiceCount(1)
            .defaultTags(List.of("savage"))
            .strengths(List.of(gangOption1, gangOption2, gangOption3, gangOption4, gangOption5, gangOption6))
            .weaknesses(List.of(gangOption7, gangOption8, gangOption9, gangOption10, gangOption11))
            .build();

    public static final PlaybookUniqueCreator chopperUniqueCreator = PlaybookUniqueCreator.builder()
            .id(new ObjectId().toString())
            .type(UniqueType.GANG)
            .gangCreator(gangCreator)
            .build();

    public static final GearInstructions chopperGearInstructions = GearInstructions.builder()
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

    public static final List<Move> improvementMovesChopper = List.of(
            hardMax3,
            coolMax2,
            sharpMax2,
            weirdMax2,
            secondWeirdMax2,
            adjustChopperUnique1,
            adjustChopperUnique2,
            addHolding,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockChopper = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.CHOPPER)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesChopper)
            .build();

    public static final List<Move> chopperDefaultMoves = List.of(chopperSpecial, packAlpha, fuckingThieves);

    public static final PlaybookCreator playbookCreatorChopper = PlaybookCreator.builder()
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
            .improvementBlock(improvementBlockChopper)
            .optionalMoves(List.of())
            .defaultMoves(chopperDefaultMoves)
            .defaultMoveCount(3)
            .moveChoiceCount(0)
            .defaultVehicleCount(1)
            .build();

    /* ----------------------------- DRIVER PLAYBOOK CREATOR --------------------------------- */

    public static final GearInstructions driverGearInstructions = GearInstructions.builder()
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

    public static final List<Move> driverOptionalMoves = List.of(combatDriver, eyeOnTheDoor, weatherEye, reputationMove, daredevil, collectorMove, myOtherCarIsATank);

    public static final List<Move> driverDefaultMoves = List.of(driverSpecial);

    public static final List<Move> improvementMovesDriver = List.of(
            coolMax3,
            hotMax2,
            sharpMax2,
            weirdMax2,
            addDriverMove1,
            addDriverMove2,
            addVehicle,
            addWorkspace,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockDriver = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.DRIVER)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesDriver)
            .build();

    // Driver has no PlaybookUniques; has Vehicles instead

    public static final PlaybookCreator playbookCreatorDriver = PlaybookCreator.builder()
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
            .improvementBlock(improvementBlockDriver)
            .defaultMoveCount(1)
            .moveChoiceCount(2)
            .defaultVehicleCount(1)
            .build();

    /* ----------------------------- GUNLUGGER PLAYBOOK CREATOR --------------------------------- */

    public static final GearInstructions gearInstructionsGunlugger = GearInstructions.builder()
            .id(new ObjectId().toString())
            .gearIntro("You get:")
            .youGetItems(List.of("armor worth 2-armor (you detail)"))
            .startingBarter(2)
            .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
            .build();

    public static final WeaponsCreator weaponsCreator = WeaponsCreator.builder()
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

    public static final PlaybookUniqueCreator gunluggerUniqueCreator = PlaybookUniqueCreator.builder()
            .id(new ObjectId().toString())
            .type(UniqueType.WEAPONS)
            .weaponsCreator(weaponsCreator)
            .build();

    public static final List<Move> improvementMovesGunlugger = List.of(
            coolMax2,
            sharpMax2,
            weirdMax2,
            addGunluggerMove1,
            addGunluggerMove2,
            addVehicle,
            addHolding,
            addGangPackAlpha,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockGunlugger = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.GUNLUGGER)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesGunlugger)
            .build();

    public static final List<Move> gunluggerOptionalMoves = List.of(battleHardened, fuckThisShit, battlefieldInstincts, insanoLikeDrano, preparedForTheInevitable, bloodcrazed, notToBeFuckedWith);

    public static final List<Move> gunluggerDefaultMoves = List.of(gunluggerSpecial);

    public static final PlaybookCreator playbookCreatorGunlugger = PlaybookCreator.builder()
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
            .improvementBlock(improvementBlockGunlugger)
            .playbookUniqueCreator(gunluggerUniqueCreator)
            .defaultMoveCount(1)
            .moveChoiceCount(3)
            .defaultVehicleCount(0)
            .build();

    /* ----------------------------- HARDHOLDER PLAYBOOK CREATOR --------------------------------- */

    public static final GearInstructions gearInstructionsHardholder = GearInstructions.builder()
            .id(new ObjectId().toString())
            .gearIntro("In addition to your holding, detail your personal fashion.\n" +
                    "\n" +
                    "You can have, for your personal use, with the MC's approval, a few pieces of non-specialized gear or weapons from any character playbook.")
            .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
            .build();

    public static final HoldingOption holdingOption1 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption2 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption3 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption4 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption5 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption6 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption7 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption8 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption9 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption10 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption11 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption12 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption13 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption14 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption15 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption16 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption17 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption18 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption19 = HoldingOption.builder()
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
    public static final HoldingOption holdingOption20 = HoldingOption.builder()
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

    public static final HoldingCreator holdingCreator = HoldingCreator.builder()
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
            .defaultSouls("75-150 souls")
            .defaultArmorBonus(1)
            .defaultSurplus(1)
            .defaultVehiclesCount(4)
            .defaultBattleVehicleCount(4)
            .defaultGangSize(GangSize.MEDIUM)
            .defaultGangHarm(2)
            .defaultGangArmor(1)
            .defaultGangTag("unruly")
            .defaultStrengthsCount(4)
            .defaultWeaknessesCount(2)
            .strengthOptions(List.of(holdingOption1, holdingOption2, holdingOption3, holdingOption4, holdingOption5,
                    holdingOption6, holdingOption7, holdingOption8, holdingOption9, holdingOption10, holdingOption11))
            .weaknessOptions(List.of(holdingOption12, holdingOption13, holdingOption14, holdingOption15,
                    holdingOption16, holdingOption17, holdingOption18, holdingOption19, holdingOption20))
            .build();

    public static final PlaybookUniqueCreator playbookUniqueCreatorHardHolder = PlaybookUniqueCreator.builder()
            .id(new ObjectId().toString())
            .type(UniqueType.HOLDING)
            .holdingCreator(holdingCreator)
            .build();

    public static final List<Move> improvementMovesHardholder = List.of(
            hardMax3,
            weirdMax2,
            coolMax2,
            hotMax2,
            sharpMax2,
            adjustHardHolderUnique1,
            adjustHardHolderUnique2,
            adjustHardHolderUnique3,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockHardholder = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.HARDHOLDER)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesHardholder)
            .build();

    public static final List<Move> hardholderDefaultMoves = List.of(hardholderSpecial, leadership, wealth);

    public static final PlaybookCreator playbookCreatorHardHolder = PlaybookCreator.builder()
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
            .improvementBlock(improvementBlockHardholder)
            .moveChoiceCount(0)
            .defaultMoveCount(3)
            .build();

    /* ----------------------------- HOCUS PLAYBOOK CREATOR --------------------------------- */

    public static final GearInstructions gearInstructionsHocus = GearInstructions.builder()
            .id(new ObjectId().toString())
            .gearIntro("In addition to your followers, detail your fashion according to your look. But apart from that and some barter, you have no gear to speak of.")
            .startingBarter(4)
            .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
            .build();

    public static final FollowersOption followersOption1 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers are dedicated to you. Surplus: +1barter, and replace want: desertion with want: hungry.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(1)
            .fortuneChange(-1)
            .surplusChange(null)
            .wantChange(List.of("+hungry", "-desertion"))
            .build();
    public static final FollowersOption followersOption2 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers are involved in successful commerce. +1fortune.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(1)
            .surplusChange(null)
            .wantChange(null)
            .build();
    public static final FollowersOption followersOption3 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers, taken as a body, constitute a powerful psychic antenna. Surplus +augury.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(-1)
            .surplusChange("+augury")
            .wantChange(null)
            .build();
    public static final FollowersOption followersOption4 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers are joyous and celebratory. Surplus: +party.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(-1)
            .surplusChange("+party")
            .wantChange(null)
            .build();
    public static final FollowersOption followersOption5 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers are rigorous and argumentative. Surplus: +insight.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(-1)
            .surplusChange("+insight")
            .wantChange(null)
            .build();
    public static final FollowersOption followersOption6 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers are hard-working, no-nonsense. +1barter.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(1)
            .fortuneChange(-1)
            .surplusChange(null)
            .wantChange(null)
            .build();
    public static final FollowersOption followersOption7 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers are eager, enthusiastic and successful recruiters. Surplus: +growth.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(-1)
            .surplusChange("+growth")
            .wantChange(null)
            .build();
    public static final FollowersOption followersOption8 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("You have few followers, 10 or fewer. Surplus: -1barter.")
            .newNumberOfFollowers(10)
            .surplusBarterChange(-1)
            .fortuneChange(-1)
            .surplusChange(null)
            .wantChange(null)
            .build();
    public static final FollowersOption followersOption9 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers aren't really yours, more like you're theirs. Want: judgement instead of want: desertion.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(-1)
            .surplusChange(null)
            .wantChange(List.of("+judgement", "-desertion"))
            .build();
    public static final FollowersOption followersOption10 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers rely entirely on you for their lives and needs. Want: +desperation.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(-1)
            .surplusChange(null)
            .wantChange(List.of("+desperation"))
            .build();
    public static final FollowersOption followersOption11 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers are drug-fixated. Surplus: +stupor.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(-1)
            .surplusChange("+stupor")
            .wantChange(null)
            .build();
    public static final FollowersOption followersOption12 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers disdain fashion, luxury and convention. Want: +disease.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(-1)
            .surplusChange(null)
            .wantChange(List.of("+disease"))
            .build();
    public static final FollowersOption followersOption13 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers disdain law, peace, reason and society. Surplus: +violence.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(-1)
            .surplusChange("+violence")
            .wantChange(null)
            .build();
    public static final FollowersOption followersOption14 = FollowersOption.builder()
            .id(new ObjectId().toString())
            .description("Your followers are decadent and perverse. Want: +savagery.")
            .newNumberOfFollowers(-1)
            .surplusBarterChange(-2)
            .fortuneChange(-1)
            .surplusChange(null)
            .wantChange(List.of("+savagery"))
            .build();

    public static final FollowersCreator followersCreator = FollowersCreator.builder()
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

    public static final PlaybookUniqueCreator playbookUniqueCreatorHocus = PlaybookUniqueCreator.builder()
            .id(new ObjectId().toString())
            .type(UniqueType.FOLLOWERS)
            .followersCreator(followersCreator)
            .build();

    public static final List<Move> improvementMovesHocus = List.of(
            coolMax2,
            hardMax2,
            sharpMax2,
            addHocusMove1,
            addHocusMove2,
            adjustHocusUnique1,
            adjustHocusUnique2,
            addHolding,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockHocus = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.HOCUS)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesHocus)
            .build();

    public static final List<Move> hocusDefaultMoves = List.of(hocusSpecial, fortunes);

    public static final List<Move> hocusMoves = List.of(frenzy, charismatic, fuckingWacknut, seeingSouls, divineProtection);

    public static final PlaybookCreator playbookCreatorHocus = PlaybookCreator.builder()
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
            .improvementBlock(improvementBlockHocus)
            .moveChoiceCount(2)
            .defaultMoveCount(2)
            .build();

    /* ----------------------------- MAESTRO D' PLAYBOOK CREATOR --------------------------------- */

    public static final GearInstructions gearInstructionsMaestro = GearInstructions.builder()
            .id(new ObjectId().toString())
            .gearIntro("In addition to your establishment, you get:")
            .youGetItems(List.of(
                    "a wicked blade, like a kitchen knife or 12\" razor-sharp scissors (2-harm hand)",
                    "fashion suitable to your look, including at your option a piece worth 1-armor (you detail)"))
            .startingBarter(2)
            .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
            .build();

    public static final SecurityOption securityOption1 = SecurityOption.builder()
            .id(new ObjectId().toString())
            .description("a real gang (3-harm gang small 1-armor)")
            .value(2)
            .build();

    public static final SecurityOption securityOption2 = SecurityOption.builder()
            .id(new ObjectId().toString())
            .description("a convenient shotgun (3-harm close reload messy)")
            .value(1)
            .build();

    public static final SecurityOption securityOption3 = SecurityOption.builder()
            .id(new ObjectId().toString())
            .description("a bouncer who knows his biz (2-harm 1-armor)")
            .value(1)
            .build();

    public static final SecurityOption securityOption4 = SecurityOption.builder()
            .id(new ObjectId().toString())
            .description("plywood & chickenwire (1-armor)")
            .value(1)
            .build();

    public static final SecurityOption securityOption5 = SecurityOption.builder()
            .id(new ObjectId().toString())
            .description("secrecy, passwords, codes & signals, invites-only, vouching etc.")
            .value(1)
            .build();

    public static final SecurityOption securityOption6 = SecurityOption.builder()
            .id(new ObjectId().toString())
            .description("everybody's packing: your cast & crew are a gang (2-harm gang small 0-armor)")
            .value(1)
            .build();

    public static final SecurityOption securityOption7 = SecurityOption.builder()
            .id(new ObjectId().toString())
            .description("a warren of dead-ends, hideaways and boltholes")
            .value(1)
            .build();

    public static final SecurityOption securityOption8 = SecurityOption.builder()
            .id(new ObjectId().toString())
            .description("no fixed location, always new venues")
            .value(1)
            .build();

    public static final EstablishmentCreator establishmentCreator = EstablishmentCreator.builder()
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

    public static final PlaybookUniqueCreator playbookUniqueCreatorMaestro = PlaybookUniqueCreator.builder()
            .id(new ObjectId().toString())
            .type(UniqueType.ESTABLISHMENT)
            .establishmentCreator(establishmentCreator)
            .build();

    public static final List<Move> improvementMovesMaestroD = List.of(
            hotMax3,
            coolMax2,
            hardMax2,
            weirdMax2,
            addMaestroDMove1,
            addMaestroDMove2,
            adjustMaestroDUnique1,
            adjustMaestroDUnique2,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockMaestroD = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.MAESTRO_D)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesMaestroD)
            .build();

    public static final List<Move> maestroDefaultMoves = List.of(maestroSpecial);

    public static final List<Move> maestroMoves = List.of(callThisHot, devilWithBlade, fingersInPie, everyBodyEats, justGiveMotive);

    public static final PlaybookCreator playbookCreatorMaestro = PlaybookCreator.builder()
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
            .improvementBlock(improvementBlockMaestroD)
            .moveChoiceCount(2)
            .defaultMoveCount(1)
            .build();

    /* ----------------------------- SAVVYHEAD PLAYBOOK CREATOR --------------------------------- */

    public static final GearInstructions gearInstructionsSavvyhead = GearInstructions.builder()
            .id(new ObjectId().toString())
            .gearIntro("In addition to your workspace, detail your personal fashion, and any personal piece or three of normal gear or weaponry.")
            .startingBarter(6)
            .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
            .build();

    public static final WorkspaceCreator workspaceCreator = WorkspaceCreator.builder()
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

    public static final PlaybookUniqueCreator playbookUniqueCreatorSavvyhead = PlaybookUniqueCreator.builder()
            .id(new ObjectId().toString())
            .type(UniqueType.WORKSPACE)
            .workspaceCreator(workspaceCreator)
            .build();

    public static final List<Move> improvementMovesSavvyhead = List.of(
            coolMax2,
            hardMax2,
            sharpMax2,
            addSavvyheadMove1,
            addSavvyheadMove2,
            addGangLeadership,
            adjustSavvyheadUnique1,
            adjustSavvyheadUnique2,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockSavvyhead = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.SAVVYHEAD)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesSavvyhead)
            .build();

    public static final List<Move> savvyheadDefaultMoves = List.of(savvyheadSpecial);

    public static final List<Move> savvyheadMoves = List.of(thingsSpeak, bonefeel, oftenerRight, frayingEdge, spookyIntense, deepInsights);

    public static final PlaybookCreator playbookCreatorSavvyhead = PlaybookCreator.builder()
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
            .improvementBlock(improvementBlockSavvyhead)
            .moveChoiceCount(2)
            .defaultMoveCount(1)
            .build();

    /* ----------------------------- SKINNER PLAYBOOK CREATOR --------------------------------- */

    public static final GearInstructions gearInstructionsSkinner = GearInstructions.builder()
            .id(new ObjectId().toString())
            .gearIntro("You get:")
            .youGetItems(List.of("fashion suitable to your look (you detail)"))
            .startingBarter(2)
            .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
            .build();

    public static final SkinnerGearItem item1 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("sleeve pistol (2-harm close reload loud)")
            .build();
    public static final SkinnerGearItem item2 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("ornate dagger (2-harm hand valuable)")
            .build();
    public static final SkinnerGearItem item3 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("hidden knives (2-harm hand infinite)")
            .build();
    public static final SkinnerGearItem item4 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("ornate sword (3-harm hand valuable)")
            .build();
    public static final SkinnerGearItem item5 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("antique handgun (2-harm close reload loud valuable)")
            .build();
    public static final SkinnerGearItem item6 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("antique coins (worn valuable)")
            .note("Drilled with holes for jewelry")
            .build();
    public static final SkinnerGearItem item7 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("eyeglasses (worn valuable)")
            .note("You may use these for +1sharp when your eyesight matters, but if you do, without them you get -1sharp when your eyesight matters.")
            .build();
    public static final SkinnerGearItem item8 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("long gorgeous coat (worn valuable)")
            .build();
    public static final SkinnerGearItem item9 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("spectacular tattoos (implanted)")
            .build();
    public static final SkinnerGearItem item10 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("skin & hair kit (applied valuable)")
            .note("Soaps, ochres, paints, creams, salves. Using it lets you take +1hot forward.")
            .build();
    public static final SkinnerGearItem item11 = SkinnerGearItem.builder()
            .id(new ObjectId().toString())
            .item("a pet (valuable alive")
            .note("Your choice and yours to detail.")
            .build();

    public static final SkinnerGearCreator skinnerGearCreator = SkinnerGearCreator.builder()
            .id(new ObjectId().toString())
            .graciousWeaponCount(1)
            .luxeGearCount(2)
            .graciousWeaponChoices(List.of(item1, item2, item3, item4, item5))
            .luxeGearChoices(List.of(item6, item7, item8, item9, item10, item11))
            .build();

    public static final PlaybookUniqueCreator playbookUniqueCreatorSkinner = PlaybookUniqueCreator.builder()
            .id(new ObjectId().toString())
            .type(UniqueType.SKINNER_GEAR)
            .skinnerGearCreator(skinnerGearCreator)
            .build();

    public static final List<Move> improvementMovesSkinner = List.of(
            coolMax2,
            secondCoolMax2,
            hardMax2,
            sharpMax2,
            addSkinnerMove1,
            addSkinnerMove2,
            addEstablishment,
            addFollowers,
            addOtherPBMove1,
            addOtherPBMove2
    );

    public static final ImprovementBlock improvementBlockSkinner = ImprovementBlock.builder()
            .id(new ObjectId().toString())
            .playbookType(PlaybookType.SKINNER)
            .improvementInstructions(IMPROVEMENT_INSTRUCTIONS_FOR_APP)
            .futureImprovementMoves(futureImprovementMoves)
            .improvementMoves(improvementMovesSkinner)
            .build();

    public static final List<Move> skinnerOptionalMoves = List.of(breathtaking, lost, artful, anArrestingSkinner, hypnotic);

    public static final List<Move> skinnerDefaultMoves = List.of(skinnerSpecial);

    public static final PlaybookCreator playbookCreatorSkinner = PlaybookCreator.builder()
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
            .improvementBlock(improvementBlockSkinner)
            .moveChoiceCount(2)
            .defaultMoveCount(1)
            .build();
}
