package techcart_backend.service;

import techcart_backend.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    List<Product> getProductsByCategory(String category);
    Product createProduct(Product product);
}