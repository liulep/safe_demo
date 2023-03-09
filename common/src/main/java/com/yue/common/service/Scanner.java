package com.yue.common.service;


import com.yue.common.entity.Entry;
import com.yue.common.entity.MessageObject;

import java.util.LinkedList;

public interface Scanner {

    Entry<Entry<String[], String>, LinkedList<MessageObject>> scanRequestAnnotation(Class<?> clazz);

}
