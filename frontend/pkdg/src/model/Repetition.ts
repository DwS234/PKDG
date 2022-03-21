export default interface Repetition {
  repetitionId?: string;
  nextDate: Date;
  easiness: number;
  consecutiveCorrectAnswers: number;
  timesSeen: number;
}
