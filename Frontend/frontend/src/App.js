import LoginPage from "./componets/login";
import AuthProvider from "./componets/authProvider";
import Dashboard from "./pages/dashboard";
import PlaylistsList from "./pages/playlists";

import { BrowserRouter, Route,Routes, Redirect, Navigate } from "react-router-dom";
import React,{ useState, useEffect } from "react";

function App() {

//We need to fech some playlists
const [playlists, setPlaylists] = useState([]);

const fetchData = () => {
  return fetch("http://localhost:8095/playlist")
        .then((response) => response.json())
        .then((data) => setPlaylists(data));
}

useEffect(() => {
  fetchData();
},[])



//use context
//react. create context

  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" 
          element={ <LoginPage/> } 
          />
          <Route
            path="/dashboard"
            element={ <Dashboard />  }
          />
            <Route
            path="/playlists"
            element={  <PlaylistsList playlists={playlists} />  }
          />
        </Routes>

      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
