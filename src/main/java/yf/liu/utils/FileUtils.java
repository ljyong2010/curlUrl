package yf.liu.utils;

import java.io.*;

public class FileUtils {

    public static void copyToFile(InputStream inputStream, File file){
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            while (true){
                int read = inputStream.read(buff);
                if (read == -1){
                    break;
                }
                byte[] temp = new byte[read];
                System.arraycopy(buff,0,temp,0,read);
                os.write(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                os.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
