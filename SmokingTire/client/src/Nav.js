
import { Link, Navigate, useNavigate, NavLink } from 'react-router-dom';

import { useContext } from 'react';
import AuthContext from './AuthContext';




function Nav() {
    const [user, setUser] = useContext(AuthContext);
    const nav = useNavigate();

    function handleLogout() {
        localStorage.removeItem("token");
        setUser(null);
        nav("/");
    }

    return (
        <nav className='px-5 navbar navbar-expand-lg navbar-dark bg-primary'>
            <div className="container-fluid">
                <NavLink className="navbar-brand" to="/">SmokingTire</NavLink>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <NavLink className={({ isActive }) => (isActive ? "nav-link active" : "nav-link")} to="/">Home</NavLink>
                        </li>

                        <li className="nav-item">
                            <NavLink className={({ isActive }) => (isActive ? "nav-link active" : "nav-link")} to="/addListing">Add Listing</NavLink>
                        </li>

                        {user?.user ? (
                            <>
                                <li className="nav-item">
                                    <NavLink className={({ isActive }) => (isActive ? "nav-link active" : "nav-link")} to="/userpage">User Profile Page</NavLink>
                                </li>
                                <li className="nav-item">
                                    <button onClick={handleLogout}>Logout {user.user.sub}</button>
                                </li>
                            </>
                        ) : (
                            <>
                                <li className="nav-item">
                                    <Link to="/login">Login</Link><br />
                                </li>
                                <li className="nav-item">
                                    <Link to="/addUser">Register</Link>
                                </li>
                            </>
                        )}

                    </ul>
                </div>
            </div>
        </nav>
    )



}

export default Nav;