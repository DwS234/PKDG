import Word from "../../model/Word";
import axiosApiInstance from "../../utils/AxiosInterceptor";
import { AUTOCOMPLETE_URL, getAvailableWordsToRepeatUrl, WORDS_BY_ENTRY } from "./WordsEndpoints";

const axios = axiosApiInstance;

class WordsService {
	async getAutocomplete(query: string): Promise<Array<string>> {
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

	async getAvailableWordsToRepeat(username: string): Promise<Array<Word>> {
		const response = await axios.get(getAvailableWordsToRepeatUrl(username));
		const data: Array<Word> = response.data;
		return data;
	}
}

export default new WordsService();
