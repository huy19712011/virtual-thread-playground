package com.example.sec07;

import com.example.sec07.aggregator.AggregatorService;
import com.example.sec07.aggregator.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Lec04AggregatorDemo {

    private static final Logger log = LoggerFactory.getLogger(Lec04AggregatorDemo.class);

    public static void main(String[] args) throws Exception {

        // beans / singletons
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        AggregatorService aggregator = new AggregatorService(executor);

        List<Future<ProductDto>> futures = IntStream.rangeClosed(1, 50)
                .mapToObj(id -> executor.submit(() -> aggregator.getProductDto(id)))
                .toList();
        List<ProductDto> list = futures.stream()
                .map(Lec04AggregatorDemo::toProductDto)
                .toList();

        log.info("list: {}", list);

    }

    private static ProductDto toProductDto(Future<ProductDto> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
