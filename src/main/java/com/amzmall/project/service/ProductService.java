package com.amzmall.project.service;

import com.amzmall.project.model.Product;
import com.amzmall.project.model.ProductDTO;
import com.amzmall.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ProductService {


    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    public void registerProduct(ProductDTO productDTO, MultipartFile photo){
        System.out.println(productDTO.toString());
        System.out.println(productDTO.toEntity().toString());

        Product product = productDTO.toEntity();

        //저장할 파일명
        String originImg = photo.getOriginalFilename();
        String savedFileName = UUID.randomUUID()+ "_" + originImg;

        //저장경로
        String projectPath = "C:/work/AMZMall/image/";
//        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
        File saveFile = new File(projectPath, savedFileName);

        try {
            photo.transferTo(saveFile);
        } catch (IOException e) {
            //저장실패
            e.printStackTrace();
        }
        product.setImg(savedFileName);
        product.setImgpath(projectPath+savedFileName);

        productRepository.save(product);





    }



}
