package app.generic.web.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DataDTO<ID> {
    private ID id;
}
