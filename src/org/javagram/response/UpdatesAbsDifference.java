package org.javagram.response;

import org.javagram.response.object.User;
import org.telegram.api.updates.TLAbsDifference;
import org.telegram.api.updates.TLDifferenceEmpty;

import java.util.Map;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public interface UpdatesAbsDifference {

    static UpdatesAbsDifference create(TLAbsDifference tlAbsDifference/*, Map<Integer, User> users*/) {
        if(tlAbsDifference instanceof TLDifferenceEmpty) {
            return new UpdatesDifferenceEmpty((TLDifferenceEmpty)tlAbsDifference);
        } else {
            return new UpdatesDifferenceOrSlice(tlAbsDifference, null);
        }
    }
}
