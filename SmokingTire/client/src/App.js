import { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import AuthContext from "./AuthContext";
import Home from "./Home";
import Nav from "./Nav";
import Login from "./Login";
import UserPage from './UserPage';
import jwtDecode from "jwt-decode";
import NotFound from './NotFound';
import AddListing from './AddListing';
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
          <Route path="/addListing" element={<AddListing/>}/>
          <Route path="/login" element={<Login />} />
          <Route path="/userpage" element={<UserPage/>}/>
          <Route path="/edit/user/:id" element={<EditUser/>} />
          <Route path="/edit/listing/:id" element={<EditListing/>}/>
          <Route path="/delete/listing/:id" element={<DeleteListing/>}/>
          <Route path="*" element={<NotFound/>} />
      </Routes>
    </div>
    </AuthContext.Provider>
  );
}

export default App;
