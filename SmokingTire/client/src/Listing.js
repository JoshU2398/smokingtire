import { useContext } from "react";






function Listing(props){
    const {listingId, listingText, userId, carId, createDate, views, mileage, price, isAvailable} = props.listingObj;
    const [user, setUser] = useContext(AuthContext);

    return (
        <div className="listing-item">
            <h1>Car: {carId}</h1>
            <p>Views: {views}</p>
            <p>Mileage: {mileage}</p>
            <p>Price: {price}</p>
            <p>Description: {listingText}</p>
            <p>Available? {isAvailable}</p>
            <h3>User: {userId}</h3>
            <p>Created: {createDate}</p>
            {user?.user.sub === userId || user?.user.authorities.includes("USER") ? (
                <>
                <Link to={'/edit/' + listingId}>Edit</Link>
                <DeleteListing listingId={listingId} removeFromState={props.removeFromState}/>
                </>
            ) : (
                null
            )}
        </div>
    )
}

export default Listing;