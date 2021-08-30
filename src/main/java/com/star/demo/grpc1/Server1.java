package com.star.demo.grpc1;

import com.star.demo.grpc.GreeterGrpc;
import com.star.demo.grpc.HelloReply;
import com.star.demo.grpc.HelloRequest;
import com.star.demo.grpc.ServerDemo;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Server1 {

    private static final Logger logger = Logger.getLogger(ServerDemo.class.getName());

    private Server server;

    private void start() throws IOException {
        int port = 20600;
        server = ServerBuilder.forPort(port)
                .addService(new Server1.PrinterImpl())
                .build()
                .start();
        logger.info("rpc server started!");
    }

    static class PrinterImpl extends PrinterGrpc.PrinterImplBase {
        @Override
        public void getInfo(CmxReq request, StreamObserver<CmxResp> responseObserver){
            request.getTagsList().stream().forEach(System.out::println);
            List<String> tags = Arrays.asList("sing","dance","act","talk","laugh");
            CmxResp resp = CmxResp.newBuilder().setName("star").addAllHobbys(tags).build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final Server1 server1 = new Server1();
        server1.start();
        server1.server.awaitTermination();
    }
}
