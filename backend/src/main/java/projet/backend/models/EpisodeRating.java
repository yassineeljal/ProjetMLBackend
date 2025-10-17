package projet.backend.models;

import jakarta.persistence.*;

@Entity
public class EpisodeRating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) private People people;
    @ManyToOne(optional = false) private Episode episode;

    private int value;

    public Long getId() { return id; }
    public People getPeople() { return people; }
    public Episode getEpisode() { return episode; }
    public int getValue() { return value; }

    public void setId(Long id) { this.id = id; }
    public void setPeople(People people) { this.people = people; }
    public void setEpisode(Episode episode) { this.episode = episode; }
    public void setValue(int value) { this.value = value; }
}