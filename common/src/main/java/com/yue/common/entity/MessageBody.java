package com.yue.common.entity;

import java.io.Serializable;
import java.util.LinkedList;

public class MessageBody implements Serializable {
    private static final long serialVersionUID = 1L;

    private Project project;
    private LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list;

    public MessageBody(String contextPath, int port,  LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list) {
        project = new Project(contextPath, port);
        System.out.println(project.toString());
        this.list = list;
    }

    public MessageBody() {}

    public Project getProject() {
        return project;
    }

    public void setProject(String contextPath, int port) {
        this.project = new Project(contextPath, port);
    }

    public LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> getList() {
        return list;
    }

    public void setList(LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MessageBody{" +
                "project=" + project +
                ", list=" + list +
                '}';
    }
}
