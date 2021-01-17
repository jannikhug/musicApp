package ch.zli.jh.mcapp.model;

import java.io.Serializable;

public class Profile implements Serializable {

    private String fullName;
    private String username;
    private String favArtist;
    private String favAlbum;

    public Profile(String fullName, String username, String favArtist, String favAlbum) {
        this.fullName = fullName;
        this.username = username;
        this.favArtist = favArtist;
        this.favAlbum = favAlbum;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFavArtist() {
        return favArtist;
    }

    public void setFavArtist(String favArtist) {
        this.favArtist = favArtist;
    }

    public String getFavAlbum() {
        return favAlbum;
    }

    public void setFavAlbum(String favAlbum) {
        this.favAlbum = favAlbum;
    }
}
