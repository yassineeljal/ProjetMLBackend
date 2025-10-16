package projet.backend.models;

import jakarta.persistence.*;

@Entity
public class SeriesRating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) private People people;
    @ManyToOne(optional = false) private Serie serie;

    // 1..5
    private int value;

    public Long getId() { return id; }
    public People getPeople() { return people; }
    public Serie getSerie() { return serie; }
    public int getValue() { return value; }

    public void setId(Long id) { this.id = id; }
    public void setPeople(People people) { this.people = people; }
    public void setSerie(Serie serie) { this.serie = serie; }
    public void setValue(int value) { this.value = value; }
}
