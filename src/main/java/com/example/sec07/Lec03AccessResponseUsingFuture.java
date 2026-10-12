package com.example.sec07;

import com.example.sec07.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Lec03AccessResponseUsingFuture {

    private static final Logger log = LoggerFactory.getLogger(Lec03AccessResponseUsingFuture.class);

    public static void main(String[] args) throws Exception {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            Future<String> product1 = executor.submit(() -> Client.getProduct(1));
            Future<String> product2 = executor.submit(() -> Client.getProduct(2));
            Future<String> product3 = executor.submit(() -> Client.getProduct(3));

            log.info("product-1: {}", product1.get());
            log.info("product-2: {}", product2.get());
            log.info("product-3: {}", product3.get());

        }

    }
}
