import { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import AuthContext from "./AuthContext";
import Home from "./Home";
import Nav from "./Nav";
import Login from "./Login";
import UserPage from './UserPage';
import jwtDecode from "jwt-decode";
import NotFound from './NotFound';
import EditListing from './EditListing';
import DeleteListing from './DeleteListing';

function App() {

  const [user, setUser] = useState(null);


  useEffect(() => {
    const jwt_token = localStorage.getItem("token");
    if(jwt_token){
        setUser({user: jwtDecode(jwt_token)});
    }
  }, []);


  return (
    <AuthContext.Provider value={[user, setUser]}>
    <div className="App">
      <Nav />
      <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/userpage" element={<UserPage/>}/>
          <Route path="/editListing/:id" element={<EditListing/>}/>
          <Route path="/deleteListing/:id" element={<DeleteListing/>}/>
          <Route path="*" element={<NotFound/>} />
          <Route path="/edit" element={<EditListing/>} />
          <Route path="/edit/user" element={<EditUser/>} />
      </Routes>
    </div>
    </AuthContext.Provider>
  );
}

export default App;
