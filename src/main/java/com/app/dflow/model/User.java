package com.app.dflow.model;

import com.app.dflow.constants.AuthProvider;
import com.app.dflow.constants.UserType;
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
@Table(name="USERS")
@AllArgsConstructor
@NoArgsConstructor
@EnableAutoConfiguration
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Room> rooms;

    private UserType type;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthProvider auth_provider;
}
