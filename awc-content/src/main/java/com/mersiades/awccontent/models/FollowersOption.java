package com.mersiades.awccontent.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowersOption {

    // new... means it replaces default value
    // ...Change means it modifies default value

    @Id
    private String id;

    private String description;

    private int newNumberOfFollowers;

    // Ranges from -1 to 1, with -2 representing null
    private int surplusBarterChange;

    // Ranges from 0 to 1, with -1 representing null
    private int fortuneChange;

    private String surplusChange;

    @Builder.Default
    private List<String> wantChange = new ArrayList<>();
}
