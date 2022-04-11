import { API_BASE_URL } from "../../utils/Environment";

const repetitionsApi = API_BASE_URL + "/api";

export const WORDS_IN_REPETITON_BY_WORDS_IDS = repetitionsApi + "/repetitions/words";

export const DUE_REPETITONS = repetitionsApi + "/repetitions/due";

export const getCreateRepetitionURL = (username: string, wordId: string) => `${repetitionsApi}/users/${username}/words/${wordId}/repetitions`;

export const getUpdateRepetitionURL = (repetitionId: string) => `${repetitionsApi}/repetitions/${repetitionId}`;

export const getDeleteRepetitionURL = (repetitionId: string) => `${repetitionsApi}/repetitions/${repetitionId}`;

export const getRepetitionsByUsernameURL = (username: string) => `${repetitionsApi}/users/${username}/repetitions`;
