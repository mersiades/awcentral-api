package com.mersiades.awcweb.bootstrap;

import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.models.uniquecreators.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import com.mersiades.awccontent.repositories.*;
import com.mersiades.awccontent.services.*;

import java.util.*;

import static com.mersiades.awccontent.constants.MoveNames.*;
import static com.mersiades.awccontent.enums.StatType.*;

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


    public GameDataLoader(PlaybookCreatorService playbookCreatorService,
                          PlaybookService playbookService,
                          NameService nameService,
                          LookService lookService,
                          StatsOptionService statsOptionService,
                          MoveService moveService,
                          StatModifierService statModifierService, VehicleCreatorService vehicleCreatorService) {
        this.playbookCreatorService = playbookCreatorService;
        this.playbookService = playbookService;
        this.nameService = nameService;
        this.lookService = lookService;
        this.statsOptionService = statsOptionService;
        this.moveService = moveService;
        this.statModifierService = statModifierService;
        this.vehicleCreatorService = vehicleCreatorService;
    }

    @Override
    public void run(String... args) {
        // Because only checking if empty before loading/creating items,
        // I'll need to drop db whenever adding new game data
        List<Move> moves = moveRepository.findAll().collectList().block();
        assert moves != null;
        if (moves.isEmpty()) {
            loadMoves();
        }

        List<Name> names = nameRepository.findAll().collectList().block();
        assert names != null;
        if (names.isEmpty()) {
            loadNames();
        }

        List<Look> looks = lookRepository.findAll().collectList().block();
        assert looks != null;
        if (looks.isEmpty()) {
            loadLooks();
        }

        List<StatsOption> statsOptions = statsOptionRepository.findAll().collectList().block();
        assert statsOptions != null;
        if (statsOptions.isEmpty()) {
            loadStatsOptions();
        }

        List<PlaybookCreator> playbookCreators = playbookCreatorRepository.findAll().collectList().block();
        assert playbookCreators != null;
        if (playbookCreators.isEmpty()) {
            loadPlaybookCreators();
        }

        List<Playbook> playbooks = playbookRepository.findAll().collectList().block();
        assert playbooks != null;
        if (playbooks.isEmpty()) {
            loadPlaybooks();
        }

        VehicleCreator vehicleCreator = vehicleCreatorRepository.findAll().take(1).blockFirst();
        if (vehicleCreator == null) {
            loadVehicleCreator();
        }

        // 'Create if empty' conditionality is embedded in the createPlaybooks() method
        createPlaybooks();

        System.out.println("Look count: " + Objects.requireNonNull(lookRepository.count().block()).toString());
        System.out.println("Move count: " + Objects.requireNonNull(moveRepository.count().block()).toString());
        System.out.println("Name count: " + Objects.requireNonNull(nameRepository.count().block()).toString());
        System.out.println("PlaybookCreator count: " + Objects.requireNonNull(playbookCreatorRepository.count().block()).toString());
        System.out.println("CarCreator count: " + Objects.requireNonNull(vehicleCreatorRepository.count().block()).toString());
        System.out.println("Playbook count: " + Objects.requireNonNull(playbookRepository.count().block()).toString());
    }

    private void loadMoves() {
        System.out.println("|| --- Loading basic moves --- ||");
        /* ----------------------------- BASIC MOVES --------------------------------- */
        MoveAction doSomethingUnderFireAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(COOL)
                .build();
        Move doSomethingUnderFire = Move.builder()
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
        MoveAction goAggroAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move goAggro = Move.builder()
                .name("GO AGGRO ON SOMEONE")
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
        MoveAction suckerAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move sucker = Move.builder()
                .name("SUCKER SOMEONE")
                .description("When you _**attack someone unsuspecting or helpless**_, ask the MC if you could miss. If you could, treat it as going aggro, but your victim has no choice to cave and do what you want. If you couldn’t, you simply inflict harm as established.")
                .kind(MoveType.BASIC)
                .moveAction(suckerAction)
                .playbook(null)
                .build();
        MoveAction doBattleAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move doBattle = Move.builder()
                .name("DO BATTLE")
                .description("When you’re _**in battle**_, you can bring the battle moves into play.")
                .kind(MoveType.BASIC)
                .moveAction(doBattleAction)
                .playbook(null)
                .build();
        MoveAction seduceOrManipAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HOT)
                .build();
        Move seduceOrManip = Move.builder()
                .name("SEDUCE OR MANIPULATE SOMEONE")
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
        MoveAction helpOrInterfereAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.HX)
                .statToRollWith(null)
                .build();
        Move helpOrInterfere = Move.builder()
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
        MoveAction readASitchAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(SHARP)
                .build();
        Move readASitch = Move.builder()
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
        MoveAction readAPersonAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(SHARP)
                .build();
        Move readAPerson = Move.builder()
                .name("READ A PERSON")
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
        MoveAction openBrainAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(WEIRD)
                .build();
        Move openBrain = Move.builder()
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
        MoveAction lifestyleAndGigsAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.BARTER)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move lifestyleAndGigs = Move.builder()
                .name("LIFESTYLE AND GIGS")
                .description("_**At the beginning of the session**_, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions. If you need jingle during a session, tell the MC you’d like to work a gig.")
                .kind(MoveType.BASIC)
                .moveAction(lifestyleAndGigsAction)
                .playbook(null)
                .build();
        MoveAction sessionEndAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move sessionEnd = Move.builder()
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

        moveService.saveAll(Flux.just(doSomethingUnderFire, goAggro, sucker, doBattle, seduceOrManip, helpOrInterfere,
                readASitch, readAPerson, openBrain, lifestyleAndGigs, sessionEnd)).blockLast();

        System.out.println("|| --- Loading peripheral moves --- ||");
        /* ----------------------------- PERIPHERAL MOVES --------------------------------- */
        MoveAction sufferHarmAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.HARM)
                .build();
        Move sufferHarm = Move.builder()
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
        MoveAction inflictHarmAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ADJUST_HX)
                .build();
        Move inflictHarmMove = Move.builder()
                .name(inflictHarmName)
                .description("When you _**inflict harm on another player’s character**_, the other character gets +1Hx with you (on their sheet) for every segment of harm you inflict.\n" +
                        "\n" +
                        "If this brings them to Hx+4, they reset to Hx+1 as usual, and therefore mark experience.")
                .kind(MoveType.PERIPHERAL)
                .moveAction(inflictHarmAction)
                .playbook(null)
                .build();
        MoveAction healPcHarmAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ADJUST_HX)
                .build();
        Move healPcHarm = Move.builder()
                .name(healHarmName)
                .description("When you _**heal another player’s character’s harm**_, you get +1Hx with them (on your sheet) for every segment of harm you heal.\n" +
                        "\n" +
                        "If this brings you to Hx+4, you reset to Hx+1 as usual, and therefore mark experience.")
                .kind(MoveType.PERIPHERAL)
                .moveAction(healPcHarmAction)
                .playbook(null)
                .build();
        MoveAction giveBarterAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.BARTER)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move giveBarter = Move.builder()
                .name("GIVE BARTER")
                .description("When you _**give 1-barter to someone, but with strings attached**_, it counts as manipulating them and hitting the roll with a 10+, no leverage or roll required.")
                .kind(MoveType.PERIPHERAL)
                .moveAction(giveBarterAction)
                .playbook(null)
                .build();
        MoveAction goToMarketAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(SHARP)
                .build();
        Move goToMarket = Move.builder()
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
        MoveAction makeWantKnownAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.BARTER)
                .build();
        Move makeWantKnown = Move.builder()
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
        MoveAction insightAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move insight = Move.builder()
                .name("INSIGHT")
                .description("When you are able to go to someone for _**insight**_, ask them what they think your best course is, and the MC will tell you.\n" +
                        "\n" +
                        "If you pursue that course, take +1 to any rolls you make in the pursuit. If you pursue that course but don’t accomplish your ends, you mark experience.")
                .kind(MoveType.PERIPHERAL)
                .moveAction(insightAction)
                .playbook(null)
                .build();
        MoveAction auguryAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(WEIRD)
                .build();
        Move augury = Move.builder()
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
        MoveAction changeHighlightedStatsAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move changeHighlightedStats = Move.builder()
                .name("CHANGE HIGHLIGHTED STATS")
                .description("_**At the beginning of any session**_, or at the end if you forgot, anyone can say, “hey, let’s change highlighted stats.” When someone says it, do it.\n" +
                        "\n" +
                        "Go around the circle again, following the same procedure you used to highlight them in the first place: the high-Hx player highlights one stat, and the MC highlight another.")
                .kind(MoveType.PERIPHERAL)
                .moveAction(changeHighlightedStatsAction)
                .playbook(null)
                .build();

        moveService.saveAll(Flux.just(sufferHarm, inflictHarmMove, healPcHarm, giveBarter, goToMarket, makeWantKnown,
                insight, augury, changeHighlightedStats)).blockLast();

        System.out.println("|| --- Loading battle moves --- ||");
        /* ----------------------------- BATTLE MOVES --------------------------------- */
        MoveAction exchangeHarmAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move exchangeHarm = Move.builder()
                .name("EXCHANGE HARM")
                .description("When you _**exchange harm**_, both sides simultaneously inflict and suffer harm as established:\n" +
                        "\n" +
                        "- *You inflict harm equal to the harm rating of your weapon, minus the armor rating of your enemy’s armor.*\n" +
                        "- *You suffer harm equal to the harm rating of your enemy’s weapon, minus the armor rating of your own armor.*")
                .kind(MoveType.BATTLE)
                .moveAction(exchangeHarmAction)
                .playbook(null)
                .build();
        MoveAction seizeByForceAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move seizeByForce = Move.builder()
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
        MoveAction assaultAPositionAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move assaultAPosition = Move.builder()
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
        MoveAction keepHoldOfSomethingAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move keepHoldOfSomething = Move.builder()
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
        MoveAction fightFreeAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move fightFree = Move.builder()
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
        MoveAction defendSomeoneAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move defendSomeone = Move.builder()
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
        MoveAction doSingleCombatAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move doSingleCombat = Move.builder()
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
        MoveAction layDownFireAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move layDownFire = Move.builder()
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
        MoveAction standOverwatchAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(COOL)
                .build();
        Move standOverwatch = Move.builder()
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
        MoveAction keepAnEyeOutAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(SHARP)
                .build();
        Move keepAnEyeOut = Move.builder()
                .name("KEEP AN EYE OUT")
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
        MoveAction beTheBaitAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(COOL)
                .build();
        Move beTheBait = Move.builder()
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
        MoveAction beTheCatAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(COOL)
                .build();
        Move beTheCat = Move.builder()
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
        MoveAction beTheMouseAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(COOL)
                .build();
        Move beTheMouse = Move.builder()
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
        MoveAction catOrMouseAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(SHARP)
                .build();
        Move catOrMouse = Move.builder()
                .name("CAT OR MOUSE")
                .description("When _**it’s not certain whether you’re the cat or the mouse**_, roll+sharp. On a hit, you decide which you are.\n" +
                        "\n" +
                        "On a 10+, you take +1forward as well.\n" +
                        "\n" +
                        "On a miss, you’re the mouse.")
                .kind(MoveType.BATTLE)
                .moveAction(catOrMouseAction)
                .playbook(null)
                .build();

        moveService.saveAll(Flux.just(exchangeHarm, seizeByForce, assaultAPosition, keepHoldOfSomething,
                fightFree, defendSomeone, doSingleCombat, layDownFire, standOverwatch, keepAnEyeOut,
                beTheBait, beTheCat, beTheMouse, catOrMouse)).blockLast();

        System.out.println("|| --- Loading road war moves --- ||");
        /* ----------------------------- ROAD WAR MOVES --------------------------------- */
        Move boardAMovingVehicle = Move.builder()
                .name("BOARD A MOVING VEHICLE")
                .description("To _**board a moving vehicle**_, roll+cool, minus its speed. To board one moving vehicle from another, roll+cool, minus the difference between their speeds.\n" +
                        "\n" +
                        "On a 10+, you’re on and you made it look easy. Take +1forward.\n" +
                        "\n" +
                        "On a 7–9, you’re on, but jesus.\n" +
                        "\n" +
                        "On a miss, the MC chooses: you’re hanging on for dear life, or you’re down and good luck to you.")
                .kind(MoveType.ROAD_WAR)
                .stat(COOL)
                .playbook(null)
                .build();
        Move outdistanceAnotherVehicle = Move.builder()
                .name("OUTDISTANCE ANOTHER VEHICLE")
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
                .stat(COOL)
                .playbook(null)
                .build();
        Move overtakeAnotherVehicle = Move.builder()
                .name("OVERTAKE ANOTHER VEHICLE")
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
                .stat(COOL)
                .playbook(null)
                .build();
        Move dealWithBadTerrain = Move.builder()
                .name("DEAL WITH BAD TERRAIN")
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
                .stat(COOL)
                .playbook(null)
                .build();
        MoveAction shoulderAnotherVehicleAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(COOL)
                .build();
        Move shoulderAnotherVehicle = Move.builder()
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

        moveService.saveAll(Flux.just(boardAMovingVehicle, outdistanceAnotherVehicle, overtakeAnotherVehicle,
                dealWithBadTerrain, shoulderAnotherVehicle)).blockLast();

        /* ----------------------------- ANGEL MOVES --------------------------------- */
        System.out.println("|| --- Loading Angel moves --- ||");
        RollModifier sixthSenseMod = RollModifier.builder()
                .id(UUID.randomUUID().toString())
                .moveToModify(openBrain)
                .statToRollWith(StatType.SHARP).build();
        RollModifier profCompassionMod = RollModifier.builder()
                .id(UUID.randomUUID().toString())
                .moveToModify(helpOrInterfere)
                .statToRollWith(StatType.SHARP).build();
        MoveAction angelSpecialAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ADJUST_HX)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move angelSpecial = Move.builder()
                .name(angelSpecialName)
                .description("If you and another character have sex, your Hx with them on your sheet goes immediately to +3, and they immediately get +1 to their Hx with you on their sheet.\n" +
                        "\n" +
                        "If that brings their Hx with you to +4, they reset it to +1 instead, as usual, and so mark experience.")
                .kind(MoveType.DEFAULT_CHARACTER)
                .moveAction(angelSpecialAction)
                .playbook(PlaybookType.ANGEL)
                .build();
        Move sixthSense = Move.builder()
                .name("SIXTH SENSE")
                .description("_**Sixth sense**_: when you open your brain to the world’s psychic maelstrom, roll+sharp instead of +weird.")
                .rollModifier(sixthSenseMod)
                .kind(MoveType.CHARACTER)
                .playbook(PlaybookType.ANGEL).build();
        Move infirmary = Move.builder()
                .name("INFIRMARY")
                .description("_**Infirmary**_: you get an infirmary, a workspace with life support, a drug lab and a crew of 2 (Shigusa & Mox, maybe).\n" +
                        "\n" +
                        "Get patients into it and you can work on them like a savvyhead on tech (_cf_).")
                .kind(MoveType.CHARACTER)
                .stat(null)
                .playbook(PlaybookType.ANGEL)
                .build();
        Move profCompassion = Move.builder()
                .name("PROFESSIONAL COMPASSION")
                .description("_**Professional compassion**_: you can roll+sharp instead of roll+Hx when you help someone who’s rolling.")
                .rollModifier(profCompassionMod)
                .kind(MoveType.CHARACTER)
                .playbook(PlaybookType.ANGEL).build();
        MoveAction battlefieldGraceAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move battlefieldGrace = Move.builder()
                .name("BATTLEFIELD GRACE")
                .description("_**Battlefield grace**_: while you are caring for people, not fighting, you get +1armor.")
                .kind(MoveType.CHARACTER)
                .moveAction(battlefieldGraceAction)
                .playbook(PlaybookType.ANGEL)
                .build();
        MoveAction healingTouchAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(WEIRD)
                .build();
        Move healingTouch = Move.builder()
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
        MoveAction touchedByDeathAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move touchedByDeath = Move.builder()
                .name("TOUCHED BY DEATH")
                .description("_**Touched by death**_: when someone is unconscious in your care, you can use them for _**augury**_. When someone has died in your care, you can use their body for _**augury**_.")
                .kind(MoveType.CHARACTER)
                .moveAction(touchedByDeathAction)
                .playbook(PlaybookType.ANGEL)
                .build();

        moveService.saveAll(Flux.just(angelSpecial, sixthSense, infirmary, profCompassion,
                battlefieldGrace, healingTouch, touchedByDeath)).blockLast();

        /* ----------------------------- ANGEL KIT MOVES --------------------------------- */

        MoveAction stabilizeAndHealAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STOCK)
                .statToRollWith(null)
                .build();
        Move stabilizeAndHeal = Move.builder()
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
        MoveAction speedTheRecoveryOfSomeoneAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.STOCK)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move speedTheRecoveryOfSomeone = Move.builder()
                .name(speedRecoveryName)
                .description("_**speed the recovery of someone at 3:00 or 6:00**_: don’t roll. They choose: you spend 1-stock and they spend 4 days (3:00) or 1 week (6:00) blissed out on chillstabs, immobile but happy, or else they do their time in agony like everyone else.")
                .kind(MoveType.UNIQUE)
                .moveAction(speedTheRecoveryOfSomeoneAction)
                .playbook(PlaybookType.ANGEL).build();
        MoveAction reviveSomeoneAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.STOCK)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move reviveSomeone = Move.builder()
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
        MoveAction treatAnNpcAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.STOCK)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move treatAnNpc = Move.builder()
                .name(treatNpcName)
                .description("_**treat an NPC**_: spend 1-stock. They’re stable now and they’ll recover in time. ")
                .kind(MoveType.UNIQUE)
                .moveAction(treatAnNpcAction)
                .playbook(PlaybookType.ANGEL).build();

        moveService.saveAll(Flux.just(stabilizeAndHeal, speedTheRecoveryOfSomeone, reviveSomeone, treatAnNpc)).blockLast();

        /* ----------------------------- BATTLEBABE MOVES --------------------------------- */
        System.out.println("|| --- Loading Battlebabe moves --- ||");
        RollModifier iceColdMod = RollModifier.builder()
                .id(UUID.randomUUID().toString())
                .moveToModify(goAggro)
                .statToRollWith(HARD).build();
        MoveAction battlebabeSpecialAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move battlebabeSpecial = Move.builder()
                .name("BATTLEBABE SPECIAL")
                .description("If you and another character have sex, nullify the other character’s sex move. Whatever it is, it just doesn’t happen.")
                .kind(MoveType.DEFAULT_CHARACTER)
                .moveAction(battlebabeSpecialAction)
                .playbook(PlaybookType.BATTLEBABE).build();
        MoveAction dangerousAndSexyAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HOT)
                .build();
        Move dangerousAndSexy = Move.builder()
                .name("DANGEROUS & SEXY")
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
        Move iceCold = Move.builder()
                .name("ICE COLD")
                .description("_**Ice cold**_: when you go aggro on an NPC, roll+cool instead of roll+hard. When you go aggro on another player’s character, roll+Hx instead of roll+hard.")
                .rollModifier(iceColdMod)
                .stat(null).kind(MoveType.CHARACTER)
                .playbook(PlaybookType.BATTLEBABE).build();
        MoveAction mercilessAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move merciless = Move.builder()
                .name("MERCILESS")
                .description("_**Merciless**_: when you inflict harm, inflict +1harm.")
                .kind(MoveType.CHARACTER)
                .moveAction(mercilessAction)
                .playbook(PlaybookType.BATTLEBABE).build();
        MoveAction visionsOfDeathAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(WEIRD)
                .build();
        Move visionsOfDeath = Move.builder()
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
        MoveAction perfectInstinctsAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move perfectInstincts = Move.builder()
                .name("PERFECT INSTINCTS")
                .description("_**Perfect instincts**_: when you’ve read a charged situation and you’re acting on the MC’s answers, take +2 instead of +1.")
                .kind(MoveType.CHARACTER)
                .moveAction(perfectInstinctsAction)
                .playbook(PlaybookType.BATTLEBABE).build();
        MoveAction impossibleReflexesAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move impossibleReflexes = Move.builder()
                .name("IMPOSSIBLE REFLEXES")
                .description("_**Impossible reflexes**_: the way you move unencumbered counts as armor. If you’re naked or nearly naked, 2-armor; if you’re wearing non-armor fashion, 1-armor. If you’re wearing armor, use it instead.")
                .kind(MoveType.CHARACTER)
                .moveAction(impossibleReflexesAction)
                .playbook(PlaybookType.BATTLEBABE).build();

        moveService.saveAll(Flux.just(battlebabeSpecial, dangerousAndSexy, iceCold, merciless, visionsOfDeath, perfectInstincts, impossibleReflexes)).blockLast();

        /* ----------------------------- BRAINER MOVES --------------------------------- */
        System.out.println("|| --- Loading Brainer moves --- ||");
        RollModifier lustMod = RollModifier.builder()
                .id(UUID.randomUUID().toString())
                .moveToModify(seduceOrManip)
                .statToRollWith(StatType.WEIRD).build();
        StatModifier attunementMod = StatModifier.builder()
                .id(UUID.randomUUID().toString())
                .statToModify(StatType.WEIRD)
                .modification(1).build();
        StatModifier savedAttunementMod = statModifierService.save(attunementMod).block();

        MoveAction brainerSpecialAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move brainerSpecial = Move.builder()
                .name("BRAINER SPECIAL")
                .description("If you and another character have sex, you automatically do a _**deep brain scan**_ on them, whether you have the move or not. Roll+weird as normal.\n" +
                        "\n" +
                        "However, the MC chooses which questions the other character’s player answers.")
                .kind(MoveType.DEFAULT_CHARACTER)
                .moveAction(brainerSpecialAction)
                .playbook(PlaybookType.BRAINER).build();
        Move unnaturalLust = Move.builder()
                .name("UNNATURAL LUST TRANSFIXION")
                .description("_**Unnatural lust transfixion**_: when you try to seduce someone, roll+weird instead of roll+hot.")
                .kind(MoveType.CHARACTER)
                .stat(null)
                .rollModifier(lustMod)
                .playbook(PlaybookType.BRAINER).build();
        Move brainReceptivity = Move.builder()
                .name("CASUAL BRAIN RECEPTIVITY")
                .description("_**Casual brain receptivity**_: when you read someone, roll+weird instead of roll+sharp. Your victim has to be able to see you, but you don’t have to interact.")
                .kind(MoveType.CHARACTER)
                .stat(null)
                .playbook(PlaybookType.BRAINER).build();
        Move brainAttunement = Move.builder()
                .name("PRETERNATURAL BRAIN ATTUNEMENT")
                .description("_**Preternatural at-will brain attunement**_: you get +1weird (weird+3).\n")
                .statModifier(savedAttunementMod)
                .kind(MoveType.CHARACTER)
                .stat(null)
                .playbook(PlaybookType.BRAINER).build();
        MoveAction brainScanAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(WEIRD)
                .build();
        Move brainScan = Move.builder()
                .name("DEEP BRAIN SCAN")
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
        MoveAction whisperProjectionAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(WEIRD)
                .build();
        Move whisperProjection = Move.builder()
                .name("DIRECT BRAIN WHISPER PROJECTION")
                .description("_**Direct-brain whisper projection**_: you can roll+weird to get the effects of going aggro, without going aggro. Your victim has to be able to see you, but you don’t have to interact.\n" +
                        "\n" +
                        "If your victim forces your hand, your mind counts as a weapon (1-harm ap close loud-optional).")
                .kind(MoveType.CHARACTER)
                .moveAction(whisperProjectionAction)
                .playbook(PlaybookType.BRAINER).build();
        MoveAction puppetStringsAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(WEIRD)
                .build();
        Move puppetStrings = Move.builder()
                .name("IN BRAIN PUPPET STRINGS")
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

        moveService.saveAll(Flux.just(brainerSpecial, unnaturalLust, brainReceptivity, brainAttunement, brainScan, whisperProjection, puppetStrings)).blockLast();

        /* ----------------------------- CHOPPER MOVES --------------------------------- */
        System.out.println("|| --- Loading Chopper moves --- ||");
        MoveAction chopperSpecialAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ADJUST_HX)
                .build();
        Move chopperSpecial = Move.builder()
                .name(chopperSpecialName)
                .description("If you and another character have sex, they immediately change their sheet to say Hx+3 with you.\n" +
                        "\n" +
                        "They also choose whether to give you -1 or +1 to your Hx with them, on your sheet.")
                .kind(MoveType.DEFAULT_CHARACTER)
                .moveAction(chopperSpecialAction)
                .playbook(PlaybookType.CHOPPER).build();
        MoveAction packAlphaAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move packAlpha = Move.builder()
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
        MoveAction fuckingThievesAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move fuckingThieves = Move.builder()
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

        moveService.saveAll(Flux.just(chopperSpecial, packAlpha, fuckingThieves)).blockLast();

        /* ----------------------------- DRIVER MOVES --------------------------------- */
        System.out.println("|| --- Loading Driver moves --- ||");
        RollModifier weatherEyeMod = RollModifier.builder()
                .id(UUID.randomUUID().toString())
                .moveToModify(openBrain)
                .statToRollWith(COOL).build();
        MoveAction driverSpecialAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(COOL)
                .build();
        Move driverSpecial = Move.builder()
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
        MoveAction combatDriverAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move combatDriver = Move.builder()
                .name("COMBAT DRIVER")
                .description("_**Combat driver**_: when you use your vehicle as a weapon, inflict +1harm. When you inflict v-harm, add +1 to your target’s roll. When you suffer v-harm, take -1 to your roll.")
                .kind(MoveType.CHARACTER)
                .moveAction(combatDriverAction)
                .playbook(PlaybookType.DRIVER).build();
        MoveAction eyeOnTheDoorAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(COOL)
                .build();
        Move eyeOnTheDoor = Move.builder()
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
        Move weatherEye = Move.builder()
                .name("WEATHER EYE")
                .description("_**Weather eye**_: when you open your brain to the world’s psychic maelstrom, roll+cool instead of roll+weird.")
                .kind(MoveType.CHARACTER)
                .stat(null)
                .rollModifier(weatherEyeMod)
                .playbook(PlaybookType.DRIVER).build();
        MoveAction reputationAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(COOL)
                .build();
        Move reputation = Move.builder()
                .name("REPUTATION")
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
        MoveAction daredevilAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move daredevil = Move.builder()
                .name("DAREDEVIL")
                .description("_**Daredevil**_: if you go straight into danger without hedging your bets, you get +1armor. If you happen to be leading a gang or convoy, it gets +1armor too.")
                .kind(MoveType.CHARACTER)
                .moveAction(daredevilAction)
                .playbook(PlaybookType.DRIVER).build();
        Move collectorMove = Move.builder()
                .name(collector)
                .description("_**Collector**_: you get 2 additional cars (you detail).")
                .kind(MoveType.CHARACTER)
                .stat(null)
                .playbook(PlaybookType.DRIVER).build();
        Move myOtherCarIsATank = Move.builder()
                .name("MY OTHER CAR IS A TANK")
                .description("_**My other car is a tank**_: you get a specialized battle vehicle (detail with the MC).")
                .kind(MoveType.CHARACTER)
                .stat(null)
                .playbook(PlaybookType.DRIVER).build();

        moveService.saveAll(Flux.just(driverSpecial, combatDriver, eyeOnTheDoor, weatherEye, reputation, daredevil, collectorMove, myOtherCarIsATank)).blockLast();

        /* ----------------------------- GUNLUGGER MOVES --------------------------------- */
        System.out.println("|| --- Loading Gunlugger moves --- ||");
        RollModifier battleHardenedMod = RollModifier.builder()
                .id(UUID.randomUUID().toString())
                // TODO: Add standoverwatch
                .moveToModify(doSomethingUnderFire)
                .statToRollWith(HARD).build();
        RollModifier battlefieldInstinctsMod = RollModifier.builder()
                .id(UUID.randomUUID().toString())
                .moveToModify(openBrain)
                .statToRollWith(HARD).build();
        StatModifier insanoMod = StatModifier.builder()
                .id(UUID.randomUUID().toString())
                .statToModify(HARD)
                .modification(1).build();
        Move gunluggerSpecial = Move.builder()
                .name("GUNLUGGER SPECIAL")
                .description("If you and another character have sex, you take +1 forward. At your option, they take +1 forward too.")
                .kind(MoveType.DEFAULT_CHARACTER)
                .stat(null)
                .playbook(PlaybookType.GUNLUGGER).build();
        Move battleHardened = Move.builder()
                .name("BATTLE-HARDENED")
                .description("_**Battle-hardened**_: when you act under fire, or when you stand overwatch, roll+hard instead of roll+cool.")
                .kind(MoveType.CHARACTER)
                .stat(null)
                .rollModifier(battleHardenedMod)
                .playbook(PlaybookType.GUNLUGGER).build();
        MoveAction fuckThisShitAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HARD)
                .build();
        Move fuckThisShit = Move.builder()
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
        Move battlefieldInstincts = Move.builder()
                .name("BATTLEFIELD INSTINCTS").description("_**Battlefield instincts**_: when you open your brain to the world’s psychic maelstrom, roll+hard instead of roll+weird, but only in battle.")
                .rollModifier(battlefieldInstinctsMod)
                .kind(MoveType.CHARACTER)
                .stat(null)
                .playbook(PlaybookType.GUNLUGGER).build();
        Move insanoLikeDrano = Move.builder()
                .name("INSANO LIKE DRANO")
                .description("_**Insano like Drano**_: you get +1hard (hard+3).")
                .kind(MoveType.CHARACTER)
                .stat(null)
                .statModifier(insanoMod)
                .playbook(PlaybookType.GUNLUGGER).build();
        Move preparedForTheInevitable = Move.builder()
                .name("PREPARED FOR THE INEVITABLE")
                .description("_**Prepared for the inevitable**_: you have a well-stocked and high-quality first aid kit. It counts as an angel kit (cf ) with a capacity of 2-stock.")
                .kind(MoveType.CHARACTER)
                .stat(null)
                .playbook(PlaybookType.GUNLUGGER).build();
        MoveAction bloodcrazedAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move bloodcrazed = Move.builder()
                .name("BLOODCRAZED")
                .description("_**Bloodcrazed**_: whenever you inflict harm, inflict +1harm.")
                .kind(MoveType.CHARACTER)
                .moveAction(bloodcrazedAction)
                .playbook(PlaybookType.GUNLUGGER).build();
        MoveAction notToBeFuckedWithAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move notToBeFuckedWith = Move.builder()
                .name("NOT TO BE FUCKED WITH")
                .description("_**NOT TO BE FUCKED WITH**_: in battle, you count as a small gang, with harm and armor according to your gear.")
                .kind(MoveType.CHARACTER)
                .moveAction(notToBeFuckedWithAction)
                .playbook(PlaybookType.GUNLUGGER).build();

        moveService.saveAll(Flux.just(gunluggerSpecial, battleHardened, fuckThisShit, battlefieldInstincts, insanoLikeDrano, preparedForTheInevitable, bloodcrazed, notToBeFuckedWith)).blockLast();
    }

    private void loadNames() {
        System.out.println("|| --- Loading playbook names --- ||");
        /* ----------------------------- ANGEL NAMES --------------------------------- */
        Name dou = new Name(PlaybookType.ANGEL, "Dou");
        Name bon = new Name(PlaybookType.ANGEL, "Bon");
        Name abe = new Name(PlaybookType.ANGEL, "Abe");
        Name boo = new Name(PlaybookType.ANGEL, "Boo");
        Name t = new Name(PlaybookType.ANGEL, "T");
        Name kal = new Name(PlaybookType.ANGEL, "Kal");
        Name charName = new Name(PlaybookType.ANGEL, "Char");
        Name jav = new Name(PlaybookType.ANGEL, "Jav");
        Name ruth = new Name(PlaybookType.ANGEL, "Ruth");
        Name wei = new Name(PlaybookType.ANGEL, "Wei");
        Name jay = new Name(PlaybookType.ANGEL, "Jay");
        Name nee = new Name(PlaybookType.ANGEL, "Nee");
        Name kim = new Name(PlaybookType.ANGEL, "Kim");
        Name lan = new Name(PlaybookType.ANGEL, "Lan");
        Name di = new Name(PlaybookType.ANGEL, "Di");
        Name dez = new Name(PlaybookType.ANGEL, "Dez");
        Name doc = new Name(PlaybookType.ANGEL, "Doc");
        Name core = new Name(PlaybookType.ANGEL, "Core");
        Name wheels = new Name(PlaybookType.ANGEL, "Wheels");
        Name buzz = new Name(PlaybookType.ANGEL, "Buzz");
        Name key = new Name(PlaybookType.ANGEL, "Key");
        Name line = new Name(PlaybookType.ANGEL, "Line");
        Name gabe = new Name(PlaybookType.ANGEL, "Gabe");
        Name biz = new Name(PlaybookType.ANGEL, "Biz");
        Name bish = new Name(PlaybookType.ANGEL, "Bish");
        Name inch = new Name(PlaybookType.ANGEL, "Inch");
        Name grip = new Name(PlaybookType.ANGEL, "Grip");
        Name setter = new Name(PlaybookType.ANGEL, "Setter");

        nameService.saveAll(Flux.just(dou, bon, abe, boo, t, kal, charName, jav, ruth, wei, jay, nee,
                kim, lan, di, dez, core, wheels, doc, buzz, key, line, gabe, biz, bish, inch, grip, setter))
                .blockLast();

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

        nameService.saveAll(Flux.just(snow, crimson, shadow, beastie, azure, midnight, scarlet, violetta, amber, rouge,
                damson, sunset, emerald, ruby, raksha, kickskirt, kite, monsoon, smith, baaba, melody, mar, tavi,
                absinthe, honeytree)).blockLast();

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

        nameService.saveAll(Flux.just(smith2, jones, jackson, marsh, lively, burroughs, gritch, joyette, iris, marie,
                amiette, suselle, cybelle, pallor, sin, charmer, pity, brace, sundown)).blockLast();

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

        nameService.saveAll(Flux.just(lauren, audrey, farley, sammy, katherine, marilyn, james, bridget, paul,
                annette, marlene, frankie, marlon, kim1, errol, humphrey, phoenix, mustang, impala, suv, cougar,
                cobra, dart, gremlin, grandCherokee, jag, beemer)).blockLast();

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

        nameService.saveAll(Flux.just(dog, domino, tBone, stinky, satan, lars, bullet, dice, shitHead, halfPint,
                shooter, diamond, goldie, tinker, loose, baby, juck, hammer, hooch, snakeEyes, pinkie, wire, blues)).blockLast();
    }

    private void loadLooks() {
        System.out.println("|| --- Loading playbook looks --- ||");
        /* ----------------------------- ANGEL LOOKS --------------------------------- */
        Look angel1 = new Look(PlaybookType.ANGEL, LookType.GENDER, "man");
        Look angel2 = new Look(PlaybookType.ANGEL, LookType.GENDER, "woman");
        Look angel3 = new Look(PlaybookType.ANGEL, LookType.GENDER, "ambiguous");
        Look angel4 = new Look(PlaybookType.ANGEL, LookType.GENDER, "transgressing");
        Look angel5 = new Look(PlaybookType.ANGEL, LookType.GENDER, "concealed");
        Look angel6 = new Look(PlaybookType.ANGEL, LookType.CLOTHES, "utility wear");
        Look angel7 = new Look(PlaybookType.ANGEL, LookType.CLOTHES, "casual wear plus utility");
        Look angel8 = new Look(PlaybookType.ANGEL, LookType.CLOTHES, "scrounge wear plus utility");
        Look angel9 = new Look(PlaybookType.ANGEL, LookType.FACE, "kind face");
        Look angel10 = new Look(PlaybookType.ANGEL, LookType.FACE, "strong face");
        Look angel11 = new Look(PlaybookType.ANGEL, LookType.FACE, "rugged face");
        Look angel12 = new Look(PlaybookType.ANGEL, LookType.FACE, "haggard face");
        Look angel13 = new Look(PlaybookType.ANGEL, LookType.FACE, "pretty face");
        Look angel14 = new Look(PlaybookType.ANGEL, LookType.FACE, "lively face");
        Look angel15 = new Look(PlaybookType.ANGEL, LookType.EYES, "quick eyes");
        Look angel16 = new Look(PlaybookType.ANGEL, LookType.EYES, "hard eyes");
        Look angel17 = new Look(PlaybookType.ANGEL, LookType.EYES, "caring eyes");
        Look angel18 = new Look(PlaybookType.ANGEL, LookType.EYES, "bright eyes");
        Look angel19 = new Look(PlaybookType.ANGEL, LookType.EYES, "laughing eyes");
        Look angel20 = new Look(PlaybookType.ANGEL, LookType.EYES, "clear eyes");
        Look angel21 = new Look(PlaybookType.ANGEL, LookType.BODY, "compact body");
        Look angel22 = new Look(PlaybookType.ANGEL, LookType.BODY, "stout body");
        Look angel23 = new Look(PlaybookType.ANGEL, LookType.BODY, "spare body");
        Look angel24 = new Look(PlaybookType.ANGEL, LookType.BODY, "big body");
        Look angel25 = new Look(PlaybookType.ANGEL, LookType.BODY, "rangy body");
        Look angel26 = new Look(PlaybookType.ANGEL, LookType.BODY, "sturdy body");

        lookService.saveAll(Flux.just(angel1, angel2, angel3, angel4, angel5, angel6, angel7, angel8, angel9,
                angel10, angel11, angel12, angel13, angel14, angel15, angel16, angel17, angel18, angel19,
                angel20, angel21, angel22, angel23, angel24, angel25, angel26)).blockLast();

        /* ----------------------------- BATTLEBABE LOOKS --------------------------------- */
        Look battlebabe1 = new Look(PlaybookType.BATTLEBABE, LookType.GENDER, "man");
        Look battlebabe2 = new Look(PlaybookType.BATTLEBABE, LookType.GENDER, "woman");
        Look battlebabe3 = new Look(PlaybookType.BATTLEBABE, LookType.GENDER, "ambiguous");
        Look battlebabe4 = new Look(PlaybookType.BATTLEBABE, LookType.GENDER, "transgressing");
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

        lookService.saveAll(Flux.just(battlebabe1, battlebabe2, battlebabe3, battlebabe4, battlebabe5, battlebabe6,
                battlebabe7, battlebabe8, battlebabe9, battlebabe10, battlebabe11, battlebabe12, battlebabe13,
                battlebabe14, battlebabe15, battlebabe16, battlebabe17, battlebabe18, battlebabe19, battlebabe20,
                battlebabe21, battlebabe22, battlebabe23, battlebabe24, battlebabe25, battlebabe26)).blockLast();

        /* ----------------------------- BRAINER LOOKS --------------------------------- */
        Look brainer1 = new Look(PlaybookType.BRAINER, LookType.GENDER, "man");
        Look brainer2 = new Look(PlaybookType.BRAINER, LookType.GENDER, "woman");
        Look brainer3 = new Look(PlaybookType.BRAINER, LookType.GENDER, "ambiguous");
        Look brainer4 = new Look(PlaybookType.BRAINER, LookType.GENDER, "transgressing");
        Look brainer5 = new Look(PlaybookType.BRAINER, LookType.GENDER, "concealed");
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

        lookService.saveAll(Flux.just(brainer1, brainer2, brainer3, brainer4, brainer5, brainer6, brainer7, brainer8,
                brainer9, brainer10, brainer11, brainer12, brainer13, brainer14, brainer15, brainer16, brainer17,
                brainer18, brainer19, brainer20, brainer21, brainer22, brainer23, brainer24, brainer25, brainer26,
                brainer27)).blockLast();

        /* ----------------------------- DRIVER LOOKS --------------------------------- */
        Look chopper1 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.GENDER).look("man").build();
        Look chopper2 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.GENDER).look("woman").build();
        Look chopper3 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.GENDER).look("ambiguous").build();
        Look chopper4 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.GENDER).look("transgressing").build();
        Look chopper5 = Look.builder().playbookType(PlaybookType.CHOPPER).category(LookType.CLOTHES).look("Combat biker wear").build();
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

        lookService.saveAll(Flux.just(chopper1, chopper2, chopper3, chopper4, chopper5, chopper6, chopper7, chopper8,
                chopper9, chopper10, chopper11, chopper12, chopper13, chopper14, chopper15, chopper16, chopper17,
                chopper18, chopper19, chopper20, chopper21, chopper22, chopper23)).blockLast();

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

        lookService.saveAll(Flux.just(driver1, driver2, driver3, driver4, driver5, driver6, driver7, driver8, driver9,
                driver10, driver11, driver12, driver13, driver14, driver15, driver16, driver17, driver18, driver19,
                driver20, driver21, driver22, driver23, driver24, driver25, driver26, driver26, driver27)).blockLast();
    }

    public void loadStatsOptions() {
        System.out.println("|| --- Loading playbook stats options --- ||");
        /* ----------------------------- ANGEL STATS OPTIONS --------------------------------- */
        StatsOption angel1 = new StatsOption(PlaybookType.ANGEL, 1, 0, 1, 2, -1);
        StatsOption angel2 = new StatsOption(PlaybookType.ANGEL, 1, 1, 0, 2, -1);
        StatsOption angel3 = new StatsOption(PlaybookType.ANGEL, -1, 1, 0, 2, 1);
        StatsOption angel4 = new StatsOption(PlaybookType.ANGEL, 2, 0, -1, 2, -1);
        statsOptionService.saveAll(Flux.just(angel1, angel2, angel3, angel4)).blockLast();

        /* ----------------------------- BATTLEBABE STATS OPTIONS --------------------------------- */
        StatsOption battlebabe1 = StatsOption.builder().playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-1).HOT(1).SHARP(1).WEIRD(0).build();
        StatsOption battlebabe2 = StatsOption.builder().playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-1).HOT(2).SHARP(0).WEIRD(-1).build();
        StatsOption battlebabe3 = StatsOption.builder().playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-2).HOT(1).SHARP(1).WEIRD(1).build();
        StatsOption battlebabe4 = StatsOption.builder().playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(0).HOT(1).SHARP(1).WEIRD(-1).build();
        statsOptionService.saveAll(Flux.just(battlebabe1, battlebabe2, battlebabe3, battlebabe4)).blockLast();

        /* ----------------------------- BRAINER STATS OPTIONS --------------------------------- */
        StatsOption brainer1 = StatsOption.builder().playbookType(PlaybookType.BRAINER).COOL(1).HARD(1).HOT(-2).SHARP(1).WEIRD(2).build();
        StatsOption brainer2 = StatsOption.builder().playbookType(PlaybookType.BRAINER).COOL(0).HARD(0).HOT(1).SHARP(0).WEIRD(2).build();
        StatsOption brainer3 = StatsOption.builder().playbookType(PlaybookType.BRAINER).COOL(1).HARD(-2).HOT(-1).SHARP(2).WEIRD(2).build();
        StatsOption brainer4 = StatsOption.builder().playbookType(PlaybookType.BRAINER).COOL(2).HARD(-1).HOT(-1).SHARP(0).WEIRD(2).build();
        statsOptionService.saveAll(Flux.just(brainer1, brainer2, brainer3, brainer4)).blockLast();

        /* ----------------------------- CHOPPER STATS OPTIONS --------------------------------- */
        StatsOption chopper1 = StatsOption.builder().playbookType(PlaybookType.CHOPPER).COOL(1).HARD(2).HOT(-1).SHARP(1).WEIRD(0).build();
        StatsOption chopper2 = StatsOption.builder().playbookType(PlaybookType.CHOPPER).COOL(1).HARD(2).HOT(1).SHARP(0).WEIRD(1).build();
        StatsOption chopper3 = StatsOption.builder().playbookType(PlaybookType.CHOPPER).COOL(1).HARD(2).HOT(0).SHARP(1).WEIRD(1).build();
        StatsOption chopper4 = StatsOption.builder().playbookType(PlaybookType.CHOPPER).COOL(2).HARD(2).HOT(-1).SHARP(0).WEIRD(1).build();
        statsOptionService.saveAll(Flux.just(chopper1, chopper2, chopper3, chopper4)).blockLast();

        /* ----------------------------- DRIVER STATS OPTIONS --------------------------------- */
        StatsOption driver1 = StatsOption.builder().playbookType(PlaybookType.DRIVER).COOL(2).HARD(-1).HOT(1).SHARP(1).WEIRD(0).build();
        StatsOption driver2 = StatsOption.builder().playbookType(PlaybookType.DRIVER).COOL(2).HARD(0).HOT(1).SHARP(1).WEIRD(-1).build();
        StatsOption driver3 = StatsOption.builder().playbookType(PlaybookType.DRIVER).COOL(2).HARD(1).HOT(-1).SHARP(0).WEIRD(1).build();
        StatsOption driver4 = StatsOption.builder().playbookType(PlaybookType.DRIVER).COOL(2).HARD(-2).HOT(0).SHARP(2).WEIRD(1).build();
        statsOptionService.saveAll(Flux.just(driver1, driver2, driver3, driver4)).blockLast();
    }

    public void loadPlaybookCreators() {
        System.out.println("|| --- Loading playbook creators --- ||");
        /* ----------------------------- ANGEL PLAYBOOK CREATOR --------------------------------- */
        List<Move> angelOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.CHARACTER)
                .collectList().block();

        List<Move> angelDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.DEFAULT_CHARACTER)
                .collectList().block();

        AngelKitCreator angelKitCreator = AngelKitCreator.builder()
                .id(UUID.randomUUID().toString())
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
                .id(UUID.randomUUID().toString())
                .type(UniqueType.ANGEL_KIT)
                .angelKitCreator(angelKitCreator)
                .build();
        GearInstructions angelGearInstructions = GearInstructions.builder()
                .id(UUID.randomUUID().toString())
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
        PlaybookCreator angelCreator = PlaybookCreator.builder()
                .playbookType(PlaybookType.ANGEL)
                .gearInstructions(angelGearInstructions)
                .improvementInstructions("Whenever you roll a highlighted stat, and whenever you reset your Hx with someone, mark an experience circle. When you mark the 5th, improve and erase.\n" +
                        "\n" +
                        "Each time you improve, choose one of the options. Check it off; you can’t choose it again.")
                .movesInstructions("You get all the basic moves. Choose 2 angel moves.\n" +
                        "\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**keeping an eye out**_, and _**baiting a trap**_, as well as the rules for harm.")
                .hxInstructions("Everyone introduces their characters by name, look and outlook. Take your turn.\n" +
                        "\n" +
                        "List the other characters’ names.\n" +
                        "\n" +
                        "Go around again for Hx. On your turn, ask 1, 2, or all 3:\n" +
                        "\n" +
                        "- *Which one of you do I figure is doomed to self-destruction?* For that character, write Hx-2.\n" +
                        "- *Which one of you put a hand in when it mattered, and helped me save a life?* For that character, write Hx+2.\n" +
                        "- *Which one of you has been beside me all along, and has seen everything I’ve seen?* For that character, write Hx+3.\n" +
                        "\n" +
                        "For everyone else, write Hx+1. You keep your eyes open.\n" +
                        "\n" +
                        "On the others’ turns, answer their questions as you like.\n" +
                        "\n" +
                        "At the end, choose one of the characters with the highest Hx on your sheet. Ask that player which of your stats is most interesting, and highlight it. The MC will have you highlight a second stat too.")
                .playbookUniqueCreator(angelUniqueCreator)
                .optionalMoves(angelOptionalMoves)
                .defaultMoves(angelDefaultMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .defaultVehicleCount(0)
                .build();


        /* ----------------------------- BATTLEBABE PLAYBOOK CREATOR --------------------------------- */
        List<Move> battlebabeOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.CHARACTER)
                .collectList().block();

        List<Move> battlebabeDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.DEFAULT_CHARACTER)
                .collectList().block();

        TaggedItem firearmBase1 = TaggedItem.builder().id(UUID.randomUUID().toString()).description("handgun").tags(List.of("2-harm", "close", "reload", "loud")).build();
        TaggedItem firearmBase2 = TaggedItem.builder().id(UUID.randomUUID().toString()).description("shotgun").tags(List.of("3-harm", "close", "reload", "messy")).build();
        TaggedItem firearmBase3 = TaggedItem.builder().id(UUID.randomUUID().toString()).description("rifle").tags(List.of("2-harm", "far", "reload", "loud")).build();
        TaggedItem firearmBase4 = TaggedItem.builder().id(UUID.randomUUID().toString()).description("crossbow").tags(List.of("2-harm", "close", "slow")).build();

        ItemCharacteristic firearmOption1 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("ornate").tag("+valuable").build();
        ItemCharacteristic firearmOption2 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("antique").tag("+valuable").build();
        ItemCharacteristic firearmOption3 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("semiautomatic").tag("-reload").build();
        ItemCharacteristic firearmOption4 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("3-round burst").tag("+1harm").build();
        ItemCharacteristic firearmOption5 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("automatic").tag("+area").build();
        ItemCharacteristic firearmOption6 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("silenced").tag("-loud").build();
        ItemCharacteristic firearmOption7 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("hi-powered").tag("close/far, or +1harm at far").build();
        ItemCharacteristic firearmOption8 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("ap ammo").tag("+ap").build();
        ItemCharacteristic firearmOption9 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("scoped").tag("+far, or +1harm at far").build();
        ItemCharacteristic firearmOption10 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("big").tag("+1harm").build();

        TaggedItem handBase1 = TaggedItem.builder().id(UUID.randomUUID().toString()).description("staff").tags(List.of("1-harm", "hand", "area")).build();
        TaggedItem handBase2 = TaggedItem.builder().id(UUID.randomUUID().toString()).description("haft").tags(List.of("1-harm", "hand")).build();
        TaggedItem handBase3 = TaggedItem.builder().id(UUID.randomUUID().toString()).description("handle").tags(List.of("1-harm", "hand")).build();
        TaggedItem handBase4 = TaggedItem.builder().id(UUID.randomUUID().toString()).description("chain").tags(List.of("1-harm", "hand", "area")).build();

        ItemCharacteristic handOption1 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("ornate").tag("+valuable").build();
        ItemCharacteristic handOption2 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("antique").tag("+valuable").build();
        ItemCharacteristic handOption3 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("head").tag("+1harm").build();
        ItemCharacteristic handOption4 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("spikes").tag("+1harm").build();
        ItemCharacteristic handOption5 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("blade").tag("+1harm").build();
        ItemCharacteristic handOption6 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("long blade*").tag("+2harm").build();
        ItemCharacteristic handOption7 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("heavy blade*").tag("+2harm").build();
        ItemCharacteristic handOption8 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("blades*").tag("+2harm").build();
        ItemCharacteristic handOption9 = ItemCharacteristic.builder().id(UUID.randomUUID().toString()).description("hidden").tag("+infinite").build();

        CustomWeaponsCreator customWeaponsCreator = CustomWeaponsCreator.builder()
                .id(UUID.randomUUID().toString())
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
                .id(UUID.randomUUID().toString())
                .type(UniqueType.CUSTOM_WEAPONS)
                .customWeaponsCreator(customWeaponsCreator)
                .build();

        GearInstructions battlebabeGearInstructions = GearInstructions.builder()
                .id(UUID.randomUUID().toString())
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
                .hxInstructions("Everyone introduces their characters by name, look and outlook. Take your turn.\n" +
                        "\n" +
                        "List the other characters’ names.\n" +
                        "\n" +
                        "Go around again for Hx. On your turn, ask the other players which of their characters you can trust.\n" +
                        "\n" +
                        "- *For the characters you can trust, write Hx-1.*\n" +
                        "- *For the characters you can’t trust, write Hx+3.*\n" +
                        "\n" +
                        "You are indifferent to what is safe, and drawn to what is not.\n" +
                        "\n" +
                        "On the others’ turns, answer their questions as you like.\n" +
                        "\n" +
                        "At the end, choose one of the characters with the highest Hx on your sheet. Ask that player which of your stats is most interesting, and highlight it. The MC will have you highlight a second stat too.")
                .playbookUniqueCreator(battlebabeUniqueCreator)
                .optionalMoves(battlebabeOptionalMoves)
                .defaultMoves(battlebabeDefaultMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .defaultVehicleCount(0)
                .build();

        /* ----------------------------- BRAINER PLAYBOOK CREATOR --------------------------------- */
        List<Move> brainerOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.CHARACTER)
                .collectList().block();

        List<Move> brainerDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.DEFAULT_CHARACTER)
                .collectList().block();

        BrainerGearCreator brainerGearCreator = BrainerGearCreator.builder()
                .id(UUID.randomUUID().toString())
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
                .id(UUID.randomUUID().toString())
                .brainerGearCreator(brainerGearCreator)
                .build();

        GearInstructions brainerGearInstructions = GearInstructions.builder()
                .id(UUID.randomUUID().toString())
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
                .improvementInstructions("Whenever you roll a highlighted stat, and whenever you reset your Hx with someone, mark an experience circle. When you mark the 5th, improve and erase.\n" +
                        "Each time you improve, choose one of the options. Check it off; you can’t choose it again.")
                .movesInstructions("You get all the basic moves. Choose 2 brainer moves.\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**keeping an eye out**_, _**baiting a trap**_, and _**turning the tables**_.")
                .hxInstructions("Everyone introduces their characters by name, look and outlook. Take your turn.\n" +
                        "\n" +
                        "List the other characters’ names.\n" +
                        "\n" +
                        "Go around again for Hx. On your turn, ask 1, 2 or all 3:\n" +
                        "\n" +
                        "- *Which one of you has slept in my presence (knowingly or un-)?* For that character, write Hx+2.\n" +
                        "- *Which one of you have I been watching carefully, in secret?* For that character, write Hx+2.\n" +
                        "- *Which one of you most evidently dislikes and distrusts me?* For that character, write Hx+3.\n" +
                        "\n" +
                        "For everyone else, write Hx+1. You have weird insights into everyone.\n" +
                        "\n" +
                        "On the others’ turns, answer their questions as you like.\n" +
                        "\n" +
                        "At the end, choose one of the characters with the highest Hx on your sheet. Ask that player which of your stats is most interesting, and highlight it. The MC will have you highlight a second stat too.")
                .playbookUniqueCreator(brainerUniqueCreator)
                .optionalMoves(brainerOptionalMoves)
                .defaultMoves(brainerDefaultMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .defaultVehicleCount(0)
                .build();

        /* ----------------------------- CHOPPER PLAYBOOK CREATOR --------------------------------- */

        List<Move> chopperOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.CHOPPER, MoveType.CHARACTER)
                .collectList().block();

        List<Move> chopperDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.CHOPPER, MoveType.DEFAULT_CHARACTER)
                .collectList().block();

        PlaybookUniqueCreator chopperUniqueCreator = PlaybookUniqueCreator.builder()
                .type(UniqueType.GANG)
                .id(UUID.randomUUID().toString())
                // TODO: add gang creator,
                .build();

        /* ----------------------------- DRIVER PLAYBOOK CREATOR --------------------------------- */

        // Driver has no PlaybookUnique; hav Vehicles instead

        List<Move> driverOptionalMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.CHARACTER)
                .collectList().block();

        List<Move> driverDefaultMoves = moveRepository
                .findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.DEFAULT_CHARACTER)
                .collectList().block();

        GearInstructions driverGearInstructions = GearInstructions.builder()
                .id(UUID.randomUUID().toString())
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
                .improvementInstructions("Whenever you roll a highlighted stat, and whenever you reset your Hx with someone, mark an experience circle. When you mark the 5th, improve and erase.\n" +
                        "Each time you improve, choose one of the options. Check it off; you can’t choose it again.")
                .movesInstructions("You get all the basic moves. Choose 2 driver moves.\n" +
                        "You can use all the battle moves, but when you get the chance, look up _**standing overwatch**_, the _**road war**_ moves, and the rules for how vehicles suffer harm")
                .hxInstructions("Everyone introduces their characters by name, look and outlook. Take your turn.\n" +
                        "\n" +
                        "List the other characters’ names.\n" +
                        "\n" +
                        "Go around again for Hx. On your turn, ask 1, 2 or all 3:\n" +
                        "\n" +
                        "- *Which one of you got me out of some serious shit?* For that character, write Hx+1.\n" +
                        "- *Which one of you has been with me for days on the road?* For that character, write Hx+2.\n" +
                        "- *Which one of you have I caught sometimes staring out at the horizon?* For that character, write Hx+3.\n" +
                        "\n" +
                        "For everyone else, write Hx+1. You aren't naturally inclined to get too close to too many people.\n" +
                        "\n" +
                        "On the others’ turns, answer their questions as you like.\n" +
                        "\n" +
                        "At the end, choose one of the characters with the highest Hx on your sheet. Ask that player which of your stats is most interesting, and highlight it. The MC will have you highlight a second stat too.")
                .optionalMoves(driverOptionalMoves)
                .defaultMoves(driverDefaultMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .defaultVehicleCount(1)
                .build();

        playbookCreatorService.saveAll(Flux.just(angelCreator,
                battlebabePlaybookCreator,
                playbookCreatorBrainer,
                playbookCreatorDriver)).blockLast();
    }

    public void loadVehicleCreator() {
        VehicleFrame bikeFrame = VehicleFrame.builder()
                .id(UUID.randomUUID().toString())
                .frameType(VehicleFrameType.BIKE)
                .massive(0)
                .examples("Road bike, trail bike, low-rider")
                .battleOptionCount(1)
                .build();

        VehicleFrame smallFrame = VehicleFrame.builder()
                .id(UUID.randomUUID().toString())
                .frameType(VehicleFrameType.SMALL)
                .massive(1)
                .examples("Compact, buggy")
                .battleOptionCount(2)
                .build();

        VehicleFrame mediumFrame = VehicleFrame.builder()
                .id(UUID.randomUUID().toString())
                .frameType(VehicleFrameType.MEDIUM)
                .massive(2)
                .examples("Coupe, sedan, jeep, pickup, van, limo, 4x4, tractor")
                .battleOptionCount(2)
                .build();

        VehicleFrame largeFrame = VehicleFrame.builder()
                .id(UUID.randomUUID().toString())
                .frameType(VehicleFrameType.LARGE)
                .massive(3)
                .examples("Semi, bus, ambulance, construction/utility")
                .battleOptionCount(2)
                .build();

        VehicleBattleOption battleOption1 = VehicleBattleOption.builder()
                .id(UUID.randomUUID().toString())
                .battleOptionType(BattleOptionType.SPEED)
                .name("+1speed")
                .build();

        VehicleBattleOption battleOption2 = VehicleBattleOption.builder()
                .id(UUID.randomUUID().toString())
                .battleOptionType(BattleOptionType.HANDLING)
                .name("+1handling")
                .build();

        VehicleBattleOption battleOption3 = VehicleBattleOption.builder()
                .id(UUID.randomUUID().toString())
                .battleOptionType(BattleOptionType.MASSIVE)
                .name("+1massive")
                .build();

        VehicleBattleOption battleOption4 = VehicleBattleOption.builder()
                .id(UUID.randomUUID().toString())
                .battleOptionType(BattleOptionType.ARMOR)
                .name("+1armor")
                .build();

        BikeCreator bikeCreator = BikeCreator.builder()
                .id(UUID.randomUUID().toString())
                .vehicleType(VehicleType.BIKE)
                .introInstructions("By default, your bike has speed=0, handling=0, 0-armor and the massive rating of its frame.")
                .frame(bikeFrame)
                .strengths(List.of("fast",
                        "rugged",
                        "aggressive",
                        "tight",
                        "huge",
                        "responsive"))
                .looks(List.of(
                        "sleek",
                        "vintage",
                        "massively-chopped",
                        "muscular",
                        "flashy",
                        "luxe",
                        "roaring",
                        "fat-ass"))
                .weaknesses(List.of("slow",
                        "sloppy",
                        "guzzler",
                        "lazy",
                        "unreliable",
                        "cramped",
                        "loud",
                        "picky",
                        "rabbity"))
                .battleOptions(List.of(battleOption1, battleOption2))
                .build();

        CarCreator carCreator = CarCreator.builder()
                .id(UUID.randomUUID().toString())
                .vehicleType(VehicleType.CAR)
                .introInstructions("By default, your vehicle has speed=0, handling=0, 0-armor and the massive rating of its frame.")
                .frames(List.of(bikeFrame, smallFrame, mediumFrame, largeFrame))
                .strengths(List.of("fast",
                        "rugged",
                        "aggressive",
                        "tight",
                        "huge",
                        "responsive",
                        "off-road",
                        "uncomplaining",
                        "capacious",
                        "workhorse",
                        "easily repaired"))
                .looks(List.of(
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
                        "garish"))
                .weaknesses(List.of("slow",
                        "sloppy",
                        "guzzler",
                        "lazy",
                        "unreliable",
                        "cramped",
                        "loud",
                        "picky",
                        "rabbity"))
                .battleOptions(List.of(battleOption1, battleOption2, battleOption3, battleOption4))
                .build();

        VehicleCreator vehicleCreator = VehicleCreator.builder()
                .carCreator(carCreator)
                .bikeCreator(bikeCreator)
                // TODO: Add combat vehicle creator
                .build();

        vehicleCreatorService.save(vehicleCreator).block();
    }

    public void loadPlaybooks() {
        System.out.println("|| --- Loading playbooks --- ||");
        /* ----------------------------- ANGEL PLAYBOOK --------------------------------- */
        Playbook angel = new Playbook(PlaybookType.ANGEL, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "When you’re lying in the dust of Apocalypse World guts aspilled, for whom do you pray? The gods? They’re long gone. Your beloved comrades? Fuckers all, or you wouldn’t be here to begin with. Your precious old mother? She’s a darling but she can’t put an intestine back inside so it’ll stay. No, you pray for some grinning kid or veteran or just someone with a heartshocker and a hand with sutures and a 6-pack of morphine. And when that someone comes, _that’s_ an angel.", "Angels are medics. If you want everybody to love you, or at least rely on you, play an angel. Warning: if things are going well, maybe nobody will rely on you. Make interesting relationships so you’ll stay relevant. Or sabotage things, I guess.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/angel-white-transparent.png");

        Playbook battlebabe = new Playbook(PlaybookType.BATTLEBABE, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "Even in a place as dangerous as Apocalypse World, battlebabes are, well. They’re the ones you should walk away from, eyes down, but you can’t. They’re the ones like the seductive blue crackling light, y’know? You mistake looking at them for falling in love, and you get too close and it’s a zillion volts and your wings burn off like paper.", "Dangerous.\n" +
                "\n" +
                "Battlebabes are good in battle, of course, but they’re wicked social too. If you want to play somebody dangerous and provocative, play a battlebabe. Warning: you might find that you’re better at making trouble than getting out of it. If you want to play the baddest ass, play a gunlugger instead.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/battlebabe-white-transparent.png");
        Playbook brainer = new Playbook(PlaybookType.BRAINER, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "Brainers are the weird psycho psychic mindfucks of Apocalypse World. They have brain control, puppet strings, creepy hearts, dead souls, and eyes like broken things. They stand in your peripheral vision and whisper into your head, staring. They clamp lenses over your eyes and read your secrets.\n" +
                "\n" +
                "They’re just the sort of tasteful accoutrement that no well-appointed hardhold can do without.", "Brainers are spooky, weird, and really fun to play. Their moves are powerful but strange. If you want everybody else to be at least a little bit afraid of you, a brainer is a good choice. Warning: you’ll be happy anyway, but you’ll be happiest if somebody wants to have sex with you even though you’re a brainer. Angle for that if you can.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/brainer-white-transparent.png");
        Playbook chopper = new Playbook(PlaybookType.CHOPPER, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "Apocalypse World is all scarcity, of course it is. There’s not enough wholesome food, not enough untainted water, not enough security, not enough light, not enough electricity, not enough children, not enough hope.\n" +
                "\n" +
                "However, the Golden Age Past did leave us two things: enough gasoline, enough bullets. Come the end, I guess the fuckers didn’t need them like they thought they would.\n" +
                "\n" +
                "So chopper, there you are. Enough for you.", "Choppers lead biker gangs. They’re powerful but lots of their power is external, in their gang. If you want weight to throw around, play a chopper—but if you want to be really in charge, play a hardholder instead. Warning: externalizing your power means drama. Expect drama.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/chopper-white-transparent.png");
        Playbook driver = new Playbook(PlaybookType.DRIVER, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "Came the apocalypse, and the infrastructure of the Golden Age tore apart. Roads heaved and split. Lines of life and communication shattered. Cities, cut off from one another, raged like smashed anthills, then burned, then fell.\n" +
                "\n" +
                "A few living still remember it: every horizon scorching hot with civilization in flames, light to put out the stars and moon, smoke to put out the sun.\n" +
                "\n" +
                "In Apocalypse World the horizons are dark, and no roads go to them.", "Drivers have cars, meaning mobility, freedom, and places to go. If you can’t see the post-apocalypse without cars, you gotta be a driver. Warning: your loose ties can accidentally keep you out of the action. Commit to the other characters to stay in play.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/driver-white-transparent.png");
        Playbook gunlugger = new Playbook(PlaybookType.GUNLUGGER, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "Apocalypse World is a mean, ugly, violent place. Law and society have broken down completely. What’s yours is yours only while you can hold it in your hands. There’s no peace. There’s no stability but what you carve, inch by inch, out of the concrete and dirt, and then defend with murder and blood.\n" +
                "\n" +
                "Sometimes the obvious move is the right one.", "Gunluggers are the baddest asses. Their moves are simple, direct and violent. Crude, even. If you want to take no shit, play a gunlugger. Warning: like angels, if things are going well, you might be kicking your heels. Interesting relationships can keep you in the scene.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/gunlugger-white-transparent.png");
        Playbook hardholder = new Playbook(PlaybookType.HARDHOLDER, "Your holding provides for your day-to-day living, so while you’re there governing it, you need not spend barter for your lifestyle at the beginning of the session.\n" +
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
                "In times of abundance, your holding’s surplus is yours to spend personally as you see fit. (Suppose that your citizen’s lives are the more abundant too, in proportion.) You can see what 1-barter is worth, from the above. For better stuff, be prepared to make unique arrangements, probably by treating with another hardholder nearby.", "There is no government, no society, in Apocalypse World. When hardholders ruled whole continents, when they waged war on the other side of the world instead of with the hold across the burn-flat, when their armies numbered in the hundreds of thousands and they had fucking _boats_ to hold their fucking _airplanes_ on, that was the golden age of legend. Now, anyone with a concrete compound and a gang of gunluggers can claim the title. You, you got something to say about it?", "Hardholders are landlords, warlords, governors of their own little strongholds. If anybody plays a hardholder, the game’s going to have a serious and immobile home base. If you want to be the one who owns it, it better be you. Warning: don’t be a hardholder unless you want the burdens.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/hardholder-white-transparent.png");
        Playbook hocus = new Playbook(PlaybookType.HOCUS, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "Now it should be crystal fucking obvious that the gods have abandoned Apocalypse World. Maybe in the golden age, with its one nation under god and its in god we trust, maybe then the gods were real. Fucked if I know. All I know is that now they’re gone daddy gone.\n" +
                "\n" +
                "My theory is that these weird hocus fuckers, when they say “the gods,” what they really mean is the miasma left over from the explosion of psychic hate and desperation that gave Apocalypse World its birth. Friends, that’s our creator now.", "Hocuses have cult followers the way choppers have gangs. They’re strange, social, public and compelling. If you want to sway mobs, play a hocus. Warning: things are going to come looking for you. Being a cult leader means having to deal with your fucking cult.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/hocus-white-transparent.png");
        Playbook maestroD = new Playbook(PlaybookType.MAESTRO_D, "Your establishment provides for your day-to-day living, so while you’re open for business, you need not spend barter for your lifestyle at the beginning of the session.\n" +
                "\n" +
                "As a one-time expenditure, and very subject to availability, 1-barter might count for:\n" +
                "\n" +
                "- *any weapon, gear or fashion not valuable or hi-tech*\n" +
                "- *a session’s hire of a violent individual as bodyguard*\n" +
                "- *the material costs for crash resuscitation by a medic*\n" +
                "- *a few sessions’ tribute to a warlord; bribes, fees and gifts sufficient to get you into almost anyone’s presence*\n" +
                "\n" +
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "In the golden age of legend, there was this guy named Maestro. He was known for dressing up real dap and wherever he went, the people had much luxe tune. There was this other guy named Maitre d’. He was known for dressing up real dap and whever he went, the people had all the food they could eat and the fanciest of it.\n" +
                "\n" +
                "Here in Apocalypse World, those two guys are dead. They died and the fat sizzled off them, they died same as much-luxe-tune and all-you-can-eat. The maestro d’ now, he can’t give you what those guys used to could, but fuck it, maybe he can find you a little somethin somethin to take off the edge.", "The maestro d’ runs a social establishment, like a bar, a drug den or a bordello. If you want to be sexier than a hardholder, with fewer obligations and less shit to deal with, play a maestro d’. Warning: fewer obligations and less shit, not none and none.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/maestrod-white-transparent.png");
        Playbook savvyhead = new Playbook(PlaybookType.SAVVYHEAD, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "If there’s one fucking thing you can count on in Apocalypse World, it’s: things break.", "Savvyheads are techies. They have really cool abilities in the form of their workspace, and a couple of fun reality-bending moves. Play a savvyhead if you want to be powerful and useful as an ally, but maybe not the leader yourself. Warning: your workspace depends on resources, and lots of them, so make friends with everyone you can.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/savvyhead-white-transparent.png");
        Playbook skinner = new Playbook(PlaybookType.SKINNER, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "Even in the filth of Apocalypse World, there’s food that isn’t death on a spit, music that isn’t shrieking hyenas, thoughts that aren’t afraid, bodies that aren’t used meat, sex that isn’t rutting, dancing that’s real. There are moments that are more than stench, smoke, rage and blood.\n" +
                "\n" +
                "Anything beautiful left in this ugly ass world, skinners hold it. Will they share it with you? What do you offer _them_?", "Skinners are pure hot. They’re entirely social and they have great, directly manipulative moves. Play a skinner if you want to be unignorable. Warning: skinners have the tools, but unlike hardholders, choppers and hocuses, they don’t have a steady influx of motivation. You’ll have most fun if you can roll your own.\n", "https://awc-images.s3-ap-southeast-2.amazonaws.com/skinner-white-transparent.png");

        playbookService.saveAll(Flux.just(angel, battlebabe, brainer, chopper, driver, gunlugger, hardholder,
                maestroD, hocus, savvyhead, skinner)).blockLast();
    }

    private void createPlaybooks() {
        // -------------------------------------- Set up PlaybookType -------------------------------------- //
        // -------------------------------------- ANGEL -------------------------------------- //
        Playbook playbookAngel = playbookService.findByPlaybookType(PlaybookType.ANGEL).block();
        assert playbookAngel != null;

        if (playbookAngel.getCreator() == null) {
            PlaybookCreator playbookCreatorAngel = playbookCreatorService.findByPlaybookType(PlaybookType.ANGEL).block();
            assert playbookCreatorAngel != null;

            List<Name> namesAngel = nameService.findAllByPlaybookType(PlaybookType.ANGEL).collectList().block();
            assert namesAngel != null;

            List<Look> looksAngel = lookService.findAllByPlaybookType(PlaybookType.ANGEL).collectList().block();
            assert looksAngel != null;

            List<StatsOption> statsOptionsAngel = statsOptionService.findAllByPlaybookType(PlaybookType.ANGEL).collectList().block();
            assert statsOptionsAngel != null;

            statsOptionsAngel.forEach(statsOption -> playbookCreatorAngel.getStatsOptions().add(statsOption));
            namesAngel.forEach(name -> playbookCreatorAngel.getNames().add(name));
            looksAngel.forEach(look -> playbookCreatorAngel.getLooks().add(look));
            playbookCreatorService.save(playbookCreatorAngel).block();
            playbookAngel.setCreator(playbookCreatorAngel);
            playbookService.save(playbookAngel).block();
        }

        // -------------------------------------- BATTLEBABE -------------------------------------- //
        Playbook playbookBattlebabe = playbookService.findByPlaybookType(PlaybookType.BATTLEBABE).block();
        assert playbookBattlebabe != null;

        if (playbookBattlebabe.getCreator() == null) {
            PlaybookCreator playbookCreatorBattlebabe = playbookCreatorService.findByPlaybookType(PlaybookType.BATTLEBABE).block();
            assert playbookCreatorBattlebabe != null;


            List<Name> namesBattlebabe = nameService.findAllByPlaybookType(PlaybookType.BATTLEBABE).collectList().block();
            assert namesBattlebabe != null;

            List<Look> looksBattlebabe = lookService.findAllByPlaybookType(PlaybookType.BATTLEBABE).collectList().block();
            assert looksBattlebabe != null;

            List<StatsOption> statsOptionsBattlebabe = statsOptionService.findAllByPlaybookType(PlaybookType.BATTLEBABE).collectList().block();
            assert statsOptionsBattlebabe != null;

            statsOptionsBattlebabe.forEach(statsOption -> playbookCreatorBattlebabe.getStatsOptions().add(statsOption));
            namesBattlebabe.forEach(name -> playbookCreatorBattlebabe.getNames().add(name));
            looksBattlebabe.forEach(look -> playbookCreatorBattlebabe.getLooks().add(look));
            playbookCreatorService.save(playbookCreatorBattlebabe).block();
            playbookBattlebabe.setCreator(playbookCreatorBattlebabe);
            playbookService.save(playbookBattlebabe).block();
        }

        // -------------------------------------- BRAINER -------------------------------------- //
        Playbook playbookBrainer = playbookService.findByPlaybookType(PlaybookType.BRAINER).block();
        assert playbookBrainer != null;

        if (playbookBrainer.getCreator() == null) {
            PlaybookCreator playbookCreatorBrainer = playbookCreatorService.findByPlaybookType(PlaybookType.BRAINER).block();
            assert playbookCreatorBrainer != null;


            List<Name> namesBrainer = nameService.findAllByPlaybookType(PlaybookType.BRAINER).collectList().block();
            assert namesBrainer != null;

            List<Look> looksBrainer = lookService.findAllByPlaybookType(PlaybookType.BRAINER).collectList().block();
            assert looksBrainer != null;

            List<StatsOption> statsOptionsBrainer = statsOptionService.findAllByPlaybookType(PlaybookType.BRAINER).collectList().block();
            assert statsOptionsBrainer != null;

            statsOptionsBrainer.forEach(statsOption -> playbookCreatorBrainer.getStatsOptions().add(statsOption));
            namesBrainer.forEach(name -> playbookCreatorBrainer.getNames().add(name));
            looksBrainer.forEach(look -> playbookCreatorBrainer.getLooks().add(look));
            playbookCreatorService.save(playbookCreatorBrainer).block();
            playbookBrainer.setCreator(playbookCreatorBrainer);
            playbookService.save(playbookBrainer).block();
        }

        // -------------------------------------- DRIVER -------------------------------------- //
        Playbook playbookDriver = playbookService.findByPlaybookType(PlaybookType.DRIVER).block();
        assert playbookDriver != null;

        if (playbookDriver.getCreator() == null) {
            PlaybookCreator playbookCreatorDriver = playbookCreatorService.findByPlaybookType(PlaybookType.DRIVER).block();
            assert playbookCreatorDriver != null;

            List<Name> namesDriver = nameService.findAllByPlaybookType(PlaybookType.DRIVER).collectList().block();
            assert namesDriver != null;


            List<Look> looksDriver = lookService.findAllByPlaybookType(PlaybookType.DRIVER).collectList().block();
            assert looksDriver != null;

            List<StatsOption> statsOptionsDriver = statsOptionService.findAllByPlaybookType(PlaybookType.DRIVER).collectList().block();
            assert statsOptionsDriver != null;

            statsOptionsDriver.forEach(statsOption -> playbookCreatorDriver.getStatsOptions().add(statsOption));
            namesDriver.forEach(name -> playbookCreatorDriver.getNames().add(name));
            looksDriver.forEach(look -> playbookCreatorDriver.getLooks().add(look));
            playbookCreatorService.save(playbookCreatorDriver).block();
            playbookDriver.setCreator(playbookCreatorDriver);
            playbookService.save(playbookDriver).block();
        }
    }
}
