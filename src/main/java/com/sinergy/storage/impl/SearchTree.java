package com.sinergy.storage.impl;

import com.sinergy.model.Type;
import com.sinergy.storage.ISearchTree;
import com.sinergy.structure.Trie;
import org.springframework.stereotype.Service;

@Service
public class SearchTree implements ISearchTree {

    private Trie surnames, names, thirdnames;

    public SearchTree() {
        surnames = new Trie();
        names = new Trie();
        thirdnames = new Trie();
    }

    @Override
    public void insert(String value, Type type) {
        switch (type) {
            case NAME:
                names.insert(value);
                break;
            case SURNAME:
                surnames.insert(value);
                break;
            case THIRDNAME:
                thirdnames.insert(value);
                break;
            case ALL:
                thirdnames.insert(value);
                surnames.insert(value);
                names.insert(value);
                break;
        }
    }

    @Override
    public String getMatchingPrefix(String value, Type type) {
        switch (type) {
            case NAME:
                return names.getMatchingPrefix(value);
            case SURNAME:
                return surnames.getMatchingPrefix(value);
            case THIRDNAME:
                return thirdnames.getMatchingPrefix(value);
        }
        return null;
    }
}
