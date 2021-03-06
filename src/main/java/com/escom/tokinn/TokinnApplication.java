package com.escom.tokinn;

import java.security.Principal;
import javax.servlet.Filter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.services.UsuarioService;
import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.send.MessengerSendClient;

@SpringBootApplication
@EnableOAuth2Client
@RestController
@SessionAttributes("userData")
public class TokinnApplication extends WebSecurityConfigurerAdapter {
	
	private static final Log logger = LogFactory.getLog(TokinnApplication.class);
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	public static void main(String[] args) {
		SpringApplication.run(TokinnApplication.class, args);
	}
	
	/*OAuth2 Authentication configuration*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
	    http.antMatcher("/**").addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}
	
	@Bean
	@ConfigurationProperties("facebook.client")
	public AuthorizationCodeResourceDetails facebook() {
		return new AuthorizationCodeResourceDetails();
	}
	
	@Bean
	@ConfigurationProperties("facebook.resource")
	public ResourceServerProperties facebookResource() {
		return new ResourceServerProperties();
	}
	
	@Bean
	public FilterRegistrationBean<Filter> oauth2ClientFilterRegistration(
	    OAuth2ClientContextFilter filter) {
	  FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
	  registration.setFilter(filter);
	  registration.setOrder(-100);
	  return registration;
	}
	
	private Filter ssoFilter() {
		OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
		OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
		facebookFilter.setRestTemplate(facebookTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
		tokenServices.setRestTemplate(facebookTemplate);
		facebookFilter.setTokenServices(tokenServices);
		System.out.println("idFacebook: "+facebook().getClientId());
		return facebookFilter;
	}
	
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}
	
	@RequestMapping("/")
	public ModelAndView loginFacebook() {
		return new ModelAndView(NavigationConstants.CONFIRMAR_FACE);
	}
	
	/**
	 * Facebook Messenger configuration
     * Initializes the {@code MessengerSendClient}.
     * @param pageAccessToken the generated {@code Page Access Token}
     */
    @Bean
    public MessengerSendClient messengerSendClient(@Value("${messenger4j.pageAccessToken}") String pageAccessToken) {
        logger.info("Initializing MessengerSendClient - pageAccessToken: " +pageAccessToken);
        return MessengerPlatform.newSendClientBuilder(pageAccessToken).build();
    }

}
