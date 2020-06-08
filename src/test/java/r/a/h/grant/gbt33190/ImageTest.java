package r.a.h.grant.gbt33190;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.levigo.jbig2.JBIG2ImageReader;
import com.levigo.jbig2.JBIG2ImageReaderSpi;
import com.levigo.jbig2.io.DefaultInputStreamFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * grant
 * 4/6/2020 5:21 下午
 * 描述：
 */
public class ImageTest {
    public static void main(String[] args) throws IOException, FormatException, ChecksumException, NotFoundException {
        String filepath = "/Users/grant/Documents/个人/image_108.jb2";

        int imageIndex = 0;
        InputStream inputStream = new FileInputStream(filepath);
        DefaultInputStreamFactory disf = new DefaultInputStreamFactory();
        ImageInputStream imageInputStream = disf.getInputStream(inputStream);

        JBIG2ImageReader imageReader = new JBIG2ImageReader(new JBIG2ImageReaderSpi());
        imageReader.setInput(imageInputStream);

        // long timeStamp = System.currentTimeMillis();
        BufferedImage bufferedImage = imageReader.read(imageIndex, imageReader.getDefaultReadParam());

        ImageIO.write(bufferedImage, "jpg", new File("/Users/grant/Documents/个人/image_108.jpg"));

        BinaryBitmap binaryBitmap= new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        QRCodeReader qrCodeReader = new QRCodeReader();
        Result result = qrCodeReader.decode(binaryBitmap);
        System.out.println(result.getText());

    }
}
