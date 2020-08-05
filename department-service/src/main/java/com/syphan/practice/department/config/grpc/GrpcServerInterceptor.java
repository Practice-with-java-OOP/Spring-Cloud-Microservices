//package com.syphan.practice.department.config.grpc;
//
//import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
//import io.grpc.Metadata;
//import io.grpc.MethodDescriptor;
//import io.grpc.ServerCall;
//import io.grpc.ServerCall.Listener;
//import io.grpc.ServerCallHandler;
//import io.grpc.ServerInterceptor;
//import io.grpc.Status;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.logging.log4j.ThreadContext;
//import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;
//
//@GRpcGlobalInterceptor
//@Slf4j
//@Component
//public class GrpcServerInterceptor implements ServerInterceptor {
//    @Autowired
//    private ClientServiceCache clientServiceCache;
//    @Autowired
//    private SecretVaultData secretVaultData;
//    @Autowired
//    GenericUtils genericUtils;
//    @Autowired
//    ClientConfigCache clientConfigCache;
//
//    private static final Metadata.Key<String> REQUEST_ID_KEY = Metadata.Key
//            .of(HeaderConstant.X_REQUEST_ID, ASCII_STRING_MARSHALLER);
//
//    @Override
//    public <ReqT, RespT> Listener<ReqT> interceptCall(
//            ServerCall<ReqT, RespT> serverCall, Metadata headers,
//            ServerCallHandler<ReqT, RespT> serverCallHandler) {
//        String requestId = headers.get(REQUEST_ID_KEY);
//        ThreadContext.put(HeaderConstant.X_REQUEST_ID, requestId);
//        ThreadContext.put(HeaderConstant.X_REQUEST_ID, requestId);
//
//        GrpcServerCall<ReqT, RespT> grpcServerCall = new GrpcServerCall<>(serverCall);
//
//        Listener<ReqT> listener = serverCallHandler.startCall(grpcServerCall, headers);
//
//        return new SimpleForwardingServerCallListener<ReqT>(listener) {
//            @Override
//            public void onMessage(ReqT message) {
//                super.onMessage(message);
//                String messageToLog = genericUtils.removeSensitiveCharacters(message.toString());
//                log.info("[{}] Request ({}):\n{}{}", serverCall.getMethodDescriptor().getFullMethodName(),
//                        requestId, messageToLog);
//                long begin = System.currentTimeMillis();
//
//                String command = serverCall.getMethodDescriptor().getFullMethodName();
//
//                log.info(" -------- Check path ------ ");
//                String clientId = headers.get(Metadata.Key.of("Client-Id", ASCII_STRING_MARSHALLER));
//                String clientSecret = headers.get(Metadata.Key.of("Client-Secret", ASCII_STRING_MARSHALLER));
//
//                if (GenericUtils.ignoreSecurityUris(command)) {
//                    serverCall.close(Status.OK, headers);
//                    return;
//                }
//                log.info(" -------- Request from client: " + clientId + " | " + command + " -------");
//
//                ClientConfig clientConfig = clientConfigCache.getClientConfigCache(clientId);
//                if (clientConfig == null) {
//                    log.info("ClientId not found !!");
//                    serverCall.close(Status.PERMISSION_DENIED, headers);
//                    return;
//                }
//
//                if (!clientConfig.getClientSecret().equals(clientSecret)) {
//                    log.info("Secret not correct !!");
//                    serverCall.close(Status.UNAUTHENTICATED, headers);
//                    return;
//                }
//
//
//                log.info("Check client service.");
//
//                Map<String, ClientService> availablePath = clientServiceCache.getClientServiceCache(clientId);
//                if (availablePath == null || !availablePath.containsKey(command)) {
//                    log.info("Client : " + clientId + " not have Permission for Path: " + command);
//                    log.info("Available with: " + Constants.GSON.toJson(availablePath));
//                    serverCall.close(Status.PERMISSION_DENIED, headers);
//                    return;
//                }
//
//                ClientService clientService = availablePath.get(command);
//
//                log.info("Check Auth Type: " + clientService.getAuthType());
//
//                if (Constants.AuthType.NONE.equals(clientService.getAuthType())) {
//                    log.info("Auth OK");
//                } else if (Constants.AuthType.RSA.equals(clientService.getAuthType())) {
//
//                    String clientPublicKey = secretVaultData.getClientRsaPublicKey().get(clientId);
//                    if (StringUtils.isEmpty(clientPublicKey)) {
//                        log.info("Client Public Key not available !!!");
//                        serverCall.close(Status.PERMISSION_DENIED, headers);
//                    }
//
//
//                    String signature = headers.get(Metadata.Key.of("Signature", ASCII_STRING_MARSHALLER));
//
//                    if (!GenericUtils.verify(message.toString(), signature, clientPublicKey)) {
////                        log.info("Body: " + body);
//                        log.info("Public key: " + clientPublicKey);
//                        log.info("Signature: " + signature);
//                        log.info("Signature Error !!!");
//                        serverCall.close(Status.UNAUTHENTICATED, headers);
//                        return;
//                    } else {
//                        log.info("RSA is OK !!!");
//                    }
//
//
//                } else {
//                    log.info("Auth Type is not setup");
//                    serverCall.close(Status.PERMISSION_DENIED, headers);
//                }
//                log.info(command + " end after: " + (System.currentTimeMillis() - begin) + " miliseconds");
//            }
//
//        };
//    }
//
//    private class GrpcServerCall<M, R> extends ServerCall<M, R> {
//
//        ServerCall<M, R> serverCall;
//
//        protected GrpcServerCall(ServerCall<M, R> serverCall) {
//            this.serverCall = serverCall;
//        }
//
//        @Override
//        public void request(int numMessages) {
//            serverCall.request(numMessages);
//        }
//
//        @Override
//        public void sendHeaders(Metadata headers) {
//            serverCall.sendHeaders(headers);
//        }
//
//        @Override
//        public void sendMessage(R message) {
//            String messageToLog = genericUtils.removeSensitiveCharacters(message.toString());
//            log.info("[{}] Response:\n{}", serverCall.getMethodDescriptor().getFullMethodName(), messageToLog);
//            serverCall.sendMessage(message);
//        }
//
//        @Override
//        public void close(Status status, Metadata trailers) {
//            serverCall.close(status, trailers);
//        }
//
//        @Override
//        public boolean isCancelled() {
//            return serverCall.isCancelled();
//        }
//
//        @Override
//        public MethodDescriptor<M, R> getMethodDescriptor() {
//            return serverCall.getMethodDescriptor();
//        }
//
//
//    }
//}
