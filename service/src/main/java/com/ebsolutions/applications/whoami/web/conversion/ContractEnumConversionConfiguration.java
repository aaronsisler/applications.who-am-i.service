package com.ebsolutions.applications.whoami.web.conversion;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Binds query parameters and path variables to generated contract enums by their contract value
 * ("-lastName"), not their Java constant name (LAST_NAME_DESC). Generated enums have a static
 * fromValue(String); any other enum keeps Spring's usual binding by constant name (ADR 0021).
 */
@Configuration(proxyBeanMethods = false)
class ContractEnumConversionConfiguration implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverterFactory(new ContractEnumConverterFactory());
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static final class ContractEnumConverterFactory
      implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
      Method fromValue = ReflectionUtils.findMethod(targetType, "fromValue", String.class);
      if (fromValue == null || !Modifier.isStatic(fromValue.getModifiers())) {
        return source -> source.isBlank() ? null : (T) Enum.valueOf(targetType, source.trim());
      }
      return source ->
          source.isBlank() ? null : (T) ReflectionUtils.invokeMethod(fromValue, null, source);
    }
  }
}
