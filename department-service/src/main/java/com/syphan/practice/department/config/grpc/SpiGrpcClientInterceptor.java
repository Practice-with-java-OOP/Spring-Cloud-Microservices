package com.syphan.practice.department.config.grpc;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@Component
@Slf4j
public class SpiGrpcClientInterceptor implements ClientInterceptor {

    //    @Value("${mm-spi.clientId}")
    protected String clientId;
    //    @Value("${mm-spi.clientSecret}")
    protected String clientSecret;
    //    @Value("${spi.timeout}")
    private Integer spiTimeout;

    public static final Metadata.Key<String> ACCEPT_LANGUAGE_KEY = Metadata.Key
            .of(HttpHeaders.ACCEPT_LANGUAGE, ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<String> CLIENT_ID_KEY = Metadata.Key
            .of("Client-Id", ASCII_STRING_MARSHALLER);

    private static final Metadata.Key<String> CLIENT_SECRET_KEY = Metadata.Key
            .of("Client-Secret", ASCII_STRING_MARSHALLER);


    @Override
    public <M, R> ClientCall<M, R> interceptCall(
            MethodDescriptor<M, R> methodDescriptor, CallOptions callOptions,
            Channel channel) {
        return new SimpleForwardingClientCall<M, R>(channel.newCall(methodDescriptor,
                callOptions.withDeadlineAfter(60000, TimeUnit.MILLISECONDS))) {

            @SneakyThrows
            @Override
            public void sendMessage(M message) {
                log.info("Request:\n{}", message);
                super.sendMessage(message);
            }

            @Override
            public void start(Listener<R> responseListener, Metadata headers) {
                headers.put(ACCEPT_LANGUAGE_KEY, LocaleContextHolder.getLocale().getLanguage());
                headers.put(CLIENT_ID_KEY, "clientId");
                headers.put(CLIENT_SECRET_KEY, "clientSecret");
                GrpcClientListener<R> grpcClientListener = new GrpcClientListener<>(
                        methodDescriptor.getFullMethodName(),
                        responseListener);
                super.start(grpcClientListener, headers);
            }
        };
    }

    @Slf4j
    private static class GrpcClientListener<R> extends ClientCall.Listener<R> {

        String methodName;
        ClientCall.Listener<R> responseListener;

        protected GrpcClientListener(String methodName, ClientCall.Listener<R> responseListener) {
            super();
            this.methodName = methodName;
            this.responseListener = responseListener;
        }

        @SneakyThrows
        @Override
        public void onMessage(R message) {
            log.info("Response:\n{}", message);
            responseListener.onMessage(message);
        }

        @Override
        public void onHeaders(Metadata headers) {
            responseListener.onHeaders(headers);
        }

        @Override
        public void onClose(Status status, Metadata trailers) {
            responseListener.onClose(status, trailers);
        }

        @Override
        public void onReady() {
            responseListener.onReady();
        }
    }
}
