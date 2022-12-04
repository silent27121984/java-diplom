package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class GraphicsConverter implements TextGraphicsConverter {

    TextColorSchema schema = new ColorSchema();
    protected int maxWidth = 0;
    protected int maxHeight = 0;
    protected double maxRatio = 0;

    @Override
    public String convert(String url) throws BadImageSizeException, IOException {

        BufferedImage img = ImageIO.read(new URL(url));
        double checkAspectRatio = (img.getWidth() > img.getHeight()) ? (double) img.getWidth() / img.getHeight() :
                (double) img.getHeight() / img.getWidth();
        if (checkAspectRatio > this.maxRatio) {
            throw new BadImageSizeException(checkAspectRatio, this.maxRatio);
        }

        int height = img.getHeight();
        int width = img.getWidth();
        double ratio;
        if (height > maxHeight || width <= maxWidth) {
            ratio = (double) width / maxWidth;
        } else {
            ratio = (double) height / maxHeight;
        }

        int newWidth = (int) (ratio == 0 ? (double) width : (double) width / ratio);
        int newHeight = (int) (ratio == 0 ? (double) height : (double) height / ratio);

        Image scaleImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaleImage, 0, 0, null);
        ImageIO.write(bwImg, "png", new File("out.png"));
        WritableRaster bwRaster = bwImg.getRaster();

        StringBuilder sb = new StringBuilder();
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                sb.append(c).append(c);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}