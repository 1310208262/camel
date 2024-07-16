package org.example.http2socket;

import java.io.*;
        import java.net.Socket;
        import java.nio.charset.StandardCharsets;
        import java.util.Scanner;
import java.util.UUID;

public class SocketClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "1.14.120.102";
            int port = 8090;
            while (true){
                Socket socket = new Socket(host, port);
                // 建立连接后获得输出流
                OutputStream outputStream = socket.getOutputStream();
                String message = System.currentTimeMillis()+"";
                System.out.println("向服务端发送消息："+message);
                //首先需要计算得知消息的长度
                byte[] sendBytes = message.getBytes("UTF-8");
                //然后将消息的长度优先发送出去
                outputStream.write(sendBytes.length >> 8);
                outputStream.write(sendBytes.length);
                //然后将消息再次发送出去
                outputStream.write(sendBytes);
                outputStream.flush();
                socket.shutdownOutput();
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[1024];
                int len;
                StringBuilder sb = new StringBuilder();
                while ((len = inputStream.read(bytes)) != -1) {
                    // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                    sb.append(new String(bytes, 0, len, "UTF-8"));
                }
                System.out.println("接收到响应消息："+ sb.toString());
                outputStream.close();
                inputStream.close();
                socket.close();
                Thread.sleep(3000);
        }
    }
}