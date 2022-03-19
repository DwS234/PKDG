import axios from "axios";
import User from "../../model/User";
import { LOCAL_STORAGE_USER_KEY } from "../../utils/LocalStorageKeys";
import { LOGIN_URL, REGISTER_URL } from "./AuthEndpoints";

class AuthService {
  async login(username: string, password: string) {
    const response = await axios.post(LOGIN_URL, { username, password });
    const data: User = response.data;

    if (data.token) {
      localStorage.setItem(LOCAL_STORAGE_USER_KEY, JSON.stringify(data));
    }

    return data;
  }

  logout() {
    localStorage.removeItem(LOCAL_STORAGE_USER_KEY);
  }

  async register(username: string, email: string, password: string) {
    return axios.post(REGISTER_URL, { username, email, password });
  }
 
  getToken() {
    const userString = localStorage.getItem(LOCAL_STORAGE_USER_KEY);
    if (!userString) {
      return {};
    }

    const user: User = JSON.parse(userString);
    if (user && user.token) {
      return user.token;
    }

    return null;
  }

  getLocalUser(): User | null {
    const userString = localStorage.getItem(LOCAL_STORAGE_USER_KEY);
    if (!userString) {
      return null;
    }

    return JSON.parse(userString);
  }
}

export default new AuthService();
