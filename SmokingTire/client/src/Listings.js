import { useState, useEffect } from 'react';
import Listing from './Listing';

function Listings() {
    const [listings, setListings] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/listings/findAvailable")
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    alert("Something went wrong while fetching...");
                }
            })
            .then(listingData => setListings(listingData))
            .catch(rejection => {
                alert("Failure: " + rejection.status + ": " + rejection.statusText)
            });
    }, []);

    function removeListingFromState(listingId) {
        setListings(listings.filter(listing => listing.listingId !== listingId));

    }

    console.log(listings)

    function listingFactory() {
        return listings.map(listing => <Listing
            key={listing.listingId}
            listingObj={listing}
            removeFromState={removeListingFromState}
        />);
    }


    return (
        <div className="main">
            <div className="view-listings">
                <h2>Cars For Sale</h2>
                {listingFactory()}
            </div>
        </div>
    )
}


export default Listings;