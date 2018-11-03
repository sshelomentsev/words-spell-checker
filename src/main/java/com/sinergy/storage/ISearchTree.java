package com.sinergy.storage;

import com.sinergy.model.Type;

public interface ISearchTree {

    void insert(String value, Type type);

    String getMatchingPrefix(String value, Type type);

}
