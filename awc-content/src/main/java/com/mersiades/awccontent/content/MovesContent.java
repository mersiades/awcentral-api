package com.mersiades.awccontent.content;

import com.mersiades.awccontent.constants.MoveNames;
import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.models.MoveAction;
import com.mersiades.awccontent.models.RollModifier;
import com.mersiades.awccontent.models.StatModifier;
import org.bson.types.ObjectId;

import java.util.List;

import static com.mersiades.awccontent.constants.MoveNames.*;
import static com.mersiades.awccontent.enums.StatType.*;

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

    public static final MoveAction goAggroAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move goAggro = Move.builder()
            .id(new ObjectId().toString())
            .name(MoveNames.goAggroName)
            .description("When you _**go aggro on someone**_, make it clear what you want them to do and what you’ll do to them. Roll+hard.\n" +
                    "\n" +
                    "On a 10+, they have to choose:\n" +
                    "\n" +
                    "- *Force your hand and suck it up.*\n" +
                    "- *Cave and do what you want.*\n" +
                    "\n" +
                    "On a 7–9, they can choose 1 of the above, or 1 of the following:\n" +
                    "\n" +
                    "- *Get the hell out of your way.*\n" +
                    "- *Barricade themselves securely in.*\n" +
                    "- *Give you something they think you want, or tell you what you want to hear.*\n" +
                    "- *Back off calmly, hands where you can see.*\n" +
                    "\n" +
                    "On a miss, be prepared for the worst.")
            .kind(MoveType.BASIC)
            .moveAction(goAggroAction)
            .playbook(null)
            .build();
    public static final MoveAction suckerAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move sucker = Move.builder()
            .id(new ObjectId().toString())
            .name(suckerSomeoneName)
            .description("When you _**attack someone unsuspecting or helpless**_, ask the MC if you could miss. If you could, treat it as going aggro, but your victim has no choice to cave and do what you want. If you couldn’t, you simply inflict harm as established.")
            .kind(MoveType.BASIC)
            .moveAction(suckerAction)
            .playbook(null)
            .build();
    public static final MoveAction doBattleAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move doBattle = Move.builder()
            .id(new ObjectId().toString())
            .name("DO BATTLE")
            .description("When you’re _**in battle**_, you can bring the battle moves into play.")
            .kind(MoveType.BASIC)
            .moveAction(doBattleAction)
            .playbook(null)
            .build();
    public static final MoveAction seduceOrManipAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HOT)
            .build();
    public static final Move seduceOrManip = Move.builder()
            .id(new ObjectId().toString())
            .name(manipulateName)
            .description("When you _**try to seduce, manipulate, bluff, fast-talk, or lie to someone**_, tell them what you want them to do, give them a reason, and roll+hot.\n" +
                    "\n" +
                    "**For NPCs**: on a 10+, they’ll go along with you, unless or until some fact or action betrays the reason you gave them.\n" +
                    "\n" +
                    "On a 7–9, they’ll go along with you, but they need some concrete assurance, corroboration, or evidence first.\n" +
                    "\n" +
                    "**For PCs**: on a 10+, both. On a 7–9, choose 1:\n" +
                    "\n" +
                    "- *If they go along with you, they mark experience.*\n" +
                    "- *If they refuse, erase one of their stat highlights for the remainder of the session.*\n" +
                    "\n" +
                    "What they do then is up to them.\n" +
                    "\n" +
                    "On a miss, for either NPCs or PCs, be prepared for the worst.")
            .kind(MoveType.BASIC)
            .moveAction(seduceOrManipAction)
            .playbook(null)
            .build();
    public static final MoveAction helpOrInterfereAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.HX)
            .statToRollWith(null)
            .build();
    public static final Move helpOrInterfere = Move.builder()
            .id(new ObjectId().toString())
            .name(helpOrInterfereName)
            .description("When you _**help**_ or _**interfere**_ with someone who’s making a roll, roll+Hx.\n" +
                    "\n" +
                    "On a 10+, they take +2 (help) or -2 (interfere) to their roll.\n" +
                    "\n" +
                    "On a 7–9, they take +1 (help) or -1 (interfere) to their roll.\n" +
                    "\n" +
                    "On a miss, be prepared for the worst.")
            .kind(MoveType.BASIC)
            .moveAction(helpOrInterfereAction)
            .playbook(null)
            .build();
    public static final MoveAction readASitchAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(SHARP)
            .build();
    public static final Move readASitch = Move.builder()
            .id(new ObjectId().toString())
            .name("READ A SITCH")
            .description("When you _**read a charged situation**_, roll+sharp.\n" +
                    "\n" +
                    "On a hit, you can ask the MC questions. Whenever you act on one of the MC’s answers, take +1. On a 10+, ask 3. On a 7–9, ask 1:\n" +
                    "\n" +
                    "- *Where’s my best escape route / way in / way past?*\n" +
                    "- *Which enemy is most vulnerable to me?*\n" +
                    "- *Which enemy is the biggest threat?*\n" +
                    "- *What should I be on the lookout for?*\n" +
                    "- *What’s my enemy’s true position?*\n" +
                    "- *Who’s in control here?*\n" +
                    "\n" +
                    "On a miss, ask 1 anyway, but be prepared for the worst.")
            .kind(MoveType.BASIC)
            .moveAction(readASitchAction)
            .playbook(null)
            .build();
    public static final MoveAction readAPersonAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(SHARP)
            .build();
    public static final Move readAPerson = Move.builder()
            .id(new ObjectId().toString())
            .name(readPersonName)
            .description("When you _**read a person**_ in a charged interaction, roll+sharp.\n" +
                    "\n" +
                    "On a 10+, hold 3. On a 7–9, hold 1. While you’re interacting with them, spend your hold to ask their player questions, 1 for 1:\n" +
                    "\n" +
                    "- *Is your character telling the truth?*\n" +
                    "- *What’s your character really feeling?*\n" +
                    "- *What does your character intend to do?*\n" +
                    "- *What does your character wish I’d do?*\n" +
                    "- *How could I get your character to__?*\n" +
                    "\n" +
                    "On a miss, ask 1 anyway, but be prepared for the worst.")
            .kind(MoveType.BASIC)
            .moveAction(readAPersonAction)
            .playbook(null)
            .build();
    public static final MoveAction openBrainAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move openBrain = Move.builder()
            .id(new ObjectId().toString())
            .name("OPEN YOUR BRAIN")
            .description("When you _**open your brain to the world’s psychic maelstrom**_, roll+weird.\n" +
                    "\n" +
                    "On a hit, the MC tells you something new and interesting about the current situation, and might ask you a question or two; answer them.\n" +
                    "\n" +
                    "On a 10+, the MC gives you good detail. On a 7–9, the MC gives you an impression. If you already know all there is to know, the MC will tell you that.\n" +
                    "\n" +
                    "On a miss, be prepared for the worst.")
            .kind(MoveType.BASIC)
            .moveAction(openBrainAction)
            .playbook(null)
            .build();
    public static final MoveAction lifestyleAndGigsAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.BARTER)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move lifestyleAndGigs = Move.builder()
            .id(new ObjectId().toString())
            .name("LIFESTYLE AND GIGS")
            .description("_**At the beginning of the session**_, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions. If you need jingle during a session, tell the MC you’d like to work a gig.")
            .kind(MoveType.BASIC)
            .moveAction(lifestyleAndGigsAction)
            .playbook(null)
            .build();
    public static final MoveAction sessionEndAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move sessionEnd = Move.builder()
            .id(new ObjectId().toString())
            .name("SESSION END")
            .description("_**At the end of every session**_, choose a character who knows you better than they used to. If there’s more than one, choose one at your whim.\n" +
                    "\n" +
                    "Tell that player to add +1 to their Hx with you on their sheet. If this brings them to Hx+4, they reset to Hx+1 (and therefore mark experience).\n" +
                    "\n" +
                    "If no one knows you better, choose a character who doesn’t know you as well as they thought, or choose any character at your whim. Tell that player to take -1 to their Hx with you on their sheet. If this brings them to Hx -3, they reset to Hx=0 (and therefore mark experience).")
            .kind(MoveType.BASIC)
            .moveAction(sessionEndAction)
            .playbook(null)
            .build();

    /* ----------------------------- PERIPHERAL MOVES --------------------------------- */

    public static final MoveAction sufferHarmAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.HARM)
            .build();
    public static final Move sufferHarm = Move.builder()
            .id(new ObjectId().toString())
            .name("SUFFER HARM")
            .description("When you _**suffer harm**_, roll+harm suffered (after armor, if you’re wearing any).\n" +
                    "\n" +
                    "On a 10+, the MC can choose 1:\n" +
                    "\n" +
                    "- *You’re out of action: unconscious, trapped, incoherent or panicked.*\n" +
                    "- *It’s worse than it seemed. Take an additional 1-harm.*\n" +
                    "- *Choose 2 from the 7–9 list below.*\n" +
                    "\n" +
                    "On a 7–9, the MC can choose 1:\n" +
                    "\n" +
                    "- *You lose your footing.*\n" +
                    "- *You lose your grip on whatever you’re holding.*\n" +
                    "- *You lose track of someone or something you’re attending to.*\n" +
                    "- *You miss noticing something important.*\n" +
                    "\n" +
                    "On a miss, the MC can nevertheless choose something from the 7–9 list above. If she does, though, it’s instead of some of the harm you’re suffering, so you take -1harm.")
            .kind(MoveType.PERIPHERAL)
            .moveAction(sufferHarmAction)
            .playbook(null)
            .build();
    public static final MoveAction sufferVHarmAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.V_HARM)
            .build();
    public static final Move sufferVHarm = Move.builder()
            .id(new ObjectId().toString())
            .name(MoveNames.sufferVHarm)
            .description("When you _**suffer v-harm**_, roll+harm suffered.\n" +
                    "\n" +
                    "On a 10+, you lose control, and your attacker chooses 1:\n" +
                    "\n" +
                    "- *You crash.*\n" +
                    "- *You spin out.*\n" +
                    "- *Choose 2 from the 7–9 list below.*\n" +
                    "\n" +
                    "On a 7–9, you're forced to swerve. Your attacker chooses 1:\n" +
                    "\n" +
                    "- *You give ground.*\n" +
                    "- *You're driven off course, or forced onto a new course'.*\n" +
                    "- *Your car takes 1-harm ap, right in the transmission.*\n" +
                    "\n" +
                    "On a miss, you swerve but recover without disadvantage.")
            .kind(MoveType.PERIPHERAL)
            .moveAction(sufferVHarmAction)
            .playbook(null)
            .build();
    public static final MoveAction inflictHarmAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ADJUST_HX)
            .build();
    public static final Move inflictHarmMove = Move.builder()
            .id(new ObjectId().toString())
            .name(inflictHarmName)
            .description("When you _**inflict harm on another player’s character**_, the other character gets +1Hx with you (on their sheet) for every segment of harm you inflict.\n" +
                    "\n" +
                    "If this brings them to Hx+4, they reset to Hx+1 as usual, and therefore mark experience.")
            .kind(MoveType.PERIPHERAL)
            .moveAction(inflictHarmAction)
            .playbook(null)
            .build();
    public static final MoveAction healPcHarmAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ADJUST_HX)
            .build();
    public static final Move healPcHarm = Move.builder()
            .id(new ObjectId().toString())
            .name(healHarmName)
            .description("When you _**heal another player’s character’s harm**_, you get +1Hx with them (on your sheet) for every segment of harm you heal.\n" +
                    "\n" +
                    "If this brings you to Hx+4, you reset to Hx+1 as usual, and therefore mark experience.")
            .kind(MoveType.PERIPHERAL)
            .moveAction(healPcHarmAction)
            .playbook(null)
            .build();
    public static final MoveAction giveBarterAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.BARTER)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move giveBarter = Move.builder()
            .id(new ObjectId().toString())
            .name("GIVE BARTER")
            .description("When you _**give 1-barter to someone, but with strings attached**_, it counts as manipulating them and hitting the roll with a 10+, no leverage or roll required.")
            .kind(MoveType.PERIPHERAL)
            .moveAction(giveBarterAction)
            .playbook(null)
            .build();
    public static final MoveAction goToMarketAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(SHARP)
            .build();
    public static final Move goToMarket = Move.builder()
            .id(new ObjectId().toString())
            .name("GO TO THE MARKET")
            .description("When you _**go into a holding’s bustling market**_, looking for some particular thing to buy, and it’s not obvious whether you should be able to just go buy one like that, roll+sharp.\n" +
                    "\n" +
                    "On a 10+, yes, you can just go buy it like that.\n" +
                    "\n" +
                    "On a 7–9, the MC chooses 1:\n" +
                    "\n" +
                    "- *It costs 1-barter more than you’d expect.*\n" +
                    "- *It’s not openly for sale, but you find someone who can lead you to someone selling it.*\n" +
                    "- *It’s not openly for sale, but you find someone who sold it recently, who may be willing to introduce you to their previous buyer.*\n" +
                    "- *It’s not available for sale, but you find something similar. Will it do?*\n" +
                    "\n" +
                    "On a miss, the MC chooses 1, plus it costs 1-barter more.")
            .kind(MoveType.PERIPHERAL)
            .moveAction(goToMarketAction)
            .playbook(null)
            .build();
    public static final MoveAction makeWantKnownAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.BARTER)
            .build();
    public static final Move makeWantKnown = Move.builder()
            .id(new ObjectId().toString())
            .name(makeWantKnownName)
            .description("When you _**make known that you want a thing and drop jingle to speed it on its way**_, roll+barter spent (max roll+3). It has to be a thing you could legitimately get this way.\n" +
                    "\n" +
                    "On a 10+ it comes to you, no strings attached.\n" +
                    "\n" +
                    "On a 7–9 it comes to you, or something pretty close.\n" +
                    "\n" +
                    "On a miss, it comes to you, but with strings very much attached.")
            .kind(MoveType.PERIPHERAL)
            .moveAction(makeWantKnownAction)
            .playbook(null)
            .build();
    public static final MoveAction insightAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move insight = Move.builder()
            .id(new ObjectId().toString())
            .name("INSIGHT")
            .description("When you are able to go to someone for _**insight**_, ask them what they think your best course is, and the MC will tell you.\n" +
                    "\n" +
                    "If you pursue that course, take +1 to any rolls you make in the pursuit. If you pursue that course but don’t accomplish your ends, you mark experience.")
            .kind(MoveType.PERIPHERAL)
            .moveAction(insightAction)
            .playbook(null)
            .build();
    public static final MoveAction auguryAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move augury = Move.builder()
            .id(new ObjectId().toString())
            .name("AUGURY")
            .description("When you are able to use something for _**augury**_, roll+weird.\n" +
                    "\n" +
                    "On a hit, you can choose 1:\n" +
                    "\n" +
                    "- *Reach through the world’s psychic maelstrom to something or someone connected to it.*\n" +
                    "- *Isolate and protect a person or thing from the world’s psychic maelstrom.*\n" +
                    "- *Isolate and contain a fragment of the world’s psychic maelstrom itself.*\n" +
                    "- *Insert information into the world’s psychic maelstrom.*\n" +
                    "- *Open a window into the world’s psychic maelstrom.*\n" +
                    "\n" +
                    "By default, the effect will last only as long as you maintain it, will reach only shallowly into the world’s psychic maelstrom as it is local to you, and will bleed instability.\n" +
                    "\n" +
                    "On a 10+, choose 2; on a 7–9, choose 1:\n" +
                    "\n" +
                    "- *It’ll persist (for a while) without your actively maintaining it.*\n" +
                    "- *It reaches deep into the world’s psychic maelstrom.*\n" +
                    "- *It reaches broadly throughout the world’s psychic maelstrom.*\n" +
                    "- *It’s stable and contained, no bleeding.*\n" +
                    "\n" +
                    "On a miss, whatever bad happens, your antenna takes the brunt of it.")
            .kind(MoveType.PERIPHERAL)
            .moveAction(auguryAction)
            .playbook(null)
            .build();
    public static final MoveAction changeHighlightedStatsAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move changeHighlightedStats = Move.builder()
            .id(new ObjectId().toString())
            .name("CHANGE HIGHLIGHTED STATS")
            .description("_**At the beginning of any session**_, or at the end if you forgot, anyone can say, “hey, let’s change highlighted stats.” When someone says it, do it.\n" +
                    "\n" +
                    "Go around the circle again, following the same procedure you used to highlight them in the first place: the high-Hx player highlights one stat, and the MC highlight another.")
            .kind(MoveType.PERIPHERAL)
            .moveAction(changeHighlightedStatsAction)
            .playbook(null)
            .build();

    /* ----------------------------- BATTLE MOVES --------------------------------- */
    public static final MoveAction exchangeHarmAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move exchangeHarm = Move.builder()
            .id(new ObjectId().toString())
            .name("EXCHANGE HARM")
            .description("When you _**exchange harm**_, both sides simultaneously inflict and suffer harm as established:\n" +
                    "\n" +
                    "- *You inflict harm equal to the harm rating of your weapon, minus the armor rating of your enemy’s armor.*\n" +
                    "- *You suffer harm equal to the harm rating of your enemy’s weapon, minus the armor rating of your own armor.*")
            .kind(MoveType.BATTLE)
            .moveAction(exchangeHarmAction)
            .playbook(null)
            .build();
    public static final MoveAction seizeByForceAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move seizeByForce = Move.builder()
            .id(new ObjectId().toString())
            .name("SEIZE BY FORCE")
            .description("To _**seize something by force**_, exchange harm, but first roll+hard.\n" +
                    "\n" +
                    "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                    "\n" +
                    "- *You inflict terrible harm (+1harm).*\n" +
                    "- *You suffer little harm (-1harm).*\n" +
                    "- *You take definite and undeniable control of it.*\n" +
                    "- *You impress, dismay, or frighten your enemy.*")
            .kind(MoveType.BATTLE)
            .moveAction(seizeByForceAction)
            .playbook(null)
            .build();
    public static final MoveAction assaultAPositionAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move assaultAPosition = Move.builder()
            .id(new ObjectId().toString())
            .name("ASSAULT A POSITION")
            .description("To _**assault a secure position**_, exchange harm, but first roll+hard.\n" +
                    "\n" +
                    "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                    "\n" +
                    "- *You inflict terrible harm (+1harm).*\n" +
                    "- *You suffer little harm (-1harm).*\n" +
                    "- *You force your way into your enemy’s position.*\n" +
                    "- *You impress, dismay, or frighten your enemy.*")
            .kind(MoveType.BATTLE)
            .moveAction(assaultAPositionAction)
            .playbook(null)
            .build();
    public static final MoveAction keepHoldOfSomethingAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move keepHoldOfSomething = Move.builder()
            .id(new ObjectId().toString())
            .name("KEEP HOLD OF SOMETHING")
            .description("To _**keep hold of something you have**_, exchange harm, but first roll+hard.\n" +
                    "\n" +
                    "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                    "\n" +
                    "- *You inflict terrible harm (+1harm).*\n" +
                    "- *You suffer little harm (-1harm).*\n" +
                    "- *You keep definite control of it.*\n" +
                    "- *You impress, dismay, or frighten your enemy.*")
            .kind(MoveType.BATTLE)
            .moveAction(keepHoldOfSomethingAction)
            .playbook(null)
            .build();
    public static final MoveAction fightFreeAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move fightFree = Move.builder()
            .id(new ObjectId().toString())
            .name("FIGHT FREE")
            .description("To _**fight your way free**_, exchange harm, but first roll+hard.\n" +
                    "\n" +
                    "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                    "\n" +
                    "- *You inflict terrible harm (+1harm).*\n" +
                    "- *You suffer little harm (-1harm).*\n" +
                    "- *You win free and get away.*\n" +
                    "- *You impress, dismay, or frighten your enemy.*")
            .kind(MoveType.BATTLE)
            .moveAction(fightFreeAction)
            .playbook(null)
            .build();
    public static final MoveAction defendSomeoneAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move defendSomeone = Move.builder()
            .id(new ObjectId().toString())
            .name("DEFEND SOMEONE")
            .description("To _**defend someone else from attack**_, exchange harm, but first roll+hard.\n" +
                    "\n" +
                    "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                    "\n" +
                    "- *You inflict terrible harm (+1harm).*\n" +
                    "- *You suffer little harm (-1harm).*\n" +
                    "- *You protect them from harm.*\n" +
                    "- *You impress, dismay, or frighten your enemy.*")
            .kind(MoveType.BATTLE)
            .moveAction(defendSomeoneAction)
            .playbook(null)
            .build();
    public static final MoveAction doSingleCombatAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move doSingleCombat = Move.builder()
            .id(new ObjectId().toString())
            .name("DO SINGLE COMBAT")
            .description("When you _**do single combat with someone**_, no quarters, exchange harm, but first roll+hard.\n" +
                    "\n" +
                    "On a 10+, both. On a 7–9, choose 1. On a miss, your opponent chooses 1 against you:\n" +
                    "\n" +
                    "- *You inflict terrible harm (+1harm).*\n" +
                    "- *You suffer little harm (-1harm).*\n" +
                    "\n" +
                    "After you exchange harm, do you prefer to end the fight now, or fight on? If both of you prefer to end the fight now, it ends. If both of you prefer to fight on, it continues, and you must make the move again.\n" +
                    "\n" +
                    "If one of you prefers to end the fight, though, and the other prefers to fight on, then the former must choose: flee, submit to the latter‘s mercy, or fight on after all.")
            .kind(MoveType.BATTLE)
            .moveAction(doSingleCombatAction)
            .playbook(null)
            .build();
    public static final MoveAction layDownFireAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move layDownFire = Move.builder()
            .id(new ObjectId().toString())
            .name("LAY DOWN FIRE")
            .description("When you _**lay down fire**_, roll+hard.\n" +
                    "\n" +
                    "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                    "\n" +
                    "- *You provide covering fire, allowing another character to move or act freely.*\n" +
                    "- *You provide supporting fire, giving another PC +1choice to their own battle move.*\n" +
                    "- *You provide suppressing fire, denying another character to move or act freely. (If a PC, they may still act under fire.)*\n" +
                    "- *You take an opportune shot, inflicting harm (but -1harm) on an enemy within your reach.*")
            .kind(MoveType.BATTLE)
            .moveAction(layDownFireAction)
            .playbook(null)
            .build();
    public static final MoveAction standOverwatchAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(COOL)
            .build();
    public static final Move standOverwatch = Move.builder()
            .id(new ObjectId().toString())
            .name("STAND OVERWATCH")
            .description("When you _**stand overwatch**_ for an ally, roll+cool.\n" +
                    "\n" +
                    "On a hit, if anyone attacks or interferes with your ally, you attack them and inflict harm as established, as well as warning your ally.\n" +
                    "\n" +
                    "On a 10+, choose 1:\n" +
                    "\n" +
                    "- *...And you inflict your harm before they can carry out their attack or interference.*\n" +
                    "- *...And you inflict terrible harm (+1harm).*\n" +
                    "\n" +
                    "On a miss, you are able to warn your ally but not attack your enemy.")
            .kind(MoveType.BATTLE)
            .moveAction(standOverwatchAction)
            .playbook(null)
            .build();
    public static final MoveAction keepAnEyeOutAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(SHARP)
            .build();
    public static final Move keepAnEyeOut = Move.builder()
            .id(new ObjectId().toString())
            .name(keepEyeOutName)
            .description("When you _**keep an eye out**_ for what’s coming, roll+sharp.\n" +
                    "\n" +
                    "On a 10+, hold 3. On a 7–9, hold 2. On a miss, hold 1. During the battle, spend your hold, 1 for 1, to ask the MC what’s coming and choose 1:\n" +
                    "\n" +
                    "- *Direct a PC ally’s attention to an enemy. If they make a battle move against that enemy, they get +1choice to their move.*\n" +
                    "- *Give a PC ally an order, instruction, or suggestion. If they do it, they get +1 to any rolls they make in the effort.*\n" +
                    "- *Direct any ally’s attention to an enemy. If they attack that enemy, they inflict +1harm.*\n" +
                    "- *Direct any ally’s attention to a danger. They take -1harm from that danger.*")
            .kind(MoveType.BATTLE)
            .moveAction(keepAnEyeOutAction)
            .playbook(null)
            .build();
    public static final MoveAction beTheBaitAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(COOL)
            .build();
    public static final Move beTheBait = Move.builder()
            .id(new ObjectId().toString())
            .name("BE THE BAIT")
            .description("When _**you’re the bait**_, roll+cool.\n" +
                    "\n" +
                    "On a 10+, choose 2. On a 7–9, choose 1:\n" +
                    "\n" +
                    "- *You draw your prey all the way into the trap. Otherwise, they only approach.*\n" +
                    "- *Your prey doesn’t suspect you. Otherwise, they’re wary and alert.*\n" +
                    "- *You don’t expose yourself to extra risk. Otherwise, any harm your prey inflicts is +1.*\n" +
                    "\n" +
                    "On a miss, the MC chooses 1 for you.")
            .kind(MoveType.BATTLE)
            .moveAction(beTheBaitAction)
            .playbook(null)
            .build();
    public static final MoveAction beTheCatAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(COOL)
            .build();
    public static final Move beTheCat = Move.builder()
            .id(new ObjectId().toString())
            .name("BE THE CAT")
            .description("When _**you’re the cat**_, roll+cool. On a hit, you catch your prey out.\n" +
                    "\n" +
                    "On a 10+, you’ve driven them first to a place of your choosing; say where.\n" +
                    "\n" +
                    "On a 7–9, you’ve had to follow them where they wanted to go; they say where.\n" +
                    "\n" +
                    "On a miss, your prey escapes you.")
            .kind(MoveType.BATTLE)
            .moveAction(beTheCatAction)
            .playbook(null)
            .build();
    public static final MoveAction beTheMouseAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(COOL)
            .build();
    public static final Move beTheMouse = Move.builder()
            .id(new ObjectId().toString())
            .name("BE THE MOUSE")
            .description("When _**you’re the mouse**_, roll+cool.\n" +
                    "\n" +
                    "On a 10+, you escape clean and leave your hunter hunting.\n" +
                    "\n" +
                    "On a 7–9, your hunter catches you out, but only after you’ve led them to a place of your choosing; say where.\n" +
                    "\n" +
                    "On a miss, your hunter catches you out and the MC says where.")
            .kind(MoveType.BATTLE)
            .moveAction(beTheMouseAction)
            .playbook(null)
            .build();
    public static final MoveAction catOrMouseAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(SHARP)
            .build();
    public static final Move catOrMouseMove = Move.builder()
            .id(new ObjectId().toString())
            .name(catOrMouseName)
            .description("When _**it’s not certain whether you’re the cat or the mouse**_, roll+sharp. On a hit, you decide which you are.\n" +
                    "\n" +
                    "On a 10+, you take +1forward as well.\n" +
                    "\n" +
                    "On a miss, you’re the mouse.")
            .kind(MoveType.BATTLE)
            .moveAction(catOrMouseAction)
            .playbook(null)
            .build();

    /* ----------------------------- ROAD WAR MOVES --------------------------------- */

    public static final MoveAction boardVehicleAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.SPEED)
            .statToRollWith(COOL)
            .build();
    public static final Move boardAMovingVehicleMove = Move.builder()
            .id(new ObjectId().toString())
            .name(boardVehicleName)
            .description("To _**board a moving vehicle**_, roll+cool, minus its speed. To board one moving vehicle from another, roll+cool, minus the difference between their speeds.\n" +
                    "\n" +
                    "On a 10+, you’re on and you made it look easy. Take +1forward.\n" +
                    "\n" +
                    "On a 7–9, you’re on, but jesus.\n" +
                    "\n" +
                    "On a miss, the MC chooses: you’re hanging on for dear life, or you’re down and good luck to you.")
            .kind(MoveType.ROAD_WAR)
            .moveAction(boardVehicleAction)
            .playbook(null)
            .build();
    public static final MoveAction outdistanceVehicleAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.SPEED)
            .statToRollWith(COOL)
            .build();
    public static final Move outdistanceAnotherVehicleMove = Move.builder()
            .id(new ObjectId().toString())
            .name(outdistanceVehicleName)
            .description("When you try to outdistance another vehicle, roll+cool, modified by the vehicles’ relative speed.\n" +
                    "\n" +
                    "On a 10+, you outdistance them and break away.\n" +
                    "\n" +
                    "On a 7–9, choose 1:\n" +
                    "\n" +
                    "- *You outdistance them and break away, but your vehicle suffers 1-harm ap from the strain.*\n" +
                    "- *You don’t escape them, but you can go to ground in a place you choose.*\n" +
                    "- *They overtake you, but their vehicle suffers 1-harm ap from the strain.*\n" +
                    "\n" +
                    "On a miss, your counterpart chooses 1 against you.")
            .kind(MoveType.ROAD_WAR)
            .moveAction(outdistanceVehicleAction)
            .playbook(null)
            .build();
    public static final MoveAction overtakeVehicleAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.SPEED)
            .statToRollWith(COOL)
            .build();
    public static final Move overtakeAnotherVehicleMove = Move.builder()
            .id(new ObjectId().toString())
            .name(overtakeVehicleName)
            .description("When you _**try to overtake another vehicle**_, roll+cool, modified by the vehicles’ relative speed.\n" +
                    "\n" +
                    "On a 10+, you overtake them and draw alongside.\n" +
                    "\n" +
                    "On a 7–9, choose 1:\n" +
                    "\n" +
                    "- *You overtake them, but your vehicle suffers 1-harm ap the same.*\n" +
                    "- *You don’t overtake them, but you can drive them into a place you choose.*\n" +
                    "- *They outdistance you, but their vehicle suffers 1-harm ap the same.*\n" +
                    "\n" +
                    "On a miss, your counterpart chooses 1 against you.")
            .kind(MoveType.ROAD_WAR)
            .moveAction(overtakeVehicleAction)
            .playbook(null)
            .build();
    public static final MoveAction dealWithTerrainAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.HANDLING)
            .statToRollWith(COOL)
            .build();
    public static final Move dealWithBadTerrain = Move.builder()
            .id(new ObjectId().toString())
            .name(dealWithTerrainName)
            .description("When you have to _**deal with bad terrain**_, roll+cool, plus your vehicle’s handling.\n" +
                    "\n" +
                    "On a 10+, you fly through untouched.\n" +
                    "\n" +
                    "On a 7–9, choose 1:\n" +
                    "\n" +
                    "- *You slow down and pick your way forward.*\n" +
                    "- *You push too hard and your vehicle suffers harm as established.*\n" +
                    "- *You ditch out and go back or try to find another way.*\n" +
                    "\n" +
                    "On a miss, the MC chooses 1 for you; the others are impossible.")
            .kind(MoveType.ROAD_WAR)
            .moveAction(dealWithTerrainAction)
            .playbook(null)
            .build();
    public static final MoveAction shoulderAnotherVehicleAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(COOL)
            .build();
    public static final Move shoulderAnotherVehicle = Move.builder()
            .id(new ObjectId().toString())
            .name("SHOULDER ANOTHER VEHICLE")
            .description("To _**shoulder another vehicle**_, roll+cool. On a hit, you shoulder it aside, inflicting v-harm as established.\n" +
                    "\n" +
                    "On a 10+, you inflict v-harm+1.\n" +
                    "\n" +
                    "On a miss, it shoulders you instead, inflicting v-harm as established.")
            .kind(MoveType.ROAD_WAR)
            .moveAction(shoulderAnotherVehicleAction)
            .playbook(null)
            .build();

    /* ----------------------------- ANGEL MOVES --------------------------------- */

    public static final RollModifier sixthSenseMod = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(openBrain))
            .statToRollWith(StatType.SHARP).build();
    public static final RollModifier profCompassionMod = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(helpOrInterfere))
            .statToRollWith(StatType.SHARP).build();
    public static final MoveAction angelSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ADJUST_HX)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move angelSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name(angelSpecialName)
            .description("If you and another character have sex, your Hx with them on your sheet goes immediately to +3, and they immediately get +1 to their Hx with you on their sheet.\n" +
                    "\n" +
                    "If that brings their Hx with you to +4, they reset it to +1 instead, as usual, and so mark experience.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(angelSpecialAction)
            .playbook(PlaybookType.ANGEL)
            .build();
    public static final Move sixthSense = Move.builder()
            .id(new ObjectId().toString())
            .name("SIXTH SENSE")
            .description("_**Sixth sense**_: when you open your brain to the world’s psychic maelstrom, roll+sharp instead of +weird.")
            .rollModifier(sixthSenseMod)
            .kind(MoveType.CHARACTER)
            .playbook(PlaybookType.ANGEL).build();
    public static final Move infirmary = Move.builder()
            .id(new ObjectId().toString())
            .name("INFIRMARY")
            .description("_**Infirmary**_: you get an infirmary, a workspace with life support, a drug lab and a crew of 2 (Shigusa & Mox, maybe).\n" +
                    "\n" +
                    "Get patients into it and you can work on them like a savvyhead on tech (_cf_).")
            .kind(MoveType.CHARACTER)
            .stat(null)
            .playbook(PlaybookType.ANGEL)
            .build();
    public static final Move profCompassion = Move.builder()
            .id(new ObjectId().toString())
            .name("PROFESSIONAL COMPASSION")
            .description("_**Professional compassion**_: you can roll+sharp instead of roll+Hx when you help someone who’s rolling.")
            .rollModifier(profCompassionMod)
            .kind(MoveType.CHARACTER)
            .playbook(PlaybookType.ANGEL).build();
    public static final MoveAction battlefieldGraceAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move battlefieldGrace = Move.builder()
            .id(new ObjectId().toString())
            .name("BATTLEFIELD GRACE")
            .description("_**Battlefield grace**_: while you are caring for people, not fighting, you get +1armor.")
            .kind(MoveType.CHARACTER)
            .moveAction(battlefieldGraceAction)
            .playbook(PlaybookType.ANGEL)
            .build();
    public static final MoveAction healingTouchAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move healingTouch = Move.builder()
            .id(new ObjectId().toString())
            .name("HEALING TOUCH")
            .description("_**Healing touch**_: when you put your hands skin-to-skin on a wounded person and open your brain to them, roll+weird.\n" +
                    "\n" +
                    "On a 10+, heal 1 segment.\n" +
                    "\n" +
                    "On a 7–9, heal 1 segment, but you’re also opening your brain, so roll that move next.\n" +
                    "\n" +
                    "On a miss: first, you don’t heal them. Second, you’ve opened both your brain and theirs to the world’s psychic maelstrom, without protection or preparation.\n" +
                    "\n" +
                    "For you, and for your patient if your patient’s a fellow player’s character, treat it as though you’ve made that move and missed the roll.\n" +
                    "\n" +
                    "For patients belonging to the MC, their experience and fate are up to the MC.\n")
            .kind(MoveType.CHARACTER)
            .moveAction(healingTouchAction)
            .playbook(PlaybookType.ANGEL)
            .build();
    public static final MoveAction touchedByDeathAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move touchedByDeath = Move.builder()
            .id(new ObjectId().toString())
            .name("TOUCHED BY DEATH")
            .description("_**Touched by death**_: when someone is unconscious in your care, you can use them for _**augury**_. When someone has died in your care, you can use their body for _**augury**_.")
            .kind(MoveType.CHARACTER)
            .moveAction(touchedByDeathAction)
            .playbook(PlaybookType.ANGEL)
            .build();

    /* ----------------------------- ANGEL KIT MOVES --------------------------------- */

    public static final MoveAction stabilizeAndHealAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STOCK)
            .statToRollWith(null)
            .build();
    public static final Move stabilizeAndHeal = Move.builder()
            .id(new ObjectId().toString())
            .name(stabilizeAndHealName).description("_**stabilize and heal someone at 9:00 or past**_: roll+stock spent.\n" +
                    "\n" +
                    "On a hit, they stabilize and heal to 6:00, and choose 2 (on a 10+) or 1 (on a 7–9):\n" +
                    "\n" +
                    "- *They fight you and you have to narcostab them. How long will they be out?*\n" +
                    "- *The pain and drugs make them babble the truth to you. Ask them what secret they spill.*\n" +
                    "- *They respond very well to treatment. Recover 1 of the stock you spent, if you spent any.*\n" +
                    "- *They’re at your complete mercy. What do you do to them?*\n" +
                    "- *Their course of recovery teaches you something about your craft. Mark experience.*\n" +
                    "- *They owe you for your time, attention, and supplies, and you’re going to hold them to it.*\n" +
                    "\n" +
                    "On a miss, they take 1-harm instead.")
            .kind(MoveType.UNIQUE)
            .moveAction(stabilizeAndHealAction)
            .playbook(PlaybookType.ANGEL).build();
    public static final MoveAction speedTheRecoveryOfSomeoneAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.STOCK)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move speedTheRecoveryOfSomeone = Move.builder()
            .id(new ObjectId().toString())
            .name(speedRecoveryName)
            .description("_**speed the recovery of someone at 3:00 or 6:00**_: don’t roll. They choose: you spend 1-stock and they spend 4 days (3:00) or 1 week (6:00) blissed out on chillstabs, immobile but happy, or else they do their time in agony like everyone else.")
            .kind(MoveType.UNIQUE)
            .moveAction(speedTheRecoveryOfSomeoneAction)
            .playbook(PlaybookType.ANGEL).build();
    public static final MoveAction reviveSomeoneAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.STOCK)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move reviveSomeone = Move.builder()
            .id(new ObjectId().toString())
            .name(reviveSomeoneName).description("_**revive someone whose life has become untenable**_, spend 2-stock.\n" +
                    "\n" +
                    "They come back, but you get to choose how they come back. Choose from the regular “when life is untenable” list, or else choose 1:\n" +
                    "\n" +
                    "- *They come back in your deep, deep debt.*\n" +
                    "- *They come back with a prosthetic (you detail).*\n" +
                    "- *You and they both come back with +1weird (max weird+3).*")
            .kind(MoveType.UNIQUE)
            .moveAction(reviveSomeoneAction)
            .playbook(PlaybookType.ANGEL).build();
    public static final MoveAction treatAnNpcAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.STOCK)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move treatAnNpc = Move.builder()
            .id(new ObjectId().toString())
            .name(treatNpcName)
            .description("_**treat an NPC**_: spend 1-stock. They’re stable now and they’ll recover in time. ")
            .kind(MoveType.UNIQUE)
            .moveAction(treatAnNpcAction)
            .playbook(PlaybookType.ANGEL).build();

    /* ----------------------------- BATTLEBABE MOVES --------------------------------- */
    public static final RollModifier iceColdMod = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(goAggro))
            .statToRollWith(HARD).build();
    public static final MoveAction battlebabeSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move battlebabeSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name("BATTLEBABE SPECIAL")
            .description("If you and another character have sex, nullify the other character’s sex move. Whatever it is, it just doesn’t happen.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(battlebabeSpecialAction)
            .playbook(PlaybookType.BATTLEBABE).build();
    public static final MoveAction dangerousAndSexyAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HOT)
            .build();
    public static final Move dangerousAndSexy = Move.builder()
            .id(new ObjectId().toString())
            .name(dangerousAndSexyName)
            .description("_**Dangerous & sexy**_: when you enter into a charged situation, roll+hot.\n" +
                    "\n" +
                    "On a 10+, hold 2. On a 7–9, hold 1.\n" +
                    "\n" +
                    "Spend your hold 1 for 1 to make eye contact with an NPC present, who freezes or flinches and can’t take action until you break it off.\n" +
                    "\n" +
                    "On a miss, your enemies identify you immediately as their foremost threat.")
            .kind(MoveType.CHARACTER)
            .moveAction(dangerousAndSexyAction)
            .playbook(PlaybookType.BATTLEBABE)
            .build();
    public static final Move iceCold = Move.builder()
            .id(new ObjectId().toString())
            .name("ICE COLD")
            .description("_**Ice cold**_: when you go aggro on an NPC, roll+cool instead of roll+hard. When you go aggro on another player’s character, roll+Hx instead of roll+hard.")
            .rollModifier(iceColdMod)
            .stat(null).kind(MoveType.CHARACTER)
            .playbook(PlaybookType.BATTLEBABE).build();
    public static final MoveAction mercilessAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move merciless = Move.builder()
            .id(new ObjectId().toString())
            .name("MERCILESS")
            .description("_**Merciless**_: when you inflict harm, inflict +1harm.")
            .kind(MoveType.CHARACTER)
            .moveAction(mercilessAction)
            .playbook(PlaybookType.BATTLEBABE).build();
    public static final MoveAction visionsOfDeathAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move visionsOfDeath = Move.builder()
            .id(new ObjectId().toString())
            .name("VISIONS OF DEATH").description("_**Visions of death**_: when you go into battle, roll+weird.\n" +
                    "\n" +
                    "On a 10+, name one person who’ll die and one who’ll live.\n" +
                    "\n" +
                    "On a 7–9, name one person who’ll die OR one person who’ll live. Don’t name a player’s character; name NPCs only.\n" +
                    "\n" +
                    "The MC will make your vision come true, if it’s even remotely possible.\n" +
                    "\n" +
                    "On a miss, you foresee your own death, and accordingly take -1 throughout the battle.")
            .kind(MoveType.CHARACTER)
            .moveAction(visionsOfDeathAction)
            .playbook(PlaybookType.BATTLEBABE).build();
    public static final MoveAction perfectInstinctsAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move perfectInstincts = Move.builder()
            .id(new ObjectId().toString())
            .name("PERFECT INSTINCTS")
            .description("_**Perfect instincts**_: when you’ve read a charged situation and you’re acting on the MC’s answers, take +2 instead of +1.")
            .kind(MoveType.CHARACTER)
            .moveAction(perfectInstinctsAction)
            .playbook(PlaybookType.BATTLEBABE).build();
    public static final MoveAction impossibleReflexesAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move impossibleReflexes = Move.builder()
            .id(new ObjectId().toString())
            .name("IMPOSSIBLE REFLEXES")
            .description("_**Impossible reflexes**_: the way you move unencumbered counts as armor. If you’re naked or nearly naked, 2-armor; if you’re wearing non-armor fashion, 1-armor. If you’re wearing armor, use it instead.")
            .kind(MoveType.CHARACTER)
            .moveAction(impossibleReflexesAction)
            .playbook(PlaybookType.BATTLEBABE).build();

    /* ----------------------------- BRAINER MOVES --------------------------------- */

    public static final RollModifier lustMod = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(seduceOrManip))
            .statToRollWith(StatType.WEIRD).build();
    public static final RollModifier brainReceptivityMod = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(readAPerson))
            .statToRollWith(StatType.WEIRD).build();
    public static final StatModifier attunementMod = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(StatType.WEIRD)
            .modification(1)
            .maxLimit(3)
            .build();

    public static final MoveAction brainerSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move brainerSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name("BRAINER SPECIAL")
            .description("If you and another character have sex, you automatically do a _**deep brain scan**_ on them, whether you have the move or not. Roll+weird as normal.\n" +
                    "\n" +
                    "However, the MC chooses which questions the other character’s player answers.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(brainerSpecialAction)
            .playbook(PlaybookType.BRAINER).build();
    public static final Move unnaturalLust = Move.builder()
            .id(new ObjectId().toString())
            .name("UNNATURAL LUST TRANSFIXION")
            .description("_**Unnatural lust transfixion**_: when you try to seduce someone, roll+weird instead of roll+hot.")
            .kind(MoveType.CHARACTER)
            .stat(null)
            .rollModifier(lustMod)
            .playbook(PlaybookType.BRAINER).build();
    public static final Move brainReceptivity = Move.builder()
            .id(new ObjectId().toString())
            .name("CASUAL BRAIN RECEPTIVITY")
            .description("_**Casual brain receptivity**_: when you read someone, roll+weird instead of roll+sharp. Your victim has to be able to see you, but you don’t have to interact.")
            .kind(MoveType.CHARACTER)
            .stat(null)
            .rollModifier(brainReceptivityMod)
            .playbook(PlaybookType.BRAINER).build();
    public static final Move brainAttunement = Move.builder()
            .id(new ObjectId().toString())
            .name("PRETERNATURAL BRAIN ATTUNEMENT")
            .description("_**Preternatural at-will brain attunement**_: you get +1weird (weird+3).\n")
            .statModifier(attunementMod)
            .kind(MoveType.CHARACTER)
            .stat(null)
            .playbook(PlaybookType.BRAINER).build();
    public static final MoveAction brainScanAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move brainScan = Move.builder()
            .id(new ObjectId().toString())
            .name(brainScanName)
            .description("_**Deep brain scan**_: when you have time and physical intimacy with someone — mutual intimacy like holding them in your arms, or 1-sided intimacy like they’re restrained to a table — you can read them more deeply than normal. Roll+weird.\n" +
                    "\n" +
                    "On a 10+, hold 3. On a 7–9, hold 1. While you’re reading them, spend your hold to ask their player questions, 1 for 1:\n" +
                    "\n" +
                    "- *What was your character’s lowest moment?*\n" +
                    "- *For what does your character crave forgiveness, and of whom?*\n" +
                    "- *What are your character’s secret pains?*\n" +
                    "- *In what ways are your character’s mind and soul vulnerable?*\n" +
                    "\n" +
                    "On a miss, you inflict 1-harm (ap) upon your subject, to no benefit")
            .kind(MoveType.CHARACTER)
            .moveAction(brainScanAction)
            .playbook(PlaybookType.BRAINER).build();
    public static final MoveAction whisperProjectionAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move whisperProjection = Move.builder()
            .id(new ObjectId().toString())
            .name("DIRECT BRAIN WHISPER PROJECTION")
            .description("_**Direct-brain whisper projection**_: you can roll+weird to get the effects of going aggro, without going aggro. Your victim has to be able to see you, but you don’t have to interact.\n" +
                    "\n" +
                    "If your victim forces your hand, your mind counts as a weapon (1-harm ap close loud-optional).")
            .kind(MoveType.CHARACTER)
            .moveAction(whisperProjectionAction)
            .playbook(PlaybookType.BRAINER).build();
    public static final MoveAction puppetStringsAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move puppetStrings = Move.builder()
            .id(new ObjectId().toString())
            .name(puppetStringsName)
            .description("_**In-brain puppet strings**_: when you have time and physical intimacy with someone — again, mutual or 1-sided — you can plant a command inside their mind. Roll+weird.\n" +
                    "\n" +
                    "On a 10+, hold 3. On a 7–9, hold 1.\n" +
                    "\n" +
                    "At your will, no matter the circumstances, you can spend your hold 1 for 1:\n" +
                    "\n" +
                    "- *Inflict 1-harm (ap).*\n" +
                    "- *They take -1 right now.*\n" +
                    "\n" +
                    "If they fulfill your command, that counts for all your remaining hold.\n" +
                    "\n" +
                    "On a miss, you inflict 1-harm (ap) upon your subject, to no benefit.")
            .kind(MoveType.CHARACTER)
            .moveAction(puppetStringsAction)
            .playbook(PlaybookType.BRAINER).build();

    /* ----------------------------- CHOPPER MOVES --------------------------------- */

    public static final MoveAction chopperSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ADJUST_HX)
            .build();
    public static final Move chopperSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name(chopperSpecialName)
            .description("If you and another character have sex, they immediately change their sheet to say Hx+3 with you.\n" +
                    "\n" +
                    "They also choose whether to give you -1 or +1 to your Hx with them, on your sheet.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(chopperSpecialAction)
            .playbook(PlaybookType.CHOPPER).build();
    public static final MoveAction packAlphaAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move packAlpha = Move.builder()
            .id(new ObjectId().toString())
            .name("PACK ALPHA")
            .description("_**Pack alpha**_: when you try to impose your will on your gang, roll+hard.\n" +
                    "\n" +
                    "On a 10+, all 3. On a 7–9, choose 1:\n" +
                    "\n" +
                    "- *They do what you want (otherwise, they refuse)*\n" +
                    "- *They don’t fight back over it (otherwise, they do fight back)*\n" +
                    "- *You don’t have to make an example of one of them (otherwise, you must)*\n" +
                    "\n" +
                    "On a miss, someone in your gang makes a bid, idle or serious, to replace you for alpha.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(packAlphaAction)
            .playbook(PlaybookType.CHOPPER).build();
    public static final MoveAction fuckingThievesAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move fuckingThieves = Move.builder()
            .id(new ObjectId().toString())
            .name("FUCKING THIEVES")
            .description("_**Fucking thieves**_: when you have your gang search their pockets and saddlebags for something, roll+hard. It has to be something small enough to fit.\n" +
                    "\n" +
                    "On a 10+, one of you happens to have just the thing, or close enough.\n" +
                    "\n" +
                    "On a 7–9, one of you happens to have something pretty close, unless what you’re looking for is hi-tech, in which case no dice.\n" +
                    "\n" +
                    "On a miss, one of you used to have just the thing, but it turns out that some asswipe stole it from you.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(fuckingThievesAction)
            .playbook(PlaybookType.CHOPPER).build();

    /* ----------------------------- DRIVER MOVES --------------------------------- */

    public static final RollModifier weatherEyeMod = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(openBrain))
            .statToRollWith(COOL).build();
    public static final MoveAction driverSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(COOL)
            .build();
    public static final Move driverSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name("DRIVER SPECIAL")
            .description("If you and another character have sex, roll+cool.\n" +
                    "\n" +
                    "On a 10+, it’s cool, no big deal.\n" +
                    "\n" +
                    "On a 7–9, give them +1 to their Hx with you on their sheet, but give yourself -1 to your Hx with them on yours.\n" +
                    "\n" +
                    "On a miss, you gotta go: take -1 ongoing, until you prove that it’s not like they own you or nothing.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(driverSpecialAction)
            .playbook(PlaybookType.DRIVER).build();
    public static final MoveAction combatDriverAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move combatDriver = Move.builder()
            .id(new ObjectId().toString())
            .name("COMBAT DRIVER")
            .description("_**Combat driver**_: when you use your vehicle as a weapon, inflict +1harm. When you inflict v-harm, add +1 to your target’s roll. When you suffer v-harm, take -1 to your roll.")
            .kind(MoveType.CHARACTER)
            .moveAction(combatDriverAction)
            .playbook(PlaybookType.DRIVER).build();
    public static final MoveAction eyeOnTheDoorAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(COOL)
            .build();
    public static final Move eyeOnTheDoor = Move.builder()
            .id(new ObjectId().toString())
            .name("EYE ON THE DOOR")
            .description("_**Eye on the door**_: name your escape route and roll+cool.\n" +
                    "\n" +
                    "On a 10+, you’re gone.\n" +
                    "On a 7–9, you can go or stay, but if you go it costs you: leave something behind or take something with you, the MC will tell you what.\n" +
                    "\n" +
                    "On a miss, you’re caught vulnerable, half in and half out.")
            .kind(MoveType.CHARACTER)
            .moveAction(eyeOnTheDoorAction)
            .playbook(PlaybookType.DRIVER).build();
    public static final Move weatherEye = Move.builder()
            .id(new ObjectId().toString())
            .name("WEATHER EYE")
            .description("_**Weather eye**_: when you open your brain to the world’s psychic maelstrom, roll+cool instead of roll+weird.")
            .kind(MoveType.CHARACTER)
            .stat(null)
            .rollModifier(weatherEyeMod)
            .playbook(PlaybookType.DRIVER).build();
    public static final MoveAction reputationAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(COOL)
            .build();
    public static final Move reputationMove = Move.builder()
            .id(new ObjectId().toString())
            .name(reputationName)
            .description("_**Reputation**_: when you meet someone important (your call), roll+cool.\n" +
                    "\n" +
                    "On a hit, they’ve heard of you, and you say what they’ve heard; the MC has them respond accordingly.\n" +
                    "\n" +
                    "On a 10+, you take +1forward for dealing with them as well.\n" +
                    "\n" +
                    "On a miss, they’ve heard of you, but the MC decides what they’ve heard.")
            .kind(MoveType.CHARACTER)
            .moveAction(reputationAction)
            .playbook(PlaybookType.DRIVER).build();
    public static final MoveAction daredevilAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move daredevil = Move.builder()
            .id(new ObjectId().toString())
            .name("DAREDEVIL")
            .description("_**Daredevil**_: if you go straight into danger without hedging your bets, you get +1armor. If you happen to be leading a gang or convoy, it gets +1armor too.")
            .kind(MoveType.CHARACTER)
            .moveAction(daredevilAction)
            .playbook(PlaybookType.DRIVER).build();
    public static final Move collectorMove = Move.builder()
            .id(new ObjectId().toString())
            .name(collectorName)
            .description("_**Collector**_: you get 2 additional cars (you detail).")
            .kind(MoveType.CHARACTER)
            .stat(null)
            .playbook(PlaybookType.DRIVER).build();
    public static final Move myOtherCarIsATank = Move.builder()
            .id(new ObjectId().toString())
            .name(otherCarTankName)
            .description("_**My other car is a tank**_: you get a specialized battle vehicle (detail with the MC).")
            .kind(MoveType.CHARACTER)
            .stat(null)
            .playbook(PlaybookType.DRIVER).build();

    /* ----------------------------- GUNLUGGER MOVES --------------------------------- */

    public static final RollModifier battleHardenedMod = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(doSomethingUnderFire, standOverwatch))
            .statToRollWith(HARD).build();
    public static final RollModifier battlefieldInstinctsMod = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(openBrain))
            .statToRollWith(HARD).build();
    public static final StatModifier insanoMod = StatModifier.builder()
            .statToModify(HARD)
            .modification(1)
            .maxLimit(3)
            .build();

    public static final MoveAction gunluggerSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.GUNLUGGER_SPECIAL)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move gunluggerSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name(gunluggerSpecialName)
            .description("If you and another character have sex, you take +1 forward. At your option, they take +1 forward too.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(gunluggerSpecialAction)
            .stat(null)
            .playbook(PlaybookType.GUNLUGGER).build();
    public static final Move battleHardened = Move.builder()
            .id(new ObjectId().toString())
            .name("BATTLE-HARDENED")
            .description("_**Battle-hardened**_: when you act under fire, or when you stand overwatch, roll+hard instead of roll+cool.")
            .kind(MoveType.CHARACTER)
            .stat(null)
            .rollModifier(battleHardenedMod)
            .playbook(PlaybookType.GUNLUGGER).build();
    public static final MoveAction fuckThisShitAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move fuckThisShit = Move.builder()
            .id(new ObjectId().toString())
            .name("FUCK THIS SHIT")
            .description("_**Fuck this shit**_: name your escape route and roll+hard.\n" +
                    "\n" +
                    "On a 10+, sweet, you’re gone.\n" +
                    "\n" +
                    "On a 7–9, you can go or stay, but if you go it costs you: leave something behind, or take something with you, the MC will tell you what.\n" +
                    "\n" +
                    "On a miss, you’re caught vulnerable, half in and half out.")
            .kind(MoveType.CHARACTER)
            .moveAction(fuckThisShitAction)
            .playbook(PlaybookType.GUNLUGGER).build();
    public static final Move battlefieldInstincts = Move.builder()
            .id(new ObjectId().toString())
            .name("BATTLEFIELD INSTINCTS").description("_**Battlefield instincts**_: when you open your brain to the world’s psychic maelstrom, roll+hard instead of roll+weird, but only in battle.")
            .rollModifier(battlefieldInstinctsMod)
            .kind(MoveType.CHARACTER)
            .stat(null)
            .playbook(PlaybookType.GUNLUGGER).build();
    public static final Move insanoLikeDrano = Move.builder()
            .id(new ObjectId().toString())
            .name("INSANO LIKE DRANO")
            .description("_**Insano like Drano**_: you get +1hard (hard+3).")
            .kind(MoveType.CHARACTER)
            .stat(null)
            .statModifier(insanoMod)
            .playbook(PlaybookType.GUNLUGGER).build();
    public static final Move preparedForTheInevitable = Move.builder()
            .id(new ObjectId().toString())
            .name(preparedForTheInevitableName)
            .description("_**Prepared for the inevitable**_: you have a well-stocked and high-quality first aid kit. It counts as an angel kit (cf ) with a capacity of 2-stock.")
            .kind(MoveType.CHARACTER)
            .stat(null)
            .playbook(PlaybookType.GUNLUGGER).build();
    public static final MoveAction bloodcrazedAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move bloodcrazed = Move.builder()
            .name("BLOODCRAZED")
            .description("_**Bloodcrazed**_: whenever you inflict harm, inflict +1harm.")
            .kind(MoveType.CHARACTER)
            .moveAction(bloodcrazedAction)
            .playbook(PlaybookType.GUNLUGGER).build();
    public static final MoveAction notToBeFuckedWithAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move notToBeFuckedWith = Move.builder()
            .id(new ObjectId().toString())
            .name("NOT TO BE FUCKED WITH")
            .description("_**NOT TO BE FUCKED WITH**_: in battle, you count as a small gang, with harm and armor according to your gear.")
            .kind(MoveType.CHARACTER)
            .moveAction(notToBeFuckedWithAction)
            .playbook(PlaybookType.GUNLUGGER).build();

    /* ----------------------------- HARDHOLDER MOVES --------------------------------- */

    public static final MoveAction hardholderSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move hardholderSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name(hardholderSpecialName)
            .description("If you and another character have sex, you can give the other character gifts worth 1-barter, at no cost to you.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(hardholderSpecialAction)
            .playbook(PlaybookType.HARDHOLDER).build();
    public static final MoveAction leadershipAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move leadership = Move.builder()
            .id(new ObjectId().toString())
            .name(leadershipName)
            .description("_**Leadership**_: when you have to order your gang to advance, regroup, hold position, hold discipline, or put their damn backs into it, roll+hard.\n" +
                    "\n" +
                    "On a hit, they do it.\n" +
                    "\n" +
                    "On a 10+, they snap to; take +1forward.\n" +
                    "\n" +
                    "On a miss, they do it, but you'll hear about it later."
            )
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(leadershipAction)
            .playbook(PlaybookType.HARDHOLDER).build();
    public static final MoveAction wealthAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    public static final Move wealth = Move.builder()
            .id(new ObjectId().toString())
            .name(wealthName)
            .description("_**Wealth**_: if your hold is secure and your rule unchallenged, at the beginning of the session, roll+hard.\n" +
                    "\n" +
                    "On a 10+, you have surplus on hand and available for the needs of the session.\n" +
                    "\n" +
                    "On a 7-9, you have surplus, but choose 1 want.\n" +
                    "\n" +
                    "On a miss, or if your hold is compromised or your rule contested, your hold is in want.\n" +
                    "\n" +
                    "The precise values of your surplus and want depend on your holding."
            )
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(wealthAction)
            .playbook(PlaybookType.HARDHOLDER).build();
    /* ----------------------------- HOCUS MOVES --------------------------------- */

    public static final MoveAction hocusSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.HOCUS_SPECIAL)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move hocusSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name(hocusSpecialName)
            .description("If you and another character have sex, you each get 1 hold. Either of you can spend your hold any time to help or interfere with the other, at a distance or despite any barriers that would normally prevent it.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(hocusSpecialAction)
            .playbook(PlaybookType.HOCUS).build();
    public static final MoveAction fortunesAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.FORTUNE)
            .build();
    public static final Move fortunes = Move.builder()
            .id(new ObjectId().toString())
            .name(fortunesName)
            .description("_**Fortunes**: fortune, surplus and want all depend on your followers._ At the beginning of the session, roll+fortune.\n" +
                    "\n" +
                    "On a 10+, your followers have surplus.\n" +
                    "\n" +
                    "On a 7-9, they have surplus, but choose one want.\n" +
                    "\n" +
                    "On a miss, they are in want.\n" +
                    "\n" +
                    "If their surplus lists barter, like 1-barter or 2-barter, that's your personal share, to spend on your lifestyle or for what you will.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(fortunesAction)
            .playbook(PlaybookType.HOCUS).build();
    public static final MoveAction frenzyAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move frenzy = Move.builder()
            .id(new ObjectId().toString())
            .name("FRENZY")
            .description("_**Frenzy**_: When you speak the truth to a mob, roll+weird.\n" +
                    "\n" +
                    "On a 10+, hold 3.\n" +
                    "\n" +
                    "On a 7-9, hold 1. Spend your hold 1 for 1 to make the mob:\n" +
                    "\n" +
                    "Spend your hold 1 for 1 to make the mob:\n" +
                    "\n" +
                    "- *Bring people forward and deliver them.*\n" +
                    "- *Bring forward all their precious things.*\n" +
                    "- *Unite and fight for you as a gang (2-harm 0-armor size appropriate).*\n" +
                    "- *Fall into an orgy of uninhibited emotion: fucking, lamenting, fighting, sharing, celebrating, as you choose.*\n" +
                    "- *Go quietly back to their lives.*\n" +
                    "\n" +
                    "On a miss, the mob turns on you.")
            .kind(MoveType.CHARACTER)
            .moveAction(frenzyAction)
            .playbook(PlaybookType.HOCUS).build();
    public static final RollModifier charismaticModifier = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(seduceOrManip))
            .statToRollWith(WEIRD).build();
    public static final Move charismatic = Move.builder()
            .id(new ObjectId().toString())
            .name("CHARISMATIC")
            .description("_**Charismatic**_: when you try to manipulate someone, roll+weird instead of roll+hot")
            .kind(MoveType.CHARACTER)
            .rollModifier(charismaticModifier)
            .playbook(PlaybookType.HOCUS).build();
    public static final StatModifier wacknutModifier = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(WEIRD)
            .modification(1)
            .maxLimit(3)
            .build();

    public static final Move fuckingWacknut = Move.builder()
            .id(new ObjectId().toString())
            .name("FUCKING WACKNUT")
            .description("_**Fucking wacknut**_: you get +1weird (weird+3)")
            .kind(MoveType.CHARACTER)
            .statModifier(wacknutModifier)
            .playbook(PlaybookType.HOCUS).build();
    public static final RollModifier seeingSoulsModifier = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(helpOrInterfere))
            .statToRollWith(WEIRD).build();
    public static final Move seeingSouls = Move.builder()
            .id(new ObjectId().toString())
            .name("SEEING SOULS")
            .description("_**Seeing souls**_: when you help or interfere with someone, roll+weird instead of roll+Hx")
            .kind(MoveType.CHARACTER)
            .rollModifier(seeingSoulsModifier)
            .playbook(PlaybookType.HOCUS).build();
    public static final MoveAction divineProtectionAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move divineProtection = Move.builder()
            .id(new ObjectId().toString())
            .name("DIVINE PROTECTION")
            .description("_**Divine protection**_: your gods give you 1-armor. If you wear armor, use that instead, they don't add.")
            .kind(MoveType.CHARACTER)
            .moveAction(divineProtectionAction)
            .playbook(PlaybookType.HOCUS).build();

    /* ----------------------------- MAESTRO D' MOVES --------------------------------- */
    public static final MoveAction maestroSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .build();
    public static final Move maestroSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name(maestroDSpecialName)
            .description("If you hook up another character up - with sex, with food, with somethin somethin, whatever - it counts as having sex with them.")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(maestroSpecialAction)
            .playbook(PlaybookType.MAESTRO_D).build();
    public static final RollModifier callThisHotModifier = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(doSomethingUnderFire))
            .statToRollWith(HOT).build();
    public static final Move callThisHot = Move.builder()
            .id(new ObjectId().toString())
            .name("YOU CALL THIS HOT?")
            .description("_**You call this hot?**_: when you do something under fire, roll+hot instead of roll+cool.")
            .kind(MoveType.CHARACTER)
            .rollModifier(callThisHotModifier)
            .playbook(PlaybookType.MAESTRO_D).build();
    public static final RollModifier devilWithBladeModifier = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(goAggro))
            .statToRollWith(HOT).build();
    public static final Move devilWithBlade = Move.builder()
            .id(new ObjectId().toString())
            .name("A DEVIL WITH A BLADE")
            .description("_**A devil with a blade**_: when you use a blade to go aggro, roll+hot instead of roll+hard.")
            .kind(MoveType.CHARACTER)
            .rollModifier(devilWithBladeModifier)
            .playbook(PlaybookType.MAESTRO_D).build();
    public static final MoveAction fingersInPieAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HOT)
            .build();
    public static final Move fingersInPie = Move.builder()
            .id(new ObjectId().toString())
            .name("FINGERS IN EVERY PIE")
            .description("_**Fingers in every pie**_: put out the word that you want a thing - could be a person, could be somethin somethin, could even be just a thing - and roll+hot.\n" +
                    "\n" +
                    "On a 10+, it shows up in your establishment for you, like magic.\n" +
                    "\n" +
                    "On a 7-9, well, your people made an effort and everybody wants to please you and close is close, right?\n" +
                    "\n" +
                    "On a miss, it shows up in your establishment for you with wicked strings attached.")
            .kind(MoveType.CHARACTER)
            .moveAction(fingersInPieAction)
            .playbook(PlaybookType.MAESTRO_D).build();
    public static final MoveAction everybodyEatsAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HOT)
            .build();
    public static final Move everyBodyEats = Move.builder()
            .id(new ObjectId().toString())
            .name("EVERYBODY EATS, EVEN THAT GUY")
            .description("_**Everybody eats, even that guy**_: when you want to know something about someone important (your call), roll+hot.\n" +
                    "\n" +
                    "On a hit, you can ask the MC questions.\n" +
                    "\n" +
                    "On a 10+, ask 3. On a 7-9, ask 1:\n" +
                    "\n" +
                    "- *How are they doing? What's up with them?.*\n" +
                    "- *Who do they know, like and/or trust?.*\n" +
                    "- *How could I get to them, physically or emotionally?.*\n" +
                    "- *What or who do they love best?*\n" +
                    "- *When next should I expect to see them?.*\n" +
                    "\n" +
                    "On a miss, ask 1 anyway, but they hear about your interest in them.")
            .kind(MoveType.CHARACTER)
            .moveAction(everybodyEatsAction)
            .playbook(PlaybookType.MAESTRO_D).build();
    public static final MoveAction justGiveMotiveAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.CHOICE)
            .build();
    public static final Move justGiveMotive = Move.builder()
            .id(new ObjectId().toString())
            .name(justGiveMotiveName)
            .description("_**Just give me a motive**_: name somebody who might conceivably eat, drink, or otherwise ingest something you've touched. If it's an NPC, roll+hard; a PC, roll+Hx.\n" +
                    "\n" +
                    "On a 10+, they do, and suffer 4-harm (ap) sometime during the next 24 hours.\n" +
                    "\n" +
                    "On a 7-9, it's 2-harm (ap)\n" +
                    "\n" +
                    "On a miss, some several people of the MC's choice, maybe including your guy, maybe not, get it, and all suffer 3-harm (ap).")
            .kind(MoveType.CHARACTER)
            .moveAction(justGiveMotiveAction)
            .playbook(PlaybookType.MAESTRO_D).build();

    /* ----------------------------- SAVVYHEAD MOVES --------------------------------- */

    public static final MoveAction savvyheadSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .build();
    public static final Move savvyheadSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name(savvyheadSpecialName)
            .description("If you and another character have sex, they automatically speak to you, as though they were a thing and you'd rolled a 10+, whether you have the move or not. The other player and the MC will answer your questions between them.\n" +
                    "\n" +
                    "Otherwise, that move never works on people, only things."
            )
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(savvyheadSpecialAction)
            .playbook(PlaybookType.SAVVYHEAD).build();

    public static final MoveAction thingsSpeakAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move thingsSpeak = Move.builder()
            .id(new ObjectId().toString())
            .name("THINGS SPEAK")
            .description("_**Things speak**_: whenever you handle or examine something interesting, roll+weird.\n" +
                    "\n" +
                    "On a hit, you can ask the MC questions.\n" +
                    "\n" +
                    "On a 10+, ask 3. On a 7-9, ask 1:\n" +
                    "\n" +
                    "- *Who handled this last before me?.*\n" +
                    "- *Who made this?.*\n" +
                    "- *What string emotions have been recently nearby this?.*\n" +
                    "- *What has been done most recently with this, or to this?*\n" +
                    "- *What's wrong with this, and how might I fix it?.*\n" +
                    "\n" +
                    "Treat a miss as though you've opened your brain to the world's psychic maelstrom and missed the roll.")
            .kind(MoveType.CHARACTER)
            .moveAction(thingsSpeakAction)
            .playbook(PlaybookType.SAVVYHEAD).build();

    public static final MoveAction bonefeelAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move bonefeel = Move.builder()
            .id(new ObjectId().toString())
            .name(bonefeelName)
            .description("_**Bonefeel**_: at the beginning of the session, roll+weird.\n" +
                    "\n" +
                    "On a 10+, hold 1+1. On a 7-9, hold 1.\n" +
                    "\n" +
                    "At any time, either you or the MC can spend your hold to have you already be there, with the proper tools and knowledge, with or without any clear explanation why.\n" +
                    "\n" +
                    "If your hold was 1+1, take +1forward now.\n" +
                    "\n" +
                    "On a miss, the MC holds 1, and can spend it to have you be there already, but somehow pinned, caught or trapped.")
            .kind(MoveType.CHARACTER)
            .moveAction(bonefeelAction)
            .playbook(PlaybookType.SAVVYHEAD).build();

    public static final MoveAction oftenerRightAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .build();
    public static final Move oftenerRight = Move.builder()
            .id(new ObjectId().toString())
            .name("OFTENER RIGHT")
            .description("_**Oftener right**_: when a character comes to you for advice, tell them what you honestly think the best course is. If they do it, they take +1 to any rolls they make in the doing, and you mark an experience circle.")
            .kind(MoveType.CHARACTER)
            .moveAction(oftenerRightAction)
            .playbook(PlaybookType.SAVVYHEAD).build();

    public static final MoveAction frayingEdgeAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .build();
    public static final Move frayingEdge = Move.builder()
            .id(new ObjectId().toString())
            .name("REALITY'S FRAYING EDGE")
            .description("_**Reality's fraying edge**_: some component of your workspace, or some arrangement of components, is uniquely receptive to the world's psychic maelstrom (+augury)\n" +
                    "\n" +
                    "Choose and name it, or else leave it for the MC to reveal during play.")
            .kind(MoveType.CHARACTER)
            .moveAction(frayingEdgeAction)
            .playbook(PlaybookType.SAVVYHEAD).build();

    public static final RollModifier spookyIntenseModifier = RollModifier.builder()
            .id(new ObjectId().toString())
            .movesToModify(List.of(doSomethingUnderFire, standOverwatch, beTheBait))
            .statToRollWith(WEIRD).build();
    public static final Move spookyIntense = Move.builder()
            .id(new ObjectId().toString())
            .name("SPOOKY INTENSE")
            .description("_**Spooky intense**_: when you do something under fire, stand overwatch, or bait a trap, roll+weird instead of roll+cool.")
            .kind(MoveType.CHARACTER)
            .rollModifier(spookyIntenseModifier)
            .playbook(PlaybookType.SAVVYHEAD).build();

    public static final StatModifier deepInsightsModifier = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(WEIRD)
            .modification(1)
            .maxLimit(3)
            .build();

    public static final Move deepInsights = Move.builder()
            .id(new ObjectId().toString())
            .name("DEEP INSIGHTS")
            .description("_**Deep insights**_: you get +1weird (weird+3)")
            .kind(MoveType.CHARACTER)
            .statModifier(deepInsightsModifier)
            .playbook(PlaybookType.SAVVYHEAD).build();

    /* ----------------------------- SKINNER MOVES --------------------------------- */
    public static final MoveAction skinnerSpecialAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.SKINNER_SPECIAL)
            .build();
    public static final Move skinnerSpecial = Move.builder()
            .id(new ObjectId().toString())
            .name(skinnerSpecialName)
            .description("If you and another character have sex, choose one:\n" +
                    "\n" +
                    "- You take +1forward, and so do they.\n" +
                    "- You take +1forward; they take -1.\n" +
                    "- They must give you a gift worth at least 1-barter.\n" +
                    "- You can _**hypnotize**_ as though you'd rolled a 10+, even if you haven't chosen to get the move.\n" +
                    "\n")
            .kind(MoveType.DEFAULT_CHARACTER)
            .moveAction(skinnerSpecialAction)
            .playbook(PlaybookType.SKINNER).build();

    public static final StatModifier breathtakingModifier = StatModifier.builder()
            .id(new ObjectId().toString())
            .statToModify(HOT)
            .modification(1)
            .maxLimit(3).build();

    public static final Move breathtaking = Move.builder()
            .id(new ObjectId().toString())
            .name("BREATHTAKING")
            .description("_**Breathtaking**_: you get +1hot (hot+3).")
            .kind(MoveType.CHARACTER)
            .statModifier(breathtakingModifier)
            .playbook(PlaybookType.SKINNER).build();
    public static final MoveAction lostAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(WEIRD)
            .build();
    public static final Move lost = Move.builder()
            .id(new ObjectId().toString())
            .name(lostName)
            .description("_**Lost**_: when you whisper someone's name to the world's psychic maelstrom, roll+weird.\n" +
                    "\n" +
                    "On a hit, they come to you, with or without any clear explanation why.\n" +
                    "\n" +
                    "On a 10+, take +1forward against them.\n" +
                    "\n" +
                    "On a miss, the MC will ask you 3 questions; answer them truthfully."
            )
            .kind(MoveType.CHARACTER)
            .moveAction(lostAction)
            .playbook(PlaybookType.SKINNER).build();
    public static final MoveAction artfulAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HOT)
            .build();
    public static final Move artful = Move.builder()
            .id(new ObjectId().toString())
            .name(artfulName)
            .description("_**Artful & gracious**_: when you perform your chosen art - any act of expression or culture - or when you put its product before an audience, roll+hot.\n" +
                    "\n" +
                    "On a hit, spend 3. On a 7-9, spend 1.\n" +
                    "\n" +
                    "Spend 1 to name an NPC member of your audience and choose one:\n" +
                    "\n" +
                    "- *This person must meet me.*\n" +
                    "- *This person must have my services.*\n" +
                    "- *This person loves me.*\n" +
                    "- *This person must give me a gift.*\n" +
                    "- *This person admires my patron.*\n" +
                    "\n" +
                    "On a miss, you gain no benefit, but suffer ho harm of lost opportunity. You simply perform very well.")
            .kind(MoveType.CHARACTER)
            .moveAction(artfulAction)
            .playbook(PlaybookType.SKINNER).build();
    public static final MoveAction arrestingSkinnerAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();
    public static final Move anArrestingSkinner = Move.builder()
            .id(new ObjectId().toString())
            .name("AN ARRESTING SKINNER")
            .description("_**An arresting skinner**_: when you remove a piece of clothing, your own or someone else's, no one who can see you can do anything but watch.\n" +
                    "\n" +
                    "You command their absolute attention. If you choose, you can exempt individual people, by name.")
            .kind(MoveType.CHARACTER)
            .moveAction(arrestingSkinnerAction)
            .playbook(PlaybookType.SKINNER).build();
    public static final MoveAction hypnoticAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HOT)
            .build();
    public static final Move hypnotic = Move.builder()
            .id(new ObjectId().toString())
            .name(hypnoticName)
            .description("_**Hypnotic**_: when you have time and solitude with someone, they become fixated upon you. Roll+hot.\n" +
                    "\n" +
                    "On a 10+, hold 3. On a 7-9, hold 2.\n" +
                    "\n" +
                    "They can spend your hold, 1 for 1, by:\n" +
                    "\n" +
                    "- *Giving you something you want.*\n" +
                    "- *Acting as your eyes and ears.*\n" +
                    "- *Fighting to protect you.*\n" +
                    "- *Doing something you tell them to.*\n" +
                    "\n" +
                    "For NPCs, while you have a hold over them they can't act against you. For PCs, instead, any time you like you can spend your hold, 1 for 1:\n" +
                    "\n" +
                    "- *They distract themselves with the thought of you. They're acting under fire.*\n" +
                    "- *They inspire themselves with the thought of you. They take +1forward now.*\n" +
                    "\n" +
                    "On a miss, they hold two over you, on the exact same terms.")
            .kind(MoveType.CHARACTER)
            .moveAction(hypnoticAction)
            .playbook(PlaybookType.SKINNER).build();

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

    public static final Move secondCoolMax2 = Move.builder()
            .id(new ObjectId().toString())
            .name(secondCoolMax2Name)
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

    public static final Move secondHardMax2 = Move.builder()
            .id(new ObjectId().toString())
            .name(secondHardMax2Name)
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

    public static final Move secondWeirdMax2 = Move.builder()
            .id(new ObjectId().toString())
            .name(secondWeirdMax2Name)
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
            .name(addOtherPBMove1Name)
            .description("get a move from another playbook\n")
            .kind(MoveType.ADD_OTHER_PB_MOVE)
            .build();

    public static final Move addOtherPBMove2 = Move.builder()
            .id(new ObjectId().toString())
            .name(addOtherPBMove2Name)
            .description("get a move from another playbook\n")
            .kind(MoveType.ADD_OTHER_PB_MOVE)
            .build();

    /* ----------------------------- ADD UNIQUE IMPROVEMENT MOVES --------------------------------- */

    public static final Move addGangLeadership = Move.builder()
            .id(new ObjectId().toString())
            .name(addGangLeadershipName)
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
            .name(addHoldingName)
            .description("get a holding (you detail) and _**wealth**_\n")
            .kind(MoveType.ADD_UNIQUE)
            .playbook(PlaybookType.HARDHOLDER)
            .build();

    public static final Move addWorkspace = Move.builder()
            .id(new ObjectId().toString())
            .name(addWorkspaceName)
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
            .name(addVehicleName)
            .description("get a vehicle (you detail)\n")
            .kind(MoveType.ADD_VEHICLE)
            .build();

    /* ----------------------------- UNGIVEN FUTURE IMPROVEMENT MOVES --------------------------------- */

    public static final Move genericIncreaseStat = Move.builder()
            .id(new ObjectId().toString())
            .name(genericIncreaseStatName)
            .description("get +1 to any stat (max stat+3)\n")
            .kind(MoveType.GENERIC_INCREASE_STAT)
            .build();

    public static final Move retire = Move.builder()
            .id(new ObjectId().toString())
            .name(retireName)
            .description("retire your character to safety\n")
            .kind(MoveType.RETIRE)
            .build();

    public static final Move addSecondCharacter = Move.builder()
            .id(new ObjectId().toString())
            .name(addSecondCharacterName)
            .description("create a second character to play\n")
            .kind(MoveType.ADD_SECOND_CHARACTER)
            .build();

    public static final Move changePlaybook = Move.builder()
            .id(new ObjectId().toString())
            .name(changePlaybookName)
            .description("change your character to a new playbook\n")
            .kind(MoveType.CHANGE_PLAYBOOK)
            .build();

    public static final Move improveBasicMoves1 = Move.builder()
            .id(new ObjectId().toString())
            .name(improveBasicMoves1Name)
            .description("choose 3 basic moves and advance them\n")
            .kind(MoveType.IMPROVE_BASIC_MOVES)
            .build();

    public static final Move improveBasicMoves2 = Move.builder()
            .id(new ObjectId().toString())
            .name(improveBasicMoves2Name)
            .description("advance the other three basic moves\n")
            .kind(MoveType.IMPROVE_BASIC_MOVES)
            .build();
}
