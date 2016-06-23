package ws.dyt.recyclerviewadapter.utils;

import android.content.res.Resources;
import android.support.annotation.RawRes;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yangxiaowei on 15/11/27.
 */
public class FileUtils {
    public static String readRawFile(Resources resources, @RawRes int rawId) {
        String content = "";
        InputStream is=null;
        try{
            is=resources.openRawResource(rawId);
            byte buffer[]=new byte[is.available()];
            is.read(buffer);
            content=new String(buffer);
        }
        catch(IOException e) {
        }
        finally {
            if(is!=null) {
                try{
                    is.close();
                }catch(IOException e) {
                }
            }
        }
        return content;
    }
}
