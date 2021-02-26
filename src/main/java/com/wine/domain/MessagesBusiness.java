package com.wine.domain;

public interface MessagesBusiness {
	
	public static final String INITIAL_RANGE_GREATER_THAN 	= "Faixa Inicial é maior ou igual que a faixa final!";
	public static final String FINAL_RANGE_INVALID 			= "Faixa final inválida, necessários 8 caracteres!";
	public static final String INITIAL_RANGE_INVALID 		= "Faixa inicial inválida, necessários 8 caracteres!";
	public static final String FINAL_RANGE_NULL 			= "Faixa final está nula ou vazia!";
	public static final String INITIAL_RANGE_NULL 			= "Faixa inicial está nula ou vazia!";
	public static final String INITIAL_DATA_INVALID 		= "Informar apenas números na faixa inicial";
	public static final String FINAL_DATA_INVALID 			= "Informar apenas números na faixa final";
	public static final String RANGE_NOT_ALLOWED 			= "Faixa já cadastrada para outra loja!";
	public static final String CREATE_OK_ZIP_CODE_RANGE		 = "{\"id\" : \"%s\", \"mensagem\": \"Faixa de CEP cadastrada com sucesso!\"}";
	public static final String DELETE_OK_ZIP_CODE_RANGE 	= "{\"id\" : \"%s\", \"mensagem\": \"Faixa de CEP removida com sucesso!\"}";

}
