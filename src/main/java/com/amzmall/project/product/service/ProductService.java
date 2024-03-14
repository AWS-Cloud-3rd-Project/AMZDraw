package com.amzmall.project.product.service;

import com.amzmall.project.product.domain.dto.ProductResDto;
import com.amzmall.project.product.domain.entity.Category;
import com.amzmall.project.product.domain.entity.Product;
import com.amzmall.project.product.domain.dto.ProductReqDto;
import com.amzmall.project.product.repository.CategoryRepository;
import com.amzmall.project.product.repository.ProductRepository;
import com.amzmall.project.util.exception.BusinessException;
import com.amzmall.project.util.service.S3UploadService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public ProductResDto registerProduct(ProductReqDto productReqDTO, MultipartFile thumbnail, List<MultipartFile> photos){
        Product product = productReqDTO.toEntity();
        String categoryName = product.getCategoryName();

        try {
            // 아마존 S3에 이미지 업로드
            String thumbnailPath = s3UploadService.upload(thumbnail, "photos/"+productReqDTO.getName());
            product.setThumbnail(thumbnailPath); // 대표 이미지 S3 URL 설정
            List<String> imgPath = s3UploadService.uploadFiles(photos, "photos/"+productReqDTO.getName());
            for (int i = 0; i < photos.size(); i++) {
                if (i == 0)
                    product.setImgPath1(imgPath.get(i));
                else if (i == 1)
                    product.setImgPath2(imgPath.get(i));
                else if (i == 2)
                    product.setImgPath3(imgPath.get(i));
            }
            // 상품 카테고리 설정
            Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new BusinessException("해당 카테고리를 찾을 수 없습니다."));
            product.setImg(thumbnail.getOriginalFilename());
            product.setCategory(category); // 상품에 카테고리 설정
            productRepository.save(product);
            log.info("상품 저장 성공");
            return product.toDto();
        } catch (IOException e) {
            // S3 업로드 실패 시 예외 처리
            throw new BusinessException("상품 이미지 업로드에 실패하였습니다.");}
//        } catch (Exception e) {
//                throw new BusinessException("상품 등록에 실패하였습니다.");
//        }

    }

    @Transactional
    public void updateProduct(int productId, Product newProduct) {

        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = optionalProduct
            .orElseThrow(() -> new BusinessException("해당 상품을 찾을 수 없습니다."));

        if (!Objects.equals(product, newProduct)) {
            product.setBrand(newProduct.getBrand());
            product.setProductCode(newProduct.getProductCode());
            product.setName(newProduct.getName());
            // 다른 필드 추가
            productRepository.save(product);
        } else {
            throw new BusinessException("수정할 내용이 없습니다.");
        }
    }

    @Transactional
    public void deleteProduct(int productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = optionalProduct
            .orElseThrow(() -> new BusinessException("해당 상품을 찾을 수 없습니다."));
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public ProductResDto getOneProduct(int productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException("해당 상품을 찾을 수 없습니다."));
        return product.toDto();
    }

    @Transactional(readOnly = true)
    public List<ProductResDto> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest)
            .stream().map(Product::toDto)
            .collect(Collectors.toList());
    }
}
