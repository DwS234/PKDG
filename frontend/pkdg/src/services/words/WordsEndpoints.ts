import { API_BASE_URL } from "../../utils/Environment";

const wordsApi = API_BASE_URL + "/api/words";

export const AUTOCOMPLETE_URL = wordsApi + "/autocomplete";
export const WORDS_BY_ENTRY = wordsApi + "/entry";
export const getAvailableWordsToRepeatUrl = (username: string) =>
	`${API_BASE_URL}/api/users/${username}/words/available-words-to-repeat`;
