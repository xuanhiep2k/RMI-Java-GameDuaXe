package model;

import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 20210811010L;
    private int id_player;
    private String username;
    private String password;
    private String fullname;
    private String gender;
    private int core;
    private String email;
    private String position;

    public Player() {
        super();
    }

    public Player(int id_player, String username, String password, String fullname, String gender, String email, String position, int core) {
        this.id_player = id_player;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.position = position;
        this.core = core;
    }

    public int getIdplayer() {
        return id_player;
    }

    public void setCore(int core) {
        this.core = core;
    }

    public int getCore() {
        return core;
    }

    public void setIdplayer(int id_player) {
        this.id_player = id_player;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
