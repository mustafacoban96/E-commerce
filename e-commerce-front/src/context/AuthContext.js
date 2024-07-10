import {  createContext,useContext,useState} from "react";


const AuthContext = createContext({
    user:null,
    access_token:null,
    refresh_token:null,
    setUser: () =>{},
    setToken: () =>{},
    setRefreshToken :() =>{}
})



export const AuthProvider = ({children}) =>{
    const [user,_setUser] = useState(() =>{
        const user = localStorage.getItem('USER')
        return user ? JSON.parse(user) : null
    });

    const [access_token, _setToken] = useState(localStorage.getItem('ACCESS_TOKEN'));
    const [refresh_token,_setRefreshToken] = useState(localStorage.getItem('REFRESH_TOKEN'));

    const setToken = (access_token) =>{
        _setToken(access_token);

        if(access_token){
            localStorage.setItem('ACCESS_TOKEN',access_token);
        }
        else{
            localStorage.removeItem('ACCESS_TOKEN')
        }
    }


    const setUser = (user) => {
        _setUser(user)
        if(user){
            localStorage.setItem('USER', JSON.stringify(user))
        }
        else{
            localStorage.removeItem('USER');
        }
    }

    const setRefreshToken = (refresh_token) =>{
        _setRefreshToken(refresh_token)

        if(refresh_token){
            localStorage.setItem('REFRESH_TOKEN',refresh_token)
        }else{
            localStorage.removeItem('REFRESH_TOKEN')
        }
    }

    return(
        <AuthContext.Provider
            value={{
                user,
                access_token,
                refresh_token,
                setUser,
                setToken,
                setRefreshToken
            }}
        >
            {children}
        </AuthContext.Provider>
    )

}

export const useAuthContext = () => useContext(AuthContext);