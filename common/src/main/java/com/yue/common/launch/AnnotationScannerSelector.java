package com.yue.common.launch;

import com.alibaba.fastjson.JSON;
import com.yue.common.annotation.EnableMessageAnnotationScanner;
import com.yue.common.config.YueScannerProperties;
import com.yue.common.entity.Entry;
import com.yue.common.entity.MessageBody;
import com.yue.common.entity.MessageObject;
import com.yue.common.entity.TransformBody;
import com.yue.common.service.ClassMethodsRequestScanner;
import com.yue.common.service.MessageServerConnection;
import com.yue.common.service.Scanner;
import com.yue.common.utils.ParseMessageGenerateFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Objects;


@EnableConfigurationProperties(YueScannerProperties.class)
public class AnnotationScannerSelector  implements  ApplicationListener<ContextRefreshedEvent>{
    private static final String BINARY_PATH = "\\target\\classes";
    private static final String ROOT_PATH = System.getProperty("user.dir");

    @Autowired
    private YueScannerProperties properties;


    public void run(){
        Scanner scanner = new ClassMethodsRequestScanner();
        LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list = new LinkedList<>();
        launch(new File(ROOT_PATH +"\\mysql_demo"+ BINARY_PATH), "", scanner, list);
        if (list.size() > 0) sendMessage(list);
    }



    private void launch(File file, String path, Scanner scanner,
                        LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list) {
        if (!file.exists()) {
            throw new RuntimeException(file.getName() + "is not exits");
        }
        for (File fileItem : Objects.requireNonNull(file.listFiles())) {
            String fileName = fileItem.getName();
            if (fileName.endsWith(".class")) {
                String classPath = path + "." + fileName.substring(0, fileName.lastIndexOf("."));
                Entry<Entry<String[], String>, LinkedList<MessageObject>> content = loadClass(classPath, scanner);
                if (content != null) list.add(content);
            }
            if (fileItem.isDirectory()) launch(fileItem, path + (path.equals("") ? "" : ".") + fileName, scanner, list);
        }
    }


    private Entry<Entry<String[], String>, LinkedList<MessageObject>> loadClass(String path, Scanner scanner) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return scanner.scanRequestAnnotation(clazz);
    }


    private void sendMessage(LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list) {
        MessageServerConnection messageServerConnection =readApplicationFile();
        if (messageServerConnection.getAddress().equals("127.0.0.1") || messageServerConnection.getAddress().equals("localhost")) {
            ParseMessageGenerateFile.generatorFile(list);
            return;
        }
        String registerID = properties.getRegisterId();
        if (registerID == null)
            throw new RuntimeException("请先获取注册码，未获取注册码的情况下上传项目可能会被打回");
        try (Socket clientSocket = new Socket(messageServerConnection.getAddress(), messageServerConnection.getPort());
             OutputStream outputStream = clientSocket.getOutputStream()) {
            TransformBody transformBody = new TransformBody(registerID, generateMessageBody(list));
            outputStream.write(JSON.toJSONString(transformBody).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Value("${server.servlet.context-path:''}")
    private String context_path;


    private MessageBody generateMessageBody(LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list) {
        if (properties == null) {
            return new MessageBody("", 8080, list);
        }
        String port = properties.getPort()+"", contextPath = context_path;
        if (contextPath == null) contextPath = "";
        if (port == null) port = "8080";
        return new MessageBody(contextPath, Integer.parseInt(port), list);
    }


    private MessageServerConnection readApplicationFile() {
        if (properties == null)
            return new MessageServerConnection();
        String address = properties.getAddress();
        String port = properties.getPort()+"";
        MessageServerConnection messageServerConnection = new MessageServerConnection();
        if (address != null && address .length() != 0 &&
                port != null && port .length() != 0) {
            messageServerConnection.setAddress(address);
            try {
                messageServerConnection.setPort(Integer.parseInt(port));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("端口号不是数组，port=" + port);
            }
        }
        return messageServerConnection;
    }

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        this.run();
//    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.run();
    }
}
