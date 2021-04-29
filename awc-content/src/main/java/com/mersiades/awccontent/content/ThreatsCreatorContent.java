package com.mersiades.awccontent.content;

import com.mersiades.awccontent.enums.ThreatType;
import com.mersiades.awccontent.models.ThreatCreator;
import com.mersiades.awccontent.models.ThreatCreatorContent;
import org.bson.types.ObjectId;

import java.util.List;

public class ThreatsCreatorContent {

    public static final String createThreatInstructions = "To create a threat:\n" +
            "\n" +
            "- Choose its kind, name it, and copy over its impulse. Describe it and list its cast.\n" +
            "- Place it on the threat map. If it's in motion, mark its direction with an arrow.\n" +
            "- List its stakes question(s).\n" +
            "- if it's connected to other threats, list them.\n" +
            "- If it calls for a custom move or a countdown, create it.";

    public static final String essentialThreatInstructions = "- Where the PCs are, create as landscape.\n" +
            "- For any PC's gang, create a brutes.\n" +
            "- For any PC's other NPCs, create as brutes, plus a grotesque and/or a wannabe warlord.\n" +
            "- For any PCs' vehicles, create as vehicles.\n" +
            "- In any local population, create an affliction.";

    public static final ThreatCreatorContent warlord = ThreatCreatorContent.builder()
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

    public static final ThreatCreatorContent grotesque = ThreatCreatorContent.builder()
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

    public static final ThreatCreatorContent brute = ThreatCreatorContent.builder()
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

    public static final ThreatCreatorContent affliction = ThreatCreatorContent.builder()
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

    public static final ThreatCreatorContent landscape = ThreatCreatorContent.builder()
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

    public static final ThreatCreatorContent terrain = ThreatCreatorContent.builder()
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

    public static final ThreatCreatorContent vehicle = ThreatCreatorContent.builder()
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

    public static final List<String> threatNames = List.of("Tum Tum", "Gnarly", "Fleece", "White", "Lala", "Bill",
            "Crine", "Mercer", "Preen", "Ik", " Shan", "Isle", "Ula", "Joe's Girl", "Dremmer",
            "Balls", "Amy", "Rufe", "Jackabacka", "Ba", "Mice", "Dog head", "Hugo", "Roark",
            "Monk", "Pierre", "Norvell", "H", "Omie Wise", "Corbett", "Jeanette", "Rum", "Peppering",
            "Brain", "Matilda", "Rothschild", "Wisher", "Partridge", "Brace Win", "Bar", "Krin",
            "Parcher", "Millions", "Grome", "Foster", "Mill", "Dustwich", "Newton", "Tao", "Missed",
            "III", "Princy", "East Harrow", "Kettle", "Putrid", "Last", "Twice", "Clarion", "Abondo",
            "Mimi", "Fianelly", "Pellet", "Li", "Harridan", "Rice", "Do", "Winkle", "Fuse", "Visage");

    public static final ThreatCreator threatCreator = ThreatCreator.builder()
            .id(new ObjectId().toString())
            .createThreatInstructions(createThreatInstructions)
            .essentialThreatInstructions(essentialThreatInstructions)
            .threats(List.of(warlord, grotesque, brute, affliction, landscape, terrain, vehicle))
            .threatNames(threatNames)
            .build();
}
