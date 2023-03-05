
import React, {useContext} from 'react';
import {AuthContext } from "../componets/authProvider";

const Dashboard = ({children}) => {

  const {isAuthenticated, token} = useContext(AuthContext);

console.log( isAuthenticated + " si " + token)

  return (
    <div>
      {isAuthenticated &&
        <div>
          <p>{token}</p>
        </div> 
      }
    </div> 
   );
};

export default Dashboard;
