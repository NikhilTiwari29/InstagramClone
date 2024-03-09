package com.example.social.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String caption;
    private String image;
    private String video;

    @ManyToMany
    private List<User> likedPost = new ArrayList<>();

    @ManyToOne
    private User user;

    private LocalDateTime createdAt;
}
