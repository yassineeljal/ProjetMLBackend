package projet.backend.models;

import jakarta.persistence.*;

@Entity
public class Episode {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Serie serie;

    private int seasonNumber;
    private int episodeNumber;
    private String title;

    public Long getId() { return id; }
    public Serie getSerie() { return serie; }
    public int getSeasonNumber() { return seasonNumber; }
    public int getEpisodeNumber() { return episodeNumber; }
    public String getTitle() { return title; }

    public void setId(Long id) { this.id = id; }
    public void setSerie(Serie serie) { this.serie = serie; }
    public void setSeasonNumber(int seasonNumber) { this.seasonNumber = seasonNumber; }
    public void setEpisodeNumber(int episodeNumber) { this.episodeNumber = episodeNumber; }
    public void setTitle(String title) { this.title = title; }
}
