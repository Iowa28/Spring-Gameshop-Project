package ru.kpfu.aminovniaz.project.controller.rest;

import com.google.common.base.Optional;
import io.swagger.annotations.ApiParam;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

import static springfox.documentation.swagger.annotations.Annotations.findApiParamAnnotation;
import static springfox.documentation.swagger.common.SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER;

@Component
@Order(SWAGGER_PLUGIN_ORDER)
public class SwaggerParamBuilder implements ExpandedParameterBuilderPlugin {
    @Override
    public boolean supports(DocumentationType type) {
        return DocumentationType.SWAGGER_2 == type;
    }

    @Override
    public void apply(ParameterExpansionContext context) {
        Optional<ApiParam> apiParamOptional = findApiParamAnnotation(context.getField().getRawMember());
        if (apiParamOptional.isPresent()) {
            ApiParam param = apiParamOptional.get();
            context.getParameterBuilder()
                    .name(param.name())
                    .modelRef(new ModelRef(param.type()))
                    .build();
        }
    }
}
