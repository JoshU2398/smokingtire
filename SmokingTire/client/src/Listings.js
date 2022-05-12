import { useState, useEffect } from 'react';
import Listing from './Listing';
import Form from './Form';

function Listings() {
    const [listings, setListings] = useState([]);

    const [makeId, setMakeId] = useState(null);
    const [modelId, setModelId] = useState(null);
  
    function childToParent(childMakeId, childModelId) {
        console.log(childMakeId);
        console.log(childModelId);

        setMakeId(childMakeId);
        setModelId(childModelId);
    }

    useEffect(() => {
        console.log(makeId);
        console.log(modelId);
        if (makeId == null && modelId == null) {
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
                    alert("Failure: all")
                });
        } else if (makeId && modelId == null) {
            fetch("http://localhost:8080/api/listings/findByMake/" + makeId)
                .then(response => {
                    if (response.status === 200) {
                        return response.json();
                    } else {
                        alert("Something went wrong while fetching...");
                    }
                })
                .then(listingData => setListings(listingData))
                .catch(rejection => {
                    alert("Failure: makes")
                });
        } else if (makeId && modelId) {
            fetch("http://localhost:8080/api/listings/findByModel/" + modelId)
                .then(response => {
                    if (response.status === 200) {
                        return response.json();
                    } else {
                        alert("Something went wrong while fetching...");
                    }
                })
                .then(listingData => setListings(listingData))
                .catch(rejection => {
                    alert("Failure: models")
                });
        }
    }, [makeId, modelId]);

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
            /></div>);
    }


    return (
        <div className="main">
            <div className="view-listings">
                <h2>Cars For Sale</h2>
                <p>Filter search: </p>
                <Form childToParent={childToParent}/>
                {listingFactory()}
            </div>
        </div>
    )
}


export default Listings;
