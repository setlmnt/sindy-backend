package com.ifba.educampo.domain.entity.associate;

import com.ifba.educampo.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work_records")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class WorkRecord extends BaseEntity<Long> {
    @Column(nullable = false)
    private Long number;

    @Column(nullable = false)
    private String series;

    public void update(WorkRecord workRecord) {
        if (workRecord.getNumber() != null) setNumber(workRecord.getNumber());
        if (workRecord.getSeries() != null) setSeries(workRecord.getSeries());
    }

    @Override
    public String toString() {
        return "WorkRecord{" +
                ", number=" + number +
                ", series='" + series + '\'' +
                '}';
    }
}
