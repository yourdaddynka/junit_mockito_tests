package edu.school21.models;

import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private boolean successStatus;

    public User(int id, String login, String password, boolean successStatus) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.successStatus = successStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSuccessStatus(boolean successStatus) {
        this.successStatus = successStatus;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSuccessStatus() {
        return successStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && successStatus == user.successStatus && login.equals(user.login) && password.equals(user.password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", successStatus=" + successStatus +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, successStatus);
    }
}
