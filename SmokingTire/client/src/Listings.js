import { useState, useEffect } from 'react';


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

    function listingFactory() {
        return listings.map(listing =>
            <div className="col-4">
                <Listing
                    key={listing.listingId}
                    listingObj={listing}
                    removeFromState={removeListingFromState}
                />
            </div>
        );
    }


    return (
        <>

            {listingFactory()}
        </>
    )
}


export default Listings;