package com.github.saphyra.randwo.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LogoutController {
    private static final String LOGOUT_MAPPIG = "/logout";

    @GetMapping(LOGOUT_MAPPIG)
    public void logout() {
        new Thread(new Logout()).start();
    }

    private class Logout implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.exit(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
