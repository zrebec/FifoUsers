package org.example;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class SUser {
    @Id
    private int userId;
    private String userGuid;
    private String userName;

    public int getUserId() {
        return userId;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
