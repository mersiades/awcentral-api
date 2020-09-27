package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Stats;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "stats")
public class Stat extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column
    private Stats name;

    @Column
    private int value;

    @ManyToOne
    @JoinColumn(name = "stat_option_id")
    private StatsOption statsOption;

}
