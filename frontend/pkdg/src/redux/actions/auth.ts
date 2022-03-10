import { Dispatch } from "redux";
import User from "../../model/User";
import AuthService from "../../services/auth/AuthService";
import { LOGIN_FAIL, LOGIN_SUCCESS, LOGOUT, REGISTER_FAIL, REGISTER_SUCCESS } from "./type";

export const register = (username: string, email: string, password: string) => async (dispatch: Dispatch) => {
  return new Promise(async (resolve, reject) => {
    try {
      await AuthService.register(username, email, password);
      dispatch({
        type: REGISTER_SUCCESS
      });
      resolve(username);
    } catch (error: any) {
      const message = (error.response && error.response.data && error.response.data.message) || error.message || error.toString();
      dispatch({
        type: REGISTER_FAIL
      });
      reject(message);
    }
  });
};
  

export const login = (username: string, password: string) => async (dispatch: Dispatch) => {
  return new Promise(async (resolve, reject) => {
    try {
      const data: User = await AuthService.login(username, password);
      dispatch({
        type: LOGIN_SUCCESS,
        payload: {
          user: data
        }
      });

      resolve(data);
    } catch (error: any) {
      let message = (error.response && error.response.data && error.response.data.message) || error.message || error.toString();

      if (error.response && error.response.status === 401) {
        message = "Niepoprawna nazwa użytkownika lub hasło";
      }

      dispatch({
        type: LOGIN_FAIL,
      });
      reject(message);
    }
  });
}

export const logout = () => (dispatch: Dispatch) => {
  AuthService.logout();
  dispatch({
    type: LOGOUT,
  });
};
