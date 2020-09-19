-- MOVES
-- Basic moves
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('DO SOMETHING UNDER FIRE', 'When you _**do something under fire**_, or dig in to endure fire, roll+cool. On a 10+, you do it. On a 7–9, you flinch, hesitate, or stall: the MC can offer you a worse outcome, a hard bargain, or an ugly choice. On a miss, be prepared for the worst.', 'COOL', 'BASIC', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('GO AGGRO ON SOMEONE', 'When you _**go aggro on someone**_, make it clear what you want them to do and what you’ll do to them. Roll+hard. On a 10+, they have to choose:

- *Force your hand and suck it up.*
- *Cave and do what you want.*

On a 7–9, they can choose 1 of the above, or 1 of the following:

- *Get the hell out of your way.*
- *Barricade themselves securely in.*
- *Give you something they think you want, or tell you what you want to hear.*
- *Back off calmly, hands where you can see.*

On a miss, be prepared for the worst.', 'HARD', 'BASIC', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('SUCKER SOMEONE', 'When you _**attack someone unsuspecting or helpless**_, ask the MC if you could miss. If you could, treat it as going aggro, but your victim has no choice to cave and do what you want. If you couldn’t, you simply inflict harm as established.', null, 'BASIC', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('DO BATTLE', 'When you’re _**in battle**_, you can bring the battle moves into play.', null, 'BASIC', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('SEDUCE OR MANIPULATE SOMEONE', 'When you _**try to seduce, manipulate, bluff, fast-talk, or lie to someone**_, tell them what you want them to do, give them a reason, and roll+hot. **For NPCs**: on a 10+, they’ll go along with you, unless or until some fact or action betrays the reason you gave them. On a 7–9, they’ll go along with you, but they need some concrete assurance, corroboration, or evidence first. **For PCs**: on a 10+, both. On a 7–9, choose 1:

- *If they go along with you, they mark experience.*
- *If they refuse, erase one of their stat highlights for the remainder of the session.*

What they do then is up to them.

On a miss, for either NPCs or PCs, be prepared for the worst.', 'HOT', 'BASIC', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('HELP OR INTERFERE WITH SOMEONE', 'When you _**help**_ or _**interfere**_ with someone who’s making a roll, roll+Hx. On a 10+, they take +2 (help) or -2 (interfere) to their roll. On a 7–9, they take +1 (help) or -1 (interfere) to their roll. On a miss, be prepared for the worst.', 'HX', 'BASIC', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('READ A SITCH', 'When you _**read a charged situation**_, roll+sharp. On a hit, you can ask the MC questions. Whenever you act on one of the MC’s answers, take +1. On a 10+, ask 3. On a 7–9, ask 1:

- *Where’s my best escape route / way in / way past?*
- *Which enemy is most vulnerable to me?*
- *Which enemy is the biggest threat?*
- *What should I be on the lookout for?*
- *What’s my enemy’s true position?*
- *Who’s in control here?*

On a miss, ask 1 anyway, but be prepared for the worst.', 'SHARP', 'BASIC', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('READ A PERSON', 'When you _**read a person**_ in a charged interaction, roll+sharp. On a 10+, hold 3. On a 7–9, hold 1. While you’re interacting with them, spend your hold to ask their player questions, 1 for 1:

- *Is your character telling the truth?*
- *What’s your character really feeling?*
- *What does your character intend to do?*
- *What does your character wish I’d do?*
- *How could I get your character to__?*

On a miss, ask 1 anyway, but be prepared for the worst.', 'SHARP', 'BASIC', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('OPEN YOUR BRAIN', 'When you _**open your brain to the world’s psychic maelstrom**_, roll+weird. On a hit, the MC tells you something new and interesting about the current situation, and might ask you a question or two; answer them. On a 10+, the MC gives you good detail. On a 7–9, the MC gives you an impression. If you already know all there is to know, the MC will tell you that. On a miss, be prepared for the worst.', 'WEIRD', 'BASIC', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('LIFESTYLE AND GIGS', '_**At the beginning of the session**_, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions. If you need jingle during a session, tell the MC you’d like to work a gig.', null, 'BASIC', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('SESSION END', '_**At the end of every session**_, choose a character who knows you better than they used to. If there’s more than one, choose one at your whim. Tell that player to add +1 to their Hx with you on their sheet. If this brings them to Hx+4, they reset to Hx+1 (and therefore mark experience). If no one knows you better, choose a character who doesn’t know you as well as they thought, or choose any character at your whim. Tell that player to take -1 to their Hx with you on their sheet. If this brings them to Hx -3, they reset to Hx=0 (and therefore mark experience).', null, 'BASIC', null);

-- Peripheral moves
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('SUFFER HARM', 'When you _**suffer harm**_, roll+harm suffered (after armor, if you’re wearing any).

On a 10+, the MC can choose 1:

- *You’re out of action: unconscious, trapped, incoherent or panicked.*
- *It’s worse than it seemed. Take an additional 1-harm.*
- *Choose 2 from the 7–9 list below.*

On a 7–9, the MC can choose 1:

- *You lose your footing.*
- *You lose your grip on whatever you’re holding.*
- *You lose track of someone or something you’re attending to.*
- *You miss noticing something important.*

On a miss, the MC can nevertheless choose something from the 7–9 list above. If she does, though, it’s instead of some of the harm you’re suffering, so you take -1harm.', null, 'PERIPHERAL', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('INFLICT HARM ON PC', 'When you _**inflict harm on another player’s character**_, the other character gets +1Hx with you (on their sheet) for every segment of harm you inflict. If this brings them to Hx+4, they reset to Hx+1 as usual, and therefore mark experience.', 'HX', 'PERIPHERAL', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('HEAL PC HARM', 'When you _**heal another player’s character’s harm**_, you get +1Hx with them (on your sheet) for every segment of harm you heal. If this brings you to Hx+4, you reset to Hx+1 as usual, and therefore mark experience.', 'HX', 'PERIPHERAL', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('GIVE BARTER', 'When you _**give 1-barter to someone, but with strings attached**_, it counts as manipulating them and hitting the roll with a 10+, no leverage or roll required.', null, 'PERIPHERAL', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('GO TO THE MARKET', 'When you _**go into a holding’s bustling market**_, looking for some particular thing to buy, and it’s not obvious whether you should be able to just go buy one like that, roll+sharp.

On a 10+, yes, you can just go buy it like that.

On a 7–9, the MC chooses 1:

- *It costs 1-barter more than you’d expect.*
- *It’s not openly for sale, but you find someone who can lead you to someone selling it.*
- *It’s not openly for sale, but you find someone who sold it recently, who may be willing to introduce you to their previous buyer.*
- *It’s not available for sale, but you find something similar. Will it do?*

On a miss, the MC chooses 1, plus it costs 1-barter more.', 'SHARP', 'PERIPHERAL', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('MAKE WANT KNOWN', 'When you _**make known that you want a thing and drop jingle to speed it on its way**_, roll+barter spent (max roll+3). It has to be a thing you could legitimately get this way. On a 10+ it comes to you, no strings attached. On a 7–9 it comes to you, or something pretty close. On a miss, it comes to you, but with strings very much attached.', null, 'PERIPHERAL', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('INSIGHT', 'When you are able to go to someone for _**insight**_, ask them what they think your best course is, and the MC will tell you. If you pursue that course, take +1 to any rolls you make in the pursuit. If you pursue that course but don’t accomplish your ends, you mark experience.', null, 'PERIPHERAL', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('AUGURY', 'When you are able to use something for _**augury**_, roll+weird.

On a hit, you can choose 1:

- *Reach through the world’s psychic maelstrom to something or someone connected to it.*
- *Isolate and protect a person or thing from the world’s psychic maelstrom.*
- *Isolate and contain a fragment of the world’s psychic maelstrom itself.*
- *Insert information into the world’s psychic maelstrom.*
- *Open a window into the world’s psychic maelstrom.*

By default, the effect will last only as long as you maintain it, will reach only shallowly into the world’s psychic maelstrom as it is local to you, and will bleed instability.

On a 10+, choose 2; on a 7–9, choose 1:

- *It’ll persist (for a while) without your actively maintaining it.*
- *It reaches deep into the world’s psychic maelstrom.*
- *It reaches broadly throughout the world’s psychic maelstrom.*
- *It’s stable and contained, no bleeding.*

On a miss, whatever bad happens, your antenna takes the brunt of it.', 'WEIRD', 'PERIPHERAL', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('CHANGE HIGHLIGHTED STATS', '_**At the beginning of any session**_, or at the end if you forgot, anyone can say, “hey, let’s change highlighted stats.” When someone says it, do it. Go around the circle again, following the same procedure you used to highlight them in the first place: the high-Hx player highlights one stat, and the MC highlight another.', null, 'PERIPHERAL', null);

-- Battle moves
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('EXCHANGE HARM', 'When you _**exchange harm**_, both sides simultaneously inflict and suffer harm as established:

- *You inflict harm equal to the harm rating of your weapon, minus the armor rating of your enemy’s armor.*
- *You suffer harm equal to the harm rating of your enemy’s weapon, minus the armor rating of your own armor.*', null, 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('SEIZE BY FORCE', 'To _**seize something by force**_, exchange harm, but first roll+hard.

On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:

- *You inflict terrible harm (+1harm).*
- *You suffer little harm (-1harm).*
- *You take definite and undeniable control of it.*
- *You impress, dismay, or frighten your enemy.*', 'HARD', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('ASSAULT A POSITION', 'To _**assault a secure position**_, exchange harm, but first roll+hard.

On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:

- *You inflict terrible harm (+1harm).*
- *You suffer little harm (-1harm).*
- *You force your way into your enemy’s position.*
- *You impress, dismay, or frighten your enemy.*', 'HARD', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('KEEP HOLD OF SOMETHING', 'To _**keep hold of something you have**_, exchange harm, but first roll+hard.

On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:

- *You inflict terrible harm (+1harm).*
- *You suffer little harm (-1harm).*
- *You keep definite control of it.*
- *You impress, dismay, or frighten your enemy.*', 'HARD', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('FIGHT FREE', 'To _**fight your way free**_, exchange harm, but first roll+hard.

On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:

- *You inflict terrible harm (+1harm).*
- *You suffer little harm (-1harm).*
- *You win free and get away.*
- *You impress, dismay, or frighten your enemy.*', 'HARD', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('DEFEND SOMEONE', 'To _**defend someone else from attack**_, exchange harm, but first roll+hard.

On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:

- *You inflict terrible harm (+1harm).*
- *You suffer little harm (-1harm).*
- *You protect them from harm.*
- *You impress, dismay, or frighten your enemy.*', 'HARD', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('DO SINGLE COMBAT', 'When you _**do single combat with someone**_, no quarters, exchange harm, but first roll+hard.

On a 10+, both. On a 7–9, choose 1. On a miss, your opponent chooses 1 against you:

- *You inflict terrible harm (+1harm).*
- *You suffer little harm (-1harm).*

After you exchange harm, do you prefer to end the fight now, or fight on? If both of you prefer to end the fight now, it ends. If both of you prefer to fight on, it continues, and you must make the move again. If one of you prefers to end the fight, though, and the other prefers to fight on, then the former must choose: flee, submit to the latter‘s mercy, or fight on after all.', 'HARD', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('LAY DOWN FIRE', 'When you _**lay down fire**_, roll+hard.

On a 10+, choose 3. On a 7–9, choose 2. On a miss, choose 1:

- *You provide covering fire, allowing another character to move or act freely.*
- *You provide supporting fire, giving another PC +1choice to their own battle move.*
- *You provide suppressing fire, denying another character to move or act freely. (If a PC, they may still act under fire.)*
- *You take an opportune shot, inflicting harm (but -1harm) on an enemy within your reach.*', 'HARD', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('STAND OVERWATCH', 'When you _**stand overwatch**_ for an ally, roll+cool. On a hit, if anyone attacks or interferes with your ally, you attack them and inflict harm as established, as well as warning your ally.

On a 10+, choose 1:

- *...And you inflict your harm before they can carry out their attack or interference.*
- *...And you inflict terrible harm (+1harm).*

On a miss, you are able to warn your ally but not attack your enemy.', 'COOL', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('KEEP AN EYE OUT', 'When you _**keep an eye out**_ for what’s coming, roll+sharp.

On a 10+, hold 3. On a 7–9, hold 2. On a miss, hold 1. During the battle, spend your hold, 1 for 1, to ask the MC what’s coming and choose 1:

- *Direct a PC ally’s attention to an enemy. If they make a battle move against that enemy, they get +1choice to their move.*
- *Give a PC ally an order, instruction, or suggestion. If they do it, they get +1 to any rolls they make in the effort.*
- *Direct any ally’s attention to an enemy. If they attack that enemy, they inflict +1harm.*
- *Direct any ally’s attention to a danger. They take -1harm from that danger.*', 'SHARP', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('BE THE BAIT', 'When _**you’re the bait**_, roll+cool.

On a 10+, choose 2. On a 7–9, choose 1:

- *You draw your prey all the way into the trap. Otherwise, they only approach.*
- *Your prey doesn’t suspect you. Otherwise, they’re wary and alert.*
- *You don’t expose yourself to extra risk. Otherwise, any harm your prey inflicts is +1.*

On a miss, the MC chooses 1 for you.', 'COOL', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('BE THE CAT', 'When _**you’re the cat**_, roll+cool. On a hit, you catch your prey out.

On a 10+, you’ve driven them first to a place of your choosing; say where.

On a 7–9, you’ve had to follow them where they wanted to go; they say where.

On a miss, your prey escapes you.', 'COOL', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('BE THE MOUSE', 'When _**you’re the mouse**_, roll+cool.

On a 10+, you escape clean and leave your hunter hunting.

On a 7–9, your hunter catches you out, but only after you’ve led them to a place of your choosing; say where.

On a miss, your hunter catches you out and the MC says where.', 'COOL', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('CAT OR MOUSE', 'When _**it’s not certain whether you’re the cat or the mouse**_, roll+sharp. On a hit, you decide which you are.

On a 10+, you take +1forward as well.

On a miss, you’re the mouse.', 'SHARP', 'BATTLE', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('BOARD A MOVING VEHICLE', 'To _**board a moving vehicle**_, roll+cool, minus its speed. To board one moving vehicle from another, roll+cool, minus the difference between their speeds.

On a 10+, you’re on and you made it look easy. Take +1forward.

On a 7–9, you’re on, but jesus.

On a miss, the MC chooses: you’re hanging on for dear life, or you’re down and good luck to you.', 'COOL', 'ROAD_WAR', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('OUTDISTANCE ANOTHER VEHICLE', 'When you try to outdistance another vehicle, roll+cool, modified by the vehicles’ relative speed.

On a 10+, you outdistance them and break away.

On a 7–9, choose 1:

- *You outdistance them and break away, but your vehicle suffers 1-harm ap from the strain.*
- *You don’t escape them, but you can go to ground in a place you choose.*
- *They overtake you, but their vehicle suffers 1-harm ap from the strain.*

On a miss, your counterpart chooses 1 against you.', 'COOL', 'ROAD_WAR', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('OVERTAKE ANOTHER VEHICLE', 'When you _**try to overtake another vehicle**_, roll+cool, modified by the vehicles’ relative speed.

On a 10+, you overtake them and draw alongside.

On a 7–9, choose 1:

- *You overtake them, but your vehicle suffers 1-harm ap the same.*
- *You don’t overtake them, but you can drive them into a place you choose.*
- *They outdistance you, but their vehicle suffers 1-harm ap the same.*

On a miss, your counterpart chooses 1 against you.', 'COOL', 'ROAD_WAR', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('DEAL WITH BAD TERRAIN', 'When you have to _**deal with bad terrain**_, roll+cool, plus your vehicle’s handling.

On a 10+, you fly through untouched.

On a 7–9, choose 1:

- *You slow down and pick your way forward.*
- *You push too hard and your vehicle suffers harm as established.*
- *You ditch out and go back or try to find another way.*

On a miss, the MC chooses 1 for you; the others are impossible.', 'COOL', 'ROAD_WAR', null);
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('SHOULDER ANOTHER VEHICLE', 'To _**shoulder another vehicle**_, roll+cool. On a hit, you shoulder it aside, inflicting v-harm as established.

On a 10+, you inflict v-harm+1.

On a miss, it shoulders you instead, inflicting v-harm as established.', 'COOL', 'ROAD_WAR', null);

-- Playbook moves
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('ANGEL SPECIAL', 'If you and another character have sex, your Hx with them on your sheet goes immediately to +3, and they immediately get +1 to their Hx with you on their sheet. If that brings their Hx with you to +4, they reset it to +1 instead, as usual, and so mark experience.
', null, 'CHARACTER', 'ANGEL');
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('SIXTH SENSE', '_**Sixth sense**_: when you open your brain to the world’s psychic maelstrom, roll+sharp instead of +weird.', null, 'CHARACTER', 'ANGEL');
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('INFIRMARY', '_**Infirmary**_: you get an infirmary, a workspace with life support, a drug lab and a crew of 2 (Shigusa & Mox, maybe). Get patients into it and you can work on them like a savvyhead on tech (_cf_).', null, 'CHARACTER', 'ANGEL');
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('PROFESSIONAL COMPASSION', '_**Professional compassion**_: you can roll+sharp instead of roll+Hx when you help someone who’s rolling.', null, 'CHARACTER', 'ANGEL');
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('BATTLEFIELD GRACE', '_**Battlefield grace**_: while you are caring for people, not fighting, you get +1armor.', null, 'CHARACTER', 'ANGEL');
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('HEALING TOUCH', '_**Healing touch**_: when you put your hands skin-to-skin on a wounded person and open your brain to them, roll+weird.

On a 10+, heal 1 segment.

On a 7–9, heal 1 segment, but you’re also opening your brain, so roll that move next.

On a miss: first, you don’t heal them. Second, you’ve opened both your brain and theirs to the world’s psychic maelstrom, without protection or preparation. For you, and for your patient if your patient’s a fellow player’s character, treat it as though you’ve made that move and missed the roll. For patients belonging to the MC, their experience and fate are up to the MC.
', 'WEIRD', 'CHARACTER', 'ANGEL');
INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('TOUCHED BY DEATH', '_**Touched by death**_: when someone is unconscious in your care, you can use them for _**augury**_. When someone has died in your care, you can use their body for _**augury**_.', null, 'CHARACTER', 'ANGEL');
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);
-- INSERT INTO moves (name, description, stat, kind, playbook) VALUES ('', '', STAT, KIND, PLAYBOOK);

-- PLAYBOOKS
-- names
-- looks
-- stats
-- stats_options
-- playbook_creators
INSERT INTO pb_creators (pb_type, gear_instr, imp_instr, moves_instr, hx_instr)
VALUES ('ANGEL', 'You get:

- angel kit, no supplier
- 1 small practical weapon
- oddments worth 2-barter
- fashion suitable to your look, including at your option a piece worth 1-armor (you detail)

Small practical weapons
(choose 1):

- .38 revolver (2-harm close reload loud)
- 9mm (2-harm close loud)
- big knife (2-harm hand)
- sawed-off (3-harm close reload messy) • stun gun (s-harm hand reload)

If you’d like to start play with a vehicle or a prosthetic, get with the MC.', 'Whenever you roll a highlighted stat, and whenever you reset your Hx with someone, mark an experience circle. When you mark the 5th, improve and erase.

Each time you improve, choose one of the options. Check it off; you can’t choose it again.', 'You get all the basic moves. Choose 2 angel moves.

You can use all the battle moves, but when you get the chance, look up _**keeping an eye out**_, and _**baiting a trap**_, as well as the rules for harm.', 'Everyone introduces their characters by name, look and outlook. Take your turn.

List the other characters’ names.

Go around again for Hx. On your turn, ask 1, 2, or all 3:

- Which one of you do I figure is doomed to self-destruction?
For that character, write Hx-2.
- Which one of you put a hand in when it mattered, and helped me save a life?
For that character, write Hx+2.
- Which one of you has been beside me all along, and has seen everything I’ve seen?
For that character, write Hx+3.

For everyone else, write Hx+1. You keep your eyes open.

On the others’ turns, answer their questions as you like.

At the end, choose one of the characters with the highest Hx on your sheet. Ask that player which of your stats is most interesting, and highlight it. The MC will have you highlight a second stat too.
');
-- INSERT INTO pb_creators (pb_type, barter_instr, intro, intro_comment) VALUES ('BATTLEBABE', '', '', '');
-- INSERT INTO pb_creators (pb_type, barter_instr, intro, intro_comment) VALUES ('BRAINER', '', '', '');
-- INSERT INTO pb_creators (pb_type, barter_instr, intro, intro_comment) VALUES ('CHOPPER', '', '', '');
-- INSERT INTO pb_creators (pb_type, barter_instr, intro, intro_comment) VALUES ('DRIVER', '', '', '');
-- INSERT INTO pb_creators (pb_type, barter_instr, intro, intro_comment) VALUES ('GUNLUGGER', '', '', '');
-- INSERT INTO pb_creators (pb_type, barter_instr, intro, intro_comment) VALUES ('HARDHOLDER', '', '', '');
-- INSERT INTO pb_creators (pb_type, barter_instr, intro, intro_comment) VALUES ('HOCUS', '', '', '');
-- INSERT INTO pb_creators (pb_type, barter_instr, intro, intro_comment) VALUES ('MAESTRO_D', '', '', '');
-- INSERT INTO pb_creators (pb_type, barter_instr, intro, intro_comment) VALUES ('SAVVYHEAD', '', '', '');
-- INSERT INTO pb_creators (pb_type, barter_instr, intro, intro_comment) VALUES ('SKINNER', '', '', '');

-- playbooks
INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('ANGEL', 'At the beginning of the session, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions.
If you need jingle during a session, tell the MC you’d like to work a gig. Your gigs:

- *Tend to the health of a dozen families or more*
- *Serve a wealthy NPC as angel on call*
- *Serve a warlord NPC as combat medic*
- *Others, as you negotiate them*

As a one-time expenditure, and very subject to availability, 1-barter might count for:

- *a night in high luxury & company*
- *any weapon, gear or fashion not valuable or hi-tech*
- *repair of a piece of hi-tech gear*
- *a session’s hire of a violent individual as bodyguard*
- *a few sessions’ tribute to a warlord; a few sessions’ maintenance and repairs for a hi-performance vehicle well-used*
- *bribes, fees and gifts sufficient to get you into almost anyone’s presence*

For better stuff, you should expect to make particular arrangements. You can’t just wander around the commons of some hardhold with oddments ajangle and expect to find hi-tech or luxe eternal.', 'When you’re lying in the dust of Apocalypse World guts aspilled, for whom do you pray? Thee gods? They’re long gone. Your beloved comrades? Fuckers all, or you wouldn’t be here to begin with. Your precious old mother? She’s a darling but she can’t put an intestine back inside so it’ll stay. No, you pray for some grinning kid or veteran or just someone with a heartshocker and a hand with sutures and a 6-pack of morphine. And when that someone comes, _that’s_ an angel.', 'Angels are medics. If you want everybody to love you, or at least rely on you, play an angel. Warning: if things are going well, maybe nobody will rely on you. Make interesting relationships so you’ll stay relevant. Or sabotage things, I guess.');

-- INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('BATTLEBABE', '', '', '');
-- INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('BRAINER', '', '', '');
-- INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('CHOPPER', '', '', '');
-- INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('DRIVER', '', '', '');
-- INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('GUNLUGGER', '', '', '');
-- INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('HARDHOLDER', '', '', '');
-- INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('HOCUS', '', '', '');
-- INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('MAESTRO_D', '', '', '');
-- INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('SAVVYHEAD', '', '', '');
-- INSERT INTO playbooks (pb_type, barter_instr, intro, intro_comment) VALUES ('SKINNER', '', '', '');
