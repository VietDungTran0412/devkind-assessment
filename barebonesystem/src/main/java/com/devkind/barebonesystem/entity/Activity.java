package com.devkind.barebonesystem.entity;

import com.devkind.barebonesystem.enums.ActivityType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "activity")
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "activity_type")
    @Enumerated(EnumType.STRING)
    private ActivityType type;
    @Column(name = "created_date")
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
