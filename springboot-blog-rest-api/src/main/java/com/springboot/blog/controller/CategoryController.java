package com.springboot.blog.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "CRUD REST APIs for Category Resource")
public class CategoryController {
	
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	//build add category rest-api
	@Operation(
			summary = "Add Category REST API",
			description = "Add Category REST API is used for adding post category."
			)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201, Created"
			)
	@PostMapping("/addcategory")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto savedCategory = categoryService.addCategory(categoryDto);
		return new ResponseEntity<>(savedCategory,HttpStatus.CREATED);
	}
	
	//build get category rest-api
	@Operation(
			summary = "Get Category By Category Id REST API",
			description = "Get Category REST API is used for retrieving the category by id."
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200, Fetched"
			)
	@GetMapping("/getcategory/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		return ResponseEntity.ok(categoryDto);
	}
	
	//build get all categories rest-api
	@Operation(
			summary = "Get All Category REST API",
			description = "Get All Category REST API is used for retrieving all the categories."
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200, Fetched"
			)
	@GetMapping("/getallcategories")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	//build update category rest-api
	@Operation(
			summary = "Update Category REST API",
			description = "Update Category REST API is used for updating the categories."
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200, Updated"
			)
	@PutMapping("/updatecategory/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
			@PathVariable("id") Long categoryId){
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
	}
	
	//build delete category rest-api
	@Operation(
			summary = "Delete Category REST API",
			description = "Delete Category REST API deletes the category by provided category id."
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200, Deleted"
			)
	@DeleteMapping("/deletecategory/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted successfully!.");
	}
}