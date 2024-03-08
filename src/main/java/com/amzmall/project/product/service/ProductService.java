package com.amzmall.project.product.service;

import com.amzmall.project.product.domain.entity.ES;
import com.amzmall.project.product.domain.entity.Product;
import com.amzmall.project.product.domain.dto.ProductDto;
import com.amzmall.project.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class ProductService {


    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }


    //상품 등록
    @Transactional
    public void registerProduct(ProductDto productDTO, MultipartFile photo){
        //debug
//        System.out.println(productDTO.toString());
//        System.out.println(productDTO.toEntity().toString());

        Product product = productDTO.toEntity();
        ES es = ES.builder()
                .id(product.getProductId())
                .name(product.getName())
                .build();

        //저장할 이미지 명
        String originImg = photo.getOriginalFilename();
        //실제 디렉토리 저장 파일 명
        String savedFileName = product.getProductId()+ "_" + originImg;

        //저장경로
        String projectPath = "C:/work/AMZMall/image/";
//        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
        File saveFile = new File(projectPath, savedFileName);

        try {
            photo.transferTo(saveFile);
        } catch (IOException e) {
            //이미지 저장 실패
            e.printStackTrace();
        }
        product.setImg(savedFileName);
        product.setImgpath(projectPath+savedFileName);


        try {
            // 성공적으로 파일 저장 후 데이터베이스에 저장
            productRepository.save(product);
        } catch (Exception e) {
            // 상품 저장 실패
            e.printStackTrace();
            throw new RuntimeException("상품 저장에 실패하였습니다.", e);
            //실패 후 대처 로직 추가필요
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
