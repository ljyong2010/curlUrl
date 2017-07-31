package yf.liu.curl;

import yf.liu.service.ProductattachService;

public class CurlPicture {
    public static void main(String[] args){
        new Thread(new PictureCreate(new ProductattachService())).start();
    }
}
