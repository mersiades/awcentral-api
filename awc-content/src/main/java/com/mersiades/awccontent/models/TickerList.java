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
public class TickerList {

    @Id
    private String id;

    private String title;

    @Builder.Default
    private List<String> items = new ArrayList<>();
}
