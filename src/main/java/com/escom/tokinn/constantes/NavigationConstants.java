package com.escom.tokinn.constantes;

public class NavigationConstants {
	//Inicio - Login - Vistas generales
	public static final String LANDING_VIEW = "/tokinn/landing";
	public static final String LOGIN_VIEW = "/tokinn/login";
	public static final String INVERSIONES_VIEW = "/tokinn/inversiones";
	public static final String AFORES_VIEW = "/tokinn/afores";
	public static final String BASE_VIEW = "/tokinn/base";

	//Usuario
	public static final String USUARIO_INDEX = "/usuario/index";
	public static final String USUARIO_ADD = "/usuario/registrar";
	public static final String USUARIO_EDIT = "/usuario/editar";
	public static final String USUARIO_PASSWORD = "/usuario/password";
	public static final String USUARIO_VIEW = "/usuario/view";
	
	//Cuenta
	public static final String CUENTA_ADD = "/cuenta/registrar";
	public static final String CUENTA_INDEX = "/cuenta/index";
	public static final String CUENTA_VULNERABILIDAD = "/cuenta/vulnerabilidad";
	public static final String CUENTA_PAGOS = "/cuenta/pagos";
	
	//Transaccion
	public static final String TRANSACCION_ADD = "/transaccion/registrar";
	public static final String TRANSACCION_VIEW = "/transaccion/consultar";
	
	//Estado de cuenta
	public static final String ESTADO_VIEW = "/estado/consultar";
	
	//Messenger chatbot
    public static final String TOKEN_INICIO = "INICIO";
    public static final String TOKEN_ESTADO = "ESTADO_DE_CUENTA";
    public static final String TOKEN_TRANSACCION = "TRANSACCION";
    public static final Integer TOKEN_LENGTH=5;
    
}
