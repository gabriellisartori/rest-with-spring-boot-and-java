package io.github.gabriellisartori.class11_above.serializer.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public final class YamlJacksonToHttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    protected YamlJacksonToHttpMessageConverter() {
        super(new YAMLMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL),
                MediaType.parseMediaType("application/yaml"));
    }
}
