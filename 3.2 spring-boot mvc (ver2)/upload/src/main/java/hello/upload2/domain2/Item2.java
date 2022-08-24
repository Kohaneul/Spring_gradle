package hello.upload2.domain2;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class Item2 {
    private Long id;
    private String itemName;
    private UploadFile2 attachFile;
    private List<UploadFile2> imageFiles;

}
