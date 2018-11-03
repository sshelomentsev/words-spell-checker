package com.sinergy.service.impl;

import com.sinergy.alg.MutationAlg;
import com.sinergy.model.Entry;
import com.sinergy.model.Result;
import com.sinergy.model.Type;
import com.sinergy.service.IProcessing;
import com.sinergy.storage.IExtendedStorage;
import com.sinergy.storage.ISearchTree;
import com.sinergy.storage.impl.DictionaryStorage;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Processing implements IProcessing {

    @Autowired
    @Qualifier("surnamesStorage")
    private IExtendedStorage surnamesStorage;

    @Autowired
    @Qualifier("namesStorage")
    private IExtendedStorage namesStorage;

    @Autowired
    @Qualifier("thirdNamesStorage")
    private IExtendedStorage thirdNamesStorage;

    @Autowired
    private DictionaryStorage dictionaryStorage;

    @Autowired
    private ISearchTree searchTree;

    @Override
    public Result processEntry(Entry entry) {
        Result ret = new Result(entry.getId());

        Triplet<Integer, String, String> s = processSurname(entry.getSurname(), entry.getCountry());
        Triplet<Integer, String, String> n = processName(entry.getSurname(), entry.getCountry());
        Triplet<Integer, String, String> t = processthirdName(entry.getSurname(), entry.getCountry());
        ret.setSurname(s.getValue1());
        ret.setSurnameOrdinal(s.getValue0());
        ret.setName(n.getValue1());
        ret.setNameOrdinal(n.getValue0());
        ret.setThirdName(n.getValue1());
        ret.setThirdNameOrdinal(n.getValue0());

        return ret;
    }

    /**
     *
     * @param surname
     * @return
     *      ordinal, correct value (or null), suffix
     */
    public Triplet<Integer, String, String> processSurname(String surname, String country) {
        String ret = null;
        String prefix = searchTree.getMatchingPrefix(surname, Type.SURNAME);
        String suffix = null;
        int ordinal = -1;

        if (prefix.isEmpty()) {
            String res = processMutations(surname, surnamesStorage, country);
            if (null != res) {
                ordinal = 1;
                ret = res;
            } else {
                if (null != surnamesStorage.getCorrectWord(surname)) {
                    ret = surnamesStorage.getCorrectWord(surname);
                    ordinal = 1;
                } else if (dictionaryStorage.hasWord(surname) || null != processMutationsForDictionary(surname)) {
                    ordinal = 2;
                } else {
                    ordinal = 1;
                    //TODO normalization
                }
            }
        } else if (prefix.equals(surname)) {
            ordinal = 1;
            ret = surname;
        } else {
            if (null != surnamesStorage.getCorrectWord(prefix)) {
                ordinal = 1;
                ret = surnamesStorage.getCorrectWord(prefix);
                suffix = surname.substring(prefix.length());
            } else if (null != surnamesStorage.getCorrectWord(surname)) {
                ordinal = 1;
                ret = surnamesStorage.getCorrectWord(surname);
            } else {
                String res = processMutations(surname, surnamesStorage, country);
                if (null != res) {
                    ordinal = 1;
                    ret = res;
                } else {
                    ordinal = 2;
                }
            }
        }
        return new Triplet<>(ordinal, ret, suffix);
    }

    public Triplet<Integer, String, String> processName(String name, String country) {
        String ret = null;
        String prefix = searchTree.getMatchingPrefix(name, Type.NAME);
        String suffix = null;
        int ordinal = -1;

        if (prefix.isEmpty()) {
            String res = processMutations(name, namesStorage, country);
            if (null != res) {
                ordinal = 1;
                ret = res;
            } else {
                if (null != namesStorage.getCorrectWord(name)) {
                    ret = namesStorage.getCorrectWord(name);
                    ordinal = 1;
                } else if (dictionaryStorage.hasWord(name) || null != processMutationsForDictionary(name)) {
                    ordinal = 2;
                } else {
                    ordinal = 1;
                    //TODO normalization
                }
            }
        } else if (prefix.equals(name)) {
            ordinal = 1;
            ret = name;
        } else {
            if (null != namesStorage.getCorrectWord(prefix)) {
                ordinal = 1;
                ret = namesStorage.getCorrectWord(prefix);
                suffix = name.substring(prefix.length());
            } else if (null != namesStorage.getCorrectWord(name)) {
                ordinal = 1;
                ret = namesStorage.getCorrectWord(name);
            } else {
                String res = processMutations(name, namesStorage, country);
                if (null != res) {
                    ordinal = 1;
                    ret = res;
                } else {
                    ordinal = 2;
                }
            }
        }
        return new Triplet<>(ordinal, ret, suffix);
    }

    public Triplet<Integer, String, String> processthirdName(String thirdName, String country) {
        String ret = null;
        String prefix = searchTree.getMatchingPrefix(thirdName, Type.THIRDNAME);
        String suffix = null;
        int ordinal = -1;

        if (prefix.isEmpty()) {
            String res = processMutations(thirdName, thirdNamesStorage, country);
            if (null != res) {
                ordinal = 1;
                ret = res;
            } else {
                if (null != thirdNamesStorage.getCorrectWord(thirdName)) {
                    ret = thirdNamesStorage.getCorrectWord(thirdName);
                    ordinal = 1;
                } else if (dictionaryStorage.hasWord(thirdName) || null != processMutationsForDictionary(thirdName)) {
                    ordinal = 2;
                } else {
                    ordinal = 1;
                    //TODO normalization
                }
            }
        } else if (prefix.equals(thirdName)) {
            ordinal = 1;
            ret = thirdName;
        } else {
            if (null != thirdNamesStorage.getCorrectWord(prefix)) {
                ordinal = 1;
                ret = thirdNamesStorage.getCorrectWord(prefix);
                suffix = thirdName.substring(prefix.length());
            } else if (null != thirdNamesStorage.getCorrectWord(thirdName)) {
                ordinal = 1;
                ret = thirdNamesStorage.getCorrectWord(thirdName);
            } else {
                String res = processMutations(thirdName, thirdNamesStorage, country);
                if (null != res) {
                    ordinal = 1;
                    ret = res;
                } else {
                    ordinal = 2;
                }
            }
        }
        return new Triplet<>(ordinal, ret, suffix);
    }



    private String processMutations(String value, IExtendedStorage storage, String country) {
        Set<String> mutations = MutationAlg.getMutations(value);
        List<String> res = mutations
                .stream()
                .filter(storage::isCorrect)
                .collect(Collectors.toList());

        if (!res.isEmpty()) {
            if (1 == res.size()) {
                return res.get(0);
            }
            return res.get(0);
            //TODO work with countries
            //return storage.getByCountry(res, country);
        }
        return null; //no option found
    }

    private String processMutationsForDictionary(String value) {
        Set<String> mutations = MutationAlg.getMutations(value);
        List<String> res = mutations
                .stream()
                .filter(mutation -> dictionaryStorage.hasWord(mutation))
                .collect(Collectors.toList());

        if (!res.isEmpty()) {
            return res.get(0);
        }
        return null; //no option found
    }

}
