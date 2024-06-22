import {  createContext,useContext,useState} from "react";


const AuthContext = createContext({
    user:null,
    token:null,
    setUser: () =>{},
    setToken: () =>{}
})



export const AuthProvider = ({children}) =>{
    const [user,_setUser] = useState(() =>{
        const user = localStorage.getItem('USER')
        return user ? JSON.parse(user) : null
    });

    const [token, _setToken] = useState(localStorage.getItem('ACCESS_TOKEN'));

    const setToken = (token) =>{
        _setToken(token);

        if(token){
            localStorage.setItem('ACCESS_TOKEN',token);
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

    return(
        <AuthContext.Provider
            value={{
                user,
                token,
                setUser,
                setToken
            }}
        >
            {children}
        </AuthContext.Provider>
    )

}

export const useAuthContext = () => useContext(AuthContext);