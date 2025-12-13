package com.example.devSync.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;


    //Many projects belong to one User
    //One project -> One owner (creator)
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;  //FK (user.id)

    //Project Memebers(Many to many)
    @ManyToMany
    @JoinTable(
        name="project_members",
        joinColumns = @JoinColumn(name="project_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    private Set<User> members = new HashSet<>();

    //One project can have many tasks
    @OneToMany(mappedBy = "project" , cascade = CascadeType.ALL) 
    private List<Task>tasks;
    

    @PrePersist
    public void onCreate(){
        this.createdAt=LocalDateTime.now();
    }
}
