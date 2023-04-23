package side.boardservice.web.controller;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class FileUploadController {

    @Value("${image.upload.path}")
    private String uploadPath;

    @ResponseBody
    @PostMapping("/ck/imageUpload.do")
    public void fileUpload(HttpServletRequest req, HttpServletResponse res,
                             @RequestParam MultipartFile upload) throws Exception{

        OutputStream out = null;
        PrintWriter printWriter = null;

        try {
            //랜덤 문자 생성
            UUID uid = UUID.randomUUID();
            //이름, 확장자, 바이트 가져오기
            String fileName = upload.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            byte[] bytes = upload.getBytes();

            //실제 이미지 저장 경로
            String path = uploadPath + File.separator + "ck" + File.separator;
            String ckUploadPath = path + uid + "." + extension;

            System.out.println("ckUploadPath :" + ckUploadPath);

            File folder = new File(path);

            //해당 디렉토리가 존재하는지 확인
            if(!folder.exists()){
                try{
                    folder.mkdirs(); // 폴더 생성
                }catch(Exception e){
                    e.getStackTrace();
                }
            }

            //이미지 저장
            out = new FileOutputStream(ckUploadPath);
            out.write(bytes);
            out.flush(); //초기화

            //ckEditor 로 전송
            printWriter = res.getWriter();
            String fileUrl = "/ck/images/" + uid + "." + extension;

            System.out.println("fileUrl :" + fileUrl);

            //json 으로 변환
            JsonObject json = new JsonObject();
            json.addProperty("uploaded", 1);
            json.addProperty("fileName", fileName);
            json.addProperty("url", fileUrl);
            printWriter.println(json);
            printWriter.flush(); //초기화

            System.out.println("json :" + json);

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
