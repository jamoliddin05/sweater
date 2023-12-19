package com.example.sweater.models;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@ToString(of = {"id", "username", "active", "email"})
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Введите имя пользователя")
    private String username;

    @NotBlank(message = "Введите пароль")
    private String password;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Введите email")
    private String email;

    private boolean active;

    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Message> messages;

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "channel_id")},
            inverseJoinColumns = {@JoinColumn(name = "subscriber_id")}
    )
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name = "channel_id")}
    )
    private Set<User> subscriptions = new HashSet<>();

    public User() {
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank(message = "Введите имя пользователя") String getUsername() {
        return this.username;
    }

    public @NotBlank(message = "Введите пароль") String getPassword() {
        return this.password;
    }

    public @Email(message = "Email is not correct") @NotBlank(message = "Введите email") String getEmail() {
        return this.email;
    }

    public boolean isActive() {
        return this.active;
    }

    public String getActivationCode() {
        return this.activationCode;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public Set<User> getSubscribers() {
        return this.subscribers;
    }

    public Set<User> getSubscriptions() {
        return this.subscriptions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(@NotBlank(message = "Введите имя пользователя") String username) {
        this.username = username;
    }

    public void setPassword(@NotBlank(message = "Введите пароль") String password) {
        this.password = password;
    }

    public void setEmail(@Email(message = "Email is not correct") @NotBlank(message = "Введите email") String email) {
        this.email = email;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }

    public void setSubscriptions(Set<User> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
