package com.mersiades.awcdata.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "stats_options")
public class StatsOption extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statsOption")
    private Set<Stat> stats = new HashSet<>();
}
