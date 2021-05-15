package com.mersiades.awccontent.content;

import com.mersiades.awccontent.models.ThreatMapCreator;
import org.bson.types.ObjectId;

import java.util.List;

public class ThreatMapCreatorContent {

    public static final ThreatMapCreator threatMapCreator = ThreatMapCreator.builder()
            .id(new ObjectId().toString())
            .names(List.of(
                    "Tum Tum", "Gnarly", "Fleece", "White", "Lala", "Bill",
                    "Crine", "Mercer", "Preen", "Ik", " Shan", "Isle", "Ula", "Joe's Girl", "Dremmer",
                    "Balls", "Amy", "Rufe", "Jackabacka", "Ba", "Mice", "Dog head", "Hugo", "Roark",
                    "Monk", "Pierre", "Norvell", "H", "Omie Wise", "Corbett", "Jeanette", "Rum", "Peppering",
                    "Brain", "Matilda", "Rothschild", "Wisher", "Partridge", "Brace Win", "Bar", "Krin",
                    "Parcher", "Millions", "Grome", "Foster", "Mill", "Dustwich", "Newton", "Tao", "Missed",
                    "III", "Princy", "East Harrow", "Kettle", "Putrid", "Last", "Twice", "Clarion", "Abondo",
                    "Mimi", "Fianelly", "Pellet", "Li", "Harridan", "Rice", "Do", "Winkle", "Fuse", "Visage"
            ))
            .resources(List.of(
                  "meat", "salt", "grain", "fresh foods", "staple foods", "preserved foods", "meat (don't ask)",
                  "drinking water", "hot water", "shelter", "liberty", "leisure",   "fuel", "heat", "security",
                    "time", "health", "medical supplies", "information", "status", "specialized goods", "luxury goods",
                    "gasoline", "weaponry", "labor", "skilled labor", "acclaim", "loyalty", "blood kin",
                    "strategic position", "drugs", "work animals", "livestock", "know-how", "walls", "living space",
                    "storage space", "machinery", "connections", "access", "raw materials", "books"
            ))
            .build();
}
