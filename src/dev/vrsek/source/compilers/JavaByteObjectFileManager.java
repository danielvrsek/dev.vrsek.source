package dev.vrsek.source.compilers;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.IOException;

public class JavaByteObjectFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
	private final JavaByteObject byteObject;

	protected JavaByteObjectFileManager(JavaByteObject byteObject, StandardJavaFileManager fileManager) {
		super(fileManager);
		this.byteObject = byteObject;
	}

	/*@Override
	public java.lang.ClassLoader getClassLoader(Location location) {
		// TODO: customizable class loader
		return ClassLoader.getInstance();
	}*/

	@Override
	public JavaFileObject getJavaFileForOutput(Location location,
											   String className, JavaFileObject.Kind kind,
											   FileObject sibling) throws IOException {
		return byteObject;
	}

	@Override
	public FileObject getFileForOutput(Location location, String packageName, String relativeName, FileObject sibling) throws IOException {
		return byteObject;
	}
}
