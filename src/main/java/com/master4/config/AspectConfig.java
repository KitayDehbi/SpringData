package com.master4.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.master4.aspects")
@EnableAspectJAutoProxy
public class AspectConfig {
}
