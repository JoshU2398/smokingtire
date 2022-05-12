
import { useContext, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthContext from "./AuthContext";

function Listing(props) {

    const { listingId, description, userId, carId, postDate, viewCount, mileage, price, isAvailable, imageUrl } = props.listingObj;

    console.log(props.listingObj);
    const temp = props.listingObj;

    const listing = {
        listingId: temp.listingId, description: temp.description, listingUser: temp.listingUser,
        car: temp.car, postDate: temp.postDate, viewCount: temp.viewCount, mileage: temp.mileage,
        price: temp.price, isAvailable: temp.available, imageUrl: temp.imageUrl
    };

    const listingUser = {
        userId: listing.listingUser.userId, username: listing.listingUser.username,
        password: listing.listingUser.password, roles: listing.listingUser.roles
    };
    listing.listingUser = listingUser;
    console.log(listing);

    const car = listing.car
    const make = car.make.makeName;
    const model = car.make.model.modelName;
    const modelYear = car.make.model.modelYear;
    const jwt = localStorage.getItem("token");

    const [image, setImage] = useState(null);
    const [user, setUser] = useContext(AuthContext);
    const nav = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8080/api/image/" + imageUrl)
        .then(response => {
            if (response.status === 200){
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



    function increaseViewCount() {

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
                        console.log(response);
                    } else {
                        console.log(response);
                        alert("update to view count failed!");
                    }
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Rejected!")
                });

        } else {
            nav("/login");
        }

    }

    return (
        <div className="container">
            <h3>{modelYear} {make} {model}</h3>
            {image !== undefined || image !== null ? <img src={image} alt="Not found."></img> : null}
            <p>Posted on: {listing.postDate}</p>
            <p>Views: {listing.viewCount}</p>
            <p>Price: ${listing.price}</p>
            <p>Mileage: {listing.mileage}</p>

            <Link to={'/view/listing/' + listing.listingId + "/" + listing.imageUrl} onClick={increaseViewCount}>View</Link>

        </div>
    )
}

export default Listing;