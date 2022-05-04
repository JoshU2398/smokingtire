import {useState, useEffect, useContext} from 'react';
import AuthContext from './AuthContext';

function UserPage() {

    const [user, setUser] = useContext(AuthContext);
    const [usersListings, setUsersListings] = useState([]);
    const [usersPurchased, setUsersPurchased] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/listings/userSelling/" + user?.username)
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
        fetch("http://localhost:8080/api/listings/userPurchased/" + user?.username)
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
        setListings(listings.filter(listing => listing.listingId !== listingId));

    }

    function listingFactory(listings) {
        return listings.map(listing => <Listing
        key={listing.listingId}
        listingObj={listing}
        removeFromState={removeListingFromState}
        />);
    }

    return (
        <>
        <h2>{user?.username}'s info</h2>

        <h3>Purchased Listings</h3>
        {listingFactory(usersPurchased)}
        <h3>Your Active Listings</h3>
        {listingFactory(usersListings)}
        </>
    )
}

export default UserPage;