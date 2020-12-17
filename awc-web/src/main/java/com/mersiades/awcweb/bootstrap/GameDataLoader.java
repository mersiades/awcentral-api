package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.MoveKinds;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.enums.Stats;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.repositories.*;
import com.mersiades.awcdata.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@Order(value = 0)
public class GameDataLoader implements CommandLineRunner {

    private final PlaybookCreatorService playbookCreatorService;
    private final PlaybookService playbookService;
    private final NameService nameService;
    private final LookService lookService;
    private final StatsOptionService statsOptionService;
    private final MoveService moveService;

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
                          MoveService moveService) {
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
        createPlaybooks();

        System.out.println("Look count: " + Objects.requireNonNull(lookRepository.count().block()).toString());
        System.out.println("Move count: " + Objects.requireNonNull(moveRepository.count().block()).toString());
        System.out.println("Name count: " + Objects.requireNonNull(nameRepository.count().block()).toString());
        System.out.println("PlaybookCreator count: " + Objects.requireNonNull(playbookCreatorRepository.count().block()).toString());
        System.out.println("Playbook count: " + Objects.requireNonNull(playbookRepository.count().block()).toString());
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "When you’re lying in the dust of Apocalypse World guts aspilled, for whom do you pray? The gods? They’re long gone. Your beloved comrades? Fuckers all, or you wouldn’t be here to begin with. Your precious old mother? She’s a darling but she can’t put an intestine back inside so it’ll stay. No, you pray for some grinning kid or veteran or just someone with a heartshocker and a hand with sutures and a 6-pack of morphine. And when that someone comes, _that’s_ an angel.", "Angels are medics. If you want everybody to love you, or at least rely on you, play an angel. Warning: if things are going well, maybe nobody will rely on you. Make interesting relationships so you’ll stay relevant. Or sabotage things, I guess.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/angel.png");

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
                "Battlebabes are good in battle, of course, but they’re wicked social too. If you want to play somebody dangerous and provocative, play a battlebabe. Warning: you might find that you’re better at making trouble than getting out of it. If you want to play the baddest ass, play a gunlugger instead.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/battlebabe.png");
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
                "They’re just the sort of tasteful accoutrement that no well-appointed hardhold can do without.", "Brainers are spooky, weird, and really fun to play. Their moves are powerful but strange. If you want everybody else to be at least a little bit afraid of you, a brainer is a good choice. Warning: you’ll be happy anyway, but you’ll be happiest if somebody wants to have sex with you even though you’re a brainer. Angle for that if you can.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/brainer.png");
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
                "So chopper, there you are. Enough for you.", "Choppers lead biker gangs. They’re powerful but lots of their power is external, in their gang. If you want weight to throw around, play a chopper—but if you want to be really in charge, play a hardholder instead. Warning: externalizing your power means drama. Expect drama.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/chopper.png");
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
                "In Apocalypse World the horizons are dark, and no roads go to them.", "Drivers have cars, meaning mobility, freedom, and places to go. If you can’t see the post-apocalypse without cars, you gotta be a driver. Warning: your loose ties can accidentally keep you out of the action. Commit to the other characters to stay in play.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/driver.png");
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
                "Sometimes the obvious move is the right one.", "Gunluggers are the baddest asses. Their moves are simple, direct and violent. Crude, even. If you want to take no shit, play a gunlugger. Warning: like angels, if things are going well, you might be kicking your heels. Interesting relationships can keep you in the scene.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/gunlugger.png");
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
                "In times of abundance, your holding’s surplus is yours to spend personally as you see fit. (Suppose that your citizen’s lives are the more abundant too, in proportion.) You can see what 1-barter is worth, from the above. For better stuff, be prepared to make unique arrangements, probably by treating with another hardholder nearby.", "There is no government, no society, in Apocalypse World. When hardholders ruled whole continents, when they waged war on the other side of the world instead of with the hold across the burn-flat, when their armies numbered in the hundreds of thousands and they had fucking _boats_ to hold their fucking _airplanes_ on, that was the golden age of legend. Now, anyone with a concrete compound and a gang of gunluggers can claim the title. You, you got something to say about it?", "Hardholders are landlords, warlords, governors of their own little strongholds. If anybody plays a hardholder, the game’s going to have a serious and immobile home base. If you want to be the one who owns it, it better be you. Warning: don’t be a hardholder unless you want the burdens.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/hardholder.png");
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
                "My theory is that these weird hocus fuckers, when they say “the gods,” what they really mean is the miasma left over from the explosion of psychic hate and desperation that gave Apocalypse World its birth. Friends, that’s our creator now.", "Hocuses have cult followers the way choppers have gangs. They’re strange, social, public and compelling. If you want to sway mobs, play a hocus. Warning: things are going to come looking for you. Being a cult leader means having to deal with your fucking cult.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/hocus.png");
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
                "Here in Apocalypse World, those two guys are dead. They died and the fat sizzled off them, they died same as much-luxe-tune and all-you-can-eat. The maestro d’ now, he can’t give you what those guys used to could, but fuck it, maybe he can find you a little somethin somethin to take off the edge.", "The maestro d’ runs a social establishment, like a bar, a drug den or a bordello. If you want to be sexier than a hardholder, with fewer obligations and less shit to deal with, play a maestro d’. Warning: fewer obligations and less shit, not none and none.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/maestrod.png");
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
                "For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.", "If there’s one fucking thing you can count on in Apocalypse World, it’s: things break.", "Savvyheads are techies. They have really cool abilities in the form of their workspace, and a couple of fun reality-bending moves. Play a savvyhead if you want to be powerful and useful as an ally, but maybe not the leader yourself. Warning: your workspace depends on resources, and lots of them, so make friends with everyone you can.", "https://awc-images.s3-ap-southeast-2.amazonaws.com/savvyhead.png");
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
                "Anything beautiful left in this ugly ass world, skinners hold it. Will they share it with you? What do you offer _them_?", "Skinners are pure hot. They’re entirely social and they have great, directly manipulative moves. Play a skinner if you want to be unignorable. Warning: skinners have the tools, but unlike hardholders, choppers and hocuses, they don’t have a steady influx of motivation. You’ll have most fun if you can roll your own.\n", "https://awc-images.s3-ap-southeast-2.amazonaws.com/skinner.png");

        playbookService.saveAll(Flux.just(angel, battlebabe, brainer, chopper, driver, gunlugger, hardholder,
                maestroD, hocus, savvyhead, skinner)).blockLast();

        List<Playbook> playbooks = playbookService.findAll().collectList().block();
    }

    public void loadPlaybookCreators() {
        System.out.println("|| --- Loading playbook creators --- ||");
        /* ----------------------------- ANGEL PLAYBOOK CREATOR --------------------------------- */
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
        PlaybookCreator angelCreator = new PlaybookCreator(Playbooks.ANGEL, angelGearInstructions, "Whenever you roll a highlighted stat, and whenever you reset your Hx with someone, mark an experience circle. When you mark the 5th, improve and erase.\n" +
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
        playbookCreatorService.save(angelCreator).block();

        List<PlaybookCreator> playbookCreators = playbookCreatorService.findAll().collectList().block();
    }

    public void loadStatsOptions() {
        System.out.println("|| --- Loading playbook stats options --- ||");
        /* ----------------------------- ANGEL STATS OPTIONS --------------------------------- */
        StatsOption angel1 = new StatsOption(Playbooks.ANGEL, 1, 0, 1, 2, -1);
        StatsOption angel2 = new StatsOption(Playbooks.ANGEL, 1, 1, 0, 2, -1);
        StatsOption angel3 = new StatsOption(Playbooks.ANGEL, -1, 1, 0, 2, 1);
        StatsOption angel4 = new StatsOption(Playbooks.ANGEL, 2, 0, -1, 2, -1);
        statsOptionService.saveAll(Flux.just(angel1, angel2, angel3, angel4)).blockLast();

        List<StatsOption> statsOptions = statsOptionService.findAll().collectList().block();
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

        List<Look> looks = lookService.findAll().collectList().block();
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

        List<Name> names = nameService.findAll().collectList().block();
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

        moveService.saveAll(Flux.just(doSomethingUnderFire, goAggro, sucker, doBattle, seduceOrManip, helpOrInterfere,
                readASitch, readAPerson, openBrain, lifestyleAndGigs, sessionEnd)).blockLast();

        System.out.println("|| --- Loading peripheral moves --- ||");
        /* ----------------------------- PERIPHERAL MOVES --------------------------------- */
        Move sufferHarm = new Move("SUFFER HARM", "When you _**suffer harm**_, roll+harm suffered (after armor, if you’re wearing any).\n" +
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
                "On a miss, the MC can nevertheless choose something from the 7–9 list above. If she does, though, it’s instead of some of the harm you’re suffering, so you take -1harm.", null, MoveKinds.PERIPHERAL, null);
        Move inflictHarm = new Move("INFLICT HARM ON PC", "When you _**inflict harm on another player’s character**_, the other character gets +1Hx with you (on their sheet) for every segment of harm you inflict. If this brings them to Hx+4, they reset to Hx+1 as usual, and therefore mark experience.", Stats.HX, MoveKinds.PERIPHERAL, null);
        Move healPcHarm = new Move("HEAL PC HARM", "When you _**heal another player’s character’s harm**_, you get +1Hx with them (on your sheet) for every segment of harm you heal. If this brings you to Hx+4, you reset to Hx+1 as usual, and therefore mark experience.", Stats.HX, MoveKinds.PERIPHERAL, null);
        Move giveBarter = new Move("GIVE BARTER", "When you _**give 1-barter to someone, but with strings attached**_, it counts as manipulating them and hitting the roll with a 10+, no leverage or roll required.", null, MoveKinds.PERIPHERAL, null);
        Move goToMarket = new Move("GO TO THE MARKET", "When you _**go into a holding’s bustling market**_, looking for some particular thing to buy, and it’s not obvious whether you should be able to just go buy one like that, roll+sharp.\n" +
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
                "On a miss, the MC chooses 1, plus it costs 1-barter more.", Stats.SHARP, MoveKinds.PERIPHERAL, null);
        Move makeWantKnown = new Move("MAKE WANT KNOWN", "When you _**make known that you want a thing and drop jingle to speed it on its way**_, roll+barter spent (max roll+3). It has to be a thing you could legitimately get this way. On a 10+ it comes to you, no strings attached. On a 7–9 it comes to you, or something pretty close. On a miss, it comes to you, but with strings very much attached.", null, MoveKinds.PERIPHERAL, null);
        Move insight = new Move("INSIGHT", "When you are able to go to someone for _**insight**_, ask them what they think your best course is, and the MC will tell you. If you pursue that course, take +1 to any rolls you make in the pursuit. If you pursue that course but don’t accomplish your ends, you mark experience.", null, MoveKinds.PERIPHERAL, null);
        Move augury = new Move("AUGURY", "When you are able to use something for _**augury**_, roll+weird.\n" +
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
                "On a miss, whatever bad happens, your antenna takes the brunt of it.", Stats.WEIRD, MoveKinds.PERIPHERAL, null);
        Move changeHighlightedStats = new Move("CHANGE HIGHLIGHTED STATS", "_**At the beginning of any session**_, or at the end if you forgot, anyone can say, “hey, let’s change highlighted stats.” When someone says it, do it. Go around the circle again, following the same procedure you used to highlight them in the first place: the high-Hx player highlights one stat, and the MC highlight another.", null, MoveKinds.PERIPHERAL, null);

        moveService.saveAll(Flux.just(sufferHarm, inflictHarm, healPcHarm, giveBarter, goToMarket, makeWantKnown,
                insight, augury, changeHighlightedStats)).blockLast();

        System.out.println("|| --- Loading battle moves --- ||");
        /* ----------------------------- BATTLE MOVES --------------------------------- */
        Move exchangeHarm = new Move("EXCHANGE HARM", "When you _**exchange harm**_, both sides simultaneously inflict and suffer harm as established:\n" +
                "\n" +
                "- *You inflict harm equal to the harm rating of your weapon, minus the armor rating of your enemy’s armor.*\n" +
                "- *You suffer harm equal to the harm rating of your enemy’s weapon, minus the armor rating of your own armor.*", null, MoveKinds.BATTLE, null);
        Move seizeByForce = new Move("SEIZE BY FORCE", "To _**seize something by force**_, exchange harm, but first roll+hard.\n" +
                "\n" +
                "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                "\n" +
                "- *You inflict terrible harm (+1harm).*\n" +
                "- *You suffer little harm (-1harm).*\n" +
                "- *You take definite and undeniable control of it.*\n" +
                "- *You impress, dismay, or frighten your enemy.*", Stats.HARD, MoveKinds.BATTLE, null);
        Move assaultAPosition = new Move("ASSAULT A POSITION", "To _**assault a secure position**_, exchange harm, but first roll+hard.\n" +
                "\n" +
                "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                "\n" +
                "- *You inflict terrible harm (+1harm).*\n" +
                "- *You suffer little harm (-1harm).*\n" +
                "- *You force your way into your enemy’s position.*\n" +
                "- *You impress, dismay, or frighten your enemy.*", Stats.HARD, MoveKinds.BATTLE, null);
        Move keepHoldOfSomething = new Move("KEEP HOLD OF SOMETHING", "To _**keep hold of something you have**_, exchange harm, but first roll+hard.\n" +
                "\n" +
                "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                "\n" +
                "- *You inflict terrible harm (+1harm).*\n" +
                "- *You suffer little harm (-1harm).*\n" +
                "- *You keep definite control of it.*\n" +
                "- *You impress, dismay, or frighten your enemy.*", Stats.HARD, MoveKinds.BATTLE, null);
        Move fightFree = new Move("FIGHT FREE", "To _**fight your way free**_, exchange harm, but first roll+hard.\n" +
                "\n" +
                "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                "\n" +
                "- *You inflict terrible harm (+1harm).*\n" +
                "- *You suffer little harm (-1harm).*\n" +
                "- *You win free and get away.*\n" +
                "- *You impress, dismay, or frighten your enemy.*", Stats.HARD, MoveKinds.BATTLE, null);
        Move defendSomeone = new Move("DEFEND SOMEONE", "To _**defend someone else from attack**_, exchange harm, but first roll+hard.\n" +
                "\n" +
                "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                "\n" +
                "- *You inflict terrible harm (+1harm).*\n" +
                "- *You suffer little harm (-1harm).*\n" +
                "- *You protect them from harm.*\n" +
                "- *You impress, dismay, or frighten your enemy.*", Stats.HARD, MoveKinds.BATTLE, null);
        Move doSingleCombat = new Move("DO SINGLE COMBAT", "When you _**do single combat with someone**_, no quarters, exchange harm, but first roll+hard.\n" +
                "\n" +
                "On a 10+, both. On a 7–9, choose 1. On a miss, your opponent chooses 1 against you:\n" +
                "\n" +
                "- *You inflict terrible harm (+1harm).*\n" +
                "- *You suffer little harm (-1harm).*\n" +
                "\n" +
                "After you exchange harm, do you prefer to end the fight now, or fight on? If both of you prefer to end the fight now, it ends. If both of you prefer to fight on, it continues, and you must make the move again. If one of you prefers to end the fight, though, and the other prefers to fight on, then the former must choose: flee, submit to the latter‘s mercy, or fight on after all.", Stats.HARD, MoveKinds.BATTLE, null);
        Move layDownFire = new Move("LAY DOWN FIRE", "When you _**lay down fire**_, roll+hard.\n" +
                "\n" +
                "On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:\n" +
                "\n" +
                "- *You provide covering fire, allowing another character to move or act freely.*\n" +
                "- *You provide supporting fire, giving another PC +1choice to their own battle move.*\n" +
                "- *You provide suppressing fire, denying another character to move or act freely. (If a PC, they may still act under fire.)*\n" +
                "- *You take an opportune shot, inflicting harm (but -1harm) on an enemy within your reach.*", Stats.HARD, MoveKinds.BATTLE, null);
        Move standOverwatch = new Move("STAND OVERWATCH", "When you _**stand overwatch**_ for an ally, roll+cool. On a hit, if anyone attacks or interferes with your ally, you attack them and inflict harm as established, as well as warning your ally.\n" +
                "\n" +
                "On a 10+, choose 1:\n" +
                "\n" +
                "- *...And you inflict your harm before they can carry out their attack or interference.*\n" +
                "- *...And you inflict terrible harm (+1harm).*\n" +
                "\n" +
                "On a miss, you are able to warn your ally but not attack your enemy.", Stats.COOL, MoveKinds.BATTLE, null);
        Move keepAnEyeOut = new Move("KEEP AN EYE OUT", "When you _**keep an eye out**_ for what’s coming, roll+sharp.\n" +
                "\n" +
                "On a 10+, hold 3. On a 7–9, hold 2. On a miss, hold 1. During the battle, spend your hold, 1 for 1, to ask the MC what’s coming and choose 1:\n" +
                "\n" +
                "- *Direct a PC ally’s attention to an enemy. If they make a battle move against that enemy, they get +1choice to their move.*\n" +
                "- *Give a PC ally an order, instruction, or suggestion. If they do it, they get +1 to any rolls they make in the effort.*\n" +
                "- *Direct any ally’s attention to an enemy. If they attack that enemy, they inflict +1harm.*\n" +
                "- *Direct any ally’s attention to a danger. They take -1harm from that danger.*", Stats.SHARP, MoveKinds.BATTLE, null);
        Move beTheBait = new Move("BE THE BAIT", "When _**you’re the bait**_, roll+cool.\n" +
                "\n" +
                "On a 10+, choose 2. On a 7–9, choose 1:\n" +
                "\n" +
                "- *You draw your prey all the way into the trap. Otherwise, they only approach.*\n" +
                "- *Your prey doesn’t suspect you. Otherwise, they’re wary and alert.*\n" +
                "- *You don’t expose yourself to extra risk. Otherwise, any harm your prey inflicts is +1.*\n" +
                "\n" +
                "On a miss, the MC chooses 1 for you.", Stats.COOL, MoveKinds.BATTLE, null);
        Move beTheCat = new Move("BE THE CAT", "When _**you’re the cat**_, roll+cool. On a hit, you catch your prey out.\n" +
                "\n" +
                "On a 10+, you’ve driven them first to a place of your choosing; say where.\n" +
                "\n" +
                "On a 7–9, you’ve had to follow them where they wanted to go; they say where.\n" +
                "\n" +
                "On a miss, your prey escapes you.", Stats.COOL, MoveKinds.BATTLE, null);
        Move beTheMouse = new Move("BE THE MOUSE", "When _**you’re the mouse**_, roll+cool.\n" +
                "\n" +
                "On a 10+, you escape clean and leave your hunter hunting.\n" +
                "\n" +
                "On a 7–9, your hunter catches you out, but only after you’ve led them to a place of your choosing; say where.\n" +
                "\n" +
                "On a miss, your hunter catches you out and the MC says where.", Stats.COOL, MoveKinds.BATTLE, null);
        Move catOrMouse = new Move("CAT OR MOUSE", "When _**it’s not certain whether you’re the cat or the mouse**_, roll+sharp. On a hit, you decide which you are.\n" +
                "\n" +
                "On a 10+, you take +1forward as well.\n" +
                "\n" +
                "On a miss, you’re the mouse.", Stats.SHARP, MoveKinds.BATTLE, null);

        moveService.saveAll(Flux.just(exchangeHarm, seizeByForce, assaultAPosition, keepHoldOfSomething,
                fightFree, defendSomeone, doSingleCombat, layDownFire, standOverwatch, keepAnEyeOut,
                beTheBait, beTheCat, beTheMouse, catOrMouse)).blockLast();

        System.out.println("|| --- Loading road war moves --- ||");
        /* ----------------------------- ROAD WAR MOVES --------------------------------- */
        Move boardAMovingVehicle = new Move("BOARD A MOVING VEHICLE", "To _**board a moving vehicle**_, roll+cool, minus its speed. To board one moving vehicle from another, roll+cool, minus the difference between their speeds.\n" +
                "\n" +
                "On a 10+, you’re on and you made it look easy. Take +1forward.\n" +
                "\n" +
                "On a 7–9, you’re on, but jesus.\n" +
                "\n" +
                "On a miss, the MC chooses: you’re hanging on for dear life, or you’re down and good luck to you.", Stats.COOL, MoveKinds.ROAD_WAR, null);
        Move outdistanceAnotherVehicle = new Move("OUTDISTANCE ANOTHER VEHICLE", "When you try to outdistance another vehicle, roll+cool, modified by the vehicles’ relative speed.\n" +
                "\n" +
                "On a 10+, you outdistance them and break away.\n" +
                "\n" +
                "On a 7–9, choose 1:\n" +
                "\n" +
                "- *You outdistance them and break away, but your vehicle suffers 1-harm ap from the strain.*\n" +
                "- *You don’t escape them, but you can go to ground in a place you choose.*\n" +
                "- *They overtake you, but their vehicle suffers 1-harm ap from the strain.*\n" +
                "\n" +
                "On a miss, your counterpart chooses 1 against you.", Stats.COOL, MoveKinds.ROAD_WAR, null);
        Move overtakeAnotherVehicle = new Move("OVERTAKE ANOTHER VEHICLE", "When you _**try to overtake another vehicle**_, roll+cool, modified by the vehicles’ relative speed.\n" +
                "\n" +
                "On a 10+, you overtake them and draw alongside.\n" +
                "\n" +
                "On a 7–9, choose 1:\n" +
                "\n" +
                "- *You overtake them, but your vehicle suffers 1-harm ap the same.*\n" +
                "- *You don’t overtake them, but you can drive them into a place you choose.*\n" +
                "- *They outdistance you, but their vehicle suffers 1-harm ap the same.*\n" +
                "\n" +
                "On a miss, your counterpart chooses 1 against you.", Stats.COOL, MoveKinds.ROAD_WAR, null);
        Move dealWithBadTerrain = new Move("DEAL WITH BAD TERRAIN", "When you have to _**deal with bad terrain**_, roll+cool, plus your vehicle’s handling.\n" +
                "\n" +
                "On a 10+, you fly through untouched.\n" +
                "\n" +
                "On a 7–9, choose 1:\n" +
                "\n" +
                "- *You slow down and pick your way forward.*\n" +
                "- *You push too hard and your vehicle suffers harm as established.*\n" +
                "- *You ditch out and go back or try to find another way.*\n" +
                "\n" +
                "On a miss, the MC chooses 1 for you; the others are impossible.", Stats.COOL, MoveKinds.ROAD_WAR, null);
        Move shoulderAnotherVehicle = new Move("SHOULDER ANOTHER VEHICLE", "To _**shoulder another vehicle**_, roll+cool. On a hit, you shoulder it aside, inflicting v-harm as established.\n" +
                "\n" +
                "On a 10+, you inflict v-harm+1.\n" +
                "\n" +
                "On a miss, it shoulders you instead, inflicting v-harm as established.", Stats.COOL, MoveKinds.ROAD_WAR, null);

        moveService.saveAll(Flux.just(boardAMovingVehicle, outdistanceAnotherVehicle, overtakeAnotherVehicle,
                dealWithBadTerrain, shoulderAnotherVehicle)).blockLast();

        /* ----------------------------- ANGEL MOVES --------------------------------- */
        System.out.println("|| --- Loading Angel moves --- ||");
        Move angelSpecial = new Move("ANGEL SPECIAL", "If you and another character have sex, your Hx with them on your sheet goes immediately to +3, and they immediately get +1 to their Hx with you on their sheet. If that brings their Hx with you to +4, they reset it to +1 instead, as usual, and so mark experience.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move sixthSense = new Move("SIXTH SENSE", "_**Sixth sense**_: when you open your brain to the world’s psychic maelstrom, roll+sharp instead of +weird.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move infirmary = new Move("INFIRMARY", "_**Infirmary**_: you get an infirmary, a workspace with life support, a drug lab and a crew of 2 (Shigusa & Mox, maybe). Get patients into it and you can work on them like a savvyhead on tech (_cf_).", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move profCompassion = new Move("PROFESSIONAL COMPASSION", "_**Professional compassion**_: you can roll+sharp instead of roll+Hx when you help someone who’s rolling.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move battlefieldGrace = new Move("BATTLEFIELD GRACE", "_**Battlefield grace**_: while you are caring for people, not fighting, you get +1armor.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move healingTouch = new Move("HEALING TOUCH", "_**Healing touch**_: when you put your hands skin-to-skin on a wounded person and open your brain to them, roll+weird.\n" +
                "\n" +
                "On a 10+, heal 1 segment.\n" +
                "\n" +
                "On a 7–9, heal 1 segment, but you’re also opening your brain, so roll that move next.\n" +
                "\n" +
                "On a miss: first, you don’t heal them. Second, you’ve opened both your brain and theirs to the world’s psychic maelstrom, without protection or preparation. For you, and for your patient if your patient’s a fellow player’s character, treat it as though you’ve made that move and missed the roll. For patients belonging to the MC, their experience and fate are up to the MC.\n", Stats.WEIRD, MoveKinds.CHARACTER, Playbooks.ANGEL);
        Move touchedByDeath = new Move("HEALING TOUCH", "_**Touched by death**_: when someone is unconscious in your care, you can use them for _**augury**_. When someone has died in your care, you can use their body for _**augury**_.", null, MoveKinds.CHARACTER, Playbooks.ANGEL);

        moveService.saveAll(Flux.just(angelSpecial, sixthSense, infirmary, profCompassion,
                battlefieldGrace, healingTouch, touchedByDeath)).blockLast();

        List<Move> moves = moveService.findAll().collectList().block();
    }

    private void createPlaybooks() {
        // -------------------------------------- Set up Playbooks -------------------------------------- //
        // -------------------------------------- ANGEL -------------------------------------- //
        PlaybookCreator playbookCreatorAngel = playbookCreatorService.findByPlaybookType(Playbooks.ANGEL).block();
        assert playbookCreatorAngel != null;

        Playbook playbookAngel = playbookService.findByPlaybookType(Playbooks.ANGEL).block();
        assert playbookAngel != null;

        List<Name> namesAngel = nameService.findAllByPlaybookType(Playbooks.ANGEL).collectList().block();
        assert namesAngel != null;

        List<Look> looksAngel = lookService.findAllByPlaybookType(Playbooks.ANGEL).collectList().block();
        assert looksAngel != null;

        List<StatsOption> statsOptionsAngel = statsOptionService.findAllByPlaybookType(Playbooks.ANGEL).collectList().block();
        assert statsOptionsAngel != null;

        for (StatsOption statsOption : statsOptionsAngel) {
            playbookCreatorAngel.getStatsOptions().add(statsOption);
        }

        namesAngel.forEach(name -> playbookCreatorAngel.getNames().add(name));
        looksAngel.forEach(look -> playbookCreatorAngel.getLooks().add(look));
        playbookCreatorService.save(playbookCreatorAngel).block();
        playbookAngel.setCreator(playbookCreatorAngel);
        playbookService.save(playbookAngel).block();
    }
}
