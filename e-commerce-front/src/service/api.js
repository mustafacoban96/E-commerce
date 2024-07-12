import axios from "axios";
//import { useUpdateAccessToken, useClearAuthContext } from './token-service';
import { useAuthContext } from "../context/AuthContext";
import { toast } from "react-toastify";





const useApi = () => {
 
  const {refresh_token,access_token,setToken,setRefreshToken,setUser} = useAuthContext();
  //refresh token expire token are going to added........
  

  const api = axios.create({
    baseURL: process.env.REACT_APP_BASE_URL,
    headers: {
      "Content-Type": "application/json"
    },
  });

  const refreshToken = async () => {

    try {
      const response = await axios.post(`${process.env.REACT_APP_AUTH_BASE_URL}/refreshtoken`, {
        
        refresh_token: localStorage.getItem('REFRESH_TOKEN'),
      })
      let refreshed_access_token = response.data.access_token
      setToken(refreshed_access_token)

    } catch (error) {
      console.log("ref",error.response);
      if(error.response.status === 403){
        toast.warning('Session has expired.. logging out.',{
          autoClose: 4000,
          position: 'top-right',
        })
      }

      setTimeout(() =>{
        setToken(null)
        setRefreshToken(null)
        setUser(null)
      },4200)
    }
  };


  api.interceptors.request.use(
    (config) => {
      if (access_token) {
        config.headers.Authorization = `Bearer ${access_token}`;
      }
      return config;
    },
    (error) => {
      console.log('ttsss:',error)
      return Promise.reject(error);
    }
  );

    api.interceptors.response.use(
      (response) => {
        return response;
      },
       async (error) => {
        const originalConfig = error.config;
        if (originalConfig && !originalConfig._retry && error.response) { 
          if (error.response.status === 401) {
            originalConfig._retry = true;
            if(refresh_token){ //control inject
              try {
                await refreshToken();
                return api(originalConfig);
              } catch (refreshError) {
                console.log('zz:',refreshError)
                //clearAuthContext();
                //window.location.href = "/login";
                return Promise.reject(refreshError);
              }
            }
            
          }
        }
  
        return Promise.reject(error);
      }
    );


  return api;
};

export default useApi;
