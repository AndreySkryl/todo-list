package database.todoList.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Component
public class User implements Serializable {
    String guid;
    String login;
    String lastName;
    String firstName;
    String password;
    String eMail;
    Timestamp createTime;
    Timestamp updateTime;

    public User() {}

    public User(String login, String password, String eMail) {
        this.login = login;
        this.password = password;
        this.eMail = eMail;
    }

    public User(String login, String lastName, String firstName, String password, String eMail) {
        this.login = login;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.eMail = eMail;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return String.format(
            "{\"User\": [\"guid\": \"%s\", \"login\": \"%s\", " +
            "\"lastName\": \"%s\", \"firstName\": \"%s\", " +
            "\"password\": \"%s\", \"eMail\": \"%s\", " +
            "\"createTime\": \"" + simpleDateFormat.format(createTime) + "\", " +
            "\"updateTime\": \"" + simpleDateFormat.format(updateTime) + "\"]}",
            guid, login, lastName, firstName, password, eMail, createTime, updateTime);
    }
}
