package dev.vrsek.source.compilers;

import javax.tools.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class InMemoryJavaCompiler {
	private final DefineClass defineClass;

	public InMemoryJavaCompiler() {
		this.defineClass = dev.vrsek.utils.reflect.ClassLoader::defineClass;
	}

	public InMemoryJavaCompiler(DefineClass defineClass) {
		this.defineClass = defineClass;
	}

	public Class compile(String className, String source) throws URISyntaxException {
		try {
			return compile(className, source, null);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Class compile(String className, String source, List<String> options) throws URISyntaxException, MalformedURLException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

		final JavaByteObject outputJavaObject = new JavaByteObject(className);
		StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnostics, null, null);

		try {
			try (JavaFileManager fileManager = new JavaByteObjectFileManager(outputJavaObject, standardFileManager)) {
				JavaCompiler.CompilationTask task = compiler.getTask(null,
						fileManager, diagnostics, options, null, getCompilationUnits(className, source));

				if (!task.call()) {
					diagnostics.getDiagnostics().forEach(System.out::println);
					return null;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return defineClass.defineClass(className, outputJavaObject.getBytes());
	}

	private Iterable<? extends JavaFileObject> getCompilationUnits(String className, String source) throws URISyntaxException, IOException {
		JavaStringObject stringObject = new JavaStringObject(className, source);
		return Arrays.asList(stringObject);
	}

	public interface DefineClass extends IExtendedClassLoader{
	}
}
