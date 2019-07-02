package com.github.saphyra.randwo.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Speaker {
    private final String path;

    public Speaker() {
        log.info("Creating speaker. Copying .vbs file to temporary file");
        File scriptFile;
        try {
            scriptFile = File.createTempFile("speak", ".vbs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        path = scriptFile.getAbsolutePath();
        log.info("temporaryFile path: {}", path);

        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(scriptFile))) {
            InputStream is = getClass().getClassLoader().getResourceAsStream("speak.vbs");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String content = reader.lines().collect(Collectors.joining(System.lineSeparator()));

            out.write(content.getBytes());
            out.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void speakValue(String text) {
        log.info("Speaking text {}", text);
        String command = String.format("wscript " + path + " \"%s\"", text);
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
