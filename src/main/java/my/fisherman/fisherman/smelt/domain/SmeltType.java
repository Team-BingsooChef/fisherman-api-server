package my.fisherman.fisherman.smelt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SmeltType {

    @Column(name = "smelt_type_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "smelt_type_name", nullable = false)
    private String name;

    @Column(name = "smelt_image", nullable = false)
    private String image;

    @Column(name = "smelt_ice_image", nullable = false)
    private String iceImage;

    @Column(nullable = false)
    private Integer probability;
}
