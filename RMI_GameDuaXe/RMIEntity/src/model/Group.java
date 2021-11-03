/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author NONAME
 */
public class Group implements Serializable {

    private int id, idplayer;
    private String namePlayer, nameGroup;

    public Group() {
        super();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdplayer(int idplayer) {
        this.idplayer = idplayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public int getId() {
        return id;
    }

    public int getIdplayer() {
        return idplayer;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public String getNameGroup() {
        return nameGroup;
    }

}
