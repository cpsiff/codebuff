package org.antlr.codebuff.validation;

import org.antlr.codebuff.*;
import org.antlr.codebuff.Formatter;
import org.antlr.codebuff.misc.LangDescriptor;
import org.antlr.v4.runtime.misc.Triple;
import org.antlr.v4.runtime.misc.Utils;

import java.io.File;
import java.util.*;

import static org.antlr.codebuff.Dbg.normalizedLevenshteinDistance;
import static org.antlr.codebuff.Tool.JAVA_CUSTOM_DESCR;
import static org.antlr.codebuff.Tool.getFilenames;
import static org.antlr.codebuff.misc.BuffUtils.filter;
import static org.antlr.codebuff.misc.BuffUtils.map;

public class CustomValidator {
	public static final String outputDir = "/tmp";
	public static final String testSetPath = "java_test_set";
	public static final LangDescriptor langDesc = JAVA_CUSTOM_DESCR;

	public static void main(String[] args) throws Exception {

		String[] styles = {"style_1", "style_2", "style_3", "style_google"};
		String[] matchingExamples = {"Example2_1.java", "Example2_2.java", "Example2_3.java", "Example2_google.java"};

		HashMap<String, Collection<Float>> allResults = new HashMap<String, Collection<Float>>();

		for (int i=0; i<styles.length; i++){
			File styleFolder = new File(testSetPath + "/" + styles[i]);
			String curExample = "corpus/java/training/custom/" + matchingExamples[i];
			ArrayList<Float> styleResults = new ArrayList<Float>();
			for (File testFile : styleFolder.listFiles()) {
			    if (testFile.getPath().endsWith(".java")) {

			    	ArrayList<String> trainingFiles = new ArrayList<String>();
			    	trainingFiles.add(curExample);
			    	List<InputDocument> exampleDoc = Tool.load(trainingFiles, langDesc);

					ArrayList<String> testingFiles = new ArrayList<String>();
					testingFiles.add(testFile.getPath());
					InputDocument curTestDoc = Tool.load(testingFiles, langDesc).get(0);

                    Triple<Formatter, Float, Float> thisResult = validate(langDesc, exampleDoc, curTestDoc, false, true);

                    styleResults.add(thisResult.c);
                }
			}
			allResults.put(style, styleResults);
		}

		printResults(allResults);
	}

	public static void printResults(HashMap<String, Collection<Float>> res){
	    res.forEach((style, accuracies) -> {
            System.out.println("Style: " + style);
            double sum = 0;
            for (Float acc : accuracies) {
                System.out.println("    " + acc);
                sum += acc;
            }
            System.out.println("    avg: " + sum / accuracies.size());
        });
    }

	public static Triple<Formatter,Float,Float> validate(LangDescriptor language,
	                                                     List<InputDocument> documents, //documents to train on (corpus)
	                                                     InputDocument testDoc,
	                                                     boolean saveOutput,
	                                                     boolean computeEditDistance)
		throws Exception
	{
//		kNNClassifier.resetCache();
		Corpus corpus = new Corpus(documents, language);
		corpus.train();
//		System.out.printf("%d feature vectors\n", corpus.featureVectors.size());
		Formatter formatter = new Formatter(corpus, language.indentSize);
		String output = formatter.format(testDoc, false);
		float editDistance = 0;
		if ( computeEditDistance ) {
			editDistance = normalizedLevenshteinDistance(testDoc.content, output);
		}
		ClassificationAnalysis analysis = new ClassificationAnalysis(testDoc, formatter.getAnalysisPerToken());
		System.out.println(testDoc.fileName+": edit distance = "+editDistance+", error rate = "+analysis.getErrorRate());
		if ( saveOutput ) {
			File dir = new File(outputDir+"/"+language.name);
			if ( saveOutput ) {
				dir = new File(outputDir+"/"+language.name);
				dir.mkdir();
			}
			Utils.writeFile(dir.getPath()+"/"+new File(testDoc.fileName).getName(), output);
		}
		return new Triple<>(formatter, editDistance, analysis.getErrorRate());
	}
}
