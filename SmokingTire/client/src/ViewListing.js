import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import AuthContext from "./AuthContext";

function ViewListing() {

    const [listing, setListing] = useState(null);
    const {id} = useParams();

    const[userStatus, setUserStatus] = useContext(AuthContext);

    const nav = useNavigate();

    useEffect(
        () => {
    
            const jwt = localStorage.getItem("token");
            if(jwt){
    
                fetch("http://localhost:8080/api/listings/findListing/" + id, {
                    headers: {
                        Authorization: "Bearer " + jwt
                    }
                })
                .then(response => {
                    if(response.status === 200) {
                        return response.json();
                    } else {
                        console.log(response);
                        alert("retrieving toEdit failed");
                    }
                })
                .then(retrievedListing => {
                    console.log(retrievedListing);
                    setListing(retrievedListing);
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Rejected!");
                })
                } else {
                    nav("/login");
                }
            },
        []
    );

    console.log(listing);
    console.log(userStatus?.user.sub)

    return (
        <>
            { listing !== undefined || listing !== null ? (
                <div className="listing-item">
                    <h3>{listing.car.make.model.modelYear} {listing.car.make.makeName} {listing.car.make.model.modelName}</h3>
                    <h4>Owner: {listing.listingUser.username}</h4>
                    <p>Posted on: {listing.postDate}</p>
                    <p>Views: {listing.viewCount}</p>
                    <p>Price: ${listing.price}</p>
                    <p>Description: {listing.description}</p>

                    <div className="car-details">
                        <p>Body: {listing.car.chassis}</p>
                        <p>Mileage: {listing.mileage}</p>
                        <p>Horsepower: {listing.car.horsepower}</p>
                        <p>Drivetrain: {listing.car.drivetrain}</p>
                        <p>Transmission: {listing.car.transmission}</p>
                    </div>
                    {listing.available === true ? <Link to={'/purchase/' + listing.listingId}>Buy Now</Link> : null }

                    {userStatus?.user.sub === listing.listingUser.username 
                    || userStatus?.user.authorities.includes("ADMIN") 
                    ? listing.available === true ? (
                        <>
                        <Link to={'/edit/listing/' + listing.listingId}>Edit</Link>
                        <Link to={'/delete/listing/' + listing.listingId}>Delete</Link>
                        </>
                    ) : (
                        null
                    ) : (
                        null
                    )}
                </div>
            ) : null }
        </>
    )
}

export default ViewListing;