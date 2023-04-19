package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    @Column(name = "name", columnDefinition = "nvarchar(100) not null")
    private String name;
    @Column(name = "urlImage", columnDefinition = "nvarchar(250)")
    private String Image;

    @Column(name = "price", columnDefinition = "int not null")
    private long price;

    @ManyToOne
    private Category category;

    @Column(name = "quantity", columnDefinition = "int not null")
    private long quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<CartItem> cartItem;


}