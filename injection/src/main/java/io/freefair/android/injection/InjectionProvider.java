package io.freefair.android.injection;

public interface InjectionProvider
{
	boolean canProvide(Class<?> clazz);
	<T> T provide(Class<? super T> clazz, Object instance, Injector injector);
}
