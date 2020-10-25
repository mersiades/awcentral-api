package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.enums.*;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    final String DISCORD_TEXT_CHANNEL_ID_1 = "741573502452105236";
    final String DISCORD_TEXT_CHANNEL_ID_2 = "823458920374529070";
    final String DISCORD_VOICE_CHANNEL_ID_1 = "741573503710527498";
    final String DISCORD_VOICE_CHANNEL_ID_2 = "123876129847590347";
    final String DISCORD_USER_ID_1 = "696484065859076146";
    final String DISCORD_USER_ID_2 = "134523465246534532";

    private final UserService userService;
    private final GameService gameService;
    private final NpcService npcService;
    private final ThreatService threatService;
    private final CharacterService characterService;
    private final PlaybookCreatorService playbookCreatorService;
    private final PlaybookService playbookService;
    private final NameService nameService;
    private final LookService lookService;
    private final StatsOptionService statsOptionService;
    private final MoveService moveService;

    public DataLoader(UserService userService, GameService gameService,
                      NpcService npcService, ThreatService threatService, CharacterService characterService, PlaybookCreatorService playbookCreatorService, PlaybookService playbookService, NameService nameService, LookService lookService, StatsOptionService statsOptionService, MoveService moveService) {
        this.userService = userService;
        this.gameService = gameService;
        this.npcService = npcService;
        this.threatService = threatService;
        this.characterService = characterService;
        this.playbookCreatorService = playbookCreatorService;
        this.playbookService = playbookService;
        this.nameService = nameService;
        this.lookService = lookService;
        this.statsOptionService = statsOptionService;
        this.moveService = moveService;
    }

    @Override
    public void run(String... args) {
        loadMoves();
        loadNames();
        loadLooks();
        loadStatsOptions();
        loadPlaybookCreators();
        loadPlaybooks();
        loadData();
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "When you’re lying in the dust of Apocalypse World guts aspilled, for whom do you pray? Thee gods? They’re long gone. Your beloved comrades? Fuckers all, or you wouldn’t be here to begin with. Your precious old mother? She’s a darling but she can’t put an intestine back inside so it’ll stay. No, you pray for some grinning kid or veteran or just someone with a heartshocker and a hand with sutures and a 6-pack of morphine. And when that someone comes, _that’s_ an angel.", "Angels are medics. If you want everybody to love you, or at least rely on you, play an angel. Warning: if things are going well, maybe nobody will rely on you. Make interesting relationships so you’ll stay relevant. Or sabotage things, I guess.");
        playbookService.save(angel);

        Set<Playbook> playbooks = playbookService.findAll();
        System.out.println("Number of saved playbooks: " + playbooks.size());
    }

    public void loadPlaybookCreators() {
        System.out.println("|| --- Loading playbook creators --- ||");
        /* ----------------------------- ANGEL PLAYBOOK CREATOR --------------------------------- */
        PlaybookCreator angelCreator = new PlaybookCreator(Playbooks.ANGEL, "You get:\n" +
                "\n" +
                "- angel kit, no supplier\n" +
                "- 1 small practical weapon\n" +
                "- oddments worth 2-barter\n" +
                "- fashion suitable to your look, including at your option a piece worth 1-armor (you detail)\n" +
                "\n" +
                "Small practical weapons\n" +
                "(choose 1):\n" +
                "\n" +
                "- .38 revolver (2-harm close reload loud)\n" +
                "- 9mm (2-harm close loud)\n" +
                "- big knife (2-harm hand)\n" +
                "- sawed-off (3-harm close reload messy)\n" +
                "- stun gun (s-harm hand reload)\n" +
                "\n" +
                "If you’d like to start play with a vehicle or a prosthetic, get with the MC.", "Whenever you roll a highlighted stat, and whenever you reset your Hx with someone, mark an experience circle. When you mark the 5th, improve and erase.\n" +
                "\n" +
                "Each time you improve, choose one of the options. Check it off; you can’t choose it again.", "You get all the basic moves. Choose 2 angel moves.\n" +
                "\n" +
                "You can use all the battle moves, but when you get the chance, look up _**keeping an eye out**_, and _**baiting a trap**_, as well as the rules for harm.", "Everyone introduces their characters by name, look and outlook. Take your turn.\n" +
                "\n" +
                "List the other characters’ names.\n" +
                "\n" +
                "Go around again for Hx. On your turn, ask 1, 2, or all 3:\n" +
                "\n" +
                "- Which one of you do I figure is doomed to self-destruction?\n" +
                "For that character, write Hx-2.\n" +
                "- Which one of you put a hand in when it mattered, and helped me save a life?\n" +
                "For that character, write Hx+2.\n" +
                "- Which one of you has been beside me all along, and has seen everything I’ve seen?\n" +
                "For that character, write Hx+3.\n" +
                "\n" +
                "For everyone else, write Hx+1. You keep your eyes open.\n" +
                "\n" +
                "On the others’ turns, answer their questions as you like.\n" +
                "\n" +
                "At the end, choose one of the characters with the highest Hx on your sheet. Ask that player which of your stats is most interesting, and highlight it. The MC will have you highlight a second stat too.");
        PlaybookCreator saved =  playbookCreatorService.save(angelCreator);
        System.out.println("saved = " + saved);

        // Throws IllegalStateException: Cannot set property names because no setter, no wither and it's not part of the persistence constructor public com.mersiades.awcdata.models.PlaybookCreator(com.mersiades.awcdata.enums.Playbooks,java.lang.String,java.lang.String,java.lang.String,java.lang.String)!
//        Set<PlaybookCreator> playbookCreators = playbookCreatorService.findAll();
//        System.out.println("Number of saved playbookCreators: " + playbookCreators.size());
    }

    public void loadStatsOptions() {
        System.out.println("|| --- Loading playbook stats options --- ||");
        /* ----------------------------- ANGEL STATS OPTIONS --------------------------------- */
        StatsOption angel1 = new StatsOption(Playbooks.ANGEL, 1, 0, 1, 2, -1);
        StatsOption angel2 = new StatsOption(Playbooks.ANGEL, 1, 1, 0, 2, -1);
        StatsOption angel3 = new StatsOption(Playbooks.ANGEL, -1, 1, 0, 2, 1);
        StatsOption angel4 = new StatsOption(Playbooks.ANGEL, 2, 0, -1, 2, -1);
        statsOptionService.save(angel1);
        statsOptionService.save(angel2);
        statsOptionService.save(angel3);
        statsOptionService.save(angel4);

        Set<StatsOption> statsOptions = statsOptionService.findAll();
        System.out.println("Number of saved statsOptions: " + statsOptions.size());
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
        lookService.save(angel1);
        lookService.save(angel2);
        lookService.save(angel3);
        lookService.save(angel4);
        lookService.save(angel5);
        lookService.save(angel6);
        lookService.save(angel7);
        lookService.save(angel8);
        lookService.save(angel9);
        lookService.save(angel10);
        lookService.save(angel11);
        lookService.save(angel12);
        lookService.save(angel13);
        lookService.save(angel14);
        lookService.save(angel15);
        lookService.save(angel16);
        lookService.save(angel17);
        lookService.save(angel18);
        lookService.save(angel19);
        lookService.save(angel20);
        lookService.save(angel21);
        lookService.save(angel22);
        lookService.save(angel23);
        lookService.save(angel24);
        lookService.save(angel25);
        lookService.save(angel26);

        Set<Look> looks = lookService.findAll();
        System.out.println("Number of saved looks: " + looks.size());
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
        nameService.save(dou);
        nameService.save(bon);
        nameService.save(abe);
        nameService.save(boo);
        nameService.save(t);
        nameService.save(kal);
        nameService.save(charName);
        nameService.save(jav);
        nameService.save(ruth);
        nameService.save(wei);
        nameService.save(jay);
        nameService.save(nee);
        nameService.save(kim);
        nameService.save(lan);
        nameService.save(di);
        nameService.save(dez);
        nameService.save(core);
        nameService.save(wheels);
        nameService.save(doc);
        nameService.save(buzz);
        nameService.save(key);
        nameService.save(line);
        nameService.save(gabe);
        nameService.save(biz);
        nameService.save(bish);
        nameService.save(inch);
        nameService.save(grip);
        nameService.save(setter);

        Set<Name> names = nameService.findAll();
        System.out.println("Number of saved names: " + names.size());
    }

    private void loadMoves() {
        System.out.println("|| --- Loading basic moves --- ||");
        /* ----------------------------- BASIC MOVES --------------------------------- */
        Move doSomethingUnderFire = new Move("DO SOMETHING UNDER FIRE", "When you _**do something under fire**_, or dig in to endure fire, roll+cool. On a 10+, you do it. On a 7–9, you flinch, hesitate, or stall: the MC can offer you a worse outcome, a hard bargain, or an ugly choice. On a miss, be prepared for the worst.", Stats.COOL, MoveKinds.BASIC, null);
        Move goAggro = new Move("GO AGGRO ON SOMEONE", "When you _**go aggro on someone**_, make it clear what you want them to do and what you’ll do to them. Roll+hard. On a 10+, they have to choose:\n" +
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
                "On a miss, be prepared for the worst.", Stats.HARD, MoveKinds.BASIC, null);
        Move sucker = new Move("SUCKER SOMEONE", "When you _**attack someone unsuspecting or helpless**_, ask the MC if you could miss. If you could, treat it as going aggro, but your victim has no choice to cave and do what you want. If you couldn’t, you simply inflict harm as established.", null, MoveKinds.BASIC, null);
        Move doBattle = new Move("DO BATTLE", "When you’re _**in battle**_, you can bring the battle moves into play.", null, MoveKinds.BASIC, null);
        Move seduceOrManip = new Move("SEDUCE OR MANIPULATE SOMEONE", "When you _**try to seduce, manipulate, bluff, fast-talk, or lie to someone**_, tell them what you want them to do, give them a reason, and roll+hot. **For NPCs**: on a 10+, they’ll go along with you, unless or until some fact or action betrays the reason you gave them. On a 7–9, they’ll go along with you, but they need some concrete assurance, corroboration, or evidence first. **For PCs**: on a 10+, both. On a 7–9, choose 1:\n" +
                "\n" +
                "- *If they go along with you, they mark experience.*\n" +
                "- *If they refuse, erase one of their stat highlights for the remainder of the session.*\n" +
                "\n" +
                "What they do then is up to them.\n" +
                "\n" +
                "On a miss, for either NPCs or PCs, be prepared for the worst.", Stats.HOT, MoveKinds.BASIC, null);
        Move helpOrInterfere = new Move("HELP OR INTERFERE WITH SOMEONE", "When you _**help**_ or _**interfere**_ with someone who’s making a roll, roll+Hx. On a 10+, they take +2 (help) or -2 (interfere) to their roll. On a 7–9, they take +1 (help) or -1 (interfere) to their roll. On a miss, be prepared for the worst.", Stats.HX, MoveKinds.BASIC, null);
        Move readASitch = new Move("READ A SITCH", "When you _**read a charged situation**_, roll+sharp. On a hit, you can ask the MC questions. Whenever you act on one of the MC’s answers, take +1. On a 10+, ask 3. On a 7–9, ask 1:\n" +
                "\n" +
                "- *Where’s my best escape route / way in / way past?*\n" +
                "- *Which enemy is most vulnerable to me?*\n" +
                "- *Which enemy is the biggest threat?*\n" +
                "- *What should I be on the lookout for?*\n" +
                "- *What’s my enemy’s true position?*\n" +
                "- *Who’s in control here?*\n" +
                "\n" +
                "On a miss, ask 1 anyway, but be prepared for the worst.", Stats.SHARP, MoveKinds.BASIC, null);
        Move readAPerson = new Move("READ A PERSON", "When you _**read a person**_ in a charged interaction, roll+sharp. On a 10+, hold 3. On a 7–9, hold 1. While you’re interacting with them, spend your hold to ask their player questions, 1 for 1:\n" +
                "\n" +
                "- *Is your character telling the truth?*\n" +
                "- *What’s your character really feeling?*\n" +
                "- *What does your character intend to do?*\n" +
                "- *What does your character wish I’d do?*\n" +
                "- *How could I get your character to__?*\n" +
                "\n" +
                "On a miss, ask 1 anyway, but be prepared for the worst.", Stats.SHARP, MoveKinds.BASIC, null);
        Move openBrain = new Move("OPEN YOUR BRAIN", "When you _**open your brain to the world’s psychic maelstrom**_, roll+weird. On a hit, the MC tells you something new and interesting about the current situation, and might ask you a question or two; answer them. On a 10+, the MC gives you good detail. On a 7–9, the MC gives you an impression. If you already know all there is to know, the MC will tell you that. On a miss, be prepared for the worst.", Stats.WEIRD, MoveKinds.BASIC, null);
        Move lifestyleAndGigs = new Move("LIFESTYLE AND GIGS", "_**At the beginning of the session**_, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions. If you need jingle during a session, tell the MC you’d like to work a gig.", null, MoveKinds.BASIC, null);
        Move sessionEnd = new Move("SESSION END", "_**At the end of every session**_, choose a character who knows you better than they used to. If there’s more than one, choose one at your whim. Tell that player to add +1 to their Hx with you on their sheet. If this brings them to Hx+4, they reset to Hx+1 (and therefore mark experience). If no one knows you better, choose a character who doesn’t know you as well as they thought, or choose any character at your whim. Tell that player to take -1 to their Hx with you on their sheet. If this brings them to Hx -3, they reset to Hx=0 (and therefore mark experience).", null, MoveKinds.BASIC, null);
        moveService.save(doSomethingUnderFire);
        moveService.save(goAggro);
        moveService.save(sucker);
        moveService.save(doBattle);
        moveService.save(seduceOrManip);
        moveService.save(helpOrInterfere);
        moveService.save(readASitch);
        moveService.save(readAPerson);
        moveService.save(openBrain);
        moveService.save(lifestyleAndGigs);
        moveService.save(sessionEnd);

        /* ----------------------------- PERIPHERAL MOVES --------------------------------- */
        // TODO: Add peripheral moves to DataLoader

        /* ----------------------------- BATTLE MOVES --------------------------------- */
        // TODO: Add battle moves to DataLoader

        /* ----------------------------- ANGEL MOVES --------------------------------- */
        System.out.println("|| --- Loading Angel moves --- ||");
        Move angelSpecial = new Move("ANGEL SPECIAL", "If you and another character have sex, your Hx with them on your sheet goes immediately to +3, and they immediately get +1 to their Hx with you on their sheet. If that brings their Hx with you to +4, they reset it to +1 instead, as usual, and so mark experience.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move sixthSense = new Move("SIXTH SENSE", "_**Sixth sense**_: when you open your brain to the world’s psychic maelstrom, roll+sharp instead of +weird.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move infirmary = new Move("INFIRMARY", "_**Infirmary**_: you get an infirmary, a workspace with life support, a drug lab and a crew of 2 (Shigusa & Mox, maybe). Get patients into it and you can work on them like a savvyhead on tech (_cf_).", null,MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move profCompassion = new Move("PROFESSIONAL COMPASSION", "_**Professional compassion**_: you can roll+sharp instead of roll+Hx when you help someone who’s rolling.",null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move bettlefieldGrace = new Move("BATTLEFIELD GRACE","_**Battlefield grace**_: while you are caring for people, not fighting, you get +1armor.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move healingTouch = new Move("HEALING TOUCH","_**Healing touch**_: when you put your hands skin-to-skin on a wounded person and open your brain to them, roll+weird.\n" +
                "\n" +
                "On a 10+, heal 1 segment.\n" +
                "\n" +
                "On a 7–9, heal 1 segment, but you’re also opening your brain, so roll that move next.\n" +
                "\n" +
                "On a miss: first, you don’t heal them. Second, you’ve opened both your brain and theirs to the world’s psychic maelstrom, without protection or preparation. For you, and for your patient if your patient’s a fellow player’s character, treat it as though you’ve made that move and missed the roll. For patients belonging to the MC, their experience and fate are up to the MC.\n", Stats.WEIRD, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move touchedByDeath = new Move("HEALING TOUCH","_**Touched by death**_: when someone is unconscious in your care, you can use them for _**augury**_. When someone has died in your care, you can use their body for _**augury**_.",null ,MoveKinds.CHARACTER, Playbooks.ANGEL);
        moveService.save(angelSpecial);
        moveService.save(sixthSense);
        moveService.save(infirmary);
        moveService.save(profCompassion);
        moveService.save(bettlefieldGrace);
        moveService.save(healingTouch);
        moveService.save(touchedByDeath);

        Set<Move> moves = moveService.findAll();
        System.out.println("Number of saved moves: " + moves.size());
    }

    private void loadData() {
        PlaybookCreator playbookCreatorAngel = playbookCreatorService.findByPlaybookType(Playbooks.ANGEL);
        Playbook playbookAngel = playbookService.findByPlaybookType(Playbooks.ANGEL);
        Set<Name> namesAngel = nameService.findAllByPlaybookType(Playbooks.ANGEL);
        Set<Look> looksAngel = lookService.findAllByPlaybookType(Playbooks.ANGEL);
        Set<StatsOption> statsOptionsAngel = statsOptionService.findAllByPlaybookType(Playbooks.ANGEL);

        for(StatsOption statsOption: statsOptionsAngel) {
            playbookCreatorAngel.getStatsOptions().add(statsOption);
            statsOption.setPlaybookCreator(playbookCreatorAngel);
            statsOptionService.save(statsOption);
        }

        namesAngel.forEach(name -> {
            name.setPlaybookCreator(playbookCreatorAngel);
            nameService.save(name);
            playbookCreatorAngel.getNames().add(name);
        });

        looksAngel.forEach(look -> {
            look.setPlaybookCreator(playbookCreatorAngel);
            lookService.save(look);
            playbookCreatorAngel.getLooks().add(look);
        });
        System.out.println("looksAngel = " + looksAngel);

        playbookAngel.setCreator(playbookCreatorAngel);
        playbookCreatorAngel.setPlaybook(playbookAngel);
        playbookCreatorService.save(playbookCreatorAngel);
        playbookService.save(playbookAngel);

        Set<StatsOption> statsOptions = playbookAngel.getCreator().getStatsOptions();

        System.out.println("|-------------- ANGEL STAT OPTIONS --------------|");
        statsOptions.forEach(statsOption -> {

            System.out.println("PB: " + statsOption.getPlaybookType());
            System.out.println("COOL: " + statsOption.getCOOL());
            System.out.println("HARD: " + statsOption.getHARD());
            System.out.println("HOT: " + statsOption.getHOT());
            System.out.println("SHARP: " + statsOption.getSHARP());
            System.out.println("WEIRD: " + statsOption.getWEIRD() + "\n");
        });


        // -------------------------------------- Set up mock Users -------------------------------------- //
        User mockUser1 = new User();

        mockUser1.setDiscordId(DISCORD_USER_ID_1);

        User mockUser2 = new User();
        mockUser2.setDiscordId(DISCORD_USER_ID_2);

        userService.save(mockUser1);
        userService.save(mockUser2);

        // ------------------------------ Set up mock Game 1 with Game Roles ----------------------------- //
        Game mockGame1 = new Game(DISCORD_TEXT_CHANNEL_ID_1, DISCORD_VOICE_CHANNEL_ID_1, "Mock Game 1");

        GameRole daveAsMC = new GameRole(Roles.MC, mockGame1, mockUser1);
        Npc mockNpc1 = new Npc(daveAsMC, "Vision", "Badass truck driver");
        Npc mockNpc2 = new Npc(daveAsMC, "Nbeke");
        daveAsMC.getNpcs().add(mockNpc1);
        daveAsMC.getNpcs().add(mockNpc2);
        Threat mockThreat1 = new Threat(daveAsMC, "Tum Tum", Threats.WARLORD, "Slaver: to own and sell people");
        Threat mockThreat2 = new Threat(daveAsMC, "Gnarly", Threats.GROTESQUE, "Cannibal: craves satiety and plenty");
        daveAsMC.getThreats().add(mockThreat1);
        daveAsMC.getThreats().add(mockThreat2);

        mockGame1.getGameRoles().add(daveAsMC);
        mockUser1.getGameRoles().add(daveAsMC);

        GameRole sarahAsPlayer = new GameRole(Roles.PLAYER, mockGame1, mockUser2);
        mockGame1.getGameRoles().add(sarahAsPlayer);
        mockUser2.getGameRoles().add(sarahAsPlayer);

        gameService.save(mockGame1);

        // ------------------------------ Set up mock Game 2 with Game Roles ----------------------------- //
        Game mockGame2 = new Game(DISCORD_TEXT_CHANNEL_ID_2, DISCORD_VOICE_CHANNEL_ID_2, "Mock Game 2");

        GameRole daveAsPlayer = new GameRole(Roles.PLAYER, mockGame2, mockUser1);
        mockGame2.getGameRoles().add(daveAsPlayer);
        mockUser1.getGameRoles().add(daveAsPlayer);

        GameRole sarahAsMC = new GameRole(Roles.MC, mockGame2, mockUser2);
        Npc mockNpc3 = new Npc(sarahAsMC, "Batty", "Overly polite gun for hire");
        Npc mockNpc4 = new Npc(sarahAsMC, "Farley");
        sarahAsMC.getNpcs().add(mockNpc3);
        sarahAsMC.getNpcs().add(mockNpc4);
        Threat mockThreat3 = new Threat(sarahAsMC, "Fleece", Threats.BRUTE, "Hunting pack: to victimize anyone vulnerable");
        Threat mockThreat4 = new Threat(sarahAsMC, "Wet Rot", Threats.AFFLICTION, "Condition: to expose people to danger");
        sarahAsMC.getThreats().add(mockThreat3);
        sarahAsMC.getThreats().add(mockThreat4);

        mockGame2.getGameRoles().add(sarahAsMC);
        mockUser2.getGameRoles().add(sarahAsMC);

        gameService.save(mockGame2);

        // ---------------------------------- Add Characters to Players --------------------------------- //
        Character mockCharacter1 = new Character("October", sarahAsPlayer, Playbooks.ANGEL, "not much gear");
        sarahAsPlayer.getCharacters().add(mockCharacter1);

        Character mockCharacter2 = new Character("Leah", daveAsPlayer, Playbooks.SAVVYHEAD, "workshop with tools");
        daveAsPlayer.getCharacters().add(mockCharacter2);

        characterService.save(mockCharacter1);
        characterService.save(mockCharacter2);

        // ----------------------------------------------------------------------------------------------- //
        npcService.save(mockNpc1);
        npcService.save(mockNpc2);
        npcService.save(mockNpc3);
        npcService.save(mockNpc4);
        threatService.save(mockThreat1);
        threatService.save(mockThreat2);
        threatService.save(mockThreat3);
        threatService.save(mockThreat4);


        // -------------------------------------- Print MockUser1 -------------------------------------- //
        printUser(mockUser1);
        System.out.println("\t ********** Game Role 1 **********");
        printGameRole(daveAsMC);
        System.out.println("\t ********** Game Role 2 **********");
        printGameRole(daveAsPlayer);
        // -------------------------------------- Print MockUser2 -------------------------------------- //
        printUser(mockUser2);
        System.out.println("\t ********** Game Role 1 **********");
        printGameRole(sarahAsPlayer);
        System.out.println("\t ********** Game Role 2 **********");
        printGameRole(sarahAsMC);
        // -------------------------------------- Print MockGame1 -------------------------------------- //
        // -------------------------------------- Print MockGame2 -------------------------------------- //
    }

    private void printGameRole(GameRole role) {
        System.out.println("\t Game: " + role.getGame().getName());
        Roles roleType = role.getRole();
        System.out.println("\t Role: " + role.getRole());
        if (roleType == Roles.MC) {
            Set<Npc> npcs = role.getNpcs();
            Set<Threat> threats = role.getThreats();
            System.out.println("\t This role has " + npcs.size() + " NPCs");
            if (npcs.size() > 0) {
                for (Npc npc : npcs) {
                    System.out.println("\t\t NPC name: " + npc.getName());
                    if (npc.getDescription() != null) {
                        System.out.println("\t\t NPC description: " + npc.getDescription());
                    }
                    System.out.println("\n");
                }
            }
            System.out.println("\t This role has " + threats.size() + " threats");
            if (threats.size() > 0) {
                for (Threat threat : threats) {
                    System.out.println("\t\t Threat name: " + threat.getName());
                    System.out.println("\t\t Threat kind: " + threat.getThreatKind());
                    System.out.println("\t\t Threat impulse: " + threat.getImpulse());
                    System.out.println("\n");
                }
            }
        } else if (roleType == Roles.PLAYER) {
            Set<Character> characters = role.getCharacters();
            System.out.println("\t This role has " + characters.size() + " characters");
            if (characters.size() > 0) {
                for (Character character : characters) {
                    System.out.println("\t\t Character name: " + character.getName());
                    System.out.println("\t\t Playbook: " + character.getPlaybook());
                    System.out.println("\t\t Gear: " + character.getGear());
                    System.out.println("\n");
                }
            }
        }
    }

    private void printUser(User user) {
        System.out.println("| ------------- " + user.getId() + " -------------- |");
        System.out.println("ID: " + user.getId());
        System.out.println("Discord ID: " + user.getDiscordId());
        System.out.println("GameRoles (#): " + user.getGameRoles().size());
        System.out.println("\n");
    }

}
