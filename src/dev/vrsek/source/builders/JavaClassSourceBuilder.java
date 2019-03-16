package dev.vrsek.source.builders;

import dev.vrsek.source.builders.model.AccessModifier;
import dev.vrsek.utils.IMapper;
import dev.vrsek.utils.ISourceFormatter;
import dev.vrsek.utils.exceptions.ValidationException;
import dev.vrsek.utils.validators.IValidator;
import dev.vrsek.utils.validators.NotNullObjectValidator;
import dev.vrsek.utils.validators.NotNullOrEmptyStringValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JavaClassSourceBuilder implements IClassSourceBuilder {
	private final ISourceFormatter sourceFormatter;
	private final IMapper<AccessModifier, String> accessModifierStringMapper;

	private AccessModifier accessModifier;
	private String className;
	private List<IMemberSourceBuilder> members;
	private String packageName;
	private List<String> imports;

	private IValidator[] validators;

	public JavaClassSourceBuilder(ISourceFormatter sourceFormatter, IMapper<AccessModifier, String> accessModifierStringMapper) {
		this.sourceFormatter = sourceFormatter;
		this.accessModifierStringMapper = accessModifierStringMapper;

		this.accessModifier = AccessModifier.PUBLIC;
		this.members = new ArrayList<>();
		this.imports = new ArrayList<>();

		initializeValidators();
	}

	private void initializeValidators() {
		validators = new IValidator[] {
				new NotNullOrEmptyStringValidator(() -> className, "Name of the class cannot be empty or null."),
				new NotNullObjectValidator(() -> accessModifier, "Access modifier cannot be null."),
				new NotNullObjectValidator(() -> members),
				new NotNullObjectValidator(() -> imports)
		};
	}

	public String getClassName() {
		return className;
	}

	@Override
	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public void setAccessModifier(AccessModifier accessModifier) {
		this.accessModifier = accessModifier;
	}

	@Override
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public void addImports(String... imports) {
		Collections.addAll(this.imports, imports);
	}

	@Override
	public void addMembers(Collection<IMemberSourceBuilder> members) {
		this.members.addAll(members);
	}

	@Override
	public void addMembers(IMemberSourceBuilder... members) {
		Collections.addAll(this.members, members);
	}

	@Override
	public String build() throws ValidationException {
		validate();

		StringBuilder sourceBuilder = new StringBuilder();

		sourceBuilder.append(serializePackageName());
		sourceBuilder.append(serializeImports());
		sourceBuilder.append(serializeClassSignature());
		sourceBuilder.append("{");
		sourceBuilder.append(serializeMembers());
		sourceBuilder.append("}");

		String plainSource = sourceBuilder.toString();

		return sourceFormatter.format(plainSource);
	}

	private void validate() throws ValidationException {
		for (IValidator validator : validators) {
			validator.validate();
		}
	}

	private String serializeClassSignature() {
		assert className != null && !className.isEmpty();
		String accessModifierString = accessModifierStringMapper.map(accessModifier);
		assert accessModifierString != null && !className.isEmpty();

		return String.format("%s class %s", accessModifierString, className);
	}

	private String serializeMembers() throws ValidationException {
		StringBuilder membersBuilder = new StringBuilder();

		for (var member : members) {
			membersBuilder.append(member.build());
		}

		return membersBuilder.toString();
	}

	private String serializeImports() {
		StringBuilder importsBuilder = new StringBuilder();

		for (String imp : imports) {
			importsBuilder.append("import ").append(imp).append(";");
		}

		return importsBuilder.toString();
	}

	private String serializePackageName() {
		if (packageName != null) {
			return "package " + packageName + ";";
		}

		return "";
	}
}
