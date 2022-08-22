package hello.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UploadFile {
    private String uploadFileName;  //고객이 관리하는 파일명
    private String storeFileName;   //서버에서 관리하는 파일명 (파일이름이 충돌날 경우가 있으므로, uuid 로 겹치지 않게 생성예정)

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
