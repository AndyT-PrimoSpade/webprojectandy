package org.generation.webproject.repository.entity;


import org.generation.webproject.controller.dto.ItemDTO;

import javax.persistence.*;

@Entity
public class Item {


    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String name;
    private String description;
    private String imageUrl;
    private String style;
    private double price;



    public Item() {}

    public Item(ItemDTO itemDTO) {
        this.name = itemDTO.getName();
        this.description = itemDTO.getDescription();
        this.imageUrl = itemDTO.getImageUrl();
        this.style = itemDTO.getStyle();
        this.price = itemDTO.getPrice();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl( String imageUrl )
    {
        this.imageUrl = imageUrl;
    }

    public String getStyle()
    {
        return style;
    }

    public void setStyle( String style )
    {
        this.style = style;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice( double price )
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "Item{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", imageUrl='"
                + imageUrl + '\'' + ",style='" + style + '\'' + ", price='" + price + '}';
    }

}
