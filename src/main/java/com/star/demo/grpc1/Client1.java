package com.star.demo.grpc1;

import com.google.common.util.concurrent.ListenableFuture;
import com.star.demo.grpc.ClientDemo;
import com.star.demo.grpc.GreeterGrpc;
import com.star.demo.grpc.HelloReply;
import com.star.demo.grpc.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class Client1 {

    private static final Logger logger = Logger.getLogger(ClientDemo.class.getName());

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        String target = "127.0.0.1:20600";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        PrinterGrpc.PrinterBlockingStub printerBlockingStub = PrinterGrpc.newBlockingStub(channel);
        String name = "码农小麦";
        int age = 25;
        List<String> tags = Arrays.asList("eat","drink","play");
        CmxReq request = CmxReq.newBuilder().setName(name).setAge(age).addAllTags(tags).build();
        final CmxResp info = printerBlockingStub.getInfo(request);
        logger.info("rpc resp: " + info.getName());
        info.getHobbysList().stream().forEach(System.out::println);
    }
}
