import Repetition from "../../model/Repetition";
import Word from "../../model/Word";
import WordInRepetition from "../../model/WordInRepetition";
import axiosApiInstance from "../../utils/AxiosInterceptor";
import { getCreateRepetitionURL, getDeleteRepetitionURL, WORDS_IN_REPETITON_BY_WORDS_IDS } from "./RepetitionsEndpoints";

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
      timesSeen: 0
    };

    const response = await axios.post(getCreateRepetitionURL(username, wordId), repetition);
    const data: Repetition = response.data;

    return data;
  }

  async deleteRepetiton(repetitionId: string) {
    await axios.delete(getDeleteRepetitionURL(repetitionId));
  }
}

export default new RepetitionsService();
