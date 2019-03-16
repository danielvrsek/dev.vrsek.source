package dev.vrsek.source.builders;

import dev.vrsek.source.builders.model.AccessModifier;
import dev.vrsek.utils.IBuilder;

public interface ISourceBuilder extends IBuilder<String> {
	void setAccessModifier(AccessModifier accessModifier);
}
