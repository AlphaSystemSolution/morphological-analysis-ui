package com.alphasystem.morphologicalanalysis.treebank.jfx.ui.util;

import com.alphasystem.morphologicalanalysis.graph.model.DependencyGraph;
import com.alphasystem.morphologicalanalysis.graph.repository.DependencyGraphRepository;
import com.alphasystem.morphologicalanalysis.ui.util.ChapterAdapter;
import com.alphasystem.morphologicalanalysis.util.MorphologicalAnalysisRepositoryUtil;
import com.alphasystem.morphologicalanalysis.wordbyword.model.Chapter;
import com.alphasystem.morphologicalanalysis.wordbyword.model.Location;
import com.alphasystem.morphologicalanalysis.wordbyword.model.Token;
import com.alphasystem.morphologicalanalysis.wordbyword.repository.LocationRepository;
import com.alphasystem.morphologicalanalysis.wordbyword.repository.TokenRepository;
import com.alphasystem.persistence.mongo.spring.support.ApplicationContextProvider;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sali
 */
public class RepositoryTool {

    private static RepositoryTool instance;

    private MorphologicalAnalysisRepositoryUtil repositoryUtil;
    private TokenRepository tokenRepository;
    private LocationRepository locationRepository;
    private DependencyGraphRepository dependencyGraphRepository;
    private DependencyGraph currentGraph;

    /**
     * do not let anyone instantiate this class
     */
    private RepositoryTool() {
        repositoryUtil = ApplicationContextProvider
                .getInstance().getBean(MorphologicalAnalysisRepositoryUtil.class);
        tokenRepository = repositoryUtil.getTokenRepository();
        locationRepository = repositoryUtil.getLocationRepository();
        dependencyGraphRepository = repositoryUtil.getDependencyGraphRepository();

    }

    public static synchronized RepositoryTool getInstance() {
        if (instance == null) {
            instance = new RepositoryTool();
        }
        return instance;
    }

    public Service<List<ChapterAdapter>> getAllChapters() {
        return new Service<List<ChapterAdapter>>() {

            @Override
            protected Task<List<ChapterAdapter>> createTask() {
                return new Task<List<ChapterAdapter>>() {

                    @Override
                    protected List<ChapterAdapter> call() throws Exception {
                        List<ChapterAdapter> results = new ArrayList<>();
                        List<Chapter> chapters = repositoryUtil.findAllChapters();
                        for (Chapter chapter : chapters) {
                            results.add(new ChapterAdapter(chapter));
                        }
                        return results;
                    } // end of "call"
                }; // end of new "Task"
            } // end of "createTask"
        }; // end of new "Service"
    }

    public Service<List<Token>> getTokens(final int chapterNumber, final int verseNumber) {
        return new Service<List<Token>>() {
            @Override
            protected Task<List<Token>> createTask() {
                return new Task<List<Token>>() {
                    @Override
                    protected List<Token> call() throws Exception {
                        return tokenRepository.findByChapterNumberAndVerseNumber(
                                chapterNumber, verseNumber);
                    } // end of call
                }; // end of "new task"
            } // end of createTask
        }; // end of "new Service"
    }

    public Token getToken(String id) {
        return tokenRepository.findOne(id);
    }

    public Location getLocation(String id){
        return locationRepository.findOne(id);
    }

    public DependencyGraph getCurrentGraph() {
        return currentGraph;
    }

    public DependencyGraph createDependencyGraph(Integer chapterNumber, Integer verseNumber, List<Token> tokens) {
        Long count = dependencyGraphRepository.countByChapterNumberAndVerseNumber(chapterNumber, verseNumber);
        int segmentNumber = (int) (count + 1);

        DependencyGraph dependencyGraph = new DependencyGraph(chapterNumber, verseNumber, segmentNumber);
        dependencyGraph.setTokens(tokens);

        currentGraph = dependencyGraphRepository.save(dependencyGraph);
        return currentGraph;
    }

    public MorphologicalAnalysisRepositoryUtil getRepositoryUtil() {
        return repositoryUtil;
    }
}
