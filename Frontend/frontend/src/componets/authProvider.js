import React, { useState, useEffect, useCallback } from 'react';


export const AuthContext = React.createContext(
    {
        isAuthenticated: false,
        token: ''
    }
)

function AuthProvider(props) {

    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [token, setToken] = useState('');
    const [pendingAutentification, setPendingAutentification] = useState(false);

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    useEffect( authRequest, [pendingAutentification] ) //AuthRequest is executed only when pendingAutentification is modified

    function loginCallback( username, password){
        setUsername(username)
        setPassword(password)
        setPendingAutentification(true)
    }

    function authRequest() {
        if(! isAuthenticated && pendingAutentification){

            const body = {
                "username": username,
                "password": password
            }

            fetch('http://localhost:8095/authenticate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            })
                .then(response => {
                    if (response.status === 200) {
                        response.json().then(res => {
                            setToken(res.jwt)
                            setIsAuthenticated(true)
                        })
                    } else {
                        console.log("Autentification failed.") 
                    }
                    setPendingAutentification(false)
                })
        }
    }

    const value =  {
        isAuthenticated: isAuthenticated,
        token: token,
        //Punem la dispozitie o fuctie de login copiilor prin context
        login: useCallback(loginCallback, []) 
    }

    return (
        <AuthContext.Provider value={value}>
        {props.children}
        </AuthContext.Provider>
    );

}

export default AuthProvider;