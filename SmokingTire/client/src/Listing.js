
import { useContext, useEffect } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import AuthContext from "./AuthContext";

function Listing(props){
    console.log(props.listingObj);
    const temp = props.listingObj;

    const listing = {
        listingId:temp.listingId, description:temp.description, listingUser:temp.listingUser, 
        car:temp.car, postDate:temp.postDate, viewCount:temp.viewCount, mileage:temp.mileage, 
        price:temp.price, isAvailable:temp.available
    };
    console.log(listing);
    
    const car = listing.car
    const make = car.make.makeName;
    const model = car.make.model.modelName;
    const modelYear = car.make.model.modelYear;

    const [user, setUser] = useContext(AuthContext);
    const nav = useNavigate();

    function increaseViewCount() {
 
        const jwt = localStorage.getItem("token");
        if (jwt) {
            fetch("http://localhost:8080/api/listings/increaseViewCount/" + listing.listingId,
                {
                    method: "PUT",
                    headers: {
                        Authorization: "Bearer " + jwt,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(listing)
                }
            )
                .then(response => {
                    if (response.status == 204) {
                        return response.json();
                    } else {
                        console.log(response);
                        alert("update to view count failed!");
                    }
                })
                .then(retrievedViews => {
                    console.log(retrievedViews);
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Rejected!")
                });

        } else {
            nav();
        }

    }

    return (
        <div className="listing-item">
            <h3>{modelYear} {make} {model}</h3>
            <h4>Owner: {listing.listingUser.username}</h4>
            <p>Posted on: {listing.postDate}</p>
            <p>Views: {listing.viewCount}</p>
            <p>Price: ${listing.price}</p>
            <p>Description: {listing.description}</p>

            <div className="car-details">
                <p>Body: {car.chassis}</p>
                <p>Mileage: {listing.mileage}</p>
                <p>Horsepower: {car.horsepower}</p>
                <p>Drivetrain: {car.drivetrain}</p>
                <p>Transmission: {car.transmission}</p>
            </div>
            <Link to={'/view/listing/' + listing.listingId} onClick={increaseViewCount}>View</Link>

            {user?.user.sub === listing.listingUser.username || user?.user.authorities.includes("ADMIN") ? listing.isAvailable === true ? (
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
    )
}

export default Listing;