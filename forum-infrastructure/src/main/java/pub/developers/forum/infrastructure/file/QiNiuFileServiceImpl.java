package pub.developers.forum.infrastructure.file;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import pub.developers.forum.common.enums.ErrorCodeEn;
import pub.developers.forum.common.support.CheckUtil;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.domain.service.FileService;

/**
 * @author Qiangqiang.Bian
 * @create 2020/11/23
 * @desc
 **/
@Slf4j
@Data
@ConfigurationProperties(prefix = "custom-config.upload-file.qiniu")
@Component
public class QiNiuFileServiceImpl implements FileService {

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String accessDomain;

    @Override
    public String uploadImg(byte[] base64, String key) {
        Configuration configuration = new Configuration();
        UploadManager uploadManager = new UploadManager(configuration);
        try {
            String token = Auth.create(accessKey, secretKey).uploadToken(bucketName, key);
            CheckUtil.isEmpty(token, ErrorCodeEn.FILE_UPLOAD_FAIL);

            Response res = uploadManager.put(base64, key, token);
            CheckUtil.isFalse(res.isOK(), ErrorCodeEn.FILE_UPLOAD_FAIL);

            return accessDomain + "/" + res.jsonToObject(Ret.class).key;
        } catch (QiniuException e) {
            LogUtil.info(log, e, "上传文件异常，e.msg={}", e.getMessage());
            CheckUtil.isTrue(true, ErrorCodeEn.FILE_UPLOAD_FAIL);
        }
        return null;
    }

    @Data
    class Ret {
        public long fsize;
        public String key;
        public String hash;
        public int width;
        public int height;
    }

}
