export default interface Word {
  id: string,
  entry: string;
  definition: string;
  examples: Array<WordInSentence>;
}

interface WordInSentence {
  sentence: string;
}
