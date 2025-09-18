package projet.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.backend.repositories.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;


}
