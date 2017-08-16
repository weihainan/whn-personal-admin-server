package com.whn.personal.modules.web;

import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MatrixToImageWriter {

    private static final Logger logger = LoggerFactory.getLogger(MatrixToImageWriter.class);

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private MatrixToImageWriter() {}

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    public static void writeToStreamWithLogo(BitMatrix matrix, String format, OutputStream stream, InputStream iputStream) throws IOException {
        int iconSize = matrix.getHeight()/5;
        int location = matrix.getHeight()*2/5;
        BufferedImage image = toBufferedImage(matrix);
        //载入logo
        Graphics2D gs = image.createGraphics();
        Image img = ImageIO.read(iputStream).getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT);
        gs.drawImage(img, location, location, null);
        gs.dispose();
        img.flush();
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
//
//    /**
//     * 发送http请求
//     * @param url
//     * @param method 请求类型
//     * @param body 请求体
//     * @return
//     * @throws IOException
//     */
//    public static void writeQRCodeWithLogo(String dentryId,BitMatrix matrix, String format, OutputStream stream) throws Exception {
//        CloseableHttpClient httpClient = null;
//        try {
//            String url = Config.getCsServiceHost() + "/download?dentryId=" + dentryId ;
//            System.out.println(url);
//            httpClient = HttpClients.createDefault();
//            URI uri = new URI(url);
//            String host = uri.getAuthority();
//            CloseableHttpResponse response = null;
//            String session = CSRestfulClient.getSession();
//            if(logger.isDebugEnabled()){
//                logger.debug("-toSend param--------------------------");
//                logger.debug("-url="+url);
//                logger.debug("-method= get");
//                logger.debug("-body= null");
//                logger.debug("-session="+session);
//            }
//            HttpGet httpGet = new HttpGet(url);
//            //添加请求头
//            httpGet.setHeader(CSRestfulClient.ACCEPT,"application/json");
//            httpGet.setHeader(CSRestfulClient.HOST,host);
//            if(!StringUtils.isNullOrEmpty(session)){
//                httpGet.setHeader(CSRestfulClient.SESSION,session);
//            }
//            response = httpClient.execute(httpGet);
//            HttpResult result =  CSRestfulClient.buildContent(response,true);
//            MatrixToImageWriter.writeToStreamWithLogo(matrix,format,stream,result.getInputStream());
//        } catch (Exception e){
//            throw e;
//        } finally {
//            if (httpClient != null) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public static HttpResult downloadFile(String dentryId) throws Exception {
//        CloseableHttpClient httpClient = null;
//        try {
//            String url = Config.getCsServiceHost() + "/download?dentryId=" + dentryId ;
//            System.out.println(url);
//            httpClient = HttpClients.createDefault();
//            URI uri = new URI(url);
//            String host = uri.getAuthority();
//            CloseableHttpResponse response = null;
//            String session = CSRestfulClient.getSession();
//            if(logger.isDebugEnabled()){
//                logger.debug("-toSend param--------------------------");
//                logger.debug("-url="+url);
//                logger.debug("-method= get");
//                logger.debug("-body= null");
//                logger.debug("-session="+session);
//            }
//            HttpGet httpGet = new HttpGet(url);
//            //添加请求头
//            httpGet.setHeader(CSRestfulClient.ACCEPT,"application/json");
//            httpGet.setHeader(CSRestfulClient.HOST,host);
//            if(!StringUtils.isNullOrEmpty(session)){
//                httpGet.setHeader(CSRestfulClient.SESSION,session);
//            }
//            response = httpClient.execute(httpGet);
//            HttpResult result =  CSRestfulClient.buildContent(response,true);
//            return result;
//        } catch (Exception e){
//            throw e;
//        } finally {
//            if (httpClient != null) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public static void main(String[] args) throws IOException{
//        Image img = ImageIO.read(new File("http://sdpcs.beta.web.sdp.101.com/v0.1/download?dentryId=8d53c854-b2a2-43e7-8879-5a49887176f0"));
//        System.out.println(img);
//    }
}
