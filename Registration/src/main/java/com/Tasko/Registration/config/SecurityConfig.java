package com.Tasko.Registration.config;

import com.amazonaws.auth.AWSCredentials;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.Tasko.Registration.filter.JwtAuthFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Properties;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@CrossOrigin(origins = "*")
@EnableTransactionManagement
public class SecurityConfig
{
    @Autowired
    private JwtAuthFilter authFilter;

    @Autowired
    private JwtAuthenticationEntryPoint entrypoint;

    @Bean
    public UserDetailsService userDetailsService()
    {

        return new UserInfoUserDetailsService();
    }

    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception
    {
        return https.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/createuser","/authenticate","/send-otp","/verify-otp","/reset-password","/client/authenticate"
                ,"/client/isPasswordNull","/client/SetPassword","/client/send-otp","/client/verify-otp","/authenticate/admin","/subscriptionPacks"
                ,"/api/master-admin","/save/Client_TaxProfessional_data","/savedistributor","/authenticate/Distribution","/taxreturn/pan"
                ,"/getByPan/{pan}","/TermsOfServiceDownload/{category}","/saveClientTemp","/getClientException","/authenticate/salesmanger","/register/salesmanregister"
                ,"/send-email/forcontact","/SubUsers/isPasswordNull/{pan}", "/SubUser_SetPassword","/verify_SubUserOtp","/send-otp_ToSubUser","/salesmanager/send-email/withattachment"
)
                .permitAll()
                .and()
                .cors()
                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(entrypoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }
    @Bean
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("taxkobyarkonet@gmail.com");
        mailSender.setPassword("uuapyiomphsbxppj");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Value("${aws.accessKeyId}") // Configure these properties in your application.properties or application.yml
    private String awsAccessKeyId;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}") // Specify the AWS region
    private String awsRegion;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretKey);
        return AmazonS3Client.builder()
                .withRegion(awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}