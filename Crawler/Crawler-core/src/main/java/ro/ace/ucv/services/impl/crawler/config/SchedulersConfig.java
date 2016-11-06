package ro.ace.ucv.services.impl.crawler.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "ro.ace.ucv.services.impl.schedulers" })
public class SchedulersConfig {

}
