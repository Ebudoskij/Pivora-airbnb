package org.ebudoskyi.houserent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "property_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    byte[] image;

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id", nullable = false)
    private Property property;
}
