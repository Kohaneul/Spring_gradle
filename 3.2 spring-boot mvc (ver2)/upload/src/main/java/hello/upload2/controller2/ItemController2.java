package hello.upload2.controller2;

import hello.upload.controller.ItemForm;
import hello.upload.domain.Item;
import hello.upload2.domain2.FileStore2;
import hello.upload2.domain2.Item2;
import hello.upload2.domain2.ItemRepository2;
import hello.upload2.domain2.UploadFile2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.UriUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/items22")
public class ItemController2 {
    private final ItemRepository2 itemRepository2;
    private final FileStore2 fileStore;

    @GetMapping("/new")
    public String itemUpload(@ModelAttribute ItemForm2 form){
        return "item-form";
    }
    //itemName(String), attachFile(multiful),imageFiles(multi)

    @PostMapping("/new")
    public String itemView(@ModelAttribute ItemForm2 form, RedirectAttributes redirectAttributes) throws IOException {
        UploadFile2 attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile2> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        Item2 item = new Item2();

        item.setItemName(form.getItemName());
        item.setAttachFile(attachFile);
        item.setImageFiles(storeImageFiles);
        itemRepository2.save(item);
        redirectAttributes.addAttribute("itemId",item.getId());
        return "redirect:items/{itemId}";
    }

    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource itemName(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:/"+fileStore.getFullPath(fileName));
    }


    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item2 item = itemRepository2.findById(itemId);
        String uploadFileName = item.getAttachFile().getUploadFileName();
        String storeFileName = item.getAttachFile().getStoreFileName();
        UrlResource resource = new UrlResource("file:/" + fileStore.getFullPath(uploadFileName));
        log.info("uploadFileName={}",uploadFileName);
        String encodeFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition="attachment; filename=\""+encodeFileName+"\"";
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);
    }


}
