package projet.backend.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Materiel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double weight;
    private String material_name;
    private Date location_date;
    private boolean isRented;
    private String category;
    @ManyToOne
    private People people;

    public Materiel() {

    }

}
