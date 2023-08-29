package example.Interceptor;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/4/25
 */
public class TimingInterceptor {

	@RuntimeType
	public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) {
		long start = System.currentTimeMillis();
		try {
			return callable.call();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			System.out.println(method + " took " + (System.currentTimeMillis() - start));
		}
	}

}