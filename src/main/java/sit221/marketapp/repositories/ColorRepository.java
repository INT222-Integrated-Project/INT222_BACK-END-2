package sit221.marketapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sit221.marketapp.models.Color;

public interface ColorRepository extends JpaRepository<Color, Integer> {

}
