package dev.vrsek.source.builders;

import java.util.Collection;

public interface IClassSourceBuilder extends ISourceBuilder {
	void setClassName(String className);

	void setPackageName(String packageName);

	void addImports(String... imports);

	void addMembers(Collection<IMemberSourceBuilder> members);

	void addMembers(IMemberSourceBuilder... members);
}
