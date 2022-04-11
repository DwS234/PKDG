import Repetition from "../../model/Repetition";
import RepetitionWithWord from "../../model/RepetitionWithWord";
import RepetitionWithWordBasic from "../../model/RepetitionWIthWordBasic";
import WordInRepetition from "../../model/WordInRepetition";
import axiosApiInstance from "../../utils/AxiosInterceptor";
import { DUE_REPETITONS, getCreateRepetitionURL, getDeleteRepetitionURL, getRepetitionsByUsernameURL, getUpdateRepetitionURL, WORDS_IN_REPETITON_BY_WORDS_IDS } from "./RepetitionsEndpoints";

const axios = axiosApiInstance;

class RepetitionsService {

  async getRepetitionsByWordsIds(wordsIdsArr: string[]): Promise<Array<WordInRepetition>> {
    const response = await axios.get(WORDS_IN_REPETITON_BY_WORDS_IDS, {
      params: {
        wordsIds: wordsIdsArr.join(",")
      }
    });
    const data: Array<WordInRepetition> = response.data;
    return data;
  }

  async addRepetition(wordId: string, username: string) {
    const repetition: Repetition = {
      nextDate: new Date(),
      consecutiveCorrectAnswers: 0,
      easiness: 2.0,
      timesSeen: 0,
      lastIntervalDays: 0
    };

    const response = await axios.post(getCreateRepetitionURL(username, wordId), repetition);
    const data: Repetition = response.data;

    return data;
  }

  async updateRepetition(repetitionId: string, repetition: Repetition) {
    const response = await axios.put(getUpdateRepetitionURL(repetitionId), repetition);
    const data: Repetition = response.data;

    return data;
  }

  async getRepetitionsByUsername(username: string) {
    const response = await axios.get(getRepetitionsByUsernameURL(username));
    const data: Array<RepetitionWithWordBasic> = response.data;
    return data;
  }

  async getDueRepetitions() {
    const response = await axios.get(DUE_REPETITONS);
    const data: Array<RepetitionWithWord> = response.data;
    return data;
  }

  async deleteRepetiton(repetitionId: string) {
    await axios.delete(getDeleteRepetitionURL(repetitionId));
  }
}

export default new RepetitionsService();
