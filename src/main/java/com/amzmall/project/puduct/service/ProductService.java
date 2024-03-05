package com.amzmall.project.puduct.service;

import com.amzmall.project.puduct.domain.entity.ES;
import com.amzmall.project.puduct.domain.entity.Product;
import com.amzmall.project.puduct.domain.dto.ProductDTO;
import com.amzmall.project.puduct.repository.ProductRepository;
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
    private S3Uploader s3Uploader;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }


    //상품 등록
    @Transactional
    public void registerProduct(ProductDTO productDTO, MultipartFile photo){
        //debug
//        System.out.println(productDTO.toString());
//        System.out.println(productDTO.toEntity().toString());

        Product product = productDTO.toEntity();
        //임시 엘라스틱서치용 데이터저장폼
        ES es = ES.builder()
                .id(product.getProductId())
                .name(product.getName())
                .build();

//        //저장할 이미지 명
//        String originImg = photo.getOriginalFilename();
//        //실제 디렉토리 저장 파일 명
//        String savedFileName = product.getProductId()+ "_" + originImg;
        System.out.println("프로덕트 저장 확인용이에용");
        if(!photo.isEmpty()){

            try {
                String storedFileName = s3Uploader.upload(photo, "photos");
                product.setImgpath(storedFileName);
            } catch (IOException e) {
                // 예외를 처리하거나 로깅합니다.
                e.printStackTrace(); // 또는 SLF4J 또는 log4j와 같은 로깅 프레임워크를 사용합니다.
            }
            //사진 이미지 url = Imgpath
        }

//        //저장경로
//        String projectPath = "C:/work/AMZMall/image/";
////        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
//        File saveFile = new File(projectPath, savedFileName);
//
//        try {
//            photo.transferTo(saveFile);
//        } catch (IOException e) {
//            //이미지 저장 실패
//            e.printStackTrace();
//        }
//        product.setImg(savedFileName);
//        product.setImgpath(projectPath+savedFileName); //ima



        //저장
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
    public void updateProduct(String productId, Product newProduct){

        Optional<Product> Product = productRepository.findById(productId);

        Product product = Product.get();


            // 변경된 필드만 수정
            if (!Product.equals(newProduct)) {
                productRepository.save(newProduct);

            // productRepository.save(product); // save 메소드를 호출하지 않아도 변경사항이 트랜잭션 종료 시에 자동으로 반영됨
        } else {
            throw new EntityNotFoundException("수정 실패!");
        }

    }


    @Transactional
    public void deleteProduct(String productId){
        Optional<Product> Product = productRepository.findById(productId);
        Product product = Product.get();
        productRepository.delete(product);
    }




}
