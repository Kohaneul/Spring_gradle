package hello.upload2.controller2;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemForm2 {
    private String itemName;
    private Long id;
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;
}
