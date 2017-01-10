package core;

import com.google.common.io.ByteStreams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseReader {

    public String read(Response response) {
        String page = "";
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ByteStreams.copy(response.body(), out);
            page = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    public static ResponseReader reader() {
        return new ResponseReader();
    }
}