package com.tts.eCommerceApp1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tts.eCommerceApp1.Model.Product;
import com.tts.eCommerceApp1.Repository.ProductRepository;

@Service
public class ProductService 
{
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAll()
	{
		return productRepository.findAll();
	}
	
	public Product findById(long id)
	{
		return productRepository.findById(id);
	}
	
	public List<String> findDistinctBrands()
	{
		return productRepository.findDistinctBrands();
	}
	
	public List<String> findDistinctCategories()
	{
		return productRepository.findDistinctCategories();
	}
	
	public void save(Product product)
	{
		productRepository.save(product);
	}
	
	public void deleteById(long id)
	{
		productRepository.deleteById(id);
	}
	
	public List<Product> findByBrandOrCategory(String brand, String category)
	{
		if(category == null && brand == null)
			return productRepository.findAll();
		else if( category == null)
			return productRepository.findByBrand(brand);
		else if (brand == null)
			return productRepository.findByCategory(category);
		else
			return productRepository.findByBrandAndCategory(brand,  category);
	}
}
