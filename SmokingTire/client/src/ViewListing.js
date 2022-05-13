import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import AuthContext from "./AuthContext";

function ViewListing() {

    const [listing, setListing] = useState(null);
    const { id, imageUrl } = useParams();

    const [userStatus, setUserStatus] = useContext(AuthContext);

    const nav = useNavigate();
    const jwt = localStorage.getItem("token");
    const [image, setImage] = useState(null);

    useEffect(() => {

        fetch("http://localhost:8080/api/listings/findListing/" + id)
            .then(response => {
                if (response.status === 200) {
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

    }, []);

    useEffect(() => {
        fetch("http://localhost:8080/api/image/" + imageUrl)
            .then(response => {
                if (response.status === 200) {
                    return response.blob();
                } else {
                    alert("We couldn't find the image!")
                }
            })
            .then(imageBlob => {
                console.log(imageBlob);
                const imageObjectURL = URL.createObjectURL(imageBlob);
                setImage(imageObjectURL);
            })
            .catch(rejection => {
                console.log(JSON.stringify(rejection));
                alert("Your server isn't on.")
            });
    },
        []);

    console.log(listing);
    console.log(userStatus?.user.sub)

    return (
        <div className="view-listing">
            {listing != undefined || listing != null ? (
                <div className="listing-item">
                    <h3>{listing.car.model.modelYear} {listing.car.model.makeName} {listing.car.model.modelName}</h3>
                    {image !== undefined || image !== null ? <img src={image} alt="Not found."></img> : null}
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
                    {listing.available === true ? userStatus?.user.sub !== listing.listingUser.username ?
                        <Link to={'/purchase/' + listing.listingId}>Buy Now</Link> 
                    : 
                        null
                    :
                        null
                    }

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
            ) : null}
        </div>
    )
}

export default ViewListing;