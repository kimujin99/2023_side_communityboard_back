package side.boardservice.web.controller;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import side.boardservice.web.service.S3FileService;

import java.io.*;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class FileUploadController {

    @Autowired
    S3FileService s3FileService;

    @ResponseBody
    @PostMapping("/ck/imageUpload.do")
    public void ckFileUpload(HttpServletResponse res,
                             @RequestParam MultipartFile upload) {

        log.info("컨 트 롤 러 진 입 ! ! ! !");

        OutputStream out = null;
        PrintWriter printWriter = null;

        try {
            //s3 파일 업로드
            // s3CkUploadPath = location/bucketName/ck/uid_ck.ext
            String s3CkUploadPath = s3FileService.s3FileUpload(upload, "ck");

            log.info("s3CkUploadPath : {}", s3CkUploadPath);

            //파일 크기
            byte[] bytes = upload.getBytes();
            //원본 파일명
            String originFileName = upload.getOriginalFilename();
            //저장 파일명
            String savedFileName = s3CkUploadPath.substring(s3CkUploadPath.lastIndexOf("/") + 1);

            //ckEditor 로 전송
            printWriter = res.getWriter();
            String fileUrl = "/upload-image/" + "ck/" + savedFileName;

            log.info("fileUrl : {}", fileUrl);

            //json 으로 변환
            JsonObject json = new JsonObject();
            json.addProperty("uploaded", 1);
            json.addProperty("fileName", originFileName);
            json.addProperty("url", fileUrl);
            printWriter.println(json);
            printWriter.flush(); //초기화

            log.info("json : {}", json);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) { out.close(); }
                if(printWriter != null) { printWriter.close(); }
            } catch(IOException e) { e.printStackTrace(); }
        }
    }

}
