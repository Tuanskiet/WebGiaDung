package com.poly.WebGiaDung.controller.admin;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.*;

@Controller
@RequiredArgsConstructor
public class ManagerSettingController {

    @GetMapping("/admin/manager-setting")
    public String viewSettingPage() {
        return "admin/setting";
    }

    @PostMapping("/admin/set-banner")
    public String doSetBanner(@RequestParam(name = "banner_file") MultipartFile multipartFile, Model model, HttpSession session) {
        String pathUpload = System.getProperty("user.dir") +  "/uploads";
        String nameFile = "banner.jpg";

        try {
            File folderUpload = new File(pathUpload);
            if(!folderUpload.exists()){
                folderUpload.mkdirs();
            }

            Path path = Paths.get(pathUpload , nameFile);
            Files.write(path, multipartFile.getBytes());

            System.out.println(folderUpload + nameFile);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Fail!");
            return "admin/setting";
        }
        model.addAttribute("success", "Success.");
        return "admin/setting";
    }
}

