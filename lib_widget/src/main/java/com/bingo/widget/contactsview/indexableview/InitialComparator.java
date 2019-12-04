package com.bingo.widget.contactsview.indexableview;

import java.util.Comparator;

/**
 * Created by zcb on 17/09/12
 */
class InitialComparator<T extends IndexableEntity> implements Comparator<EntityWrapper<T>> {
    @Override
    public int compare(EntityWrapper<T> lhs, EntityWrapper<T> rhs) {
        return lhs.getIndex().compareTo(rhs.getIndex());
    }
}
