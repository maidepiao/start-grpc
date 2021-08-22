package com.star.demo.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @ClassName ServerDemo
 * @Description TODO
 * @Author star.hu
 * @Date 2021/8/22 14:43
 * @ModifyDate 2021/8/22 14:43
 * @Version 1.0
 */
public class ServerDemo {

    private static final Logger logger = Logger.getLogger(ServerDemo.class.getName());

    private Server server;

    private void start() throws IOException {
        int port = 20600;
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        logger.info("rpc server started!");
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final ServerDemo serverDemo = new ServerDemo();
        serverDemo.start();
        serverDemo.server.awaitTermination();
    }
}
