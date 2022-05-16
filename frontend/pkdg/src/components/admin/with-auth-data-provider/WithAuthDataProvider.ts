import {
  CreateParams,
  DataProvider,
  DeleteManyParams,
  DeleteParams,
  fetchUtils,
  GetListParams,
  GetManyParams,
  GetManyReferenceParams,
  GetOneParams,
  Options,
  UpdateManyParams,
  UpdateParams,
} from "react-admin";
import AuthService from "../../../services/auth/AuthService";
import { API_BASE_URL } from "../../../utils/Environment";

const API_URL = `${API_BASE_URL}/admin-api`;

interface HttpResponse {
  status: number;
  headers: Headers;
  body: string;
  json: any;
}

const httpClient = (url: string, options: Options = { method: "GET" }) => {
  if (!options.headers) {
    options.headers = new Headers({ Accept: "application/json" });
  }

  const token = AuthService.getToken();
  (options.headers as Headers).set("Authorization", `Bearer ${token}`);
  return fetchUtils.fetchJson(url, options);
};

const WithAuthDataProvider: DataProvider = {
  getList: async (resource: string, params: GetListParams) => {
    const { page, perPage } = params.pagination;
    const { field, order } = params.sort;

    const url = `${API_URL}/${resource}?page=${page - 1}&size=${perPage}&sort=${field},${order}`;

    const { json }: HttpResponse = await httpClient(url);

    return {
      data: json.data,
      total: json.totalSize,
    };
  },

  getOne: async (resource: string, params: GetOneParams) => {
    const { id } = params;
    const url = `${API_URL}/${resource}/${id}`;

    const { json }: HttpResponse = await httpClient(url);

    return {
      data: json,
    };
  },

  getMany: async (resource: string, params: GetManyParams) => {
    const { ids } = params;
    const url = `${API_URL}/${resource}/${ids.join(",")}`;

    const { json }: HttpResponse = await httpClient(url);

    return {
      data: json,
    };
  },

  getManyReference: async (resource: string, params: GetManyReferenceParams) => {
    const { json }: HttpResponse = await httpClient("");

    return {
      data: json,
    };
  },

  update: async (resource: string, params: UpdateParams) => {
    const { id, data } = params;
    const url = `${API_URL}/${resource}/${id}`;

    const { json }: HttpResponse = await httpClient(url, { method: "PUT", body: JSON.stringify(data) });

    return {
      data: json,
    };
  },

  updateMany: async (resource: string, params: UpdateManyParams) => {
    const { ids } = params;
    const url = `${API_URL}/${resource}/${ids.join(",")}`;

    const { json }: HttpResponse = await httpClient(url, { method: "PUT" });

    return {
      data: json,
    };
  },

  create: async (resource: string, params: CreateParams) => {
    const { data } = params;
    const url = `${API_URL}/${resource}`;

    const { json }: HttpResponse = await httpClient(url, { method: "POST", body: JSON.stringify(data) });

    return {
      data: json,
    };
  },

  delete: async (resource: string, params: DeleteParams) => {
    const { id } = params;
    const url = `${API_URL}/${resource}/${id}`;

    const { json }: HttpResponse = await httpClient(url, { method: "DELETE" });

    return {
      data: json,
    };
  },

  deleteMany: async (resource: string, params: DeleteManyParams) => {
    const { ids } = params;

    for (let id in ids) {
      const url = `${API_URL}/${resource}/${id}`;
      await httpClient(url, { method: "DELETE" });
    }

    return {
      data: [],
    };
  },
};

export default WithAuthDataProvider;
