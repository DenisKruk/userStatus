package com.websystique.springboot.model;

public class User {
    private long id;
    private String name;
    private String secondName;
    private String eMail;
    private int status = 1;

    public User() {
    }

    public User(long id, String name, String secondName, String eMail, int status) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.eMail = eMail;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String geteMail() {
        return eMail;
    }

    public int getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setStatus(int status) {
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

    public static int compare(Object o1, Object o2) {
        Long p1 = ((User) o1).getId();
        Long p2 = ((User) o2).getId();

        if (p1 > p2) {
            return 1;
        } else if (p1 < p2) {
            return -1;
        } else {
            return 0;
        }
    }
}

