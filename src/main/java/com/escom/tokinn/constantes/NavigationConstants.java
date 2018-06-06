package com.escom.tokinn.constantes;

public class NavigationConstants {
	//Inicio - Login - Vistas generales
	public static final String LANDING_VIEW = "landing";
	public static final String LOGIN_VIEW = "login";
	public static final String INVERSIONES_VIEW = "inversiones";
	public static final String AFORES_VIEW = "afores";
	public static final String BASE_VIEW = "loginFacebook";
	public static final String VINCULACION = "vinculacion";

	//Usuario
	public static final String USUARIO_INDEX = "usuario_index";
	public static final String USUARIO_ADD = "usuario_registrar";
	public static final String USUARIO_EDIT = "usuario_editar";
	public static final String USUARIO_PASSWORD = "usuario_password";
	public static final String USUARIO_VIEW = "usuario_view";
	
	//Cuenta
	public static final String CUENTA_ADD = "cuenta_registrar";
	public static final String CUENTA_INDEX = "cuenta_index";
	public static final String CUENTA_VULNERABILIDAD = "cuenta_vulnerabilidad";
	public static final String CUENTA_PAGOS = "cuenta_pagos";
	public static final String CUENTA_CREAR = "cuenta_crear";
	
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
    public static final String CONFIRMAR_FACE = "confirmarFace";
    public static final String DESVINCULAR_TOKEN = "desvincular_token";
}
