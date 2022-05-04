import { Link } from 'react-router-dom';
import { useContext } from 'react';
import AuthContext from './AuthContext';



function Nav(){
    const [user, setUser] = useContext(AuthContext);

    function handleLogout(){
        localStorage.removeItem("token");
        setUser(null);
    }

    return (
        <nav>
                <ul>
                    <li>
                        <Link to="/">Home</Link>
                    </li>
                    <li>
                        <Link to="/edit/user">Edit User</Link>
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