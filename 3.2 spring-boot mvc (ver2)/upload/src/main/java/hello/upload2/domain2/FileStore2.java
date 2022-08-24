package hello.upload2.domain2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileStore2 {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName){
        return fileDir+fileName;
    }

    public UploadFile2 storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile2(originalFilename,storeFileName);
    }

    private String createStoreFileName(String originalFileName){
        String uuid = UUID.randomUUID().toString();
        return uuid+"."+originalFileName.split("[.]")[1];
    }


    public List<UploadFile2> storeFiles(List<MultipartFile>multipartFiles) throws IOException {
        List<UploadFile2> uploadFiles = new ArrayList<>();
        if(!multipartFiles.isEmpty()){
            for (MultipartFile multipartFile : multipartFiles) {
                uploadFiles.add(storeFile(multipartFile));

            }
        }
        return uploadFiles;
    }



}
