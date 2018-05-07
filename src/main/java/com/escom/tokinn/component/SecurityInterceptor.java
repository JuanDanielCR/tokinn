package com.escom.tokinn.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component("securityInterceptor")
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	private static final Log log = LogFactory.getLog(SecurityInterceptor.class);
	
	/**
	 * Interceptor de peticiones HTTP que permitirá a aplicar los algoritmos de autenticación
	 * antes de procesa cualquier petición HTTP.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("Aplicar algoritmo de auntenticación aquí");
		System.out.println("Aplicar algoritmo de auntenticación aquí");
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("Aplicar algoritmo de auntenticación aquí x2");
		System.out.println("Aplicar algoritmo de auntenticación aquí x2");
		super.afterCompletion(request, response, handler, ex);
	}
	
}
