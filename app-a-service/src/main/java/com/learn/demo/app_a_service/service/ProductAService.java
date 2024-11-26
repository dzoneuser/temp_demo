package com.learn.demo.app_a_service.service;

import com.learn.demo.app_a_service.dto.BillingRequest;
import com.learn.demo.app_a_service.exception.ResourceNotFoundException;
import com.learn.demo.app_a_service.repository.ProductARepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@Service
public class ProductAService {

    @Autowired
    private  WebClient webClient; // (zz: why final required as in example)
    @Autowired
    private ProductARepository repository;

    @Value("${productB.timeOutInSeconds}")
    private int timeOutInSeconds;


    public String test() {
        return webClient.get()
                .uri("productB/test/1")
                .retrieve()
                .bodyToMono(String.class)
               //.timeout(Duration.ofSeconds(timeOutInSeconds))
                .block();
    }

    //refer:https://medium.com/@barbieri.santiago/circuit-breaker-c470a82d7233
  // @TimeLimiter(name = "offersTimeLimiter")
    @CircuitBreaker(name = "productB", fallbackMethod = "saveBillingFallback")
    public BillingRequest saveBilling(BillingRequest billingRequest, String arg)
            throws ExecutionException, InterruptedException {

        BillingRequest savedRequest =  repository.save(billingRequest);

         CompletableFuture<String> response =  CompletableFuture.supplyAsync(() -> {
               return webClient.get()
                       .uri("productB/test/"+arg)
                       .retrieve()
                       .bodyToMono(String.class)
                       //     .timeout(Duration.ofSeconds(timeOutInSeconds))
                       .block();

          });
        System.out.println("response from service: "+response.get());

        return savedRequest; //zz+ do something with resp ; may add to savedreq
    }



    public BillingRequest saveBillingFallback(BillingRequest billingRequest, String arg, Throwable exception) {

        System.out.println("circuit breaker Opened! from TimeLimitter : saveBillingFallback. Exception: "+exception);
        return new BillingRequest();

    }




    public List<BillingRequest> findAll(){
        return repository.findAll();
    }

    public BillingRequest findById(long id){
        return repository.findById( id).orElseThrow(() -> new ResourceNotFoundException("id "+id+" not found"));
    }

}
