package org.antlr.codebuff.validation;

import com.google.gson.Gson;
import org.antlr.codebuff.*;
import org.antlr.codebuff.Formatter;
import org.antlr.codebuff.misc.LangDescriptor;
import org.antlr.v4.runtime.misc.Triple;
import org.antlr.v4.runtime.misc.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.antlr.codebuff.Dbg.normalizedLevenshteinDistance;
import static org.antlr.codebuff.Tool.JAVA_CUSTOM_DESCR;

/**
 * Tests accuracy of a single example program
 * Single example program needs to match format of ground truth test data set
 * Can test multiple "styles" of the example program when given multiple "styles" of test data set which correspond
 * Prints results to console and writes them to a file
 */
public class SingleExampleValidator {
	public static final String outputDir = "/tmp";
	public static final String testSetPath = "java_test_set";
	public static final LangDescriptor langDesc = JAVA_CUSTOM_DESCR;

	public static void main(String[] args) throws Exception {

		String[] styles = {"style_1", "style_2", "style_google"};
//		String[] examples = {"Example2_1.java", "Example2_2.java", "Example2_3.java", "Example2_google.java"};
		String[] examples = {"Example1.java", "Example1.java", "Example1.java"}; // Name of corresponding example files in java/training/custom/

		HashMap<String, Collection<Float>> results = evaluate(examples, styles);

		printResults(results);
		writeToJson(results, "results.json");
	}

	/**
	 * Train on the given examples and test their accuracy on the corresponding style files
	 * Style folder should have same format as corresponding example
	 * @param exampleFiles list of example folders to train on
	 * @param styleFolders list of style folders in java_test_set_path folder to evaluate on, matching examples
	 * @return hashmap of training results
	 * @throws Exception if files can't be read
	 */
	public static HashMap<String, Collection<Float>> evaluate(String[] exampleFiles, String[] styleFolders) throws Exception{
		HashMap<String, Collection<Float>> allResults = new HashMap<String, Collection<Float>>();
		for (int i=0; i<styleFolders.length; i++){
			File styleFolder = new File(testSetPath + "/" + styleFolders[i]);
			String curExample = "corpus/java/training/custom/" + exampleFiles[i];
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
			allResults.put(styleFolders[i], styleResults);
		}
		return allResults;
	}

	/**
	 * Write the given object as json to the given file name
	 * Uses Gson library (https://github.com/google/gson) to serialize complex objects
	 * @param obj the object to write
	 * @param writePath path to write json file to, will be created
	 */
	public static void writeToJson(HashMap<String, Collection<Float>> obj, String writePath){
		Gson gson = new Gson();
		String json = gson.toJson(obj); //convert res to json
		try {
			FileWriter myWriter = new FileWriter(writePath);
			myWriter.write(json);
			myWriter.close();
			System.out.println("Wrote results to " + writePath);
		} catch (IOException e) {
			System.out.println("An error occurred writing to file.");
			e.printStackTrace();
		}
	}

	/**
	 * Prints the results generated in main to the console
	 * @param res the results to print to console
	 */
	public static void printResults(HashMap<String, Collection<Float>> res){
	    AtomicReference<Double> averages = new AtomicReference<>((double) 0);
		res.forEach((style, accuracies) -> {
            System.out.println("Style: " + style);
            double sum = 0;
            for (Float acc : accuracies) {
                System.out.println("    " + acc);
                sum += acc;
            }
            System.out.println("    avg: " + sum / accuracies.size());
			double finalSum = sum;
			averages.updateAndGet(v -> (double) (v + finalSum/accuracies.size()));
        });
		System.out.println("\nOverall avg: " + averages.get() / res.size());
    }

	/**
	 * Trains on a corpus and tests the accuracy of training on a test document
	 * Borrowed from SubsetValidator.java
	 * @param language the LangDescriptor defined in Tool.java matching the language of documents
	 * @param documents list of documents to train on (your corpus)
	 * @param testDoc single document to test the trained formatter on - should be formatted to match documents
	 * @param saveOutput save output to file
	 * @param computeEditDistance compute edit distance as well as error rate
	 * @return Triple of (trained formatter, edit distance, error rate)
	 * @throws Exception if file cannot be written
	 */
	public static Triple<Formatter,Float,Float> validate(LangDescriptor language,
	                                                     List<InputDocument> documents,
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
