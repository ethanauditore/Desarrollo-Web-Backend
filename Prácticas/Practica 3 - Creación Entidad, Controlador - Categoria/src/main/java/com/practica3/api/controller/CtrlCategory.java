package com.practica3.api.controller;

import com.practica3.api.entity.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        Category category1 = new Category();
        category1.setCategory_id(1);
        category1.setCategory("Electrónica");
        category1.setStatus(1);

        Category category2 = new Category();
        category2.setCategory_id(2);
        category2.setCategory("Línea Blanca");
        category2.setStatus(1);

        List<Category> categories = new ArrayList<Category>();
        categories.add(category1);
        categories.add(category2);
        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<Category> readCategory(@PathVariable int category_id) {
        Category category1 = new Category();
        category1.setCategory_id(1);
        category1.setCategory("Abarrotes");
        category1.setStatus(1);

        return new ResponseEntity<>(category1, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult) {
        String message = "";
        if (bindingResult.hasErrors()) {
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        message = "category created";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<String> updateCategory(@PathVariable int category_id, @Valid @RequestBody Category category, BindingResult bindingResult) {
        String message = "";
        if (bindingResult.hasErrors()) {
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        message = "category updated";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int category_id) {
        String message = "";

        message = "category removed";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
