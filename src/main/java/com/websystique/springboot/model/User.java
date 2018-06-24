package com.websystique.springboot.model;

public class User {
    private long id;
    private String name;
    private String secondName;
    private String eMail;
    private int status=1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        secondName = secondName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User() {
    }

    public User(String name, String secondName, String eMail) {
        this.name = name;
        this.secondName = secondName;
        this.eMail = eMail;
    }

    public User(long id, String name, String secondName, String eMail, int status) {

        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.eMail = eMail;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (secondName != null ? !secondName.equals(user.secondName) : user.secondName != null) return false;
        return eMail != null ? eMail.equals(user.eMail) : user.eMail == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + eMail.hashCode();
        return result;
    }
}
