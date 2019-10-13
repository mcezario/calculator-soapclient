package org.mcezario.calculator.cmd;

import lombok.extern.slf4j.Slf4j;
import org.mcezario.calculator.usecases.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CmdLineUri implements CommandLineRunner {

    @Autowired
    private Calculator service;

    @Override
    public void run(String... args) {
        log.info("Calling SOAP calculator client...");
        try {
            log.info("Result: {}", service.sum(1, 2));
        } catch (final Exception e) {
            log.error("Error to evaluate the SUM operation. Make sure that the WEB Service Server is up.", e);
        }
    }

}
