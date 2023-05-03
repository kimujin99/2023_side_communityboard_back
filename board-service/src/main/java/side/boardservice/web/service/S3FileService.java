package side.boardservice.web.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public String s3FileUpload(MultipartFile multipartFile, String dir) throws IOException {
        //파일명 설정 : UUID_경로.extension
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String s3FileName = UUID.randomUUID() + "_" + dir + "." + extension;
        //파일 저장경로 설정
        String uploadFilePath = dir + "/";

        //파일 사이즈 설정
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getSize());

        String keyName = uploadFilePath + s3FileName;

        //파일 업로드
        // 외부에 공개하는 파일인 경우 Public Read 권한을 추가, ACL 확인
        amazonS3.putObject(new PutObjectRequest(bucketName, keyName, multipartFile.getInputStream(), objMeta)
                .withCannedAcl(CannedAccessControlList.PublicRead));


        return amazonS3.getUrl(bucketName, keyName).toString();
    }
}
