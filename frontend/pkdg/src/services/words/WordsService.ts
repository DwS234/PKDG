import axios from "axios";
import Word from "../../model/Word";
import { AUTOCOMPLETE_URL, WORDS_BY_ENTRY } from "./WordsEndpoints";

class WordsService {

  async getAutocomplete(query: string) {
    const response = await axios.get(AUTOCOMPLETE_URL, { 
      params: {
        q: query
      }
    });
    const data: Array<string> = response.data;
    return data;
  }

  async getWordsByEntry(entry: string) {
    const response = await axios.get(WORDS_BY_ENTRY + `/${entry}`);
    const data: Array<Word> = response.data;
    return data;
  }
}

export default new WordsService();
