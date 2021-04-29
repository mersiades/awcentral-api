package com.mersiades.awccontent.content;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.StatsOption;
import org.bson.types.ObjectId;

public class StatOptionsContent {

    public static final StatsOption statsOptionAngel1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.ANGEL).COOL(1).HARD(0).HOT(1).SHARP(2).WEIRD(-1).build(); // 3
    public static final StatsOption statsOptionAngel2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.ANGEL).COOL(1).HARD(1).HOT(0).SHARP(2).WEIRD(-1).build(); // 3
    public static final StatsOption statsOptionAngel3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.ANGEL).COOL(-1).HARD(1).HOT(0).SHARP(2).WEIRD(1).build(); // 3
    public static final StatsOption statsOptionAngel4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.ANGEL).COOL(2).HARD(0).HOT(-1).SHARP(2).WEIRD(-1).build(); // 2

    public static final StatsOption statsOptionBattlebabe1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-1).HOT(1).SHARP(1).WEIRD(0).build(); // 4
    public static final StatsOption statsOptionBattlebabe2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-1).HOT(2).SHARP(0).WEIRD(-1).build(); // 3
    public static final StatsOption statsOptionBattlebabe3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-2).HOT(1).SHARP(1).WEIRD(1).build(); // 4
    public static final StatsOption statsOptionBattlebabe4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(0).HOT(1).SHARP(1).WEIRD(-1).build(); // 3

    public static final StatsOption statsOptionBrainer1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.BRAINER).COOL(1).HARD(1).HOT(-2).SHARP(1).WEIRD(2).build(); // 3
    public static final StatsOption statsOptionBrainer2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.BRAINER).COOL(0).HARD(0).HOT(1).SHARP(0).WEIRD(2).build(); // 3
    public static final StatsOption statsOptionBrainer3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.BRAINER).COOL(1).HARD(-2).HOT(-1).SHARP(2).WEIRD(2).build(); // 2
    public static final StatsOption statsOptionBrainer4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.BRAINER).COOL(2).HARD(-1).HOT(-1).SHARP(0).WEIRD(2).build(); // 2

    public static final StatsOption statsOptionChopper1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.CHOPPER).COOL(1).HARD(2).HOT(-1).SHARP(1).WEIRD(0).build(); // 3
    public static final StatsOption statsOptionChopper2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.CHOPPER).COOL(1).HARD(2).HOT(1).SHARP(0).WEIRD(1).build(); // 4
    public static final StatsOption statsOptionChopper3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.CHOPPER).COOL(1).HARD(2).HOT(0).SHARP(1).WEIRD(1).build(); // 5
    public static final StatsOption statsOptionChopper4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.CHOPPER).COOL(2).HARD(2).HOT(-1).SHARP(0).WEIRD(1).build(); // 4

    public static final StatsOption statsOptionDriver1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.DRIVER).COOL(2).HARD(-1).HOT(1).SHARP(1).WEIRD(0).build(); // 3
    public static final StatsOption statsOptionDriver2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.DRIVER).COOL(2).HARD(0).HOT(1).SHARP(1).WEIRD(-1).build(); // 3
    public static final StatsOption statsOptionDriver3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.DRIVER).COOL(2).HARD(1).HOT(-1).SHARP(0).WEIRD(1).build(); // 3
    public static final StatsOption statsOptionDriver4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.DRIVER).COOL(2).HARD(-2).HOT(0).SHARP(2).WEIRD(1).build(); // 3

    public static final StatsOption statsOptionGunlugger1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.GUNLUGGER).COOL(1).HARD(2).HOT(-1).SHARP(1).WEIRD(0).build(); // 3
    public static final StatsOption statsOptionGunlugger2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.GUNLUGGER).COOL(-1).HARD(2).HOT(-2).SHARP(1).WEIRD(2).build(); // 2
    public static final StatsOption statsOptionGunlugger3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.GUNLUGGER).COOL(1).HARD(2).HOT(-2).SHARP(2).WEIRD(-1).build(); // 2
    public static final StatsOption statsOptionGunlugger4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.GUNLUGGER).COOL(2).HARD(2).HOT(-2).SHARP(0).WEIRD(0).build(); // 2

    public static final StatsOption statsOptionHardHolder1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.HARDHOLDER).COOL(-1).HARD(2).HOT(1).SHARP(1).WEIRD(0).build(); // 3
    public static final StatsOption statsOptionHardHolder2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.HARDHOLDER).COOL(1).HARD(2).HOT(1).SHARP(1).WEIRD(-2).build(); // 3
    public static final StatsOption statsOptionHardHolder3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.HARDHOLDER).COOL(-2).HARD(2).HOT(0).SHARP(2).WEIRD(0).build(); // 2
    public static final StatsOption statsOptionHardHolder4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.HARDHOLDER).COOL(0).HARD(2).HOT(1).SHARP(-1).WEIRD(1).build(); // 3

    public static final StatsOption statsOptionHocus1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.HOCUS).COOL(0).HARD(1).HOT(-1).SHARP(1).WEIRD(2).build(); // 3
    public static final StatsOption statsOptionHocus2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.HOCUS).COOL(1).HARD(-1).HOT(1).SHARP(0).WEIRD(2).build(); // 3
    public static final StatsOption statsOptionHocus3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.HOCUS).COOL(-1).HARD(1).HOT(0).SHARP(1).WEIRD(2).build(); // 3
    public static final StatsOption statsOptionHocus4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.HOCUS).COOL(1).HARD(0).HOT(1).SHARP(-1).WEIRD(2).build(); // 3

    public static final StatsOption statsOptionMaestroD1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.MAESTRO_D).COOL(1).HARD(-1).HOT(2).SHARP(0).WEIRD(1).build(); // 3
    public static final StatsOption statsOptionMaestroD2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.MAESTRO_D).COOL(0).HARD(1).HOT(2).SHARP(1).WEIRD(-1).build(); // 3
    public static final StatsOption statsOptionMaestroD3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.MAESTRO_D).COOL(-1).HARD(2).HOT(2).SHARP(0).WEIRD(-1).build(); // 2
    public static final StatsOption statsOptionMaestroD4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.MAESTRO_D).COOL(0).HARD(0).HOT(2).SHARP(1).WEIRD(0).build(); // 3

    public static final StatsOption statsOptionSavvyhead1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.SAVVYHEAD).COOL(-1).HARD(0).HOT(1).SHARP(1).WEIRD(2).build(); // 3
    public static final StatsOption statsOptionSavvyhead2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.SAVVYHEAD).COOL(0).HARD(-1).HOT(-1).SHARP(2).WEIRD(2).build(); // 2
    public static final StatsOption statsOptionSavvyhead3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.SAVVYHEAD).COOL(1).HARD(-1).HOT(0).SHARP(1).WEIRD(2).build(); // 3
    public static final StatsOption statsOptionSavvyhead4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.SAVVYHEAD).COOL(1).HARD(1).HOT(-1).SHARP(0).WEIRD(2).build(); // 3

    public static final StatsOption statsOptionSkinner1 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.SKINNER).COOL(1).HARD(-1).HOT(2).SHARP(1).WEIRD(0).build(); // 3
    public static final StatsOption statsOptionSkinner2 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.SKINNER).COOL(0).HARD(0).HOT(2).SHARP(0).WEIRD(1).build(); // 3
    public static final StatsOption statsOptionSkinner3 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.SKINNER).COOL(-1).HARD(0).HOT(2).SHARP(2).WEIRD(-1).build(); // 2
    public static final StatsOption statsOptionSkinner4 = StatsOption.builder().id(new ObjectId().toString()).playbookType(PlaybookType.SKINNER).COOL(1).HARD(1).HOT(2).SHARP(1).WEIRD(-2).build(); // 3
    
    
}
