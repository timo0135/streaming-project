import axios from "axios";
import { useRouter } from "vue-router";

export default {
  install: (app, {baseUrl}) => {
    app.config.globalProperties.$api = () => {
      const router = useRouter();
      const instance = axios.create({
        baseURL: baseUrl,
        headers: {
          "Content-type": "application/json",
        //   "Authorization": `Bearer ${store.user.token}`,
        },
      });

      instance.interceptors.response.use(
        response => response,
        error => {
          if (error.response?.status === 401) {
            router.push({ name: 'signin' });
          }
          return Promise.reject(error);
        }
      );

      return instance;
    };
  }
};