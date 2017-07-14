package stuff.backend.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import stuff.backend.data.option.Choice;

/**
 * Created by faiter on 6/7/17.
 */
@JsonDeserialize(as = Choice.class)
public interface Option {

    String getTitle();


}
