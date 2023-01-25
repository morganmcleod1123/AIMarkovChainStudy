# Language Guesser AI
A program I created in Java that uses Markov chains to determine the language of text inputted by a user. The program is able to accurately predict any language it has been previously trained with. 
## Project Description
### Markov Chains
Markov chains are extremely effective at determining the probability of something transitioning from one state to another. This made them useful in determining the language of inputted text because they can evaluate how the characters in the  text transition between each other. Once this is evaluated, the pattern of the text is compared with the known patterns of languages that the program has been trained on.
### Training
I trained the program using books written in several languages. For example, I used Lewis Carroll's *Alice in Wonderland* to train the program in English. Though I only trained the program in four languages, the program could theoretically be trained on any number of languages provided a sufficiently sized sample of text.
### Accuracy
From my testing, the program was able to accurately predict the language of inputted text in four words or less. I did not input any "tricky" text, in other words, text that is in one language but has the characteristics of another. I am unsure whether such a scenario would ever occur in standard use. Therefore, for regular use I would say the program is highly accurate.
