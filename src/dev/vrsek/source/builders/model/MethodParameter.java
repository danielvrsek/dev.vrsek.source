package dev.vrsek.source.builders.model;

public class MethodParameter {
	private final String parameterType;
	private final String parameterName;

	public MethodParameter(String parameterType, String parameterName){
		this.parameterType = parameterType;
		this.parameterName = parameterName;
	}

	public String getParameterType() {
		return parameterType;
	}

	public String getParameterName() {
		return parameterName;
	}
}
