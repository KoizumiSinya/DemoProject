package studio.sinya.jp.demo_httpframwork.http.callback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import studio.sinya.jp.demo_httpframwork.http.utils.FileUtils;

/**
 * Created by SinyaKoizumi on 2015/9/13.
 */
public abstract class AbstractCallback implements ICallback {
    public String path;
    private static final int IO_BUFFER_SIZE = 4 * 1024;

    @Override
    public Object handle(HttpResponse response) {

        // file, json, xml, image, string

        int statusCode = -1;
        InputStream in = null;

        try {
            HttpEntity entity = response.getEntity();
            statusCode = response.getStatusLine().getStatusCode();

            switch (statusCode) {
                case HttpStatus.SC_OK:

                    if (FileUtils.isValidate(path)) {

                        //将服务器返回的数据写到指定path下的文件中
                        FileOutputStream fos = new FileOutputStream(path);

                        if (entity.getContentEncoding() != null) {
                            String encoding = entity.getContentEncoding().getValue();

                            if (encoding != null && "gzip".equalsIgnoreCase(encoding)) {
                                in = new GZIPInputStream(entity.getContent());
                            }

                            if (encoding != null && "deflate".equalsIgnoreCase(encoding)) {
                                in = new InflaterInputStream(entity.getContent());
                            }

                        } else {
                            in = entity.getContent();
                        }

                        byte[] buf = new byte[IO_BUFFER_SIZE];
                        int len;
                        while ((len = in.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }

                        fos.flush();
                        fos.close();
                        in.close();

                        return bindData(path);

                    } else {
                        return bindData(EntityUtils.toString(entity));
                    }

                default:
                    break;
            }
            return null;

        } catch (Exception e) {

        }
        return null;
    }

    protected Object bindData(String content) {
        return null;
    }

    public AbstractCallback setPath(String path) {
        this.path = path;
        return this;
    }
}
