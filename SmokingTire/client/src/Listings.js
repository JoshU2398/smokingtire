import { useState, useEffect } from 'react';
import Listing from './Listing';

function Listings() {
    const [listings, setListings] = useState([]);

    useEffect(() => {
        fetch("http://smokingtiresapi-env.eba-e7j6miiq.us-east-1.elasticbeanstalk.com/api/listings/findAvailable")
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    alert("Something went wrong while fetching...");
                }
            })
            .then(listingData => setListings(listingData))
            .catch(rejection => {
                alert("Failure: all")
            });
        
    }, []);

    function removeListingFromState(listingId) {
        setListings(listings.filter(listing => listing.listingId !== listingId));

    }

    console.log(listings)

    function listingFactory() {
        return listings.map(listing => <div className="col-4">
            <Listing
                key={listing.listingId}
                listingObj={listing}
                removeFromState={removeListingFromState}
            /><br /><br /></div>);
    }


    return (
        <div className="main">
            <div className="view-listings">
                <h2>Cars For Sale</h2><br />

                {listingFactory()}
            </div>
        </div>
    )
}


export default Listings;
