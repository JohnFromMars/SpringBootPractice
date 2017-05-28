package com.marsbase.springboot;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@EnableAsync
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class App extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

	/**
	 * setting tiles
	 * 
	 * @return
	 */
	@Bean
	public UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setViewClass(TilesView.class);
		return resolver;
	}

	/**
	 * setting tiles, where tile is
	 * 
	 * @return
	 */
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer configurer = new TilesConfigurer();
		String[] defs = new String[] { "/WEB-INF/tiles.xml" };
		configurer.setDefinitions(defs);
		return configurer;
	}

	@Bean
	public PasswordEncoder getEncoder() {
		return (PasswordEncoder) new BCryptPasswordEncoder();
	}

	@Bean
	public EmbeddedServletContainerCustomizer errorHandler() {
		return new EmbeddedServletContainerCustomizer() {

			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
			}
		};
	}

	@Bean
	public PolicyFactory getUserHtmlPolicy() {
		//@formatter:off
		return new HtmlPolicyBuilder()
				      .allowCommonBlockElements()
				      .allowCommonInlineFormattingElements()
				      .toFactory();
		//@formatter:on
	}

}
