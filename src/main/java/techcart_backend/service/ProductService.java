package techcart_backend.service;

import techcart_backend.dto.ProductRequest;
import techcart_backend.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest request);
}