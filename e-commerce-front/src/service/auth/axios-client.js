import axios from "axios"


const axiosClient = axios.create({
    baseURL:process.env.REACT_APP_AUTH_BASE_URL
})


axiosClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('ACCESS_TOKEN');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    
    return Promise.reject(error);
});


axiosClient.interceptors.response.use((response) =>{
    return response
}, (error) =>{
    const {response} = error;
    
    
    if(response && response.status === 401){
        console.log('response döndü')
        localStorage.removeItem('ACCESS_TOKEN');
    }
    
    return Promise.reject(error);
})

export default axiosClient;