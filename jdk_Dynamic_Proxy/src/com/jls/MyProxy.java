import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 自实现动态代理
 *
 * @author zhangkuan
 * @email xianjian-mail@qq.com
 * @Date 2019/1/1 15:17
 */
public class MyProxy {

    /**
     * 换行符
     */
    private static final String LN = "\r\n";
    /**
     * 生成的代理类的名称，这里为了方便就不生成了，直接字符串简单定义一下
     */
    private static final String SRC_NAME = "$GuituProxy0";
    /**
     * 生成的代理类的包名，同样为了测试方便直接定义成字符串
     */
    private static final String PACKAGE_NAME = "com.guitu18.study.proxy.guitu";

    /**
     * 生成并返回一个代理对象
     *
     * @param classLoader       自实现的类加载器
     * @param interfaces             被代理类所实现的所有接口
     * @param invocationHandler 一个{@link GuituInvocationHandler}接口的实现
     *                               我们代理类对其代理的对象增强的代码写在对该接口的实现中
     *                               {@link GuituProxy#invoke(Object, Method, Object[])}
     * @return 返回生成的代理对象
     */
    public static Object newProxyInstance(MyClassLoader classLoader,
                                          Class<?>[] interfaces,
                                          MyInvocationHandler invocationHandler) {
        try {
            // 1.动态生成源代码.java文件并写入到磁盘
            File file = generateSrcToFile(interfaces);

            // 2.把生成的.java文件编译成.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manage = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = manage.getJavaFileObjects(file);
            JavaCompiler.CompilationTask task =
                    compiler.getTask(null, manage, null, null, null, iterable);
            task.call();
            manage.close();

            // 3.把编译的.class文件加载到JVM
            Class proxyClass = classLoader.findClass(SRC_NAME);
            Constructor constructor = proxyClass.getConstructor(MyInvocationHandler.class);

            // 4.返回动态生成的代理对象
            return constructor.newInstance(invocationHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 这里仅为理解原理和学习，代码生成简单有效即可
     *
     * @param interfaces 被代理类所实现的所有接口
     * @return 返回生成的源代码的File对象
     */
    private static File generateSrcToFile(Class<?>[] interfaces) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("package " + PACKAGE_NAME + ";" + LN);
            sb.append("import java.lang.reflect.Method;" + LN);

            /**
             * 实现所有接口
             */
            StringBuffer interfaceStr = new StringBuffer();
            for (int i = 0; i < interfaces.length; i++) {
                interfaceStr.append(interfaces[i].getName());
                if (interfaces.length > 1 && i < interfaces.length - 2) {
                    interfaceStr.append(",");
                }
            }
            sb.append("public class " + SRC_NAME + " implements " + interfaceStr.toString() + " {" + LN);
            sb.append("    GuituInvocationHandler guituInvocationHandler;" + LN);
            sb.append("    public " + SRC_NAME + "(GuituInvocationHandler guituInvocationHandler) { " + LN);
            sb.append("        this.guituInvocationHandler = guituInvocationHandler;" + LN);
            sb.append("    }" + LN);

            /**
             * 实现所有接口的所有方法
             */
            for (Class<?> anInterface : interfaces) {
                for (Method method : anInterface.getMethods()) {
                    // 方法形参数组
                    Parameter[] parameters = method.getParameters();
                    // 方法方法形参，类型 名称 字符串
                    StringBuffer paramStr = new StringBuffer();
                    // 方法形参类型字符串
                    StringBuffer paramTypeStr = new StringBuffer();
                    // 方法形参名称字符串
                    StringBuffer paramNameStr = new StringBuffer();
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        // 拼接方法形参，类型 名称
                        paramStr.append(parameter.getType().getName() + " " + parameter.getName());
                        // 拼接方法形参类型，供反射调用
                        paramTypeStr.append(parameter.getType().getName()).append(".class");
                        // 拼接方法形参名称，供反射调用
                        paramNameStr.append(parameter.getName());
                        if (parameters.length > 1 && i < parameters.length - 2) {
                            sb.append(", ");
                            paramTypeStr.append(",");
                            paramNameStr.append(", ");
                        }
                    }
                    // 生成方法
                    String returnTypeName = method.getReturnType().getName();
                    sb.append("    public " + returnTypeName + " " + method.getName() + "(" + paramStr.toString() + ") {" + LN);
                    sb.append("        try{" + LN);
                    sb.append("            Method method = " + interfaces[0].getName() +
                            ".class.getMethod(\"" + method.getName() + "\",new Class[]{" + paramTypeStr.toString() + "});" + LN);
                    // 判断方法是否有返回值
                    if (!"void".equals(returnTypeName)) {
                        sb.append("            " + returnTypeName +
                                " invoke = (" + returnTypeName + ")this.guituInvocationHandler.invoke(this, method, new Object[]{"
                                + paramNameStr.toString() + "});" + LN);
                        sb.append("            return invoke;" + LN);
                    } else {
                        sb.append("            this.guituInvocationHandler.invoke(this, method, null);" + LN);
                    }
                    sb.append("        }catch(Throwable e){" + LN);
                    sb.append("            e.printStackTrace();" + LN);
                    sb.append("        }" + LN);
                    if (!"void".equals(method.getReturnType().getName())) {
                        sb.append("        return null;" + LN);
                    }
                    sb.append("    }" + LN);
                }
            }
            sb.append("}" + LN);

            // 将生成的字节码写入到磁盘文件
            String path = MyProxy.class.getResource("").getPath();
            System.out.println(path);
            File file = new File(path + SRC_NAME + ".java");
            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.flush();
            fw.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
