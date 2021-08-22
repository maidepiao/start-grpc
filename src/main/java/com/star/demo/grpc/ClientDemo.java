package com.star.demo.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * @ClassName ClientDemo
 * @Description TODO
 * @Author star.hu
 * @Date 2021/8/22 14:43
 * @ModifyDate 2021/8/22 14:43
 * @Version 1.0
 */
public class ClientDemo {

    private static final Logger logger = Logger.getLogger(ClientDemo.class.getName());

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        String target = "127.0.0.1:20600";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        final GreeterGrpc.GreeterFutureStub greeterBlockingStub = GreeterGrpc.newFutureStub(channel);
        String name = "码农小麦";
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        ListenableFuture<HelloReply> reply = greeterBlockingStub.sayHello(request);
        logger.info("rpc resp: " + reply.get(3, TimeUnit.SECONDS).getMessage());
    }
}
