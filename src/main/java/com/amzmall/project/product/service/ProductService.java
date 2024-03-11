package com.amzmall.project.product.service;

import com.amzmall.project.product.domain.dto.ProductResDto;
import com.amzmall.project.product.domain.entity.Category;
import com.amzmall.project.product.domain.entity.ES;
import com.amzmall.project.product.domain.entity.Product;
import com.amzmall.project.product.domain.dto.ProductReqDto;
import com.amzmall.project.product.repository.CategoryRepository;
import com.amzmall.project.product.repository.ProductRepository;
import com.amzmall.project.util.exception.BusinessException;
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

    @Transactional
    public ProductResDto registerProduct(ProductReqDto productReqDTO, MultipartFile photo){

        Product product = productReqDTO.toEntity();
        String categoryName = product.getCategoryName();

        Category category = categoryRepository.findByName(categoryName)
            .orElseThrow(() -> new BusinessException("해당 카테고리를 찾을 수 없습니다."));

        product.setCategory(category); // 상품에 카테고리 설정

        ES es = ES.builder()
            .id(product.getId())
            .name(product.getName())
            .build();

        try {
            productRepository.save(product);
            log.info("상품 저장 성공");
            return product.toDto();
        } catch (Exception e) {
            throw new BusinessException("상품 등록에 실패하였습니다.");
        }
    }
    //        String originImg = photo.getOriginalFilename();             //저장할 이미지 명
//        String savedFileName = product.getId()+ "_" + originImg;    //실제 디렉토리 저장 파일 명
//        String projectPath = "C:/work/AMZMall/image/";              //저장경로
//        // String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
//        File saveFile = new File(projectPath, savedFileName);
//
//        try {
//            photo.transferTo(saveFile);
//        } catch (IOException e) {
//            throw new BusinessException("이미지 저장에 실패하였습니다.");
//        }
//        product.setImg(savedFileName);
//        product.setImgpath(projectPath+savedFileName);

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
