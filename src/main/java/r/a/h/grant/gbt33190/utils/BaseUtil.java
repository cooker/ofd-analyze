package r.a.h.grant.gbt33190.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * grant
 * 16/2/2020 10:06 AM
 * 描述：
 */
public class BaseUtil {
    public static boolean isAbsolutePath(String path) {
        if (path.startsWith("/") || path.indexOf(":") > 0) {
            return true;
        }
        return false;
    }

    public static String getParent(String fileName) {
        File file = new File(fileName);
        return file.getParent();
    }

    public static boolean isChinese(char ch){
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }

        return false;
    }

    public static List<String> newList(String... strs){
        return Stream.of(strs).collect(Collectors.toList());
    }

    public static String unzip(String file) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        String tmp = ProperteisUtil.getValue("config.properties","parsing.path") +
                File.separator + UUID.randomUUID().toString();
        Files.createDirectories(
                Paths.get(tmp)
        );
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()){
            ZipEntry zipEntry = entries.nextElement();
            File f = Paths.get(tmp, zipEntry.getName()).toFile();
            if (zipEntry.isDirectory()){
                f.mkdirs();
            }else {
                if (!f.getParentFile().exists()){
                    f.getParentFile().mkdirs();
                }
                f.createNewFile();
                InputStream in = zipFile.getInputStream(zipEntry);
                FileOutputStream fout = new FileOutputStream(f);
                IOUtils.copy(in, fout);
                IOUtils.closeQuietly(fout);
            }
        }
        zipFile.close();
        return tmp;
    }

    public static void rmDir(String path) {
        if (path != null){
            try {
                FileUtils.forceDeleteOnExit(Paths.get(path).toFile());
            }catch (IOException e){}
        }
    }
}
