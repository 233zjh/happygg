package com.ruoyi.common.utils.file;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import com.ruoyi.common.utils.ImageCompressor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.Seq;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * 文件上传工具类
 *
 * @author ruoyi
 */
public class FileUploadUtils
{
    /**
     * 默认大小 100M
     */
    public static final long DEFAULT_MAX_SIZE = 100 * 1024 * 1024L;

    /**
     * 图片默认大小 512KB
     */
    public static final long IMAGE_DEFAULT_MAX_SIZE = 512 * 1024L;

    /**
     * 视频默认大小 10M
     */
    public static final long VIDEO_DEFAULT_MAX_SIZE = 10 * 1024 * 1024L;
    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = RuoYiConfig.getProfile();

    public static void setDefaultBaseDir(String defaultBaseDir)
    {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir()
    {
        return defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static final String upload(MultipartFile file) throws IOException
    {
        try
        {
            return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException 比如读写文件出错时
     * @throws InvalidExtensionException 文件校验异常
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException
    {

        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

//        assertAllowed(file, allowedExtension);
        String fileName = extractFilename(file);
        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        String newPath = "";
        InputStream is = file.getInputStream();
        if(fileName.toUpperCase().contains(".HEIC")){
            File tempFile = new File(absPath);
            file.transferTo(tempFile);
            //如果为heic格式图片，则转换成jpg格式
            newPath = absPath.replace(".HEIC",".JPG");
            // 使用ImageMagick转换格式并不保留exif信息
            String cmd = "magick " + absPath + " -strip "  + absPath.replace(".HEIC",".JPG");
            ImageCompressor.executeCmd(cmd);
            fileName =  fileName.replace(".HEIC",".JPG");
            tempFile.delete();
            is = Files.newInputStream(Paths.get(newPath));
        }

//      无损压缩 目前不支持heic、heif格式文件
        try {

            BufferedImage inputImage = ImageIO.read(is);
            BufferedImage _newImg= new BufferedImage(inputImage.getWidth(),inputImage.getHeight(),BufferedImage.TYPE_INT_RGB);

            _newImg.getGraphics().drawImage(inputImage, 0, 0, null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // 使用ImageWriter来写入图片，可以设置压缩质量
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(getExtension(file));
            if (writers.hasNext()) {
                ImageWriter writer = writers.next();
                ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
                writer.setOutput(ios);
                IIOImage image = new IIOImage(_newImg, null, null);
                ImageWriteParam param = writer.getDefaultWriteParam();
                long size = file.getSize();
                float rate = 1f;
                if(fileName.lastIndexOf("mp4")>0){// 视频
                    if (size > VIDEO_DEFAULT_MAX_SIZE)
                    {
                        rate = VIDEO_DEFAULT_MAX_SIZE / (size * 1f);
                    }
                }else{
                    if (size > IMAGE_DEFAULT_MAX_SIZE)
                    {
                        rate = IMAGE_DEFAULT_MAX_SIZE / (size * 1f);
                    }
                }
                // 设置压缩质量参数（如果格式支持）
                if (param.canWriteCompressed()&&rate!=1f) {
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(rate);
                }

                writer.write(null, image, param);
                ios.close();
                writer.dispose();
            }

            File tempFile = Paths.get(absPath).toFile();
            // 将压缩后的图片数据写入到输出文件
            ImageIO.write(_newImg, getExtension(file), tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            is.close();
        }
////        使用Thumbnails进行图片压缩
//        long size = file.getSize();
//        double rate = 1.0;
//        if(fileName.lastIndexOf("mp4")>0){// 视频
//            if (size > VIDEO_DEFAULT_MAX_SIZE)
//            {
//                rate = VIDEO_DEFAULT_MAX_SIZE / (size * 1.0);
//            }
//        }else{
//            if (size > IMAGE_DEFAULT_MAX_SIZE)
//            {
//                rate = IMAGE_DEFAULT_MAX_SIZE / (size * 1.0);
//            }
//        }
//        File tempFile = Paths.get(absPath).toFile();
//        try (InputStream is = file.getInputStream()) {
//            Thumbnails.of(is)
//                    .scale(1f)
//                    .outputQuality(rate)
//                    .toFile(tempFile);
//        }
        return getPathFileName(baseDir, fileName);
    }

    public static MultipartFile convertFileToMultipartFile(File file) throws IOException {
        String originalFilename = file.getName();
        String contentType = "application/octet-stream"; // 你可以根据文件类型设置正确的 MIME 类型
        String name = "file"; // 这是表单字段的名称
        byte[] content = new byte[(int) file.length()];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.read(content);
        }
        return new MockMultipartFile(name, originalFilename, contentType, content);
    }

    public static void main(String[] args) {
        String[] readFormats = ImageIO.getReaderFormatNames();
        String[] writeFormats = ImageIO.getWriterFormatNames();
        System.out.println("Readers:  " + Arrays.asList(readFormats));
        System.out.println("Writers:  " + Arrays.asList(writeFormats));
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file)
    {
        return StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(),
                FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType), getExtension(file));
    }

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists())
        {
            if (!desc.getParentFile().exists())
            {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static final String getPathFileName(String uploadDir, String fileName) throws IOException
    {
        int dirLastIndex = RuoYiConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException
    {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension))
        {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            }
            else
            {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension)
    {
        for (String str : allowedExtension)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file)
    {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension))
        {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }
}
