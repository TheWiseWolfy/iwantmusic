
import React, { useState ,useContext} from 'react';
import { Link, Navigate } from 'react-router-dom';
import {AuthContext } from "../componets/authProvider";

function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const {isAuthenticated, token, login} = useContext(AuthContext);

    function handleSubmit(event) {
        event.preventDefault();  
        
        login(username,password)
      }
    
    return (
      <div>
        {!isAuthenticated &&
          <form onSubmit={handleSubmit}>
            <label>
              Username:
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </label>
            <br />
            <label>
              Password:
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </label>
            <br />
            <button type="submit">Submit</button>
          </form>
        }
        {isAuthenticated && 
          
          <Navigate to="/dashboard"></Navigate>
        }
      </div>
      );

}

export default LoginPage;