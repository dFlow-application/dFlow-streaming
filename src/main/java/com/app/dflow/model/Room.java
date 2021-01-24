package com.app.dflow.model;

import com.app.dflow.constants.RoomStatus;
import com.app.dflow.constants.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name="ROOMS")
@AllArgsConstructor
@NoArgsConstructor
@EnableAutoConfiguration
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String roomIdentifier;

    @NotNull
    private RoomStatus status;

    @NotNull
    private RoomType type;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> subscribers;

    @ManyToOne
    private User owner;

    private int maxCapacity;
    private int actualSize;
}
