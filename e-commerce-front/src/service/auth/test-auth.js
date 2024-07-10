import axios from "axios"

//https://dev.to/chafroudtarek/refresh-token-implementation-in-reactjs-53f7

const axiosClient = axios.create({
    baseURL:process.env.REACT_APP_BASE_URL,
    headers:{
        'Content-Type': 'application/json', 
        
    },
    //withCredentials: true,
})

const refresh = async (refresh_token) => {
    try {
        
      const resp = await axiosClient.post("/auth/refreshtoken",{
        refresh_token
      });
      console.log("refresh token--->", resp);
      return resp.data;
    } catch (e) {
      console.log("Error",e);
      
      return e   
    }
  };


axiosClient.interceptors.request.use((config) => {
    const access_token = localStorage.getItem('ACCESS_TOKEN');
    if (access_token) {
        config.headers.Authorization = `Bearer ${access_token}`;
    }
    return config;
}, (error) => {
    
    return Promise.reject(error);
});


axiosClient.interceptors.response.use((response) =>{
    return response
}, async (error) =>{
    console.log(error)
    const originalRequest = error.config;
    console.log(originalRequest);
    console.log('dd',error.response.status)
    if(error.response.status === 403 && !originalRequest._retry){
        console.log('Step1')
        originalRequest._retry = true;
        const refresh_token = localStorage.getItem('REFRESH_TOKEN');
        console.log('emir')
        const resp =  await refresh(refresh_token)
        console.log('refresh token api throw it ---->',resp);
        if (resp && resp.access_token) {
            localStorage.setItem('ACCESS_TOKEN', resp.access_token);

            axiosClient.defaults.headers.common['Authorization'] = `Bearer ${resp.access_token}`;
            console.log('aaw:',resp)
            return axiosClient(originalRequest);
        }
        
        
       
    }
    return Promise.reject(error);
})



export default axiosClient;