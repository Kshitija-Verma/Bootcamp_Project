package com.kshitz.kshitz.entities.tokens;

import com.kshitz.kshitz.entities.users.User;
import org.springframework.data.mongodb.core.mapping.Document;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Document
public class ConfirmationToken {


    private String tokenId;
    private String token;


    private Date createdDate;

    private Date expiryDate;



    private User user;
public ConfirmationToken(){

}
    public ConfirmationToken(User user) {

        this.user = user;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        createdDate = new Date(calendar.getTime().getTime());
        calendar.add(Calendar.MINUTE,10);
        expiryDate = new Date(calendar.getTime().getTime());
        token = UUID.randomUUID().toString();
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
