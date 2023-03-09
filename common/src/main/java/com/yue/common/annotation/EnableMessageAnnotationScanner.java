package com.yue.common.annotation;
import com.yue.common.config.YueScannerProperties;
import com.yue.common.launch.AnnotationScannerSelector;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(AnnotationScannerSelector.class)
@EnableConfigurationProperties(YueScannerProperties.class)
public @interface EnableMessageAnnotationScanner {


}
