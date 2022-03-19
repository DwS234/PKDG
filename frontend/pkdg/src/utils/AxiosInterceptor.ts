import axios from "axios";
import AuthService from "../services/auth/AuthService";
const axiosApiInstance = axios.create();

// Add Bearer token in Authorization header
axiosApiInstance.interceptors.request.use(
  async config => {
    const token = AuthService.getToken();
    config.headers = {
      'Authorization': `Bearer ${token}`,
    }
    return config;
  },
  error => {
    Promise.reject(error)
});

export default axiosApiInstance;
