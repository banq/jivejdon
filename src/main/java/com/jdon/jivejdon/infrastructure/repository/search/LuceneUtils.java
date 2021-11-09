package com.jdon.jivejdon.infrastructure.repository.search;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneUtils {
    private static Directory directory;
    private static Analyzer analyzer;

    static {
        try {
            directory = FSDirectory.open(new File("/temp/jivejdonLuceneDB"));
            analyzer = new IKAnalyzer(true);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static Directory getDirectory() {
        return directory;
    }

    public static Analyzer getAnalyzer() {
        return analyzer;
    }

}