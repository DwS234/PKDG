import { API_BASE_URL } from "../../utils/Environment";

const authApi = API_BASE_URL + "/api/auth";

export const LOGIN_URL = authApi + "/login";
export const REGISTER_URL = authApi + "/register";
