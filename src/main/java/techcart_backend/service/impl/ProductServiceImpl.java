package techcart_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import techcart_backend.dto.ProductRequest;
import techcart_backend.dto.ProductResponse;
import techcart_backend.entity.Product;
import techcart_backend.exception.ResourceNotFoundException;
import techcart_backend.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return mapToProductResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .category(request.getCategory())
                .price(request.getPrice())
                .rating(request.getRating())
                .image(request.getImage())
                .description(request.getDescription())
                .stockQuantity(request.getStockQuantity())
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .rating(product.getRating())
                .image(product.getImage())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .build();
    }
}