package r.a.h.grant.gbt33190;

import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * grant
 * 29/4/2020 10:04 上午
 * 描述：
 */
public class TagsXmlTest {
    String path = "/Users/grant/Pictures/fp/Doc_0/Tags/Tag_Invoice.xml";
    @Test
    public void xml() throws IOException {
        File file = new File(path);
        FileInputStream in = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(in, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        bufferedReader.close();

        String[] xml = lines.get(1).split(" ");
        StringBuilder sb = new StringBuilder(xml[0]);
//        sb.append(" ").append(TagNameSpaceEnum.ofd.getKey()).append("=").append(TagNameSpaceEnum.ofd.getValue());
//        sb.append(" ").append(TagNameSpaceEnum.fp.getKey()).append("=").append(TagNameSpaceEnum.fp.getValue());
        sb.append(" ").append(xml[xml.length - 1]);
        lines.set(1, sb.toString());

        FileWriter writer = new FileWriter(file, false);
        for (String str : lines){
            writer.write(str + "\n");
        }
        writer.close();
    }
}
