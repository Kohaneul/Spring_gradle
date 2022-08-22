package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;


    public String getFullPath(String filename){
        return fileDir+filename;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        //multipartfile을 가지고 uploadfile로 반환
        if(multipartFile.isEmpty()){
            return null;
        }

        //image.png
        String originalFilename = multipartFile.getOriginalFilename();
        //서버에 저장하는 파일명
        String storeFileName = createStoreFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(originalFilename,storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        System.out.println("ext : "+ext);
        //uuid+파일 확장자
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        return originalFilename.substring(idx+1);    //확장자명
    }


    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }

        return storeFileResult;
    }




}
