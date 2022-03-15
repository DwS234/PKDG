export default interface Word {
  wordId: string,
  entry: string;
  definition: string;
  examples: Array<WordInSentence>;
}

interface WordInSentence {
  sentence: string;
}
