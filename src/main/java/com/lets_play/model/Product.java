package com.lets_play.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;

    @NotBlank(message = "Le nom du produit est obligatoire")
    private String name;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 0, message = "Le prix ne peut pas être négatif")
    private Double price;

    private String userId;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", userId='" + userId + '\'' +
                '}';
    }
}
