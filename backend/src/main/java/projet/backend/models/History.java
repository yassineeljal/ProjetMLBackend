package projet.backend.models;

import jakarta.persistence.*;
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private People people;

    @ManyToOne
    private Serie serie;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public People getPeople() { return people; }
    public void setPeople(People people) { this.people = people; }

    public Serie getSerie() { return serie; }
    public void setSerie(Serie serie) { this.serie = serie; }
}
