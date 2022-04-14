package learning.markov;

import core.Duple;
import learning.core.Histogram;

import javax.swing.text.html.Option;
import java.util.*;

public class MarkovChain<L,S> {
    private LinkedHashMap<L, HashMap<Optional<S>, Histogram<S>>> label2symbol2symbol = new LinkedHashMap<>();

    public Set<L> allLabels() {return label2symbol2symbol.keySet();}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (L language: label2symbol2symbol.keySet()) {
            sb.append(language);
            sb.append('\n');
            for (Map.Entry<Optional<S>, Histogram<S>> entry: label2symbol2symbol.get(language).entrySet()) {
                sb.append("    ");
                sb.append(entry.getKey());
                sb.append(":");
                sb.append(entry.getValue().toString());
                sb.append('\n');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // Increase the count for the transition from prev to next.
    // Should pass SimpleMarkovTest.testCreateChains().
    public void count(Optional<S> prev, L label, S next) {
        if(!label2symbol2symbol.containsKey(label)){
            label2symbol2symbol.put(label, new HashMap<>());
        }
        if(!label2symbol2symbol.get(label).containsKey(prev)){
            label2symbol2symbol.get(label).put(prev, new Histogram<>());
        }
        // For specific language, get the histogram for the previous character and
        // increment the value in that histogram associated with the next character
        label2symbol2symbol.get(label).get(prev).bump(next);
    }

    // Returns P(sequence | label)
    // Should pass SimpleMarkovTest.testSourceProbabilities() and MajorMarkovTest.phraseTest()
    //
    // HINT: Be sure to add 1 to both the numerator and denominator when finding the probability of a
    // transition. This helps avoid sending the probability to zero.
    public double probability(ArrayList<S> sequence, L label) {
        // Multiply probability of each letter to next in the sequence and return that
        double probProduct = 1.0;
        Optional<S> previousLetter = Optional.empty();
        for(S letter :sequence){
            if(label2symbol2symbol.get(label).containsKey(previousLetter)){
                Histogram<S> prevLetterHist = label2symbol2symbol.get(label).get(previousLetter);
                probProduct *= ((double)(prevLetterHist.getCountFor(letter) +1 ) / (prevLetterHist.getTotalCounts() + 1));
            }
            previousLetter = Optional.of(letter);
        }
        return probProduct;
    }

    // Return a map from each label to P(label | sequence).
    // Should pass MajorMarkovTest.testSentenceDistributions()
    // Must add up to 1.

    // P(label | sequence) = P(sequence | label) * P(label)/ P(sequence)
    // P(label | sequence) = probability(sequence, label) * 1/allLabels.size() / combinations of letters
    public LinkedHashMap<L,Double> labelDistribution(ArrayList<S> sequence) {
        LinkedHashMap<L, Double> probMap = new LinkedHashMap<>();
        double sumOfLabelProbs = 0.0;
        for(L label : allLabels()){
            sumOfLabelProbs += probability(sequence, label);
        }
        for(L label : allLabels()){
            probMap.put(label, probability(sequence, label) / sumOfLabelProbs);
        }
        return probMap;
    }

    // Calls labelDistribution(). Returns the label with highest probability.
    // Should pass MajorMarkovTest.bestChainTest()
    public L bestMatchingChain(ArrayList<S> sequence) {
        LinkedHashMap<L, Double> probMap = labelDistribution(sequence);
        double bestDouble = 0.0;
        L bestL = null;
        Set<L> probMapKeys = probMap.keySet();
        for(L label : probMapKeys){
            if (probMap.get(label) > bestDouble) {
                bestDouble = probMap.get(label);
                bestL = label;
            }
        }
        return bestL;
    }
}
