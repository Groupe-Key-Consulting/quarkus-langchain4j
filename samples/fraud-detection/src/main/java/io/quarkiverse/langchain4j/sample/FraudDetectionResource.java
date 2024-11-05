package io.quarkiverse.langchain4j.sample;

import java.util.List;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/fraud")
public class FraudDetectionResource {

    private final FraudDetectionAi service;
    private final TransactionRepository transactions;

    public FraudDetectionResource(FraudDetectionAi service, TransactionRepository transactions) {
        this.service = service;
        this.transactions = transactions;
    }

    @GET
    @Path("/distance")
    public String detectBasedOnDistance(@RestQuery long customerId) {
        return service.detectDistanceFraudForCustomer(customerId);
    }

    @GET
    @Path("/amount")
    public String detectBaseOnAmount(@RestQuery long customerId) {
        return service.detectAmountFraudForCustomer(customerId);
    }

    @GET
    @Path("/amount/streamed")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> detectBaseOnAmountReactive(@RestQuery long customerId) {
        return service.detectAmountFraudForCustomerStreamed(customerId);
    }

    @GET
    @Path("/transactions")
    public List<Transaction> list(@RestQuery long customerId) {
        return transactions.getTransactionsForCustomer(customerId);
    }

    @GET
    @Path("/verification")
    public double verify(@RestQuery long customerId) {
        return transactions.getAmountForCustomer(customerId);
    }
}
