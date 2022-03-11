package org.generation.webproject.controller;

import org.generation.component.FileUploadUtil;
import org.generation.webproject.controller.dto.ItemDTO;
import org.generation.webproject.repository.entity.*;
import org.generation.webproject.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/item")

public class ItemController {

    @Value("${image.folder}")
    private String imageFolder;


    // Mapping the API call from the client to the respective method (endpoint)
    // handle the HTTP request call (GET, POST, DELETE, PUT)

    private final ItemService itemService;

    public ItemController( @Autowired ItemService itemService )
    {
        this.itemService = itemService;
    }
    // GET HTTP request of all item
    @CrossOrigin
    @GetMapping("/all")
    public Iterable<Item> getItems()
    {
        return itemService.all();
    }
    // GET HTTP request with item ID to the Controller
    @CrossOrigin
    @GetMapping("/{id}")
    public Item findItemById( @PathVariable Integer id )
    {
        return itemService.findById( id );
    }
    // DELETE HTTP requests with the item ID
    @CrossOrigin
    @DeleteMapping( "/{id}" )
    public void delete( @PathVariable Integer id )
    {
        itemService.delete( id );
    }
    // send a POST HTTP requests with all the item data (name, description, image filename, style, price, image object)
    @CrossOrigin
    @PostMapping("/add")
    public void save(  @RequestParam(name="name", required = true) String name,
                       @RequestParam(name="description", required = true) String description,
                       @RequestParam(name="imageUrl", required = true) String imageUrl,
                       @RequestParam(name="style", required = true) String style,
                       @RequestParam(name="price", required = true) double price,
                       @RequestParam("imagefile") MultipartFile multipartFile) throws IOException {

        // Part 1 - provide the ability to save the image file into the directory in the server
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        FileUploadUtil.saveFile(imageFolder, fileName, multipartFile);

        // Part 2 - other data( name, description, ...) store into the database productimages/images/t-shirt_new.jpeg
        String fullPath = imageFolder + '/' + imageUrl;

        // Create an instance object of the ItemDTO (Data Transfer Object) to store all the data that is sent from the Client
        ItemDTO itemDto = new ItemDTO(name, description, fullPath, style, price);
        itemService.save(new Item(itemDto));
    }




}
