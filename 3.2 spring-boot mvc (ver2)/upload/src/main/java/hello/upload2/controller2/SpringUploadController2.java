package hello.upload2.controller2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
@Slf4j
@RequestMapping("/spring")
public class SpringUploadController2 {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String upload(){
        return "upload-form";
    }


    @PostMapping("/upload")
    public String upload(@RequestParam String itemName, @RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        log.info("itemName={}",itemName);
        log.info("file={}",file.getOriginalFilename());
        log.info("request={}",request);
        if(!file.isEmpty()){
            String fullPath = fileDir + file.getOriginalFilename();
            log.info("파일저장={}",fullPath);
            file.transferTo(new File(fullPath));
        }
        return "upload-form";
    }


}
