import { Link, Navigate, useNavigate } from 'react-router-dom';
import { useContext } from 'react';
import AuthContext from './AuthContext';



function Nav(){
    const [user, setUser] = useContext(AuthContext);
    const nav = useNavigate();

    function handleLogout(){
        localStorage.removeItem("token");
        setUser(null);
        nav("/");
    }

    return (
        <nav>
                <ul>
                    <li>
                        <Link to="/">Home</Link>
                    </li>

                    <li>
                        <Link to="/addListing">Add Listing</Link>
                    </li>

                    {user?.user ? (
                        <>
                            <li>
                                <Link to="/userpage">User Profile Page</Link>
                            </li>
                            <li>
                                <button onClick={handleLogout}>Logout {user.user.sub}</button>
                            </li>
                        </>
                    ) : (
                        <li>
                            <Link to="/login">Login</Link>
                        </li>
                    )}

                </ul>
        </nav>
    )



}

export default Nav;