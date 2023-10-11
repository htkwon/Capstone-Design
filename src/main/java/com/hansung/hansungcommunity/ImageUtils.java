package com.hansung.hansungcommunity;

import com.hansung.hansungcommunity.dto.ImageDto;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

// 이미지 압축 및 크기 조정 유틸리티 클래스
public class ImageUtils {

    private static final String patternString = "<img\\s+[^>]*src\\s*=\\s*\"([^\"]*)\"[^>]*>";


    // 인스턴스화 방지
    private ImageUtils() {
        throw new AssertionError();
    }

    public static byte[] compressAndResizeImage(byte[] imageData, int targetWidth, int targetHeight, float quality, String formatName) throws IOException {
        // 원본 이미지 로드
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));

        // 원하는 크기로 이미지 조정
        Image resizedImage = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage bufferedResizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedResizedImage.createGraphics();
        graphics.drawImage(resizedImage, 0, 0, null);
        graphics.dispose();

        // 이미지를 압축하여 바이트 배열로 변환
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedResizedImage, formatName, outputStream);

        // 압축된 이미지 바이트 배열 반환
        return outputStream.toByteArray();
    }

    public static List<ImageDto> extractImagesFromContent(String content){
        List<ImageDto> images = new ArrayList<>();

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(content);

        for (; matcher.find();) {
            String imageUrl = matcher.group(1);
            String resizeUrl = imageUrl + "/resize";
            ImageDto imageDto = new ImageDto();
            imageDto.setImageUrl(resizeUrl);
            images.add(imageDto);
        }
        return  images;
    }

}
