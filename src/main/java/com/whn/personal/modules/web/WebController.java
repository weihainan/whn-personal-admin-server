package com.whn.personal.modules.web;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.whn.personal.internal.constant.GustApi;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/8/16.
 */
@RestController
@RequestMapping("/v0.1")
public class WebController {

    @Resource
    private DataSource dataSource;

    @GustApi
    @RequestMapping(value = "/{userId}/{script}", method = RequestMethod.GET)
    public Object initSql(@PathVariable String userId, @PathVariable String script) {
        if (!"214372".equals(userId)) {
            Map<String, String> res = new HashMap<>();
            res.put("result", "not auth");
            return res;
        }

        try {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("sql/" + script + ".sql"));
            populator.populate(this.dataSource.getConnection());
            Map<String, String> res = new HashMap<>();
            res.put("result", "success");
            return res;
        } catch (SQLException e) {
            Map<String, String> res = new HashMap<>();
            res.put("result", "fails");
            return res;
        }
    }

    @GustApi
    @RequestMapping(value = "/zip", method = RequestMethod.GET)
    public void zip(HttpServletResponse response) {
        int size = 256;
        ZipOutputStream zipOutputStream = null;
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", String.format("attachment;filename=test.zip", "UTF-8"));
        try {
            zipOutputStream = new ZipOutputStream(response.getOutputStream());
            zipOutputStream.setEncoding("UTF-8");
            zipOutputStream.setFallbackToUTF8(true);
            zipOutputStream.setUseLanguageEncodingFlag(true);

            ZipEntry zipEntry = new ZipEntry(1 + ".jpg");
            zipOutputStream.putNextEntry(zipEntry);
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            BitMatrix bitMatrix = multiFormatWriter.encode("二维码内容", BarcodeFormat.QR_CODE, size, size, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", zipOutputStream);
            zipOutputStream.flush();
            zipOutputStream.closeEntry();

            // 下面代码可以放在for里面 里边加入多个文件到压缩包里面
            zipEntry = new ZipEntry(2 + ".jpg");
            zipOutputStream.putNextEntry(zipEntry);
            bitMatrix = multiFormatWriter.encode("二维码内容2", BarcodeFormat.QR_CODE, size, size, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", zipOutputStream);
            zipOutputStream.flush();
            zipOutputStream.closeEntry();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != zipOutputStream) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
