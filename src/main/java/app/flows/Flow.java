package app.flows;

import app.converters.ElementConverter;
import app.converters.UserConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Flow {

    private final UserConverter userConverter;
    private final ElementConverter elementConverter;

    @Autowired
    public Flow(UserConverter userConverter, ElementConverter elementConverter) {
        this.userConverter = userConverter;
        this.elementConverter = elementConverter;
    }

}
