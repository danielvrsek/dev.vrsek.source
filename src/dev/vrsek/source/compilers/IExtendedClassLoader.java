package dev.vrsek.source.compilers;

public interface IExtendedClassLoader {
	Class defineClass(String name, byte[] bytes);
}
