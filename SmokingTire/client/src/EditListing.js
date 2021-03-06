import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AuthContext from "./AuthContext";

function EditListing() {
    const [toEdit, setToEdit] = useState(null);

    const { id } = useParams();

    const [userStatus, setUserStatus] = useContext(AuthContext);

    const nav = useNavigate();

    useEffect(
        () => {

            const jwt = localStorage.getItem("token");


            if(jwt) {
                fetch("http://smokingtiresui-env-1.eba-2r42cd2t.us-east-1.elasticbeanstalk.com/api/listings/findListing/" + id,
                {
                    headers: {
                        Authorization: "Bearer " + jwt

                    }
                ).then(response => {
                    if (response.status == 200) {
                        return response.json();
                    } else {
                        console.log(response);
                        alert("retrieving toEdit failed");
                    }
                })
                    .then(retrievedListing => {
                        console.log(retrievedListing);
                        setToEdit(retrievedListing);
                    })
                    .catch(rejection => {
                        console.log(rejection);
                        alert("Rejected!");
                    });
            } else {
                nav("/login");
            }
        }, []
    );


    function handleTextChange(event) {
        let copy = { ...toEdit };
        copy.description = event.target.value;
        setToEdit(copy);

    }

    // function handleDateChange(event){
    //     let copy = {...toEdit};
    //     copy.createDate = event.target.value;
    //     setToEdit(copy);
    // }

    function handleMileageChange(event) {
        let copy = { ...toEdit };
        copy.mileage = event.target.value;
        setToEdit(copy);

    }

    function handlePriceChange(event) {
        let copy = { ...toEdit };
        copy.price = event.target.value;
        setToEdit(copy);

    }

    function handleEditSubmit(event) {
        event.preventDefault();

        const jwt = localStorage.getItem("token");
        console.log(toEdit);

        const listingUser = {
            userId: toEdit.listingUser.userId, username: toEdit.listingUser.username,
            password: toEdit.listingUser.password, roles: toEdit.listingUser.roles
        };
        const listing = {

            listingId:toEdit.listingId, description:toEdit.description, listingUser:listingUser, 
            car:toEdit.car, postDate:toEdit.postDate, viewCount:toEdit.viewCount, mileage:toEdit.mileage, 
            price:toEdit.price, isAvailable:toEdit.available, imageUrl:toEdit.imageUrl

        };

        console.log(listing);

        fetch("http://smokingtiresui-env-1.eba-2r42cd2t.us-east-1.elasticbeanstalk.com/api/listings/edit/" + id, {
            method: "PUT",
            headers: {
                Authorization: "Bearer " + jwt,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(listing)
        })
            .then(response => {
                if (response.status == 204) {
                    nav("/userpage");
                } else {
                    console.log(response);
                    alert("edit failed");
                }
            })
            .catch(rejection => {
                console.log(rejection);
                alert("Edit failed!");
            });

    }

    function cancel() {
        nav("/userpage");
    }


    return toEdit ? <form className="edit-listing" onSubmit={handleEditSubmit}>
                <label htmlFor="listingText">Description: </label><br/>
                <textarea className="listing-edit" id="listingText" value={toEdit?.description} onChange={handleTextChange}></textarea><br/>


        <label htmlFor="listingPrice">Price: </label><br />
        <textarea className="listing-edit" id="listingPrice" value={toEdit?.price} onChange={handlePriceChange}></textarea><br /><br />

        <label htmlFor="listingMileage">Mileage: </label><br />
        <textarea className="listing-edit" id="listingMileage" value={toEdit?.mileage} onChange={handleMileageChange}></textarea><br /><br />
        <button>Submit</button>
        <button onClick={cancel}>Cancel</button>
    </form></div> :
        <></>

}

export default EditListing;

