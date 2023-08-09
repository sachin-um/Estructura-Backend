package com.Estructura.API.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, MultipartFile multipartFile,String fileName) throws IOException {
        Path uploadPath= Paths.get(uploadDir);
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream=multipartFile.getInputStream()){
            Path filePath=uploadPath.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());

            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("Could not save uploaded file"+ fileName);
        }
    }
    public static String generateFileName(String originalFilename) {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        return UUID.randomUUID().toString() + "." + extension;
    }

    public static void uploadImages(String uploadDir, MultipartFile mainImage, String mainImageName2, List<MultipartFile> extraImages, String extraImage1Name, String extraImage2Name, String extraImage3Name) throws IOException {
        int count=0;
        if (mainImage !=null){
            FileUploadUtil.saveFile(uploadDir, mainImage, mainImageName2);
        }
        if (extraImages !=null) {
            for (MultipartFile file : extraImages) {
                if (!file.isEmpty()) {
                    if (count == 0) {
                        String fileName = StringUtils.cleanPath(extraImage1Name);
                        FileUploadUtil.saveFile(uploadDir, file, fileName);
                    }
                    if (count == 1) {
                        String fileName = StringUtils.cleanPath(extraImage2Name);
                        FileUploadUtil.saveFile(uploadDir, file, fileName);
                    }
                    if (count == 2) {
                        String fileName = StringUtils.cleanPath(extraImage3Name);
                        FileUploadUtil.saveFile(uploadDir, file, fileName);
                    }
                    count++;

                }
            }
        }
    }
}
