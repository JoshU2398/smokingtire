import {useState, useEffect, useContext} from 'react';
import { Link } from 'react-router-dom';
import Listing from "./Listing";
import AuthContext from './AuthContext';

function UserPage() {

    const [user, setUser] = useContext(AuthContext);
    const [usersListings, setUsersListings] = useState([]);
    const [usersPurchased, setUsersPurchased] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/listings/userSelling/" + user.user.sub)
        .then(response => {
            if(response.status === 200){
                    return response.json();
            }else{
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
            if(response.status === 200){
                    return response.json();
            }else{
                alert("Something went wrong while fetching...");
            }
        })
        .then(listingData => setUsersPurchased(listingData))
        .catch(rejection => {
            alert("Failure: " + rejection.status + ": " + rejection.statusText)
        });
    }, []);

    function removeListingFromState(listingId){
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
        <>
        <div>
            <h2>{user.user.sub} Account Actions</h2>
            <Link to={'/edit/user/' + user.user.sub}>Edit</Link>
            <p>Password: </p>
        </div>

        <div className='purchased-listings'>
        <h3>Purchased Listings</h3>
        {listingFactory(usersPurchased)}
        </div>

        <div className='users-active-listings'>
        <h3>Your Active Listings</h3>
        {listingFactory(usersListings)}
        </div>
        </>
    )
}

export default UserPage;