package tdpay.mvc.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@EnableAsync
@EnableScheduling
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR)
                .setAmbiguityIgnored(true);
        return modelMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory clientRequestFactory = new HttpComponentsClientHttpRequestFactory();
        // set the read timeot, this value is in miliseconds
        clientRequestFactory.setConnectTimeout(3000);
        clientRequestFactory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(clientRequestFactory);
        return restTemplate;
    }

}
