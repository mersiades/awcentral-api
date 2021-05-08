package com.mersiades.awccontent.content;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Playbook;
import org.bson.types.ObjectId;

public class PlaybooksContent {
    public static final Playbook angel = Playbook.builder()
            .id(new ObjectId().toString())
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

    public static final Playbook battlebabe = Playbook.builder()
            .id(new ObjectId().toString())
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

    public static final Playbook brainer = Playbook.builder()
            .id(new ObjectId().toString())
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

    public static final Playbook chopper = Playbook.builder()
            .id(new ObjectId().toString())
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

    public static final Playbook driver = Playbook.builder()
            .id(new ObjectId().toString())
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

    public static final Playbook gunlugger = Playbook.builder()
            .id(new ObjectId().toString())
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

    public static final Playbook hardholder = Playbook.builder()
            .id(new ObjectId().toString())
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

    public static final Playbook hocus = Playbook.builder()
            .id(new ObjectId().toString())
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

    public static final Playbook maestroD = Playbook.builder()
            .id(new ObjectId().toString())
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

    public static final Playbook savvyhead = Playbook.builder()
            .id(new ObjectId().toString())
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

    public static final Playbook skinner = Playbook.builder()
            .id(new ObjectId().toString())
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
}
