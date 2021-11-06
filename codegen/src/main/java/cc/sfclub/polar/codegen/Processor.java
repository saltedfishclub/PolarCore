package cc.sfclub.polar.codegen;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.code.Type;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.Set;
import java.util.StringJoiner;

@SupportedAnnotationTypes("cc.sfclub.polar.codegen.Object")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(javax.annotation.processing.Processor.class)
public class Processor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                var clazz = (Type.ClassType) element.asType();
                var newType = TypeSpec.classBuilder(element.getSimpleName() + "Inst")
                        .addAnnotation(GeneratedByCodegen.class);
                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.asType().getKind() == TypeKind.EXECUTABLE) {
                        if (enclosedElement.getSimpleName().charAt(0) == '<') {
                            continue;
                        }
                        System.out.println(enclosedElement.getSimpleName());
                        var method = (ExecutableType) enclosedElement.asType();
                        System.out.println(method.getReturnType().toString());
                        boolean isVoid = method.getReturnType().getKind() == TypeKind.VOID;
                        var methodDecl = MethodSpec.methodBuilder(String.valueOf(enclosedElement.getSimpleName()))
                                .returns(TypeName.get(method.getReturnType()));
                        StringBuilder a = new StringBuilder("return ");
                        if (!isVoid) {
                            int i = 0;
                            a.append("getInstance()." + enclosedElement.getSimpleName() + "(");
                            StringJoiner joiner = new StringJoiner(",");
                            for (TypeMirror parameterSpecs : method.getParameterTypes()) {
                                joiner.add("a" + i++);
                                methodDecl.addParameters(Collections.singleton(ParameterSpec.builder(TypeName.get(parameterSpecs), "a" + i, Modifier.STATIC).build()));
                            }
                            a.append(joiner.toString()).append(")");
                        }
                        a.append(";");
                        methodDecl
                                .addModifiers(Modifier.STATIC)
                                .addStatement(a.toString());
                        newType.addMethod(methodDecl.build());
                    }
                }
                System.out.println(newType.build().toString());
            }
        }
        return false;
    }
}
