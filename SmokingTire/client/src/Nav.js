import { Link, useNavigate } from 'react-router-dom';
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
                <ul>
                    <li>
                        <Link to="/">Home</Link>
                    </li>

                    <li>
                        <Link to="/viewListings">Buy a Car</Link>
                    </li>

                    <li>
                        <Link to="/addListing">Sell Your Car</Link>
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
                        <>
                            <li>
                                <Link to="/login">Login</Link><br />
                            </li>
                            <li>
                                <Link to="/addUser">Register</Link>
                            </li>
                        </>
                    )}

                </ul>
            </div>
        </nav>
    )



}

export default Nav;