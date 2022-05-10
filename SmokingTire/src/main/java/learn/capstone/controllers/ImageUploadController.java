package learn.capstone.controllers;

import learn.capstone.storage.StorageFileNotFoundException;
import learn.capstone.storage.StorageService;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@CrossOrigin(origins = "http://localHost:3000")
@RequestMapping("/api/image")
public class ImageUploadController {

    private final StorageService storageService;

    @Autowired
    public ImageUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/{modelId}")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, @PathVariable Integer modelId){
        storageService.store(file, modelId);
        redirectAttributes.addFlashAttribute("message", "you successfully uploaded " + file.getOriginalFilename() + "!");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageByListingId(@PathVariable String imageName) throws IOException{
        InputStream fileStream = new FileInputStream("./listingImages/" + imageName);
        return IOUtils.toByteArray(fileStream);
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
}
