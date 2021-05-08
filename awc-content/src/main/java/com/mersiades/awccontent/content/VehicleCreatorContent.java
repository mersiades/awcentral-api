package com.mersiades.awccontent.content;

import com.mersiades.awccontent.enums.BattleOptionType;
import com.mersiades.awccontent.enums.VehicleFrameType;
import com.mersiades.awccontent.enums.VehicleType;
import com.mersiades.awccontent.models.*;
import org.bson.types.ObjectId;

import java.util.List;

public class VehicleCreatorContent {
    public static final VehicleFrame bikeFrame = VehicleFrame.builder()
            .id(new ObjectId().toString())
            .frameType(VehicleFrameType.BIKE)
            .massive(0)
            .examples("Road bike, trail bike, low-rider")
            .battleOptionCount(1)
            .build();

    public static final VehicleFrame smallFrame = VehicleFrame.builder()
            .id(new ObjectId().toString())
            .frameType(VehicleFrameType.SMALL)
            .massive(1)
            .examples("Compact, buggy")
            .battleOptionCount(2)
            .build();

    public static final VehicleFrame mediumFrame = VehicleFrame.builder()
            .id(new ObjectId().toString())
            .frameType(VehicleFrameType.MEDIUM)
            .massive(2)
            .examples("Coupe, sedan, jeep, pickup, van, limo, 4x4, tractor")
            .battleOptionCount(2)
            .build();

    public static final VehicleFrame largeFrame = VehicleFrame.builder()
            .id(new ObjectId().toString())
            .frameType(VehicleFrameType.LARGE)
            .massive(3)
            .examples("Semi, bus, ambulance, construction/utility")
            .battleOptionCount(2)
            .build();

    public static final VehicleBattleOption battleOption1 = VehicleBattleOption.builder()
            .id(new ObjectId().toString())
            .battleOptionType(BattleOptionType.SPEED)
            .name("+1speed")
            .build();

    public static final VehicleBattleOption battleOption2 = VehicleBattleOption.builder()
            .id(new ObjectId().toString())
            .battleOptionType(BattleOptionType.HANDLING)
            .name("+1handling")
            .build();

    public static final VehicleBattleOption battleOption3 = VehicleBattleOption.builder()
            .id(new ObjectId().toString())
            .battleOptionType(BattleOptionType.MASSIVE)
            .name("+1massive")
            .build();

    public static final VehicleBattleOption battleOption4 = VehicleBattleOption.builder()
            .id(new ObjectId().toString())
            .battleOptionType(BattleOptionType.ARMOR)
            .name("+1armor")
            .build();

    public static final List<String> bikeStrengths = List.of("fast",
            "rugged",
            "aggressive",
            "tight",
            "huge",
            "responsive");
    public static final List<String> bikeLooks = List.of(
            "sleek",
            "vintage",
            "massively-chopped",
            "muscular",
            "flashy",
            "luxe",
            "roaring",
            "fat-ass");
    public static final List<String> bikeWeaknesses = List.of("slow",
            "sloppy",
            "guzzler",
            "lazy",
            "unreliable",
            "cramped",
            "loud",
            "picky",
            "rabbity");

    public static final BikeCreator bikeCreator = BikeCreator.builder()
            .id(new ObjectId().toString())
            .vehicleType(VehicleType.BIKE)
            .introInstructions("By default, your bike has speed=0, handling=0, 0-armor and the massive rating of its frame.")
            .frame(bikeFrame)
            .strengths(bikeStrengths)
            .looks(bikeLooks)
            .weaknesses(bikeWeaknesses)
            .battleOptions(List.of(battleOption1, battleOption2))
            .build();

    public static final List<String> carStrengths = List.of("fast",
            "rugged",
            "aggressive",
            "tight",
            "huge",
            "responsive",
            "off-road",
            "uncomplaining",
            "capacious",
            "workhorse",
            "easily repaired");

    public static final List<String> carLooks = List.of(
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
            "garish");

    public static final List<String> carWeaknesses = List.of("slow",
            "sloppy",
            "guzzler",
            "lazy",
            "unreliable",
            "cramped",
            "loud",
            "picky",
            "rabbity");

    public static final CarCreator carCreator = CarCreator.builder()
            .id(new ObjectId().toString())
            .vehicleType(VehicleType.CAR)
            .introInstructions("By default, your vehicle has speed=0, handling=0, 0-armor and the massive rating of its frame.")
            .frames(List.of(bikeFrame, smallFrame, mediumFrame, largeFrame))
            .strengths(carStrengths)
            .looks(carLooks)
            .weaknesses(carWeaknesses)
            .battleOptions(List.of(battleOption1, battleOption2, battleOption3, battleOption4))
            .build();

    public static final VehicleBattleOption battleOption5 = VehicleBattleOption.builder()
            .id(new ObjectId().toString())
            .battleOptionType(BattleOptionType.WEAPON)
            .name("Mounted machine guns (3-harm close/far area messy)")
            .build();

    public static final VehicleBattleOption battleOption6 = VehicleBattleOption.builder()
            .id(new ObjectId().toString())
            .battleOptionType(BattleOptionType.WEAPON)
            .name("Mounted grenade launcher (4-harm close area messy)")
            .build();

    public static final VehicleBattleOption battleOption7 = VehicleBattleOption.builder()
            .id(new ObjectId().toString())
            .battleOptionType(BattleOptionType.WEAPON)
            .name("Ram or ramming spikes (as a weapon, vehicle inflicts +1harm)")
            .build();

    public static final VehicleBattleOption battleOption8 = VehicleBattleOption.builder()
            .id(new ObjectId().toString())
            .battleOptionType(BattleOptionType.WEAPON)
            .name("Mounted 50cal mg (5-harm far area messy)")
            .build();

    public static final VehicleBattleOption battleOption9 = VehicleBattleOption.builder()
            .id(new ObjectId().toString())
            .battleOptionType(BattleOptionType.WEAPON)
            .name("Mounted boarding platform or harness (+1 to attempts to board another vehicle from this one)")
            .build();

    public static final BattleVehicleCreator battleVehicleCreator = BattleVehicleCreator.builder()
            .id(new ObjectId().toString())
            .vehicleType(VehicleType.BATTLE)
            .battleVehicleOptions(List.of(battleOption1, battleOption2, battleOption3, battleOption4, battleOption5,
                    battleOption6, battleOption7, battleOption8, battleOption9))
            .build();

    public static final VehicleCreator vehicleCreator = VehicleCreator.builder()
            .id(new ObjectId().toString())
            .carCreator(carCreator)
            .bikeCreator(bikeCreator)
            .battleVehicleCreator(battleVehicleCreator)
            .build();
}
