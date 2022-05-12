import { useState, useEffect, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Listing from "./Listing";
import AuthContext from './AuthContext';

function UserPage() {

    const [user, setUser] = useContext(AuthContext);
    const jwt = localStorage.getItem("token");
    const [userId, setUserId] = useState(null);

    const [usersListings, setUsersListings] = useState([]);
    const [usersPurchased, setUsersPurchased] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {

        if (jwt) {
            fetch("http://localhost:8080/api/security/findUser/" + user.user.sub,
                {
                    headers: {
                        Authorization: "Bearer " + jwt
                    }
                })
                .then(response => {
                    if (response.status == 200) {
                        return response.json();
                    } else {
                        console.log(response);
                        alert("retrieving toEdit failed");
                    }
                })
                .then(retrievedUser => {
                    console.log(retrievedUser);
                    setUserId(retrievedUser.userId);
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Something very bad happened...");
                });
        } else {
            navigate("/login");
        }
    }, []);

    useEffect(() => {
        fetch("http://localhost:8080/api/listings/userSelling/" + user.user.sub)
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    alert("Something went wrong while fetching...");
                }
            })
            .then(listingData => setUsersListings(listingData))
            .catch(rejection => {
                alert("Failure: " + rejection.status + ": " + rejection.statusText)
            });
    }, []);

    useEffect(() => {
        fetch("http://localhost:8080/api/listings/userPurchased/" + user.user.sub)
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    alert("Something went wrong while fetching...");
                }
            })
            .then(listingData => setUsersPurchased(listingData))
            .catch(rejection => {
                alert("Failure: " + rejection.status + ": " + rejection.statusText)
            });
    }, []);

    function removeListingFromState(listingId) {
        setUsersListings(usersListings.filter(listing => listing.listingId !== listingId));
    }

    function listingFactory(props) {
        return props.map(listing => <Listing
            key={listing.listingId}
            listingObj={listing}
            removeFromState={removeListingFromState}
        />);
    }

    return (
        <div className="user-page">
            <div className="user-details">
                <h2>Account Details</h2>
                <Link to={'/edit/user/' + user.user.sub}>Edit Account</Link><br /><br />
                <Link to={'/delete/user/' + userId}>Delete Account</Link><br /><br />
            </div>

            <div className='purchased-listings'>
                &nbsp;<h2>Purchased Listings</h2><br /><br />
                {listingFactory(usersPurchased)}
            </div>

            <div className='users-active-listings'>
                &nbsp;<h2>Your Active Listings</h2><br /><br />
                {listingFactory(usersListings)}
            </div>
        </div>
    )
}

export default UserPage;