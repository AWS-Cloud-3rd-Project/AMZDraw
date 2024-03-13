package com.amzmall.project.util.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName)throws IOException {
        File uploadFile = convert(multipartFile)
            .orElseThrow(() -> new IllegalArgumentException("Multipart -> file 실패"));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName){
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl  = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    public List<String> uploadFiles(List<MultipartFile> multipartFiles, String dirName){
        List<String> fileNameList = new ArrayList<>();

        // forEach 구문을 통해 multipartFiles 리스트로 넘어온 파일들을 순차적으로 fileNameList 에 추가
        multipartFiles.forEach(file -> {
            String fileName = dirName + "/" + file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()){
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
            fileNameList.add(fileName);
        });

        return fileNameList;
    }

    private String putS3(File uploadFile, String fileName){
        amazonS3.putObject(
            new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile){
        if(targetFile.delete()){
            log.info("파일삭제");
        }else {
            log.info("파일 삭제 실패");
        }
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException{
        File convertFile = new File(multipartFile.getOriginalFilename());
        if(convertFile.createNewFile()){
            try(FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
