package com.escom.tokinn.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.repository.CuentaRepository;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@Service("cuentaService")
public class CuentaService {
	@Autowired
	@Qualifier("cuentaRepository")
	private CuentaRepository cuentaRepository; 
	
	@Autowired
	@Qualifier("tokenService")
	private TokenService tokenService;
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public CuentaService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public int notificarRegistro(Usuario entidad) {
		//Generación del token de vinculación
		String tokenVinculacion = "vink_";
		if(entidad.getHasToken() == Boolean.TRUE && entidad.getIdFacebook() != null) {
			tokenVinculacion += tokenService.getHash(entidad.getIdFacebook())
					.substring(0, NavigationConstants.TOKEN_LENGTH).toUpperCase();
		}
		System.out.println("token_vinculacion: "+tokenVinculacion);
		//Thread safe
		MimeMessagePreparator preparator = getMessagePreparator(entidad, tokenVinculacion);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(entidad.getEmail());
	    msg.setFrom("escomcrypto@gmail.com");
	    msg.setSubject("Bienvenida Tokinn");
	    msg.setText("Hola: " + entidad.getNombre());
	        try{
	            javaMailSender.send(preparator);
	        }
	        catch (MailException ex) {
	            // simply log it and go on...
	            System.err.println(ex.getMessage());
	        }
		return 0;
	} 
	private MimeMessagePreparator getMessagePreparator(final Usuario entidad, String tokenVinculacion){
		 MimeMessagePreparator preparator = new MimeMessagePreparator() {
		     public void prepare(MimeMessage mimeMessage) throws Exception {
		    	 MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		         helper.setSubject("Tokinn vinculación");
		         helper.setFrom("escomcrypto@gmail.com");
		         helper.setTo(entidad.getEmail());
		         Map<String, Object> model = new HashMap<String, Object>();
		         System.out.println("nomnre: "+entidad.getNombre()+" tokenVinculacion: "+tokenVinculacion+" entidad.getEmail(): "+entidad.getEmail());
		         model.put("nombre", entidad.getNombre());
		         model.put("contra", tokenVinculacion);
		         model.put("correo", entidad.getEmail());
		         System.out.println("obteniendo template");
		         String text = getFreeMarkerTemplateContent(model);//Use Freemarker or Velocity
		         //use the true flag to indicate you need a multipart message
		         helper.setText(text, true);
		    }
	    };
	    return preparator;
	}
	public String getFreeMarkerTemplateContent(Map<String, Object> model) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException{
        StringBuffer content = new StringBuffer();
        @SuppressWarnings("deprecation")
		Configuration configuration= new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), "/email/");
        Template template = configuration.getTemplate("vinculacion.ftl");
        try{
         content.append(FreeMarkerTemplateUtils.processTemplateIntoString(template, model));
         return content.toString();
        }catch(Exception e){
            System.out.println("Exception occured while processing fmtemplate:"+e.getMessage());
        }
        return "";
    }
}
