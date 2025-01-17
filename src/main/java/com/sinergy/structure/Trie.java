package com.sinergy.structure;

import java.util.HashMap;

public class Trie {

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode((char) 0);
    }

    public void insert(String word) {
        int len = word.length();
        TrieNode crawl = root;

        for (int level = 0; level < len; level++) {
            HashMap<Character,TrieNode> child = crawl.getChildren();
            char ch = word.charAt(level);

            if (child.containsKey(ch)) {
                crawl = child.get(ch);
            } else {
                TrieNode tmp = new TrieNode(ch);
                child.put(ch, tmp);
                crawl = tmp;
            }
        }

        crawl.setIsEnd(true);
    }

    public String getMatchingPrefix(String input) {
        String result = ""; // Initialize resultant string
        int length = input.length();  // Find length of the input string

        // Initialize reference to traverse through Trie
        TrieNode crawl = root;

        // Iterate through all characters of input string 'str' and traverse
        // down the Trie
        int level, prevMatch = 0;
        for( level = 0 ; level < length; level++ )
        {
            // Find current character of str
            char ch = input.charAt(level);

            // HashMap of current Trie node to traverse down
            HashMap<Character,TrieNode> child = crawl.getChildren();

            // See if there is a Trie edge for the current character
            if( child.containsKey(ch) )
            {
                result += ch;          //Update result
                crawl = child.get(ch); //Update crawl to move down in Trie

                // If this is end of a word, then update prevMatch
                if( crawl.isEnd() )
                    prevMatch = level + 1;
            }
            else  break;
        }

        // If the last processed character did not match end of a word,
        // return the previously matching prefix
        if( !crawl.isEnd() )
            return result.substring(0, prevMatch);

        else return result;
    }

}
