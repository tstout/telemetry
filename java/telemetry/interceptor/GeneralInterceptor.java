package telemetry.interceptor;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.*;

// Here's an example of creating a transformer:
// https://github.com/YoannBuch/bytebuddysandbox/blob/master/src/test/java/test/bytebuddy/ReproTest.java
//
// See this for some additional things which probably need to be implemented:
// https://github.com/raphw/byte-buddy/issues/110
//
public class GeneralInterceptor {
    @RuntimeType public Object intercept(
            @AllArguments Object[] allArguments, @Origin Method method, @SuperCall Callable<?> callable) {

        System.out.println("Method Intercepted !!!!!!!! -------> " + method);
        onEnter(method, allArguments);
        Object ret = null;
        try {
            ret = callable.call();
        } catch (Exception e) {
            onException(method, allArguments, e);
        }

        onExit(method, allArguments, ret);
        return ret;
    }

    public static class MyGeneralInterceptor {
        @Advice.OnMethodEnter
        public static void enter(@Advice.Origin Method method, @Advice.This Object thiz) {
            System.out.println("Intercepted Normal >> " + method);
        }
    }


    public static void configAgent(Instrumentation inst, Class interceptor) {
        new AgentBuilder.Default()
                .with(new DebugListener())
                .ignore(nameStartsWith("net.bytebuddy.")
                        .or(nameStartsWith("sun.reflect"))
                        .or(nameStartsWith("org.apache.log4j"))
                        .or(nameStartsWith("sun.misc")))
                .type(ElementMatchers.nameContains("com.containerstore"))
                .transform((builder, td, cl, m) -> builder.visit(Advice.to
                (MyGeneralInterceptor.class)
                        .on(not(isStatic()).and(not(isConstructor())))))
                .installOn(inst);

        System.out.println("Agent Installed....");
    }

    protected void onException(Method m, Object[] args, Exception e) {
    }

    protected void onEnter(Method m, Object[] args) {
        System.out.println("-----ON Enter " + m);
    }

    protected Object onExit(Method m, Object[] args, Object returnVal) {
        return null;
    }


    static class DebugListener implements AgentBuilder.Listener {

        @Override
        public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                boolean loaded, DynamicType dynamicType) {
            System.out.println(String.format("onTransformation %s", typeDescription));

        }

        @Override
        public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean
                loaded) {
            //System.out.println("onIgnored " + typeDescription);
        }

        @Override
        public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable
                throwable) {
            System.out.println("----!!!! onError " + typeName + throwable.getMessage());
        }

        @Override public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
            //System.out.println("onComplete " + typeName);
        }
    }
}

