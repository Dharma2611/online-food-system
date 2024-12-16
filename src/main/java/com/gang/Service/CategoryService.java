package com.gang.Service;

import java.util.List;

import com.gang.Entity.Category;

public interface CategoryService {
	
	
	public Category createCategory(String name,Long userId) throws Exception;
	public List<Category>  findCategoryByRestaurantsId(Long id)throws Exception;
	
	public Category findCategoryById(Long id)throws Exception;

}
