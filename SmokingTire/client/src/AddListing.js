import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "./AuthContext";


function AddListing(){

    const [price, setPrice] = useState(0);
    const [mileage, setMileage] = useState(0);
    const [postDate, setPostDate] = useState("");
    const [description, setDescription] = useState("");
    const [viewCount, setViewCount] = useState(0);
    const [user, setUser] = useContext(AuthContext);
    const [car, setCar] = useState({});
    const [isAvailable, setAvailable] = useState(true);
    const [horsepower, setHorsepower] = useState(0);
    const [drivetrain, setDrivetrain] = useState("");
    const [chassis, setChassis] = useState("");
    const [transmission, setTransmission] = useState("");
    const [make, setMake] = useState({});
    const [makeName, setMakeName] = useState("");
    const [model, setModel] = useState({});
    const [modelName, setModelName] = useState("");
    const [modelYear, setModelYear] = useState(0);

    const navigate = useNavigate();
    const jwt = localStorage.getItem("token");

    if(jwt){
        navigate("addListing");
    } else {
        navigate("/");
    }


    function addPriceHandler(e){
        setPrice(e.target.value);
    }

    function addMileageHandler(e){
        setMileage(e.target.value);
    }

    function addPostDateHandler(e){
        setPostDate(e.target.value);
    }

    function addDescriptionHandler(e){
        setDescription(e.target.value);
    }

    function addViewCountHandler(e){
        setViewCount(e.target.value);
    }

    function addCarHandler(e){
        setCar(e.target.value);
    }

    function addHorsePowerHandler(e){
        setHorsepower(e.target.value);
    }

    function addDrivetrainHandler(e){
        setDrivetrain(e.target.value);
    }

    function addChassisHandler(e){
        setChassis(e.target.value);
    }

    function addTransmissionHandler(e){
        setTransmission(e.target.value);
    }

    function addMakeHandler(e){
        setMake(e.target.value);
    }

    function addMakeNameHandler(e){
        setMakeName(e.target.value);
    }

    function addModelHandler(e){
        setModel(e.target.value);
    }

    function addModelNameHandler(e){
        setModelName(e.target.value);
    }

    function addModelYearHandler(e){
        setModelYear(e.target.value);
    }



    function handleSubmit(e) {
        e.preventDefault();

        let newModel = {
            modelName: modelName,
            modelYear: modelYear
        }

        let newMake = {
            makeName: makeName,
            model: newModel
        }

        let newCar = {
            horsepower: horsepower,
            drivetrain: drivetrain,
            chassis: chassis,
            transmission: transmission,
            make: newMake
        }
        
        let newListing = {
            price: price,
            mileage: mileage,
            postDate: postDate,
            description: description,
            viewCount: viewCount,
            user: user,
            car: newCar,
            isAvailable: isAvailable};

            fetch("http://localhost:8080/api/add", {
                method: "POST",
                headers: {
                    Authorization: "Bearer " + jwt,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(newListing)
            })
            .then(response => {
                alert(response.status);
                navigate("/")
            })
            .catch(
                rejection => console.log("failure ", rejection)
            );
    }




    return (
        <>
            <form className="addListing" onSubmit={handleSubmit}>
                <h3 className="addListingTitle">Create a Listing</h3>
                <div className="numInput">
                    <label htmlFor="price"><b>Enter Price</b></label>
                    <input
                        name="price"
                        placeholder="0"
                        onChange={addPriceHandler}></input>
                </div>
                <div className="numInput">
                    <label htmlFor="mileage"><b>Enter Mileage</b></label>
                    <input
                        name="Mileage"
                        placeholder="0"
                        onChange={addMileageHandler}></input>
                </div>
                <div className="stringInput">
                    <label htmlFor="postDate"><b>Enter Post Date</b></label>
                    <input
                        name="postDate"
                        placeholder="mm-dd-yyyy"
                        onChange={addPostDateHandler}></input>
                </div>
                <div className="stringInput">
                    <label htmlFor="description"><b>Enter Description</b></label>
                    <text
                        name="Description"
                        placeholder="dummy text"
                        onChange={addDescriptionHandler}></text>
                </div>
                <div className="numInput">
                    <label htmlFor="viewCount"><b>Enter View Count</b></label>
                    <input
                        name="viewCount"
                        placeholder="0"
                        onChange={addViewCountHandler}></input>
                </div>
                <div className="stringInput">
                    <label htmlFor="makeName"><b>Enter Make</b></label>
                    <input
                        name="MakeName"
                        placeholder="Make"
                        onChange={addMakeNameHandler}></input>
                </div>
                <div className="stringInput">
                    <label htmlFor="modelName"><b>Enter Model</b></label>
                    <input
                        name="modelName"
                        placeholder="model"
                        onChange={addModelNameHandler}></input>
                </div>
                <div className="numInput">
                    <label htmlFor="modelYear"><b>Enter Year</b></label>
                    <input
                        name="modelYear"
                        placeholder="0"
                        onChange={addModelYearHandler}></input>
                </div>
            </form>
        </>

        )
}

export default AddListing;