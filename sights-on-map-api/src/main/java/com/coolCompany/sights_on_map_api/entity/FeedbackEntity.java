package com.coolCompany.sights_on_map_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedback")
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sight_id", nullable = false, referencedColumnName = "id")
    private SightEntity sight;

    @Column(nullable = false)
    private String username;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private int estimation; // from 1 to 5

    // getters and setters
}
