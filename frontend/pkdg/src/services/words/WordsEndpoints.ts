import { API_BASE_URL } from "../../utils/Environment";

const wordsApi = API_BASE_URL + "/api/words";

export const AUTOCOMPLETE_URL = wordsApi + "/autocomplete";
export const WORDS_BY_ENTRY = wordsApi + "/entry";
