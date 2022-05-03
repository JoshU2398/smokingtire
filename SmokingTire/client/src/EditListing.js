import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AuthContext from "./AuthContext";





function EditListing(){
    const[toEdit, setToEdit] = useState(null);

    const {someId} = useParams();

    const[userStatus, setUserStatus] = useContext(AuthContext);

    const nav = useNavigate();

    useEffect(
        () => {

            const jwt = localStorage.getItem("token");
            if(jwt){

                fetch("http://localhost:8080/api/listing/" + someId,
                {
                    headers: {
                        Authorization: "Bearer " + jwt
                    }
                }
                ).then(response => {
                    if(response.status == 200){
                        return response.json();
                    }else{
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
                }else{
                    nav("/login");
                }
            },
            []
    );


            function handleTextChange(event){
                let copy = {...toEdit};
                copy.listingText = event.target.value;
                setToEdit(copy);

            }

            // function handleDateChange(event){
            //     let copy = {...toEdit};
            //     copy.createDate = event.target.value;
            //     setToEdit(copy);
            // }



            function handleAvailabilityChange(event){
                let copy = {...toEdit};
                copy.isAvailable = event.target.value;
                setToEdit(copy);

            }

            function handleMileageChange(event){
                let copy = {...toEdit};
                copy.mileage = event.target.value;
                setToEdit(copy);

            }

            function handlePriceChange(event){
                let copy = {...toEdit};
                copy.price = event.target.value;
                setToEdit(copy);

            }



            function handleEditSubmit(event){
                event.preventDefault();

                const jwt = localStorage.getItem("token");

                fetch("http://localhost:8080/api/listing", {
                    method: "PUT",
                    headers: {
                        Authorization: "Bearer " + jwt,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(toEdit)
                })
                .then(response => {
                    if(response.status == 200){
                        nav("/");
                    }else{
                        console.log(response);
                        alert("edit failed");
                    }
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Edit failed!");
                });

            }

            return toEdit ? <form onSubmit={handleEditSubmit}>


                        <label htmlFor="listingText">Text: </label><br/>
                        <textarea className="listing-edit" id="listingText" value={toEdit?.listingText} onChange={handleTextChange}></textarea><br/>

                        <label htmlFor="listingAvailability">Available?: </label><br/>
                        <textarea className="listing-edit" id="listingAvailability" value={toEdit?.isAvailable} onChange={handleAvailabilityChange}></textarea><br/>

                        <label htmlFor="listingPrice">Price: </label><br/>
                        <textarea className="listing-edit" id="listingPrice" value={toEdit?.price} onChange={handlePriceChange}></textarea><br/>

                        <label htmlFor="listingMileage">Mileage: </label><br/>
                        <textarea className="listing-edit" id="listingMileage" value={toEdit?.mileage} onChange={handleMileageChange}></textarea><br/>
                        <button>Submit</button>
            </form> :
            <></>




        }

        export default EditListing;
    
