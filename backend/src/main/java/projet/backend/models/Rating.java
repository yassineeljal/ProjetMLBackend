package projet.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "episode_id"}),
        @UniqueConstraint(columnNames = {"user_id", "series_id"})
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score; // note de 1 à 5

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long episodeId; // null si c'est une note de série
    private Long seriesId;  // null si c'est une note d'épisode

    public Rating() {}

    public Rating(User user, Integer score, Long episodeId, Long seriesId) {
        this.user = user;
        this.score = score;
        this.episodeId = episodeId;
        this.seriesId = seriesId;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Long getEpisodeId() { return episodeId; }
    public void setEpisodeId(Long episodeId) { this.episodeId = episodeId; }
    public Long getSeriesId() { return seriesId; }
    public void setSeriesId(Long seriesId) { this.seriesId = seriesId; }
}
