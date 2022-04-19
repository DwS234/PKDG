import { fetchUtils, Options } from "react-admin";

import AuthService from "../../../services/auth/AuthService";
import { API_BASE_URL } from "../../../utils/Environment";

const API_URL = `${API_BASE_URL}/api`;

interface HttpResponse {
	status: number;
	headers: Headers;
	body: string;
	json: any;
}

const httpClient = (url: string, options: any = {}) => {
	if (!options.headers) {
		options.headers = new Headers({ Accept: "application/json" });
	}

	const token = AuthService.getToken();
	options.headers.set("Authorization", `Bearer ${token}`);
	return fetchUtils.fetchJson(url, options);
};

const WithAuthDataProvider: any = {
	getList: async (resource: string, params: any) => {
		const { page, perPage } = params.pagination;
		const { field, order } = params.sort;

		const url = `${API_URL}/${resource}?page=${page}&size=${perPage}&sort=${field},${order}`;

		const { json }: HttpResponse = await httpClient(url);
		for (let i = 0; i < json.data.length; i++) {
			json.data[i] = { id: i + 1, ...json.data[i] };
		}

		return {
			data: json.data,
			total: json.totalSize
		};
	}
};

export default WithAuthDataProvider;
