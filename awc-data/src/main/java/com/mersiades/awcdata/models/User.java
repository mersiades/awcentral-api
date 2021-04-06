package com.mersiades.awcdata.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String displayName;

    private String email;

    // one to many
    @Builder.Default
    private List<GameRole> gameRoles = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User [");
        if (this.getId() != null) {
            sb.append("id= ").append(this.getId());
        }

        if (this.gameRoles != null) {
            sb.append(", gameRoles size= ").append(this.gameRoles.size());
        }

        sb.append("]");

        return sb.toString();
    }

}
