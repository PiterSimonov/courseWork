package simonov.hotel.utilites;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FileUpLoader {
    private static Log log = LogFactory.getLog(FileUpLoader.class);
    private static final String IMGUR_LINK = "https://api.imgur.com/3/image";
    private static final String CLIENT_ID = "eb5571d14ad3857";

    public static String uploadImageToImgur(MultipartFile imageFile) {
        URL url;
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
        try {
            url = new URL(IMGUR_LINK);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedImage image = ImageIO.read(imageFile.getInputStream());
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArray);
            byte[] byteImage = byteArray.toByteArray();
            String dataImage = Base64.encode(byteImage);
            String data = URLEncoder.encode("image", "UTF-8") + "="
                    + URLEncoder.encode(dataImage, "UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Authorization", "Client-ID " + CLIENT_ID);
            conn.setRequestMethod(RequestMethod.POST.name());
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            StringBuilder stb = new StringBuilder();
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                stb.append(line).append("\n");
            }
            return parseJsonString(stb.toString());
        } catch (IOException e) {
            log.error("Image uploading failed due to exception:", e);
        } finally {
            try {
                if (wr!=null){wr.close();}
                if (rd!=null){rd.close();}
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    private static String parseJsonString(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readValue(jsonString, JsonNode.class);
        JsonNode jsonData = node.get("data");
        JsonNode link = jsonData.get("link");
        String stringLink = link.asText();
        return stringLink.replace(".jpg", "m.jpg");
    }
}
