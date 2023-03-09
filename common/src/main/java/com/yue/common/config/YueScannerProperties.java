package com.yue.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "message")
public class YueScannerProperties {

    private String address;

    private Integer port;

    private String registerId;

    public String getAddress() {
        return address;
    }

    public Integer getPort() {
        return port;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public YueScannerProperties(String address, Integer port, String registerId) {
        this.address = address;
        this.port = port;
        this.registerId = registerId;
    }

    public YueScannerProperties() {
    }

    @Override
    public String toString() {
        return "YueScannerProperties{" +
                "address='" + address + '\'' +
                ", port=" + port +
                ", registerId='" + registerId + '\'' +
                '}';
    }
}
