package com.amzmall.project.product.service;

import com.amzmall.project.product.domain.entity.Category;
import com.amzmall.project.product.domain.entity.ES;
import com.amzmall.project.product.domain.entity.Product;
import com.amzmall.project.product.domain.dto.ProductDto;
import com.amzmall.project.product.repository.CategoryRepository;
import com.amzmall.project.product.repository.ProductRepository;
import com.amzmall.project.util.advice.ExMessage;
import com.amzmall.project.util.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void registerProduct(ProductDto productDTO, MultipartFile photo){

        Product product = productDTO.toEntity();
        String categoryName = product.getCategoryName();

        Category category = categoryRepository.findByName(categoryName)
            .orElseThrow(() -> new BusinessException("해당 카테고리를 찾을 수 없습니다."));

        product.setCategory(category); // 상품에 카테고리 설정

        ES es = ES.builder()
            .id(product.getId())
            .name(product.getName())
            .build();

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

        try {
            productRepository.save(product);
            log.info("상품 저장 성공");
        } catch (Exception e) {
            throw new BusinessException("상품 등록에 실패하였습니다.");
        }
    }


    @Transactional
    public void updateProduct(int productId, Product newProduct){

        Optional<Product> Product = productRepository.findById(productId);
        Product product = Product.get();

            // 변경된 필드만 수정
            if (!Product.equals(newProduct)) {
                productRepository.save(newProduct);
        } else {
            throw new EntityNotFoundException("수정 실패!");
        }

    }

    @Transactional
    public void deleteProduct(int productId){
        Optional<Product> Product = productRepository.findById(productId);
        Product product = Product.get();
        productRepository.delete(product);
    }

}
