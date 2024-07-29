import axios from "axios";
import { toast } from "react-toastify";

const axiosConfig = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL, //replace with your BaseURL
  headers: {
  
    'Content-Type': 'application/json', // change according header type accordingly

  },
});
const refreshToken = async () => {
   
    try {
      const response = await axios.post(`${process.env.REACT_APP_AUTH_BASE_URL}/refreshtoken`, {
        
        refresh_token: localStorage.getItem('REFRESH_TOKEN'),
      })
      let refreshed_access_token = response.data.access_token
      //setToken(refreshed_access_token)
      
      return refreshed_access_token;
    } catch (error) {
      console.log("ref",error.response);
      if(error.response.status === 403){
        toast.warning('Session has expired.. logging out.',{
          autoClose: 4000,
          position: 'top-right',
        })
      }

      setTimeout(() =>{
        localStorage.removeItem('ACCESS_TOKEN')
        localStorage.removeItem('REFRESH_TOKEN')
        localStorage.removeItem('USER')
      },4200)
    }
  };


axiosConfig.interceptors.request.use(
    (config) => {
      const access_token = localStorage.getItem('ACCESS_TOKEN'); // get stored access token
      if (access_token) {
        config.headers.Authorization = `Bearer ${access_token}`; // set in header
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  axiosConfig.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      const originalRequest = error.config;
      if (error.response.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;
        const refresh_token = localStorage.getItem('REFRESH_TOKEN');
        if (refresh_token) {
          try {
            
            // don't use axious instance that already configured for refresh token api call
            const access_token = await refreshToken();
            localStorage.setItem('ACCESS_TOKEN', access_token);  //set new access token
            originalRequest.headers.Authorization = `Bearer ${access_token}`;
            console.log(originalRequest)
            return axios(originalRequest); //recall Api with new token
          } catch (error) {
            console.log('zz:',error)
                //clearAuthContext();
                //window.location.href = "/login";
                return Promise.reject(error);
          }
        }
      }
      return Promise.reject(error);
    }
  );

export default axiosConfig;