package com.parking.parkinglot.ejb;


import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Stateful
@SessionScoped
public class InvoiceBean implements Serializable {
    Set<Long> usersIds=new HashSet<>();
    public Set<Long> getUsersIds() {
        return usersIds;
    }
    public void setUsersIds(Set<Long> usersIds) {
        this.usersIds = usersIds;
    }
}
