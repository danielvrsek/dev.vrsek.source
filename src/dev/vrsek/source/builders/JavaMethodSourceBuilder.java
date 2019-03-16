package dev.vrsek.source.builders;

import dev.vrsek.source.builders.model.AccessModifier;
import dev.vrsek.source.builders.model.MethodParameter;
import dev.vrsek.utils.IMapper;

public class JavaMethodSourceBuilder implements IMemberSourceBuilder {
	private final IMapper<AccessModifier, String> accessModifierStringMapper;

	private AccessModifier accessModifier;
	private String typeName;
	private String name;
	private MethodParameter[] parameters;
	private String[] body;

	public JavaMethodSourceBuilder(IMapper<AccessModifier, String> accessModifierStringMapper){
		this.accessModifierStringMapper = accessModifierStringMapper;

		initializeDefaultValues();
	}

	private void initializeDefaultValues(){
		accessModifier = AccessModifier.PUBLIC;
		this.body = new String[0];
		this.parameters = new MethodParameter[0];
	}

	@Override
	public void setAccessModifier(AccessModifier accessModifier) {
		this.accessModifier = accessModifier;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setParameters(MethodParameter[] parameters){
		this.parameters = parameters;
	}

	public void setBody(String[] body){
		this.body = body;
	}

	@Override
	public String build() {
		StringBuilder methodSourceBuilder = new StringBuilder();

		methodSourceBuilder.append(serializeSignature());
		methodSourceBuilder.append("{");
		methodSourceBuilder.append(serializeBody());
		methodSourceBuilder.append("}");

		return methodSourceBuilder.toString();
	}

	private String serializeBody() {
		StringBuilder stringBuilder = new StringBuilder();

		for (String line : body) {
			stringBuilder.append(line);
		}

		return stringBuilder.toString();
	}

	private String serializeSignature() {
		String accessModifierString = accessModifierStringMapper.map(accessModifier);
		String parametersSerialized = serializeParameters();

		return String.format("%s %s %s(%s)", accessModifierString, typeName, name, parametersSerialized);
	}

	private String serializeParameters() {
		StringBuilder parametersBuilder = new StringBuilder();

		for (int i = 0; i < parameters.length; i++) {
			parametersBuilder.append(String.format("%s %s", parameters[i].getParameterType(), parameters[i].getParameterName()));

			if (i + 1 < parameters.length) {
				parametersBuilder.append(", ");
			}
		}

		return parametersBuilder.toString();
	}
}
