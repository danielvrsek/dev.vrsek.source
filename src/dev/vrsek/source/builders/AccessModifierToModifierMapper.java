package dev.vrsek.source.builders;

import dev.vrsek.source.builders.model.AccessModifier;
import dev.vrsek.utils.IMapper;

import java.lang.reflect.Modifier;

public class AccessModifierToModifierMapper implements IMapper<AccessModifier, Integer> {

	@Override
	public Integer map(AccessModifier input) {
		switch (input) {
			case PRIVATE:
				return Modifier.PRIVATE;
			case PROTECTED:
				return Modifier.PROTECTED;
			case PUBLIC:
				return Modifier.PUBLIC;
			default:
				return null;
		}
	}
}
