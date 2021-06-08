package com.gmail.lesiiayurchenko.model.entity;

import java.util.Objects;

public class Account {
    private int id;
    private String login;
    private String password;
    private String email;
    public enum Role {
        ADMIN, MANAGER, CUSTOMER, GUEST
    }
    private Role role;
    private boolean blocked;

    public Account(int id, String login, String password, String email, Role role, boolean blocked) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.blocked = blocked;
    }

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && blocked == account.blocked && login.equals(account.login)
                && password.equals(account.password) && email.equals(account.email) && role == account.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, role, blocked);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", blocked=" + blocked +
                '}';
    }
}
