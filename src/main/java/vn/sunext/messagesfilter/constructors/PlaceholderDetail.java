package vn.sunext.messagesfilter.constructors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceholderDetail {

    String filterName;

    public PlaceholderDetail(String filterName) {
        this.filterName = filterName;
    }

}
