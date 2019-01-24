package edu.unidhaka.cse.cse2216.keephealthy;

public class Food {
    private String artistId;
    private String artistName;
    private String artistGenre;

    public Food(){
    }

    public Food(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
}