package side.boardservice.web.controller;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import side.boardservice.domain.Message;
import side.boardservice.domain.StatusEnum;
import side.boardservice.web.service.S3FileService;

import java.io.*;
import java.nio.charset.Charset;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FileUploadController {

    @Autowired
    S3FileService s3FileService;

    @ResponseBody
    @PostMapping("/boards/ck/imageUpload.do")
    public void ckFileUpload(HttpServletResponse res,
                             @RequestParam MultipartFile upload) {

        PrintWriter printWriter = null;

        try {
            //s3 파일 업로드
            // s3CkUploadPath = location/bucketName/ck/uid_ck.ext
            String s3CkUploadPath = s3FileService.s3FileUpload(upload, "ck");

            //파일 크기
            byte[] bytes = upload.getBytes();
            //원본 파일명
            String originFileName = upload.getOriginalFilename();
            //저장 파일명
            String savedFileName = s3CkUploadPath.substring(s3CkUploadPath.lastIndexOf("/") + 1);

            //ckEditor 로 전송
            printWriter = res.getWriter();
            String fileUrl = "/upload-image/" + "ck/" + savedFileName;

            //json 으로 변환
            JsonObject json = new JsonObject();
            json.addProperty("uploaded", 1);
            json.addProperty("fileName", originFileName);
            json.addProperty("url", fileUrl);
            printWriter.println(json);
            printWriter.flush(); //초기화

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(printWriter != null) { printWriter.close(); }
        }
    }

    @ResponseBody
    @PostMapping("/profile-upload.do")
    public ResponseEntity<Message> profileUpload(HttpServletResponse res,
                                                 @RequestParam MultipartFile profile) {
        String s3CkUploadPath = null;

        try {
            //s3 파일 업로드
            s3CkUploadPath = s3FileService.s3FileUpload(profile, "prf");
            log.info("s3CkUploadPath: {}", s3CkUploadPath);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return fetchResponseOk(s3CkUploadPath);
        }

    }

    //JSON으로 성공 메시지 뿌려주는 함수
    public ResponseEntity<Message> fetchResponseOk(Object data) {
        //응답 객체
        Message msg = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //메시지 설정
        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공");
        msg.setData(data);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }
}
