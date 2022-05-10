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
import AddUser from './AddUser';
import EditUser from './EditUser';
import DeleteUser from './DeleteUser';
import ViewListing from './ViewListing';
import PurchaseListing from './PurchaseListing';
import UploadImage from './UploadImage';

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
          <Route path="/view/listing/:id/:imageUrl" element={<ViewListing/>}/>
          <Route path="/purchase/:id" element={<PurchaseListing/>}/>
          <Route path="/userpage" element={<UserPage/>}/>
          <Route path='/addUser' element={<AddUser/>}/>
          <Route path="/edit/user/:username" element={<EditUser/>} />
          <Route path="/delete/user/:username" element={<DeleteUser/>} />
          <Route path="/edit/listing/:id" element={<EditListing/>}/>
          <Route path="/delete/listing/:id" element={<DeleteListing/>}/>
          <Route path='/image/:id' element={<UploadImage/>}/>
          <Route path="*" element={<NotFound/>} />
      </Routes>
    </div>
    </AuthContext.Provider>
  );
}

export default App;
