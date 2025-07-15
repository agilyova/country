package guru.qa.country.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Empty;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import guru.qa.country.domain.graphql.CountryGql;
import guru.qa.country.domain.graphql.CountryInputGql;
import guru.qa.country.grpc.CountResponse;
import guru.qa.country.grpc.CountryRequest;
import guru.qa.country.grpc.CountryResponse;
import guru.qa.country.grpc.CountryServiceGrpc;
import guru.qa.country.service.CountryService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GrpcCountryService extends CountryServiceGrpc.CountryServiceImplBase {

  private final CountryService countryService;

  public GrpcCountryService(CountryService countryService) {
    this.countryService = countryService;
  }

  @Override
  public void countries(Empty request, StreamObserver<CountryResponse> responseObserver) {
    List<CountryGql> allGqlCountries = countryService.getAllGqlCountries();

    for (CountryGql countryGql : allGqlCountries) {
      responseObserver.onNext(
        CountryResponse.newBuilder()
          .setId(countryGql.id().toString())
          .setName(countryGql.name())
          .setCode(countryGql.code())
          .build()
      );
    }
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<CountryRequest> addCountry(StreamObserver<CountResponse> responseObserver) {

    return new StreamObserver<>() {
      AtomicInteger countryCount = new AtomicInteger();
      List<String> failedCountries = new ArrayList<>();

      @Override
      public void onNext(CountryRequest countryRequest) {
        try {
          countryService.addCountryGql(
            new CountryInputGql(
              countryRequest.getName(),
              countryRequest.getCode()
            )
          );
          countryCount.incrementAndGet();
        } catch (Exception e) {
          failedCountries.add(countryRequest.getCode() + " " + countryRequest.getName() + ": " + e.getMessage());
        }
      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onCompleted() {
        CountResponse.Builder builder = CountResponse.newBuilder()
          .setCount(countryCount.intValue());

        if (!failedCountries.isEmpty()) {
          builder.addAllErrors(failedCountries);
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
      }
    };
  }

  @Override
  public void updateCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
    try {
      String jsonString = JsonFormat.printer()
        .omittingInsignificantWhitespace()
        .print(request);

      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonString);

      CountryGql countryGql = countryService.updateCountryByCodeGql(request.getCode(), jsonNode);

      CountryResponse response = CountryResponse.newBuilder()
        .setId(countryGql.id().toString())
        .setCode(countryGql.code())
        .setName(countryGql.name())
        .build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();

    } catch (Exception e) {
      e.printStackTrace();
      responseObserver.onError(e);
    }
  }
}
