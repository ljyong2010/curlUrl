package yf.liu.curl;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yf.liu.model.TfFProductattach;
import yf.liu.service.ProductattachService;
import yf.liu.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PictureCreate implements Runnable {
    private final static Logger LOGGER = LoggerFactory.getLogger(PictureCreate.class);

    private ProductattachService productattachService;
    public PictureCreate(ProductattachService productattachService) {
        this.productattachService = productattachService;
    }

    public void createClient(){
        List<TfFProductattach> tfFs = productattachService.getTfList();
        try {
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).setConnectionRequestTimeout(6000).setConnectTimeout(6000).build();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            for (TfFProductattach tf:tfFs){
                String fileBase = tf.getStr("file_path");
                HttpGet httpGet = new HttpGet("http://www.v360v.com"+fileBase);
                httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                if (entity!=null){
                    InputStream inputStream = entity.getContent();
                    String filePath = "F:"+fileBase.substring(0,fileBase.lastIndexOf("/")+1);
                    String fileName = fileBase.substring(fileBase.lastIndexOf("/")+1);
                    File dir = new File(filePath);
                    if (!dir.exists()){
                        dir.mkdirs();
                    }
                    File file = new File(filePath + fileName);
                    FileUtils.copyToFile(inputStream,file);
                    LOGGER.info("downlowd success");
                    /*Thread.sleep(1000);*/
                    productattachService.updateTf(tf.getBigDecimal("pkid").intValue());
                    System.out.println("download success: =================="+tf.getBigDecimal("pkid").intValue());
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        int count = productattachService.getProductattachCount()/100+1;
        System.out.println("total execute : "+count+"====");
        for (int i = 0;i < count;i++){
            System.out.println("being execute : "+(count-i)+"====");
            createClient();
        }

    }

}
