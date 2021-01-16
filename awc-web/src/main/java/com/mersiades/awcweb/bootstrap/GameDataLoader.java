package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.enums.*;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniquecreators.AngelKitCreator;
import com.mersiades.awcdata.models.uniquecreators.BrainerGearCreator;
import com.mersiades.awcdata.models.uniquecreators.CustomWeaponsCreator;
import com.mersiades.awcdata.repositories.*;
import com.mersiades.awcdata.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.*;

import static com.mersiades.awcdata.enums.Stats.*;

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

    public GameDataLoader(PlaybookCreatorService playbookCreatorService,
                          PlaybookService playbookService,
                          NameService nameService,
                          LookService lookService,
                          StatsOptionService statsOptionService,
                          MoveService moveService,
                          StatModifierService statModifierService) {
        this.playbookCreatorService = playbookCreatorService;
        this.playbookService = playbookService;
        this.nameService = nameService;
        this.lookService = lookService;
        this.statsOptionService = statsOptionService;
        this.moveService = moveService;
        this.statModifierService = statModifierService;
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

        // 'Create if empty' conditionality is embedded in the createPlaybooks() method
        createPlaybooks();

        System.out.println("Look count: " + Objects.requireNonNull(lookRepository.count().block()).toString());
        System.out.println("Move count: " + Objects.requireNonNull(moveRepository.count().block()).toString());
        System.out.println("Name count: " + Objects.requireNonNull(nameRepository.count().block()).toString());
        System.out.println("PlaybookCreator count: " + Objects.requireNonNull(playbookCreatorRepository.count().block()).toString());
        System.out.println("Playbook count: " + Objects.requireNonNull(playbookRepository.count().block()).toString());
    }

    private void loadMoves() {
        System.out.println("|| --- Loading basic moves --- ||");
        /* ----------------------------- BASIC MOVES --------------------------------- */
        Move doSomethingUnderFire = Move.builder()
                .name("DO SOMETHING UNDER FIRE")
                .description("When you _**do something under fire**_, or dig in to endure fire, roll+cool.\n" +
                        "\n" +
                        "On a 10+, you do it.\n" +
                        "\n" +
                        "On a 7–9, you flinch, hesitate, or stall: the MC can offer you a worse outcome, a hard bargain, or an ugly choice.\n" +
                        "\n" +
                        "On a miss, be prepared for the worst.")
                .kind(MoveKinds.BASIC)
                .stat(Stats.COOL)
                .playbook(null)
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
                .kind(MoveKinds.BASIC)
                .stat(HARD)
                .playbook(null)
                .build();
        Move sucker = Move.builder()
                .name("SUCKER SOMEONE")
                .description("When you _**attack someone unsuspecting or helpless**_, ask the MC if you could miss. If you could, treat it as going aggro, but your victim has no choice to cave and do what you want. If you couldn’t, you simply inflict harm as established.")
                .kind(MoveKinds.BASIC)
                .stat(null)
                .playbook(null)
                .build();
        Move doBattle = Move.builder()
                .name("DO BATTLE")
                .description("When you’re _**in battle**_, you can bring the battle moves into play.")
                .kind(MoveKinds.BASIC)
                .stat(null)
                .playbook(null)
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
                .kind(MoveKinds.BASIC)
                .stat(Stats.HOT)
                .playbook(null)
                .build();
        Move helpOrInterfere = Move.builder()
                .name("HELP OR INTERFERE WITH SOMEONE")
                .description("When you _**help**_ or _**interfere**_ with someone who’s making a roll, roll+Hx.\n" +
                        "\n" +
                        "On a 10+, they take +2 (help) or -2 (interfere) to their roll.\n" +
                        "\n" +
                        "On a 7–9, they take +1 (help) or -1 (interfere) to their roll.\n" +
                        "\n" +
                        "On a miss, be prepared for the worst.")
                .kind(MoveKinds.BASIC)
                .stat(Stats.HX)
                .playbook(null)
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
                .kind(MoveKinds.BASIC)
                .stat(Stats.SHARP)
                .playbook(null)
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
                .kind(MoveKinds.BASIC)
                .stat(Stats.SHARP)
                .playbook(null)
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
                .kind(MoveKinds.BASIC)
                .stat(Stats.WEIRD)
                .playbook(null)
                .build();
        Move lifestyleAndGigs = Move.builder()
                .name("LIFESTYLE AND GIGS")
                .description("_**At the beginning of the session**_, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions. If you need jingle during a session, tell the MC you’d like to work a gig.")
                .kind(MoveKinds.BASIC)
                .stat(null)
                .playbook(null)
                .build();
        Move sessionEnd = Move.builder()
                .name("SESSION END")
                .description("_**At the end of every session**_, choose a character who knows you better than they used to. If there’s more than one, choose one at your whim.\n" +
                        "\n" +
                        "Tell that player to add +1 to their Hx with you on their sheet. If this brings them to Hx+4, they reset to Hx+1 (and therefore mark experience).\n" +
                        "\n" +
                        "If no one knows you better, choose a character who doesn’t know you as well as they thought, or choose any character at your whim. Tell that player to take -1 to their Hx with you on their sheet. If this brings them to Hx -3, they reset to Hx=0 (and therefore mark experience).")
                .kind(MoveKinds.BASIC)
                .stat(null)
                .playbook(null)
                .build();

        moveService.saveAll(Flux.just(doSomethingUnderFire, goAggro, sucker, doBattle, seduceOrManip, helpOrInterfere,
                readASitch, readAPerson, openBrain, lifestyleAndGigs, sessionEnd)).blockLast();

        System.out.println("|| --- Loading peripheral moves --- ||");
        /* ----------------------------- PERIPHERAL MOVES --------------------------------- */
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
                .kind(MoveKinds.PERIPHERAL)
                .stat(null)
                .playbook(null)
                .build();
        Move inflictHarm = Move.builder()
                .name("INFLICT HARM ON PC")
                .description("When you _**inflict harm on another player’s character**_, the other character gets +1Hx with you (on their sheet) for every segment of harm you inflict.\n" +
                        "\n" +
                        "If this brings them to Hx+4, they reset to Hx+1 as usual, and therefore mark experience.")
                .kind(MoveKinds.PERIPHERAL)
                .stat(null)
                .playbook(null)
                .build();
        Move healPcHarm = Move.builder()
                .name("HEAL PC HARM")
                .description("When you _**heal another player’s character’s harm**_, you get +1Hx with them (on your sheet) for every segment of harm you heal.\n" +
                        "\n" +
                        "If this brings you to Hx+4, you reset to Hx+1 as usual, and therefore mark experience.")
                .kind(MoveKinds.PERIPHERAL)
                .stat(null)
                .playbook(null)
                .build();
        Move giveBarter = Move.builder()
                .name("GIVE BARTER")
                .description("When you _**give 1-barter to someone, but with strings attached**_, it counts as manipulating them and hitting the roll with a 10+, no leverage or roll required.")
                .kind(MoveKinds.PERIPHERAL)
                .stat(null)
                .playbook(null)
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
                .kind(MoveKinds.PERIPHERAL)
                .stat(Stats.SHARP)
                .playbook(null)
                .build();
        Move makeWantKnown = Move.builder()
                .name("MAKE WANT KNOWN")
                .description("When you _**make known that you want a thing and drop jingle to speed it on its way**_, roll+barter spent (max roll+3). It has to be a thing you could legitimately get this way.\n" +
                        "\n" +
                        "On a 10+ it comes to you, no strings attached.\n" +
                        "\n" +
                        "On a 7–9 it comes to you, or something pretty close.\n" +
                        "\n" +
                        "On a miss, it comes to you, but with strings very much attached.")
                .kind(MoveKinds.PERIPHERAL)
                .stat(null)
                .playbook(null)
                .build();
        Move insight = Move.builder()
                .name("INSIGHT")
                .description("When you are able to go to someone for _**insight**_, ask them what they think your best course is, and the MC will tell you.\n" +
                        "\n" +
                        "If you pursue that course, take +1 to any rolls you make in the pursuit. If you pursue that course but don’t accomplish your ends, you mark experience.")
                .kind(MoveKinds.PERIPHERAL)
                .stat(null)
                .playbook(null)
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
                .kind(MoveKinds.PERIPHERAL)
                .stat(Stats.WEIRD)
                .playbook(null)
                .build();
        Move changeHighlightedStats = Move.builder()
                .name("CHANGE HIGHLIGHTED STATS")
                .description("_**At the beginning of any session**_, or at the end if you forgot, anyone can say, “hey, let’s change highlighted stats.” When someone says it, do it.\n" +
                        "\n" +
                        "Go around the circle again, following the same procedure you used to highlight them in the first place: the high-Hx player highlights one stat, and the MC highlight another.")
                .kind(MoveKinds.PERIPHERAL)
                .stat(null)
                .playbook(null)
                .build();

        moveService.saveAll(Flux.just(sufferHarm, inflictHarm, healPcHarm, giveBarter, goToMarket, makeWantKnown,
                insight, augury, changeHighlightedStats)).blockLast();

        System.out.println("|| --- Loading battle moves --- ||");
        /* ----------------------------- BATTLE MOVES --------------------------------- */
        Move exchangeHarm = Move.builder()
                .name("EXCHANGE HARM")
                .description("When you _**exchange harm**_, both sides simultaneously inflict and suffer harm as established:\n" +
                        "\n" +
                        "- *You inflict harm equal to the harm rating of your weapon, minus the armor rating of your enemy’s armor.*\n" +
                        "- *You suffer harm equal to the harm rating of your enemy’s weapon, minus the armor rating of your own armor.*")
                .kind(MoveKinds.BATTLE)
                .stat(null)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(HARD)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(HARD)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(HARD)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(HARD)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(HARD)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(HARD)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(HARD)
                .playbook(null)
                .build();
        Move standOverwatch = Move.builder()
                .name("STAND OVERWATCH")
                .description("When you _**stand overwatch**_ for an ally, roll+cool. On a hit, if anyone attacks or interferes with your ally, you attack them and inflict harm as established, as well as warning your ally.\n" +
                        "\n" +
                        "On a 10+, choose 1:\n" +
                        "\n" +
                        "- *...And you inflict your harm before they can carry out their attack or interference.*\n" +
                        "- *...And you inflict terrible harm (+1harm).*\n" +
                        "\n" +
                        "On a miss, you are able to warn your ally but not attack your enemy.")
                .kind(MoveKinds.BATTLE)
                .stat(COOL)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(SHARP)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(COOL)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(COOL)
                .playbook(null)
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
                .kind(MoveKinds.BATTLE)
                .stat(COOL)
                .playbook(null)
                .build();
        Move catOrMouse = Move.builder()
                .name("CAT OR MOUSE")
                .description("When _**it’s not certain whether you’re the cat or the mouse**_, roll+sharp. On a hit, you decide which you are.\n" +
                        "\n" +
                        "On a 10+, you take +1forward as well.\n" +
                        "\n" +
                        "On a miss, you’re the mouse.")
                .kind(MoveKinds.BATTLE)
                .stat(SHARP)
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
                .kind(MoveKinds.ROAD_WAR)
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
                .kind(MoveKinds.ROAD_WAR)
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
                .kind(MoveKinds.ROAD_WAR)
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
                .kind(MoveKinds.ROAD_WAR)
                .stat(COOL)
                .playbook(null)
                .build();
        Move shoulderAnotherVehicle = Move.builder()
                .name("SHOULDER ANOTHER VEHICLE")
                .description("To _**shoulder another vehicle**_, roll+cool. On a hit, you shoulder it aside, inflicting v-harm as established.\n" +
                        "\n" +
                        "On a 10+, you inflict v-harm+1.\n" +
                        "\n" +
                        "On a miss, it shoulders you instead, inflicting v-harm as established.")
                .kind(MoveKinds.ROAD_WAR)
                .stat(COOL)
                .playbook(null)
                .build();

        moveService.saveAll(Flux.just(boardAMovingVehicle, outdistanceAnotherVehicle, overtakeAnotherVehicle,
                dealWithBadTerrain, shoulderAnotherVehicle)).blockLast();

        /* ----------------------------- ANGEL MOVES --------------------------------- */
        System.out.println("|| --- Loading Angel moves --- ||");
        RollModifier sixthSenseMod = RollModifier.builder().id(UUID.randomUUID().toString()).movesToModify(Collections.singletonList(openBrain)).statToRollWith(Collections.singletonList(Stats.SHARP)).build();
        RollModifier profCompassionMod = RollModifier.builder().id(UUID.randomUUID().toString()).movesToModify(Collections.singletonList(helpOrInterfere)).statToRollWith(Collections.singletonList(Stats.SHARP)).build();
        Move angelSpecial = new Move("ANGEL SPECIAL", "If you and another character have sex, your Hx with them on your sheet goes immediately to +3, and they immediately get +1 to their Hx with you on their sheet. If that brings their Hx with you to +4, they reset it to +1 instead, as usual, and so mark experience.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move sixthSense = Move.builder().name("SIXTH SENSE").description("_**Sixth sense**_: when you open your brain to the world’s psychic maelstrom, roll+sharp instead of +weird.").rollModifier(sixthSenseMod).kind(MoveKinds.CHARACTER).playbook(Playbooks.ANGEL).build();
        Move infirmary = new Move("INFIRMARY", "_**Infirmary**_: you get an infirmary, a workspace with life support, a drug lab and a crew of 2 (Shigusa & Mox, maybe). Get patients into it and you can work on them like a savvyhead on tech (_cf_).", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move profCompassion = Move.builder().name("PROFESSIONAL COMPASSION").description("_**Professional compassion**_: you can roll+sharp instead of roll+Hx when you help someone who’s rolling.").rollModifier(profCompassionMod).kind(MoveKinds.CHARACTER).playbook(Playbooks.ANGEL).build();
        Move battlefieldGrace = new Move("BATTLEFIELD GRACE", "_**Battlefield grace**_: while you are caring for people, not fighting, you get +1armor.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move healingTouch = new Move("HEALING TOUCH", "_**Healing touch**_: when you put your hands skin-to-skin on a wounded person and open your brain to them, roll+weird.\n" +
                "\n" +
                "On a 10+, heal 1 segment.\n" +
                "\n" +
                "On a 7–9, heal 1 segment, but you’re also opening your brain, so roll that move next.\n" +
                "\n" +
                "On a miss: first, you don’t heal them. Second, you’ve opened both your brain and theirs to the world’s psychic maelstrom, without protection or preparation. For you, and for your patient if your patient’s a fellow player’s character, treat it as though you’ve made that move and missed the roll. For patients belonging to the MC, their experience and fate are up to the MC.\n", Stats.WEIRD, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move touchedByDeath = new Move("TOUCHED BY DEATH", "_**Touched by death**_: when someone is unconscious in your care, you can use them for _**augury**_. When someone has died in your care, you can use their body for _**augury**_.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);

        moveService.saveAll(Flux.just(angelSpecial, sixthSense, infirmary, profCompassion,
                battlefieldGrace, healingTouch, touchedByDeath)).blockLast();

        /* ----------------------------- ANGEL KIT MOVES --------------------------------- */

        Move stabilizeAndHeal = Move.builder().name("STABILIZE AND HEAL SOMEONE").description("_**stabilize and heal someone at 9:00 or past**_: roll+stock spent.\n" +
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
                "On a miss, they take 1-harm instead.").playbook(Playbooks.ANGEL).kind(MoveKinds.UNIQUE).build();
        Move speedTheRecoveryOfSomeone = Move.builder().name("SPEED THE RECOVERY OF SOMEONE")
                .description("_**speed the recovery of someone at 3:00 or 6:00**_: don’t roll. They choose: you spend 1-stock and they spend 4 days (3:00) or 1 week (6:00) blissed out on chillstabs, immobile but happy, or else they do their time in agony like everyone else.")
                .playbook(Playbooks.ANGEL).kind(MoveKinds.UNIQUE).build();
        Move reviveSomeone = Move.builder().name("REVIVE SOMEONE").description("_**revive someone whose life has become untenable**_, spend 2-stock. They come back, but you get to choose how they come back. Choose from the regular “when life is untenable” list, or else choose 1:\n" +
                "\n" +
                "- *They come back in your deep, deep debt.*\n" +
                "- *They come back with a prosthetic (you detail).*\n" +
                "- *You and they both come back with +1weird (max weird+3).*")
                .playbook(Playbooks.ANGEL).kind(MoveKinds.UNIQUE).build();
        Move treatAnNpc = Move.builder().name("TREAT AN NPC").description("_**treat an NPC**_: spend 1-stock. They’re stable now and they’ll recover in time. ")
                .playbook(Playbooks.ANGEL).kind(MoveKinds.UNIQUE).build();

        moveService.saveAll(Flux.just(stabilizeAndHeal, speedTheRecoveryOfSomeone, reviveSomeone, treatAnNpc)).blockLast();

        /* ----------------------------- BATTLEBABE MOVES --------------------------------- */
        System.out.println("|| --- Loading Battlebabe moves --- ||");
        RollModifier iceColdMod = RollModifier.builder().id(UUID.randomUUID().toString()).movesToModify(Collections.singletonList(goAggro)).statToRollWith(Arrays.asList(HARD, Stats.HX)).build();
        Move battlebabeSpecial = Move.builder().name("BATTLEBABE SPECIAL").description("If you and another character have sex, nullify the other character’s sex move. Whatever it is, it just doesn’t happen.").stat(null).kind(MoveKinds.CHARACTER).playbook(Playbooks.BATTLEBABE).build();
        Move dangerousAndSexy = Move.builder().name("DANGEROUS & SEXY").description("_**Dangerous & sexy**_: when you enter into a charged situation, roll+hot.\n" +
                "\n" +
                "On a 10+, hold 2. On a 7–9, hold 1.\n" +
                "\n" +
                "Spend your hold 1 for 1 to make eye contact with an NPC present, who freezes or flinches and can’t take action until you break it off.\n" +
                "\n" +
                "On a miss, your enemies identify you immediately as their foremost threat.").stat(Stats.HOT).kind(MoveKinds.CHARACTER).playbook(Playbooks.BATTLEBABE).build();
        Move iceCold = Move.builder().name("ICE COLD").description("_**Ice cold**_: when you go aggro on an NPC, roll+cool instead of roll+hard. When you go aggro on another player’s character, roll+Hx instead of roll+hard.").rollModifier(iceColdMod).stat(null).kind(MoveKinds.CHARACTER).playbook(Playbooks.BATTLEBABE).build();
        Move merciless = Move.builder().name("MERCILESS").description("_**Merciless**_: when you inflict harm, inflict +1harm.").stat(null).kind(MoveKinds.CHARACTER).playbook(Playbooks.BATTLEBABE).build();
        Move visionsOfDeath = Move.builder().name("VISIONS OF DEATH").description("_**Visions of death**_: when you go into battle, roll+weird.\n" +
                "\n" +
                "On a 10+, name one person who’ll die and one who’ll live.\n" +
                "\n" +
                "On a 7–9, name one person who’ll die OR one person who’ll live. Don’t name a player’s character; name NPCs only.\n" +
                "\n" +
                "The MC will make your vision come true, if it’s even remotely possible.\n" +
                "\n" +
                "On a miss, you foresee your own death, and accordingly take -1 throughout the battle.").stat(Stats.WEIRD).kind(MoveKinds.CHARACTER).playbook(Playbooks.BATTLEBABE).build();
        Move perfectInstincts = Move.builder().name("PERFECT INSTINCTS").description("_**Perfect instincts**_: when you’ve read a charged situation and you’re acting on the MC’s answers, take +2 instead of +1.").stat(null).kind(MoveKinds.CHARACTER).playbook(Playbooks.BATTLEBABE).build();
        Move impossibleReflexes = Move.builder().name("IMPOSSIBLE REFLEXES").description("_**Impossible reflexes**_: the way you move unencumbered counts as armor. If you’re naked or nearly naked, 2-armor; if you’re wearing non-armor fashion, 1-armor. If you’re wearing armor, use it instead.").stat(null).kind(MoveKinds.CHARACTER).playbook(Playbooks.BATTLEBABE).build();

        moveService.saveAll(Flux.just(battlebabeSpecial, dangerousAndSexy, iceCold, merciless, visionsOfDeath, perfectInstincts, impossibleReflexes)).blockLast();

        /* ----------------------------- BRAINER MOVES --------------------------------- */
        System.out.println("|| --- Loading Brainer moves --- ||");
        RollModifier lustMod = RollModifier.builder().id(UUID.randomUUID().toString()).movesToModify(Collections.singletonList(seduceOrManip)).statToRollWith(Collections.singletonList(Stats.WEIRD)).build();
        StatModifier attunementMod = StatModifier.builder().id(UUID.randomUUID().toString()).statToModify(Stats.WEIRD).modification(1).build();
        StatModifier savedAttunementMod = statModifierService.save(attunementMod).block();
        Move brainerSpecial = Move.builder().name("BRAINER SPECIAL").description("If you and another character have sex, you automatically do a _**deep brain scan**_ on them, whether you have the move or not. Roll+weird as normal. However, the MC chooses which questions the other character’s player answers.").stat(null).kind(MoveKinds.CHARACTER).playbook(Playbooks.BRAINER).build();
        Move unnaturalLust = Move.builder().name("UNNATURAL LUST TRANSFIXION").description("_**Unnatural lust transfixion**_: when you try to seduce someone, roll+weird instead of roll+hot.").stat(null).rollModifier(lustMod).kind(MoveKinds.CHARACTER).playbook(Playbooks.BRAINER).build();
        Move brainReceptivity = Move.builder().name("CASUAL BRAIN RECEPTIVITY").description("_**Casual brain receptivity**_: when you read someone, roll+weird instead of roll+sharp. Your victim has to be able to see you, but you don’t have to interact.").stat(null).kind(MoveKinds.CHARACTER).playbook(Playbooks.BRAINER).build();
        Move brainAttunement = Move.builder().name("PRETERNATURAL BRAIN ATTUNEMENT").description("_**Preternatural at-will brain attunement**_: you get +1weird (weird+3).\n").statModifier(savedAttunementMod).stat(null).kind(MoveKinds.CHARACTER).playbook(Playbooks.BRAINER).build();
        Move brainScan = Move.builder().name("DEEP BRAIN SCAN").description("_**Deep brain scan**_: when you have time and physical intimacy with someone — mutual intimacy like holding them in your arms, or 1-sided intimacy like they’re restrained to a table — you can read them more deeply than normal. Roll+weird.\n" +
                "\n" +
                "On a 10+, hold 3. On a 7–9, hold 1. While you’re reading them, spend your hold to ask their player questions, 1 for 1:\n" +
                "\n" +
                "- *What was your character’s lowest moment?*\n" +
                "- *For what does your character crave forgiveness, and of whom?*\n" +
                "- *What are your character’s secret pains?*\n" +
                "- *In what ways are your character’s mind and soul vulnerable?*\n" +
                "\n" +
                "On a miss, you inflict 1-harm (ap) upon your subject, to no benefit").stat(Stats.WEIRD).kind(MoveKinds.CHARACTER).playbook(Playbooks.BRAINER).build();
        Move whisperProjection = Move.builder().name("DIRECT BRAIN WHISPER PROJECTION").description("_**Direct-brain whisper projection**_: you can roll+weird to get the effects of going aggro, without going aggro. Your victim has to be able to see you, but you don’t have to interact. If your victim forces your hand, your mind counts as a weapon (1-harm ap close loud-optional).").stat(null).kind(MoveKinds.CHARACTER).playbook(Playbooks.BRAINER).build();
        Move puppetStrings = Move.builder().name("IN BRAIN PUPPET STRINGS").description("_**In-brain puppet strings**_: when you have time and physical intimacy with someone — again, mutual or 1-sided — you can plant a command inside their mind. Roll+weird.\n" +
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
                "On a miss, you inflict 1-harm (ap) upon your subject, to no benefit.").stat(Stats.WEIRD).kind(MoveKinds.CHARACTER).playbook(Playbooks.BRAINER).build();

        moveService.saveAll(Flux.just(brainerSpecial, unnaturalLust, brainReceptivity, brainAttunement, brainScan, whisperProjection, puppetStrings)).blockLast();

        /* ----------------------------- CHOPPER MOVES --------------------------------- */
        System.out.println("|| --- Loading Chopper moves --- ||");
        Move chopperSpecial = Move.builder().name("CHOPPER_SPECIAL").description("If you and another character have sex, they immediately change their sheet to say Hx+3 with you. They also choose whether to give you -1 or +1 to your Hx with them, on your sheet.").stat(null).kind(MoveKinds.CHARACTER).playbook(Playbooks.CHOPPER).build();
        Move packAlpha = Move.builder().name("PACK ALPHA").description("_**Pack alpha**_: when you try to impose your will on your gang, roll+hard.\n" +
                "\n" +
                "On a 10+, all 3. On a 7–9, choose 1:\n" +
                "\n" +
                "- *They do what you want (otherwise, they refuse)*\n" +
                "- *They don’t fight back over it (otherwise, they do fight back)*\n" +
                "- *You don’t have to make an example of one of them (otherwise, you must)*\n" +
                "\n" +
                "On a miss, someone in your gang makes a bid, idle or serious, to replace you for alpha.").stat(HARD).kind(MoveKinds.CHARACTER).playbook(Playbooks.CHOPPER).build();
        Move fuckingThieves = Move.builder().name("FUCKING THIEVES").description("_**Fucking thieves**_: when you have your gang search their pockets and saddlebags for something, roll+hard. It has to be something small enough to fit.\n" +
                "\n" +
                "On a 10+, one of you happens to have just the thing, or close enough.\n" +
                "\n" +
                "On a 7–9, one of you happens to have something pretty close, unless what you’re looking for is hi-tech, in which case no dice.\n" +
                "\n" +
                "On a miss, one of you used to have just the thing, but it turns out that some asswipe stole it from you.").stat(HARD).kind(MoveKinds.CHARACTER).playbook(Playbooks.CHOPPER).build();

        moveService.saveAll(Flux.just(chopperSpecial, packAlpha, fuckingThieves)).blockLast();

        /* ----------------------------- DRIVER MOVES --------------------------------- */
        System.out.println("|| --- Loading Driver moves --- ||");
        RollModifier weatherEyeMod = RollModifier.builder().id(UUID.randomUUID().toString()).movesToModify(Collections.singletonList(openBrain)).statToRollWith(Collections.singletonList(Stats.COOL)).build();
        Move driverSpecial = Move.builder().name("DRIVER SPECIAL").description("If you and another character have sex, roll+cool.\n" +
                "\n" +
                "On a 10+, it’s cool, no big deal.\n" +
                "\n" +
                "On a 7–9, give them +1 to their Hx with you on their sheet, but give yourself -1 to your Hx with them on yours.\n" +
                "\n" +
                "On a miss, you gotta go: take -1 ongoing, until you prove that it’s not like they own you or nothing.").stat(Stats.COOL).kind(MoveKinds.CHARACTER).playbook(Playbooks.DRIVER).build();
        Move combatDriver = Move.builder().name("COMBAT DRIVER").description("_**Combat driver**_: when you use your vehicle as a weapon, inflict +1harm. When you inflict v-harm, add +1 to your target’s roll. When you suffer v-harm, take -1 to your roll.").kind(MoveKinds.CHARACTER).playbook(Playbooks.DRIVER).build();
        Move eyeOnTheDoor = Move.builder().name("EYE ON THE DOOR").description("_**Eye on the door**_: name your escape route and roll+cool.\n" +
                "\n" +
                "On a 10+, you’re gone.\n" +
                "On a 7–9, you can go or stay, but if you go it costs you: leave something behind or take something with you, the MC will tell you what.\n" +
                "\n" +
                "On a miss, you’re caught vulnerable, half in and half out.").stat(Stats.COOL).kind(MoveKinds.CHARACTER).playbook(Playbooks.DRIVER).build();
        Move weatherEye = Move.builder().name("WEATHER EYE").description("_**Weather eye**_: when you open your brain to the world’s psychic maelstrom, roll+cool instead of roll+weird.").rollModifier(weatherEyeMod).kind(MoveKinds.CHARACTER).playbook(Playbooks.DRIVER).build();
        Move reputation = Move.builder().name("REPUTATION").description("_**Reputation**_: when you meet someone important (your call), roll+cool.\n" +
                "\n" +
                "On a hit, they’ve heard of you, and you say what they’ve heard; the MC has them respond accordingly.\n" +
                "\n" +
                "On a 10+, you take +1forward for dealing with them as well.\n" +
                "\n" +
                "On a miss, they’ve heard of you, but the MC decides what they’ve heard.").stat(Stats.COOL).kind(MoveKinds.CHARACTER).playbook(Playbooks.DRIVER).build();
        Move daredevil = Move.builder().name("DAREDEVIL").description("_**Daredevil**_: if you go straight into danger without hedging your bets, you get +1armor. If you happen to be leading a gang or convoy, it gets +1armor too.").kind(MoveKinds.CHARACTER).playbook(Playbooks.DRIVER).build();
        Move collector = Move.builder().name("COLLECTOR").description("_**Collector**_: you get 2 additional cars (you detail).").kind(MoveKinds.CHARACTER).playbook(Playbooks.DRIVER).build();
        Move myOtherCarIsATank = Move.builder().name("MY OTHER CAR IS A TANK").description("_**My other car is a tank**_: you get a specialized battle vehicle (detail with the MC).").kind(MoveKinds.CHARACTER).playbook(Playbooks.DRIVER).build();

        moveService.saveAll(Flux.just(driverSpecial, combatDriver, eyeOnTheDoor, weatherEye, reputation, daredevil, collector, myOtherCarIsATank)).blockLast();

        /* ----------------------------- GUNLUGGER MOVES --------------------------------- */
        System.out.println("|| --- Loading Gunlugger moves --- ||");
        RollModifier battleHardenedMod = RollModifier.builder().id(UUID.randomUUID().toString()).movesToModify(Arrays.asList(doSomethingUnderFire, standOverwatch)).statToRollWith(Collections.singletonList(HARD)).build();
        RollModifier battlefieldInstinctsMod = RollModifier.builder().id(UUID.randomUUID().toString()).movesToModify(Collections.singletonList(openBrain)).statToRollWith(Collections.singletonList(HARD)).build();
        StatModifier insanoMod = StatModifier.builder().id(UUID.randomUUID().toString()).statToModify(HARD).modification(1).build();
        Move gunluggerSpecial = Move.builder().name("GUNLUGGER SPECIAL").description("If you and another character have sex, you take +1 forward. At your option, they take +1 forward too.").kind(MoveKinds.CHARACTER).playbook(Playbooks.GUNLUGGER).build();
        Move battleHardened = Move.builder().name("BATTLE-HARDENED").description("_**Battle-hardened**_: when you act under fire, or when you stand overwatch, roll+hard instead of roll+cool.").rollModifier(battleHardenedMod).kind(MoveKinds.CHARACTER).playbook(Playbooks.GUNLUGGER).build();
        Move fuckThisShit = Move.builder().name("FUCK THIS SHIT").description("_**Fuck this shit**_: name your escape route and roll+hard.\n" +
                "\n" +
                "On a 10+, sweet, you’re gone.\n" +
                "\n" +
                "On a 7–9, you can go or stay, but if you go it costs you: leave something behind, or take something with you, the MC will tell you what.\n" +
                "\n" +
                "On a miss, you’re caught vulnerable, half in and half out.").stat(HARD).kind(MoveKinds.CHARACTER).playbook(Playbooks.GUNLUGGER).build();
        Move battlefieldInstincts = Move.builder().name("BATTLEFIELD INSTINCTS").description("_**Battlefield instincts**_: when you open your brain to the world’s psychic maelstrom, roll+hard instead of roll+weird, but only in battle.").rollModifier(battlefieldInstinctsMod).kind(MoveKinds.CHARACTER).playbook(Playbooks.GUNLUGGER).build();
        Move insanoLikeDrano = Move.builder().name("INSANO LIKE DRANO").description("_**Insano like Drano**_: you get +1hard (hard+3).").statModifier(insanoMod).kind(MoveKinds.CHARACTER).playbook(Playbooks.GUNLUGGER).build();
        Move preparedForTheInevitable = Move.builder().name("PREPARED FOR THE INEVITABLE").description("_**Prepared for the inevitable**_: you have a well-stocked and high-quality first aid kit. It counts as an angel kit (cf ) with a capacity of 2-stock.").kind(MoveKinds.CHARACTER).playbook(Playbooks.GUNLUGGER).build();
        Move bloodcrazed = Move.builder().name("BLOODCRAZED").description("_**Bloodcrazed**_: whenever you inflict harm, inflict +1harm.").kind(MoveKinds.CHARACTER).playbook(Playbooks.GUNLUGGER).build();
        Move notToBeFuckedWith = Move.builder().name("NOT TO BE FUCKED WITH").description("_**NOT TO BE FUCKED WITH**_: in battle, you count as a small gang, with harm and armor according to your gear.").kind(MoveKinds.CHARACTER).playbook(Playbooks.GUNLUGGER).build();

        moveService.saveAll(Flux.just(gunluggerSpecial, battleHardened, fuckThisShit, battlefieldInstincts, insanoLikeDrano, preparedForTheInevitable, bloodcrazed, notToBeFuckedWith)).blockLast();
    }

    private void loadNames() {
        System.out.println("|| --- Loading playbook names --- ||");
        /* ----------------------------- ANGEL NAMES --------------------------------- */
        Name dou = new Name(Playbooks.ANGEL, "Dou");
        Name bon = new Name(Playbooks.ANGEL, "Bon");
        Name abe = new Name(Playbooks.ANGEL, "Abe");
        Name boo = new Name(Playbooks.ANGEL, "Boo");
        Name t = new Name(Playbooks.ANGEL, "T");
        Name kal = new Name(Playbooks.ANGEL, "Kal");
        Name charName = new Name(Playbooks.ANGEL, "Char");
        Name jav = new Name(Playbooks.ANGEL, "Jav");
        Name ruth = new Name(Playbooks.ANGEL, "Ruth");
        Name wei = new Name(Playbooks.ANGEL, "Wei");
        Name jay = new Name(Playbooks.ANGEL, "Jay");
        Name nee = new Name(Playbooks.ANGEL, "Nee");
        Name kim = new Name(Playbooks.ANGEL, "Kim");
        Name lan = new Name(Playbooks.ANGEL, "Lan");
        Name di = new Name(Playbooks.ANGEL, "Di");
        Name dez = new Name(Playbooks.ANGEL, "Dez");
        Name doc = new Name(Playbooks.ANGEL, "Doc");
        Name core = new Name(Playbooks.ANGEL, "Core");
        Name wheels = new Name(Playbooks.ANGEL, "Wheels");
        Name buzz = new Name(Playbooks.ANGEL, "Buzz");
        Name key = new Name(Playbooks.ANGEL, "Key");
        Name line = new Name(Playbooks.ANGEL, "Line");
        Name gabe = new Name(Playbooks.ANGEL, "Gabe");
        Name biz = new Name(Playbooks.ANGEL, "Biz");
        Name bish = new Name(Playbooks.ANGEL, "Bish");
        Name inch = new Name(Playbooks.ANGEL, "Inch");
        Name grip = new Name(Playbooks.ANGEL, "Grip");
        Name setter = new Name(Playbooks.ANGEL, "Setter");

        nameService.saveAll(Flux.just(dou, bon, abe, boo, t, kal, charName, jav, ruth, wei, jay, nee,
                kim, lan, di, dez, core, wheels, doc, buzz, key, line, gabe, biz, bish, inch, grip, setter))
                .blockLast();

        /* ----------------------------- BATTLEBABE NAMES --------------------------------- */
        Name snow = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Snow").build();
        Name crimson = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Crimson").build();
        Name shadow = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Shadow").build();
        Name azure = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Azure").build();
        Name midnight = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Midnight").build();
        Name scarlet = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Scarlet").build();
        Name violetta = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Violetta").build();
        Name amber = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Amber").build();
        Name rouge = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Rouge").build();
        Name damson = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Damson").build();
        Name sunset = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Sunset").build();
        Name emerald = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Emerald").build();
        Name ruby = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Ruby").build();
        Name raksha = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Raksha").build();
        Name kickskirt = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Kickskirt").build();
        Name kite = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Kite").build();
        Name monsoon = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Monsoon").build();
        Name smith = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Smith").build();
        Name beastie = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Beastie").build();
        Name baaba = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Baaba").build();
        Name melody = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Melody").build();
        Name mar = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Mar").build();
        Name tavi = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Tavi").build();
        Name absinthe = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Absinthe").build();
        Name honeytree = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Honeytree").build();

        nameService.saveAll(Flux.just(snow, crimson, shadow, beastie, azure, midnight, scarlet, violetta, amber, rouge,
                damson, sunset, emerald, ruby, raksha, kickskirt, kite, monsoon, smith, baaba, melody, mar, tavi,
                absinthe, honeytree)).blockLast();

        /* ----------------------------- BRAINER NAMES --------------------------------- */
        Name smith2 = Name.builder().playbookType(Playbooks.BRAINER).name("Smith").build();
        Name jones = Name.builder().playbookType(Playbooks.BRAINER).name("Jones").build();
        Name jackson = Name.builder().playbookType(Playbooks.BRAINER).name("Jackson").build();
        Name marsh = Name.builder().playbookType(Playbooks.BRAINER).name("Marsh").build();
        Name lively = Name.builder().playbookType(Playbooks.BRAINER).name("Lively").build();
        Name burroughs = Name.builder().playbookType(Playbooks.BRAINER).name("Burroughs").build();
        Name gritch = Name.builder().playbookType(Playbooks.BRAINER).name("Gritch").build();
        Name joyette = Name.builder().playbookType(Playbooks.BRAINER).name("Joyette").build();
        Name iris = Name.builder().playbookType(Playbooks.BRAINER).name("Iris").build();
        Name marie = Name.builder().playbookType(Playbooks.BRAINER).name("Marie").build();
        Name amiette = Name.builder().playbookType(Playbooks.BRAINER).name("Amiette").build();
        Name suselle = Name.builder().playbookType(Playbooks.BRAINER).name("Suselle").build();
        Name cybelle = Name.builder().playbookType(Playbooks.BRAINER).name("Cybelle").build();
        Name pallor = Name.builder().playbookType(Playbooks.BRAINER).name("Pallor").build();
        Name sin = Name.builder().playbookType(Playbooks.BRAINER).name("Sin").build();
        Name charmer = Name.builder().playbookType(Playbooks.BRAINER).name("Charmer").build();
        Name pity = Name.builder().playbookType(Playbooks.BRAINER).name("Pity").build();
        Name brace = Name.builder().playbookType(Playbooks.BRAINER).name("Brace").build();
        Name sundown = Name.builder().playbookType(Playbooks.BRAINER).name("Sundown").build();

        nameService.saveAll(Flux.just(smith2, jones, jackson, marsh, lively, burroughs, gritch, joyette, iris, marie,
                amiette, suselle, cybelle, pallor, sin, charmer, pity, brace, sundown)).blockLast();
    }

    private void loadLooks() {
        System.out.println("|| --- Loading playbook looks --- ||");
        /* ----------------------------- ANGEL LOOKS --------------------------------- */
        Look angel1 = new Look(Playbooks.ANGEL, LookCategories.GENDER, "man");
        Look angel2 = new Look(Playbooks.ANGEL, LookCategories.GENDER, "woman");
        Look angel3 = new Look(Playbooks.ANGEL, LookCategories.GENDER, "ambiguous");
        Look angel4 = new Look(Playbooks.ANGEL, LookCategories.GENDER, "transgressing");
        Look angel5 = new Look(Playbooks.ANGEL, LookCategories.GENDER, "concealed");
        Look angel6 = new Look(Playbooks.ANGEL, LookCategories.CLOTHES, "utility wear");
        Look angel7 = new Look(Playbooks.ANGEL, LookCategories.CLOTHES, "casual wear plus utility");
        Look angel8 = new Look(Playbooks.ANGEL, LookCategories.CLOTHES, "scrounge wear plus utility");
        Look angel9 = new Look(Playbooks.ANGEL, LookCategories.FACE, "kind face");
        Look angel10 = new Look(Playbooks.ANGEL, LookCategories.FACE, "strong face");
        Look angel11 = new Look(Playbooks.ANGEL, LookCategories.FACE, "rugged face");
        Look angel12 = new Look(Playbooks.ANGEL, LookCategories.FACE, "haggard face");
        Look angel13 = new Look(Playbooks.ANGEL, LookCategories.FACE, "pretty face");
        Look angel14 = new Look(Playbooks.ANGEL, LookCategories.FACE, "lively face");
        Look angel15 = new Look(Playbooks.ANGEL, LookCategories.EYES, "quick eyes");
        Look angel16 = new Look(Playbooks.ANGEL, LookCategories.EYES, "hard eyes");
        Look angel17 = new Look(Playbooks.ANGEL, LookCategories.EYES, "caring eyes");
        Look angel18 = new Look(Playbooks.ANGEL, LookCategories.EYES, "bright eyes");
        Look angel19 = new Look(Playbooks.ANGEL, LookCategories.EYES, "laughing eyes");
        Look angel20 = new Look(Playbooks.ANGEL, LookCategories.EYES, "clear eyes");
        Look angel21 = new Look(Playbooks.ANGEL, LookCategories.BODY, "compact body");
        Look angel22 = new Look(Playbooks.ANGEL, LookCategories.BODY, "stout body");
        Look angel23 = new Look(Playbooks.ANGEL, LookCategories.BODY, "spare body");
        Look angel24 = new Look(Playbooks.ANGEL, LookCategories.BODY, "big body");
        Look angel25 = new Look(Playbooks.ANGEL, LookCategories.BODY, "rangy body");
        Look angel26 = new Look(Playbooks.ANGEL, LookCategories.BODY, "sturdy body");

        lookService.saveAll(Flux.just(angel1, angel2, angel3, angel4, angel5, angel6, angel7, angel8, angel9,
                angel10, angel11, angel12, angel13, angel14, angel15, angel16, angel17, angel18, angel19,
                angel20, angel21, angel22, angel23, angel24, angel25, angel26)).blockLast();

        /* ----------------------------- BATTLEBABE LOOKS --------------------------------- */
        Look battlebabe1 = new Look(Playbooks.BATTLEBABE, LookCategories.GENDER, "man");
        Look battlebabe2 = new Look(Playbooks.BATTLEBABE, LookCategories.GENDER, "woman");
        Look battlebabe3 = new Look(Playbooks.BATTLEBABE, LookCategories.GENDER, "ambiguous");
        Look battlebabe4 = new Look(Playbooks.BATTLEBABE, LookCategories.GENDER, "transgressing");
        Look battlebabe5 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.CLOTHES).look("formal wear").build();
        Look battlebabe6 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.CLOTHES).look("display wear").build();
        Look battlebabe7 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.CLOTHES).look("luxe wear").build();
        Look battlebabe8 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.CLOTHES).look("casual wear").build();
        Look battlebabe9 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.CLOTHES).look("showy armor").build();
        Look battlebabe10 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.FACE).look("smooth face").build();
        Look battlebabe11 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.FACE).look("sweet face").build();
        Look battlebabe12 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.FACE).look("handsome face").build();
        Look battlebabe13 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.FACE).look("sharp face").build();
        Look battlebabe14 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.FACE).look("girlish face").build();
        Look battlebabe15 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.FACE).look("boyish face").build();
        Look battlebabe16 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.FACE).look("striking face").build();
        Look battlebabe17 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.EYES).look("calculating eyes").build();
        Look battlebabe18 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.EYES).look("merciless eyes").build();
        Look battlebabe19 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.EYES).look("frosty eyes").build();
        Look battlebabe20 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.EYES).look("arresting eyes").build();
        Look battlebabe21 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.EYES).look("indifferent eyes").build();
        Look battlebabe22 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.BODY).look("sweet body").build();
        Look battlebabe23 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.BODY).look("slim body").build();
        Look battlebabe24 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.BODY).look("gorgeous body").build();
        Look battlebabe25 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.BODY).look("muscular body").build();
        Look battlebabe26 = Look.builder().playbookType(Playbooks.BATTLEBABE).category(LookCategories.BODY).look("angular body").build();

        lookService.saveAll(Flux.just(battlebabe1, battlebabe2, battlebabe3, battlebabe4, battlebabe5, battlebabe6,
                battlebabe7, battlebabe8, battlebabe9, battlebabe10, battlebabe11, battlebabe12, battlebabe13,
                battlebabe14, battlebabe15, battlebabe16, battlebabe17, battlebabe18, battlebabe19, battlebabe20,
                battlebabe21, battlebabe22, battlebabe23, battlebabe24, battlebabe25, battlebabe26)).blockLast();

        /* ----------------------------- BRAINER LOOKS --------------------------------- */
        Look brainer1 = new Look(Playbooks.BRAINER, LookCategories.GENDER, "man");
        Look brainer2 = new Look(Playbooks.BRAINER, LookCategories.GENDER, "woman");
        Look brainer3 = new Look(Playbooks.BRAINER, LookCategories.GENDER, "ambiguous");
        Look brainer4 = new Look(Playbooks.BRAINER, LookCategories.GENDER, "transgressing");
        Look brainer5 = new Look(Playbooks.BRAINER, LookCategories.GENDER, "concealed");
        Look brainer6 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.CLOTHES).look("high formal wear").build();
        Look brainer7 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.CLOTHES).look("clinical wear").build();
        Look brainer8 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.CLOTHES).look("fetish-bondage wear").build();
        Look brainer9 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.CLOTHES).look("environmental wear improper to the local environment").build();
        Look brainer10 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.FACE).look("scarred face").build();
        Look brainer11 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.FACE).look("smooth face").build();
        Look brainer12 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.FACE).look("pale face").build();
        Look brainer13 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.FACE).look("bony face").build();
        Look brainer14 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.FACE).look("plump moist face").build();
        Look brainer15 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.FACE).look("sweet face").build();
        Look brainer16 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.EYES).look("soft eyes").build();
        Look brainer17 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.EYES).look("dead eyes").build();
        Look brainer18 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.EYES).look("deep eyes").build();
        Look brainer19 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.EYES).look("caring eyes").build();
        Look brainer20 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.EYES).look("pale eyes").build();
        Look brainer21 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.EYES).look("ruined eyes").build();
        Look brainer22 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.EYES).look("wet eyes").build();
        Look brainer23 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.BODY).look("awkward angular body").build();
        Look brainer24 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.BODY).look("soft body").build();
        Look brainer25 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.BODY).look("slight body").build();
        Look brainer26 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.BODY).look("crippled body").build();
        Look brainer27 = Look.builder().playbookType(Playbooks.BRAINER).category(LookCategories.BODY).look("fat body").build();

        lookService.saveAll(Flux.just(brainer1, brainer2, brainer3, brainer4, brainer5, brainer6, brainer7, brainer8,
                brainer9, brainer10, brainer11, brainer12, brainer13, brainer14, brainer15, brainer16, brainer17,
                brainer18, brainer19, brainer20, brainer21, brainer22, brainer23, brainer24, brainer25, brainer26,
                brainer27)).blockLast();
    }

    public void loadStatsOptions() {
        System.out.println("|| --- Loading playbook stats options --- ||");
        /* ----------------------------- ANGEL STATS OPTIONS --------------------------------- */
        StatsOption angel1 = new StatsOption(Playbooks.ANGEL, 1, 0, 1, 2, -1);
        StatsOption angel2 = new StatsOption(Playbooks.ANGEL, 1, 1, 0, 2, -1);
        StatsOption angel3 = new StatsOption(Playbooks.ANGEL, -1, 1, 0, 2, 1);
        StatsOption angel4 = new StatsOption(Playbooks.ANGEL, 2, 0, -1, 2, -1);
        statsOptionService.saveAll(Flux.just(angel1, angel2, angel3, angel4)).blockLast();

        /* ----------------------------- BATTLEBABE STATS OPTIONS --------------------------------- */
        StatsOption battlebabe1 = StatsOption.builder().playbookType(Playbooks.BATTLEBABE).COOL(3).HARD(-1).HOT(1).SHARP(1).WEIRD(0).build();
        StatsOption battlebabe2 = StatsOption.builder().playbookType(Playbooks.BATTLEBABE).COOL(3).HARD(-1).HOT(2).SHARP(0).WEIRD(-1).build();
        StatsOption battlebabe3 = StatsOption.builder().playbookType(Playbooks.BATTLEBABE).COOL(3).HARD(-2).HOT(1).SHARP(1).WEIRD(1).build();
        StatsOption battlebabe4 = StatsOption.builder().playbookType(Playbooks.BATTLEBABE).COOL(3).HARD(0).HOT(1).SHARP(1).WEIRD(-1).build();
        statsOptionService.saveAll(Flux.just(battlebabe1, battlebabe2, battlebabe3, battlebabe4)).blockLast();

        /* ----------------------------- BRAINER STATS OPTIONS --------------------------------- */
        StatsOption brainer1 = StatsOption.builder().playbookType(Playbooks.BRAINER).COOL(1).HARD(1).HOT(-2).SHARP(1).WEIRD(2).build();
        StatsOption brainer2 = StatsOption.builder().playbookType(Playbooks.BRAINER).COOL(0).HARD(0).HOT(1).SHARP(0).WEIRD(2).build();
        StatsOption brainer3 = StatsOption.builder().playbookType(Playbooks.BRAINER).COOL(1).HARD(-2).HOT(-1).SHARP(2).WEIRD(2).build();
        StatsOption brainer4 = StatsOption.builder().playbookType(Playbooks.BRAINER).COOL(2).HARD(-1).HOT(-1).SHARP(0).WEIRD(2).build();
        statsOptionService.saveAll(Flux.just(brainer1, brainer2, brainer3, brainer4)).blockLast();
    }

    public void loadPlaybookCreators() {
        System.out.println("|| --- Loading playbook creators --- ||");
        /* ----------------------------- ANGEL PLAYBOOK CREATOR --------------------------------- */
        List<CharacterMove> angelMoves = moveRepository
                .findAllByPlaybookAndKind(Playbooks.ANGEL, MoveKinds.CHARACTER)
                .map(move -> {
                    CharacterMove characterMove;
                    if (move.getName().equals("ANGEL SPECIAL")) {
                        characterMove = CharacterMove.createFromMove(move, true);
                    } else {
                        characterMove = CharacterMove.createFromMove(move, false);
                    }
                    return characterMove;
                })
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
                .youGet("You get:")
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
                .playbookType(Playbooks.ANGEL)
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
                .playbookMoves(angelMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .build();


        /* ----------------------------- BATTLEBABE PLAYBOOK CREATOR --------------------------------- */
        List<CharacterMove> battlebabeMoves = moveRepository
                .findAllByPlaybookAndKind(Playbooks.BATTLEBABE, MoveKinds.CHARACTER)
                .map(move -> {
                    CharacterMove characterMove;
                    if (move.getName().equals("BATTLEBABE SPECIAL")) {
                        characterMove = CharacterMove.createFromMove(move, true);
                    } else {
                        characterMove = CharacterMove.createFromMove(move, false);
                    }
                    return characterMove;
                })
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
                .youGet("You get:")
                .youGetItems(List.of("fashion suitable to your look, including at your option fashion worth 1-armor or body armor worth 2-armor (you detail)"))
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .startingBarter(4)
                .build();

        PlaybookCreator battlebabePlaybookCreator = PlaybookCreator.builder()
                .playbookType(Playbooks.BATTLEBABE)
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
                .playbookMoves(battlebabeMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .build();

        /* ----------------------------- BRAINER PLAYBOOK CREATOR --------------------------------- */
        List<CharacterMove> brainerMoves = moveRepository
                .findAllByPlaybookAndKind(Playbooks.BRAINER, MoveKinds.CHARACTER)
                .map(move -> {
                    CharacterMove characterMove;
                    if (move.getName().equals("BRAINER SPECIAL")) {
                        characterMove = CharacterMove.createFromMove(move, true);
                    } else {
                        characterMove = CharacterMove.createFromMove(move, false);
                    }
                    return characterMove;
                })
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
                .youGet("You get:")
                .youGetItems(List.of("fashion suitable to your look, including at your option a piece worth 1-armor (you detail)"))
                .introduceChoice("Small fancy weapons")
                .numberCanChoose(1)
                .chooseableGear(List.of("silenced 9mm (2-harm close hi-tech)", "ornate dagger (2-harm hand valuable)", "hidden knives (2-harm hand infinite)", "scalpels (3-harm intimate hi-tech)", "antique handgun (2-harm close reload loud valuable)"))
                .startingBarter(8)
                .withMC("If you’d like to start play with a vehicle or a prosthetic, get with the MC.")
                .build();

        PlaybookCreator playbookCreatorBrainer = PlaybookCreator.builder()
                .playbookType(Playbooks.BRAINER)
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
                .playbookMoves(brainerMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .build();

        playbookCreatorService.saveAll(Flux.just(angelCreator, battlebabePlaybookCreator, playbookCreatorBrainer)).blockLast();
    }

    public void loadPlaybooks() {
        System.out.println("|| --- Loading playbooks --- ||");
        /* ----------------------------- ANGEL PLAYBOOK --------------------------------- */
        Playbook angel = new Playbook(Playbooks.ANGEL, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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

        Playbook battlebabe = new Playbook(Playbooks.BATTLEBABE, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
        Playbook brainer = new Playbook(Playbooks.BRAINER, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
        Playbook chopper = new Playbook(Playbooks.CHOPPER, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
        Playbook driver = new Playbook(Playbooks.DRIVER, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
        Playbook gunlugger = new Playbook(Playbooks.GUNLUGGER, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
        Playbook hardholder = new Playbook(Playbooks.HARDHOLDER, "Your holding provides for your day-to-day living, so while you’re there governing it, you need not spend barter for your lifestyle at the beginning of the session.\n" +
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
        Playbook hocus = new Playbook(Playbooks.HOCUS, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
        Playbook maestroD = new Playbook(Playbooks.MAESTRO_D, "Your establishment provides for your day-to-day living, so while you’re open for business, you need not spend barter for your lifestyle at the beginning of the session.\n" +
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
        Playbook savvyhead = new Playbook(Playbooks.SAVVYHEAD, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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
        Playbook skinner = new Playbook(Playbooks.SKINNER, "At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.\n" +
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

        List<Playbook> playbooks = playbookService.findAll().collectList().block();
    }

    private void createPlaybooks() {
        // -------------------------------------- Set up Playbooks -------------------------------------- //
        // -------------------------------------- ANGEL -------------------------------------- //
        Playbook playbookAngel = playbookService.findByPlaybookType(Playbooks.ANGEL).block();
        assert playbookAngel != null;

        if (playbookAngel.getCreator() == null) {
            PlaybookCreator playbookCreatorAngel = playbookCreatorService.findByPlaybookType(Playbooks.ANGEL).block();
            assert playbookCreatorAngel != null;

            List<Name> namesAngel = nameService.findAllByPlaybookType(Playbooks.ANGEL).collectList().block();
            assert namesAngel != null;

            List<Look> looksAngel = lookService.findAllByPlaybookType(Playbooks.ANGEL).collectList().block();
            assert looksAngel != null;

            List<StatsOption> statsOptionsAngel = statsOptionService.findAllByPlaybookType(Playbooks.ANGEL).collectList().block();
            assert statsOptionsAngel != null;

            statsOptionsAngel.forEach(statsOption -> playbookCreatorAngel.getStatsOptions().add(statsOption));
            namesAngel.forEach(name -> playbookCreatorAngel.getNames().add(name));
            looksAngel.forEach(look -> playbookCreatorAngel.getLooks().add(look));
            playbookCreatorService.save(playbookCreatorAngel).block();
            playbookAngel.setCreator(playbookCreatorAngel);
            playbookService.save(playbookAngel).block();
        }

        // -------------------------------------- BATTLEBABE -------------------------------------- //
        Playbook playbookBattlebabe = playbookService.findByPlaybookType(Playbooks.BATTLEBABE).block();
        assert playbookBattlebabe != null;

        if (playbookBattlebabe.getCreator() == null) {
            PlaybookCreator playbookCreatorBattlebabe = playbookCreatorService.findByPlaybookType(Playbooks.BATTLEBABE).block();
            assert playbookCreatorBattlebabe != null;


            List<Name> namesBattlebabe = nameService.findAllByPlaybookType(Playbooks.BATTLEBABE).collectList().block();
            assert namesBattlebabe != null;

            List<Look> looksBattlebabe = lookService.findAllByPlaybookType(Playbooks.BATTLEBABE).collectList().block();
            assert looksBattlebabe != null;

            List<StatsOption> statsOptionsBattlebabe = statsOptionService.findAllByPlaybookType(Playbooks.BATTLEBABE).collectList().block();
            assert statsOptionsBattlebabe != null;

            statsOptionsBattlebabe.forEach(statsOption -> playbookCreatorBattlebabe.getStatsOptions().add(statsOption));
            namesBattlebabe.forEach(name -> playbookCreatorBattlebabe.getNames().add(name));
            looksBattlebabe.forEach(look -> playbookCreatorBattlebabe.getLooks().add(look));
            playbookCreatorService.save(playbookCreatorBattlebabe).block();
            playbookBattlebabe.setCreator(playbookCreatorBattlebabe);
            playbookService.save(playbookBattlebabe).block();
        }

        // -------------------------------------- BRAINER -------------------------------------- //
        Playbook playbookBrainer = playbookService.findByPlaybookType(Playbooks.BRAINER).block();
        assert playbookBrainer != null;

        if (playbookBrainer.getCreator() == null) {
            PlaybookCreator playbookCreatorBrainer = playbookCreatorService.findByPlaybookType(Playbooks.BRAINER).block();
            assert playbookCreatorBrainer != null;


            List<Name> namesBrainer = nameService.findAllByPlaybookType(Playbooks.BRAINER).collectList().block();
            assert namesBrainer != null;

            List<Look> looksBrainer = lookService.findAllByPlaybookType(Playbooks.BRAINER).collectList().block();
            assert looksBrainer != null;

            List<StatsOption> statsOptionsBrainer = statsOptionService.findAllByPlaybookType(Playbooks.BRAINER).collectList().block();
            assert statsOptionsBrainer != null;

            statsOptionsBrainer.forEach(statsOption -> playbookCreatorBrainer.getStatsOptions().add(statsOption));
            namesBrainer.forEach(name -> playbookCreatorBrainer.getNames().add(name));
            looksBrainer.forEach(look -> playbookCreatorBrainer.getLooks().add(look));
            playbookCreatorService.save(playbookCreatorBrainer).block();
            playbookBrainer.setCreator(playbookCreatorBrainer);
            playbookService.save(playbookBrainer).block();
        }
    }
}
